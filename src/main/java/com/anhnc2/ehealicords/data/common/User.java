package com.anhnc2.ehealicords.data.common;

//import com.anhnc2.ehealicords.constant.Gender;
//import com.anhnc2.ehealicords.constant.UserStatus;
//import com.anhnc2.ehealicords.data.entity.UserEntity;
//import lombok.Builder;
//import lombok.Data;
//import lombok.ToString;
//
//@Data
//@Builder
//@ToString
//public class User {
//    private Long id;
//    private String phoneNumber;
//    private String fullName;
//    private String job;
//    private UserStatus status;
//    private String personalId;
//    private Gender gender;
//    private Integer provinceId;
//    private Integer wardId;
//    private Integer districtId;
//    private String dateOfBirth;
//    private String districtName;
//    private String provinceName;
//    private String wardName;
//    private String address;
//    private String avatarKey;
//    private long balance;
//    private long balanceUpdateTs;
//    private Boolean isValid;
//
//    public static User fromEntity(UserEntity userEntity) {
//        return User.builder()
//                        .id(userEntity.getId())
//                        .phoneNumber(userEntity.getPhoneNumber())
//                        .job(userEntity.getJob())
//                        .status(userEntity.getStatus())
//                        .personalId(userEntity.getPersonalId())
//                        .gender(userEntity.getGender())
//                        .dateOfBirth(userEntity.getDateOfBirthString())
//                        .fullName(userEntity.getFullName())
//                        .provinceId(userEntity.getProvinceId())
//                        .districtId(userEntity.getDistrictId())
//                        .wardId(userEntity.getWardId())
//                        .address(userEntity.getAddress())
//                        .avatarKey(userEntity.getAvatarKey())
//                        .balance(userEntity.getBalance())
//                        .isValid(userEntity.getIsValid())
//                        .balanceUpdateTs(userEntity.getBalanceUpdateTs())
//                        .build();
//
//    }
//}
