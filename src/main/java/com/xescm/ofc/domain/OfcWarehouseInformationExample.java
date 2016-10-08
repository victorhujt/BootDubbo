package com.xescm.ofc.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OfcWarehouseInformationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OfcWarehouseInformationExample() {
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

        public Criteria andSupportNameIsNull() {
            addCriterion("support_name is null");
            return (Criteria) this;
        }

        public Criteria andSupportNameIsNotNull() {
            addCriterion("support_name is not null");
            return (Criteria) this;
        }

        public Criteria andSupportNameEqualTo(String value) {
            addCriterion("support_name =", value, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportNameNotEqualTo(String value) {
            addCriterion("support_name <>", value, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportNameGreaterThan(String value) {
            addCriterion("support_name >", value, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportNameGreaterThanOrEqualTo(String value) {
            addCriterion("support_name >=", value, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportNameLessThan(String value) {
            addCriterion("support_name <", value, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportNameLessThanOrEqualTo(String value) {
            addCriterion("support_name <=", value, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportNameLike(String value) {
            addCriterion("support_name like", value, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportNameNotLike(String value) {
            addCriterion("support_name not like", value, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportNameIn(List<String> values) {
            addCriterion("support_name in", values, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportNameNotIn(List<String> values) {
            addCriterion("support_name not in", values, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportNameBetween(String value1, String value2) {
            addCriterion("support_name between", value1, value2, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportNameNotBetween(String value1, String value2) {
            addCriterion("support_name not between", value1, value2, "supportName");
            return (Criteria) this;
        }

        public Criteria andSupportCodeIsNull() {
            addCriterion("support_code is null");
            return (Criteria) this;
        }

        public Criteria andSupportCodeIsNotNull() {
            addCriterion("support_code is not null");
            return (Criteria) this;
        }

        public Criteria andSupportCodeEqualTo(String value) {
            addCriterion("support_code =", value, "supportCode");
            return (Criteria) this;
        }

        public Criteria andSupportCodeNotEqualTo(String value) {
            addCriterion("support_code <>", value, "supportCode");
            return (Criteria) this;
        }

        public Criteria andSupportCodeGreaterThan(String value) {
            addCriterion("support_code >", value, "supportCode");
            return (Criteria) this;
        }

        public Criteria andSupportCodeGreaterThanOrEqualTo(String value) {
            addCriterion("support_code >=", value, "supportCode");
            return (Criteria) this;
        }

        public Criteria andSupportCodeLessThan(String value) {
            addCriterion("support_code <", value, "supportCode");
            return (Criteria) this;
        }

        public Criteria andSupportCodeLessThanOrEqualTo(String value) {
            addCriterion("support_code <=", value, "supportCode");
            return (Criteria) this;
        }

        public Criteria andSupportCodeLike(String value) {
            addCriterion("support_code like", value, "supportCode");
            return (Criteria) this;
        }

        public Criteria andSupportCodeNotLike(String value) {
            addCriterion("support_code not like", value, "supportCode");
            return (Criteria) this;
        }

        public Criteria andSupportCodeIn(List<String> values) {
            addCriterion("support_code in", values, "supportCode");
            return (Criteria) this;
        }

        public Criteria andSupportCodeNotIn(List<String> values) {
            addCriterion("support_code not in", values, "supportCode");
            return (Criteria) this;
        }

        public Criteria andSupportCodeBetween(String value1, String value2) {
            addCriterion("support_code between", value1, value2, "supportCode");
            return (Criteria) this;
        }

        public Criteria andSupportCodeNotBetween(String value1, String value2) {
            addCriterion("support_code not between", value1, value2, "supportCode");
            return (Criteria) this;
        }

        public Criteria andProvideTransportIsNull() {
            addCriterion("provide_transport is null");
            return (Criteria) this;
        }

        public Criteria andProvideTransportIsNotNull() {
            addCriterion("provide_transport is not null");
            return (Criteria) this;
        }

        public Criteria andProvideTransportEqualTo(Integer value) {
            addCriterion("provide_transport =", value, "provideTransport");
            return (Criteria) this;
        }

        public Criteria andProvideTransportNotEqualTo(Integer value) {
            addCriterion("provide_transport <>", value, "provideTransport");
            return (Criteria) this;
        }

        public Criteria andProvideTransportGreaterThan(Integer value) {
            addCriterion("provide_transport >", value, "provideTransport");
            return (Criteria) this;
        }

        public Criteria andProvideTransportGreaterThanOrEqualTo(Integer value) {
            addCriterion("provide_transport >=", value, "provideTransport");
            return (Criteria) this;
        }

        public Criteria andProvideTransportLessThan(Integer value) {
            addCriterion("provide_transport <", value, "provideTransport");
            return (Criteria) this;
        }

        public Criteria andProvideTransportLessThanOrEqualTo(Integer value) {
            addCriterion("provide_transport <=", value, "provideTransport");
            return (Criteria) this;
        }

        public Criteria andProvideTransportIn(List<Integer> values) {
            addCriterion("provide_transport in", values, "provideTransport");
            return (Criteria) this;
        }

        public Criteria andProvideTransportNotIn(List<Integer> values) {
            addCriterion("provide_transport not in", values, "provideTransport");
            return (Criteria) this;
        }

        public Criteria andProvideTransportBetween(Integer value1, Integer value2) {
            addCriterion("provide_transport between", value1, value2, "provideTransport");
            return (Criteria) this;
        }

        public Criteria andProvideTransportNotBetween(Integer value1, Integer value2) {
            addCriterion("provide_transport not between", value1, value2, "provideTransport");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeIsNull() {
            addCriterion("shipment_time is null");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeIsNotNull() {
            addCriterion("shipment_time is not null");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeEqualTo(Date value) {
            addCriterion("shipment_time =", value, "shipmentTime");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeNotEqualTo(Date value) {
            addCriterion("shipment_time <>", value, "shipmentTime");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeGreaterThan(Date value) {
            addCriterion("shipment_time >", value, "shipmentTime");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("shipment_time >=", value, "shipmentTime");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeLessThan(Date value) {
            addCriterion("shipment_time <", value, "shipmentTime");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeLessThanOrEqualTo(Date value) {
            addCriterion("shipment_time <=", value, "shipmentTime");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeIn(List<Date> values) {
            addCriterion("shipment_time in", values, "shipmentTime");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeNotIn(List<Date> values) {
            addCriterion("shipment_time not in", values, "shipmentTime");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeBetween(Date value1, Date value2) {
            addCriterion("shipment_time between", value1, value2, "shipmentTime");
            return (Criteria) this;
        }

        public Criteria andShipmentTimeNotBetween(Date value1, Date value2) {
            addCriterion("shipment_time not between", value1, value2, "shipmentTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIsNull() {
            addCriterion("arrive_time is null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIsNotNull() {
            addCriterion("arrive_time is not null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeEqualTo(Date value) {
            addCriterion("arrive_time =", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotEqualTo(Date value) {
            addCriterion("arrive_time <>", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThan(Date value) {
            addCriterion("arrive_time >", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("arrive_time >=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThan(Date value) {
            addCriterion("arrive_time <", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThanOrEqualTo(Date value) {
            addCriterion("arrive_time <=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIn(List<Date> values) {
            addCriterion("arrive_time in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotIn(List<Date> values) {
            addCriterion("arrive_time not in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeBetween(Date value1, Date value2) {
            addCriterion("arrive_time between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotBetween(Date value1, Date value2) {
            addCriterion("arrive_time not between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIsNull() {
            addCriterion("warehouse_name is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIsNotNull() {
            addCriterion("warehouse_name is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameEqualTo(String value) {
            addCriterion("warehouse_name =", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotEqualTo(String value) {
            addCriterion("warehouse_name <>", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameGreaterThan(String value) {
            addCriterion("warehouse_name >", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_name >=", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLessThan(String value) {
            addCriterion("warehouse_name <", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLessThanOrEqualTo(String value) {
            addCriterion("warehouse_name <=", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLike(String value) {
            addCriterion("warehouse_name like", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotLike(String value) {
            addCriterion("warehouse_name not like", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIn(List<String> values) {
            addCriterion("warehouse_name in", values, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotIn(List<String> values) {
            addCriterion("warehouse_name not in", values, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameBetween(String value1, String value2) {
            addCriterion("warehouse_name between", value1, value2, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotBetween(String value1, String value2) {
            addCriterion("warehouse_name not between", value1, value2, "warehouseName");
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