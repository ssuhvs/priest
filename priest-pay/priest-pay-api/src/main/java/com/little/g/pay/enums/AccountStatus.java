package com.little.g.pay.enums;

import java.util.Objects;

/**
 * Created by lengligang on 16/1/18.
 * user account status
 */
public enum AccountStatus {
    INIT((byte) 0, "初始状态"),
    LOCKED((byte) (1), "冻结状态");

    AccountStatus(Byte value, String desc) {
        this.value = value;this.desc=desc;
    }

    public Byte value;
    public String desc;


    public Byte getValue() {
        return value;
    }
    public String getDesc(){return desc;}

    public static AccountStatus valueOf(Byte value) {    //    手写的从int到enum的转换函数
        for (AccountStatus type : values()) {
            if (Objects.equals(type.getValue(), value)) {
                return type;
            }
        }
        return null;
    }
}
