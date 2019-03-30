package com.little.g.common.dto;

/**
 * User: liuling
 * Date: 16/3/1
 * Time: 下午5:36
 */
public class TokenInfo {

    private boolean isOK;//token是否验证通过

    private Long uid;//用户uid

    private int version;//token版本

    private int errorStatus;//token出错类型

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean isOK) {
        this.isOK = isOK;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(int errorStatus) {
        this.errorStatus = errorStatus;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "isOK=" + isOK +
                ", uid=" + uid +
                ", version=" + version +
                ", errorStatus=" + errorStatus +
                '}';
    }
}
