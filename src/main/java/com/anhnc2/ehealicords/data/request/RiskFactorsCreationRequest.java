package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.RiskFactorsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiskFactorsCreationRequest {
    private Long patientId;
    private Boolean smoke;
    private Boolean drink;
    private Boolean drug;
    private Boolean exercise;
    private String exposureFactors;
    private String occupationalHazards;
    private String othersHazards;

    public RiskFactorsEntity toEntity() {
        return RiskFactorsEntity.builder()
                .patientId(patientId)
                .smoke(smoke)
                .drink(drink)
                .drug(drug)
                .exercise(exercise)
                .exposureFactors(exposureFactors)
                .occupationalHazards(occupationalHazards)
                .othersHazards(othersHazards)
                .build();
    }
}
