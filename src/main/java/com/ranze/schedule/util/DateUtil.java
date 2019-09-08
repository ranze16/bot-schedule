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

}
