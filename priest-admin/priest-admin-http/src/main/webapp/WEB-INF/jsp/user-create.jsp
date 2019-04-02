<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>后台管理系统</title>
    <jsp:include page="common/header2.jsp"/>
</head>

<body style="margin-top: 30px;">
    <div id="page-wrapper">

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">

                    <div class="panel-body">
                        <fieldset class="layui-elem-field">
                            <legend>用户管理</legend><br/>
                        <form method="POST"  class="layui-form layui-form-pane" role="form" action="/admin/user/create">
                            <input type="hidden" name="id" value="${adminUser.id}"/>

                            <div class="layui-form-item">
                                <label class="layui-form-label">用户名</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="username" value="${adminUser.username}" lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">手机号</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="mobile" value="${adminUser.mobile}" lay-verify="phone" placeholder="请输入手机号" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" name="password" placeholder="密码" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">所属角色</label>
                                <div class="layui-input-inline">
                                    <select name="roleId" id="roleId">
                                        <option value="0" >待定</option>
                                        <c:forEach var="role" items="${roleList}" varStatus="status">
                                            <c:choose>
                                                <c:when test="${role.id eq adminUser.roleId}">
                                                    <option value="${role.id}" selected="">${role.name}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${role.id}">${role.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>


                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <button class="layui-btn" lay-submit="" lay-filter="demo1" type="submit">保 存</button>
                                </div>
                            </div>
                        </form>
</fieldset>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->
        </div>

    </div>
</body>
</html>
