package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.MedicalSpecialtyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpecialtyCreationRequest {
    private String name;
    private String description;

    public MedicalSpecialtyEntity toEntity() {
        return MedicalSpecialtyEntity.builder()
                .name(this.name)
                .description(this.description)
                .build();
    }
}
