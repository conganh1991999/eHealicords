package com.anhnc2.ehealicords.data.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ExHistoryUpdateRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String recordType;
    private Integer branchId;
    private Long exDoctorId;
}
