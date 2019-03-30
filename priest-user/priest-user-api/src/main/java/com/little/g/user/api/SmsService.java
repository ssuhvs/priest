package com.little.g.user.api;

import com.little.g.common.ResultJson;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
    ResultJson sendSms(@NotEmpty String countryCode, @NotEmpty String mobile, @NotEmpty String deviceId, @NotBlank Integer smsType,@NotBlank Byte interType);

    /**
     * 校验验证码合法性
     * @param mobile
     * @param deviceId
     * @param smsCode
     * @param interType
     * @return
     */
    boolean checkSms(@NotBlank @Size(max = 15) String mobile, @NotBlank @Size(min = 3, max = 50) String deviceId,
                     @NotBlank @Size(min = 4, max = 6) String smsCode, Byte interType);
}
