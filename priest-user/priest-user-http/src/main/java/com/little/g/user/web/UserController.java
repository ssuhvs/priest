package com.little.g.user.web;

import com.little.g.common.ResultJson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lengligang on 2019/3/12.
 */
@RequestMapping("/test")
@RestController
public class UserController {
    @RequestMapping("/login")
    public ResultJson testLogin(){
        ResultJson r = new ResultJson();
        return r;
    }
}
