package com.little.g.pay.enums;

import java.util.Objects;

/**
 * Created by lengligang on 16/1/18.
 */
public enum BusinessType {
    RECHARGE((byte)1,"充值"), //充值
    ;

    BusinessType(Byte value, String desc){
        this.value=value;
        this.desc=desc;
    }
    public Byte value;
    public String desc;

    public Byte getValue() {
        return value;
    }
    public String getDesc(){return desc;}


    public static BusinessType valueOf(Byte value) {    //    手写的从int到enum的转换函数
        for (BusinessType type : values()) {
            if (Objects.equals(type.getValue(), value)) {
                return type;
            }
        }
        return null;
    }
}
