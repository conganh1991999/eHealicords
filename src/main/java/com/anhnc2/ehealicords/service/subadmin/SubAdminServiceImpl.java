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
import com.anhnc2.ehealicords.service.staff.StaffService;
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
    private final StaffService staffService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    @Transactional
    public long create(SaveSubAdminRequest request) {
        String password = PasswordGenerator.random();
        StaffEntity staff = staffService.createStaffWithRoleAdminAndSubAdmin(request, password);

        // notifyToSubAdminOverEmail(request, password);

        return staff.getId();
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
        staffService.deactivate(staffId);
    }

    @Override
    public void activate(long staffId) {
        staffService.activate(staffId);
    }


    @Override
    @Transactional
    public void update(long staffId, SaveSubAdminRequest request) {
        staffService.update(staffId, request);
    }

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
