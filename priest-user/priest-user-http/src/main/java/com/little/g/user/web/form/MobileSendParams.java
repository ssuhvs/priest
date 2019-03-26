package com.little.g.user.web.form;


import com.little.g.common.enums.SmsSendType;
import com.little.g.common.validate.annatations.SmsInterType;
import com.little.g.common.validate.annatations.SmsType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * User: erin
 * Date: 14-10-17
 * Time: 下午4:16
 */
public class MobileSendParams extends BaseMobileParams {

    @Length(min = 6, max = 6)
    private String captcha;//页面验证码
    @Length(min = 20, max = 40)
    private String code;//接口参数签名  todo 后续一定要传的
    @SmsType
    private Integer smsType = SmsSendType.SMSCODE.getValue();//验证码类型 1：短信验证码 2：语音验证码
    @SmsInterType
    private Integer interfaceType;//发短信的接口类型  1：注册登录接口的发短信 2：修改手机号的发短信，俩接口区别在于后者需要手机号不能存在
    @NotNull
    private Long ct;//时间戳  todo 后续一定要传的
    private Integer loginVersion;//登录版本号

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Integer getLoginVersion() {
        return loginVersion;
    }

    public void setLoginVersion(Integer loginVersion) {
        this.loginVersion = loginVersion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public Integer getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Integer interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Long getCt() {
        return ct;
    }

    public void setCt(Long ct) {
        this.ct = ct;
    }

    @Override
    public String toString() {
        return "MobileSendParams{" +
                "captcha='" + captcha + '\'' +
                ", code='" + code + '\'' +
                ", smsType=" + smsType +
                ", interfaceType=" + interfaceType +
                ", ct=" + ct +
                ", loginVersion=" + loginVersion +
                '}';
    }
}
