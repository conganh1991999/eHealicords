package com.anhnc2.ehealicords.service.user;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.constant.UserStatus;
import com.anhnc2.ehealicords.data.cache.VerifyCode;
import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.common.User;
import com.anhnc2.ehealicords.data.entity.DistrictEntity;
import com.anhnc2.ehealicords.data.entity.ProvinceEntity;
import com.anhnc2.ehealicords.data.entity.UserEntity;
import com.anhnc2.ehealicords.data.entity.UserRelativeEntity;
import com.anhnc2.ehealicords.data.entity.WardEntity;
import com.anhnc2.ehealicords.data.response.BalanceResult;
import com.anhnc2.ehealicords.data.response.LiteUser;
import com.anhnc2.ehealicords.data.response.LiteUserRelative;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.exception.LoginException;
import com.anhnc2.ehealicords.exception.PatientException;
import com.anhnc2.ehealicords.exception.RegisterException;
import com.anhnc2.ehealicords.repository.UserRelativeRepository;
import com.anhnc2.ehealicords.repository.UserRepository;
import com.anhnc2.ehealicords.service.common.AppUserService;
import com.anhnc2.ehealicords.service.common.JwtService;
import com.anhnc2.ehealicords.service.external.SmsService;
import com.anhnc2.ehealicords.service.external.StorageService;
import com.anhnc2.ehealicords.service.clinic.LocationService;
import com.anhnc2.ehealicords.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String PATIENT_KEY_PREFIX = "patients";
    private static final String SMS_PREFIX = "Mã xác thực Hey Doctor của bạn là ";

    private final UserRepository userRepository;
    private final LocationService locationService;
    private final UserRelativeRepository userRelativeRepository;
    private final JwtService jwtService;
    private final SmsService smsService;
    private final AppUserService userService;
    private final StorageService storageService;
    private final Cache codeCache;

    public UserServiceImpl(
            UserRepository userRepository,
            UserRelativeRepository userRelativeRepository,
            JwtService jwtService,
            SmsService smsService,
            AppUserService userService,
            StorageService storageService,
            CacheManager cacheManager,
            LocationService locationService) {
        this.userRepository = userRepository;
        this.userRelativeRepository = userRelativeRepository;
        this.jwtService = jwtService;
        this.smsService = smsService;
        this.userService = userService;
        this.storageService = storageService;
        this.locationService = locationService;
        this.codeCache = cacheManager.getCache("auth-codes");
    }

    @Override
    public void createRegisterCode(String phoneNumber) {
        // Check if account is existed
        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new RegisterException(StatusCode.ACCOUNT_EXISTED);
        }

        VerifyCode verifyCode = genVerifyCode(phoneNumber);

        // Send to user
        smsService.send(NumberUtil.formatPhoneNumber(phoneNumber), SMS_PREFIX + verifyCode.getCode());
    }

    @Override
    public void createLoginCode(String phoneNumber) {
        // Check if account is existed
        if (!userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new LoginException(StatusCode.ACCOUNT_NOT_EXISTED);
        }

        VerifyCode verifyCode = genVerifyCode(phoneNumber);

        // Send to user
        smsService.send(NumberUtil.formatPhoneNumber(phoneNumber), SMS_PREFIX + verifyCode.getCode());
    }

    @Override
    public String register(String phoneNumber, String verifyCode) {
        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new RegisterException(StatusCode.ACCOUNT_EXISTED);
        }

        VerifyCode cachedCode = getVerifyCode(phoneNumber);
        if (cachedCode != null && cachedCode.check(verifyCode)) {
            UserEntity userEntity =
                    UserEntity.builder()
                            .phoneNumber(phoneNumber)
                            .updatedTime(System.currentTimeMillis())
                            .status(UserStatus.ACTIVE)
                            .balance(0L)
                            .balanceUpdateTs(System.currentTimeMillis())
                            .isValid(false)
                            .build();
            userRepository.saveAndFlush(userEntity);
            return jwtService.generate(userEntity);
        }

        throw new RegisterException(StatusCode.INVALID_VERIFY_CODE);
    }

    @Override
    public String login(String phoneNumber, String verifyCode) {
        Optional<UserEntity> patientOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (!patientOptional.isPresent()) {
            throw new LoginException(StatusCode.ACCOUNT_NOT_EXISTED);
        }

        VerifyCode cachedCode = getVerifyCode(phoneNumber);
        if (cachedCode != null && cachedCode.check(verifyCode)) {
            UserEntity userEntity = patientOptional.get();
            return jwtService.generate(userEntity);
        }

        throw new LoginException(StatusCode.INVALID_VERIFY_CODE);
    }

    @Override
    public User getCurrentPatientWithLocation() {
        UserEntity userEntity = userRepository.findById(userService.getCurrentUser().getId()).get();

        User user = User.fromEntity(userEntity);

        Integer provinceId = userEntity.getProvinceId();
        if (provinceId != null) {
            user.setProvinceName(
                    locationService.getProvinceById(provinceId).orElse(new ProvinceEntity()).getName());
        }

        Integer districtId = userEntity.getDistrictId();
        if (districtId != null) {
            user.setDistrictName(
                    locationService.getDistrictById(districtId).orElse(new DistrictEntity()).getName());
        }

        Integer wardId = userEntity.getWardId();
        if (wardId != null) {
            user.setWardName(locationService.getWardById(wardId).orElse(new WardEntity()).getName());
        }

        return user;
    }

    @Override
    public User getCurrentPatient() {
        UserEntity userEntity = userRepository.findById(userService.getCurrentUser().getId()).get();
        return User.fromEntity(userEntity);
    }

    @Override
    public List<UserRelativeEntity> getAllRelatives() {
        return userRelativeRepository.findAllByUserId(userService.getCurrentUserId());
    }

    @Override
    public void updateInfo(long userId, User user) {
        LOGGER.info("Update user info: user={}", user);

        UserEntity dao = userRepository.findById(userId).get();

        if (user.getFullName() == null || user.getDateOfBirth() == null || user.getGender() == null) {
            throw new AppException(StatusCode.INVALID_INFO);
        }

        dao.setFullName(user.getFullName());
        dao.setGender(user.getGender());
        dao.setPersonalId(user.getPersonalId());
        dao.setJob(user.getJob());
        dao.setProvinceId(user.getProvinceId());
        dao.setDistrictId(user.getDistrictId());
        dao.setWardId(user.getWardId());
        dao.setAddress(user.getAddress());
        dao.setUpdatedTime(System.currentTimeMillis());
        dao.setStatus(UserStatus.ACTIVE);
        dao.setIsValid(true);
        dao.setDateOfBirth(LocalDate.parse(user.getDateOfBirth()));

        userRepository.saveAndFlush(dao);
    }

    @Override
    public PresignResult getAvatarUpdateUrl(String fileName) {
        Long userId = userService.getCurrentUserId();
        String key = String.join("/", PATIENT_KEY_PREFIX, String.valueOf(userId), "avatar", fileName);
        return PresignResult.builder().key(key).presignUrl(storageService.preSign(key)).build();
    }

    @Override
    public void updateAvatar(String key) {
        long userId = userService.getCurrentUserId();
        User currentUserDTODTO = getCurrentPatientWithLocation();
        String avatarKey = currentUserDTODTO.getAvatarKey();

        if (avatarKey != null) {
            // storageService.delete(avatarKey);
        }

        userRepository.updateAvatarKey(userId, key);
    }

    @Override
    public Long createNewRelative(UserRelativeEntity userRelativeEntity) {
        userRelativeEntity.setUserId(userService.getCurrentUserId());
        UserRelativeEntity relative = userRelativeRepository.saveAndFlush(userRelativeEntity);
        return relative.getId();
    }

    @Override
    public void updateRelative(UserRelativeEntity userRelativeEntity) {
        long patientId = userService.getCurrentUserId();

        UserRelativeEntity dbObject =
                userRelativeRepository.findByUserIdAndId(patientId, userRelativeEntity.getId());

        if (dbObject != null) {
            userRelativeEntity.setUserId(patientId);
            userRelativeRepository.saveAndFlush(userRelativeEntity);
            return;
        }

        throw new PatientException(StatusCode.RELATIVE_NOT_EXISTED);
    }

    @Override
    public void deleteRelative(Long id) {
        long patientId = userService.getCurrentUserId();

        UserRelativeEntity dbObject = userRelativeRepository.findByUserIdAndId(patientId, id);

        if (dbObject != null) {
            userRelativeRepository.delete(dbObject);
        }

        throw new PatientException(StatusCode.RELATIVE_NOT_EXISTED);
    }

    @Override
    public BalanceResult getBalanceOfUser(long userId) {
        UserEntity patient = userRepository.findById(userId).get();
        LOGGER.info("Get balance of userId= {}, balance={}", userId, patient.getBalance());
        return new BalanceResult(patient.getBalance(), patient.getBalanceUpdateTs());
    }

    @Override
    public UserRelativeEntity getRelative(long relativeId) {
        return userRelativeRepository.findById(relativeId).get();
    }

    @Override
    public PaginationResponse<List<LiteUser>> getAllUsers(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<UserEntity> userPage = userRepository.findAll(pageRequest);
        return PaginationResponse.<List<LiteUser>>builder()
                .page(page)
                .pageSize(pageSize)
                .total(userPage.getTotalElements())
                .totalPage(userPage.getTotalPages())
                .items(userPage.getContent().stream().map(LiteUser::fromDAO).collect(Collectors.toList()))
                .build();
    }

    @Override
    public User getUserDetailById(long userId) {
        UserEntity userEntity = userRepository.findById(userId).get();

        User user = User.fromEntity(userEntity);

        Integer provinceId = userEntity.getProvinceId();
        if (provinceId != null) {
            user.setProvinceName(
                    locationService.getProvinceById(provinceId).orElse(new ProvinceEntity()).getName());
        }

        Integer districtId = userEntity.getDistrictId();
        if (districtId != null) {
            user.setDistrictName(
                    locationService.getDistrictById(districtId).orElse(new DistrictEntity()).getName());
        }

        Integer wardId = userEntity.getWardId();
        if (wardId != null) {
            user.setWardName(locationService.getWardById(wardId).orElse(new WardEntity()).getName());
        }

        return user;
    }

    @Override
    public List<LiteUserRelative> getUserRelatives(long userId) {
        return userRelativeRepository.findAllByUserId(userId).stream()
                .map(LiteUserRelative::fromDAO).collect(Collectors.toList());
    }

    @Override
    public PaginationResponse<List<LiteUserRelative>> getAllRelativeUsers(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<UserRelativeEntity> userRelativePage = userRelativeRepository.findAll(pageRequest);

        return PaginationResponse.<List<LiteUserRelative>>builder()
                .pageSize(pageSize)
                .page(page)
                .total(userRelativePage.getTotalElements())
                .totalPage(userRelativePage.getTotalPages())
                .items(userRelativePage.getContent().stream().map(LiteUserRelative::fromDAO).collect(
                        Collectors.toList()))
                .build();
    }

    private VerifyCode genVerifyCode(String phoneNumber) {
        VerifyCode verifyCode =
                VerifyCode.builder().code(NumberUtil.randomVerifyCode()).validTimes(4).build();

        codeCache.put(phoneNumber, verifyCode);

        return verifyCode;
    }

    private VerifyCode getVerifyCode(String phoneNumber) {
        VerifyCode verifyCode = codeCache.get(phoneNumber, VerifyCode.class);

        if (verifyCode != null) {
            verifyCode.decreaseValidTimes();
            codeCache.put(phoneNumber, verifyCode);
        }

        return verifyCode;
    }
}
