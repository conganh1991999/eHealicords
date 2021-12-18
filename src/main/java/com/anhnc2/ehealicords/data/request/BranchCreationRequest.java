package com.anhnc2.ehealicords.data.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BranchCreationRequest {
    private Integer id;

    private String name;

    private String fullAddress;

    private String address;

    private String email;

    private String phoneNumber;

    private Integer provinceId;

    private Integer districtId;

    private Integer wardId;

    private String morningOpen;

    private String morningClose;

    private String afternoonOpen;

    private String afternoonClose;

    private String eveningOpen;

    private String eveningClose;

    private List<Integer> days;
}
