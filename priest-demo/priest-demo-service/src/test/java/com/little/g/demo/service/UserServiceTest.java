package com.little.g.demo.service;

import com.little.g.demo.BaseTest;
import com.little.g.demo.api.UserService;
import com.little.g.demo.dto.UserDTO;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by lengligang on 2019/3/9.
 */
public class UserServiceTest  extends BaseTest{
    @Resource
    private UserService userService;
    @Test
    public void testAdd(){
        UserDTO dto= new UserDTO();
        dto.setCreateTime(System.currentTimeMillis());
        Assert.assertTrue(userService.add(dto));
    }
}
