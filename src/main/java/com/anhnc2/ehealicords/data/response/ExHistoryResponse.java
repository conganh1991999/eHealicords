package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.ExHistoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExHistoryResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String recordType;
    private String status;
    private Long patientId;
    private Integer branchId;
    private Long exDoctorId;
    private Long reDoctorId;
    private String briefFileUrl;

    private String branchName;
    private String exDoctorName;
    private String reDoctorName;

    public ExHistoryResponse(ExHistoryEntity entity) {
        this.id = entity.getId();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.recordType = entity.getRecordType();
        this.status = entity.getStatus();
        this.patientId = entity.getPatientId();
        this.branchId = entity.getBranchId();
        this.exDoctorId = entity.getExDoctorId();
        this.reDoctorId = entity.getReDoctorId();
        this.briefFileUrl = entity.getBriefFileUrl();
    }
}
