package com.anhnc2.ehealicords.data.common;

import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Staff {
    private String name;

    public static Staff fromDAO(SpecialistEntity specialistEntity) {
        return Staff.builder().name(specialistEntity.getFullName()).build();
    }
}
