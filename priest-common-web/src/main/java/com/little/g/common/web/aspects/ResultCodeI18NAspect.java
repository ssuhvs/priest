package com.little.g.common.web.aspects;

import com.little.g.common.ResultJson;
import com.little.g.common.web.interceptor.HeaderParamsHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

/**
 * support code message not exist.
 * Created by lengligang on 15/11/26.
 */
@Aspect
public class ResultCodeI18NAspect {
    private static final Logger logger = LoggerFactory.getLogger(ResultCodeI18NAspect.class);

    /*
        modify by wangzhen 2016-02-01 15:15:00
        change @Resource to @Autowired,declare exclusive one message source
     */
    @Autowired
    private MessageSource messageSource;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void pointcut() {
    }

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void modifyReturnMsg(ResultJson result) {
        if (result == null) {
            return;
        }
        if(!StringUtils.isEmpty(result.getM())){
            if(result.getM().startsWith("msg.")){
                result.setM(getI18NResultMsg(result.getM(), HeaderParamsHolder.getHeader().getLocale()));
            }

        }
    }

    private Locale getLocale(String language, String country) {
        try {
            if (StringUtils.isEmpty(language) || StringUtils.isEmpty(country)) {
                return Locale.getDefault();
            }
            return new Locale(language, country);
        } catch (Exception e) {
            return Locale.getDefault();
        }

    }

    private String getI18NResultMsg(Integer code, String language, String country) {
        return messageSource.getMessage(code + "", null, getLocale(language, country));
    }

    private String getI18NResultMsg(String code, Locale locale) {
        try {
            return messageSource.getMessage(code, null, locale);
        } catch (NoSuchMessageException e) {
            logger.warn("result json does not include the code({}): message,plz fix", code);
            return "";
        }
    }
}
