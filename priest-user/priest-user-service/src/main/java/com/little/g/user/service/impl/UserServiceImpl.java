package com.little.g.user.service.impl;

import com.little.g.common.ResultJson;
import com.little.g.common.enums.UserStatus;
import com.little.g.common.exception.ServiceDataException;
import com.little.g.user.api.UserService;
import com.little.g.user.dto.UserDTO;
import com.little.g.user.mapper.UserMapper;
import com.little.g.user.mapper.UserMapperExt;
import com.little.g.user.model.User;
import com.little.g.user.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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

    @Override
    public ResultJson joinin(@NotEmpty String mobile, @NotBlank @Size(min = 4, max = 6) String smscode, @NotBlank String deviceId, Integer deviceType) {
        //判断是否首次创建用户
        UserDTO user=getUserInfoByMobile(mobile);
        if(user == null){
            //首次创建
            Long uid=addUser(user);
            user.setUid(uid);
        }
        //创建登录token
        return null;
    }

    public Long addUser(UserDTO userDTO){
        User user=new User();
        BeanUtils.copyProperties(user,userDTO);
        user.setUid(maxUid());
        user.setCreateTime(System.currentTimeMillis());
        user.setStatus(UserStatus.INIT.getValue());
        if(userMapper.insert(user)<0){
            throw new ServiceDataException(ResultJson.SYSTEM_UNKNOWN_EXCEPTION);
        }
        return user.getUid();
    }


    private Long maxUid() {
        Long uid;
        String key = "uid_max";

        //if exist , get and incr
        Long r=valueOperations.get(key);
        if (r!=null && r>0) {
            uid = valueOperations.increment(key);
        } else {
            //if not exist , get max uid from db
            uid = userMapperExt.maxUid();
            if(uid==null || uid <=0){
                uid=10000l;
            }

            uid = valueOperations.increment(key,uid);
        }
        return uid;
    }
}
