package com.ranze.schedule.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessConfigTest {
    @Autowired
    BusinessConfig config;

    @Test
    public void getDataCenterId() {
        assert config.getDataCenterId() != 0;
    }

    @Test
    public void getWorkerId() {
        assert config.getWorkerId() != 0;
    }

    @Test
    public void getCreateBabyNameIntent() {
        String createBabyNameIntent = config.getCreateBabyNameIntent();
        System.out.println(createBabyNameIntent);
        assert createBabyNameIntent != null;
        assert !createBabyNameIntent.equals("");
    }

    @Test
    public void getWildCardSlot() {
        String wildCardSlot = config.getWildCardSlot();
        System.out.println(wildCardSlot);
        assert !wildCardSlot.equals("");
    }
}