package com.anhnc2.ehealicords.service.specialist;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistCreationRequest;
import com.anhnc2.ehealicords.data.request.SpecialistUpdateRequest;
import com.anhnc2.ehealicords.data.response.DoctorResponse;
import com.anhnc2.ehealicords.data.response.LiteStaff;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.data.response.SpecialistDetailsResponse;
import com.anhnc2.ehealicords.data.response.StaffInfoResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.exception.RegisterException;
import com.anhnc2.ehealicords.repository.BranchRepository;
import com.anhnc2.ehealicords.repository.MedicalSpecialtyRepository;
import com.anhnc2.ehealicords.repository.RoomRepository;
import com.anhnc2.ehealicords.repository.SpecialistRepository;
import com.anhnc2.ehealicords.repository.StaffRepository;
import com.anhnc2.ehealicords.service.common.AppUserService;
import com.anhnc2.ehealicords.service.external.MailService;
import com.anhnc2.ehealicords.service.external.StorageService;
import com.anhnc2.ehealicords.service.staff.StaffService;
import com.anhnc2.ehealicords.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {

    private static final String SPECIALIST_KEY_PREFIX = "specialists";
    private static final String AVATAR = "avatar";

    private final AppUserService userService;
    private final StaffService staffService;

    private final StorageService storageService;
    private final MailService mailService;

    private final StaffRepository staffRepository;
    private final MedicalSpecialtyRepository medicalSpecialtyRepository;
    private final BranchRepository branchRepository;
    private final RoomRepository roomRepository;
    private final SpecialistRepository specialistRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public StaffInfoResponse createSpecialist(SpecialistCreationRequest specialist, MultipartFile avatar) {
        StaffEntity createdStaff = staffService.createStaffForSpecialist(specialist);

        SpecialistEntity createdSpecialist = createSpecialistProfile(specialist, avatar, createdStaff);

        // sendSuccessEmailToSpecialist(createdStaff, createdSpecialist);
        // notifyToDoctorOverEmail(request, password);

        return new StaffInfoResponse(createdStaff, createdSpecialist);
    }

    private SpecialistEntity createSpecialistProfile(SpecialistCreationRequest specialist,
                                                     MultipartFile avatar, StaffEntity createdStaff) {
        SpecialistEntity newSpecialist =
                SpecialistEntity.builder()
                        .email(specialist.getEmail())
                        .fullName(specialist.getFullName())
                        .phoneNumber(specialist.getPhoneNumber())
                        .avatarKey(specialist.getAvatarKey())
                        .dateOfBirth(LocalDate.parse(specialist.getDateOfBirth()))
                        .dateOfStartingWork(LocalDate.parse(specialist.getDateOfStartingWork()))
                        .gender((specialist.getGender()))
                        .specialistType((specialist.getType()))
                        .academicRank((specialist.getAcademicRank()))
                        .degree((specialist.getDegree()))
                        .degreeOfSpecialist((specialist.getDegreeOfSpecialist()))
                        .medialSpecialtyId(specialist.getMedialSpecialtyId())
                        .roomId(specialist.getRoomId())
                        .staffId(createdStaff.getId())
                        .branchId(specialist.getBranchId())
                        .updatedTime(System.currentTimeMillis())
                        .build();

        newSpecialist = specialistRepository.saveAndFlush(newSpecialist);

        if (avatar == null) {
            return newSpecialist;
        }

        String avatarFileName = avatar.getOriginalFilename();
        String avatarKey =
                String.join(
                        "/",
                        SPECIALIST_KEY_PREFIX,
                        String.valueOf(newSpecialist.getId()),
                        AVATAR,
                        FileUtil.appendCurrentTimeMillisToName(
                                avatarFileName == null ? AVATAR : avatarFileName
                        )
                );

        try {
            storageService.put(avatarKey, FileUtil.convertMultipartFileToFile(avatar));
            newSpecialist.setAvatarKey(avatarKey);
            newSpecialist.setUpdatedTime(System.currentTimeMillis());
            return specialistRepository.save(newSpecialist);
        } catch (IOException e) {
            throw new RegisterException(StatusCode.FILE_SAVED_FAIL);
        }
    }

    @Override
    public List<SpecialistEntity> getAllSpecialists() {
        return specialistRepository.findAll();
    }

    @Override
    public List<LiteStaff> getAllSpecialistsOfBranch(Integer branchId) {
        List<SpecialistEntity> specialists = specialistRepository.findAllSpecialistOfBranch(branchId);
        return specialists.stream().map(LiteStaff::fromDAO).collect(Collectors.toList());
    }

    @Override
    public List<LiteStaff> getAllSpecialistsOfSpecialty(Integer branchId, Integer specialtyId) {
        List<SpecialistEntity> specialists =
                specialistRepository.findAlByMedialSpecialtyIdAndBranchId(specialtyId, branchId);

        return specialists.stream().map(LiteStaff::fromDAO).collect(Collectors.toList());
    }

    @Override
    public SpecialistDetailsResponse getSpecialist(Long id) {
        SpecialistEntity specialist =
                specialistRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(StatusCode.STAFF_DOES_NOT_EXISTS));

        return SpecialistDetailsResponse.builder()
                .id(specialist.getId())
                .email(specialist.getEmail())
                .fullName(specialist.getFullName())
                .phoneNumber(specialist.getPhoneNumber())
                .avatarKey(specialist.getAvatarKey())
                .dateOfBirth(specialist.getDateOfBirth())
                .dateOfStartingWork(specialist.getDateOfStartingWork())
                .gender(specialist.getGender())
                .specialistType(specialist.getSpecialistType())
                .academicRank(specialist.getAcademicRank())
                .degree(specialist.getDegree())
                .degreeOfSpecialist(specialist.getDegreeOfSpecialist())
                .medicalSpecialtyId(specialist.getMedialSpecialtyId())
                .medicalSpecialtyName(medicalSpecialtyRepository.getById(specialist.getMedialSpecialtyId()).getName())
                .branchId(specialist.getBranchId())
                .branchName(branchRepository.getById(specialist.getBranchId()).getName())
                .roomId(specialist.getRoomId())
                .roomName(roomRepository.getById(specialist.getRoomId()).getName())
                .createdTime(specialist.getCreatedTime())
                .updatedTime(specialist.getUpdatedTime())
                .build();
    }

    @Override
    public void changeSpecialistPassword(PasswordUpdateRequest request) {
        staffService.updateStaffPassword(request);
    }

    @Override
    public void deleteSpecialist(Long specialistId) {
        // DELETE staff
        // DELETE avatar
        // DELETE specialist
    }

    @Override
    @Transactional
    public void updateSpecialistInformation(Long specialistId, SpecialistUpdateRequest specialistUpdateRequest) {
        SpecialistEntity specialist = specialistRepository.getById(specialistId);
        StaffEntity staff = staffRepository.getById(specialist.getStaffId());

        specialist.setEmail(specialistUpdateRequest.getEmail());
        specialist.setFullName(specialistUpdateRequest.getFullName());
        specialist.setPhoneNumber(specialistUpdateRequest.getPhoneNumber());
        specialist.setDateOfBirth(specialistUpdateRequest.getDateOfBirth());
        specialist.setDateOfStartingWork(specialistUpdateRequest.getDateOfStartingWork());
        specialist.setGender(specialistUpdateRequest.getGender());
        specialist.setSpecialistType(specialistUpdateRequest.getSpecialistType());
        specialist.setAcademicRank(specialistUpdateRequest.getAcademicRank());
        specialist.setDegree(specialistUpdateRequest.getDegree());
        specialist.setDegreeOfSpecialist(specialistUpdateRequest.getDegreeOfSpecialist());
        specialist.setMedialSpecialtyId(specialistUpdateRequest.getMedialSpecialtyId());
        specialist.setRoomId(specialistUpdateRequest.getRoomId());
        specialist.setUpdatedTime(System.currentTimeMillis());

        specialistRepository.saveAndFlush(specialist);

        String oldEmail = staff.getEmail();

        staff.setFullName(specialistUpdateRequest.getFullName());
        staff.setEmail(specialistUpdateRequest.getEmail());
        staff = staffRepository.saveAndFlush(staff);

        if (!oldEmail.equals(specialistUpdateRequest.getEmail())) {
            staffService.resetPassword(staff);
        }
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
    public SpecialistEntity getByStaffId(long id) {
        return specialistRepository.findByStaffId(id);
    }

    @Override
    public PresignResult getPresignUrl(String filename, String filetype) {
        String key = String.join("/", SPECIALIST_KEY_PREFIX, "avatar", filename);

        return PresignResult.builder()
                .key(key)
                .presignUrl(storageService.preSignWithType(key, filetype))
                .build();
    }

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

    private String getSpecialtyName(SpecialistEntity specialist) {
        return medicalSpecialtyRepository.findById(specialist.getMedialSpecialtyId()).get().getName();
    }

//    private void sendSuccessEmailToSpecialist(
//            StaffEntity createdStaff, SpecialistEntity createdSpecialist) {
//        String to = createdStaff.getEmail();
//        String subject = "HeyDoctor: Đăng ký tài khoản bác sĩ thành công!";
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("doctorName", createdSpecialist.getFullName());
//        params.put("email", createdStaff.getEmail());
//        params.put("password", createdStaff.getEmail());
//
//        mailService.sendEmail(to, subject, "create-doctor", params);
//    }
//
//    private void notifyToDoctorOverEmail(SpecialistCreationRequest request, String password) {
//        String to = request.getEmail();
//        String subject = "HeyDoctor: Tạo Tài Khoản Bác Sĩ Thành Công!";
//        Map<String, Object> params = new HashMap<String, Object>();
//
//        params.put("doctorName", request.getFullName());
//        params.put("email", to);
//        params.put("password", password);
//        mailService.sendEmail(to, subject, "create-doctor", params);
//    }
//
}
