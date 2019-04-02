<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/startbootstrap/css/bootstrap.min.css" rel="stylesheet">
    <title>后台管理系统</title>
    <jsp:include page="common/header2.jsp"/>
    <script type="text/javascript">
        //弹出一个iframe层
        function openEditWindow(btn){

            $('body').css({overflow:'hidden'});
            layer.open({
                type: 2,
                title:0,
                shadeClose: true, //点击遮罩关闭层
                area : ['800px' , '800px'],
                content: '/admin/role/edit?id='+btn.value,
                end:function(){
                    $('body').css({overflow:'auto'});
                }
            });
        };
    </script>
</head>

<body>
<%--<jsp:include page="navbar.jsp" flush="true" />--%>
<div class="layui-tab-content site-demo site-demo-body">
    <!--异步提交表单，伪ajax。-->
    <form id="commonPostForm" method="post" target="commonPostIframe">
    </form>
    <iframe width="0" height="0" style="display:none;" name="commonPostIframe"></iframe>
        <div class="layui-field-box">

                <button type="button" class="layui-btn layui-btn-small" onclick="openEditWindow(this)" id="park-add">添加角色</button>

            <table class="layui-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>角色名称</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${! empty page.result}">
                    <c:forEach var="item" items="${page.result}" varStatus="status">
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.name}</td>

                            <td>
                                <button type="button" onclick="openEditWindow(this)" value="${item.id}" class="layui-btn layui-btn-normal layui-btn-mini">修改</button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>

            <div id="page-content"><ul id="pagination-demo" class="pagination-sm"></ul></div>
        </div>

</div>
<!-- /#wrapper -->
<script type="application/javascript">
    var total = ${page.pageCount};
    var href = '/admin/role/list?currentPage={{number}}';
</script>
<script src="/js/page.js"></script>
</body>
</html>
