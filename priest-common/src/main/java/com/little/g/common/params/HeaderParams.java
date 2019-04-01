package com.little.g.common.params;

import java.util.Locale;
import java.util.Map;

/**
 * Created by keysilence on 14-10-24.
 */
public class HeaderParams {

    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 用户设备号
     */
    private String deviceId;
    /**
     * 设备类型
     */
    private Byte deviceType;
    /**
     * 设备操作系统
     */
    private String os;
    /**
     * 是否压缩
     */
    private Integer isZip;
    /**
     * 登录Token
     */
    private String token;
    /**
     * Ip地址
     */
    private String ipAddress;
    /**
     * 请求的URI
     *
     * @return
     */
    private String requestURI;

    /**
     * 请求参数信息
     */
    private Map<String, Object> requestParam;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * app的当前版本号
     */
    private String appVersion;

    private Locale locale;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Integer getIsZip() {
        return isZip;
    }

    public void setIsZip(Integer isZip) {
        this.isZip = isZip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }



    public Map<String, Object> getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(Map<String, Object> requestParam) {
        this.requestParam = requestParam;
    }


    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }



}
