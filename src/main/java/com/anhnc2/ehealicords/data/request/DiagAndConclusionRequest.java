package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.DiagAndConclusionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiagAndConclusionRequest {
    private Long id;
    private Long patientId;
    private Long historyId;
    private String mainDisease;
    private String additionalDisease;
    private String diseasePrognosis;
    private String solution;
    private String diseaseConclusion;
    private String consultation;
    private String briefFileUrl;

    public DiagAndConclusionEntity toEntity() {
        return DiagAndConclusionEntity.builder()
                .patientId(patientId)
                .historyId(historyId)
                .mainDisease(mainDisease)
                .additionalDisease(additionalDisease)
                .diseasePrognosis(diseasePrognosis)
                .solution(solution)
                .diseaseConclusion(diseaseConclusion)
                .consultation(consultation)
                .briefFileUrl(briefFileUrl)
                .build();
    }
}
