package com.ranze.schedule;

public final class Cons {
    public static final String DEFAULT_INTENT = "ai.dueros.common.default_intent";

    public static final String ATTRI_KEY_ACTION = "action";
    public static final String ATTRI_SET_NAME = "set_name";

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
    public static final long ONE_DAY_MILLIONS = ONE_HOUR_MILLIONS * 24;

    public static final String TASK_ONCE_STR = "task_once";
    public static final String TASK_LONG_STR = "task_long";

    public static final String TASK_STATE_EXPIRED = "已过期";
    public static final String TASK_STATE_NEED_CLOCK_IN = "可打卡";
    public static final String TASK_STATE_CLOCKED_IN = "已打卡";
    public static final String TASK_STATE_WAIT_CLOCKED_IN = "待打卡";


}
