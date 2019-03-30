package com.little.g.user.service;

import com.little.g.user.BaseTest;
import com.little.g.user.api.SmsService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by llg on 2019/3/24.
 */
public class SmsServiceTest extends BaseTest {
    @Resource
    private SmsService smsService;
    @Test
    public void testSendSms(){
        smsService.sendSms("86","15201008961","A15201008961", 1,(byte)1);
    }
}
