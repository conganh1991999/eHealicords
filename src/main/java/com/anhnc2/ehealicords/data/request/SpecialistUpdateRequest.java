package com.anhnc2.ehealicords.data.request;

import  com.anhnc2.ehealicords.constant.AcademicRank;
import  com.anhnc2.ehealicords.constant.Gender;
import  com.anhnc2.ehealicords.constant.SpecialistDegree;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DoctorUpdateRequest {
    AcademicRank academicRank;
    String avatarKey;
    SpecialistDegree degree;
    String email;
    String fullName;
    Gender gender;
    String phoneNumber;
}
