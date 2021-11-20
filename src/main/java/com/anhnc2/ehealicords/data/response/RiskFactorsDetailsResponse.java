package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.RiskFactorsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RiskFactorsDetailsResponse {
    private Long id;
    private Long patientId;
    private Long historyId;
    private Long updatedDoctorId;
    private Boolean smoke;
    private Boolean drink;
    private Boolean drug;
    private Boolean exercise;
    private String exposureFactors;
    private String occupationalHazards;
    private String othersHazards;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    private String updatedDoctorName;

    public RiskFactorsDetailsResponse(RiskFactorsEntity entity) {
        this.id = entity.getId();
        this.patientId = entity.getPatientId();
        this.historyId = entity.getHistoryId();
        this.updatedDoctorId = entity.getUpdatedDoctorId();
        this.smoke = entity.getSmoke();
        this.drink = entity.getDrink();
        this.drug = entity.getDrug();
        this.exercise = entity.getExercise();
        this.exposureFactors = entity.getExposureFactors();
        this.occupationalHazards = entity.getOccupationalHazards();
        this.othersHazards = entity.getOthersHazards();
    }
}
