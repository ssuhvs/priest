package com.little.g.user.service.impl;

import com.little.g.user.api.UserService;
import com.little.g.user.dto.UserDTO;
import com.little.g.user.mapper.UserMapper;
import com.little.g.user.model.User;
import com.little.g.user.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by lengligang on 2019/3/26.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
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
}
