package com.little.g.common.web.interceptor;

import com.google.common.base.Strings;
import com.little.g.common.enums.DeviceTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by keysilence on 14-10-24.
 */
public class HeaderInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HeaderInterceptor.class);

    /**
     * This implementation always returns {@code true}.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        /**
         * 用户设备号
         */
        String deviceIdHeader = request.getHeader("deviceId");
        /**
         * 设备操作系统
         */
        String osHeader = request.getHeader("os");
        /**
         * 当前app的版本号
         */
        String appVersion = request.getHeader("appVersion");
        /**
         * 是否压缩
         */
        String isZipHeader = request.getHeader("isZip");
        /**
         * 接口URI
         */
        String uri = request.getRequestURI();
        /**
         * 设备类型
         */
        String deviceType = request.getHeader("deviceType");


        Integer isZip = StringUtils.isEmpty(isZipHeader) ? null : Integer.valueOf(isZipHeader);


        /**
         * IP地址
         */
        String xri = request.getHeader("X-Real-IP");
        String xff = request.getHeader("X-Forwarded-For");
        String ipAddress = (xri != null && !"".equals(xri)) ? xri : xff;

        Map<String, Object> param = new HashMap<>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            param.put(name, request.getParameter(name));
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            int size = cookies.length;
            for (int i = 0; i < size; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("os") && Strings.isNullOrEmpty(osHeader)) {
                    osHeader = cookie.getValue();
                } else if (cookie.getName().equals("appVersion") && Strings.isNullOrEmpty(appVersion)) {
                    appVersion = cookie.getValue();
                } else if (cookie.getName().equals("isZip") && Strings.isNullOrEmpty(isZipHeader)) {
                    isZipHeader = cookie.getValue();
                } else if (cookie.getName().equals("ip") && Strings.isNullOrEmpty(ipAddress)) {
                    ipAddress = cookie.getValue();
                } else if (cookie.getName().equals("deviceId") && Strings.isNullOrEmpty(deviceIdHeader)) {
                    deviceIdHeader = cookie.getValue();
                } else if (cookie.getName().equals("deviceType") && Strings.isNullOrEmpty(deviceType)) {
                    deviceType = cookie.getValue();
                }
            }
        }


        if (Strings.isNullOrEmpty(deviceType)) {
            deviceType = String.valueOf(DeviceTypeEnum.MOBILE.getValue());
        }
        HeaderParamsHolder.getHeader().setDeviceType(Integer.valueOf(deviceType));
        HeaderParamsHolder.getHeader().setRequestURI(uri);
        HeaderParamsHolder.getHeader().setDeviceId(deviceIdHeader);
        HeaderParamsHolder.getHeader().setOs(osHeader);
        HeaderParamsHolder.getHeader().setIsZip(isZip);
        HeaderParamsHolder.getHeader().setIpAddress(ipAddress);
        HeaderParamsHolder.getHeader().setAppVersion(appVersion);
        HeaderParamsHolder.getHeader().setRequestParam(param);



        Locale locale = request.getLocale();
        if (locale == null) {
            locale = Locale.CHINA; // CHINESE only language
        }
        HeaderParamsHolder.getHeader().setLocale(locale);

        return true;
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HeaderParamsHolder.setHeader(null);
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }

}
