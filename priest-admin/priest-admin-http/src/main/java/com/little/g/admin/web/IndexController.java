package com.little.g.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lengligang on 2019/4/1.
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "/index";
    }


    @RequestMapping("/admin/index")
    public String adminIndex(){
        return "/admin/index";
    }
}
