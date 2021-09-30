package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.constant.RoleType;
import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.constant.UserStatus;
import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.common.Specialist;
import com.anhnc2.ehealicords.data.entity.BranchEntity;
import com.anhnc2.ehealicords.data.entity.RoleEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.CreateDoctorRequest;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistInfoRequest;
import com.anhnc2.ehealicords.data.request.UpdateDoctorRequest;
import com.anhnc2.ehealicords.data.response.DoctorDetailsResponse;
import com.anhnc2.ehealicords.data.response.DoctorResponse;
import com.anhnc2.ehealicords.data.response.LiteSpecialist;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.data.response.SpecialistInfoResponse;
import com.anhnc2.ehealicords.exception.RegisterException;
import com.anhnc2.ehealicords.repository.MedicalSpecialtyRepository;
import com.anhnc2.ehealicords.repository.RoleRepository;
import com.anhnc2.ehealicords.repository.SpecialistRepository;
import com.anhnc2.ehealicords.repository.StaffRepository;
import com.anhnc2.ehealicords.service.common.AppUserService;
import com.anhnc2.ehealicords.service.external.MailService;
import com.anhnc2.ehealicords.service.external.StorageService;
import com.anhnc2.ehealicords.util.FileUtil;
import com.anhnc2.ehealicords.util.PasswordGenerate;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {
    private static final String SPECIALIST_KEY_PREFIX = "specialists";

    private final StaffService staffService;
    private final StorageService storageService;
    private final MailService mailService;
    private final AppUserService userService;
    private final RoleRepository roleRepository;
    private final BranchService branchService;
    private final SpecialistRepository specialistRepository;
    private final PasswordEncoder passwordEncoder;
    private final StaffRepository staffRepository;
    private final MedicalSpecialtyRepository medicalSpecialtyRepository;

    @Override
    public List<SpecialistEntity> getAllSpecialists() {
        return specialistRepository.findAll();
    }

    @Override
    @Transactional
    public SpecialistInfoResponse createSpecialist(
            SpecialistInfoRequest specialist, MultipartFile avatar) {
        StaffEntity createdStaff = createSpecialistAccount(specialist);

        SpecialistEntity createdSpecialist = createSpecialistInfo(specialist, avatar, createdStaff);

        sendSuccessEmailToSpecialist(createdStaff, createdSpecialist);

        return new SpecialistInfoResponse(createdStaff, createdSpecialist);
    }

    private StaffEntity createSpecialistAccount(SpecialistInfoRequest specialist) {
        return staffService.createStaff(specialist);
    }

    private SpecialistEntity createSpecialistInfo(
            SpecialistInfoRequest specialist, MultipartFile avatar, StaffEntity createdStaff) {
        SpecialistEntity newSpecialist =
                SpecialistEntity.builder()
                        .staffId(createdStaff.getId())
                        .fullName(specialist.getFullName())
                        .phoneNumber(specialist.getPhoneNumber())
                        .gender(specialist.getGender())
                        .academicRank(specialist.getAcademicRank())
                        .degreeOfSpecialist(specialist.getDegreeOfSpecialist())
                        .specialtyId(specialist.getSpecialtyId())
                        .updatedTime(System.currentTimeMillis())
                        .branchId(specialist.getBranchId())
                        .build();

        newSpecialist = specialistRepository.saveAndFlush(newSpecialist);

        String avatarKey =
                String.join(
                        "/",
                        SPECIALIST_KEY_PREFIX,
                        String.valueOf(newSpecialist.getId()),
                        "avatar",
                        FileUtil.appendCurrentTimeMillisToName(avatar.getOriginalFilename()));

        try {
            storageService.put(avatarKey, FileUtil.convertMultipartFileToFile(avatar));
            newSpecialist.setAvatarKey(avatarKey);
            newSpecialist.setUpdatedTime(System.currentTimeMillis());
            return specialistRepository.save(newSpecialist);
        } catch (IOException e) {
            throw new RegisterException(StatusCode.FILE_SAVED_FAIL);
        }
    }

    private void sendSuccessEmailToSpecialist(
            StaffEntity createdStaff, SpecialistEntity createdSpecialist) {
        String to = createdStaff.getEmail();
        String subject = "HeyDoctor: Đăng ký tài khoản bác sĩ thành công!";
        Map<String, Object> params = new HashMap<>();

        params.put("doctorName", createdSpecialist.getFullName());
        params.put("email", createdStaff.getEmail());
        params.put("password", createdStaff.getEmail());

        mailService.sendEmail(to, subject, "create-doctor", params);
    }

    @Override
    public void updateSpecialistInfo(SpecialistInfoRequest specialist) {
        long staffId = userService.getCurrentUserId();

        SpecialistEntity currentSpecialist = getByStaffId(staffId);

        staffService.updateStaff(staffId, specialist.getFullName(), specialist.getBranchId());

        currentSpecialist.setFullName(specialist.getFullName());
        currentSpecialist.setPhoneNumber(specialist.getPhoneNumber());
        currentSpecialist.setGender(specialist.getGender());
        currentSpecialist.setAcademicRank(specialist.getAcademicRank());
        currentSpecialist.setDegreeOfSpecialist(specialist.getDegreeOfSpecialist());
        currentSpecialist.setSpecialtyId(specialist.getSpecialtyId());
        currentSpecialist.setBranchId(specialist.getBranchId());

        specialistRepository.save(currentSpecialist);
    }

    @Override
    public PresignResult getAvatarUpdateUrl(String fileName) {
        SpecialistEntity specialist = getByStaffId(userService.getCurrentUserId());

        String key =
                String.join(
                        "/", SPECIALIST_KEY_PREFIX, String.valueOf(specialist.getId()), "avatar", fileName);

        return PresignResult.builder().key(key).presignUrl(storageService.preSign(key)).build();
    }

    @Override
    public void updateAvatar(String key) {
        SpecialistEntity specialist = getByStaffId(userService.getCurrentUserId());

        String avatarKey = specialist.getAvatarKey();

        if (avatarKey != null) {
            storageService.delete(avatarKey);
        }

        specialist.setAvatarKey(key);
        specialistRepository.save(specialist);
    }

    @Override
    public void updatePassword(PasswordUpdateRequest request) {
        staffService.updatePassword(userService.getCurrentUserId(), request);
    }

    @Override
    public void deleteSpecialist() {
        // DELETE staff
        // DELETE avatar
        // DELETE specialist
    }

    @Override
    public SpecialistEntity getByStaffId(long id) {
        return specialistRepository.findByStaffId(id);
    }

    @Override
    public SpecialistEntity getBySpecialistId(long specialistId) {
        return specialistRepository.findById(specialistId).get();
    }

    @Override
    public Specialist findById(long id) {
        return Specialist.fromDAO(specialistRepository.findById(id).get());
    }

    @Override
    public List<LiteSpecialist> findAllSpecialistsOfBranch(int branchId) {
        List<SpecialistEntity> specialists = specialistRepository.findAllSpecialistOfBranch(branchId);

        return specialists.stream().map(LiteSpecialist::fromDAO).collect(Collectors.toList());
    }

    @Override
    public List<LiteSpecialist> findAllSpecialistBySpecialityIdAndBranchId(
            int branchId, int specialtyId) {
        List<SpecialistEntity> specialists =
                specialistRepository.findAlBySpecialtyIdAndBranchId(specialtyId, branchId);

        return specialists.stream().map(LiteSpecialist::fromDAO).collect(Collectors.toList());
    }

    @Override
    public PresignResult getPresignUrl(String filename, String filetype) {
        String key = String.join("/", SPECIALIST_KEY_PREFIX, "avatar", filename);

        return PresignResult.builder()
                .key(key)
                .presignUrl(storageService.preSignWithType(key, filetype))
                .build();
    }

    // Author Duc Vo
    @Override
    @Transactional
    public void createDoctor(CreateDoctorRequest request) {

        String password = PasswordGenerate.random();

        StaffEntity savedStaff = createStaffWithRoleDoctor(request, password);

        SpecialistEntity specialist =
                SpecialistEntity.builder()
                        .fullName(request.getFullName())
                        .avatarKey(request.getAvatarKey())
                        .academicRank(request.getAcademicRank())
                        .specialtyId(request.getSpecialtyId())
                        .branchId(request.getBranchId())
                        .degreeOfSpecialist(request.getDegree())
                        .gender(request.getGender())
                        .staffId(savedStaff.getId())
                        .phoneNumber(request.getPhoneNumber())
                        .updatedTime(System.currentTimeMillis())
                        .build();

        specialistRepository.saveAndFlush(specialist);

        notifyToDoctorOverEmail(request, password);
    }

    // Author Duc Vo
    @Override
    public PaginationResponse<List<DoctorResponse>> getAllDoctorOfBranch(
            int branchId, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<SpecialistEntity> doctors = specialistRepository.findAllByBranchId(branchId, pageRequest);
        List<DoctorResponse> result =
                doctors.getContent().stream()
                        .map(
                                specialist ->
                                        DoctorResponse.builder()
                                                .id(specialist.getId())
                                                .fullName(specialist.getFullName())
                                                .specialty(getSpecialtyName(specialist))
                                                .build())
                        .collect(Collectors.toList());

        return PaginationResponse.<List<DoctorResponse>>builder()
                .page(page)
                .pageSize(pageSize)
                .total(doctors.getTotalElements())
                .totalPage(doctors.getTotalPages())
                .items(result)
                .build();
    }

    @Override
    public DoctorDetailsResponse getDetailDoctor(long doctorId) {
        SpecialistEntity specialist = specialistRepository.findById(doctorId).get();
        StaffEntity staff = staffRepository.findById(specialist.getStaffId()).get();

        return DoctorDetailsResponse.builder()
                .id(specialist.getId())
                .fullName(specialist.getFullName())
                .email(staff.getEmail())
                .degree(specialist.getDegreeOfSpecialist())
                .academicRank(specialist.getAcademicRank())
                .avatarKey(specialist.getAvatarKey())
                .phoneNumber(specialist.getPhoneNumber())
                .gender(specialist.getGender())
                .specialtyId(specialist.getSpecialtyId())
                .specialtyName(getSpecialtyName(specialist))
                .branchId(specialist.getBranchId())
                .branchName(staff.getBranchEntity().getName())
                .build();
    }

    @Override
    @Transactional
    public void resetPassword(long doctorId) {
        SpecialistEntity specialist = specialistRepository.findById(doctorId).get();
        StaffEntity staff = staffRepository.findById(specialist.getStaffId()).get();
        String password = PasswordGenerate.random();
        String encodedPassword = passwordEncoder.encode(password);
        staff.setPassword(encodedPassword);
        staff.setStatus(UserStatus.WAITING_CHANGE_PASSWORD);

        staffRepository.saveAndFlush(staff);
        notifyResetPasswordToDoctorOverEmail(staff.getEmail(), staff.getFullName(), password);
    }

    @Override
    @Transactional
    public void updateDoctor(long doctorId, UpdateDoctorRequest updateDoctorRequest) {
        SpecialistEntity specialist = specialistRepository.findById(doctorId).get();
        StaffEntity staff = staffRepository.findById(specialist.getStaffId()).get();

        String oldEmail = staff.getEmail();

        if (updateDoctorRequest.getAvatarKey() != null) {
            specialist.setAvatarKey(updateDoctorRequest.getAvatarKey());
        }

        specialist.setFullName(updateDoctorRequest.getFullName());
        staff.setFullName(updateDoctorRequest.getFullName());

        specialist.setDegreeOfSpecialist(updateDoctorRequest.getDegree());
        specialist.setGender(updateDoctorRequest.getGender());
        specialist.setAcademicRank(updateDoctorRequest.getAcademicRank());
        specialist.setPhoneNumber(updateDoctorRequest.getPhoneNumber());
        staff.setEmail(updateDoctorRequest.getEmail());

        specialistRepository.saveAndFlush(specialist);
        staffRepository.saveAndFlush(staff);

        if (!oldEmail.equals(updateDoctorRequest.getEmail())) {
            resetPassword(specialist.getId());
        }
    }

    private String getSpecialtyName(SpecialistEntity specialist) {
        return medicalSpecialtyRepository.findById(specialist.getSpecialtyId()).get().getName();
    }

    private StaffEntity createStaffWithRoleDoctor(CreateDoctorRequest request, String password) {
        Set<RoleEntity> roleEntities =
                roleRepository.findAllIn(Collections.singletonList(RoleType.ROLE_DOCTOR.name()));

        BranchEntity branch = branchService.getBranchById(request.getBranchId());

        String encodedPassword = passwordEncoder.encode(password);

        StaffEntity staff =
                StaffEntity.builder()
                        .email(request.getEmail())
                        .fullName(request.getFullName())
                        .roleEntities(roleEntities)
                        .password(encodedPassword)
                        .status(UserStatus.WAITING_CHANGE_PASSWORD)
                        .branchEntity(branch)
                        .build();

        return staffRepository.saveAndFlush(staff);
    }

    // Author Duc Vo
    private void notifyToDoctorOverEmail(CreateDoctorRequest request, String password) {
        String to = request.getEmail();
        String subject = "HeyDoctor: Tạo Tài Khoản Bác Sĩ Thành Công!";
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("doctorName", request.getFullName());
        params.put("email", to);
        params.put("password", password);
        mailService.sendEmail(to, subject, "create-doctor", params);
    }

    // Author Duc Vo
    private void notifyResetPasswordToDoctorOverEmail(String to, String name, String password) {
        String subject = "HeyDoctor: Đặt Mật Khẩu Tài Khoản Bác Sĩ Thành Công!";
        Map<String, Object> params = new HashMap<>();

        params.put("doctorName", name);
        params.put("email", to);
        params.put("password", password);
        mailService.sendEmail(to, subject, "doctor-reset-password", params);
    }
}
