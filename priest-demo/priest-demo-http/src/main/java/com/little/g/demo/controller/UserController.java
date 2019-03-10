package com.little.g.demo.controller;

import com.little.g.common.ResultJson;
import com.little.g.common.dto.ListResultDTO;
import com.little.g.common.params.TimeQueryParam;
import com.little.g.demo.api.UserService;
import com.little.g.demo.dto.UserDTO;
import com.little.g.demo.service.TestService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by llg on 2019/3/10.
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private TestService testService;

    @RequestMapping("/test")
    private ResultJson test(){

        ResultJson r=new ResultJson();
        TimeQueryParam queryParam = new TimeQueryParam();
        ListResultDTO<UserDTO> list= userService.list(queryParam);
        r.setData(list);
        return r;
    }
    @RequestMapping("/test2")
    public ResultJson test2(){
        ResultJson r=new ResultJson();
        r.putD("test",testService.test());
        return r;
    }
}
