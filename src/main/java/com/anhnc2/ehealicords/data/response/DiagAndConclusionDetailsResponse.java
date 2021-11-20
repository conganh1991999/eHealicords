package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.DiagAndConclusionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiagAndConclusionDetailsResponse {
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

    public DiagAndConclusionDetailsResponse(DiagAndConclusionEntity entity) {
        this.id = entity.getId();
        this.patientId = entity.getPatientId();
        this.historyId = entity.getHistoryId();
        this.mainDisease = entity.getMainDisease();
        this.additionalDisease = entity.getAdditionalDisease();
        this.diseasePrognosis = entity.getDiseasePrognosis();
        this.solution = entity.getSolution();
        this.diseaseConclusion = entity.getDiseaseConclusion();
        this.consultation = entity.getConsultation();
        this.briefFileUrl = entity.getBriefFileUrl();
    }
}
