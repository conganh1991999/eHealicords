package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.AnamnesisEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AnamnesisResponse {
    private Long id;
    private Long patientId;
    private Long historyId;
    private Long updatedDoctorId;
    private String anamnesisType;
    private String name;
    private String who;
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    private String updatedDoctorName;

    public AnamnesisResponse(AnamnesisEntity entity) {
        this.id = entity.getId();
        this.patientId = entity.getPatientId();
        this.historyId = entity.getHistoryId();
        this.updatedDoctorId = entity.getUpdatedDoctorId();
        this.anamnesisType = entity.getAnamnesisType();
        this.name = entity.getName();
        this.who = entity.getWho();
        this.description = entity.getDescription();
    }
}
