package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.constant.AcademicRank;
import com.anhnc2.ehealicords.constant.Degree;
import com.anhnc2.ehealicords.constant.Gender;
import com.anhnc2.ehealicords.constant.SpecialistDegree;
import com.anhnc2.ehealicords.constant.SpecialistType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
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

    private Integer medicalSpecialtyId;
    private String medicalSpecialtyName;

    private Integer roomId;
    private String roomName;

    private Integer branchId;
    private String branchName;

    private Long createdTime;
    private Long updatedTime;
}
