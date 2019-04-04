<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台管理系统</title>
    <jsp:include page="../common/header2.jsp"/>
</head>
<body style="margin-top: 30px;">
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <fieldset class="layui-elem-field">
                        <legend>${module}添加/编辑</legend>
                        <br/>
                        <form method="POST" class="layui-form layui-form-pane" role="form" action="${uri}/save">
                            <input type="hidden" name="id" value="${'${'}${paramName}.id${'}'}"/>

                            <#list attributes as rec>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">${rec.comment}</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="${rec.name}" value="${'${'}${paramName}.${rec.name}${'}'}"
                                               placeholder="" ${rec.required?then('required=\'required\'','')}  autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                            </#list>

                            <center>
                            <div class="layui-form-item">
                                    <button class="layui-btn" lay-submit lay-filter="demo1" type="submit">保 存
                                    </button>
                            </div>
                            </center>
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
