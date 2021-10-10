package com.anhnc2.ehealicords.data.request;

import com.anhnc2.ehealicords.constant.AcademicRank;
import com.anhnc2.ehealicords.constant.Degree;
import com.anhnc2.ehealicords.constant.Gender;
import com.anhnc2.ehealicords.constant.SpecialistDegree;
import com.anhnc2.ehealicords.constant.SpecialistType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SpecialistCreationRequest {
    private String email;
    private String fullName;
    private String phoneNumber;
    private String avatarKey;
    private String dateOfBirth;
    private String dateOfStartingWork;

    private Gender gender;
    private SpecialistType type;
    private AcademicRank academicRank;
    private Degree degree;
    private SpecialistDegree degreeOfSpecialist;

    private Integer medialSpecialtyId;
    private Integer roomId;
    private Long staffId;
    private Integer branchId;

    private MultipartFile avatar;
}