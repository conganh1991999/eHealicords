package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.DistrictEntity;
import com.anhnc2.ehealicords.data.entity.PatientEntity;
import com.anhnc2.ehealicords.data.entity.ProvinceEntity;
import com.anhnc2.ehealicords.data.entity.WardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientDetailsResponse {
    private Long id;
    private Long identityCardNumber;
    private Long personalHealthId;
    private String gender;
    private String healthInsuranceCardNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private String hometown;
    private String phoneNumber;
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
    private String relativeName;
    private String relativePhoneNumber;
    private Long relativeIdentityCardNumber;
    private String relative;
    private Long createdTime;
    private Long updatedTime;
    private Integer branchId;
    private Long specialistId;
    private ProvinceEntity birthProvince;
    private ProvinceEntity tempProvince;
    private DistrictEntity tempDistrict;
    private WardEntity tempWard;
    private ProvinceEntity permProvince;
    private DistrictEntity permDistrict;
    private WardEntity permWard;

    // private Integer birthProvinceId;
    // private Integer tempProvinceId;
    // private Integer tempDistrictId;
    // private Integer tempWardId;
    // private Integer permProvinceId;
    // private Integer permDistrictId;
    // private Integer permWardId;

    public PatientDetailsResponse(PatientEntity entity) {
        this.id = entity.getId();

        this.identityCardNumber = entity.getIdentityCardNumber();
        this.personalHealthId = entity.getPersonalHealthId();

        this.gender = entity.getGender();

        this.healthInsuranceCardNumber = entity.getHealthInsuranceCardNumber();

        this.fullName = entity.getFullName();

        this.dateOfBirth = entity.getDateOfBirth();
        this.placeOfBirth = entity.getPlaceOfBirth();

        this.hometown = entity.getHometown();

        this.phoneNumber = entity.getPhoneNumber();

        this.identityCardIssuePlace = entity.getIdentityCardIssuePlace();
        this.identityCardIssueDate = entity.getIdentityCardIssueDate();

        this.temporaryResidenceAddress = entity.getTemporaryResidenceAddress();
        this.permanentAddress = entity.getPermanentAddress();

        this.nationality = entity.getNationality();
        this.religion = entity.getReligion();
        this.folk = entity.getFolk();
        this.occupation = entity.getOccupation();

        this.academicLevel = entity.getAcademicLevel();
        this.maritalStatus = entity.getMaritalStatus();

        this.relativeName = entity.getRelativeName();
        this.relativePhoneNumber = entity.getRelativePhoneNumber();
        this.relativeIdentityCardNumber = entity.getRelativeIdentityCardNumber();
        this.relative = entity.getRelative();

        this.createdTime = entity.getCreatedTime();
        this.updatedTime = entity.getUpdatedTime();

        this.branchId = entity.getBranchId();
        this.specialistId = entity.getSpecialistId();

        this.birthProvince = entity.getBirthProvinceEntity().toBuilder().build(); // not nullable
        this.tempProvince = entity.getTempProvinceEntity().toBuilder().build(); // not nullable
        this.tempDistrict = entity.getTempDistrictEntity().toBuilder().build(); // not nullable
        this.tempWard = entity.getTempWardEntity().toBuilder().build(); // not nullable

        ProvinceEntity provinceEntity = entity.getPermProvinceEntity();
        DistrictEntity districtEntity = entity.getPermDistrictEntity();
        WardEntity wardEntity = entity.getPermWardEntity();

        this.permProvince = provinceEntity == null ? null : provinceEntity.toBuilder().build();
        this.permDistrict = districtEntity == null ? null : districtEntity.toBuilder().build();
        this.permWard = wardEntity == null ? null : wardEntity.toBuilder().build();
    }
}
