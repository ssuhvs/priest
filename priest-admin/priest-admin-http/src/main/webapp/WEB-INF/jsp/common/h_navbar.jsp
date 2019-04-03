<%@ page import="com.little.g.admin.dto.AdminUserDTO" %>
<%@ page import="com.little.g.admin.dto.SysMenuDTO" %>
<%@ page import="com.little.g.admin.web.util.SessionUtils,com.little.g.admin.web.common.AdminConstants" %>
<%@ page import="java.util.List" %>
<%@ page import="static com.little.g.admin.web.common.AdminConstants.SESSION_USER_KEY" %>
<%@ page import="static com.little.g.admin.web.common.AdminConstants.SESSION_USER_ROLE_KEY" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!--左侧导航开始-->
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="nav-close"><i class="fa fa-times-circle"></i>
    </div>
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element">
                    <%
                        AdminUserDTO user = SessionUtils.getSessionValue(request, SESSION_USER_KEY);
                        String roleName = SessionUtils.getSessionValue(request, SESSION_USER_ROLE_KEY);
                        if (user != null) {
                    %>
                    <span><img alt="image" class="img-circle" src="/images/logo.jpeg" style="width: 70px;height: 70px" /></span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <span class="clear">
                            <span class="block m-t-xs"><strong class="font-bold"><%=user.getUsername()%></strong></span>
                            <span class="text-muted text-xs block"><%=roleName%><b class="caret"></b></span>
                        </span>
                    </a>
                    <%
                        }
                    %>

                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li class="divider"></li>
                        <li><a href="/admin/logout">安全退出</a></li>
                        <li class="divider"></li>
                    </ul>
                </div>
                <div class="logo-element">小G科技
                </div>
            </li>

            <%
                List<SysMenuDTO> menuList = SessionUtils.getSessionValue(request, AdminConstants.SESSION_USER_MENU_KEY);
                if (menuList != null) {
                    for (SysMenuDTO menu : menuList) {
                        if (menu.getpId() == 0 && menu.getSubMenus()!=null && menu.getSubMenus().size() > 0) {
            %>
            <li class="layui-nav-item layui-nav-itemed">
                <a href="#">
                    <i class="<%=menu.getClassName()%>"></i>
                    <span class="nav-label"><%=menu.getName()%></span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <%
                        List<SysMenuDTO> childMenuList = menu.getSubMenus();//SessionUtils.getSessionValue(request, AdminConstants.SESSION_USER_MENU_KEY);
                        if (childMenuList != null && childMenuList.size() > 0) {
                            for (SysMenuDTO childMenu : childMenuList) {
//                                if (childMenu.getpId() == menu.getId().intValue()) {
                    %>
                    <li>
                        <%
                            if (childMenu.getId() == 6) {
                        %>
                        <a class="J_menuItem" href="<%=childMenu.getUrl()%>" data-index="0"><%=childMenu.getName()%></a>
                        <%
                        } else {
                        %>
                        <a class="J_menuItem" href="<%=childMenu.getUrl()%>"><%=childMenu.getName()%></a>
                        <%
                            }
                        %>

                    </li>
                    <%
//                                }
                            }
                        }
                    %>

                </ul>
            </li>
            <%
                        }
                    }
                }
            %>

            <%--<%--%>
                <%--if(user != null){--%>
                    <%--if(user.getId() == 27 || user.getId() == 35 || user.getId() == 30 || user.getId() == 26){%>--%>
                    <%--<li class="layui-nav-item layui-nav-itemed">--%>
                        <%--<a href="#">--%>
                            <%--<i class="glyphicon glyphicon-heart"></i>--%>
                            <%--<span class="nav-label">心愿灯</span>--%>
                            <%--<span class="fa arrow"></span>--%>
                        <%--</a>--%>
                        <%--<ul class="nav nav-second-level">--%>
                            <%--<li><a class="J_menuItem" href="/admin/skyLightMember/list" data-index="0">用户管理</a></li>--%>
                            <%--<li><a class="J_menuItem" href="/admin/skyLightOrders/list" data-index="0">心愿单</a></li>--%>
                            <%--<li><a class="J_menuItem" href="/admin/skyLight/list" data-index="0">灯维护</a></li>--%>
                            <%--<li><a class="J_menuItem" href="/admin/skyLightType/list" data-index="0">类型维护</a></li>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
            <%--<%  }--%>
            <%--}%>--%>
        </ul>
    </div>
</nav>
<!--左侧导航结束-->