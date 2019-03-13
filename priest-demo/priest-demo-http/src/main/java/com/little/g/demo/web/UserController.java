package com.little.g.demo.web;

import com.little.g.common.ResultJson;
import com.little.g.common.params.TimeQueryParam;
import com.little.g.demo.api.UserService;
import com.little.g.demo.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by lengligang on 2019/3/12.
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private MessageSourceUtil messageSourceUtil;

    @RequestMapping("/test")
    public ResultJson test(){

        ResultJson r=new ResultJson();
        r.setData(userService.list(new TimeQueryParam()));

        return r;
    }
    @RequestMapping("/add")
    public ResultJson testAdd(@Valid UserDTO user){
        ResultJson r=new ResultJson();
//        UserDTO user=new UserDTO();
//        user.setCreateTime(System.currentTimeMillis());
//        user.setAge(2);
//        userService.add(user);

        r.putD("test",messageSourceUtil.getMessage("test",null));

        return r;
    }

    @RequestMapping("/del")
    public ResultJson testDel(){
        ResultJson r=new ResultJson();
        userService.delete(null);
        return r;
    }
}
