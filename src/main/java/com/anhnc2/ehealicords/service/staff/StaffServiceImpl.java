package com.anhnc2.ehealicords.service.staff;

import com.anhnc2.ehealicords.constant.RoleType;
import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.constant.UserStatus;
import com.anhnc2.ehealicords.data.auth.AuthUser;
import com.anhnc2.ehealicords.data.entity.BranchEntity;
import com.anhnc2.ehealicords.data.entity.RoleEntity;
import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.ChangeLoginInfoRequest;
import com.anhnc2.ehealicords.data.request.ForceChangePasswordRequest;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistInfoRequest;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.exception.PasswordNotMatchException;
import com.anhnc2.ehealicords.exception.TheSameOldPasswordException;
import com.anhnc2.ehealicords.exception.WaitingChangePasswordException;
import com.anhnc2.ehealicords.exception.AuthException;
import com.anhnc2.ehealicords.repository.RoleRepository;
import com.anhnc2.ehealicords.repository.StaffRepository;
import com.anhnc2.ehealicords.service.clinic.BranchService;
import com.anhnc2.ehealicords.service.common.JwtService;
import com.anhnc2.ehealicords.service.external.MailService;
import com.anhnc2.ehealicords.service.staff.StaffService;
import com.anhnc2.ehealicords.util.PasswordGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Log4j2
@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final BranchService branchService;

    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    private final MailService mailService;

    @Override
    public String checkPassword(String email, String password) {
        AuthUser authUser
                = staffRepository.findByEmail(email)
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
    public StaffEntity createStaff(SpecialistInfoRequest staff) {
        Set<RoleEntity> roleEntities =
                roleRepository.findAllIn(Collections.singletonList(RoleType.ROLE_DOCTOR.name()));

        BranchEntity branch = branchService.getBranchById(staff.getBranchId());

        String encodedPassword = passwordEncoder.encode(staff.getEmail());

        StaffEntity newStaff =
                StaffEntity.builder()
                        .email(staff.getEmail())
                        .fullName(staff.getFullName())
                        .roleEntities(roleEntities)
                        .password(encodedPassword)
                        .status(UserStatus.ACTIVE)
                        .branchEntity(branch)
                        .build();

        return staffRepository.saveAndFlush(newStaff);
    }

    @Override
    public void updateStaff(Long staffId, String fullName, Integer branchId) {
        StaffEntity currentStaff = getStaffById(staffId);
        BranchEntity branch = branchService.getBranchById(branchId);

        currentStaff.setFullName(fullName);
        currentStaff.setBranchEntity(branch);

        staffRepository.save(currentStaff);
    }

    @Override
    public void updatePassword(Long staffId, PasswordUpdateRequest request) {
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new AppException(StatusCode.PASSWORD_DOES_NOT_MATCH);
        }
        StaffEntity currentStaff = getStaffById(staffId);
        currentStaff.setPassword(passwordEncoder.encode(request.getPassword()));
        staffRepository.save(currentStaff);
    }

    @Override
    public StaffEntity getStaffById(Long id) {
        return staffRepository.findById(id).get();
    }

    @Override
    public void updateLoginInformation(long staffId, ChangeLoginInfoRequest loginInfo) {
        StaffEntity staff = staffRepository.findById(staffId).get();
        changePassword(loginInfo, staff);
    }

    @Override
    public void forceChangePassword(ForceChangePasswordRequest request) {
        StaffEntity staff = staffRepository.findByEmail(request.getEmail()).get();

        ChangeLoginInfoRequest changeLoginInfoRequest = ChangeLoginInfoRequest.builder()
                .password(request.getPassword())
                .oldPassword(request.getOldPassword())
                .build();

        changePassword(changeLoginInfoRequest, staff);
    }

    @Override
    public void resetPasswordByEmail(String email) {
        StaffEntity staff = staffRepository.findByEmail(email).get();
        String password = PasswordGenerator.random();
        String encodedPassword = passwordEncoder.encode(password);
        staff.setPassword(encodedPassword);
        staff.setStatus(UserStatus.WAITING_CHANGE_PASSWORD);

        staffRepository.saveAndFlush(staff);
        notifyResetPasswordToDoctorOverEmail(staff.getEmail(), staff.getFullName(), password);
    }

    private void notifyResetPasswordToDoctorOverEmail(String to, String name, String password) {
        String subject = "HeyDoctor: Đặt Mật Khẩu Mới Thành Công!";
        Map<String, Object> params = new HashMap<>();

        params.put("staffName", name);
        params.put("email", to);
        params.put("password", password);
        mailService.sendEmail(to, subject, "staff-reset-password", params);
    }

    private void changePassword(ChangeLoginInfoRequest loginInfo, StaffEntity staff) {

        if(!passwordEncoder.matches(loginInfo.getOldPassword(), staff.getPassword())){
            throw new PasswordNotMatchException();
        }

        if(passwordEncoder.matches(loginInfo.getPassword(), staff.getPassword())){
            throw new TheSameOldPasswordException();
        }

        staff.setPassword(passwordEncoder.encode(loginInfo.getPassword()));
        staff.setStatus(UserStatus.ACTIVE);
        staffRepository.saveAndFlush(staff);

    }
}
