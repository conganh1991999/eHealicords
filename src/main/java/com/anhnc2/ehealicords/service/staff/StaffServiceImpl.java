package com.anhnc2.ehealicords.service.staff;

import com.anhnc2.ehealicords.constant.RoleType;
import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.constant.UserStatus;
import com.anhnc2.ehealicords.data.auth.AuthUser;
import com.anhnc2.ehealicords.data.entity.BranchEntity;
import com.anhnc2.ehealicords.data.entity.RoleEntity;
import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.ForceChangePasswordRequest;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SaveSubAdminRequest;
import com.anhnc2.ehealicords.data.request.SpecialistCreationRequest;
import com.anhnc2.ehealicords.data.request.StaffCreationRequest;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.exception.AuthException;
import com.anhnc2.ehealicords.exception.PasswordNotMatchException;
import com.anhnc2.ehealicords.exception.TheSameOldPasswordException;
import com.anhnc2.ehealicords.exception.WaitingChangePasswordException;
import com.anhnc2.ehealicords.repository.BranchRepository;
import com.anhnc2.ehealicords.repository.RoleRepository;
import com.anhnc2.ehealicords.repository.StaffRepository;
import com.anhnc2.ehealicords.service.common.AppUserService;
import com.anhnc2.ehealicords.service.common.JwtService;
import com.anhnc2.ehealicords.service.external.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Log4j2
@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final AppUserService appUserService;

    private final JwtService jwtService;

    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    @Override
    public String checkPassword(String email, String password) {
        AuthUser authUser = staffRepository
                .findByEmail(email)
                .orElseThrow(() -> new AuthException(StatusCode.AUTHENTICATION_FAILED, null));

        if (authUser.getStatus() == UserStatus.DISABLED) {
            throw new AuthException(StatusCode.AUTHENTICATION_FAILED, null);
        }

        if (passwordEncoder.matches(password, authUser.getPassword())) {
            if (authUser.getStatus() == UserStatus.WAITING_CHANGE_PASSWORD) {
                throw new WaitingChangePasswordException();
            }
            return jwtService.generate(authUser);
        }

        throw new AuthException(StatusCode.AUTHENTICATION_FAILED, null);
    }

    @Override
    public void forceChangePassword(ForceChangePasswordRequest request) {
        StaffEntity staff =
                staffRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() -> new AppException(StatusCode.STAFF_DOES_NOT_EXISTS));

        PasswordUpdateRequest updateRequest =
                PasswordUpdateRequest.builder()
                        .newPassword(request.getNewPassword())
                        .oldPassword(request.getOldPassword())
                        .build();

        updatePassword(updateRequest, staff);
    }

    @Override
    public void updateStaffPassword(PasswordUpdateRequest request) {
        StaffEntity staff = getCurrentStaff();
        updatePassword(request, staff);
    }

    private void updatePassword(PasswordUpdateRequest updateRequest, StaffEntity staff) {
        if (!passwordEncoder.matches(updateRequest.getOldPassword(), staff.getPassword())) {
            throw new PasswordNotMatchException();
        }

        if (passwordEncoder.matches(updateRequest.getNewPassword(), staff.getPassword())) {
            throw new TheSameOldPasswordException();
        }

        staff.setPassword(passwordEncoder.encode(updateRequest.getNewPassword()));
        staff.setStatus(UserStatus.ACTIVE);
        staffRepository.saveAndFlush(staff);
    }

    @Override
    public void resetPassword(StaffEntity staff) {
        String password = "random"; // PasswordGenerator.random();
        String encodedPassword = passwordEncoder.encode(password);
        staff.setPassword(encodedPassword);
        staff.setStatus(UserStatus.WAITING_CHANGE_PASSWORD);

        staffRepository.saveAndFlush(staff);
        // notifyResetPasswordToDoctorOverEmail(staff.getEmail(), staff.getFullName(), password);
    }

    @Override
    public StaffEntity createStaffForSubAdmin(SaveSubAdminRequest subAdminRequest) {
        Set<RoleEntity> roleEntities =
                roleRepository.findAllIn(Collections.singletonList(RoleType.ROLE_SUB_ADMIN.name()));

        return createStaff(new StaffCreationRequest(subAdminRequest, roleEntities));
    }

    @Override
    public StaffEntity createStaffForSpecialist(SpecialistCreationRequest specialistRequest) {
        Set<RoleEntity> roleEntities =
                roleRepository.findAllIn(Collections.singletonList(RoleType.ROLE_DOCTOR.name()));

        return createStaff(new StaffCreationRequest(specialistRequest, roleEntities));
    }

    private StaffEntity createStaff(StaffCreationRequest request) {
        String password = "random"; // PasswordGenerator.random();
        String encodedPassword = passwordEncoder.encode(password);

        BranchEntity branchEntity =
                request.getBranchId() != null
                        ? branchRepository.getById(request.getBranchId())
                        : getCurrentStaff().getBranchEntity();


        StaffEntity newStaff =
                StaffEntity.builder()
                        .email(request.getEmail())
                        .fullName(request.getFullName())
                        .roleEntities(request.getRoleEntities())
                        .password(encodedPassword)
                        .status(UserStatus.WAITING_CHANGE_PASSWORD)
                        .branchEntity(branchEntity)
                        .build();

        return staffRepository.saveAndFlush(newStaff);
    }

    @Override
    public StaffEntity getCurrentStaff() {
        return staffRepository
                .findById(appUserService.getCurrentUserId())
                .orElseThrow(() -> new AppException(StatusCode.STAFF_DOES_NOT_EXISTS));
    }

