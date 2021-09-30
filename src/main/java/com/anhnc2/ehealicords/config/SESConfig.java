package com.anhnc2.ehealicords.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aws.ses")
public class SESConfig {
    private String sourceEmail;
    private String accessKeyId;
    private String secretAccessKey;
}
