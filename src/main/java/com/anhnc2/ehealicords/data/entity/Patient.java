package com.anhnc2.ehealicords.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "patient")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identity_card_number", nullable = false, unique = true)
    private Long identityCardNumber;

    @Column(name = "personal_health_id", nullable = false, unique = true)
    private Long personalHealthId;

    private String gender;

    private String healthInsuranceCardNumber;

    private String fullName;

    private LocalDate dateOfBirth;

    private String placeOfBirth;

    private String hometown;

    private String phoneNumber;

    private String identityCardIssuePlace;

    private LocalDate identityCardIssueDate;

    private String temporaryResidenceAddress;

    private String permanentAddress;

    private String nationality;

    private String religion;

    private String folk;

    private String occupation;

    private String academicLevel;

    private String maritalStatus;

    private String relativeName;

    private String relativePhoneNumber;

    @Column(name = "relative_identity_card_number", nullable = false, unique = true)
    private Long relativeIdentityCardNumber;

    private String relative;

    private Long createdTime;

    private Long updatedTime;

    private Integer branchId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_province_id", insertable = false, updatable = false)
    private ProvinceEntity birthProvinceEntity;

    @Column(name = "birth_province_id")
    private Integer birthProvinceId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temp_province_id", insertable = false, updatable = false)
    private ProvinceEntity tempProvinceEntity;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temp_district_id", insertable = false, updatable = false)
    private DistrictEntity tempDistrictEntity;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temp_ward_id", insertable = false, updatable = false)
    private WardEntity tempWardEntity;

    @Column(name = "temp_province_id")
    private Integer tempProvinceId;

    @Column(name = "temp_district_id")
    private Integer tempDistrictId;

    @Column(name = "temp_ward_id")
    private Integer tempWardId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perm_province_id", insertable = false, updatable = false)
    private ProvinceEntity permProvinceEntity;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perm_district_id", insertable = false, updatable = false)
    private DistrictEntity permDistrictEntity;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perm_ward_id", insertable = false, updatable = false)
    private WardEntity permWardEntity;

    @Column(name = "perm_province_id")
    private Integer permProvinceId;

    @Column(name = "perm_district_id")
    private Integer permDistrictId;

    @Column(name = "perm_ward_id")
    private Integer permWardId;
}
