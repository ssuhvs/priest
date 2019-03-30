package com.little.g.common.enums;

/**
 * User: liuling
 * Date: 16/3/1
 * Time: 下午6:17
 */
public enum TokenErrorType {

    EXPIRED(1), //token自然过期
    VERSION(2), //不是可支持的token版本
    SELF_CHECK(3); //token自校验失败

    public int value;

    TokenErrorType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
