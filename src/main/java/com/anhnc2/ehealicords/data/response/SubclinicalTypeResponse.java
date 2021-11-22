package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.SubclinicalTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubclinicalTypeResponse {
    private Long id;
    private String name;
    private String description;

    public SubclinicalTypeResponse(SubclinicalTypeEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }
}
