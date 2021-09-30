package com.anhnc2.ehealicords.config;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
@Data
public class ZoneConfig {
    private ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
}