//    @Override
//    public void resetPasswordByEmail(String email) {
//        StaffEntity staff = staffRepository.findByEmail(email).get();
//        String password = PasswordGenerator.random();
//        String encodedPassword = passwordEncoder.encode(password);
//        staff.setPassword(encodedPassword);
//        staff.setStatus(UserStatus.WAITING_CHANGE_PASSWORD);
//
//        staffRepository.saveAndFlush(staff);
//        notifyResetPasswordToDoctorOverEmail(staff.getEmail(), staff.getFullName(), password);
//    }
//
//    @Override
//    public void updateStaff(Long staffId, String fullName, Integer branchId) {
//        StaffEntity currentStaff = getStaffById(staffId);
//        BranchEntity branch = branchRepository
//                .findById(branchId)
//                .orElseThrow(() -> new BranchException(StatusCode.BRANCH_DOES_NOT_EXISTED));
//
//        currentStaff.setFullName(fullName);
//        currentStaff.setBranchEntity(branch);
//
//        staffRepository.save(currentStaff);
//    }
//
//    @Override
//    public void deactivate(long staffId) {
//        StaffEntity staff = staffRepository.findById(staffId).get();
//
//        List<RoleType> roles = staff.getRoleEntities().stream()
//                .map(RoleEntity::getType)
//                .collect(Collectors.toList());
//
//        if (roles.contains(RoleType.ROLE_SUB_ADMIN)) {
//            staff.setStatus(UserStatus.DISABLED);
//            staffRepository.saveAndFlush(staff);
//            notifyDeactivateToSubAdminOverEmail(staff.getEmail(), staff.getFullName());
//        }
//    }
//
//    @Override
//    public void activate(long staffId) {
//        StaffEntity staff = staffRepository.findById(staffId).get();
//
//        List<RoleType> roles = staff.getRoleEntities().stream()
//                .map(RoleEntity::getType)
//                .collect(Collectors.toList());
//
//        if (roles.contains(RoleType.ROLE_SUB_ADMIN)) {
//            String password = PasswordGenerator.random();
//            staff.setStatus(UserStatus.WAITING_CHANGE_PASSWORD);
//            staff.setPassword(passwordEncoder.encode(password));
//
//            staffRepository.saveAndFlush(staff);
//
//            notifyActivateToSubAdminOverEmail(staff.getEmail(), staff.getFullName(), password);
//        }
//    }
//
//    @Override
//    public void update(long staffId, SaveSubAdminRequest request) {
//        StaffEntity staff = staffRepository.findById(staffId).get();
//
//        List<RoleType> roles = staff.getRoleEntities().stream()
//                .map(RoleEntity::getType)
//                .collect(Collectors.toList());
//
//        if (!roles.contains(RoleType.ROLE_SUB_ADMIN)) {
//            return;
//        }
//
//        staff.setFullName(request.getFullName());
//
//        if(!request.getEmail().equals(staff.getEmail())){
//            String password = PasswordGenerator.random();
//            staff.setStatus(UserStatus.WAITING_CHANGE_PASSWORD);
//            staff.setPassword(passwordEncoder.encode(password));
//
//            createStaffForSubAdmin(request, password);
//        }
//
//        staffRepository.saveAndFlush(staff);
//    }
//
//    private void notifyResetPasswordToDoctorOverEmail(String to, String name, String password) {
//        String subject = "HeyDoctor: Đặt Mật Khẩu Tài Khoản Bác Sĩ Thành Công!";
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("doctorName", name);
//        params.put("email", to);
//        params.put("password", password);
//        mailService.sendEmail(to, subject, "doctor-reset-password", params);
//    }
//
//    private void notifyActivateToSubAdminOverEmail(String to, String fullName, String password) {
//        String subject = "HeyDoctor: Tài Khoản Được Kích Hoạt Trở lại!";
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("adminName", fullName);
//        params.put("email", to);
//        params.put("password", password);
//        mailService.sendEmail(to, subject, "activate-subadmin", params);
//    }
//
//    private void notifyDeactivateToSubAdminOverEmail(String to, String fullName) {
//        String subject = "HeyDoctor: Tài Khoản Bị Vô Hiệu Hóa!";
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("adminName", fullName);
//        mailService.sendEmail(to, subject, "deactivate-subadmin", params);
//    }
//
//    private void notifyResetPasswordToDoctorOverEmail(String to, String name, String password) {
//        String subject = "HeyDoctor: Đặt Mật Khẩu Mới Thành Công!";
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("staffName", name);
//        params.put("email", to);
//        params.put("password", password);
//        mailService.sendEmail(to, subject, "staff-reset-password", params);
//    }
//
//    private void notifyToSubAdminOverEmail(SaveSubAdminRequest request, String password) {
//        String to = request.getEmail();
//        String subject = "HeyDoctor: Tạo Tài Khoản Admin Thành Công!";
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("adminName", request.getFullName());
//        params.put("email", to);
//        params.put("password", password);
//        mailService.sendEmail(to, subject, "create-subadmin", params);
//    }
}
