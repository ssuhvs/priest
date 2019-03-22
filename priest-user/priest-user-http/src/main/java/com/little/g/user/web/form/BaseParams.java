package com.little.g.user.web.form;

/**
 * 基类
 * User: erin
 * Date: 14-10-16
 * Time: 下午2:58
 */
public class BaseParams {

    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "BaseParams{" +
                ", uid=" + uid +
                '}';
    }
}
