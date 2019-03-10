package com.little.g.demo.service;

import org.springframework.stereotype.Service;

/**
 * Created by llg on 2019/3/10.
 */
@Service("testService")
public class TestServiceImpl implements TestService {
    @Override
    public String test() {
        return "test";
    }
}
