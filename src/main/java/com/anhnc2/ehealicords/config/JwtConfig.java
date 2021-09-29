package com.anhnc2.ehealicords.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig implements AppConfig {
    private String secret;
    private long validTime;

    @Override
    public void validate() {
        boolean isValid = (secret != null) && !secret.equals("") && validTime > 0;
        if (!isValid) {
            throw new RuntimeException("JwtConfig is not valid");
        }
    }
}
