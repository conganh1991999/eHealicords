package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.constant.AcademicRank;
import com.anhnc2.ehealicords.constant.Gender;
import com.anhnc2.ehealicords.constant.SpecialistDegree;
import lombok.Data;

@Data
public class CreateDoctorRequest {
    AcademicRank academicRank;
    String avatarKey;
    Integer branchId;
    SpecialistDegree degree;
    String email;
    String fullName;
    Gender gender;
    String phoneNumber;
    Integer specialtyId;
}
