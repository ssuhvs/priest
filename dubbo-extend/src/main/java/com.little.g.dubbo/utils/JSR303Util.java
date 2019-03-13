package com.little.g.dubbo.utils;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Created by wangzhen on 15/11/25.
 */
public class JSR303Util {


    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    /**
     * API请求参数校验
     *
     * @param requestParams
     * @param <T>
     * @return
     */
    public static <T> String validateParams(T requestParams) {
        return validateParams(requestParams,null);
    }

    /**
     * API请求参数校验
     *
     * @param requestParams
     * @param <T>
     * @return
     */
    public static <T> String validateParams(T requestParams,Class<?> groups) {

        StringBuilder sb = new StringBuilder();

        Set<ConstraintViolation<T>> constraintViolations =null;
        if(groups == null) {
            constraintViolations=validator.validate(requestParams);
        }else {
            constraintViolations=validator.validate(requestParams,groups);
        }
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            sb.append(constraintViolation.getPropertyPath()).append(constraintViolation.getMessage()).append("|");
        }
        String validateResult;
        if (sb.length() > 0) {
            validateResult = sb.deleteCharAt(sb.length() - 1).toString();
        } else {
            validateResult = "";
        }
        return validateResult;
    }

    public static void main(String[] args) {
        String r=validateParams("xxxx", NotBlank.class);
        System.out.print(r);
    }
}
