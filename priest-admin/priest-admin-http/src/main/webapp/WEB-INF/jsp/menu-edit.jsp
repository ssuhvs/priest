<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台管理系统</title>
    <jsp:include page="common/header2.jsp"/>
</head>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>资源新增/更新</legend>
</fieldset>
<center>
        <form role="form" method="POST"  action="/admin/menu/save" class="layui-form layui-form-pane" >
            <input type="hidden" name="id" value="${menu.id}"/>
            <div class="layui-form-item">
                <label class="layui-form-label">名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="name" value="${menu.name}" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">URL</label>
                <div class="layui-input-inline">
                    <input type="text" name="url" value="${menu.url}" lay-verify="required"  placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">PID</label>
                <div class="layui-input-inline">
                    <input type="text" name="pId" value="${menu.pId}" lay-verify="required"  placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">icon</label>
                <div class="layui-input-inline">
                    <input type="text" name="className" value="${menu.className}" class="layui-input">
                </div>
            </div>
            <button class="layui-btn" lay-submit="" lay-filter="demo2" type="submit">保存</button>
        </form>
</center>
</body>
</html>
