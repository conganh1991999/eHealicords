package com.anhnc2.ehealicords.data.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PresignResult {
    private String key;
    private String presignUrl;
}
