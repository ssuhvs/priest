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
  <meta name="description" content="">
  <meta name="author" content="">

  <title>后台管理系统</title>
  <jsp:include page="common/h_header.jsp"/>
</head>

<body>

<div id="wrapper">

  <%-- <jsp:include page="navbar.jsp" flush="true" /%> --%>

  <div id="page-wrapper">

    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">后台用户管理</h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>


    <div class="row">
      <div class="col-lg-12">
        <div class="panel panel-default">
          <!--  <div class="panel-heading">
               DataTables Advanced Tables
           </div> -->
          <!-- /.panel-heading -->
          <div class="panel-body">

            <form method="POST" id="user-psw">
                <input type="hidden" name="id" value="${id}"/>
              <input type="text" name="newPassword" id="newPassword">
              <button class="btn btn-lg btn-primary btn-block" type="submit">保存</button>
            </form>

          </div>
          <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
      </div>
      <!-- /.col-lg-12 -->
    </div>



  </div>

  <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<script src="/js/user.js"></script>
<script src="/js/common.js" ></script>
<script>

  checkClass("user");

</script>
</body>
</html>
