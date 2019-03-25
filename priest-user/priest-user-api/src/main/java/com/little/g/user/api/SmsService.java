package com.little.g.user.api;

import com.little.g.common.ResultJson;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * Created by lengligang on 2019/3/22.
 */
public interface SmsService {
    /**
     * 发送短信
     * @param countryCode
     * @param mobile
     * @param deviceId
     * @param smsType
     * @param interType
     * @return
     */
    ResultJson sendSms(@NotEmpty String countryCode, @NotEmpty String mobile, @NotEmpty String deviceId, @NotBlank Integer smsType,@NotBlank Integer interType);
}
