package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.constant.BranchStatus;
import com.anhnc2.ehealicords.data.entity.BranchEntity;
import lombok.Data;

@Data
public class BranchResponse {
    public BranchResponse(BranchEntity branchEntity) {
        this.id = branchEntity.getId();
        this.name = branchEntity.getName();
        this.fullAddress = branchEntity.getFullAddress();
        this.status = branchEntity.getStatus();
    }

    private Integer id;
    private String name;
    private String fullAddress;
    private BranchStatus status;
    private Long feeAppointment;
    private Long feeConsulting;
}
