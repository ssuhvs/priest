package com.little.g.admin.web.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lengligang on 2019/4/2.
 */
public class AdminConstants {


    public static final List<String> commonPermission = new ArrayList<>();

    static{
        commonPermission.add("/admin/login");
        commonPermission.add("/admin/login1");
    }

    public static final String SESSION_USER_KEY = "username";
    public static final String SESSION_USER_MENU_KEY = "menulist";
    public static final String SESSION_USER_MENU_MAP_KEY = "menuMap";
    public static final String SESSION_USER_ROLE_KEY = "session_role";
    public static final String NGINX_WWW_ROOT="";
    public static final String PROFILE_STATUS = "0"; //环境状态 1测试。0线上
    public static final String WEB_IFRAME_SCRIPT = "<script type='text/javascript'>" +
            "parent.layer.msg('%s', {icon: 1,time: 1000}, function(){" +
            "   parent.location.reload();" +
            //"   parent.layer.close(parent.layer.getFrameIndex(window.name));" +
            "});" +
            "</script>";
}
