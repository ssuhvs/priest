package com.little.g.user.web.controller;

import com.little.g.common.ResultJson;
import com.little.g.common.encrypt.Coder;
import com.little.g.user.api.SmsService;
import com.little.g.user.api.UserService;
import com.little.g.user.web.common.UserConstants;
import com.little.g.user.web.form.JoininParams;
import com.little.g.user.web.form.MobileSendParams;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by lengligang on 2019/3/22.
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private SmsService smsService;
    @Resource
    private UserService userService;


    @RequestMapping("/sendsms")
    public ResultJson sendSms(@Valid MobileSendParams mobileSendParams){
        ResultJson result=new ResultJson();
        //当前登录版本号为1
        Integer interVersion = mobileSendParams.getLoginVersion();
        if (interVersion == null || interVersion != UserConstants.smsVersion) {
            result.setC(ResultJson.INVALID_PARAM);
            result.setM("msg.sms.version.invalid");
            return result;
        }


        String countryCode=mobileSendParams.getCountryCode();
        String mobile=mobileSendParams.getMobile();
        String deviceId=mobileSendParams.getDeviceId();
        Integer smsType=mobileSendParams.getSmsType();
        Byte interType=mobileSendParams.getInterfaceType();

        String originalCode = mobileSendParams.getCode();
        Long ct = mobileSendParams.getCt();
        String storeCode = Coder.generatorCode(mobile, deviceId, ct);
//        if (!Strings.isNullOrEmpty(originalCode) && !storeCode.equals(originalCode)) {
//            result.setC(ResultJson.INVALID_PARAM);
//            result.setM("msg.sms.version.invalid");
//            return result;
//        }

        return smsService.sendSms(countryCode, mobile, deviceId, smsType, interType);
    }

    @RequestMapping("/joinin")
    public ResultJson joinin(@Valid JoininParams params){
        return userService.joinin(params.getMobile(), params.getSmscode() ,params.getDeviceId(), params.getDeviceType(),null);
    }



}
