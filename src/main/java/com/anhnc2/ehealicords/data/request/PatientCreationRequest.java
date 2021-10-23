package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.data.entity.PatientEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientCreationRequest {
    private Long identityCardNumber;
    private String gender;
    private String healthInsuranceCardNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private Integer birthProvinceId;
    private String hometown;
    private String phoneNumber;
    private String relativeName;
    private String relativePhoneNumber;
    private Long relativeIdentityCardNumber;
    private String relative;
    private Integer tempProvinceId;
    private Integer tempDistrictId;
    private Integer tempWardId;

    public PatientEntity toEntity() {
        return PatientEntity.builder()
                .identityCardNumber(this.identityCardNumber)
                .gender(this.gender)
                .healthInsuranceCardNumber(this.healthInsuranceCardNumber)
                .fullName(this.fullName)
                .dateOfBirth(this.dateOfBirth)
                .placeOfBirth(this.placeOfBirth)
                .birthProvinceId(this.birthProvinceId)
                .hometown(this.hometown)
                .phoneNumber(this.phoneNumber)
                .relativeName(this.relativeName)
                .relativePhoneNumber(this.relativePhoneNumber)
                .relativeIdentityCardNumber(this.relativeIdentityCardNumber)
                .relative(this.relative)
                .tempProvinceId(this.tempProvinceId)
                .tempDistrictId(this.tempDistrictId)
                .tempWardId(this.tempWardId)
                .build();
    }
}
