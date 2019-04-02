<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>popo泡泡</title>
    <link href="/h_layout/css/bootstrap.min.css" rel="stylesheet">
    <link href="/h_layout/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="/h_layout/css/animate.css" rel="stylesheet">
    <link href="/h_layout/css/style.css" rel="stylesheet">
    <link href="/h_layout/css/login.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>
        if(window.top!==window.self){window.top.location=window.location};
    </script>
</head>

<body class="signin">
<div class="signinpanel">
    <div class="row">
        <%--<div class="col-sm-7">--%>
            <%--<div class="signin-info">--%>
                <%--<div class="logopanel m-b">--%>
                    <%--<h1></h1>--%>
                <%--</div>--%>
                <%--<div class="m-b"></div>--%>
                <%--<h4> <strong></strong></h4>--%>
                <%--&lt;%&ndash;<ul class="m-b">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 绿色</li>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 健康</li>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 美味</li>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
                <%--<strong></strong>--%>
            <%--</div>--%>
        <%--</div>--%>
            <div class="col-sm-3"></div>
        <div class="col-sm-5">
            <form action="/admin/login1" method="post">
                <h4 class="no-margins">手机号码登录：</h4>
                <input type="text" class="form-control uname" minlength="2" name="username" placeholder="手机号" required="" aria-required="true" aria-invalid="true"/>

                <input type="password" name="password" class="form-control pword m-b" placeholder="密码" required="" aria-required="true" aria-invalid="true"/>
                ${msg}
                <div class="l-captcha" data-site-key="68766b253c8ae636d8b0e653a3d439da"></div>
                <button class="btn btn-success btn-block">登录</button>
            </form>
        </div>
    </div>

    <div class="signup-footer">

        <center>  &copy; 2018 All Rights Reserved. 北京数字家圆科技有限公司</center>

    </div>
</div>
<script src="//captcha.luosimao.com/static/dist/api.js"></script>
</body>


</html>
