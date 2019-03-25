package com.little.g.common.validate.annatations;

import com.little.g.common.validate.validators.SmsInterTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: erin
 * Date: 15/5/29
 * Time: 下午8:14
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = SmsInterTypeValidator.class)
@Documented
public @interface SmsInterType {

    String message() default "发短信接口类型不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
