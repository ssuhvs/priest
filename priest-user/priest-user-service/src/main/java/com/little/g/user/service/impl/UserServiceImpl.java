package com.little.g.user.service.impl;

import com.little.g.common.ResultJson;
import com.little.g.common.enums.SmsInterType;
import com.little.g.common.enums.UserStatus;
import com.little.g.common.exception.ServiceDataException;
import com.little.g.user.api.SmsService;
import com.little.g.user.api.TokenService;
import com.little.g.user.api.UserService;
import com.little.g.user.dto.UserDTO;
import com.little.g.user.dto.UserDeviceTokenDTO;
import com.little.g.user.dto.UserJoininDTO;
import com.little.g.user.mapper.UserMapper;
import com.little.g.user.mapper.UserMapperExt;
import com.little.g.user.model.User;
import com.little.g.user.model.UserExample;
import com.little.g.user.params.UserUpdateParam;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by lengligang on 2019/3/26.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserMapperExt userMapperExt;
    @Resource
    private TokenService tokenService;
    @Resource
    private SmsService smsService;

    @Resource
    private ValueOperations<String,Long> valueOperations;
    @Override
    public UserDTO getUserInfoByMobile(@NotEmpty String mobile) {
        UserExample e=new UserExample();
        e.or().andMobileEqualTo(mobile);
        e.setOrderByClause("uid desc limit 1");
        List<User> userList=userMapper.selectByExample(e);
        if(CollectionUtils.isEmpty(userList)) return null;
        UserDTO user=new UserDTO();
        BeanUtils.copyProperties(userList.get(0),user);
        return user;
    }

    @Transactional
    @Override
    public ResultJson joinin(@NotEmpty String mobile, @NotBlank @Size(min = 4, max = 6) String smscode, @NotBlank String deviceId, Byte deviceType,String os) {
        //校验短信验证码是否正常
        ResultJson r=new ResultJson();
        boolean smsResult=smsService.checkSms(mobile,deviceId,smscode, SmsInterType.REG_OR_LOGIN.getValue());
        if(!smsResult){
            r.setC(ResultJson.INVALID_PARAM);
            r.setM("msg.mobile.smscode.invalid");
            return r;
        }

        //判断是否首次创建用户
        UserDTO user=getUserInfoByMobile(mobile);
        if(user == null){
            //首次创建
            user=new UserDTO();
            user.setMobile(mobile);
            addUser(user);
        }
        //创建登录token
        UserDeviceTokenDTO token=tokenService.createToken(user.getUid(),deviceId,deviceType,os);
        UserJoininDTO dto=new UserJoininDTO();
        dto.setAccessExpiresIn(token.getAccessExpiresIn());
        dto.setAccessToken(token.getAccessToken());
        dto.setRefreshExpiresIn(token.getRefreshExpiresIn());
        dto.setRefreshToken(token.getRefreshToken());
        dto.setUid(user.getUid());
        dto.setUser(user);

        r.setData(dto);
        return r;
    }


    @Override
    public UserDTO getUserById(Long uid) {
        if(uid == null) return  null;
        User user=userMapper.selectByPrimaryKey(uid);
        if(user == null)return null;

        UserDTO dto=new UserDTO();
        BeanUtils.copyProperties(user,dto);
        return dto;
    }

    @Override
    public boolean update(@Valid UserUpdateParam param) {

        if(param.getUid() == null || param.getUid()<=0) return false;

        User user=new User();
        BeanUtils.copyProperties(param,user);
        user.setUpdateTime(System.currentTimeMillis());

        return userMapper.updateByPrimaryKeySelective(user)>0;
    }

    public Long addUser(UserDTO userDTO){
        User user=new User();
        user.setMobile(userDTO.getMobile());
        user.setUid(maxUid());
        user.setCreateTime(System.currentTimeMillis());
        user.setStatus(UserStatus.INIT.getValue());
        BeanUtils.copyProperties(user,userDTO);
        if(userMapper.insert(user)<0){
            throw new ServiceDataException(ResultJson.SYSTEM_UNKNOWN_EXCEPTION);
        }
        return user.getUid();
    }


    private Long maxUid() {
        Long uid;
        String key = "uid_max";

        //if exist , get and incr
        Number r=valueOperations.get(key);
        if (r!=null) {
            Number incr = valueOperations.increment(key);
            return incr.longValue();
        } else {
            //if not exist , get max uid from db
            uid = userMapperExt.maxUid();
            if(uid==null || uid <=0){
                uid=10000l;
            }
            uid=uid+1;
            valueOperations.set(key,(uid));
        }
        return uid;
    }
}
