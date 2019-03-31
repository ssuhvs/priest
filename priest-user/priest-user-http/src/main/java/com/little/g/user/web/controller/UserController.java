package com.little.g.user.web.controller;

import com.little.g.common.ResultJson;
import com.little.g.common.encrypt.Coder;
import com.little.g.common.params.HeaderParams;
import com.little.g.common.web.interceptor.HeaderParamsHolder;
import com.little.g.user.api.SmsService;
import com.little.g.user.api.TokenService;
import com.little.g.user.api.UserService;
import com.little.g.user.dto.UserDTO;
import com.little.g.user.dto.UserDeviceTokenDTO;
import com.little.g.user.params.UserUpdateParam;
import com.little.g.user.web.common.UserConstants;
import com.little.g.user.web.form.JoininParams;
import com.little.g.user.web.form.MobileSendParams;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Resource
    private TokenService tokenService;


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


    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public ResultJson get(){
        ResultJson r=new ResultJson();
        Long uid=HeaderParamsHolder.getHeader().getUid();
        UserDTO userDTO=userService.getUserById(uid);
        r.setData(userDTO);
        return r;
    }


    @RequestMapping("/update")
    public ResultJson update(@Valid UserUpdateParam param){
        ResultJson result=new ResultJson();
        Long uid=HeaderParamsHolder.getHeader().getUid();
        param.setUid(uid);
        boolean r=userService.update(param);
        if(!r){
            result.setC(ResultJson.SYSTEM_UNKNOWN_EXCEPTION);
        }
        return result;
    }



    @RequestMapping("/logout")
    public ResultJson logout(){
        HeaderParams headers=HeaderParamsHolder.getHeader();
        ResultJson result=new ResultJson();
        boolean r=tokenService.logout(headers.getUid(),headers.getDeviceId(),headers.getDeviceType(),headers.getOs());
        if(!r){
            result.setC(ResultJson.SYSTEM_UNKNOWN_EXCEPTION);
        }
        return result;
    }

    @RequestMapping(value = "/token/refresh",method = RequestMethod.POST)
    public ResultJson tokenRefresh(@RequestParam String refreshToken){
        HeaderParams headers=HeaderParamsHolder.getHeader();
        UserDeviceTokenDTO userDeviceTokenDTO=tokenService.refreshToken(headers.getUid(),headers.getDeviceId(),headers.getDeviceType(),headers.getOs(),refreshToken);
        ResultJson result=new ResultJson();
        result.setData(userDeviceTokenDTO);
        return result;
    }





}
