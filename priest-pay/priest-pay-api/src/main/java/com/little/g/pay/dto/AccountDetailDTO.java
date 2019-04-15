package com.little.g.pay.dto;

import java.io.Serializable;

/**
 * Created by zhaoyao on 05/01/2017.
 */
public class AccountDetailDTO implements Serializable {
    private static final long serialVersionUID = 6797948881816067639L;

    private Account account;
    private long balance;
    private int status;
    private long updateTime;
    private long createTime;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
