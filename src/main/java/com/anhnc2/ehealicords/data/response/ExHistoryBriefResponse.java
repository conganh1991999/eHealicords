package com.anhnc2.ehealicords.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExHistoryBriefResponse {
    private String pathologicalProgress;//
    private String subclinicalBriefs;
    private String mainDisease;//
    private String additionalDisease;//
    private String solution;//
    private String diseaseConclusion;//
    private String consultation;//

    private Double pulse;//
    private Double temperature;//
    private String bp;//
    private Double breathing;//
    private Double weight;//

    private Integer noXq;
    private Integer noCt;
    private Integer noSa;
    private Integer noXn;
    private Integer noOt;
    private Integer noPres;
    private Integer total;

    private LocalDate startDate;//
    private LocalDate endDate;//

    private String patientName;//
    private String branchName;//
    private String exDoctorName;//
    private String reDoctorName;//
    private String recordType;//
    private String status;//
    private String briefFileUrl;//
}
