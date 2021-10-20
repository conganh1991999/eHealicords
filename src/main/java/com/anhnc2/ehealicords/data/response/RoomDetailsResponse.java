package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.RoomEntity;
import com.anhnc2.ehealicords.data.entity.RoomTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDetailsResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer branchId;
    private RoomTypeEntity roomTypeEntity;

    public RoomDetailsResponse(RoomEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.branchId = entity.getBranchId();
        this.roomTypeEntity = entity.getRoomTypeEntity().toBuilder().build();
    }
}
