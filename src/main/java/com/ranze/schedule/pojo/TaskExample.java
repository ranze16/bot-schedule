package com.ranze.schedule.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TaskExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        protected void addCriterionForJDBCTime(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value.getTime()), property);
        }

        protected void addCriterionForJDBCTime(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Time> timeList = new ArrayList<java.sql.Time>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                timeList.add(new java.sql.Time(iter.next().getTime()));
            }
            addCriterion(condition, timeList, property);
        }

        protected void addCriterionForJDBCTime(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andBindTypeIsNull() {
            addCriterion("bind_type is null");
            return (Criteria) this;
        }

        public Criteria andBindTypeIsNotNull() {
            addCriterion("bind_type is not null");
            return (Criteria) this;
        }

        public Criteria andBindTypeEqualTo(Byte value) {
            addCriterion("bind_type =", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeNotEqualTo(Byte value) {
            addCriterion("bind_type <>", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeGreaterThan(Byte value) {
            addCriterion("bind_type >", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("bind_type >=", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeLessThan(Byte value) {
            addCriterion("bind_type <", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeLessThanOrEqualTo(Byte value) {
            addCriterion("bind_type <=", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeIn(List<Byte> values) {
            addCriterion("bind_type in", values, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeNotIn(List<Byte> values) {
            addCriterion("bind_type not in", values, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeBetween(Byte value1, Byte value2) {
            addCriterion("bind_type between", value1, value2, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("bind_type not between", value1, value2, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindUserIsNull() {
            addCriterion("bind_user is null");
            return (Criteria) this;
        }

        public Criteria andBindUserIsNotNull() {
            addCriterion("bind_user is not null");
            return (Criteria) this;
        }

        public Criteria andBindUserEqualTo(String value) {
            addCriterion("bind_user =", value, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindUserNotEqualTo(String value) {
            addCriterion("bind_user <>", value, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindUserGreaterThan(String value) {
            addCriterion("bind_user >", value, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindUserGreaterThanOrEqualTo(String value) {
            addCriterion("bind_user >=", value, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindUserLessThan(String value) {
            addCriterion("bind_user <", value, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindUserLessThanOrEqualTo(String value) {
            addCriterion("bind_user <=", value, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindUserLike(String value) {
            addCriterion("bind_user like", value, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindUserNotLike(String value) {
            addCriterion("bind_user not like", value, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindUserIn(List<String> values) {
            addCriterion("bind_user in", values, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindUserNotIn(List<String> values) {
            addCriterion("bind_user not in", values, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindUserBetween(String value1, String value2) {
            addCriterion("bind_user between", value1, value2, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindUserNotBetween(String value1, String value2) {
            addCriterion("bind_user not between", value1, value2, "bindUser");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdIsNull() {
            addCriterion("bind_task_id is null");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdIsNotNull() {
            addCriterion("bind_task_id is not null");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdEqualTo(Long value) {
            addCriterion("bind_task_id =", value, "bindTaskId");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdNotEqualTo(Long value) {
            addCriterion("bind_task_id <>", value, "bindTaskId");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdGreaterThan(Long value) {
            addCriterion("bind_task_id >", value, "bindTaskId");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdGreaterThanOrEqualTo(Long value) {
            addCriterion("bind_task_id >=", value, "bindTaskId");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdLessThan(Long value) {
            addCriterion("bind_task_id <", value, "bindTaskId");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdLessThanOrEqualTo(Long value) {
            addCriterion("bind_task_id <=", value, "bindTaskId");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdIn(List<Long> values) {
            addCriterion("bind_task_id in", values, "bindTaskId");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdNotIn(List<Long> values) {
            addCriterion("bind_task_id not in", values, "bindTaskId");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdBetween(Long value1, Long value2) {
            addCriterion("bind_task_id between", value1, value2, "bindTaskId");
            return (Criteria) this;
        }

        public Criteria andBindTaskIdNotBetween(Long value1, Long value2) {
            addCriterion("bind_task_id not between", value1, value2, "bindTaskId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterionForJDBCDate("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeIsNull() {
            addCriterion("exclude_date_type is null");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeIsNotNull() {
            addCriterion("exclude_date_type is not null");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeEqualTo(Byte value) {
            addCriterion("exclude_date_type =", value, "excludeDateType");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeNotEqualTo(Byte value) {
            addCriterion("exclude_date_type <>", value, "excludeDateType");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeGreaterThan(Byte value) {
            addCriterion("exclude_date_type >", value, "excludeDateType");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("exclude_date_type >=", value, "excludeDateType");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeLessThan(Byte value) {
            addCriterion("exclude_date_type <", value, "excludeDateType");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeLessThanOrEqualTo(Byte value) {
            addCriterion("exclude_date_type <=", value, "excludeDateType");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeIn(List<Byte> values) {
            addCriterion("exclude_date_type in", values, "excludeDateType");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeNotIn(List<Byte> values) {
            addCriterion("exclude_date_type not in", values, "excludeDateType");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeBetween(Byte value1, Byte value2) {
            addCriterion("exclude_date_type between", value1, value2, "excludeDateType");
            return (Criteria) this;
        }

        public Criteria andExcludeDateTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("exclude_date_type not between", value1, value2, "excludeDateType");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeIsNull() {
            addCriterion("single_date_time is null");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeIsNotNull() {
            addCriterion("single_date_time is not null");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeEqualTo(Date value) {
            addCriterionForJDBCDate("single_date_time =", value, "singleDateTime");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("single_date_time <>", value, "singleDateTime");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("single_date_time >", value, "singleDateTime");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("single_date_time >=", value, "singleDateTime");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeLessThan(Date value) {
            addCriterionForJDBCDate("single_date_time <", value, "singleDateTime");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("single_date_time <=", value, "singleDateTime");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeIn(List<Date> values) {
            addCriterionForJDBCDate("single_date_time in", values, "singleDateTime");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("single_date_time not in", values, "singleDateTime");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("single_date_time between", value1, value2, "singleDateTime");
            return (Criteria) this;
        }

        public Criteria andSingleDateTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("single_date_time not between", value1, value2, "singleDateTime");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartIsNull() {
            addCriterion("time_in_day_start is null");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartIsNotNull() {
            addCriterion("time_in_day_start is not null");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartEqualTo(Date value) {
            addCriterionForJDBCTime("time_in_day_start =", value, "timeInDayStart");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartNotEqualTo(Date value) {
            addCriterionForJDBCTime("time_in_day_start <>", value, "timeInDayStart");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartGreaterThan(Date value) {
            addCriterionForJDBCTime("time_in_day_start >", value, "timeInDayStart");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("time_in_day_start >=", value, "timeInDayStart");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartLessThan(Date value) {
            addCriterionForJDBCTime("time_in_day_start <", value, "timeInDayStart");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("time_in_day_start <=", value, "timeInDayStart");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartIn(List<Date> values) {
            addCriterionForJDBCTime("time_in_day_start in", values, "timeInDayStart");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartNotIn(List<Date> values) {
            addCriterionForJDBCTime("time_in_day_start not in", values, "timeInDayStart");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("time_in_day_start between", value1, value2, "timeInDayStart");
            return (Criteria) this;
        }

        public Criteria andTimeInDayStartNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("time_in_day_start not between", value1, value2, "timeInDayStart");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndIsNull() {
            addCriterion("time_in_day_end is null");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndIsNotNull() {
            addCriterion("time_in_day_end is not null");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndEqualTo(Date value) {
            addCriterionForJDBCTime("time_in_day_end =", value, "timeInDayEnd");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndNotEqualTo(Date value) {
            addCriterionForJDBCTime("time_in_day_end <>", value, "timeInDayEnd");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndGreaterThan(Date value) {
            addCriterionForJDBCTime("time_in_day_end >", value, "timeInDayEnd");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("time_in_day_end >=", value, "timeInDayEnd");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndLessThan(Date value) {
            addCriterionForJDBCTime("time_in_day_end <", value, "timeInDayEnd");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("time_in_day_end <=", value, "timeInDayEnd");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndIn(List<Date> values) {
            addCriterionForJDBCTime("time_in_day_end in", values, "timeInDayEnd");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndNotIn(List<Date> values) {
            addCriterionForJDBCTime("time_in_day_end not in", values, "timeInDayEnd");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("time_in_day_end between", value1, value2, "timeInDayEnd");
            return (Criteria) this;
        }

        public Criteria andTimeInDayEndNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("time_in_day_end not between", value1, value2, "timeInDayEnd");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andAdditionIsNull() {
            addCriterion("addition is null");
            return (Criteria) this;
        }

        public Criteria andAdditionIsNotNull() {
            addCriterion("addition is not null");
            return (Criteria) this;
        }

        public Criteria andAdditionEqualTo(String value) {
            addCriterion("addition =", value, "addition");
            return (Criteria) this;
        }

        public Criteria andAdditionNotEqualTo(String value) {
            addCriterion("addition <>", value, "addition");
            return (Criteria) this;
        }

        public Criteria andAdditionGreaterThan(String value) {
            addCriterion("addition >", value, "addition");
            return (Criteria) this;
        }

        public Criteria andAdditionGreaterThanOrEqualTo(String value) {
            addCriterion("addition >=", value, "addition");
            return (Criteria) this;
        }

        public Criteria andAdditionLessThan(String value) {
            addCriterion("addition <", value, "addition");
            return (Criteria) this;
        }

        public Criteria andAdditionLessThanOrEqualTo(String value) {
            addCriterion("addition <=", value, "addition");
            return (Criteria) this;
        }

        public Criteria andAdditionLike(String value) {
            addCriterion("addition like", value, "addition");
            return (Criteria) this;
        }

        public Criteria andAdditionNotLike(String value) {
            addCriterion("addition not like", value, "addition");
            return (Criteria) this;
        }

        public Criteria andAdditionIn(List<String> values) {
            addCriterion("addition in", values, "addition");
            return (Criteria) this;
        }

        public Criteria andAdditionNotIn(List<String> values) {
            addCriterion("addition not in", values, "addition");
            return (Criteria) this;
        }

        public Criteria andAdditionBetween(String value1, String value2) {
            addCriterion("addition between", value1, value2, "addition");
            return (Criteria) this;
        }

        public Criteria andAdditionNotBetween(String value1, String value2) {
            addCriterion("addition not between", value1, value2, "addition");
            return (Criteria) this;
        }

        public Criteria andMarkedIsNull() {
            addCriterion("is_marked is null");
            return (Criteria) this;
        }

        public Criteria andMarkedIsNotNull() {
            addCriterion("is_marked is not null");
            return (Criteria) this;
        }

        public Criteria andMarkedEqualTo(Byte value) {
            addCriterion("is_marked =", value, "marked");
            return (Criteria) this;
        }

        public Criteria andMarkedNotEqualTo(Byte value) {
            addCriterion("is_marked <>", value, "marked");
            return (Criteria) this;
        }

        public Criteria andMarkedGreaterThan(Byte value) {
            addCriterion("is_marked >", value, "marked");
            return (Criteria) this;
        }

        public Criteria andMarkedGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_marked >=", value, "marked");
            return (Criteria) this;
        }

        public Criteria andMarkedLessThan(Byte value) {
            addCriterion("is_marked <", value, "marked");
            return (Criteria) this;
        }

        public Criteria andMarkedLessThanOrEqualTo(Byte value) {
            addCriterion("is_marked <=", value, "marked");
            return (Criteria) this;
        }

        public Criteria andMarkedIn(List<Byte> values) {
            addCriterion("is_marked in", values, "marked");
            return (Criteria) this;
        }

        public Criteria andMarkedNotIn(List<Byte> values) {
            addCriterion("is_marked not in", values, "marked");
            return (Criteria) this;
        }

        public Criteria andMarkedBetween(Byte value1, Byte value2) {
            addCriterion("is_marked between", value1, value2, "marked");
            return (Criteria) this;
        }

        public Criteria andMarkedNotBetween(Byte value1, Byte value2) {
            addCriterion("is_marked not between", value1, value2, "marked");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}