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
    <title>管理员注册</title>

    <%
        //获取当前项目的虚拟路径，以/开始不以/结束！！！
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>

    <!-- 引入bootstrap样式文件 -->
    <link href="${APP_PATH}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入jquery -->
    <script type="text/javascript" src="${APP_PATH}/bootstrap/jq/jquery-2.1.0.min.js"></script>
    <!-- 引入js文件 -->
    <script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>

    <script type="text/javascript">

        $(function () {

            //为输入框绑定事件
            $("#username").change(function () {
                //1.失去焦点获得输入框的内容
                var usernameInput = $(this).val();
                var s = {"id": 1, "username": usernameInput, "password": "123"};
                alert(usernameInput);

                //2.去服务器段校验该用户名是否存在ajax
                $.ajax({
                    url: "${APP_PATH}/admin/checkUsername",        //请求地址
                    type: "POST",                                  //请求方式
                    contentType: "application/json;charset=UTF-8", //参数类型
                    data:JSON.stringify(s),                        //参数
                    dataType: "json",                              //返回数据类型
                    success: function (data) {                     //回调函数
                        // if (data){
                        //     alert("该用户名已经被使用");
                        // }else {
                        //     alert("该用户用可以使用");
                        // }
                    }

                });

            });

        });

    </script>
</head>
<body>
<div class="container" style="width: 400px;">
    <h2 style="text-align: center;">管理员注册</h2>
    <form action="${APP_PATH}/admin/loginsubmit" method="post">
        <div class="form-group">
            <label for="username">用户名：</label>
            <input type="text" name="username" class="form-control" id="username" placeholder="请输入用户名"/>
        </div>

        <div class="form-group">
            <label for="password">密码：</label>
            <input type="password" name="password" class="form-control" id="password" placeholder="请输入密码"/>
        </div>

        <div class="form-group">
            <label for="repassword">确认密码：</label>
            <input type="password" name="repassword" class="form-control" id="repassword" placeholder="请输入密码"/>
        </div>

        <hr/>
        <div class="form-group" style="text-align: center;">
            <input class="btn btn btn-primary" type="submit" value="注册">
        </div>

    </form>


</div>
</body>
</html>