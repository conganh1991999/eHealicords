package com.anhnc2.ehealicords.data.common;

//import com.anhnc2.ehealicords.constant.Gender;
//import com.anhnc2.ehealicords.data.entity.UserRelativeEntity;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@Data
//@AllArgsConstructor
//@Builder
//@NoArgsConstructor
//public class PatientRelative {
//    private Long id;
//    private String fullName;
//    private Long patientId;
//    private LocalDate dateOfBirth;
//    private String job;
//    private Gender gender;
//    private String personalId;
//    private String relation;
//
//    public UserRelativeEntity toPatientRelative() {
//        return UserRelativeEntity.builder()
//                .id(id)
//                .fullName(fullName)
//                .userId(patientId)
//                .dateOfBirth(dateOfBirth)
//                .job(job)
//                .gender(gender)
//                .personalId(personalId)
//                .relation(relation)
//                .build();
//    }
//
//    public static PatientRelative fromEntity(UserRelativeEntity userRelativeEntity) {
//        return PatientRelative.builder()
//                .id(userRelativeEntity.getId())
//                .fullName(userRelativeEntity.getFullName())
//                .patientId(userRelativeEntity.getUserId())
//                .dateOfBirth(userRelativeEntity.getDateOfBirth())
//                .job(userRelativeEntity.getJob())
//                .gender(userRelativeEntity.getGender())
//                .personalId(userRelativeEntity.getPersonalId())
//                .relation(userRelativeEntity.getRelation())
//                .build();
//    }
//}
