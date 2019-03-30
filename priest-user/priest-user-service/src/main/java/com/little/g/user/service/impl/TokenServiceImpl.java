package com.little.g.user.service.impl;

import com.little.g.common.enums.DeviceTypeEnum;
import com.little.g.user.api.TokenService;
import com.little.g.user.dto.UserDeviceTokenDTO;
import com.little.g.user.mapper.UserDeviceTokenMapper;
import com.little.g.user.model.UserDeviceToken;
import com.little.g.user.model.UserDeviceTokenExample;
import com.little.g.user.service.common.RedisConstants;
import com.little.g.common.enums.TokenVersion;
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
    public UserDeviceTokenDTO createToken(@NotBlank @Min(1) Long uid, @NotBlank @Size(min = 3, max = 50) String deviceId, @NotBlank Byte deviceType, @Size(min = 1, max = 100) String os) {


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
                redisTemplate.delete(getTokenKey(token.getAccessToken()));
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


    String getTokenKey(String token){
        return String.format("%s%s", RedisConstants.TOKEN_PREFIX,token);
    }


}
