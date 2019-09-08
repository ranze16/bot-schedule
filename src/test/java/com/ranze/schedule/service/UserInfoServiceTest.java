package com.ranze.schedule.service;

import com.ranze.schedule.pojo.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceTest {
    @Autowired
    UserInfoService userInfoService;

    private String userId = "user_1";

    @Test
    public void insertUserInfo() {
        userInfoService.insertUserInfo("user_1", "ll");
    }

    @Test
    public void incrementPoints() {
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        boolean success = userInfoService.incrementPoints(userInfo, 10);
        assert success;
    }

    @Test
    public void getUserInfo() {
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        System.out.println(userInfo);
    }
}