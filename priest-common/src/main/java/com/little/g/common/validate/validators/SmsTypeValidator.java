package com.little.g.common.validate.validators;

import com.little.g.common.validate.annatations.SmsType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * User: erin
 * Date: 15/5/29
 * Time: 下午8:10
 */
public class SmsTypeValidator implements ConstraintValidator<SmsType, Integer> {
    @Override
    public void initialize(SmsType smsType) {

    }

    @Override
    public boolean isValid(Integer smsType, ConstraintValidatorContext constraintValidatorContext) {
        if (smsType == null) {
            return true;
        }
        if (smsType == 1 || smsType == 2 || smsType == 3 || smsType == 4 || smsType == 5) {
            return true;
        }
        return false;
    }
}
