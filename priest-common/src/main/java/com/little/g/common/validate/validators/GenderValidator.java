package com.little.g.common.validate.validators;

import com.little.g.common.validate.annatations.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * User: erin
 * Date: 14-10-30
 * Time: 上午9:36
 */
public class GenderValidator implements ConstraintValidator<Gender, Byte> {
    @Override
    public void initialize(Gender constraintAnnotation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isValid(Byte value, ConstraintValidatorContext context) {
        return value == null
                || (value == -1 || value == 1 || value == 0);


    }
}
