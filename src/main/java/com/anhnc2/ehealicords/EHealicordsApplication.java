package com.anhnc2.ehealicords;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableCaching
public class EHealicordsApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(EHealicordsApplication.class);

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        LOGGER.info("Spring server timezone = {}", TimeZone.getDefault().getID());
    }

    public static void main(String[] args) {
        SpringApplication.run(EHealicordsApplication.class, args);
    }
}
