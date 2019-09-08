package com.ranze.schedule.pojo;

import lombok.ToString;

import java.util.Date;

@ToString
public class Task {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String userId;

    private Byte bindType;

    private String bindUser;

    private Long bindTaskId;

    private Byte type;

    private Date startDate;

    private Date endDate;

    private Byte excludeDateType;

    private Date singleDateTime;

    private Date timeInDayStart;

    private Date timeInDayEnd;

    private String content;

    private String addition;

    private Byte marked;

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

    public String getBindUser() {
        return bindUser;
    }

    public void setBindUser(String bindUser) {
        this.bindUser = bindUser == null ? null : bindUser.trim();
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Byte getExcludeDateType() {
        return excludeDateType;
    }

    public void setExcludeDateType(Byte excludeDateType) {
        this.excludeDateType = excludeDateType;
    }

    public Date getSingleDateTime() {
        return singleDateTime;
    }

    public void setSingleDateTime(Date singleDateTime) {
        this.singleDateTime = singleDateTime;
    }

    public Date getTimeInDayStart() {
        return timeInDayStart;
    }

    public void setTimeInDayStart(Date timeInDayStart) {
        this.timeInDayStart = timeInDayStart;
    }

    public Date getTimeInDayEnd() {
        return timeInDayEnd;
    }

    public void setTimeInDayEnd(Date timeInDayEnd) {
        this.timeInDayEnd = timeInDayEnd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition == null ? null : addition.trim();
    }

    public Byte getMarked() {
        return marked;
    }

    public void setMarked(Byte marked) {
        this.marked = marked;
    }
}