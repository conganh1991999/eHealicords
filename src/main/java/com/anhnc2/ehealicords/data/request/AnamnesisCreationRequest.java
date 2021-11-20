package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.AnamnesisEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnamnesisCreationRequest {
    private Long patientId;
    private String anamnesisType;
    private String name;
    private String who;
    private String description;

    public AnamnesisEntity toEntity() {
        return AnamnesisEntity.builder()
                .patientId(patientId)
                .anamnesisType(anamnesisType)
                .name(name)
                .who(who)
                .description(description)
                .build();
    }
}
