package com.little.g.pay.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TransactionRecordExample() {
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

        public Criteria andTranNumIsNull() {
            addCriterion("tran_num is null");
            return (Criteria) this;
        }

        public Criteria andTranNumIsNotNull() {
            addCriterion("tran_num is not null");
            return (Criteria) this;
        }

        public Criteria andTranNumEqualTo(String value) {
            addCriterion("tran_num =", value, "tranNum");
            return (Criteria) this;
        }

        public Criteria andTranNumNotEqualTo(String value) {
            addCriterion("tran_num <>", value, "tranNum");
            return (Criteria) this;
        }

        public Criteria andTranNumGreaterThan(String value) {
            addCriterion("tran_num >", value, "tranNum");
            return (Criteria) this;
        }

        public Criteria andTranNumGreaterThanOrEqualTo(String value) {
            addCriterion("tran_num >=", value, "tranNum");
            return (Criteria) this;
        }

        public Criteria andTranNumLessThan(String value) {
            addCriterion("tran_num <", value, "tranNum");
            return (Criteria) this;
        }

        public Criteria andTranNumLessThanOrEqualTo(String value) {
            addCriterion("tran_num <=", value, "tranNum");
            return (Criteria) this;
        }

        public Criteria andTranNumLike(String value) {
            addCriterion("tran_num like", value, "tranNum");
            return (Criteria) this;
        }

        public Criteria andTranNumNotLike(String value) {
            addCriterion("tran_num not like", value, "tranNum");
            return (Criteria) this;
        }

        public Criteria andTranNumIn(List<String> values) {
            addCriterion("tran_num in", values, "tranNum");
            return (Criteria) this;
        }

        public Criteria andTranNumNotIn(List<String> values) {
            addCriterion("tran_num not in", values, "tranNum");
            return (Criteria) this;
        }

        public Criteria andTranNumBetween(String value1, String value2) {
            addCriterion("tran_num between", value1, value2, "tranNum");
            return (Criteria) this;
        }

        public Criteria andTranNumNotBetween(String value1, String value2) {
            addCriterion("tran_num not between", value1, value2, "tranNum");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(String value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(String value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(String value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(String value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(String value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLike(String value) {
            addCriterion("account_id like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotLike(String value) {
            addCriterion("account_id not like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<String> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<String> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(String value1, String value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(String value1, String value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andOppositeIsNull() {
            addCriterion("opposite is null");
            return (Criteria) this;
        }

        public Criteria andOppositeIsNotNull() {
            addCriterion("opposite is not null");
            return (Criteria) this;
        }

        public Criteria andOppositeEqualTo(String value) {
            addCriterion("opposite =", value, "opposite");
            return (Criteria) this;
        }

        public Criteria andOppositeNotEqualTo(String value) {
            addCriterion("opposite <>", value, "opposite");
            return (Criteria) this;
        }

        public Criteria andOppositeGreaterThan(String value) {
            addCriterion("opposite >", value, "opposite");
            return (Criteria) this;
        }

        public Criteria andOppositeGreaterThanOrEqualTo(String value) {
            addCriterion("opposite >=", value, "opposite");
            return (Criteria) this;
        }

        public Criteria andOppositeLessThan(String value) {
            addCriterion("opposite <", value, "opposite");
            return (Criteria) this;
        }

        public Criteria andOppositeLessThanOrEqualTo(String value) {
            addCriterion("opposite <=", value, "opposite");
            return (Criteria) this;
        }

        public Criteria andOppositeLike(String value) {
            addCriterion("opposite like", value, "opposite");
            return (Criteria) this;
        }

        public Criteria andOppositeNotLike(String value) {
            addCriterion("opposite not like", value, "opposite");
            return (Criteria) this;
        }

        public Criteria andOppositeIn(List<String> values) {
            addCriterion("opposite in", values, "opposite");
            return (Criteria) this;
        }

        public Criteria andOppositeNotIn(List<String> values) {
            addCriterion("opposite not in", values, "opposite");
            return (Criteria) this;
        }

        public Criteria andOppositeBetween(String value1, String value2) {
            addCriterion("opposite between", value1, value2, "opposite");
            return (Criteria) this;
        }

        public Criteria andOppositeNotBetween(String value1, String value2) {
            addCriterion("opposite not between", value1, value2, "opposite");
            return (Criteria) this;
        }

        public Criteria andTradeNumIsNull() {
            addCriterion("trade_num is null");
            return (Criteria) this;
        }

        public Criteria andTradeNumIsNotNull() {
            addCriterion("trade_num is not null");
            return (Criteria) this;
        }

        public Criteria andTradeNumEqualTo(String value) {
            addCriterion("trade_num =", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumNotEqualTo(String value) {
            addCriterion("trade_num <>", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumGreaterThan(String value) {
            addCriterion("trade_num >", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumGreaterThanOrEqualTo(String value) {
            addCriterion("trade_num >=", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumLessThan(String value) {
            addCriterion("trade_num <", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumLessThanOrEqualTo(String value) {
            addCriterion("trade_num <=", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumLike(String value) {
            addCriterion("trade_num like", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumNotLike(String value) {
            addCriterion("trade_num not like", value, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumIn(List<String> values) {
            addCriterion("trade_num in", values, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumNotIn(List<String> values) {
            addCriterion("trade_num not in", values, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumBetween(String value1, String value2) {
            addCriterion("trade_num between", value1, value2, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andTradeNumNotBetween(String value1, String value2) {
            addCriterion("trade_num not between", value1, value2, "tradeNum");
            return (Criteria) this;
        }

        public Criteria andMoneyIsNull() {
            addCriterion("money is null");
            return (Criteria) this;
        }

        public Criteria andMoneyIsNotNull() {
            addCriterion("money is not null");
            return (Criteria) this;
        }

        public Criteria andMoneyEqualTo(Long value) {
            addCriterion("money =", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotEqualTo(Long value) {
            addCriterion("money <>", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThan(Long value) {
            addCriterion("money >", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThanOrEqualTo(Long value) {
            addCriterion("money >=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThan(Long value) {
            addCriterion("money <", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThanOrEqualTo(Long value) {
            addCriterion("money <=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyIn(List<Long> values) {
            addCriterion("money in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotIn(List<Long> values) {
            addCriterion("money not in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyBetween(Long value1, Long value2) {
            addCriterion("money between", value1, value2, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotBetween(Long value1, Long value2) {
            addCriterion("money not between", value1, value2, "money");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andBtypeIsNull() {
            addCriterion("btype is null");
            return (Criteria) this;
        }

        public Criteria andBtypeIsNotNull() {
            addCriterion("btype is not null");
            return (Criteria) this;
        }

        public Criteria andBtypeEqualTo(Byte value) {
            addCriterion("btype =", value, "btype");
            return (Criteria) this;
        }

        public Criteria andBtypeNotEqualTo(Byte value) {
            addCriterion("btype <>", value, "btype");
            return (Criteria) this;
        }

        public Criteria andBtypeGreaterThan(Byte value) {
            addCriterion("btype >", value, "btype");
            return (Criteria) this;
        }

        public Criteria andBtypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("btype >=", value, "btype");
            return (Criteria) this;
        }

        public Criteria andBtypeLessThan(Byte value) {
            addCriterion("btype <", value, "btype");
            return (Criteria) this;
        }

        public Criteria andBtypeLessThanOrEqualTo(Byte value) {
            addCriterion("btype <=", value, "btype");
            return (Criteria) this;
        }

        public Criteria andBtypeIn(List<Byte> values) {
            addCriterion("btype in", values, "btype");
            return (Criteria) this;
        }

        public Criteria andBtypeNotIn(List<Byte> values) {
            addCriterion("btype not in", values, "btype");
            return (Criteria) this;
        }

        public Criteria andBtypeBetween(Byte value1, Byte value2) {
            addCriterion("btype between", value1, value2, "btype");
            return (Criteria) this;
        }

        public Criteria andBtypeNotBetween(Byte value1, Byte value2) {
            addCriterion("btype not between", value1, value2, "btype");
            return (Criteria) this;
        }

        public Criteria andCommentIsNull() {
            addCriterion("`comment` is null");
            return (Criteria) this;
        }

        public Criteria andCommentIsNotNull() {
            addCriterion("`comment` is not null");
            return (Criteria) this;
        }

        public Criteria andCommentEqualTo(String value) {
            addCriterion("`comment` =", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotEqualTo(String value) {
            addCriterion("`comment` <>", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThan(String value) {
            addCriterion("`comment` >", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThanOrEqualTo(String value) {
            addCriterion("`comment` >=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThan(String value) {
            addCriterion("`comment` <", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThanOrEqualTo(String value) {
            addCriterion("`comment` <=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLike(String value) {
            addCriterion("`comment` like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotLike(String value) {
            addCriterion("`comment` not like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentIn(List<String> values) {
            addCriterion("`comment` in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotIn(List<String> values) {
            addCriterion("`comment` not in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentBetween(String value1, String value2) {
            addCriterion("`comment` between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotBetween(String value1, String value2) {
            addCriterion("`comment` not between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Long value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Long value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Long value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Long value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Long value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Long> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Long> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Long value1, Long value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Long value1, Long value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andTranNumLikeInsensitive(String value) {
            addCriterion("upper(tran_num) like", value.toUpperCase(), "tranNum");
            return (Criteria) this;
        }

        public Criteria andAccountIdLikeInsensitive(String value) {
            addCriterion("upper(account_id) like", value.toUpperCase(), "accountId");
            return (Criteria) this;
        }

        public Criteria andOppositeLikeInsensitive(String value) {
            addCriterion("upper(opposite) like", value.toUpperCase(), "opposite");
            return (Criteria) this;
        }

        public Criteria andTradeNumLikeInsensitive(String value) {
            addCriterion("upper(trade_num) like", value.toUpperCase(), "tradeNum");
            return (Criteria) this;
        }

        public Criteria andCommentLikeInsensitive(String value) {
            addCriterion("upper(`comment`) like", value.toUpperCase(), "comment");
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