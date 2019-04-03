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
    <jsp:include page="../common/header2.jsp"/>
    <script type="text/javascript">
        //弹出一个iframe层
        function openEditWindow(value) {
            layer.open({
                type: 2,
                title: 0,
                shadeClose: true, //点击遮罩关闭层
                area: ['540px', '550px'],
                content: '/admin/loggerAction/edit?id=' + value
            });
        };
    </script>
</head>

<body>


    <!--异步提交表单，伪ajax。-->
    <form id="commonPostForm" method="post" target="commonPostIframe">
    </form>
    <iframe width="0" height="0" style="display:none;" name="commonPostIframe"></iframe>

        <div class="layui-field-box">
            <table class="layui-table">
                <thead>
                <tr>
                        <th>编号</th>
                        <th>操作人</th>
                        <th>操作描述</th>
                        <th>模块名称</th>
                        <th>操作类型描述</th>
                        <th>操作类型</th>
                        <th>详细内容</th>
                        <th>操作人id</th>
                </tr>
                </thead>
                <tbody>

                <c:if test="${!empty page.result}">
                    <c:forEach var="entity" items="${page.result}" varStatus="status">
                        <tr class="active">
                            <td class="h6">${entity.id}</td>
                                <td class="h6">${entity.staffName}</td>
                                <td class="h6">${entity.comment}</td>
                                <td class="h6">${entity.moduleName}</td>
                                <td class="h6">${entity.operationDescription}</td>
                                <td class="h6">${entity.operationType}</td>
                            <td class="h6"><a href="#" onclick="openEditWindow('${entity.id}')">查看详细</a></td>
                                <td class="h6">${entity.userId}</td>

                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <div id="page-content">
                <ul id="pagination-demo" class="pagination-sm"></ul>
            </div>
        </div>

</div>

<!-- /#wrapper -->
<script type="application/javascript">
    var total = ${page.pageCount};
    var href = '/admin/loggerAction/list?currentPage={{number}}';
</script>
<script src="/js/page.js"></script>

</body>
</html>
