package com.little.g.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by lengligang on 2019/3/25.
 */

@RunWith(SpringJUnit4ClassRunner.class)
//根据自己的配置文件路径进行修改
@ContextConfiguration(locations = "classpath*:/META-INF/spring/*.xml")
public class RedisTest {

    @Resource
    private ValueOperations<String, String> valueOperations;

    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate(){
        valueOperations.set("test","xxxx",1000);
        String r=valueOperations.get("test");
        Assert.assertEquals(r,"xxxx");

    }

}
