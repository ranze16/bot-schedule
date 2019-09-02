package com.ranze.schedule.pojo;

import java.util.Date;

public class Task {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String userId;

    private Byte bindType;

    private Long bindTaskId;

    private Byte type;

    private Date startTime;

    private Date endTime;

    private Date singleTime;

    private Date timeInDay;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Byte getBindType() {
        return bindType;
    }

    public void setBindType(Byte bindType) {
        this.bindType = bindType;
    }

    public Long getBindTaskId() {
        return bindTaskId;
    }

    public void setBindTaskId(Long bindTaskId) {
        this.bindTaskId = bindTaskId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getSingleTime() {
        return singleTime;
    }

    public void setSingleTime(Date singleTime) {
        this.singleTime = singleTime;
    }

    public Date getTimeInDay() {
        return timeInDay;
    }

    public void setTimeInDay(Date timeInDay) {
        this.timeInDay = timeInDay;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}