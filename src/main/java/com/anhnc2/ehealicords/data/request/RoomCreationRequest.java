package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomCreationRequest {
    private String name;
    private String description;
    private Integer roomTypeId;

    public RoomEntity toEntity() {
        return RoomEntity.builder()
                .name(this.name)
                .description(this.description)
                .roomTypeId(this.roomTypeId)
                .build();
    }
}
