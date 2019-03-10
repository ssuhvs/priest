package com.little.g.common.web;

import com.little.g.common.web.mapper.UserMapper;
import com.little.g.common.web.model.User;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by lengligang on 2019/3/9.
 */
public class UserMapperTest extends BaseTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void testAdd(){
        User  user = new User();
        user.setMyName("张三");

        int r=userMapper.insert(user);

        Assert.assertTrue(r>0);
    }
}
