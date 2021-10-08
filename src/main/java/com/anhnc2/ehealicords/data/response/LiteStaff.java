package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LiteStaff {
    private Long id;
    private String name;

    public static LiteStaff fromDAO(SpecialistEntity staff) {
        return LiteStaff.builder().id(staff.getId()).name(staff.getFullName()).build();
    }
}
