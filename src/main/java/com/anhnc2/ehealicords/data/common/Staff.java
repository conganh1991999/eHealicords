package com.anhnc2.ehealicords.data.common;

import com.anhnc2.ehealicords.data.entity.StaffEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Staff {
    private String name;

    public static Staff fromDAO(StaffEntity staffEntity) {
        return Staff.builder().name(staffEntity.getFullName()).build();
    }
}
