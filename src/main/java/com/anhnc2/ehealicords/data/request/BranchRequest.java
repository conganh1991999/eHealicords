package com.anhnc2.ehealicords.data.request;

import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchRequest {
    Integer id;

    String name;

    Integer provinceId;

    Integer districtId;

    Integer wardId;

    String fullAddress;

    String address;

    LocalTime morningOpen;

    LocalTime morningClose;

    LocalTime afternoonOpen;

    LocalTime afternoonClose;

    LocalTime eveningOpen;

    LocalTime eveningClose;

    List<Integer> days;
}
