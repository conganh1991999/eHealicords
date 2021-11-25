package com.anhnc2.ehealicords.data.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ExHistoryBriefResponse {
    private String pathologicalProgress;
    private List<String> subclinicalBriefs;
    private String mainDisease;
    private String additionalDisease;
    private String solution;
    private String diseaseConclusion;
    private String consultation;

    private Double pulse;
    private Double temperature;
    private String bp;
    private Double breathing;
    private Double weight;

    List<String> listSubclinical;
    List<Integer> numberOfResultFiles;
    List<Integer> numberOfImages;

    private LocalDate startDate;
    private LocalDate endDate;

    private String patientName;
    private String branchName;
    private String exDoctorName;
    private String reDoctorName;
    private String recordType;
    private String status;
    private String briefFileUrl;
}
