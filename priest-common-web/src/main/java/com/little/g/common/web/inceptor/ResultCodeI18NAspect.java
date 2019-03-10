package com.little.g.common.web.inceptor;

import com.little.g.common.ResultJson;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractMessageSource;

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
    private AbstractMessageSource messageSource;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void pointcut() {
    }

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void modifyReturnMsg(ResultJson result) {
        if (result == null) {
            return;
        }
        if (result.getC() != 0) {
            result.setM(getI18NResultMsg(result.getC(), Locale.CHINA));
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

    private String getI18NResultMsg(Integer code, Locale locale) {
        try {
            return messageSource.getMessage(code + "", null, locale);
        } catch (NoSuchMessageException e) {
            logger.warn("result json does not include the code({}): message,plz fix", code);
            return "";
        }
    }
}
