package com.ranze.schedule;

import com.ranze.schedule.pojo.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CachedData {
    public Map<String, UserInfo> userInfoMap = new ConcurrentHashMap<>();

}
