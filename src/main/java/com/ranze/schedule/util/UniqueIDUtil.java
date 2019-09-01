package com.ranze.schedule.util;

import com.ranze.schedule.config.BusinessConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueIDUtil {
    private BusinessConfig businessConfig;

    private SnowflakeIDUtil snowflakeIDUtil;

    public UniqueIDUtil(BusinessConfig businessConfig) {
        snowflakeIDUtil = new SnowflakeIDUtil(businessConfig.getDataCenterId(), businessConfig.getWorkerId());
    }

    public long nextId() {
        return snowflakeIDUtil.nextId();
    }

    @Autowired
    public void setBusinessConfig(BusinessConfig businessConfig) {
        this.businessConfig = businessConfig;
    }
}
