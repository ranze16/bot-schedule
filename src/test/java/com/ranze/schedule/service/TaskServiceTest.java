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

    @Test

    public void insertOnceTask() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        boolean success = taskService.insertOnceTask(userId, timestamp, content, -1);
        assert success;
    }

    @Test
    public void insertIntervalTask() {
        Date startTime = new Date(System.currentTimeMillis());
        Date endTime = new Date(startTime.getTime() + 3600 * 1000 * 24 * 10);
        Time timeInDay = new Time(System.currentTimeMillis() + 3600 * 1000);
        boolean success = taskService.insertIntervalTask(userId, startTime, endTime, timeInDay, content, -1);
        assert success;
    }

    @Test
    public void insertLongTermTask() {
        Time timeInDay = new Time(System.currentTimeMillis() + 3600 * 1000 * 2);
        boolean success = taskService.insertLongTermTask(userId, timeInDay, content, -1);
        assert success;
    }

    @Test
    public void selectCurrentTasks() {
        List<Task> tasks = taskService.selectTodayTasks(userId);
        System.out.println(tasks);
    }

    @Test
    public void selectAllTask() {
        List<Task> tasks = taskService.selectAllTask(userId);
        System.out.println(tasks);
    }

    @Test
    public void deleteTask() {
        taskService.deleteTask(618599960518201344L);
    }

    @Test
    public void insertClockInToday() {
        taskService.insertClockInToday(userId, 618599218768121856L);
    }

    @Test
    public void selectCurrentNearbyTasks() {
        List<Task> tasks = taskService.selectCurrentNearbyTasks(userId, Cons.ONE_HOUR_MILLIONS);
        System.out.println(tasks);
    }
}