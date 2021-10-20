package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.RoomTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomTypeResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer branchId;

    public RoomTypeResponse(RoomTypeEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.branchId = entity.getBranchId();
    }
}
