package com.anhnc2.ehealicords.data.request;

import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

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

    private LocalTime morningOpen;

    private LocalTime morningClose;

    private LocalTime afternoonOpen;

    private LocalTime afternoonClose;

    private LocalTime eveningOpen;

    private LocalTime eveningClose;

    private List<Integer> days;
}
