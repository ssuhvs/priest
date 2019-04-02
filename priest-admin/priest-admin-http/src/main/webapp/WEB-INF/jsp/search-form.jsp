<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<form id="searchForm" method="post" action="${param.action}" class="layui-form layui-form-pane">
    <div class="layui-form-item">
        <div class="layui-input-inline" style="width: 100px;">
            <input type="text" id="keyword" name="keyword" placeholder="${param.tips}" value="${param.keyword}"/>
        </div>
        <div class="layui-input-inline" style="width: 100px;">
            <button id="btn-search" class="btn btn-sm btn-primary" type="submit">搜索</button>
        </div>
    </div>
</form>