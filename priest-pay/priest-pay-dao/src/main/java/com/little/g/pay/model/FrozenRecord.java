package com.little.g.pay.model;

import java.io.Serializable;

public class FrozenRecord implements Serializable {
    private String tradeNum;

    private String accountId;

    private Long amount;

    private Long balance;

    private Long createTime;

    private Long updateTime;

    private static final long serialVersionUID = 1L;

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum == null ? null : tradeNum.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}