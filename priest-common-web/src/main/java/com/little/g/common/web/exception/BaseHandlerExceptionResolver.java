package com.little.g.common.web.exception;

import com.google.common.base.Strings;
import com.little.g.common.ResultJson;
import com.little.g.common.exception.ServiceDataException;
import com.little.g.common.web.controller.MessageHelper;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static jodd.datetime.JDateTimeDefault.locale;

/**
 * priest spring web base exception handler
 * Created by wangzhen on 5/4/15.
 */
public class BaseHandlerExceptionResolver implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(BaseHandlerExceptionResolver.class);

    @Resource
    private MessageHelper messageHelper;

    private String mimeType = MimeTypeUtils.APPLICATION_JSON_VALUE;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ResultJson resultJson = null;

        try {
            response.setContentType(mimeType);
            PrintWriter printWriter = response.getWriter();

            if (ex instanceof ServiceDataException) {
                int code = ((ServiceDataException) ex).getCode();
                resultJson = messageHelper.getCachedResultJson(code, locale);
                if (resultJson != null) {
                    logger.error("sde code:{} message:{}", code, resultJson.getM());
                } else {
                    logger.error("sde code:{} message:{}", code, "");
                }

                printWriter.println(resultJson);
                printWriter.close();

                return null;
            } else if (ex instanceof RpcException) {
                int code = ((RpcException) ex).getCode();
                // code == 0,return 20000
                if (code > 0) {
                    if (code <= 5) {
                        resultJson = messageHelper.getCachedResultJson(code, locale);
                        if (resultJson != null) {
                            logger.error("dubbo invoke exception, code:{} message:{}", code, resultJson.getM());
                        } else {
                            logger.error("dubbo invoke exception, code:{} message:{}", code, "");
                        }
                    } else {
                        // receive dubbo filter validator msg
                        String msg = ex.getMessage();
                        if (Strings.isNullOrEmpty(msg)) {
                            resultJson = messageHelper.getCachedResultJson(code, msg, locale);
                        } else {
                            resultJson = messageHelper.getCachedResultJson(code, locale);
                        }

                        if (resultJson != null) {
                            logger.error("dubbo filter verify exception, code:{} raw msg:{} message:{}", code, msg, resultJson.getM());
                        } else {
                            logger.error("dubbo filter verify exception code:{} raw msg:{} message:{}", code, msg, "");
                        }
                    }
                }

                printWriter.println(resultJson);
                printWriter.close();

                return null;
            }

            resultJson = messageHelper.getCachedResultJson(ResultJson.SYSTEM_UNKNOWN_EXCEPTION, locale);
            printWriter.println(resultJson);
            printWriter.close();

            return null;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
