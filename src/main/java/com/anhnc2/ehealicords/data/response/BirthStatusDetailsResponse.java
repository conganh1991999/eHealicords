package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.BirthStatusEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BirthStatusDetailsResponse {
    private Long id;
    private Long patientId;
    private Long historyId;
    private Long updatedDoctorId;
    private Boolean normalBirth;
    private Boolean hardBirth;
    private Boolean caesareanSection;
    private Boolean bornPrematurely;
    private Boolean suffocatedAtBirth;
    private Double bornWeight;
    private Double bornLength;
    private String birthDefects;
    private String othersProblem;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    private String updatedDoctorName;

    public BirthStatusDetailsResponse(BirthStatusEntity entity) {
        this.id = entity.getId();
        this.patientId = entity.getPatientId();
        this.historyId = entity.getHistoryId();
        this.updatedDoctorId = entity.getUpdatedDoctorId();
        this.normalBirth = entity.getNormalBirth();
        this.hardBirth = entity.getHardBirth();
        this.caesareanSection = entity.getCaesareanSection();
        this.bornPrematurely = entity.getBornPrematurely();
        this.suffocatedAtBirth = entity.getSuffocatedAtBirth();
        this.bornWeight = entity.getBornWeight();
        this.bornLength = entity.getBornLength();
        this.birthDefects = entity.getBirthDefects();
        this.othersProblem = entity.getOthersProblem();
    }
}
