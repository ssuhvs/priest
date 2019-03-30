package com.little.g.common.validate.validators;


import com.little.g.common.enums.DeviceTypeEnum;
import com.little.g.common.validate.annatations.DeviceType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * User: erin
 * Date: 15/5/29
 * Time: 下午8:23
 */
public class DeviceTypeValidator implements ConstraintValidator<DeviceType, Byte> {
    @Override
    public void initialize(DeviceType deviceType) {

    }

    @Override
    public boolean isValid(Byte deviceType, ConstraintValidatorContext constraintValidatorContext) {
        if (deviceType == null) {
            return true;
        }
        if (DeviceTypeEnum.checkKeyIsExist(deviceType)) {
            return true;
        }
        return false;
    }
}
