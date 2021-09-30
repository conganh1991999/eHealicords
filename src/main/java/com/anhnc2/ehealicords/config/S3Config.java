package com.anhnc2.ehealicords.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aws.s3")
public class S3Config {
    private String bucketName;
    private String accessKeyId;
    private String secretAccessKey;
}
