package com.little.g.user.web.form;



import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 登录、注册、受邀用户登入系统时的参数类
 * User: erin
 * Date: 14-10-16
 * Time: 下午3:01
 */
public class JoininParams extends BaseMobileParams {

    @NotNull(message = "smscode is not allowed null")
    @Length(min = 4, max = 6)
    protected String smscode;   //短信验证码

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JoininParams{");
        sb.append("smscode='").append(smscode).append('\'');
        sb.append(", deviceId='").append(deviceId).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", deviceType=").append(deviceType);
        sb.append('}');
        return sb.toString();
    }
}
