package com.little.g.pay.model;

import java.io.Serializable;

public class BalanceCheckpoint implements Serializable {
    private Long id;

    private Long uid;

    private String accountId;

    private Long balance;

    private Long frozen;

    private Long maxId;

    private Long updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getFrozen() {
        return frozen;
    }

    public void setFrozen(Long frozen) {
        this.frozen = frozen;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}