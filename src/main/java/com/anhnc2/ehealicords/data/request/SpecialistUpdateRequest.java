package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.constant.AcademicRank;
import com.anhnc2.ehealicords.constant.Degree;
import com.anhnc2.ehealicords.constant.Gender;
import com.anhnc2.ehealicords.constant.SpecialistDegree;
import com.anhnc2.ehealicords.constant.SpecialistType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class SpecialistUpdateRequest {
    private String email;
    private String fullName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private LocalDate dateOfStartingWork;
    private Gender gender;

    private SpecialistType specialistType;
    private AcademicRank academicRank;
    private Degree degree;
    private SpecialistDegree degreeOfSpecialist;

    private Integer medialSpecialtyId;

    private Integer roomId;
}
