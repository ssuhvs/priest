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
        function openEditWindow(btn) {
            layer.open({
                type: 2,
                title: 0,
                shadeClose: true, //点击遮罩关闭层
                area: ['540px', '550px'],
                content: '${uri}/edit?id=' + btn.value
            });
        }
        ;
    </script>
</head>

<body>

<div class="layui-tab-content site-demo site-demo-body">
    <br/>
    <!--异步提交表单，伪ajax。-->
    <form id="commonPostForm" method="post" target="commonPostIframe">
    </form>
    <iframe width="0" height="0" style="display:none;" name="commonPostIframe"></iframe>
    <fieldset class="layui-elem-field layui-field-title">
        <legend>${module}管理</legend>
        <div class="layui-field-box">
            <blockquote class="layui-elem-quote">
                <button class="btn btn-sm btn-success" onclick="openEditWindow(this)">添加${module}</button>
            </blockquote>
            <table class="layui-table">
                <thead>
                <tr>
                    <th>编号</th>
                        <#list attributes as rec>
                            <th>${rec.comment}</th>
                        </#list>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                <c:if test="${'${!'}empty page.result${'}'}">
                    <c:forEach var="entity" items="${'${'}page.result${'}'}" varStatus="status">
                        <tr class="active">
                            <td class="h6">${'${'}entity.id${'}'}</td>
                                <#list attributes as rec>
                                    <td class="h6">${'${'}entity.${rec.name}${'}'}</td>
                                </#list>
                            <td class="h6">
                                <button type="button" onclick="openEditWindow(this)" value="${'${'}entity.id${'}'}"
                                        class="btn btn-xs btn-info">
                                    修改
                                </button>
                                <button type="button"
                                        onclick="commonDeleteConfirm('${uri}/delete?id=${'${'}entity.id${'}'}')"
                                        class="btn btn-xs btn-danger">
                                    删除
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <div id="page-content">
                <ul id="pagination-demo" class="pagination-sm"></ul>
            </div>
        </div>
        <!-- /.table-responsive -->
    </fieldset>
</div>
<!-- /.panel-body -->
</div>

<!-- /#wrapper -->
<script type="application/javascript">
    var total = ${'${'}page.pageCount${'}'};
    var href = '${uri}/list?page={{number}}';
</script>
<script src="/js/page.js"></script>

</body>
</html>
