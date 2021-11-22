package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.RecordTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordTypeResponse {
    private Long id;
    private String name;
    private String description;

    public RecordTypeResponse(RecordTypeEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }
}
