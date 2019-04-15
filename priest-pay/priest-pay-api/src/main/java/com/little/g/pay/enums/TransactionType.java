package com.little.g.pay.enums;

import java.util.Objects;

/**
 * Created by lengligang on 16/1/18.
 */
public enum TransactionType {

    INCOME((byte)1,"入账"),  //入账
    OUTCOME((byte)2,"出账"), //出账
    FROZE((byte)3,"冻结"),   //冻结
    UNFROZE((byte)4,"解冻"), //解冻
    UNFROZEDEDUCT((byte)5,"解冻扣款"); //解冻扣款

    TransactionType(Byte value, String desc){
        this.value=value;this.desc=desc;
    }

    public static TransactionType valueOf(byte t) {
        for (TransactionType type : values()) {
            if (Objects.equals(type.getValue(), t)) {
                return type;
            }
        }
        return null;
    }

    public Byte value;
    public String desc;

    public String getDesc() {
        return desc;
    }

    public Byte getValue() {
        return value;
    }

    public static TransactionType valueOf(Byte value) {    //    手写的从int到enum的转换函数
        for (TransactionType type : values()) {
            if (Objects.equals(type.getValue(), value)) {
                return type;
            }
        }
        return null;
    }
}
