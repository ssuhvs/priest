package com.little.g.common.enums;

/**
 * 发短信接口的类型
 * User: erin
 * Date: 15/5/15
 * Time: 上午10:36
 */
public enum SmsInterType {

    REG_OR_LOGIN(1),//注册登录接口的发短信
    UPDATE_MOBILE(2),//修改手机号的发短信
    MOBILE_CHECK(5);


    public int value;

    SmsInterType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
