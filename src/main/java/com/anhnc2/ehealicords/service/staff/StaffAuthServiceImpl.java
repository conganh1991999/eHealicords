package com.anhnc2.ehealicords.service.staff;

import com.anhnc2.ehealicords.constant.UserStatus;
import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.ChangeLoginInfoRequest;
import com.anhnc2.ehealicords.data.request.ForceChangePasswordRequest;
import com.anhnc2.ehealicords.exception.PasswordNotMatchException;
import com.anhnc2.ehealicords.exception.TheSameOldPasswordException;
import com.anhnc2.ehealicords.repository.StaffRepository;
import com.anhnc2.ehealicords.service.external.MailService;
import com.anhnc2.ehealicords.util.PasswordGenerator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class StaffAuthServiceImpl implements StaffAuthService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

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
