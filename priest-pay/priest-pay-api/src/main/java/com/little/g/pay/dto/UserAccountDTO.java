package com.little.g.pay.dto;

import java.io.Serializable;

public class UserAccountDTO implements Serializable {
    private Long uid;

    private Long money;

    private Long frozonMoney;

    private Byte status;

    private Long updateTime;

    private Long createTime;

    private static final long serialVersionUID = 1L;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getFrozonMoney() {
        return frozonMoney;
    }

    public void setFrozonMoney(Long frozonMoney) {
        this.frozonMoney = frozonMoney;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}