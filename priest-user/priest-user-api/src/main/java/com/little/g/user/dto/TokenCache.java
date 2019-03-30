package com.little.g.user.dto;

import java.io.Serializable;

/**
 * Created by lengligang on 2019/3/30.
 */
public class TokenCache implements Serializable {
    private String deviceId;
    private boolean login;
    private Long uid;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TokenCache{");
        sb.append("deviceId='").append(deviceId).append('\'');
        sb.append(", login=").append(login);
        sb.append(", uid=").append(uid);
        sb.append('}');
        return sb.toString();
    }
}
