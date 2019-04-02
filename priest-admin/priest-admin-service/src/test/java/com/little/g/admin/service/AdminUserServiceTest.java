package com.little.g.admin.service;

import com.little.g.admin.dto.AdminUserDTO;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by lengligang on 2019/4/2.
 */
public class AdminUserServiceTest extends BaseTest {
    @Resource
    private AdminUserService adminUserService;

    @Test
    public void testUserQuesty(){
        AdminUserDTO admin= adminUserService.findUserByMobile("15201008961");
        System.out.print(admin);
    }
}
