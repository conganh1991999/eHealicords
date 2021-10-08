package com.anhnc2.ehealicords.service.subadmin;

import com.anhnc2.ehealicords.constant.RoleType;
import com.anhnc2.ehealicords.constant.UserStatus;
import com.anhnc2.ehealicords.data.entity.RoleEntity;
import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.SaveSubAdminRequest;
import com.anhnc2.ehealicords.data.response.SubAdminResponse;
import com.anhnc2.ehealicords.repository.BranchRepository;
import com.anhnc2.ehealicords.repository.RoleRepository;
import com.anhnc2.ehealicords.repository.StaffRepository;
import com.anhnc2.ehealicords.service.external.MailService;
import com.anhnc2.ehealicords.util.PasswordGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class SubAdminServiceImpl implements SubAdminService {

    private final BranchRepository branchRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    @Transactional
    public long create(SaveSubAdminRequest request) {
        String password = PasswordGenerator.random();
        StaffEntity subAdmin = createStaffWithRoleAdminAndSubAdmin(request, password);

        notifyToSubAdminOverEmail(request, password);

        return subAdmin.getId();
    }

    @Override
    public List<SubAdminResponse> getAll() {
        RoleEntity roleEntities = roleRepository.findByType(RoleType.ROLE_SUB_ADMIN.name())
                .get();

        return roleEntities.getUsers().stream()
                .map(SubAdminResponse::fromStaff)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivate(long staffId) {
        StaffEntity staff = staffRepository.findById(staffId).get();

        List<RoleType> roles = staff.getRoleEntities().stream()
                .map(RoleEntity::getType)
                .collect(Collectors.toList());

        if (roles.contains(RoleType.ROLE_SUB_ADMIN)) {
            staff.setStatus(UserStatus.DISABLED);
            staffRepository.saveAndFlush(staff);
            notifyDeactivateToSubAdminOverEmail(staff.getEmail(), staff.getFullName());
        }
    }

    @Override
    public void activate(long staffId) {
        StaffEntity staff = staffRepository.findById(staffId).get();

        List<RoleType> roles = staff.getRoleEntities().stream()
                .map(RoleEntity::getType)
                .collect(Collectors.toList());

        if (roles.contains(RoleType.ROLE_SUB_ADMIN)) {
            String password = PasswordGenerator.random();
            staff.setStatus(UserStatus.WAITING_CHANGE_PASSWORD);
            staff.setPassword(passwordEncoder.encode(password));

            staffRepository.saveAndFlush(staff);

            notifyActivateToSubAdminOverEmail(staff.getEmail(), staff.getFullName(), password);
        }
    }

    @Override
    @Transactional
    public void update(long staffId, SaveSubAdminRequest request) {
        StaffEntity staff = staffRepository.findById(staffId).get();

        List<RoleType> roles = staff.getRoleEntities().stream()
                .map(RoleEntity::getType)
                .collect(Collectors.toList());

        if (!roles.contains(RoleType.ROLE_SUB_ADMIN)) {
            return;
        }

        staff.setFullName(request.getFullName());

        if(!request.getEmail().equals(staff.getEmail())){
            String password = PasswordGenerator.random();
            staff.setStatus(UserStatus.WAITING_CHANGE_PASSWORD);
            staff.setPassword(passwordEncoder.encode(password));

            createStaffWithRoleAdminAndSubAdmin(request, password);
        }

        staffRepository.saveAndFlush(staff);
    }


    private StaffEntity createStaffWithRoleAdminAndSubAdmin(SaveSubAdminRequest request,
                                                            String password) {
        Set<RoleEntity> roleEntities =
                roleRepository.findAllIn(
                        Collections.singletonList(RoleType.ROLE_SUB_ADMIN.name())
                );

        String encodedPassword = passwordEncoder.encode(password);

        StaffEntity staff =
                StaffEntity.builder()
                        .email(request.getEmail())
                        .fullName(request.getFullName())
                        .roleEntities(roleEntities)
                        .password(encodedPassword)
                        .status(UserStatus.WAITING_CHANGE_PASSWORD)
                        .branchEntity(branchRepository.getById(request.getBranchId()))
                        .build();

        return staffRepository.saveAndFlush(staff);
    }

    private void notifyActivateToSubAdminOverEmail(String to, String fullName, String password) {
        String subject = "HeyDoctor: Tài Khoản Được Kích Hoạt Trở lại!";
        Map<String, Object> params = new HashMap<>();

        params.put("adminName", fullName);
        params.put("email", to);
        params.put("password", password);
        mailService.sendEmail(to, subject, "activate-subadmin", params);
    }

    private void notifyDeactivateToSubAdminOverEmail(String to, String fullName) {
        String subject = "HeyDoctor: Tài Khoản Bị Vô Hiệu Hóa!";
        Map<String, Object> params = new HashMap<>();

        params.put("adminName", fullName);
        mailService.sendEmail(to, subject, "deactivate-subadmin", params);
    }

    private void notifyToSubAdminOverEmail(SaveSubAdminRequest request, String password) {
        String to = request.getEmail();
        String subject = "HeyDoctor: Tạo Tài Khoản Admin Thành Công!";
        Map<String, Object> params = new HashMap<>();

        params.put("adminName", request.getFullName());
        params.put("email", to);
        params.put("password", password);
        mailService.sendEmail(to, subject, "create-subadmin", params);
    }
}
