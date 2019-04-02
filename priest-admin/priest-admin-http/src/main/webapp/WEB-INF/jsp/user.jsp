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
  <link href="/startbootstrap/css/bootstrap.min.css" rel="stylesheet">
  <title>后台管理系统</title>
  <script type="text/javascript">
    //弹出一个iframe层
    function openEditWindow(btn){
      layer.open({
        type: 2,
        title: 0,
        shadeClose: true, //点击遮罩关闭层
        area : ['380px' , '450px'],
        content: '/admin/user/edit?id='+btn.value
      });
    };
  </script>
</head>

<body>

<jsp:include page="common/header2.jsp" flush="true" />
<div class="wrapper wrapper-content animated fadeInRight">
            <!--异步提交表单，伪ajax。-->
            <form id="commonPostForm" method="post" target="commonPostIframe">
            </form>
            <iframe width="0" height="0" style="display:none;" name="commonPostIframe"></iframe>

      <div class="layui-field-box">

              <button class="layui-btn layui-btn-small" onclick="openEditWindow(this)">添加用户</button>

          <table class="layui-table">
                <thead>
                <tr>
                  <th>用户id</th>
                  <th>用户名</th>
                  <th>手机号码</th>
                  <th>状态</th>
                  <th>创建时间</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>

                <c:if test="${! empty pagination.result}">
                  <c:forEach var="user" items="${pagination.result}" varStatus="status">
                    <tr class="active">
                      <td class="h6">${user.id}</td>
                      <td class="h6">${user.username}</td>
                      <td class="h6">${user.mobile}</td>

                      <td class="h6">
                        <c:choose>
                          <c:when test="${user.status == 0}">
                            <font color="#006400">启用</font>
                          </c:when>
                          <c:when test="${user.status == 1}">
                           <font color="red">停用</font>
                          </c:when>
                        </c:choose>
                      </td>
                      <jsp:useBean id="createdDate" class="java.util.Date" />
                      <jsp:setProperty name="createdDate" property="time" value="${user.createTime}" />
                      <td class="h6"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${createdDate}" /></td>
                      <td class="h6">
                        <c:choose>
                          <c:when test="${user.status == 0}">
                            <button type="button" onclick="commonDeleteConfirm('/admin/updateStatus?id=${dubbo.user.id}&status=1')" class="btn btn-xs btn-info">屏蔽</button>
                          </c:when>
                          <c:when test="${user.status == 1}">
                            <button type="button" onclick="commonDeleteConfirm('/admin/updateStatus?id=${dubbo.user.id}&status=0')" class="btn btn-xs btn-info">恢复</button>
                          </c:when>
                        </c:choose>
                        <button type="button" onclick="openEditWindow(this)" value="${user.id}" class="btn btn-xs btn-info">修改</button>
                      </td>
                    </tr>
                  </c:forEach>
                </c:if>
                </tbody>
              </table>
              <div id="page-content"><ul id="pagination-demo" class="pagination-sm"></ul></div>
            </div>
            <!-- /.table-responsive -->

          </div>
<!-- /#wrapper -->
<script type="application/javascript">
    var total = ${pagination.pageCount};
    var href = '/admin/user/list?currentPage={{number}}';
</script>
<script src="/js/page.js"></script>
</body>
</html>
