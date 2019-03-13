package com.little.g.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by lengligang on 2019/3/13.
 */
@Component
public class MessageSourceUtil {

    private static final Logger log = LoggerFactory.getLogger(MessageSourceUtil.class);

    @Autowired
    private MessageSource messageSource;
    /**
     * 国际化
     *
     * @param result
     * @return
     */
    public String getMessage(String result, Object[] params) {
        String message = "";
        try {
            Locale locale = LocaleContextHolder.getLocale();
            message = messageSource.getMessage(result, params, locale);
        } catch (Exception e) {
            log.error("parse message error! ", e);
        }
        return message;
    }
}
