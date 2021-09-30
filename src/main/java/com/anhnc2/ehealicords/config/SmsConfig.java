package com.anhnc2.ehealicords.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {
    private String sid;
    private String token;
    private boolean enable;
    private String sourceNumber;
}
