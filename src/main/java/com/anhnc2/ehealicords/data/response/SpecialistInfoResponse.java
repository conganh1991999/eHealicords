package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.constant.AcademicRank;
import com.anhnc2.ehealicords.constant.Gender;
import com.anhnc2.ehealicords.constant.SpecialistDegree;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecialistInfoResponse {
    private Long specialistId;
    private Long staffId;
    private Integer medicalSpecialtyId;
    private Integer branchId;

    private String fullName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private AcademicRank academicRank;
    private SpecialistDegree degreeOfSpecialist;

    private String avatarName;

    public SpecialistInfoResponse(StaffEntity staffEntity, SpecialistEntity specialistEntity) {
        this.specialistId = specialistEntity.getId();
        this.staffId = staffEntity.getId();
        this.medicalSpecialtyId = specialistEntity.getMedialSpecialtyId();
        this.branchId = specialistEntity.getBranchId();
        this.fullName = specialistEntity.getFullName();
        this.email = staffEntity.getEmail();
        this.phoneNumber = specialistEntity.getPhoneNumber();
        this.gender = specialistEntity.getGender();
        this.academicRank = specialistEntity.getAcademicRank();
        this.degreeOfSpecialist = specialistEntity.getDegreeOfSpecialist();
        this.avatarName = resolveAvatarKey(specialistEntity.getAvatarKey());
    }

    private String resolveAvatarKey(String avatarKey) {
        return avatarKey.substring(avatarKey.lastIndexOf("/") + 1);
    }

}
