package com.anhnc2.ehealicords.data.common;

import com.anhnc2.ehealicords.data.entity.RoomTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomType {
    private Integer id;
    private String name;
    private String description;
    private Integer branchId;

    public RoomType(RoomTypeEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.branchId = entity.getBranchId();
    }

    public RoomTypeEntity toEntity() {
        return RoomTypeEntity.builder()
                .name(this.name)
                .description(this.description)
                .branchId(this.branchId)
                .build();
    }
}
