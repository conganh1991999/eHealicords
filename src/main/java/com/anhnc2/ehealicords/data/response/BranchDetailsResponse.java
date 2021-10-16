package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.constant.BranchStatus;
import com.anhnc2.ehealicords.data.entity.BranchEntity;
import com.anhnc2.ehealicords.data.entity.BusinessHoursEntity;
import com.anhnc2.ehealicords.data.entity.DistrictEntity;
import com.anhnc2.ehealicords.data.entity.ProvinceEntity;
import com.anhnc2.ehealicords.data.entity.WardEntity;
import lombok.Data;

@Data
public class BranchDetailsResponse {

    public BranchDetailsResponse(BranchEntity branchEntity) {
        this.id = branchEntity.getId();
        this.name = branchEntity.getName();
        this.email = branchEntity.getEmail();
        this.phoneNumber = branchEntity.getPhoneNumber();
        this.status = branchEntity.getStatus();
        this.address = branchEntity.getAddress();
        this.province = branchEntity.getProvinceEntity().toBuilder().build();
        this.district = branchEntity.getDistrictEntity().toBuilder().build();
        this.ward = branchEntity.getWardEntity().toBuilder().build();
        this.businessHours = branchEntity.getBusinessHoursEntity().toBuilder().build();
    }

    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private BranchStatus status;
    private String address;
    private ProvinceEntity province;
    private DistrictEntity district;
    private WardEntity ward;
    private BusinessHoursEntity businessHours;
}
