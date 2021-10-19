package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.constant.AcademicRank;
import com.anhnc2.ehealicords.constant.Degree;
import com.anhnc2.ehealicords.constant.Gender;
import com.anhnc2.ehealicords.constant.SpecialistDegree;
import com.anhnc2.ehealicords.constant.SpecialistType;
import com.anhnc2.ehealicords.data.entity.MedicalSpecialtyEntity;
import com.anhnc2.ehealicords.data.entity.RoomEntity;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SpecialistDetailsResponse {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String avatarKey;
    private LocalDate dateOfBirth;
    private LocalDate dateOfStartingWork;
    private Gender gender;

    private SpecialistType specialistType;
    private AcademicRank academicRank;
    private Degree degree;
    private SpecialistDegree degreeOfSpecialist;

    private MedicalSpecialtyEntity medicalSpecialtyEntity;
    private RoomEntity roomEntity;

    private Long createdTime;
    private Long updatedTime;

    public SpecialistDetailsResponse(SpecialistEntity specialist) {
        this.id = specialist.getId();
        this.email = specialist.getEmail();
        this.fullName = specialist.getFullName();
        this.phoneNumber = specialist.getPhoneNumber();
        this.avatarKey = specialist.getAvatarKey();
        this.dateOfBirth = specialist.getDateOfBirth();
        this.dateOfStartingWork = specialist.getDateOfStartingWork();
        this.gender = specialist.getGender();
        this.specialistType = specialist.getSpecialistType();
        this.academicRank = specialist.getAcademicRank();
        this.degree = specialist.getDegree();
        this.degreeOfSpecialist = specialist.getDegreeOfSpecialist();
        this.medicalSpecialtyEntity = specialist.getMedialSpecialtyEntity().toBuilder().build();
        this.roomEntity = specialist.getRoomEntity().toBuilder().build();
        this.createdTime = specialist.getCreatedTime();
        this.updatedTime = specialist.getUpdatedTime();
    }
}
