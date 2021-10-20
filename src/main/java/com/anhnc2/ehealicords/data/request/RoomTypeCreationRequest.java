package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.RoomTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomTypeCreationRequest {
    private String name;
    private String description;

    public RoomTypeEntity toEntity() {
        return RoomTypeEntity.builder()
                .name(this.name)
                .description(this.description)
                .build();
    }
}
