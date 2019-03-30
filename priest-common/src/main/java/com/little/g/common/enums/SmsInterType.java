package com.little.g.common.enums;

/**
 * 发短信接口的类型
 * User: erin
 * Date: 15/5/15
 * Time: 上午10:36
 */
public enum SmsInterType {

    REG_OR_LOGIN((byte)1),//注册登录接口的发短信
    UPDATE_MOBILE((byte)2),//修改手机号的发短信
    MOBILE_CHECK((byte)5);


    public Byte value;

    SmsInterType(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }
}
