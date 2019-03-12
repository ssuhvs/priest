package com.little.g.demo.web;

import com.little.g.common.ResultJson;
import com.little.g.common.params.TimeQueryParam;
import com.little.g.demo.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lengligang on 2019/3/12.
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public ResultJson test(){

        ResultJson r=new ResultJson();
        r.setData(userService.list(new TimeQueryParam()));

        return r;
    }
}
