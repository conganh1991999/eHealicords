package com.anhnc2.ehealicords.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecialistResponse {
    private Long id;
    private String fullName;
    private String specialty;
}
