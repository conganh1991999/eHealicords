package com.anhnc2.ehealicords.data.common;

import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Specialist {
    private String name;

    public static Specialist fromDAO(SpecialistEntity specialistEntity) {
        return Specialist.builder().name(specialistEntity.getFullname()).build();
    }
}
