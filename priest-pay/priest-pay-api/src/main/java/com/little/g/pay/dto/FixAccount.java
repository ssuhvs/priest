package com.little.g.pay.dto;

/**
 * 固定帐号, 各种服务提供商账户定义
 * Created by zhaoyao on 16/5/9.
 */
public class FixAccount implements Account {

    private static final long serialVersionUID = 4099874254600797724L;
    private long uid;
    // 该账户是否允许入账
    private boolean allowCredit;

    public FixAccount(long uid, boolean allowCredit) {
        this.uid = uid;
        this.allowCredit = allowCredit;
    }

    private FixAccount() {
    }

    public long getUid() {
        return uid;
    }

    public boolean isAllowCredit() {
        return allowCredit;
    }

    @Override
    public String getId() {
        return "FIX"+uid;
    }

    @Override
    public String getPrefix() {
        return "FIX";
    }

    @Override
    public String toString() {
        return "FixAccount{" +
                "uid=" + uid +
                ", allowCredit=" + allowCredit +
                '}';
    }
}
