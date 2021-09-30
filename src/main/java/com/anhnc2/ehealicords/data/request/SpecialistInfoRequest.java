package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.constant.AcademicRank;
import com.anhnc2.ehealicords.constant.Gender;
import com.anhnc2.ehealicords.constant.SpecialistDegree;
import lombok.Data;

@Data
public class SpecialistInfoRequest {
    String fullName;
    String email;
    String phoneNumber;
    Gender gender;
    AcademicRank academicRank;
    SpecialistDegree degreeOfSpecialist;
    Integer specialtyId;
    Integer branchId;
}
