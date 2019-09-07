package com.ranze.schedule.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "sys")
@PropertySource("classpath:business.properties")
@Data
public class BusinessConfig {
    private long dataCenterId;
    private long workerId;

    private String wildCardSlot;
    private String createBabyNameIntent;
    private String createScheduleIntent;

}
