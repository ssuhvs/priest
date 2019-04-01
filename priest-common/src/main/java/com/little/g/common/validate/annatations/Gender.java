package com.little.g.common.validate.annatations;

import com.little.g.common.validate.validators.GenderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: erin
 * Date: 14-10-30
 * Time: 上午9:36
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
@Documented
public @interface Gender {
    String message() default "性别值不正确，只能为0或1";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
