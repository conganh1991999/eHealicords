package com.anhnc2.ehealicords.data.common;

import com.anhnc2.ehealicords.data.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Room {
    private Integer id;
    private String name;
    private String description;
    private Integer roomTypeId;
    private String roomTypeName;
    private Integer branchId;

    public Room(RoomEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.roomTypeId = entity.getRoomTypeId();
        this.roomTypeName = entity.getRoomTypeEntity().getName();
        this.branchId = entity.getBranchId();
    }

    public RoomEntity toEntity() {
        return RoomEntity.builder()
                .name(this.name)
                .description(this.description)
                .roomTypeId(this.roomTypeId)
                .branchId(this.branchId)
                .build();
    }
}
