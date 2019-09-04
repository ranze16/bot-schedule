package com.ranze.schedule.service;

import com.ranze.schedule.Cons;
import com.ranze.schedule.mapper.ClockInMapper;
import com.ranze.schedule.mapper.TaskMapper;
import com.ranze.schedule.pojo.ClockIn;
import com.ranze.schedule.pojo.Task;
import com.ranze.schedule.pojo.TaskExample;
import com.ranze.schedule.util.DateUtil;
import com.ranze.schedule.util.UniqueIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskService {
    @Autowired
    UniqueIDUtil uniqueIDUtil;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    ClockInMapper clockInMapper;

    public boolean insertOnceTask(String userId, String bindUser, Timestamp singleTime, String content, long bindTaskId) {
        // 单次任务不会有排除日期的需求
        if (bindTaskId > 0) {
            return insertTask(userId, Cons.BIND_ATTACHED, bindUser, bindTaskId, Cons.TASK_ONCE,
                    null, null, Cons.EXCLUDE_DATE_TYPE_NONE, singleTime, null, content);
        } else {
            return insertTask(userId, Cons.BIND_MASTER, bindUser, -1, Cons.TASK_ONCE,
                    null, null, Cons.EXCLUDE_DATE_TYPE_NONE, singleTime, null, content);
        }
    }

    public boolean insertIntervalTask(String userId, String bindUser, Date startTime, Date entTime, byte excludeDateType,
                                      Time timeInDay, String content, long bindTaskId) {
        if (bindTaskId > 0) {
            return insertTask(userId, Cons.BIND_ATTACHED, bindUser, bindTaskId, Cons.TASK_INTERVAL,
                    startTime, entTime, excludeDateType, null, timeInDay, content);
        } else {
            return insertTask(userId, Cons.BIND_MASTER, bindUser, -1, Cons.TASK_INTERVAL,
                    startTime, entTime, excludeDateType, null, timeInDay, content);
        }
    }

    public boolean insertLongTermTask(String userId, String bindUser, byte excludeDateType,
                                      Time timeInDay, String content, long bindTaskId) {
        if (bindTaskId > 0) {
            return insertTask(userId, Cons.BIND_ATTACHED, bindUser, bindTaskId, Cons.TASK_LONG,
                    null, null, excludeDateType, null, timeInDay, content);
        } else {
            return insertTask(userId, Cons.BIND_MASTER, bindUser, -1, Cons.TASK_LONG,
                    null, null, excludeDateType, null, timeInDay, content);
        }

    }

    /**
     * 创建任务
     * 任务分为主任务与附属任务，如果是附属任务，则需要设置 {@code bindTaskId}
     * <ul>
     * <li>单次任务只设置 {@code singleDateTime}></li>
     * <li>一段时间内任务设置 {@code startDate}, {@code endDate}, {@code timeInDay}></li>
     * <li>长期任务只设置 {@code timeInDay}</li>
     * </ul>
     *
     * @param userId         任务所属用户的 user id
     * @param bindType       任务绑定类型，1 master, 2 attached
     * @param bindUser       任务绑定者的称呼，如宝贝或者妈妈等
     * @param bindTaskId     如果是 attached 任务, 这个参数表示绑定的任务的 id, 否则为 -1
     * @param type           任务类型, 1 单次任务, 2 一段时间的任务 3 长期任务
     * @param startDate      一段时间任务的开始日期
     * @param endDate        一段时间任务的结束日期
     * @param singleDateTime 单次任务的日期时间
     * @param timeInDay      每天执行任务的时间点，如果是长期任务，则只设置这个时间
     * @param content        任务的具体内容
     * @return 成功返回 {@code true}， 失败返回 {@code false}
     */
    public boolean insertTask(String userId, byte bindType, String bindUser, long bindTaskId, byte type,
                              Date startDate, Date endDate, byte excludeDateType,
                              Timestamp singleDateTime, Time timeInDay, String content) {
        Task task = new Task();
        task.setId(uniqueIDUtil.nextId());
        task.setUserId(userId);
        task.setBindType(bindType);
        task.setBindUser(bindUser);

        if (bindTaskId > 0) {
            task.setBindTaskId(bindTaskId);
        }

        task.setType(type);

        if (startDate != null) {
            task.setStartDate(startDate);
        }
        if (endDate != null) {
            task.setEndDate(endDate);
        }

        task.setExcludeDateType(excludeDateType);

        if (singleDateTime != null) {
            task.setSingleDateTime(singleDateTime);
        }

        if (timeInDay != null) {
            task.setTimeInDay(timeInDay);
        }

        task.setContent(content);

        return taskMapper.insertSelective(task) == 1;
    }

    public List<Task> selectCurrentNearbyTasks(String userId, long earlierTimeInMillions) {
        List<Task> ret = new ArrayList<>();

        List<Task> tasks = selectTodayTasks(userId);

        long zeroMillions = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        long curToZeroMillions = System.currentTimeMillis() - zeroMillions;

        // 任务的执行时间必须在 [judgeStartTime, curToZeroMillions] 之间, 如果 judgeStartTime 小于 0, 则置为 0
        long judgeStartTime = curToZeroMillions - earlierTimeInMillions;
        if (judgeStartTime < 0) {
            judgeStartTime = 0;
        }

        long finalJudgeStartTime = judgeStartTime;
        tasks.forEach(task -> {
            // 单次任务根据 singleTime 判断, 长期任务和时间段任务根据 timeInDay 判断
            if (task.getType() == Cons.TASK_ONCE) {
                long taskTime = task.getSingleDateTime().getTime() - zeroMillions;
                if (taskTime >= finalJudgeStartTime && taskTime <= curToZeroMillions) {
                    ret.add(task);
                }

            } else {
                long taskTime = task.getTimeInDay().getTime();
                if (taskTime >= finalJudgeStartTime && taskTime <= curToZeroMillions) {
                    ret.add(task);
                }
            }

        });
        return ret;
    }

    public List<Task> selectTodayTasks(String userId) {
        TaskExample taskExample = new TaskExample();

        // 一段时间任务
        Date today = new Date(System.currentTimeMillis());
        taskExample.or()
                .andUserIdEqualTo(userId)
                .andStartDateLessThanOrEqualTo(today)
                .andEndDateGreaterThanOrEqualTo(today);

        long zeroMillions = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        Date todayZero = new Date(zeroMillions);
        Date tomorrowZero = new Date(zeroMillions + 3600 * 1000 * 24);

        // 单次任务
        taskExample.or()
                .andUserIdEqualTo(userId)
                .andSingleDateTimeBetween(todayZero, tomorrowZero);

        // 长期任务
        taskExample.or()
                .andUserIdEqualTo(userId)
                .andTypeEqualTo(Cons.TASK_LONG);

        List<Task> tasks = taskMapper.selectByExample(taskExample);

        List<Task> ret = new ArrayList<>();
        for (Task t : tasks) {
            if (!shouldExclude(t)) {
                ret.add(t);
            }
        }
        return ret;
    }

    private boolean shouldExclude(Task t) {
        boolean exclude = true;
        byte excludeDateType = t.getExcludeDateType();
        Date today = new Date(System.currentTimeMillis());
        switch (excludeDateType) {
            case Cons.EXCLUDE_DATE_TYPE_NONE:
                exclude = false;
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_MONDAY:
                exclude = !dateUtil.isMonday(today);
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_TUESDAY:
                exclude = !dateUtil.isTuesDay(today);
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_WEDNESDAY:
                exclude = !dateUtil.isWednesday(today);
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_THURSDAY:
                exclude = !dateUtil.isThursday(today);
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_FRIDAY:
                exclude = !dateUtil.isFriday(today);
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_SATURDAY:
                exclude = !dateUtil.isSaturday(today);
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_SUNDAY:
                exclude = !dateUtil.isSunday(today);
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_WEEKDAY:
                exclude = !dateUtil.isWeekday(today);
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_WEEKEND:
                exclude = !dateUtil.isWeekend(today);
                break;
            default:
                exclude = false;
                break;
        }
        return exclude;

    }

    public boolean markTask(long taskId) {
        Task task = new Task();
        task.setId(taskId);
        task.setMarked(Cons.BYTE_1);

        return taskMapper.updateByPrimaryKeySelective(task) == 1;

    }

    public List<Task> selectMarkedTask(String userId) {
        TaskExample taskExample = new TaskExample();
        TaskExample.Criteria criteria = taskExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andMarkedEqualTo(Cons.BYTE_1);
        return taskMapper.selectByExample(taskExample);
    }

    public List<Task> selectAllTask(String userId) {
        TaskExample taskExample = new TaskExample();
        TaskExample.Criteria criteria = taskExample.createCriteria();
        criteria.andUserIdEqualTo(userId);

        return taskMapper.selectByExample(taskExample);
    }

    public boolean deleteTask(long taskId) {
        return taskMapper.deleteByPrimaryKey(taskId) == 1;
    }

    public boolean insertClockInToday(String userId, long taskId) {
        return insertClockIn(userId, taskId, new Date(System.currentTimeMillis()));
    }

    public boolean insertClockIn(String userId, long taskId, Date date) {
        ClockIn clockIn = new ClockIn();
        clockIn.setId(uniqueIDUtil.nextId());
        clockIn.setUserId(userId);
        clockIn.setTaskId(taskId);
        clockIn.setOpDate(date);

        int ret = 0;
        try {
            ret = clockInMapper.insertSelective(clockIn);
        } catch (DuplicateKeyException e) {
            log.info("重复插入打卡记录");
        }

        return ret == 1;
    }

}
