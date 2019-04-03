package com.little.g.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lengligang on 2019/4/3.
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "redirect:/admin/index";
    }

    @RequestMapping("/404")
    public String notFound(){
        return "/jsp/404";
    }
}
