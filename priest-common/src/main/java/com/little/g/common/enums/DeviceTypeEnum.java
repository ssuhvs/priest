package com.little.g.common.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 设备类型枚举类
 * User: erin
 * Date: 14-11-13
 * Time: 下午4:27
 */
public enum DeviceTypeEnum {

    MOBILE(1),//手机
    PAD(2),//平板电脑
    PC(3),//PC机
    TV(4),//电视
    H5(6);//HTML5页面

    public int value;

    //数字与字符串映射字典表
    public static Map<Integer, DeviceTypeEnum> TYPE_MAPPING_DICT = new LinkedHashMap<>();

    static {
        TYPE_MAPPING_DICT.put(MOBILE.getValue(), MOBILE);
        TYPE_MAPPING_DICT.put(PAD.getValue(), PAD);
        TYPE_MAPPING_DICT.put(PC.getValue(), PC);
        TYPE_MAPPING_DICT.put(TV.getValue(), TV);
        TYPE_MAPPING_DICT.put(H5.getValue(), H5);
    }

    public static boolean checkKeyIsExist(Integer code) {
        if (TYPE_MAPPING_DICT.containsKey(code)) {
            return true;
        }
        return false;
    }


    public static DeviceTypeEnum getDeviceTypeEnum(int value) {
        return TYPE_MAPPING_DICT.get(value);
    }

    DeviceTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
