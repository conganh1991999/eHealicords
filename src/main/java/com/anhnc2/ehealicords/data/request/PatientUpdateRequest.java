package com.anhnc2.ehealicords.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientUpdateRequest {
    private Long id; // create
    private Long identityCardNumber; // create
    private Long personalHealthId; // create
    private String gender; // create
    private String healthInsuranceCardNumber; // create
    private String fullName; // create
    private LocalDate dateOfBirth; // create
    private String placeOfBirth; // create
    private String hometown; // create
    private String phoneNumber; // create
    private String identityCardIssuePlace;
    private LocalDate identityCardIssueDate;
    private String temporaryResidenceAddress;
    private String permanentAddress;
    private String nationality;
    private String religion;
    private String folk;
    private String occupation;
    private String academicLevel;
    private String maritalStatus;
    private String relativeName; // create
    private String relativePhoneNumber; // create
    private Long relativeIdentityCardNumber; // create
    private String relative; // create
    private Long createdTime; // create
    private Long updatedTime;

    private Integer branchId; // create
    private Long specialistId; // create

    private Integer birthProvinceId; // create
    private Integer tempProvinceId; // create
    private Integer tempDistrictId; // create
    private Integer tempWardId; // create
    private Integer permProvinceId;
    private Integer permDistrictId;
    private Integer permWardId;
}
