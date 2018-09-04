package com.pi.nbcenter.device.entity.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IotDeviceInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IotDeviceInfoExample() {
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

        public Criteria andNbDevIdIsNull() {
            addCriterion("nb_dev_id is null");
            return (Criteria) this;
        }

        public Criteria andNbDevIdIsNotNull() {
            addCriterion("nb_dev_id is not null");
            return (Criteria) this;
        }

        public Criteria andNbDevIdEqualTo(String value) {
            addCriterion("nb_dev_id =", value, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andNbDevIdNotEqualTo(String value) {
            addCriterion("nb_dev_id <>", value, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andNbDevIdGreaterThan(String value) {
            addCriterion("nb_dev_id >", value, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andNbDevIdGreaterThanOrEqualTo(String value) {
            addCriterion("nb_dev_id >=", value, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andNbDevIdLessThan(String value) {
            addCriterion("nb_dev_id <", value, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andNbDevIdLessThanOrEqualTo(String value) {
            addCriterion("nb_dev_id <=", value, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andNbDevIdLike(String value) {
            addCriterion("nb_dev_id like", value, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andNbDevIdNotLike(String value) {
            addCriterion("nb_dev_id not like", value, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andNbDevIdIn(List<String> values) {
            addCriterion("nb_dev_id in", values, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andNbDevIdNotIn(List<String> values) {
            addCriterion("nb_dev_id not in", values, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andNbDevIdBetween(String value1, String value2) {
            addCriterion("nb_dev_id between", value1, value2, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andNbDevIdNotBetween(String value1, String value2) {
            addCriterion("nb_dev_id not between", value1, value2, "nbDevId");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeIsNull() {
            addCriterion("partner_code is null");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeIsNotNull() {
            addCriterion("partner_code is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeEqualTo(Long value) {
            addCriterion("partner_code =", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeNotEqualTo(Long value) {
            addCriterion("partner_code <>", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeGreaterThan(Long value) {
            addCriterion("partner_code >", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeGreaterThanOrEqualTo(Long value) {
            addCriterion("partner_code >=", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeLessThan(Long value) {
            addCriterion("partner_code <", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeLessThanOrEqualTo(Long value) {
            addCriterion("partner_code <=", value, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeIn(List<Long> values) {
            addCriterion("partner_code in", values, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeNotIn(List<Long> values) {
            addCriterion("partner_code not in", values, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeBetween(Long value1, Long value2) {
            addCriterion("partner_code between", value1, value2, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andPartnerCodeNotBetween(Long value1, Long value2) {
            addCriterion("partner_code not between", value1, value2, "partnerCode");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionIsNull() {
            addCriterion("iot_protocol_version is null");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionIsNotNull() {
            addCriterion("iot_protocol_version is not null");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionEqualTo(Integer value) {
            addCriterion("iot_protocol_version =", value, "iotProtocolVersion");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionNotEqualTo(Integer value) {
            addCriterion("iot_protocol_version <>", value, "iotProtocolVersion");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionGreaterThan(Integer value) {
            addCriterion("iot_protocol_version >", value, "iotProtocolVersion");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("iot_protocol_version >=", value, "iotProtocolVersion");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionLessThan(Integer value) {
            addCriterion("iot_protocol_version <", value, "iotProtocolVersion");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionLessThanOrEqualTo(Integer value) {
            addCriterion("iot_protocol_version <=", value, "iotProtocolVersion");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionIn(List<Integer> values) {
            addCriterion("iot_protocol_version in", values, "iotProtocolVersion");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionNotIn(List<Integer> values) {
            addCriterion("iot_protocol_version not in", values, "iotProtocolVersion");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionBetween(Integer value1, Integer value2) {
            addCriterion("iot_protocol_version between", value1, value2, "iotProtocolVersion");
            return (Criteria) this;
        }

        public Criteria andIotProtocolVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("iot_protocol_version not between", value1, value2, "iotProtocolVersion");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiIsNull() {
            addCriterion("iot_dev_imsi is null");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiIsNotNull() {
            addCriterion("iot_dev_imsi is not null");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiEqualTo(String value) {
            addCriterion("iot_dev_imsi =", value, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiNotEqualTo(String value) {
            addCriterion("iot_dev_imsi <>", value, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiGreaterThan(String value) {
            addCriterion("iot_dev_imsi >", value, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiGreaterThanOrEqualTo(String value) {
            addCriterion("iot_dev_imsi >=", value, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiLessThan(String value) {
            addCriterion("iot_dev_imsi <", value, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiLessThanOrEqualTo(String value) {
            addCriterion("iot_dev_imsi <=", value, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiLike(String value) {
            addCriterion("iot_dev_imsi like", value, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiNotLike(String value) {
            addCriterion("iot_dev_imsi not like", value, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiIn(List<String> values) {
            addCriterion("iot_dev_imsi in", values, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiNotIn(List<String> values) {
            addCriterion("iot_dev_imsi not in", values, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiBetween(String value1, String value2) {
            addCriterion("iot_dev_imsi between", value1, value2, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImsiNotBetween(String value1, String value2) {
            addCriterion("iot_dev_imsi not between", value1, value2, "iotDevImsi");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiIsNull() {
            addCriterion("iot_dev_imei is null");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiIsNotNull() {
            addCriterion("iot_dev_imei is not null");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiEqualTo(String value) {
            addCriterion("iot_dev_imei =", value, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiNotEqualTo(String value) {
            addCriterion("iot_dev_imei <>", value, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiGreaterThan(String value) {
            addCriterion("iot_dev_imei >", value, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiGreaterThanOrEqualTo(String value) {
            addCriterion("iot_dev_imei >=", value, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiLessThan(String value) {
            addCriterion("iot_dev_imei <", value, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiLessThanOrEqualTo(String value) {
            addCriterion("iot_dev_imei <=", value, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiLike(String value) {
            addCriterion("iot_dev_imei like", value, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiNotLike(String value) {
            addCriterion("iot_dev_imei not like", value, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiIn(List<String> values) {
            addCriterion("iot_dev_imei in", values, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiNotIn(List<String> values) {
            addCriterion("iot_dev_imei not in", values, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiBetween(String value1, String value2) {
            addCriterion("iot_dev_imei between", value1, value2, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevImeiNotBetween(String value1, String value2) {
            addCriterion("iot_dev_imei not between", value1, value2, "iotDevImei");
            return (Criteria) this;
        }

        public Criteria andIotDevCertIsNull() {
            addCriterion("iot_dev_cert is null");
            return (Criteria) this;
        }

        public Criteria andIotDevCertIsNotNull() {
            addCriterion("iot_dev_cert is not null");
            return (Criteria) this;
        }

        public Criteria andIotDevCertEqualTo(String value) {
            addCriterion("iot_dev_cert =", value, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevCertNotEqualTo(String value) {
            addCriterion("iot_dev_cert <>", value, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevCertGreaterThan(String value) {
            addCriterion("iot_dev_cert >", value, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevCertGreaterThanOrEqualTo(String value) {
            addCriterion("iot_dev_cert >=", value, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevCertLessThan(String value) {
            addCriterion("iot_dev_cert <", value, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevCertLessThanOrEqualTo(String value) {
            addCriterion("iot_dev_cert <=", value, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevCertLike(String value) {
            addCriterion("iot_dev_cert like", value, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevCertNotLike(String value) {
            addCriterion("iot_dev_cert not like", value, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevCertIn(List<String> values) {
            addCriterion("iot_dev_cert in", values, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevCertNotIn(List<String> values) {
            addCriterion("iot_dev_cert not in", values, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevCertBetween(String value1, String value2) {
            addCriterion("iot_dev_cert between", value1, value2, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevCertNotBetween(String value1, String value2) {
            addCriterion("iot_dev_cert not between", value1, value2, "iotDevCert");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeIsNull() {
            addCriterion("iot_dev_regcode is null");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeIsNotNull() {
            addCriterion("iot_dev_regcode is not null");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeEqualTo(Integer value) {
            addCriterion("iot_dev_regcode =", value, "iotDevRegcode");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeNotEqualTo(Integer value) {
            addCriterion("iot_dev_regcode <>", value, "iotDevRegcode");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeGreaterThan(Integer value) {
            addCriterion("iot_dev_regcode >", value, "iotDevRegcode");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_regcode >=", value, "iotDevRegcode");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeLessThan(Integer value) {
            addCriterion("iot_dev_regcode <", value, "iotDevRegcode");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeLessThanOrEqualTo(Integer value) {
            addCriterion("iot_dev_regcode <=", value, "iotDevRegcode");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeIn(List<Integer> values) {
            addCriterion("iot_dev_regcode in", values, "iotDevRegcode");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeNotIn(List<Integer> values) {
            addCriterion("iot_dev_regcode not in", values, "iotDevRegcode");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_regcode between", value1, value2, "iotDevRegcode");
            return (Criteria) this;
        }

        public Criteria andIotDevRegcodeNotBetween(Integer value1, Integer value2) {
            addCriterion("iot_dev_regcode not between", value1, value2, "iotDevRegcode");
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

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Byte value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Byte value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Byte value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Byte value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Byte value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Byte> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Byte> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Byte value1, Byte value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Byte value1, Byte value2) {
            addCriterion("state not between", value1, value2, "state");
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