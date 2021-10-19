package com.anhnc2.ehealicords.service.subadmin;

import com.anhnc2.ehealicords.constant.RoleType;
import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.RoleEntity;
import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SaveSubAdminRequest;
import com.anhnc2.ehealicords.data.response.SubAdminResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.repository.RoleRepository;
import com.anhnc2.ehealicords.service.external.MailService;
import com.anhnc2.ehealicords.service.staff.StaffService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class SubAdminServiceImpl implements SubAdminService {

    private final StaffService staffService;

    private final MailService mailService;

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Long createSubAdmin(SaveSubAdminRequest request) {
        StaffEntity staff = staffService.createStaffForSubAdmin(request);

        // notifyToSubAdminOverEmail(request, password);

        return staff.getId();
    }

    @Override
    public List<SubAdminResponse> getAllSubAdmin() {
        RoleEntity roleEntity =
                roleRepository
                        .findByType(RoleType.ROLE_SUB_ADMIN.name())
                        .orElseThrow(() -> new AppException(StatusCode.ROLE_SUB_ADMIN_NOT_EXISTS));

        return roleEntity.getUsers()
                .stream()
                .map(SubAdminResponse::fromStaff)
                .collect(Collectors.toList());
    }

    @Override
    public void changeSubAdminPassword(PasswordUpdateRequest request) {
        staffService.updateStaffPassword(request);
    }

//    @Override
//    public void deactivate(long staffId) {
//        staffService.deactivate(staffId);
//    }
//
//    @Override
//    public void activate(long staffId) {
//        staffService.activate(staffId);
//    }
}
