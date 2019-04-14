package com.little.g.pay.dto;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * 冻结帐号
 * Created by zhaoyao on 16/5/9.
 */
public class FrozenAccount implements Account {

    private static final long serialVersionUID = -4082893515069056657L;
    private String transNum;

    public FrozenAccount(String transNum) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(transNum));
        this.transNum = transNum;
    }

    public FrozenAccount() {
    }

    public String getTransNum() {
        return transNum;
    }

    @Override
    public String getId() {
        return "UF" + transNum;
    }

    @Override
    public String getPrefix() {
        return "UF";
    }

    @Override
    public String toString() {
        return "FrozenAccount{" +
                "transNum='" + transNum + '\'' +
                '}';
    }
}
