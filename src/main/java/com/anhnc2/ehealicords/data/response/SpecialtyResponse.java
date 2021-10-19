package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.MedicalSpecialtyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpecialtyResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer branchId;

    public SpecialtyResponse(MedicalSpecialtyEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.branchId = entity.getBranchId();
    }
}
