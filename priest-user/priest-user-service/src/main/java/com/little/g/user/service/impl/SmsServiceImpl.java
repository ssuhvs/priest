package com.little.g.user.service.impl;

import com.little.g.common.ResultJson;
import com.little.g.user.api.SmsService;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by lengligang on 2019/3/23.
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

    @Resource
    private ValueOperations<String, String> valueOperations;

    @Override
    public ResultJson sendSms(@NotEmpty String countryCode, @NotEmpty String mobile, @NotEmpty String deviceId, @NotNull Integer smsType, @NotNull Integer interType) {

        valueOperations.get("test");
        return null;
    }
}
