package com.pi.nbcenter.device.entity.auto;

import java.util.ArrayList;
import java.util.List;

public class BasePartnerConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BasePartnerConfigExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
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

        public Criteria andPartnerConfigIsNull() {
            addCriterion("partner_config is null");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigIsNotNull() {
            addCriterion("partner_config is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigEqualTo(String value) {
            addCriterion("partner_config =", value, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigNotEqualTo(String value) {
            addCriterion("partner_config <>", value, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigGreaterThan(String value) {
            addCriterion("partner_config >", value, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigGreaterThanOrEqualTo(String value) {
            addCriterion("partner_config >=", value, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigLessThan(String value) {
            addCriterion("partner_config <", value, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigLessThanOrEqualTo(String value) {
            addCriterion("partner_config <=", value, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigLike(String value) {
            addCriterion("partner_config like", value, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigNotLike(String value) {
            addCriterion("partner_config not like", value, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigIn(List<String> values) {
            addCriterion("partner_config in", values, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigNotIn(List<String> values) {
            addCriterion("partner_config not in", values, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigBetween(String value1, String value2) {
            addCriterion("partner_config between", value1, value2, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigNotBetween(String value1, String value2) {
            addCriterion("partner_config not between", value1, value2, "partnerConfig");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueIsNull() {
            addCriterion("partner_config_value is null");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueIsNotNull() {
            addCriterion("partner_config_value is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueEqualTo(String value) {
            addCriterion("partner_config_value =", value, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueNotEqualTo(String value) {
            addCriterion("partner_config_value <>", value, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueGreaterThan(String value) {
            addCriterion("partner_config_value >", value, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueGreaterThanOrEqualTo(String value) {
            addCriterion("partner_config_value >=", value, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueLessThan(String value) {
            addCriterion("partner_config_value <", value, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueLessThanOrEqualTo(String value) {
            addCriterion("partner_config_value <=", value, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueLike(String value) {
            addCriterion("partner_config_value like", value, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueNotLike(String value) {
            addCriterion("partner_config_value not like", value, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueIn(List<String> values) {
            addCriterion("partner_config_value in", values, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueNotIn(List<String> values) {
            addCriterion("partner_config_value not in", values, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueBetween(String value1, String value2) {
            addCriterion("partner_config_value between", value1, value2, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andPartnerConfigValueNotBetween(String value1, String value2) {
            addCriterion("partner_config_value not between", value1, value2, "partnerConfigValue");
            return (Criteria) this;
        }

        public Criteria andConfigStateIsNull() {
            addCriterion("config_state is null");
            return (Criteria) this;
        }

        public Criteria andConfigStateIsNotNull() {
            addCriterion("config_state is not null");
            return (Criteria) this;
        }

        public Criteria andConfigStateEqualTo(Integer value) {
            addCriterion("config_state =", value, "configState");
            return (Criteria) this;
        }

        public Criteria andConfigStateNotEqualTo(Integer value) {
            addCriterion("config_state <>", value, "configState");
            return (Criteria) this;
        }

        public Criteria andConfigStateGreaterThan(Integer value) {
            addCriterion("config_state >", value, "configState");
            return (Criteria) this;
        }

        public Criteria andConfigStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("config_state >=", value, "configState");
            return (Criteria) this;
        }

        public Criteria andConfigStateLessThan(Integer value) {
            addCriterion("config_state <", value, "configState");
            return (Criteria) this;
        }

        public Criteria andConfigStateLessThanOrEqualTo(Integer value) {
            addCriterion("config_state <=", value, "configState");
            return (Criteria) this;
        }

        public Criteria andConfigStateIn(List<Integer> values) {
            addCriterion("config_state in", values, "configState");
            return (Criteria) this;
        }

        public Criteria andConfigStateNotIn(List<Integer> values) {
            addCriterion("config_state not in", values, "configState");
            return (Criteria) this;
        }

        public Criteria andConfigStateBetween(Integer value1, Integer value2) {
            addCriterion("config_state between", value1, value2, "configState");
            return (Criteria) this;
        }

        public Criteria andConfigStateNotBetween(Integer value1, Integer value2) {
            addCriterion("config_state not between", value1, value2, "configState");
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