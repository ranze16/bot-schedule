package com.ranze.schedule.service;

import com.ranze.schedule.Cons;
import com.ranze.schedule.pojo.Task;
import com.ranze.schedule.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
    @Autowired
    DateUtil dateUtil;
    @Autowired
    TaskService taskService;

    private String userId = "35361352";
    private String content = "背单词";
    private String bindUserBaby = "宝贝";

    @Test
    public void insertOnceTask() {
        Date date = new Date(System.currentTimeMillis());
        Time startTime = new Time(System.currentTimeMillis() + Cons.ONE_HOUR_MILLIONS);
        Time endTime = new Time(System.currentTimeMillis() + Cons.ONE_HOUR_MILLIONS * 2);
        Task task = taskService.insertOnceTask(userId, bindUserBaby, date, startTime, endTime, content, -1);
        assert task != null;
    }

    @Test
    public void insertIntervalTask() {
        Date startTime = new Date(System.currentTimeMillis() + Cons.ONE_DAY_MILLIONS);
        Date endTime = new Date(startTime.getTime() + 3600 * 1000 * 24 * 10);
        Time timeInDayStart = new Time(System.currentTimeMillis() + 3600 * 1000);
        Time timeInDayEnd = new Time(System.currentTimeMillis() + 3600 * 1000 * 3);
        Task task = taskService.insertIntervalTask(userId, bindUserBaby, startTime, endTime,
                Cons.EXCLUDE_DATE_TYPE_KEEP_THURSDAY, timeInDayStart, timeInDayEnd, content, -1);
        assert task != null;
    }

    @Test
    public void insertLongTermTask() {
        Time timeInDayStart = new Time(System.currentTimeMillis() + 3600 * 1000 * 2);
        Time timeInDayEnd = new Time(System.currentTimeMillis() + 3600 * 1000 * 2);
        Task task = taskService.insertLongTermTask(userId, bindUserBaby, Cons.EXCLUDE_DATE_TYPE_KEEP_WEEKEND,
                timeInDayStart, timeInDayEnd, content, -1);
        assert task != null;
    }

    @Test
    public void selectAllTask() {
        List<Task> tasks = taskService.selectAllTask(userId);
        System.out.println("task size: " + tasks.size() + ", " + tasks);
    }

    @Test
    public void deleteTask() {
        boolean success = taskService.deleteTask(620050546199498752L);
    }

    @Test
    public void insertClockInToday() {
        taskService.insertClockInToday(userId, 620050546199498752L);
    }

    @Test
    public void selectCurrentNearbyTasks() {
        List<Task> tasks = taskService.selectCurrentNearbyTasks(userId, Cons.ONE_HOUR_MILLIONS);
        System.out.println(tasks);
    }

    @Test
    public void selectTasksByDate() {
        List<Task> tasks1 = taskService.selectTasksByDate(userId, dateUtil.today());
        List<Task> tasks2 = taskService.selectTasksByDate(userId, dateUtil.tomorrow());
        System.out.println(tasks1);
        System.out.println(tasks2);
    }

    @Test
    public void markTask() {
        boolean success = taskService.markTask(620049823151820800L);
        assert success;

    }

    @Test
    public void selectMarkedTask() {
        List<Task> tasks = taskService.selectMarkedTask(userId);
        System.out.println(tasks);
    }

    @Test
    public void selectClockInTaskIds() {
        List<Long> clockInTaskIds = taskService.selectClockInTaskIds(userId, dateUtil.today());
        System.out.println(clockInTaskIds);
    }
}