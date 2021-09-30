package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LiteSpecialist {
    private Long id;
    private String name;

    public static LiteSpecialist fromDAO(SpecialistEntity specialist) {
        return LiteSpecialist.builder().id(specialist.getId()).name(specialist.getFullName()).build();
    }
}
