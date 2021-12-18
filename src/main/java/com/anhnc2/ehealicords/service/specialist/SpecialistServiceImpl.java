package com.anhnc2.ehealicords.service.specialist;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistCreationRequest;
import com.anhnc2.ehealicords.data.request.SpecialistUpdateRequest;
import com.anhnc2.ehealicords.data.response.SpecialistDetailsResponse;
import com.anhnc2.ehealicords.data.response.SpecialistInfoResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.exception.RegisterException;
import com.anhnc2.ehealicords.repository.SpecialistRepository;
import com.anhnc2.ehealicords.repository.StaffRepository;
import com.anhnc2.ehealicords.service.common.AppUserService;
import com.anhnc2.ehealicords.service.external.MailService;
import com.anhnc2.ehealicords.service.external.StorageService;
import com.anhnc2.ehealicords.service.staff.StaffService;
import com.anhnc2.ehealicords.util.FileUtil;
import lombok.AllArgsConstructor;
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
    private final SpecialistRepository specialistRepository;

    @Override
    @Transactional
    public SpecialistInfoResponse createSpecialist(SpecialistCreationRequest specialist) {
        StaffEntity createdStaff = staffService.createStaffForSpecialist(specialist);

        SpecialistEntity createdSpecialist = createSpecialistProfile(specialist, createdStaff);

        // sendSuccessEmailToSpecialist(createdStaff, createdSpecialist);
        // notifyToDoctorOverEmail(request, password);

        return new SpecialistInfoResponse(createdSpecialist);
    }

    private SpecialistEntity createSpecialistProfile(SpecialistCreationRequest specialist, StaffEntity createdStaff) {
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
                        .branchId(createdStaff.getBranchEntity().getId())
                        .updatedTime(System.currentTimeMillis())
                        .build();

        newSpecialist = specialistRepository.saveAndFlush(newSpecialist);

        String avatarKey = specialist.getAvatarKey();
        MultipartFile avatar = specialist.getAvatar();

        String newAvatarKey;

        if (avatarKey != null && !avatarKey.equals("null") && !avatarKey.trim().isEmpty()) {
            newAvatarKey = avatarKey;
        } else if (avatar != null) {
            newAvatarKey = saveAvatarFile(avatar);
        } else {
            newAvatarKey = null;
        }

        newSpecialist.setAvatarKey(newAvatarKey);
        newSpecialist.setUpdatedTime(System.currentTimeMillis());
        return specialistRepository.saveAndFlush(newSpecialist);
    }

    @Override
    public List<SpecialistInfoResponse> getAllSpecialistsOfBranch() {
        Integer branchId = staffService.getCurrentStaff().getBranchEntity().getId();

        List<SpecialistEntity> specialists = specialistRepository.findAllSpecialistOfBranch(branchId);

        return specialists.stream().map(SpecialistInfoResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<SpecialistInfoResponse> getAllSpecialistsOfSpecialty(Integer specialtyId) {
        Integer branchId = staffService.getCurrentStaff().getBranchEntity().getId();

        List<SpecialistEntity> specialists =
                specialistRepository.findAlByMedialSpecialtyIdAndBranchId(specialtyId, branchId);

        return specialists.stream().map(SpecialistInfoResponse::new).collect(Collectors.toList());
    }

    @Override
    public SpecialistDetailsResponse getSpecialist(Long id) {
        SpecialistEntity specialist =
                specialistRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(StatusCode.SPECIALIST_DOES_NOT_EXISTS));

        return new SpecialistDetailsResponse(specialist);
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
        // specialist.setSpecialistType(specialistUpdateRequest.getSpecialistType());
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
    public PresignResult getPresignUrl(String fileName) {
        String key = String.join("/", SPECIALIST_KEY_PREFIX, AVATAR, fileName);

        return PresignResult.builder()
                .key(key)
                .presignUrl(storageService.preSign(key))
                .build();
    }

    @Override
    public PresignResult getPresignUrl(String fileName, String fileType) {
        String key = String.join("/", SPECIALIST_KEY_PREFIX, AVATAR, fileName);

        return PresignResult.builder()
                .key(key)
                .presignUrl(storageService.preSignWithType(key, fileType))
                .build();
    }

    @Override
    public void updateAvatar(String newKey) {
        SpecialistEntity specialist
                = specialistRepository.findByStaffId(userService.getCurrentUserId());

        String oldKey = specialist.getAvatarKey();

        specialist.setAvatarKey(newKey);
        specialistRepository.save(specialist);

        if (oldKey != null) {
            // storageService.delete(oldKey);
        }
    }

    @Override
    public String updateAvatar(Long specialistId, MultipartFile avatar) {
        SpecialistEntity specialist = specialistRepository.getById(specialistId);

        String oldKey = specialist.getAvatarKey();

        String newKey = avatar == null ? null : saveAvatarFile(avatar);

        specialist.setAvatarKey(newKey);
        specialistRepository.save(specialist);

        if (oldKey != null) {
            // storageService.delete(oldKey);
        }

        return newKey == null ? null : newKey.substring(newKey.lastIndexOf("/") + 1);
    }

    private String saveAvatarFile(MultipartFile avatar) {
        String avatarFileName = avatar.getOriginalFilename();
        String avatarKey =
                String.join(
                        "/",
                        SPECIALIST_KEY_PREFIX,
                        AVATAR,
                        FileUtil.appendCurrentTimeMillisToName(
                                avatarFileName == null ? AVATAR : avatarFileName
                        )
                );

        try {
            storageService.put(avatarKey, FileUtil.convertMultipartFileToFile(avatar));
            return avatarKey;
        } catch (IOException e) {
            throw new RegisterException(StatusCode.FILE_SAVED_FAIL);
        }
    }

    @Override
    public void deleteSpecialist(Long specialistId) {
        specialistRepository.deleteById(specialistId);
        // DELETE staff roles
        // DELETE staff
        // DELETE avatar
        // DELETE specialist
    }

    @Override
    public void changeSpecialistPassword(PasswordUpdateRequest request) {
        staffService.updateStaffPassword(request);
    }

    @Override
    public SpecialistEntity getCurrentSpecialist() {
        return specialistRepository.findByStaffId(staffService.getCurrentStaff().getId());
    }

//    @Override
//    public PaginationResponse<List<SpecialistResponse>> getAllSpecialistsOfBranch(Integer branchId, Integer page, Integer pageSize) {
//        PageRequest pageRequest = PageRequest.of(page, pageSize);
//        Page<SpecialistEntity> doctors = specialistRepository.findAllByBranchId(branchId, pageRequest);
//        List<SpecialistResponse> result =
//                doctors.getContent().stream()
//                        .map(
//                                specialist ->
//                                        SpecialistResponse.builder()
//                                                .id(specialist.getId())
//                                                .fullName(specialist.getFullName())
//                                                .specialty(specialist.getMedialSpecialtyEntity().getName())
//                                                .build())
//                        .collect(Collectors.toList());
//
//        return PaginationResponse.<List<SpecialistResponse>>builder()
//                .page(page)
//                .pageSize(pageSize)
//                .total(doctors.getTotalElements())
//                .totalPage(doctors.getTotalPages())
//                .items(result)
//                .build();
//    }
//
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
}
