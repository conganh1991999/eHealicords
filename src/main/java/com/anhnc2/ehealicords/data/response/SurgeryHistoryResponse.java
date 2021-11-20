package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.SurgeryHistoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SurgeryHistoryResponse {
    private Long id;
    private Long patientId;
    private Long historyId;
    private Long updatedDoctorId;
    private String surgicalSystem;
    private String yearOfSurgery;
    private String description;
    private String whereOfSurgery;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    private String updatedDoctorName;

    public SurgeryHistoryResponse(SurgeryHistoryEntity entity) {
        this.id = entity.getId();
        this.patientId = entity.getPatientId();
        this.historyId = entity.getHistoryId();
        this.updatedDoctorId = entity.getUpdatedDoctorId();
        this.surgicalSystem = entity.getSurgicalSystem();
        this.yearOfSurgery = entity.getYearOfSurgery();
        this.description = entity.getDescription();
        this.whereOfSurgery = entity.getWhereOfSurgery();
    }
}
