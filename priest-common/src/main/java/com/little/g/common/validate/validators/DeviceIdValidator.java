package com.little.g.common.validate.validators;

import com.google.common.base.Strings;
import com.little.g.common.validate.annatations.DeviceId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * User: erin
 * Date: 14-11-18
 * Time: 上午11:00
 */
public class DeviceIdValidator implements ConstraintValidator<DeviceId, String> {
    @Override
    public void initialize(DeviceId deviceId) {

    }

    @Override
    public boolean isValid(String deviceId, ConstraintValidatorContext constraintValidatorContext) {
        if (Strings.isNullOrEmpty(deviceId)) {
            return true;
        }
        //设备号只能是数字、大小写英文字母组成，且长度在1到100位之间 todo 兼容冒号
        return deviceId.matches("[:a-zA-Z0-9_.-]{0,99}");

    }

    public static void main(String[] args) {
        String str="-435:4_64565..46456546";
        System.out.println(str.matches("[:a-zA-Z0-9_.-]{0,99}"));
    }
}
