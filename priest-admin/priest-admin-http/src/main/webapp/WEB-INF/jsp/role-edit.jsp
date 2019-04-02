<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台管理系统</title>
    <jsp:include page="common/header2.jsp"/>
</head>
     <div >
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                <legend>角色新增/更新</legend>
            </fieldset>
            <form class="layui-form layui-form-pane" role="form" method="POST" action="/admin/role/save">
                <input type="hidden" name="id" value="${role.id}"/>

                <div class="layui-form-item">
                    <label class="layui-form-label">角色名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" value="${role.name}" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                    <hr>
                    <div class="layui-form-item">

                        <div class="layui-input-block">
                            <c:set value="${ fn:split(role.menus, ',') }" var="checkedMenus" />
<br/>
                <c:forEach var="menuM" items="${menuList}" >
                    <c:if test="${menuM.pId == 0}">
                        <input type="hidden" name="menuId" value="${menuM.id}" title="${menuM.name}" checked="">
                            <p>${menuM.name}</p>
                            <c:forEach var="menu" items="${menuList}" >
                                <c:if test="${menuM.id == menu.pId}">
                                    <c:set var="checked" value=""/>
                                    <c:forEach items="${ checkedMenus }" var="s">
                                        <c:if test="${s == menu.id}"><c:set var="checked" value="checked"/></c:if>
                                    </c:forEach>
                                    <input type="checkbox" name="menuId" value="${menu.id}" title="${menu.name}" ${checked}>
                                </c:if>
                            </c:forEach>
                    </c:if>
                </c:forEach>
                        </div>
                    </div>
                </div>
                <center>
                <button class="layui-btn" lay-submit="" lay-filter="demo2" type="submit">保存</button>
                </center>
            </form>
        </div>
</html>
