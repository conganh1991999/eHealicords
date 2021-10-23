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
public class PatientResponse {
    private Long id;
    private Long personalHealthId;

    private Integer branchId;
    private Long specialistId;

    private Long createdTime;
    private Long updatedTime;

    private Long identityCardNumber;
    private String gender;
    private String healthInsuranceCardNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private ProvinceEntity birthProvince;
    private String hometown;
    private String phoneNumber;
    private String relativeName;
    private String relativePhoneNumber;
    private Long relativeIdentityCardNumber;
    private String relative;
    private ProvinceEntity tempProvince;
    private DistrictEntity tempDistrict;
    private WardEntity tempWard;

    public PatientResponse(PatientEntity entity) {
        this.id = entity.getId();
        this.personalHealthId = entity.getPersonalHealthId();
        this.branchId = entity.getBranchId();
        this.specialistId = entity.getSpecialistId();
        this.createdTime = entity.getCreatedTime();
        this.updatedTime = entity.getUpdatedTime();
        this.identityCardNumber = entity.getIdentityCardNumber();
        this.gender = entity.getGender();
        this.healthInsuranceCardNumber = entity.getHealthInsuranceCardNumber();
        this.fullName = entity.getFullName();
        this.dateOfBirth = entity.getDateOfBirth();
        this.placeOfBirth = entity.getPlaceOfBirth();
        this.birthProvince = entity.getBirthProvinceEntity();
        this.hometown = entity.getHometown();
        this.phoneNumber = entity.getPhoneNumber();
        this.relativeName = entity.getRelativeName();
        this.relativePhoneNumber = entity.getRelativePhoneNumber();
        this.relativeIdentityCardNumber = entity.getRelativeIdentityCardNumber();
        this.relative = entity.getRelative();
        this.tempProvince = entity.getTempProvinceEntity();
        this.tempDistrict = entity.getTempDistrictEntity();
        this.tempWard = entity.getTempWardEntity();
    }
}
