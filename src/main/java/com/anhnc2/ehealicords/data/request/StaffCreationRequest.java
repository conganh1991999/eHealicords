package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.constant.UserStatus;
import com.anhnc2.ehealicords.data.entity.RoleEntity;
import lombok.Data;

import java.util.Set;

@Data
public class StaffCreationRequest {
    private String email;
    private String fullName;
    private Set<RoleEntity> roleEntities;
    private String password;
    private UserStatus status;
    private Integer branchId;

    public StaffCreationRequest(SpecialistCreationRequest specialist, Set<RoleEntity> roleEntities) {
        this.email = specialist.getEmail();
        this.fullName = specialist.getFullName();
        this.roleEntities = roleEntities;
        this.branchId = specialist.getBranchId();
    }

    public StaffCreationRequest(SaveSubAdminRequest subAdmin, Set<RoleEntity> roleEntities) {
        this.email = subAdmin.getEmail();
        this.fullName = subAdmin.getFullName();
        this.roleEntities = roleEntities;
        this.branchId = subAdmin.getBranchId();
    }
}