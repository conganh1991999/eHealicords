package com.anhnc2.ehealicords.service.staff;

import com.anhnc2.ehealicords.constant.RoleType;
import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.constant.UserStatus;
import com.anhnc2.ehealicords.data.auth.AuthUser;
import com.anhnc2.ehealicords.data.entity.BranchEntity;
import com.anhnc2.ehealicords.data.entity.RoleEntity;
import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistInfoRequest;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.exception.WaitingChangePasswordException;
import com.anhnc2.ehealicords.exception.AuthException;
import com.anhnc2.ehealicords.repository.RoleRepository;
import com.anhnc2.ehealicords.repository.StaffRepository;
import com.anhnc2.ehealicords.service.clinic.BranchService;
import com.anhnc2.ehealicords.service.common.JwtService;
import com.anhnc2.ehealicords.service.staff.StaffService;
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
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final BranchService branchService;

    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    @Override
    public String checkPassword(String email, String password) {
        AuthUser authUser =
                staffRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AuthException(StatusCode.AUTHENTICATION_FAILED, null));

        if(authUser.getStatus() == UserStatus.DISABLED){
            throw new AuthException(StatusCode.AUTHENTICATION_FAILED, null);
        }

        if (passwordEncoder.matches(password, authUser.getPassword())) {

            if(authUser.getStatus() == UserStatus.WAITING_CHANGE_PASSWORD){
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
}
