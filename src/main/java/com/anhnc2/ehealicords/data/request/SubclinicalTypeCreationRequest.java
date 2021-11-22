package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.SubclinicalTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubclinicalTypeCreationRequest {
    private String name;
    private String description;

    public SubclinicalTypeEntity toEntity() {
        return SubclinicalTypeEntity.builder()
                .name(name)
                .description(description)
                .build();
    }
}
