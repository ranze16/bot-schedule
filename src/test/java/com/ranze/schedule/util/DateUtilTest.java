package com.ranze.schedule.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateUtilTest {
    @Autowired
    DateUtil dateUtil;

    @Test
    public void convertDate() {
        Date date = dateUtil.convertDate("2019-09-08");
        System.out.println(date.getTime());
        System.out.println((System.currentTimeMillis() - date.getTime()) / 1000 / 60);
    }

    @Test
    public void convertTime() {
        Time time = dateUtil.convertTime("04:00:00");
        System.out.println(time.getTime());
        System.out.println(System.currentTimeMillis());
    }
}