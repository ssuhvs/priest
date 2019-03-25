package com.little.g.common.enums;

/**
 * 下发短信验证码方式枚举类
 * User: erin
 * Date: 15/5/8
 * Time: 上午11:17
 */
public enum SmsSendType {

    SMSCODE(1),//短信下发
    VOICECODE(2);//语音下发

    public int value;

    SmsSendType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
