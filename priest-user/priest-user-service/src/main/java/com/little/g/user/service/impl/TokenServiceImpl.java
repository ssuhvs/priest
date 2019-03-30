package com.little.g.user.service.impl;

import com.little.g.common.ResultJson;
import com.little.g.common.enums.DeviceTypeEnum;
import com.little.g.common.enums.TokenVersion;
import com.little.g.common.exception.ServiceDataException;
import com.little.g.user.api.TokenService;
import com.little.g.user.dto.TokenCache;
import com.little.g.user.dto.UserDeviceTokenDTO;
import com.little.g.user.mapper.UserDeviceTokenMapper;
import com.little.g.user.model.UserDeviceToken;
import com.little.g.user.model.UserDeviceTokenExample;
import com.little.g.user.service.common.RedisConstants;
import com.little.g.user.token.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by lengligang on 2019/3/29.
 */

@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    int version = TokenVersion.VERSION_2.getValue();

    @Resource
    private UserDeviceTokenMapper userDeviceTokenMapper;

    @Resource
    private RedisTemplate<String,UserDeviceTokenDTO> redisTemplate;

    @Override
    public UserDeviceTokenDTO createToken(@NotBlank @Min(1) Long uid, @NotBlank @Size(min = 3, max = 50) String deviceId, @NotBlank Byte deviceType, @Size(min = 1, max = 100) String os){
        return createToken(uid,deviceId,deviceType,os,false);
    }


    private UserDeviceTokenDTO createToken(@NotBlank @Min(1) Long uid, @NotBlank @Size(min = 3, max = 50) String deviceId, @NotBlank Byte deviceType, @Size(min = 1, max = 100) String os,boolean refresh) {


        long curTime = System.currentTimeMillis();
        int time = (int) (curTime / 1000);
        String accessToken = TokenUtil.generatorToken(uid, version, time);
        String refreshToken = TokenUtil.generatorToken(uid, version, time + 1);

        UserDeviceTokenDTO  userDeviceTokenDTO = new UserDeviceTokenDTO(uid, deviceId, accessToken);
        if (deviceType != null && DeviceTypeEnum.checkKeyIsExist(deviceType)) {
            userDeviceTokenDTO.setDeviceType(deviceType);
        } else {
            //默认为手机端
            userDeviceTokenDTO.setDeviceType(DeviceTypeEnum.MOBILE.getValue());
        }
        userDeviceTokenDTO.setRefreshToken(refreshToken);
        userDeviceTokenDTO.setOs(os);


        userDeviceTokenDTO.setAccessExpiresIn(curTime + TimeUnit.DAYS.toMillis(30));
        userDeviceTokenDTO.setRefreshExpiresIn(curTime + TimeUnit.DAYS.toMillis(60));

        UserDeviceTokenExample example = new UserDeviceTokenExample();
        //同一个设备只能有一台登录
        example.or().andDeviceIdEqualTo(deviceId);
        //同类型设备只能有一台
        example.or().andUidEqualTo(uid).andDeviceTypeEqualTo(deviceType);
        example.setOrderByClause("id desc limit 20");

        List<UserDeviceToken> tokenList =  userDeviceTokenMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(tokenList)){
            UserDeviceTokenExample updateExample = new UserDeviceTokenExample();
            updateExample.or().andIdIn(tokenList.stream().map(token->token.getId()).collect(Collectors.toList()));
            userDeviceTokenMapper.deleteByExample(updateExample);
            //清理redis缓存
            for(UserDeviceToken token:tokenList){
                if(token.getDeviceId().equals(deviceId)){
                    //刷新token 使久token 30s 内有效
                    token.setAccessExpiresIn(System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(30));
                    token.setRefreshExpiresIn(0l);
                    UserDeviceTokenDTO dto=new UserDeviceTokenDTO();
                    BeanUtils.copyProperties(token,dto);
                    redisTemplate.opsForValue().set(getTokenKey(token.getAccessToken()),dto,30,TimeUnit.SECONDS);
                }else {
                    redisTemplate.delete(getTokenKey(token.getAccessToken()));
                }
            }

        }

        UserDeviceToken token=new UserDeviceToken();
        BeanUtils.copyProperties(userDeviceTokenDTO,token);

        if(userDeviceTokenMapper.insert(token)>0){
            //
            userDeviceTokenDTO.setId(token.getId());
            redisTemplate.opsForValue().set(getTokenKey(token.getAccessToken()),userDeviceTokenDTO,30,TimeUnit.DAYS);
        }


        return userDeviceTokenDTO;
    }

    @Override
    public TokenCache verify(@NotBlank @Size(min = 3, max = 50) String deviceId, @NotBlank @Size(min = 3, max = 50) String token) {

        TokenCache tokenCache=new TokenCache();
        tokenCache.setDeviceId(deviceId);
        UserDeviceTokenDTO userDeviceTokenDTO=redisTemplate.opsForValue().get(getTokenKey(token));
        if(userDeviceTokenDTO == null){

            //数据库查询
            userDeviceTokenDTO=getDeviceToken(deviceId,token);

            if(userDeviceTokenDTO == null){
                return tokenCache;
            }

            redisTemplate.opsForValue().set(getTokenKey(token),userDeviceTokenDTO,30,TimeUnit.DAYS);
        }

        tokenCache.setUid(userDeviceTokenDTO.getUid());
        if(userDeviceTokenDTO.getAccessExpiresIn()>System.currentTimeMillis()){
            tokenCache.setLogin(true);
        }

        return tokenCache;
    }


    private UserDeviceTokenDTO getDeviceToken(String deviceId,String token){
        UserDeviceTokenExample example=new UserDeviceTokenExample();
        example.or().andDeviceIdEqualTo(deviceId)
                .andAccessTokenEqualTo(token);
        example.setOrderByClause("id desc limit 1");
        List<UserDeviceToken> deviceTokenList=userDeviceTokenMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(deviceTokenList)){
            return null;
        }
        UserDeviceTokenDTO dto=new UserDeviceTokenDTO();
        BeanUtils.copyProperties(deviceTokenList.get(0),dto);
        return dto;
    }

    @Override
    public UserDeviceTokenDTO refreshToken(@NotBlank @Min(1) Long uid, @NotBlank @Size(min = 3, max = 50) String deviceId, @NotBlank Byte deviceType, @Size(min = 1, max = 100) String os, String refreshToken) {

        UserDeviceTokenExample example=new UserDeviceTokenExample();
        example.or().andUidEqualTo(uid)
                .andDeviceIdEqualTo(deviceId)
                .andDeviceTypeEqualTo(deviceType)
                .andRefreshTokenEqualTo(refreshToken);
        example.setOrderByClause("id desc limit 1");

        List<UserDeviceToken> deviceTokenList=userDeviceTokenMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(deviceTokenList)){
            throw new ServiceDataException(ResultJson.INVALID_PARAM,"msg.user.refresh.invalid");
        }
        UserDeviceToken token=deviceTokenList.get(0);

        if(token.getRefreshExpiresIn() != null && token.getRefreshExpiresIn()>System.currentTimeMillis()){
            return  createToken(uid,deviceId,deviceType,os,true);
        }

        throw new ServiceDataException(ResultJson.INVALID_PARAM,"msg.user.refresh.invalid");
    }

    @Override
    public boolean logout(@NotBlank @Min(1) Long uid, @NotBlank @Size(min = 3, max = 50) String deviceId, @NotBlank Byte deviceType, @Size(min = 1, max = 100) String os) {
        UserDeviceTokenExample example=new UserDeviceTokenExample();
        example.or().andUidEqualTo(uid)
                .andDeviceIdEqualTo(deviceId)
                .andDeviceTypeEqualTo(deviceType);
        example.setOrderByClause("id desc limit 1");
        List<UserDeviceToken> deviceTokenList=userDeviceTokenMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(deviceTokenList)){
            return true;
        }
        UserDeviceToken token=deviceTokenList.get(0);

        redisTemplate.delete(getTokenKey(token.getAccessToken()));

        return userDeviceTokenMapper.deleteByPrimaryKey(token.getId())>0;
    }


    String getTokenKey(String token){
        return String.format("%s%s", RedisConstants.TOKEN_PREFIX,token);
    }




}
