package com.little.g.user.dto;

import java.io.Serializable;

/**
 * Created by lengligang on 2019/3/29.
 */
public class UserJoininDTO implements Serializable {

    private Long uid;

    private String accessToken;
    private Long accessExpiresIn;
    private String refreshToken;
    private Long refreshExpiresIn;

    private UserDTO user;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getAccessExpiresIn() {
        return accessExpiresIn;
    }

    public void setAccessExpiresIn(Long accessExpiresIn) {
        this.accessExpiresIn = accessExpiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public void setRefreshExpiresIn(Long refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserJoininDTO{");
        sb.append("uid=").append(uid);
        sb.append(", accessToken='").append(accessToken).append('\'');
        sb.append(", accessExpiresIn=").append(accessExpiresIn);
        sb.append(", refreshToken='").append(refreshToken).append('\'');
        sb.append(", refreshExpiresIn=").append(refreshExpiresIn);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
