package com.ranze.schedule.service;

import com.ranze.schedule.Cons;
import com.ranze.schedule.mapper.TaskMapper;
import com.ranze.schedule.pojo.Task;
import com.ranze.schedule.util.UniqueIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Service
@Slf4j
public class TaskService {
    @Autowired
    UniqueIDUtil uniqueIDUtil;

    @Autowired
    TaskMapper taskMapper;

    public boolean insertOnceTask(String userId, Timestamp singleTime, String content, long bindTaskId) {
        if (bindTaskId > 0) {
            return insertTask(userId, Cons.BIND_ATTACHED, bindTaskId, Cons.TASK_ONCE,
                    null, null, singleTime, null, content);
        } else {
            return insertTask(userId, Cons.BIND_MASTER, -1, Cons.TASK_ONCE,
                    null, null, singleTime, null, content);
        }
    }

    public boolean insertIntervalTask(String userId, Date startTime, Date entTime, Time timeInDay,
                                      String content, long bindTaskId) {
        if (bindTaskId > 0) {
            return insertTask(userId, Cons.BIND_ATTACHED, bindTaskId, Cons.TASK_INTERVAL,
                    startTime, entTime, null, timeInDay, content);
        } else {
            return insertTask(userId, Cons.BIND_MASTER, -1, Cons.TASK_INTERVAL,
                    startTime, entTime, null, timeInDay, content);
        }
    }

    public boolean insertLongTermTask(String userId, Time timeInDay, String content, long bindTaskId) {
        if (bindTaskId > 0) {
            return insertTask(userId, Cons.BIND_ATTACHED, bindTaskId, Cons.TASK_LONG,
                    null, null, null, timeInDay, content);
        } else {
            return insertTask(userId, Cons.BIND_MASTER, -1, Cons.TASK_LONG,
                    null, null, null, timeInDay, content);
        }

    }

    /**
     * 创建任务
     * 任务分为主任务与附属任务，如果是附属任务，则需要设置 {@code bindTaskId}
     * <ul>
     * <li>单次任务只设置 {@code singleTime}></li>
     * <li>一段时间内任务设置 {@code startTime}, {@code endTime}, {@code timeInDay}></li>
     * <li>长期任务只设置 {@code timeInDay}</li>
     * </ul>
     *
     * @param userId     任务所属用户的 user id
     * @param bindType   任务绑定类型，1 master, 2 attached
     * @param bindTaskId 如果是 attached 任务, 这个参数表示绑定的任务的 id, 否则为 -1
     * @param type       任务类型, 1 单次任务, 2 一段时间的任务 3 长期任务
     * @param startTime  一段时间任务的开始日期
     * @param endTime    一段时间任务的结束日期
     * @param singleTime 单次任务的日期时间
     * @param timeInDay  每天执行任务的时间点，如果是长期任务，则只设置这个时间
     * @param content    任务的具体内容
     * @return 成功返回 {@code true}， 失败返回 {@code false}
     */
    public boolean insertTask(String userId, byte bindType, long bindTaskId, byte type, Date startTime, Date endTime,
                              Timestamp singleTime, Time timeInDay, String content) {
        Task task = new Task();
        task.setId(uniqueIDUtil.nextId());
        task.setUserId(userId);
        task.setBindType(bindType);

        if (bindTaskId > 0) {
            task.setBindTaskId(bindTaskId);
        }

        task.setType(type);

        if (startTime != null) {
            task.setStartTime(startTime);
        }
        if (endTime != null) {
            task.setEndTime(endTime);
        }

        if (singleTime != null) {
            task.setSingleTime(singleTime);
        }

        if (timeInDay != null) {
            task.setTimeInDay(timeInDay);
        }

        task.setContent(content);

        return taskMapper.insertSelective(task) == 1;
    }

}
