package com.xescm.ofc.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OfcDistributionBasicInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OfcDistributionBasicInfoExample() {
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

        public Criteria andTransCodeIsNull() {
            addCriterion("trans_code is null");
            return (Criteria) this;
        }

        public Criteria andTransCodeIsNotNull() {
            addCriterion("trans_code is not null");
            return (Criteria) this;
        }

        public Criteria andTransCodeEqualTo(String value) {
            addCriterion("trans_code =", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotEqualTo(String value) {
            addCriterion("trans_code <>", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeGreaterThan(String value) {
            addCriterion("trans_code >", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_code >=", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeLessThan(String value) {
            addCriterion("trans_code <", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeLessThanOrEqualTo(String value) {
            addCriterion("trans_code <=", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeLike(String value) {
            addCriterion("trans_code like", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotLike(String value) {
            addCriterion("trans_code not like", value, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeIn(List<String> values) {
            addCriterion("trans_code in", values, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotIn(List<String> values) {
            addCriterion("trans_code not in", values, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeBetween(String value1, String value2) {
            addCriterion("trans_code between", value1, value2, "transCode");
            return (Criteria) this;
        }

        public Criteria andTransCodeNotBetween(String value1, String value2) {
            addCriterion("trans_code not between", value1, value2, "transCode");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIsNull() {
            addCriterion("goods_type is null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIsNotNull() {
            addCriterion("goods_type is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeEqualTo(String value) {
            addCriterion("goods_type =", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotEqualTo(String value) {
            addCriterion("goods_type <>", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeGreaterThan(String value) {
            addCriterion("goods_type >", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeGreaterThanOrEqualTo(String value) {
            addCriterion("goods_type >=", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLessThan(String value) {
            addCriterion("goods_type <", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLessThanOrEqualTo(String value) {
            addCriterion("goods_type <=", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLike(String value) {
            addCriterion("goods_type like", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotLike(String value) {
            addCriterion("goods_type not like", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIn(List<String> values) {
            addCriterion("goods_type in", values, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotIn(List<String> values) {
            addCriterion("goods_type not in", values, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeBetween(String value1, String value2) {
            addCriterion("goods_type between", value1, value2, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotBetween(String value1, String value2) {
            addCriterion("goods_type not between", value1, value2, "goodsType");
            return (Criteria) this;
        }

        public Criteria andUrgentIsNull() {
            addCriterion("urgent is null");
            return (Criteria) this;
        }

        public Criteria andUrgentIsNotNull() {
            addCriterion("urgent is not null");
            return (Criteria) this;
        }

        public Criteria andUrgentEqualTo(Integer value) {
            addCriterion("urgent =", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentNotEqualTo(Integer value) {
            addCriterion("urgent <>", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentGreaterThan(Integer value) {
            addCriterion("urgent >", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentGreaterThanOrEqualTo(Integer value) {
            addCriterion("urgent >=", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentLessThan(Integer value) {
            addCriterion("urgent <", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentLessThanOrEqualTo(Integer value) {
            addCriterion("urgent <=", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentIn(List<Integer> values) {
            addCriterion("urgent in", values, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentNotIn(List<Integer> values) {
            addCriterion("urgent not in", values, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentBetween(Integer value1, Integer value2) {
            addCriterion("urgent between", value1, value2, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentNotBetween(Integer value1, Integer value2) {
            addCriterion("urgent not between", value1, value2, "urgent");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceIsNull() {
            addCriterion("departure_place is null");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceIsNotNull() {
            addCriterion("departure_place is not null");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceEqualTo(String value) {
            addCriterion("departure_place =", value, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceNotEqualTo(String value) {
            addCriterion("departure_place <>", value, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceGreaterThan(String value) {
            addCriterion("departure_place >", value, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceGreaterThanOrEqualTo(String value) {
            addCriterion("departure_place >=", value, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceLessThan(String value) {
            addCriterion("departure_place <", value, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceLessThanOrEqualTo(String value) {
            addCriterion("departure_place <=", value, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceLike(String value) {
            addCriterion("departure_place like", value, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceNotLike(String value) {
            addCriterion("departure_place not like", value, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceIn(List<String> values) {
            addCriterion("departure_place in", values, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceNotIn(List<String> values) {
            addCriterion("departure_place not in", values, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceBetween(String value1, String value2) {
            addCriterion("departure_place between", value1, value2, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceNotBetween(String value1, String value2) {
            addCriterion("departure_place not between", value1, value2, "departurePlace");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeIsNull() {
            addCriterion("departure_place_code is null");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeIsNotNull() {
            addCriterion("departure_place_code is not null");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeEqualTo(String value) {
            addCriterion("departure_place_code =", value, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeNotEqualTo(String value) {
            addCriterion("departure_place_code <>", value, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeGreaterThan(String value) {
            addCriterion("departure_place_code >", value, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("departure_place_code >=", value, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeLessThan(String value) {
            addCriterion("departure_place_code <", value, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeLessThanOrEqualTo(String value) {
            addCriterion("departure_place_code <=", value, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeLike(String value) {
            addCriterion("departure_place_code like", value, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeNotLike(String value) {
            addCriterion("departure_place_code not like", value, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeIn(List<String> values) {
            addCriterion("departure_place_code in", values, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeNotIn(List<String> values) {
            addCriterion("departure_place_code not in", values, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeBetween(String value1, String value2) {
            addCriterion("departure_place_code between", value1, value2, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDeparturePlaceCodeNotBetween(String value1, String value2) {
            addCriterion("departure_place_code not between", value1, value2, "departurePlaceCode");
            return (Criteria) this;
        }

        public Criteria andDestinationIsNull() {
            addCriterion("destination is null");
            return (Criteria) this;
        }

        public Criteria andDestinationIsNotNull() {
            addCriterion("destination is not null");
            return (Criteria) this;
        }

        public Criteria andDestinationEqualTo(String value) {
            addCriterion("destination =", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotEqualTo(String value) {
            addCriterion("destination <>", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationGreaterThan(String value) {
            addCriterion("destination >", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationGreaterThanOrEqualTo(String value) {
            addCriterion("destination >=", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLessThan(String value) {
            addCriterion("destination <", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLessThanOrEqualTo(String value) {
            addCriterion("destination <=", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLike(String value) {
            addCriterion("destination like", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotLike(String value) {
            addCriterion("destination not like", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationIn(List<String> values) {
            addCriterion("destination in", values, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotIn(List<String> values) {
            addCriterion("destination not in", values, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationBetween(String value1, String value2) {
            addCriterion("destination between", value1, value2, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotBetween(String value1, String value2) {
            addCriterion("destination not between", value1, value2, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeIsNull() {
            addCriterion("destination_code is null");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeIsNotNull() {
            addCriterion("destination_code is not null");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeEqualTo(String value) {
            addCriterion("destination_code =", value, "destinationCode");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeNotEqualTo(String value) {
            addCriterion("destination_code <>", value, "destinationCode");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeGreaterThan(String value) {
            addCriterion("destination_code >", value, "destinationCode");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeGreaterThanOrEqualTo(String value) {
            addCriterion("destination_code >=", value, "destinationCode");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeLessThan(String value) {
            addCriterion("destination_code <", value, "destinationCode");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeLessThanOrEqualTo(String value) {
            addCriterion("destination_code <=", value, "destinationCode");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeLike(String value) {
            addCriterion("destination_code like", value, "destinationCode");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeNotLike(String value) {
            addCriterion("destination_code not like", value, "destinationCode");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeIn(List<String> values) {
            addCriterion("destination_code in", values, "destinationCode");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeNotIn(List<String> values) {
            addCriterion("destination_code not in", values, "destinationCode");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeBetween(String value1, String value2) {
            addCriterion("destination_code between", value1, value2, "destinationCode");
            return (Criteria) this;
        }

        public Criteria andDestinationCodeNotBetween(String value1, String value2) {
            addCriterion("destination_code not between", value1, value2, "destinationCode");
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

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(BigDecimal value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(BigDecimal value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(BigDecimal value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(BigDecimal value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<BigDecimal> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<BigDecimal> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andCubageIsNull() {
            addCriterion("cubage is null");
            return (Criteria) this;
        }

        public Criteria andCubageIsNotNull() {
            addCriterion("cubage is not null");
            return (Criteria) this;
        }

        public Criteria andCubageEqualTo(BigDecimal value) {
            addCriterion("cubage =", value, "cubage");
            return (Criteria) this;
        }

        public Criteria andCubageNotEqualTo(BigDecimal value) {
            addCriterion("cubage <>", value, "cubage");
            return (Criteria) this;
        }

        public Criteria andCubageGreaterThan(BigDecimal value) {
            addCriterion("cubage >", value, "cubage");
            return (Criteria) this;
        }

        public Criteria andCubageGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cubage >=", value, "cubage");
            return (Criteria) this;
        }

        public Criteria andCubageLessThan(BigDecimal value) {
            addCriterion("cubage <", value, "cubage");
            return (Criteria) this;
        }

        public Criteria andCubageLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cubage <=", value, "cubage");
            return (Criteria) this;
        }

        public Criteria andCubageIn(List<BigDecimal> values) {
            addCriterion("cubage in", values, "cubage");
            return (Criteria) this;
        }

        public Criteria andCubageNotIn(List<BigDecimal> values) {
            addCriterion("cubage not in", values, "cubage");
            return (Criteria) this;
        }

        public Criteria andCubageBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cubage between", value1, value2, "cubage");
            return (Criteria) this;
        }

        public Criteria andCubageNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cubage not between", value1, value2, "cubage");
            return (Criteria) this;
        }

        public Criteria andCubageStrIsNull() {
            addCriterion("cubage_str is null");
            return (Criteria) this;
        }

        public Criteria andCubageStrIsNotNull() {
            addCriterion("cubage_str is not null");
            return (Criteria) this;
        }

        public Criteria andCubageStrEqualTo(BigDecimal value) {
            addCriterion("cubage_str =", value, "cubageStr");
            return (Criteria) this;
        }

        public Criteria andCubageStrNotEqualTo(BigDecimal value) {
            addCriterion("cubage_str <>", value, "cubageStr");
            return (Criteria) this;
        }

        public Criteria andCubageStrGreaterThan(BigDecimal value) {
            addCriterion("cubage_str >", value, "cubageStr");
            return (Criteria) this;
        }

        public Criteria andCubageStrGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cubage_str >=", value, "cubageStr");
            return (Criteria) this;
        }

        public Criteria andCubageStrLessThan(BigDecimal value) {
            addCriterion("cubage_str <", value, "cubageStr");
            return (Criteria) this;
        }

        public Criteria andCubageStrLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cubage_str <=", value, "cubageStr");
            return (Criteria) this;
        }

        public Criteria andCubageStrIn(List<BigDecimal> values) {
            addCriterion("cubage_str in", values, "cubageStr");
            return (Criteria) this;
        }

        public Criteria andCubageStrNotIn(List<BigDecimal> values) {
            addCriterion("cubage_str not in", values, "cubageStr");
            return (Criteria) this;
        }

        public Criteria andCubageStrBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cubage_str between", value1, value2, "cubageStr");
            return (Criteria) this;
        }

        public Criteria andCubageStrNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cubage_str not between", value1, value2, "cubageStr");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxIsNull() {
            addCriterion("total_standard_box is null");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxIsNotNull() {
            addCriterion("total_standard_box is not null");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxEqualTo(Integer value) {
            addCriterion("total_standard_box =", value, "totalStandardBox");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxNotEqualTo(Integer value) {
            addCriterion("total_standard_box <>", value, "totalStandardBox");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxGreaterThan(Integer value) {
            addCriterion("total_standard_box >", value, "totalStandardBox");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_standard_box >=", value, "totalStandardBox");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxLessThan(Integer value) {
            addCriterion("total_standard_box <", value, "totalStandardBox");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxLessThanOrEqualTo(Integer value) {
            addCriterion("total_standard_box <=", value, "totalStandardBox");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxIn(List<Integer> values) {
            addCriterion("total_standard_box in", values, "totalStandardBox");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxNotIn(List<Integer> values) {
            addCriterion("total_standard_box not in", values, "totalStandardBox");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxBetween(Integer value1, Integer value2) {
            addCriterion("total_standard_box between", value1, value2, "totalStandardBox");
            return (Criteria) this;
        }

        public Criteria andTotalStandardBoxNotBetween(Integer value1, Integer value2) {
            addCriterion("total_standard_box not between", value1, value2, "totalStandardBox");
            return (Criteria) this;
        }

        public Criteria andTransRequireIsNull() {
            addCriterion("trans_require is null");
            return (Criteria) this;
        }

        public Criteria andTransRequireIsNotNull() {
            addCriterion("trans_require is not null");
            return (Criteria) this;
        }

        public Criteria andTransRequireEqualTo(String value) {
            addCriterion("trans_require =", value, "transRequire");
            return (Criteria) this;
        }

        public Criteria andTransRequireNotEqualTo(String value) {
            addCriterion("trans_require <>", value, "transRequire");
            return (Criteria) this;
        }

        public Criteria andTransRequireGreaterThan(String value) {
            addCriterion("trans_require >", value, "transRequire");
            return (Criteria) this;
        }

        public Criteria andTransRequireGreaterThanOrEqualTo(String value) {
            addCriterion("trans_require >=", value, "transRequire");
            return (Criteria) this;
        }

        public Criteria andTransRequireLessThan(String value) {
            addCriterion("trans_require <", value, "transRequire");
            return (Criteria) this;
        }

        public Criteria andTransRequireLessThanOrEqualTo(String value) {
            addCriterion("trans_require <=", value, "transRequire");
            return (Criteria) this;
        }

        public Criteria andTransRequireLike(String value) {
            addCriterion("trans_require like", value, "transRequire");
            return (Criteria) this;
        }

        public Criteria andTransRequireNotLike(String value) {
            addCriterion("trans_require not like", value, "transRequire");
            return (Criteria) this;
        }

        public Criteria andTransRequireIn(List<String> values) {
            addCriterion("trans_require in", values, "transRequire");
            return (Criteria) this;
        }

        public Criteria andTransRequireNotIn(List<String> values) {
            addCriterion("trans_require not in", values, "transRequire");
            return (Criteria) this;
        }

        public Criteria andTransRequireBetween(String value1, String value2) {
            addCriterion("trans_require between", value1, value2, "transRequire");
            return (Criteria) this;
        }

        public Criteria andTransRequireNotBetween(String value1, String value2) {
            addCriterion("trans_require not between", value1, value2, "transRequire");
            return (Criteria) this;
        }

        public Criteria andPickupTimeIsNull() {
            addCriterion("pickup_time is null");
            return (Criteria) this;
        }

        public Criteria andPickupTimeIsNotNull() {
            addCriterion("pickup_time is not null");
            return (Criteria) this;
        }

        public Criteria andPickupTimeEqualTo(Date value) {
            addCriterion("pickup_time =", value, "pickupTime");
            return (Criteria) this;
        }

        public Criteria andPickupTimeNotEqualTo(Date value) {
            addCriterion("pickup_time <>", value, "pickupTime");
            return (Criteria) this;
        }

        public Criteria andPickupTimeGreaterThan(Date value) {
            addCriterion("pickup_time >", value, "pickupTime");
            return (Criteria) this;
        }

        public Criteria andPickupTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pickup_time >=", value, "pickupTime");
            return (Criteria) this;
        }

        public Criteria andPickupTimeLessThan(Date value) {
            addCriterion("pickup_time <", value, "pickupTime");
            return (Criteria) this;
        }

        public Criteria andPickupTimeLessThanOrEqualTo(Date value) {
            addCriterion("pickup_time <=", value, "pickupTime");
            return (Criteria) this;
        }

        public Criteria andPickupTimeIn(List<Date> values) {
            addCriterion("pickup_time in", values, "pickupTime");
            return (Criteria) this;
        }

        public Criteria andPickupTimeNotIn(List<Date> values) {
            addCriterion("pickup_time not in", values, "pickupTime");
            return (Criteria) this;
        }

        public Criteria andPickupTimeBetween(Date value1, Date value2) {
            addCriterion("pickup_time between", value1, value2, "pickupTime");
            return (Criteria) this;
        }

        public Criteria andPickupTimeNotBetween(Date value1, Date value2) {
            addCriterion("pickup_time not between", value1, value2, "pickupTime");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeIsNull() {
            addCriterion("expected_arrived_time is null");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeIsNotNull() {
            addCriterion("expected_arrived_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeEqualTo(Date value) {
            addCriterion("expected_arrived_time =", value, "expectedArrivedTime");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeNotEqualTo(Date value) {
            addCriterion("expected_arrived_time <>", value, "expectedArrivedTime");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeGreaterThan(Date value) {
            addCriterion("expected_arrived_time >", value, "expectedArrivedTime");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("expected_arrived_time >=", value, "expectedArrivedTime");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeLessThan(Date value) {
            addCriterion("expected_arrived_time <", value, "expectedArrivedTime");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeLessThanOrEqualTo(Date value) {
            addCriterion("expected_arrived_time <=", value, "expectedArrivedTime");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeIn(List<Date> values) {
            addCriterion("expected_arrived_time in", values, "expectedArrivedTime");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeNotIn(List<Date> values) {
            addCriterion("expected_arrived_time not in", values, "expectedArrivedTime");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeBetween(Date value1, Date value2) {
            addCriterion("expected_arrived_time between", value1, value2, "expectedArrivedTime");
            return (Criteria) this;
        }

        public Criteria andExpectedArrivedTimeNotBetween(Date value1, Date value2) {
            addCriterion("expected_arrived_time not between", value1, value2, "expectedArrivedTime");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeIsNull() {
            addCriterion("consignor_code is null");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeIsNotNull() {
            addCriterion("consignor_code is not null");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeEqualTo(String value) {
            addCriterion("consignor_code =", value, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeNotEqualTo(String value) {
            addCriterion("consignor_code <>", value, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeGreaterThan(String value) {
            addCriterion("consignor_code >", value, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("consignor_code >=", value, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeLessThan(String value) {
            addCriterion("consignor_code <", value, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeLessThanOrEqualTo(String value) {
            addCriterion("consignor_code <=", value, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeLike(String value) {
            addCriterion("consignor_code like", value, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeNotLike(String value) {
            addCriterion("consignor_code not like", value, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeIn(List<String> values) {
            addCriterion("consignor_code in", values, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeNotIn(List<String> values) {
            addCriterion("consignor_code not in", values, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeBetween(String value1, String value2) {
            addCriterion("consignor_code between", value1, value2, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorCodeNotBetween(String value1, String value2) {
            addCriterion("consignor_code not between", value1, value2, "consignorCode");
            return (Criteria) this;
        }

        public Criteria andConsignorNameIsNull() {
            addCriterion("consignor_name is null");
            return (Criteria) this;
        }

        public Criteria andConsignorNameIsNotNull() {
            addCriterion("consignor_name is not null");
            return (Criteria) this;
        }

        public Criteria andConsignorNameEqualTo(String value) {
            addCriterion("consignor_name =", value, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsignorNameNotEqualTo(String value) {
            addCriterion("consignor_name <>", value, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsignorNameGreaterThan(String value) {
            addCriterion("consignor_name >", value, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsignorNameGreaterThanOrEqualTo(String value) {
            addCriterion("consignor_name >=", value, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsignorNameLessThan(String value) {
            addCriterion("consignor_name <", value, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsignorNameLessThanOrEqualTo(String value) {
            addCriterion("consignor_name <=", value, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsignorNameLike(String value) {
            addCriterion("consignor_name like", value, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsignorNameNotLike(String value) {
            addCriterion("consignor_name not like", value, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsignorNameIn(List<String> values) {
            addCriterion("consignor_name in", values, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsignorNameNotIn(List<String> values) {
            addCriterion("consignor_name not in", values, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsignorNameBetween(String value1, String value2) {
            addCriterion("consignor_name between", value1, value2, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsignorNameNotBetween(String value1, String value2) {
            addCriterion("consignor_name not between", value1, value2, "consignorName");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeIsNull() {
            addCriterion("consignee_code is null");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeIsNotNull() {
            addCriterion("consignee_code is not null");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeEqualTo(String value) {
            addCriterion("consignee_code =", value, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeNotEqualTo(String value) {
            addCriterion("consignee_code <>", value, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeGreaterThan(String value) {
            addCriterion("consignee_code >", value, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("consignee_code >=", value, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeLessThan(String value) {
            addCriterion("consignee_code <", value, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeLessThanOrEqualTo(String value) {
            addCriterion("consignee_code <=", value, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeLike(String value) {
            addCriterion("consignee_code like", value, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeNotLike(String value) {
            addCriterion("consignee_code not like", value, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeIn(List<String> values) {
            addCriterion("consignee_code in", values, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeNotIn(List<String> values) {
            addCriterion("consignee_code not in", values, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeBetween(String value1, String value2) {
            addCriterion("consignee_code between", value1, value2, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeCodeNotBetween(String value1, String value2) {
            addCriterion("consignee_code not between", value1, value2, "consigneeCode");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameIsNull() {
            addCriterion("consignee_name is null");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameIsNotNull() {
            addCriterion("consignee_name is not null");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameEqualTo(String value) {
            addCriterion("consignee_name =", value, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameNotEqualTo(String value) {
            addCriterion("consignee_name <>", value, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameGreaterThan(String value) {
            addCriterion("consignee_name >", value, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameGreaterThanOrEqualTo(String value) {
            addCriterion("consignee_name >=", value, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameLessThan(String value) {
            addCriterion("consignee_name <", value, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameLessThanOrEqualTo(String value) {
            addCriterion("consignee_name <=", value, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameLike(String value) {
            addCriterion("consignee_name like", value, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameNotLike(String value) {
            addCriterion("consignee_name not like", value, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameIn(List<String> values) {
            addCriterion("consignee_name in", values, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameNotIn(List<String> values) {
            addCriterion("consignee_name not in", values, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameBetween(String value1, String value2) {
            addCriterion("consignee_name between", value1, value2, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andConsigneeNameNotBetween(String value1, String value2) {
            addCriterion("consignee_name not between", value1, value2, "consigneeName");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeIsNull() {
            addCriterion("carrier_code is null");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeIsNotNull() {
            addCriterion("carrier_code is not null");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeEqualTo(String value) {
            addCriterion("carrier_code =", value, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeNotEqualTo(String value) {
            addCriterion("carrier_code <>", value, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeGreaterThan(String value) {
            addCriterion("carrier_code >", value, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeGreaterThanOrEqualTo(String value) {
            addCriterion("carrier_code >=", value, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeLessThan(String value) {
            addCriterion("carrier_code <", value, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeLessThanOrEqualTo(String value) {
            addCriterion("carrier_code <=", value, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeLike(String value) {
            addCriterion("carrier_code like", value, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeNotLike(String value) {
            addCriterion("carrier_code not like", value, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeIn(List<String> values) {
            addCriterion("carrier_code in", values, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeNotIn(List<String> values) {
            addCriterion("carrier_code not in", values, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeBetween(String value1, String value2) {
            addCriterion("carrier_code between", value1, value2, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierCodeNotBetween(String value1, String value2) {
            addCriterion("carrier_code not between", value1, value2, "carrierCode");
            return (Criteria) this;
        }

        public Criteria andCarrierNameIsNull() {
            addCriterion("carrier_name is null");
            return (Criteria) this;
        }

        public Criteria andCarrierNameIsNotNull() {
            addCriterion("carrier_name is not null");
            return (Criteria) this;
        }

        public Criteria andCarrierNameEqualTo(String value) {
            addCriterion("carrier_name =", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameNotEqualTo(String value) {
            addCriterion("carrier_name <>", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameGreaterThan(String value) {
            addCriterion("carrier_name >", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameGreaterThanOrEqualTo(String value) {
            addCriterion("carrier_name >=", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameLessThan(String value) {
            addCriterion("carrier_name <", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameLessThanOrEqualTo(String value) {
            addCriterion("carrier_name <=", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameLike(String value) {
            addCriterion("carrier_name like", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameNotLike(String value) {
            addCriterion("carrier_name not like", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameIn(List<String> values) {
            addCriterion("carrier_name in", values, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameNotIn(List<String> values) {
            addCriterion("carrier_name not in", values, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameBetween(String value1, String value2) {
            addCriterion("carrier_name between", value1, value2, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameNotBetween(String value1, String value2) {
            addCriterion("carrier_name not between", value1, value2, "carrierName");
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