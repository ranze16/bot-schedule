package com.ranze.schedule.service;

import com.ranze.schedule.Cons;
import com.ranze.schedule.pojo.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
    @Autowired
    TaskService taskService;

    private String userId = "test_task";
    private String content = "背单词";
    private String bindUserBaby = "宝贝";

    @Test

    public void insertOnceTask() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + Cons.ONE_DAY_MILLIONS * 2);
        boolean success = taskService.insertOnceTask(userId, bindUserBaby, timestamp, content, -1);
        assert success;
    }

    @Test
    public void insertIntervalTask() {
        Date startTime = new Date(System.currentTimeMillis() + Cons.ONE_DAY_MILLIONS);
        Date endTime = new Date(startTime.getTime() + 3600 * 1000 * 24 * 10);
        Time timeInDay = new Time(System.currentTimeMillis() + 3600 * 1000);
        boolean success = taskService.insertIntervalTask(userId, bindUserBaby, startTime, endTime,
                Cons.EXCLUDE_DATE_TYPE_ONLY_WEEKDAY, timeInDay, content, -1);
        assert success;
    }

    @Test
    public void insertLongTermTask() {
        Time timeInDay = new Time(System.currentTimeMillis() + 3600 * 1000 * 2);
        boolean success = taskService.insertLongTermTask(userId, bindUserBaby, Cons.EXCLUDE_DATE_TYPE_NONE,
                timeInDay, content, -1);
        assert success;
    }

    @Test
    public void selectAllTask() {
        List<Task> tasks = taskService.selectAllTask(userId);
        System.out.println(tasks);
    }

    @Test
    public void deleteTask() {
        taskService.deleteTask(618942933227409408L);
    }

    @Test
    public void insertClockInToday() {
        taskService.insertClockInToday(userId, 618943308869275648L);
    }

    @Test
    public void selectCurrentNearbyTasks() {
        List<Task> tasks = taskService.selectCurrentNearbyTasks(userId, Cons.ONE_HOUR_MILLIONS);
        System.out.println(tasks);
    }

    @Test
    public void selectTodayTasks() {
        List<Task> tasks = taskService.selectTodayTasks(userId);
        System.out.println(tasks);
    }

    @Test
    public void markTask() {
        boolean success = taskService.markTask(618942694735089664L);
        assert success;

    }

    @Test
    public void selectMarkedTask() {
        List<Task> tasks = taskService.selectMarkedTask(userId);
        System.out.println(tasks);
    }
}