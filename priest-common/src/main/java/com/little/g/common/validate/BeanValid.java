package com.little.g.common.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * bean参数校验
 * Created by mayan on 15-5-15.
 */
@Target({PARAMETER,FIELD})
@Retention(RUNTIME)
@Documented
public @interface BeanValid {

    String message() default "param validator error";
    Class<?> type();

    int code() default 0;
}
