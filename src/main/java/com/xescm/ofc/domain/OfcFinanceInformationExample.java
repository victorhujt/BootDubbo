package com.xescm.ofc.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OfcFinanceInformationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OfcFinanceInformationExample() {
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

        public Criteria andServiceChargeIsNull() {
            addCriterion("service_charge is null");
            return (Criteria) this;
        }

        public Criteria andServiceChargeIsNotNull() {
            addCriterion("service_charge is not null");
            return (Criteria) this;
        }

        public Criteria andServiceChargeEqualTo(BigDecimal value) {
            addCriterion("service_charge =", value, "serviceCharge");
            return (Criteria) this;
        }

        public Criteria andServiceChargeNotEqualTo(BigDecimal value) {
            addCriterion("service_charge <>", value, "serviceCharge");
            return (Criteria) this;
        }

        public Criteria andServiceChargeGreaterThan(BigDecimal value) {
            addCriterion("service_charge >", value, "serviceCharge");
            return (Criteria) this;
        }

        public Criteria andServiceChargeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("service_charge >=", value, "serviceCharge");
            return (Criteria) this;
        }

        public Criteria andServiceChargeLessThan(BigDecimal value) {
            addCriterion("service_charge <", value, "serviceCharge");
            return (Criteria) this;
        }

        public Criteria andServiceChargeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("service_charge <=", value, "serviceCharge");
            return (Criteria) this;
        }

        public Criteria andServiceChargeIn(List<BigDecimal> values) {
            addCriterion("service_charge in", values, "serviceCharge");
            return (Criteria) this;
        }

        public Criteria andServiceChargeNotIn(List<BigDecimal> values) {
            addCriterion("service_charge not in", values, "serviceCharge");
            return (Criteria) this;
        }

        public Criteria andServiceChargeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_charge between", value1, value2, "serviceCharge");
            return (Criteria) this;
        }

        public Criteria andServiceChargeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_charge not between", value1, value2, "serviceCharge");
            return (Criteria) this;
        }

        public Criteria andOrderAmountIsNull() {
            addCriterion("order_amount is null");
            return (Criteria) this;
        }

        public Criteria andOrderAmountIsNotNull() {
            addCriterion("order_amount is not null");
            return (Criteria) this;
        }

        public Criteria andOrderAmountEqualTo(BigDecimal value) {
            addCriterion("order_amount =", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotEqualTo(BigDecimal value) {
            addCriterion("order_amount <>", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountGreaterThan(BigDecimal value) {
            addCriterion("order_amount >", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_amount >=", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountLessThan(BigDecimal value) {
            addCriterion("order_amount <", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_amount <=", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountIn(List<BigDecimal> values) {
            addCriterion("order_amount in", values, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotIn(List<BigDecimal> values) {
            addCriterion("order_amount not in", values, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_amount between", value1, value2, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_amount not between", value1, value2, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountIsNull() {
            addCriterion("loan_amount is null");
            return (Criteria) this;
        }

        public Criteria andLoanAmountIsNotNull() {
            addCriterion("loan_amount is not null");
            return (Criteria) this;
        }

        public Criteria andLoanAmountEqualTo(BigDecimal value) {
            addCriterion("loan_amount =", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountNotEqualTo(BigDecimal value) {
            addCriterion("loan_amount <>", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountGreaterThan(BigDecimal value) {
            addCriterion("loan_amount >", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_amount >=", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountLessThan(BigDecimal value) {
            addCriterion("loan_amount <", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_amount <=", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountIn(List<BigDecimal> values) {
            addCriterion("loan_amount in", values, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountNotIn(List<BigDecimal> values) {
            addCriterion("loan_amount not in", values, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_amount between", value1, value2, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_amount not between", value1, value2, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountIsNull() {
            addCriterion("collect_loan_amount is null");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountIsNotNull() {
            addCriterion("collect_loan_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountEqualTo(BigDecimal value) {
            addCriterion("collect_loan_amount =", value, "collectLoanAmount");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountNotEqualTo(BigDecimal value) {
            addCriterion("collect_loan_amount <>", value, "collectLoanAmount");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountGreaterThan(BigDecimal value) {
            addCriterion("collect_loan_amount >", value, "collectLoanAmount");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("collect_loan_amount >=", value, "collectLoanAmount");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountLessThan(BigDecimal value) {
            addCriterion("collect_loan_amount <", value, "collectLoanAmount");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("collect_loan_amount <=", value, "collectLoanAmount");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountIn(List<BigDecimal> values) {
            addCriterion("collect_loan_amount in", values, "collectLoanAmount");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountNotIn(List<BigDecimal> values) {
            addCriterion("collect_loan_amount not in", values, "collectLoanAmount");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("collect_loan_amount between", value1, value2, "collectLoanAmount");
            return (Criteria) this;
        }

        public Criteria andCollectLoanAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("collect_loan_amount not between", value1, value2, "collectLoanAmount");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeIsNull() {
            addCriterion("collect_service_charge is null");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeIsNotNull() {
            addCriterion("collect_service_charge is not null");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeEqualTo(BigDecimal value) {
            addCriterion("collect_service_charge =", value, "collectServiceCharge");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeNotEqualTo(BigDecimal value) {
            addCriterion("collect_service_charge <>", value, "collectServiceCharge");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeGreaterThan(BigDecimal value) {
            addCriterion("collect_service_charge >", value, "collectServiceCharge");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("collect_service_charge >=", value, "collectServiceCharge");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeLessThan(BigDecimal value) {
            addCriterion("collect_service_charge <", value, "collectServiceCharge");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("collect_service_charge <=", value, "collectServiceCharge");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeIn(List<BigDecimal> values) {
            addCriterion("collect_service_charge in", values, "collectServiceCharge");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeNotIn(List<BigDecimal> values) {
            addCriterion("collect_service_charge not in", values, "collectServiceCharge");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("collect_service_charge between", value1, value2, "collectServiceCharge");
            return (Criteria) this;
        }

        public Criteria andCollectServiceChargeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("collect_service_charge not between", value1, value2, "collectServiceCharge");
            return (Criteria) this;
        }

        public Criteria andCollectFlagIsNull() {
            addCriterion("collect_flag is null");
            return (Criteria) this;
        }

        public Criteria andCollectFlagIsNotNull() {
            addCriterion("collect_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCollectFlagEqualTo(String value) {
            addCriterion("collect_flag =", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagNotEqualTo(String value) {
            addCriterion("collect_flag <>", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagGreaterThan(String value) {
            addCriterion("collect_flag >", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagGreaterThanOrEqualTo(String value) {
            addCriterion("collect_flag >=", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagLessThan(String value) {
            addCriterion("collect_flag <", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagLessThanOrEqualTo(String value) {
            addCriterion("collect_flag <=", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagLike(String value) {
            addCriterion("collect_flag like", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagNotLike(String value) {
            addCriterion("collect_flag not like", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagIn(List<String> values) {
            addCriterion("collect_flag in", values, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagNotIn(List<String> values) {
            addCriterion("collect_flag not in", values, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagBetween(String value1, String value2) {
            addCriterion("collect_flag between", value1, value2, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagNotBetween(String value1, String value2) {
            addCriterion("collect_flag not between", value1, value2, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagIsNull() {
            addCriterion("count_flag is null");
            return (Criteria) this;
        }

        public Criteria andCountFlagIsNotNull() {
            addCriterion("count_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCountFlagEqualTo(String value) {
            addCriterion("count_flag =", value, "countFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagNotEqualTo(String value) {
            addCriterion("count_flag <>", value, "countFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagGreaterThan(String value) {
            addCriterion("count_flag >", value, "countFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagGreaterThanOrEqualTo(String value) {
            addCriterion("count_flag >=", value, "countFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagLessThan(String value) {
            addCriterion("count_flag <", value, "countFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagLessThanOrEqualTo(String value) {
            addCriterion("count_flag <=", value, "countFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagLike(String value) {
            addCriterion("count_flag like", value, "countFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagNotLike(String value) {
            addCriterion("count_flag not like", value, "countFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagIn(List<String> values) {
            addCriterion("count_flag in", values, "countFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagNotIn(List<String> values) {
            addCriterion("count_flag not in", values, "countFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagBetween(String value1, String value2) {
            addCriterion("count_flag between", value1, value2, "countFlag");
            return (Criteria) this;
        }

        public Criteria andCountFlagNotBetween(String value1, String value2) {
            addCriterion("count_flag not between", value1, value2, "countFlag");
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