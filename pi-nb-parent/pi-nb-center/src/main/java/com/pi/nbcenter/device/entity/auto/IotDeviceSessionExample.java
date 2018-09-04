package com.pi.nbcenter.device.entity.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IotDeviceSessionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IotDeviceSessionExample() {
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

        public Criteria andIotDevIdIsNull() {
            addCriterion("iot_dev_id is null");
            return (Criteria) this;
        }

        public Criteria andIotDevIdIsNotNull() {
            addCriterion("iot_dev_id is not null");
            return (Criteria) this;
        }

        public Criteria andIotDevIdEqualTo(String value) {
            addCriterion("iot_dev_id =", value, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIdNotEqualTo(String value) {
            addCriterion("iot_dev_id <>", value, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIdGreaterThan(String value) {
            addCriterion("iot_dev_id >", value, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIdGreaterThanOrEqualTo(String value) {
            addCriterion("iot_dev_id >=", value, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIdLessThan(String value) {
            addCriterion("iot_dev_id <", value, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIdLessThanOrEqualTo(String value) {
            addCriterion("iot_dev_id <=", value, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIdLike(String value) {
            addCriterion("iot_dev_id like", value, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIdNotLike(String value) {
            addCriterion("iot_dev_id not like", value, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIdIn(List<String> values) {
            addCriterion("iot_dev_id in", values, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIdNotIn(List<String> values) {
            addCriterion("iot_dev_id not in", values, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIdBetween(String value1, String value2) {
            addCriterion("iot_dev_id between", value1, value2, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIdNotBetween(String value1, String value2) {
            addCriterion("iot_dev_id not between", value1, value2, "iotDevId");
            return (Criteria) this;
        }

        public Criteria andIotDevIpIsNull() {
            addCriterion("iot_dev_ip is null");
            return (Criteria) this;
        }

        public Criteria andIotDevIpIsNotNull() {
            addCriterion("iot_dev_ip is not null");
            return (Criteria) this;
        }

        public Criteria andIotDevIpEqualTo(String value) {
            addCriterion("iot_dev_ip =", value, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevIpNotEqualTo(String value) {
            addCriterion("iot_dev_ip <>", value, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevIpGreaterThan(String value) {
            addCriterion("iot_dev_ip >", value, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevIpGreaterThanOrEqualTo(String value) {
            addCriterion("iot_dev_ip >=", value, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevIpLessThan(String value) {
            addCriterion("iot_dev_ip <", value, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevIpLessThanOrEqualTo(String value) {
            addCriterion("iot_dev_ip <=", value, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevIpLike(String value) {
            addCriterion("iot_dev_ip like", value, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevIpNotLike(String value) {
            addCriterion("iot_dev_ip not like", value, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevIpIn(List<String> values) {
            addCriterion("iot_dev_ip in", values, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevIpNotIn(List<String> values) {
            addCriterion("iot_dev_ip not in", values, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevIpBetween(String value1, String value2) {
            addCriterion("iot_dev_ip between", value1, value2, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevIpNotBetween(String value1, String value2) {
            addCriterion("iot_dev_ip not between", value1, value2, "iotDevIp");
            return (Criteria) this;
        }

        public Criteria andIotDevPortIsNull() {
            addCriterion("iot_dev_port is null");
            return (Criteria) this;
        }

        public Criteria andIotDevPortIsNotNull() {
            addCriterion("iot_dev_port is not null");
            return (Criteria) this;
        }

        public Criteria andIotDevPortEqualTo(Integer value) {
            addCriterion("iot_dev_port =", value, "iotDevPort");
            return (Criteria) this;
        }

        public Criteria andIotDevPortNotEqualTo(Integer value) {
            addCriterion("iot_dev_port <>", value, "iotDevPort");
            return (Criteria) this;
        }

        public Criteria andIotDevPortGreaterThan(Integer value) {
            addCriterion("iot_dev_port >", value, "iotDevPort");
            return (Criteria) this;
        }

        public Criteria andIotDevPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_port >=", value, "iotDevPort");
            return (Criteria) this;
        }

        public Criteria andIotDevPortLessThan(Integer value) {
            addCriterion("iot_dev_port <", value, "iotDevPort");
            return (Criteria) this;
        }

        public Criteria andIotDevPortLessThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_port <=", value, "iotDevPort");
            return (Criteria) this;
        }

        public Criteria andIotDevPortIn(List<Integer> values) {
            addCriterion("iot_dev_port in", values, "iotDevPort");
            return (Criteria) this;
        }

        public Criteria andIotDevPortNotIn(List<Integer> values) {
            addCriterion("iot_dev_port not in", values, "iotDevPort");
            return (Criteria) this;
        }

        public Criteria andIotDevPortBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_port between", value1, value2, "iotDevPort");
            return (Criteria) this;
        }

        public Criteria andIotDevPortNotBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_port not between", value1, value2, "iotDevPort");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeIsNull() {
            addCriterion("latest_active_time is null");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeIsNotNull() {
            addCriterion("latest_active_time is not null");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeEqualTo(Date value) {
            addCriterion("latest_active_time =", value, "latestActiveTime");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeNotEqualTo(Date value) {
            addCriterion("latest_active_time <>", value, "latestActiveTime");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeGreaterThan(Date value) {
            addCriterion("latest_active_time >", value, "latestActiveTime");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("latest_active_time >=", value, "latestActiveTime");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeLessThan(Date value) {
            addCriterion("latest_active_time <", value, "latestActiveTime");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeLessThanOrEqualTo(Date value) {
            addCriterion("latest_active_time <=", value, "latestActiveTime");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeIn(List<Date> values) {
            addCriterion("latest_active_time in", values, "latestActiveTime");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeNotIn(List<Date> values) {
            addCriterion("latest_active_time not in", values, "latestActiveTime");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeBetween(Date value1, Date value2) {
            addCriterion("latest_active_time between", value1, value2, "latestActiveTime");
            return (Criteria) this;
        }

        public Criteria andLatestActiveTimeNotBetween(Date value1, Date value2) {
            addCriterion("latest_active_time not between", value1, value2, "latestActiveTime");
            return (Criteria) this;
        }

        public Criteria andIotDevStateIsNull() {
            addCriterion("iot_dev_state is null");
            return (Criteria) this;
        }

        public Criteria andIotDevStateIsNotNull() {
            addCriterion("iot_dev_state is not null");
            return (Criteria) this;
        }

        public Criteria andIotDevStateEqualTo(Integer value) {
            addCriterion("iot_dev_state =", value, "iotDevState");
            return (Criteria) this;
        }

        public Criteria andIotDevStateNotEqualTo(Integer value) {
            addCriterion("iot_dev_state <>", value, "iotDevState");
            return (Criteria) this;
        }

        public Criteria andIotDevStateGreaterThan(Integer value) {
            addCriterion("iot_dev_state >", value, "iotDevState");
            return (Criteria) this;
        }

        public Criteria andIotDevStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_state >=", value, "iotDevState");
            return (Criteria) this;
        }

        public Criteria andIotDevStateLessThan(Integer value) {
            addCriterion("iot_dev_state <", value, "iotDevState");
            return (Criteria) this;
        }

        public Criteria andIotDevStateLessThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_state <=", value, "iotDevState");
            return (Criteria) this;
        }

        public Criteria andIotDevStateIn(List<Integer> values) {
            addCriterion("iot_dev_state in", values, "iotDevState");
            return (Criteria) this;
        }

        public Criteria andIotDevStateNotIn(List<Integer> values) {
            addCriterion("iot_dev_state not in", values, "iotDevState");
            return (Criteria) this;
        }

        public Criteria andIotDevStateBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_state between", value1, value2, "iotDevState");
            return (Criteria) this;
        }

        public Criteria andIotDevStateNotBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_state not between", value1, value2, "iotDevState");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryIsNull() {
            addCriterion("iot_dev_battery is null");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryIsNotNull() {
            addCriterion("iot_dev_battery is not null");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryEqualTo(Integer value) {
            addCriterion("iot_dev_battery =", value, "iotDevBattery");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryNotEqualTo(Integer value) {
            addCriterion("iot_dev_battery <>", value, "iotDevBattery");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryGreaterThan(Integer value) {
            addCriterion("iot_dev_battery >", value, "iotDevBattery");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryGreaterThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_battery >=", value, "iotDevBattery");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryLessThan(Integer value) {
            addCriterion("iot_dev_battery <", value, "iotDevBattery");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryLessThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_battery <=", value, "iotDevBattery");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryIn(List<Integer> values) {
            addCriterion("iot_dev_battery in", values, "iotDevBattery");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryNotIn(List<Integer> values) {
            addCriterion("iot_dev_battery not in", values, "iotDevBattery");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_battery between", value1, value2, "iotDevBattery");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryNotBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_battery not between", value1, value2, "iotDevBattery");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentIsNull() {
            addCriterion("iot_dev_battery_percent is null");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentIsNotNull() {
            addCriterion("iot_dev_battery_percent is not null");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentEqualTo(Integer value) {
            addCriterion("iot_dev_battery_percent =", value, "iotDevBatteryPercent");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentNotEqualTo(Integer value) {
            addCriterion("iot_dev_battery_percent <>", value, "iotDevBatteryPercent");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentGreaterThan(Integer value) {
            addCriterion("iot_dev_battery_percent >", value, "iotDevBatteryPercent");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentGreaterThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_battery_percent >=", value, "iotDevBatteryPercent");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentLessThan(Integer value) {
            addCriterion("iot_dev_battery_percent <", value, "iotDevBatteryPercent");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentLessThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_battery_percent <=", value, "iotDevBatteryPercent");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentIn(List<Integer> values) {
            addCriterion("iot_dev_battery_percent in", values, "iotDevBatteryPercent");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentNotIn(List<Integer> values) {
            addCriterion("iot_dev_battery_percent not in", values, "iotDevBatteryPercent");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_battery_percent between", value1, value2, "iotDevBatteryPercent");
            return (Criteria) this;
        }

        public Criteria andIotDevBatteryPercentNotBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_battery_percent not between", value1, value2, "iotDevBatteryPercent");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiIsNull() {
            addCriterion("iot_dev_rssi is null");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiIsNotNull() {
            addCriterion("iot_dev_rssi is not null");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiEqualTo(Integer value) {
            addCriterion("iot_dev_rssi =", value, "iotDevRssi");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiNotEqualTo(Integer value) {
            addCriterion("iot_dev_rssi <>", value, "iotDevRssi");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiGreaterThan(Integer value) {
            addCriterion("iot_dev_rssi >", value, "iotDevRssi");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiGreaterThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_rssi >=", value, "iotDevRssi");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiLessThan(Integer value) {
            addCriterion("iot_dev_rssi <", value, "iotDevRssi");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiLessThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_rssi <=", value, "iotDevRssi");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiIn(List<Integer> values) {
            addCriterion("iot_dev_rssi in", values, "iotDevRssi");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiNotIn(List<Integer> values) {
            addCriterion("iot_dev_rssi not in", values, "iotDevRssi");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_rssi between", value1, value2, "iotDevRssi");
            return (Criteria) this;
        }

        public Criteria andIotDevRssiNotBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_rssi not between", value1, value2, "iotDevRssi");
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