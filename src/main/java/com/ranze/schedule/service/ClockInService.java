package com.ranze.schedule.service;

import com.ranze.schedule.mapper.ClockInMapper;
import com.ranze.schedule.pojo.ClockIn;
import com.ranze.schedule.pojo.ClockInExample;
import com.ranze.schedule.pojo.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ClockInService {
    @Autowired
    ClockInMapper clockInMapper;


}
