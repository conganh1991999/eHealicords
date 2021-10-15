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
        this.province = branchEntity.getProvinceEntity().toBuilder().build();
        this.district = branchEntity.getDistrictEntity().toBuilder().build();
        this.ward = branchEntity.getWardEntity().toBuilder().build();
        this.businessHour = branchEntity.getBusinessHoursEntity().toBuilder().build();
        this.address = branchEntity.getAddress();
        this.status = branchEntity.getStatus();
    }

    private Integer id;

    private String name;

    private ProvinceEntity province;

    private DistrictEntity district;

    private WardEntity ward;

    BranchStatus status;

    private String address;

    BusinessHoursEntity businessHour;

    Integer minutePerShift;

    Integer minuteDeposit;

    Long feeAppointment;

    Long feeConsulting;
}
