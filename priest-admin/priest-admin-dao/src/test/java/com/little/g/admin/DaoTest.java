package com.little.g.admin;

import com.little.g.admin.mapper.AdminUserMapper;
import com.little.g.admin.model.AdminUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lengligang on 2019/4/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/*.xml")
public class DaoTest {
    @Resource
    private AdminUserMapper adminUserMapper;


    @Test
    public void testGet(){
        List<AdminUser> list= adminUserMapper.selectAll();
        System.out.print(list);
    }
}

