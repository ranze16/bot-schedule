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

    private String createBabyNameIntent;
    private String createScheduleIntent;

    private String wildCardSlot;
    private String taskLimitationSlot;
    private String taskTypeSlot;
    private String taskInDayStartTimeSlot;
    private String taskInDayEndTimeSlot;
    private String onceTaskDateSlot;

}
