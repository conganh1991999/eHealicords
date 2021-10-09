package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.constant.UserStatus;
import com.anhnc2.ehealicords.data.entity.StaffEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubAdminResponse {
    Long id;
    String email;
    String fullName;
    UserStatus status;
    Integer branchId;

    public static SubAdminResponse fromStaff(StaffEntity staffEntity){
        return SubAdminResponse.builder()
                .id(staffEntity.getId())
                .email(staffEntity.getEmail())
                .fullName(staffEntity.getFullName())
                .status(staffEntity.getStatus())
                .branchId(staffEntity.getBranchEntity().getId())
                .build();
    }
}
