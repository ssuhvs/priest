package com.little.g.common.validate.annatations;

import com.little.g.common.validate.validators.DeviceIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: erin
 * Date: 14-11-18
 * Time: 上午10:58
 */
@Target({PARAMETER,METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DeviceIdValidator.class)
@Documented
public @interface DeviceId {
    String message() default "设备号组成参数有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
