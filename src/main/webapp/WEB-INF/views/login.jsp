<%--
  Created by IntelliJ IDEA.
  User: 王帅
  Date: 2019/7/24
  Time: 9:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>管理员登录</title>

    <%
        //获取当前项目的虚拟路径，以/开始不以/结束！！！
        pageContext.setAttribute("APP_PATH",request.getContextPath());
    %>

    <!-- 引入bootstrap样式文件 -->
    <link href="${APP_PATH}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入jquery -->
    <script type="text/javascript" src="${APP_PATH}/bootstrap/jq/jquery-2.1.0.min.js"></script>
    <!-- 引入js文件 -->
    <script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        //切换验证码
        function refreshCode() {
            //1.获取验证码的图片对象
            var vcode = document.getElementById("vcode");

            //2.设置其src属性，加上时间戳
            vcode.src = "${APP_PATH}/admin/checkCode?time="+new Date().getTime();
        }

    </script>
</head>
<body>
<div class="container" style="width: 400px;">
    <h2 style="text-align: center;">管理员登陆</h2>
    <form action="${APP_PATH}/admin/loginsubmit" method="post">
        <div class="form-group">
            <label for="user">用户名：</label>
            <input type="text" name="username" class="form-control" id="user" placeholder="请输入用户名"/>
        </div>

        <div class="form-group">
            <label for="password">密码：</label>
            <input type="password" name="password" class="form-control" id="password" placeholder="请输入密码"/>
        </div>

        <div class="form-inline">
            <label for="vcode">验证码：</label>
            <input type="text" name="verifycode" class="form-control" id="verifycode" placeholder="请输入验证码" style="width: 120px;"/>

            <a href="javascript:refreshCode();">
                <img src="${APP_PATH}/admin/checkCode" title="看不清点击刷新" id="vcode"/>
            </a>

        </div>
        <hr/>

        <div class="form-group" style="text-align: center;">
            <input class="btn btn btn-primary" type="submit" value="登录">
            <a class="btn btn btn-success" href="${APP_PATH}/regist.jsp" >注册</a>
        </div>

    </form>

    <!-- 出错显示的信息框 -->
    <div class="alert alert-warning alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" >
            <span>&times;</span></button>
        <strong>${error}</strong>
    </div>

</div>
</body>
</html>