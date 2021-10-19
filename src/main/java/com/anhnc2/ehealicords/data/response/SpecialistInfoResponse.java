package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.constant.AcademicRank;
import com.anhnc2.ehealicords.constant.Degree;
import com.anhnc2.ehealicords.constant.Gender;
import com.anhnc2.ehealicords.constant.SpecialistDegree;
import com.anhnc2.ehealicords.constant.SpecialistType;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecialistInfoResponse {
    private Long specialistId;

    private String email;
    private String fullName;
    private String phoneNumber;

    private String avatarName;

    private LocalDate dateOfBirth;
    private LocalDate dateOfStartingWork;

    private Gender gender;

    private SpecialistType type;
    private AcademicRank academicRank;
    private Degree degree;
    private SpecialistDegree degreeOfSpecialist;

    private String medicalSpecialtyName;
    private String roomName;

    public SpecialistInfoResponse(SpecialistEntity specialistEntity) {
        this.specialistId = specialistEntity.getId();
        this.email = specialistEntity.getEmail();
        this.fullName = specialistEntity.getFullName();
        this.phoneNumber = specialistEntity.getPhoneNumber();
        this.avatarName = resolveAvatarKey(specialistEntity.getAvatarKey());
        this.dateOfBirth = specialistEntity.getDateOfBirth();
        this.dateOfStartingWork = specialistEntity.getDateOfStartingWork();
        this.gender = specialistEntity.getGender();
        this.type = specialistEntity.getSpecialistType();
        this.academicRank = specialistEntity.getAcademicRank();
        this.degree = specialistEntity.getDegree();
        this.degreeOfSpecialist = specialistEntity.getDegreeOfSpecialist();
        // this.medicalSpecialtyName = specialistEntity.getMedialSpecialtyEntity().getName();
        // this.roomName = specialistEntity.getRoomEntity().getName();
    }

    private String resolveAvatarKey(String avatarKey) {
        return avatarKey == null ? null : avatarKey.substring(avatarKey.lastIndexOf("/") + 1);
    }

}
