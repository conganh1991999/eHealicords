package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.RecordTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordTypeCreationRequest {
    private String name;
    private String description;

    public RecordTypeEntity toEntity() {
        return RecordTypeEntity.builder()
                .name(name)
                .description(description)
                .build();
    }
}
