package com.little.g.user.service.impl;

import com.google.common.collect.Maps;
import com.little.g.common.CommonErrorCodes;
import com.little.g.common.ResultJson;
import com.little.g.common.enums.SmsInterType;
import com.little.g.common.enums.SmsSendType;
import com.little.g.common.exception.ServiceDataException;
import com.little.g.user.api.RateLimitService;
import com.little.g.user.api.SmsService;
import com.little.g.user.api.UserService;
import com.little.g.user.dto.UserDTO;
import com.little.g.user.service.common.Constants;
import com.little.g.user.service.common.RedisConstants;
import com.little.g.user.service.utils.TencentSmsUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lengligang on 2019/3/23.
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {
    @Resource
    private UserService userService;

    @Resource
    private RateLimitService rateLimitService;
    @Value("${env.online}")
    private boolean online;
    @Resource
    private RedisTemplate<String,String>  redisTemplate;
    @Resource
    private MessageSource messageSource;

    @Override
    public ResultJson sendSms(@NotEmpty String countryCode, @NotEmpty String mobile, @NotEmpty String deviceId, @NotNull Integer smsType, @NotNull Byte interType) {
        ResultJson result=new ResultJson();

        if (interType != null && SmsInterType.UPDATE_MOBILE.getValue() == interType) {
            //校验手机号是否存在
            UserDTO user = userService.getUserInfoByMobile(mobile);
            if (user != null) {
                result.setC(ResultJson.INVALID_PARAM);
                result.setM("msg.mobile.already.exist");
                return result;
            }
        }

        if(rateLimitService.upLimit(RedisConstants.SMS_MOBILE_LIMIT+mobile,online? Constants.MAX_CHECKSMS_COUNT_ONEDAY:Constants.MAX_CHECKSMS_COUNT_ONEDAY_TEST,
        TimeUnit.DAYS.toSeconds(1l))){
            //是否达到上线
            result.setC(CommonErrorCodes.SYSTEM_LIMIT);
            result.setM("msg.mobile.sendsms.limit");
            return result;
        }

        HashOperations<String,String,String> hashOperations= redisTemplate.opsForHash();
        String cacheKey = getSmsKey(mobile, interType);
        Map<String,String> cacheMap=hashOperations.entries(cacheKey);

        String randomCode="";
        if (cacheMap != null && cacheMap.size() > 0) {
            randomCode = cacheMap.get("smsCode");
        } else {
            //生成随机数
            randomCode = RandomStringUtils.randomNumeric(6);
        }

        String content=messageSource.getMessage("login.sms.content",new String[]{randomCode}, Locale.CHINA);



        Long sendTime;
        if(CollectionUtils.isEmpty(cacheMap) || (smsType == SmsSendType.SMSCODE.getValue() && (!cacheMap.containsKey("sendTime") || !NumberUtils.isDigits(cacheMap.get("sendTime"))))
                || (smsType == SmsSendType.VOICECODE.getValue() && (!cacheMap.containsKey("sendVoiceTime") || !NumberUtils.isDigits(cacheMap.get("sendVoiceTime"))))){
            sendTime=0l;
        }else {

            if (SmsSendType.VOICECODE.getValue() == smsType) {
                content = randomCode;
                sendTime = Long.parseLong(cacheMap.get("sendVoiceTime"));
            } else {
                sendTime = Long.parseLong(cacheMap.get("sendTime"));
            }
        }

        long curtime = System.currentTimeMillis();
        boolean valid = curtime >= (sendTime + TimeUnit.MINUTES.toMillis(1)); // 1分钟只能发1条短信
        if (!valid) {
            result.setC(CommonErrorCodes.SYSTEM_LIMIT);
            result.setM("msg.mobile.sendsms.minute.limit");
            return result;
        }

        Map<String, String> mapData = Maps.newHashMap();
        mapData.put("smsCode", randomCode);    //初始化验证码
        if (smsType != null && SmsSendType.VOICECODE.getValue() == smsType) {
            mapData.put("sendVoiceTime", Long.toString(System.currentTimeMillis()));   //语音验证码发送时间
        } else {
            mapData.put("sendTime", Long.toString(System.currentTimeMillis()));   //短信验证码发送时间
        }

        hashOperations.putAll(cacheKey,mapData);

        redisTemplate.expire(cacheKey,1,TimeUnit.MINUTES);


        if(online){
            //发送短信息
            TencentSmsUtil.sendSms(mobile,new String[]{randomCode});
        }else {
            result.putD("smsCode",randomCode);
        }

        return result;
    }

    private String getSmsKey(@NotEmpty String mobile, @NotNull Byte interType) {
        return String.format("%d_%s%s",interType, RedisConstants.SMSCODE_MOBILE_MAP,mobile);
    }


    @Override
    public boolean checkSms(@NotBlank @Size(max = 15) String mobile, @NotBlank @Size(min = 3, max = 50) String deviceId, @NotBlank @Size(min = 4, max = 6) String smsCode, Byte interType) {


        Integer current=rateLimitService.getCurrent(RedisConstants.SMS_MOBILE_LIMIT+mobile);
        if(current>(online? Constants.MAX_CHECKSMS_COUNT_ONEDAY:Constants.MAX_CHECKSMS_COUNT_ONEDAY_TEST)){
            throw new ServiceDataException(ResultJson.SYSTEM_UNKNOWN_EXCEPTION,"msg.mobile.sendsms.limit");
        }

        HashOperations<String,String,String> hashOperations= redisTemplate.opsForHash();
        String cacheKey = getSmsKey(mobile, interType);
        Map<String,String> map=hashOperations.entries(cacheKey);
        if(map != null && map.size()>0){
            String serverCode = map.get("smsCode");
            if (StringUtils.isNotEmpty(serverCode) && serverCode.equals(smsCode)) {
                redisTemplate.delete(cacheKey);
                return true;
            }
            redisTemplate.delete(cacheKey);
        }

        return false;
    }
}
