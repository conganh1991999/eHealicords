package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer roomTypeId;
    private Integer branchId;

    public RoomResponse(RoomEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.roomTypeId = entity.getRoomTypeId();
        this.branchId = entity.getBranchId();
    }
}
