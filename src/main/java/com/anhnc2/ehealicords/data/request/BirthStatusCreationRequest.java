package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.BirthStatusEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BirthStatusCreationRequest {
    private Long patientId;
    private Boolean normalBirth;
    private Boolean hardBirth;
    private Boolean caesareanSection;
    private Boolean bornPrematurely;
    private Boolean suffocatedAtBirth;
    private Double bornWeight;
    private Double bornLength;
    private String birthDefects;
    private String othersProblem;

    public BirthStatusEntity toEntity() {
        return BirthStatusEntity.builder()
                .patientId(patientId)
                .normalBirth(normalBirth)
                .hardBirth(hardBirth)
                .caesareanSection(caesareanSection)
                .bornPrematurely(bornPrematurely)
                .suffocatedAtBirth(suffocatedAtBirth)
                .bornWeight(bornWeight)
                .bornLength(bornLength)
                .birthDefects(birthDefects)
                .othersProblem(othersProblem)
                .build();
    }
}
