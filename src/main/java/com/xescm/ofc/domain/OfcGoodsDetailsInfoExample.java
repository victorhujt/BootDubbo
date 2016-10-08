package com.xescm.ofc.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OfcGoodsDetailsInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OfcGoodsDetailsInfoExample() {
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

        public Criteria andGoodsCodeIsNull() {
            addCriterion("goods_code is null");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeIsNotNull() {
            addCriterion("goods_code is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeEqualTo(String value) {
            addCriterion("goods_code =", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeNotEqualTo(String value) {
            addCriterion("goods_code <>", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeGreaterThan(String value) {
            addCriterion("goods_code >", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeGreaterThanOrEqualTo(String value) {
            addCriterion("goods_code >=", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeLessThan(String value) {
            addCriterion("goods_code <", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeLessThanOrEqualTo(String value) {
            addCriterion("goods_code <=", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeLike(String value) {
            addCriterion("goods_code like", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeNotLike(String value) {
            addCriterion("goods_code not like", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeIn(List<String> values) {
            addCriterion("goods_code in", values, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeNotIn(List<String> values) {
            addCriterion("goods_code not in", values, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeBetween(String value1, String value2) {
            addCriterion("goods_code between", value1, value2, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeNotBetween(String value1, String value2) {
            addCriterion("goods_code not between", value1, value2, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIsNull() {
            addCriterion("goods_name is null");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIsNotNull() {
            addCriterion("goods_name is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsNameEqualTo(String value) {
            addCriterion("goods_name =", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotEqualTo(String value) {
            addCriterion("goods_name <>", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameGreaterThan(String value) {
            addCriterion("goods_name >", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameGreaterThanOrEqualTo(String value) {
            addCriterion("goods_name >=", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLessThan(String value) {
            addCriterion("goods_name <", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLessThanOrEqualTo(String value) {
            addCriterion("goods_name <=", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLike(String value) {
            addCriterion("goods_name like", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotLike(String value) {
            addCriterion("goods_name not like", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIn(List<String> values) {
            addCriterion("goods_name in", values, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotIn(List<String> values) {
            addCriterion("goods_name not in", values, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameBetween(String value1, String value2) {
            addCriterion("goods_name between", value1, value2, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotBetween(String value1, String value2) {
            addCriterion("goods_name not between", value1, value2, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecIsNull() {
            addCriterion("goods_spec is null");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecIsNotNull() {
            addCriterion("goods_spec is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecEqualTo(String value) {
            addCriterion("goods_spec =", value, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecNotEqualTo(String value) {
            addCriterion("goods_spec <>", value, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecGreaterThan(String value) {
            addCriterion("goods_spec >", value, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecGreaterThanOrEqualTo(String value) {
            addCriterion("goods_spec >=", value, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecLessThan(String value) {
            addCriterion("goods_spec <", value, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecLessThanOrEqualTo(String value) {
            addCriterion("goods_spec <=", value, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecLike(String value) {
            addCriterion("goods_spec like", value, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecNotLike(String value) {
            addCriterion("goods_spec not like", value, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecIn(List<String> values) {
            addCriterion("goods_spec in", values, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecNotIn(List<String> values) {
            addCriterion("goods_spec not in", values, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecBetween(String value1, String value2) {
            addCriterion("goods_spec between", value1, value2, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andGoodsSpecNotBetween(String value1, String value2) {
            addCriterion("goods_spec not between", value1, value2, "goodsSpec");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(BigDecimal value) {
            addCriterion("quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(BigDecimal value) {
            addCriterion("quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(BigDecimal value) {
            addCriterion("quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(BigDecimal value) {
            addCriterion("quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<BigDecimal> values) {
            addCriterion("quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<BigDecimal> values) {
            addCriterion("quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIsNull() {
            addCriterion("unit_price is null");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIsNotNull() {
            addCriterion("unit_price is not null");
            return (Criteria) this;
        }

        public Criteria andUnitPriceEqualTo(BigDecimal value) {
            addCriterion("unit_price =", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotEqualTo(BigDecimal value) {
            addCriterion("unit_price <>", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceGreaterThan(BigDecimal value) {
            addCriterion("unit_price >", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unit_price >=", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceLessThan(BigDecimal value) {
            addCriterion("unit_price <", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unit_price <=", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIn(List<BigDecimal> values) {
            addCriterion("unit_price in", values, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotIn(List<BigDecimal> values) {
            addCriterion("unit_price not in", values, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unit_price between", value1, value2, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unit_price not between", value1, value2, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andReservedField01IsNull() {
            addCriterion("reserved_field01 is null");
            return (Criteria) this;
        }

        public Criteria andReservedField01IsNotNull() {
            addCriterion("reserved_field01 is not null");
            return (Criteria) this;
        }

        public Criteria andReservedField01EqualTo(String value) {
            addCriterion("reserved_field01 =", value, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField01NotEqualTo(String value) {
            addCriterion("reserved_field01 <>", value, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField01GreaterThan(String value) {
            addCriterion("reserved_field01 >", value, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField01GreaterThanOrEqualTo(String value) {
            addCriterion("reserved_field01 >=", value, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField01LessThan(String value) {
            addCriterion("reserved_field01 <", value, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField01LessThanOrEqualTo(String value) {
            addCriterion("reserved_field01 <=", value, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField01Like(String value) {
            addCriterion("reserved_field01 like", value, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField01NotLike(String value) {
            addCriterion("reserved_field01 not like", value, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField01In(List<String> values) {
            addCriterion("reserved_field01 in", values, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField01NotIn(List<String> values) {
            addCriterion("reserved_field01 not in", values, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField01Between(String value1, String value2) {
            addCriterion("reserved_field01 between", value1, value2, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField01NotBetween(String value1, String value2) {
            addCriterion("reserved_field01 not between", value1, value2, "reservedField01");
            return (Criteria) this;
        }

        public Criteria andReservedField02IsNull() {
            addCriterion("reserved_field02 is null");
            return (Criteria) this;
        }

        public Criteria andReservedField02IsNotNull() {
            addCriterion("reserved_field02 is not null");
            return (Criteria) this;
        }

        public Criteria andReservedField02EqualTo(String value) {
            addCriterion("reserved_field02 =", value, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField02NotEqualTo(String value) {
            addCriterion("reserved_field02 <>", value, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField02GreaterThan(String value) {
            addCriterion("reserved_field02 >", value, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField02GreaterThanOrEqualTo(String value) {
            addCriterion("reserved_field02 >=", value, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField02LessThan(String value) {
            addCriterion("reserved_field02 <", value, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField02LessThanOrEqualTo(String value) {
            addCriterion("reserved_field02 <=", value, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField02Like(String value) {
            addCriterion("reserved_field02 like", value, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField02NotLike(String value) {
            addCriterion("reserved_field02 not like", value, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField02In(List<String> values) {
            addCriterion("reserved_field02 in", values, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField02NotIn(List<String> values) {
            addCriterion("reserved_field02 not in", values, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField02Between(String value1, String value2) {
            addCriterion("reserved_field02 between", value1, value2, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField02NotBetween(String value1, String value2) {
            addCriterion("reserved_field02 not between", value1, value2, "reservedField02");
            return (Criteria) this;
        }

        public Criteria andReservedField03IsNull() {
            addCriterion("reserved_field03 is null");
            return (Criteria) this;
        }

        public Criteria andReservedField03IsNotNull() {
            addCriterion("reserved_field03 is not null");
            return (Criteria) this;
        }

        public Criteria andReservedField03EqualTo(String value) {
            addCriterion("reserved_field03 =", value, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField03NotEqualTo(String value) {
            addCriterion("reserved_field03 <>", value, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField03GreaterThan(String value) {
            addCriterion("reserved_field03 >", value, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField03GreaterThanOrEqualTo(String value) {
            addCriterion("reserved_field03 >=", value, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField03LessThan(String value) {
            addCriterion("reserved_field03 <", value, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField03LessThanOrEqualTo(String value) {
            addCriterion("reserved_field03 <=", value, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField03Like(String value) {
            addCriterion("reserved_field03 like", value, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField03NotLike(String value) {
            addCriterion("reserved_field03 not like", value, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField03In(List<String> values) {
            addCriterion("reserved_field03 in", values, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField03NotIn(List<String> values) {
            addCriterion("reserved_field03 not in", values, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField03Between(String value1, String value2) {
            addCriterion("reserved_field03 between", value1, value2, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField03NotBetween(String value1, String value2) {
            addCriterion("reserved_field03 not between", value1, value2, "reservedField03");
            return (Criteria) this;
        }

        public Criteria andReservedField04IsNull() {
            addCriterion("reserved_field04 is null");
            return (Criteria) this;
        }

        public Criteria andReservedField04IsNotNull() {
            addCriterion("reserved_field04 is not null");
            return (Criteria) this;
        }

        public Criteria andReservedField04EqualTo(String value) {
            addCriterion("reserved_field04 =", value, "reservedField04");
            return (Criteria) this;
        }

        public Criteria andReservedField04NotEqualTo(String value) {
            addCriterion("reserved_field04 <>", value, "reservedField04");
            return (Criteria) this;
        }

        public Criteria andReservedField04GreaterThan(String value) {
            addCriterion("reserved_field04 >", value, "reservedField04");
            return (Criteria) this;
        }

        public Criteria andReservedField04GreaterThanOrEqualTo(String value) {
            addCriterion("reserved_field04 >=", value, "reservedField04");
            return (Criteria) this;
        }

        public Criteria andReservedField04LessThan(String value) {
            addCriterion("reserved_field04 <", value, "reservedField04");
            return (Criteria) this;
        }

        public Criteria andReservedField04LessThanOrEqualTo(String value) {
            addCriterion("reserved_field04 <=", value, "reservedField04");
            return (Criteria) this;
        }

        public Criteria andReservedField04Like(String value) {
            addCriterion("reserved_field04 like", value, "reservedField04");
            return (Criteria) this;
        }

        public Criteria andReservedField04NotLike(String value) {
            addCriterion("reserved_field04 not like", value, "reservedField04");
            return (Criteria) this;
        }

        public Criteria andReservedField04In(List<String> values) {
            addCriterion("reserved_field04 in", values, "reservedField04");
            return (Criteria) this;
        }

        public Criteria andReservedField04NotIn(List<String> values) {
            addCriterion("reserved_field04 not in", values, "reservedField04");
            return (Criteria) this;
        }

        public Criteria andReservedField04Between(String value1, String value2) {
            addCriterion("reserved_field04 between", value1, value2, "reservedField04");
            return (Criteria) this;
        }

        public Criteria andReservedField04NotBetween(String value1, String value2) {
            addCriterion("reserved_field04 not between", value1, value2, "reservedField04");
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