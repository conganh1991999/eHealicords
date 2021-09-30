package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.constant.AcademicRank;
import com.anhnc2.ehealicords.constant.Gender;
import com.anhnc2.ehealicords.constant.SpecialistDegree;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorDetailsResponse {
    private Long id;
    private AcademicRank academicRank;
    private String avatarKey;
    private Integer branchId;
    private String branchName;
    private SpecialistDegree degree;
    private String email;
    private String fullName;
    private Gender gender;
    private String phoneNumber;
    private Integer specialtyId;
    private String specialtyName;
}
