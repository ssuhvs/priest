package com.little.g.common.enums;

/**
 * User: liuling
 * Date: 15/9/17
 * Time: 下午1:52
 */
public enum GenderEnum {


    MAN((byte)1,"男"), // 男
    WOMEN((byte)-1,"女"), // 女
    UNKNOWN((byte)0,"保密"); //保密

    GenderEnum(Byte value, String desc ) {
        this.desc = desc;
        this.value = value;
    }

    public Byte value;

    public String getDesc() {
        return desc;
    }

    public String desc;

    public Byte getValue() {
        return value;
    }
}
