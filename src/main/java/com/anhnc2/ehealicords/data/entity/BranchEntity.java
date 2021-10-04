package com.anhnc2.ehealicords.data.entity;

import com.anhnc2.ehealicords.constant.BranchStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "branch")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String fullAddress;

    private String address;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private BranchStatus status;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", insertable = false, updatable = false)
    private ProvinceEntity provinceEntity;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private DistrictEntity districtEntity;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id", insertable = false, updatable = false)
    private WardEntity wardEntity;

    @Column(name = "province_id")
    private Integer provinceId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "ward_id")
    private Integer wardId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_hours_id", insertable = false, updatable = false)
    BusinessHoursEntity businessHoursEntity;

    @Column(name = "business_hours_id")
    private Integer businessHoursId;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "branchEntity")
    private List<AccountEntity> accountEntities;
}
