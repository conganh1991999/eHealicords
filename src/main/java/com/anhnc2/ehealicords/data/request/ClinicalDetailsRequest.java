package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.ClinicalEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClinicalDetailsRequest {
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

    public ClinicalEntity toEntity() {
        return ClinicalEntity.builder()
                .patientId(patientId)
                .historyId(historyId)
                .reason(reason)
                .pathologicalProgress(pathologicalProgress)
                .pulse(pulse)
                .temperature(temperature)
                .bp(bp)
                .breathing(breathing)
                .height(height)
                .weight(weight)
                .fullBodyExamination(fullBodyExamination)
                .circulatorySystem(circulatorySystem)
                .respiratorySystem(respiratorySystem)
                .digestiveSystem(digestiveSystem)
                .genitourinarySystem(genitourinarySystem)
                .nerveSystem(nerveSystem)
                .musculoskeletalSystem(musculoskeletalSystem)
                .entSystem(entSystem)
                .maxillofacialSystem(maxillofacialSystem)
                .eye(eye)
                .nutritionalAndEndocrinologyEtc(nutritionalAndEndocrinologyEtc)
                .briefFileUrl(briefFileUrl)
                .build();
    }
}