package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.PrescriptionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PrescriptionResponse {
    private Long id;
    private Long patientId;
    private Long historyId;
    private Long updatedSpecialistId;
    private Long performedSpecialistId;
    private Long suppliedSpecialistId;
    private String prescriptionStatus;
    private String content;
    private String briefFileUrl;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    private String updatedSpecialistName;
    private String performedSpecialistName;
    private String suppliedSpecialistName;

    public PrescriptionResponse(PrescriptionEntity entity) {
        this.id = entity.getId();
        this.patientId = entity.getPatientId();
        this.historyId = entity.getHistoryId();
        this.updatedSpecialistId = entity.getUpdatedSpecialistId();
        this.performedSpecialistId = entity.getPerformedSpecialistId();
        this.suppliedSpecialistId = entity.getSuppliedSpecialistId();
        this.prescriptionStatus = entity.getPrescriptionStatus();
        this.content = entity.getContent();
        this.briefFileUrl = entity.getBriefFileUrl();
    }
}
