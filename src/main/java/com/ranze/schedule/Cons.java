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

    public static final long HALF_HOUR_MILLIONS = 3600 / 2 * 1000;
    public static final long ONE_HOUR_MILLIONS = HALF_HOUR_MILLIONS * 2;
    public static final long ONE_DAY_MILLIONS = ONE_HOUR_MILLIONS * 24;

}
