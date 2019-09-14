package com.ranze.schedule;

public final class Cons {
    public static final String APP_NAME = "宝贝日程表";

    public static final String DEFAULT_INTENT = "ai.dueros.common.default_intent";

    public static final String ATTRI_KEY_ACTION = "action";
    public static final String ATTRI_SET_NAME = "set_name";
    public static final String ATTRI_CLOCK_IN = "clock_in";
    public static final String ATTRI_CRE_ONCE_TASK = "create_once_task";
    public static final String ATTRI_CRE_LONG_TASK = "create_long_task";
    public static final String ATTRI_ONCE_TASK_DATE = "once_task_date";
    public static final String ATTRI_SELECT_TIME_START = "select_time_start"; // 选择任务执行的开始时间
    public static final String ATTRI_SELECT_TIME_END = "select_time_end"; // 选择任务执行的结束时间
    public static final String ATTRI_BIND_TASK = "bind_task";
    public static final String ATTRI_KEY_LAST_TASK_ID = "task_id";
    public static final String ATTRI_TASK_CONTENT = "task_content";
    public static final String ATTRI_TASK_OWNER = "owner";

    public static final byte TASK_ONCE = 1; // 单次任务
    public static final byte TASK_INTERVAL = 2; // 一段时间内的任务
    public static final byte TASK_LONG = 3; // 长期任务

    public static final byte BIND_MASTER = 1;
    public static final byte BIND_ATTACHED = 2;

    public static final byte EXCLUDE_DATE_TYPE_NONE = 0; // 不排除任务日期
    public static final byte EXCLUDE_DATE_TYPE_KEEP_MONDAY = 1; // 只保留周一
    public static final byte EXCLUDE_DATE_TYPE_KEEP_TUESDAY = 2; // 只保留周二
    public static final byte EXCLUDE_DATE_TYPE_KEEP_WEDNESDAY = 3; // 只保留周三
    public static final byte EXCLUDE_DATE_TYPE_KEEP_THURSDAY = 4; // 只保留周四
    public static final byte EXCLUDE_DATE_TYPE_KEEP_FRIDAY = 5; // 只保留周五
    public static final byte EXCLUDE_DATE_TYPE_KEEP_SATURDAY = 6; // 只保留周六
    public static final byte EXCLUDE_DATE_TYPE_KEEP_SUNDAY = 7; // 只保留周日
    public static final byte EXCLUDE_DATE_TYPE_KEEP_WEEKDAY = 8; // 排除周末和节假日
    public static final byte EXCLUDE_DATE_TYPE_KEEP_WEEKEND = 9; // 排除周末和节假日

    public static final byte BYTE_1 = 1;

    public static final long HALF_HOUR_MILLIONS = 3600 / 2 * 1000;
    public static final long ONE_HOUR_MILLIONS = HALF_HOUR_MILLIONS * 2;
    public static final long EIGHT_HOUR_MILLIONS = ONE_HOUR_MILLIONS * 8;
    public static final long ONE_DAY_MILLIONS = ONE_HOUR_MILLIONS * 24;

    public static final String TASK_ONCE_STR = "task_once";
    public static final String TASK_LONG_STR = "task_long";

    public static final String TASK_STATE_EXPIRED = "已过期";
    public static final String TASK_STATE_NEED_CLOCK_IN = "可打卡";
    public static final String TASK_STATE_CLOCKED_IN = "已打卡";
    public static final String TASK_STATE_WAIT_CLOCKED_IN = "待打卡";

    public static final String INTENT_CLOCK_IN = "clock_in";
    public static final String INTENT_BIND_TASK = "bind_task";

    public static final String SLOT_BIND_TASK_TIME_IN_DAY_START = "sys.time1-bind";
    public static final String SLOT_BIND_TASK_TIME_IN_DAY_END = "sys.time2-bind";
    public static final String SLOT_BIND_TASK_CONTENT = "sys.wildcard-slot-bind";
    public static final String SLOT_BIND_TASK_OWNER = "sys.family-member";

    public static final String BABY = "宝贝";

    public static final int POINTS_ONE_CLOCK_IN = 10;
    public static final int POINTS_BONUSES_DOUBLE_CLOCK_IN = 10;

}
