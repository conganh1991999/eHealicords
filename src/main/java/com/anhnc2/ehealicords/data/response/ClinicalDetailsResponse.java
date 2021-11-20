package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.ClinicalEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClinicalDetailsResponse {
    private Long id;
    private Long patientId;
    private Long historyId;
    private String reason;
    private String pathologicalProgress;
    private Double pulse;
    private Double temperature;
    private String bp;
    private Double breathing;
    private Double height;
    private Double weight;
    private String fullBodyExamination;
    private String circulatorySystem;
    private String respiratorySystem;
    private String digestiveSystem;
    private String genitourinarySystem;
    private String nerveSystem;
    private String musculoskeletalSystem;
    private String entSystem;
    private String maxillofacialSystem;
    private String eye;
    private String nutritionalAndEndocrinologyEtc;
    private String briefFileUrl;

    public ClinicalDetailsResponse(ClinicalEntity entity) {
        this.id = entity.getId();
        this.patientId = entity.getPatientId();
        this.historyId = entity.getHistoryId();
        this.reason = entity.getReason();
        this.pathologicalProgress = entity.getPathologicalProgress();
        this.pulse = entity.getPulse();
        this.temperature = entity.getTemperature();
        this.bp = entity.getBp();
        this.breathing = entity.getBreathing();
        this.height = entity.getHeight();
        this.weight = entity.getWeight();
        this.fullBodyExamination = entity.getFullBodyExamination();
        this.circulatorySystem = entity.getCirculatorySystem();
        this.respiratorySystem = entity.getRespiratorySystem();
        this.digestiveSystem = entity.getDigestiveSystem();
        this.genitourinarySystem = entity.getGenitourinarySystem();
        this.nerveSystem = entity.getNerveSystem();
        this.musculoskeletalSystem = entity.getMusculoskeletalSystem();
        this.entSystem = entity.getEntSystem();
        this.maxillofacialSystem = entity.getMaxillofacialSystem();
        this.eye = entity.getEye();
        this.nutritionalAndEndocrinologyEtc = entity.getNutritionalAndEndocrinologyEtc();
        this.briefFileUrl = entity.getBriefFileUrl();
    }
}
