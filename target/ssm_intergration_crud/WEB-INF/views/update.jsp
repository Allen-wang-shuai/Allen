
<%--
  Created by IntelliJ IDEA.
  User: 王帅
  Date: 2019/7/24
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- HTML5文档-->
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>添加用户</title>

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

</head>
<body>
<div class="container" style="width: 400px;">
    <center><h3>修改员工信息页面</h3></center>
    <form action="${APP_PATH}/employee/updateEmp" method="post">
        <%--隐藏域提交id--%>
        <input type="hidden" name="empId" value="${emp.empId}">

        <div class="form-group">
            <label for="empName">empName：</label>
            <input type="text" class="form-control" id="empName" name="empName" value="${emp.empName}" placeholder="请输入姓名">
        </div>

        <div class="form-group">
            <label>gender：</label>
            <c:if test="${emp.gender=='M'}">
                <input type="radio" name="gender" value="M" checked="checked"/>男
                <input type="radio" name="gender" value="F"/>女
            </c:if>

            <c:if test="${emp.gender=='F'}">
                <input type="radio" name="gender" value="M" />男
                <input type="radio" name="gender" value="F" checked="checked"/>女
            </c:if>
        </div>

        <div class="form-group">
            <label for="email">Email：</label>
            <input type="email" class="form-control" id="email" name="email" value="${emp.email}" placeholder="请输入邮箱地址"/>
        </div>

        <div class="form-group">
            <label for="department">department：</label>
            <select name="dId" class="form-control" id="department">
                <c:if test="${emp.dId==1}">
                    <option value="1" selected>开发部</option>
                    <option value="2">测试部</option>
                </c:if>

                <c:if test="${emp.dId==2}">
                    <option value="1" >开发部</option>
                    <option value="2" selected>测试部</option>
                </c:if>
            </select>
        </div>

        <div class="form-group" style="text-align: center">
            <input class="btn btn-primary" type="submit" value="提交" />
            <input class="btn btn-default" type="reset" value="重置" />
            <input class="btn btn-default" type="button" value="返回" />
        </div>
    </form>
</div>
</body>
</html>