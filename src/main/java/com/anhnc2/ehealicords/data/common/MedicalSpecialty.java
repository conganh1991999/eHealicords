package com.anhnc2.ehealicords.data.common;

import com.anhnc2.ehealicords.data.entity.MedicalSpecialtyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicalSpecialty {
    private Integer id;
    private String name;
    private String description;

    public MedicalSpecialty(MedicalSpecialtyEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }

    public MedicalSpecialtyEntity toEntity() {
        return MedicalSpecialtyEntity.builder()
                .name(this.name)
                .description(this.description)
                .build();
    }
}
