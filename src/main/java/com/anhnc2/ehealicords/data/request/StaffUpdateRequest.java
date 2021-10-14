package com.anhnc2.ehealicords.data.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StaffUpdateRequest {
    private String fullName;
    private String email;
}
