package com.ranze.schedule.util;

import com.ranze.schedule.Cons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
public class DateUtil {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public boolean isMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    }

    public boolean isTuesDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
    }

    public boolean isWednesday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
    }

    public boolean isThursday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
    }


    public boolean isFriday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }

    public boolean isSaturday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }


    public boolean isSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public boolean isWeekend(Date date) {
        return isSaturday(date) || isSunday(date);
    }

    public boolean isWeekday(Date date) {
        return !isSaturday(date) && !isSunday(date);
    }

    public byte convertDateExclude(String exclude) {
        switch (exclude) {
            case "every_monday":
                return Cons.EXCLUDE_DATE_TYPE_KEEP_MONDAY;
            case "every_tuesday":
                return Cons.EXCLUDE_DATE_TYPE_KEEP_TUESDAY;
            case "every_wednesday":
                return Cons.EXCLUDE_DATE_TYPE_KEEP_MONDAY;
            case "every_thursday":
                return Cons.EXCLUDE_DATE_TYPE_KEEP_THURSDAY;
            case "every_friday":
                return Cons.EXCLUDE_DATE_TYPE_KEEP_FRIDAY;
            case "every_saturday":
                return Cons.EXCLUDE_DATE_TYPE_KEEP_FRIDAY;
            case "every_sunday":
                return Cons.EXCLUDE_DATE_TYPE_KEEP_SUNDAY;
            case "every_weekday":
                return Cons.EXCLUDE_DATE_TYPE_KEEP_WEEKDAY;
            case "every_weekend":
                return Cons.EXCLUDE_DATE_TYPE_KEEP_WEEKEND;
            case "everyday":
                return Cons.EXCLUDE_DATE_TYPE_NONE;
            default:
                return Cons.EXCLUDE_DATE_TYPE_NONE;
        }
    }

    public String convertDateExclude(byte exclude) {
        String excludeStr = "每天";
        switch (exclude) {
            case Cons.EXCLUDE_DATE_TYPE_KEEP_MONDAY:
                excludeStr = "每周一";
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_TUESDAY:
                excludeStr = "每周二";
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_WEDNESDAY:
                excludeStr = "每周三";
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_THURSDAY:
                excludeStr = "每周四";
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_FRIDAY:
                excludeStr = "每周五";
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_SATURDAY:
                excludeStr = "每周六";
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_SUNDAY:
                excludeStr = "每周日";
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_WEEKEND:
                excludeStr = "每周末";
                break;
            case Cons.EXCLUDE_DATE_TYPE_KEEP_WEEKDAY:
                excludeStr = "工作日";
                break;
            case Cons.EXCLUDE_DATE_TYPE_NONE:
                excludeStr = "每天";
                break;
            default:
                excludeStr = "每天";
                break;
        }
        return excludeStr;

    }

    public java.sql.Date today() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    public java.sql.Date tomorrow() {
        return new java.sql.Date(System.currentTimeMillis() + Cons.ONE_DAY_MILLIONS);
    }

    public java.sql.Date convertDate(String date) {
        try {
            return new java.sql.Date(dateFormat.parse(date).getTime());
        } catch (ParseException e) {
            log.info("Parse {} caused exception {}", date, e.getMessage());
        }
        return null;
    }

    public Time convertTime(String time) {
        try {
            return new Time(timeFormat.parse(time).getTime());
        } catch (ParseException e) {
            log.info("Parse {} caused exception {}", time, e.getMessage());
        }
        return null;
    }

    public String convertDate(Date date) {
        return timeFormat.format(date);
    }

    public int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

}
