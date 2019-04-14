package com.little.g.pay.dto;

import java.io.Serializable;

/**
 * Created by zhaoyao on 16/4/27.
 */
public class CheckBalanceResultDTO implements Serializable {

    private static final long serialVersionUID = 8701394905931579537L;

    // 0: 正常 1: 余额大于流水 2: 余额小于流水
    private long moneyDifference;

    // code 非0时, 为流水与余额的差异值(分)
    private long frozenDifference;

    public long getFrozenDifference() {
        return frozenDifference;
    }

    public void setFrozenDifference(long frozenDifference) {
        this.frozenDifference = frozenDifference;
    }

    public long getMoneyDifference() {
        return moneyDifference;
    }

    public void setMoneyDifference(long moneyDifference) {
        this.moneyDifference = moneyDifference;
    }

    @Override
    public String toString() {
        return "CheckBalanceResultDTO{" +
                "moneyDifference=" + moneyDifference +
                ", frozenDifference=" + frozenDifference +
                '}';
    }
}
