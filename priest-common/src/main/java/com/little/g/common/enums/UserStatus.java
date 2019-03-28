package com.little.g.common.enums;

/**
 * 用户状态枚举类
 * User: erin
 * Date: 14-10-16
 * Time: 下午6:04
 */
public enum UserStatus {

    INIT((byte)0); // 初始

    public Byte value;

    UserStatus(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }

}
