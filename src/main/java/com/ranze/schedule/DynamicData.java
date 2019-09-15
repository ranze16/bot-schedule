package com.ranze.schedule;

import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class DynamicData {
    private static final String URL_BACKGROUND_DAY = "http://dbp-resource.gz.bcebos.com/e07bac25-a0b9-6eef-8fd2-b32387308650/day.png?authorization=bce-auth-v1%2Fa4d81bbd930c41e6857b989362415714%2F2019-09-15T13%3A44%3A01Z%2F-1%2F%2F4a26eb6bccb6d18de2c60e7372e8e783cc3799fa85e78761f571c4a4072951f7";
    private static final String URL_BACKGROUND_NIGHT = "http://dbp-resource.gz.bcebos.com/e07bac25-a0b9-6eef-8fd2-b32387308650/night.png?authorization=bce-auth-v1%2Fa4d81bbd930c41e6857b989362415714%2F2019-09-15T13%3A44%3A01Z%2F-1%2F%2Ff2ce292351b1bc0633e7070c52e36f5392e9c4a726483fd02e6cb724bdb1f1da";

    public String getHomeBackgroundUrl() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour <= 6 || hour >= 18) {
            return URL_BACKGROUND_NIGHT;
        } else {
            return URL_BACKGROUND_DAY;
        }
    }

}
