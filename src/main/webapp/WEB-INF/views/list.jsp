<%--
  Created by IntelliJ IDEA.
  User: 王帅
  Date: 2019/8/18
  Time: 9:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>EmployeeList</title>
    <%
        //获取当前项目的虚拟路径，以/开始不以/结束！！！
        pageContext.setAttribute("APP_PATH",request.getContextPath());
    %>
    <!--
    web的路径问题：
    不以/开始的相对路径，找资源，以当前资源的路径为基准，的判断当前资源和目标资源的位置关系，比较容易出问题
    以/开始的相对路径，找资源以服务器的路径为标准(http://localhost:8080)需要加上项目名
                http://localhost:8080
    如果以/开始的话要在/前加上项目路径

    -->
    <!-- 引入bootstrap样式文件 -->
    <link href="${APP_PATH}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入jquery -->
    <script type="text/javascript" src="${APP_PATH}/bootstrap/jq/jquery-2.1.0.min.js"></script>
    <!-- 引入js文件 -->
    <script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>

    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>

    <script>

        function deleteUser(id) {
            //用户安全提示
            if (confirm("您确定要删除么？")){
                //访问路径
                location.href="${APP_PATH}/employee/deleteEmpoyee?empId="+id;
            }
            //取消的话啥事不干
        }

        <%--上面这个方法不能放到下面这个方法里是因为放里面后就找不到这个方法了
            因为是用href="javascript:deleteUser(${user.id});"来生命的点击删除超链接后
            会到javascript中寻找该方法，如果定义到另一个方法中没有直接定义到javascript中
            那么就会找不到这个方法
        --%>

        window.onload = function () {
            //给删除选中按钮添加单击事件
            document.getElementById("delSelected").onclick = function () {
                if (confirm("您确定要删除么？")){
                    var flag = false;
                    //判断是否有选中条目
                    var cbs = document.getElementsByClassName("selectedId");
                    for (var i = 0; i<cbs.length;i++ ) {
                        if (cbs[i].checked) {
                            //只要有一个选中就可以提交了
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        //表单提交
                        document.getElementById("form").submit();
                    }else {
                        alert("请选中您要删除的项");
                    }

                }
                //取消的话啥事不干
            }

            //实现全选和全不选
            //1.获取第一个cb
            document.getElementById("firstCb").onclick = function () {
                //2.获取下边列表所有的cb
                var cbs = document.getElementsByClassName("selectedId");
                // var cbs = document.getElementsByName("selectedId");
                //3.遍历
                for (var i = 0; i <cbs.length; i++){
                    //4.设置这些cbs[i]的状态 = firstCb.checked
                    cbs[i].checked = this.checked;
                }

            }
        }

    </script>

</head>
<body>

    <!-- 效果出不来九成都是bootstrap、js、jquery资源没引入成功，检查路径，或者就是引入路径不对 -->

    <!-- 搭建显示页面 -->
<div class="container">

    <!-- 标题 -->
    <div class="col-md-8">
        <div >
            <h1>SSM-CRUD</h1>
        </div>
    </div>

    <div class="col-md-2 col-md-offset-10">
        <div >
            <a class="btn btn-danger" href="${APP_PATH}/admin/logout">
                <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>退出
            </a>
        </div>
    </div>

    <div style="float: left ">

        <form class="form-inline" action="${APP_PATH}/employee/getLikeEmps" method="post">
            <div class="form-group">
                <label for="exampleInputName2">姓名</label>
                <input type="text" name="empName"  class="form-control" id="exampleInputName2">
            </div>

            <div class="form-group">
                <label for="exampleInputName3">邮箱</label>
                <input type="email" name="email"  class="form-control" id="exampleInputName3">
            </div>

            <div class="form-group">
                <label for="department">部门</label>
                <select name="dId" class="form-control" id="department">
                    <option value=1>开发部</option>
                    <option value=2>测试部</option>
                </select>
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>

    </div>

    <!-- 按钮 -->
    <div style="float: right;margin: 5px">

        <a class="btn btn-success" href="${APP_PATH}/employee/toAddJsp">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
        </a>
        <a class="btn btn-danger" href="javascript:void(0);" id="delSelected">
            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除选中
        </a>

    </div>

    <!-- 显示表格数据 -->
    <div class="row">
        <div class="col-md-12">
            <form id="form" action="${APP_PATH}/employee/deleteSelected" method="post">
            <table class="table table-hover" style="text-align: center">
                <tr>
                    <th><input type="checkbox" id="firstCb"></th>
                    <th>empId</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>operation</th>
                </tr>
                <c:forEach items="${pageInfo.list}" var="emp" varStatus="s">
                    <!--
                    对于标签的身份类型属性要明确：
                        1、有id、class、name三个
                        2、每个标签有自己特有的id属性，不同标签id属性不能相同，所以通过id属性来获取元素时只能获取一个
                        3、各个标签可以有相同的class或者name，这样的话再通过这两个属性获取元素时获取到的元素可以是多个，相当于一个集合
                        4、想实现特有的功能要结合每个属性的特点适当选取
                    -->
                    <tr>
                        <!--
                            请求参数绑定集合类型的数据，该集合必须存在于某个类当中为该类的一个属性，
                            在controller方法中用该类接收springMVC框架会将该集合作为数据为该类的属性赋值，
                            之后我们再通过该类的get方法得到该集合
                            在jsp页面中格式为该类集合名字+角标+集合泛型的类的属性名  其值为集合泛型的类的属性的值
                        -->
                        <th><input type="checkbox" class="selectedId" name="list[${s.index}].empId" value="${emp.empId}"></th>
        <!--
            对于input标签的type的checkbox的值如果选中的话在提交时才会被提交，不选中不会被提交，一定要指定其name和value
            name可用于参数绑定，value是真正需要用到的值。
            对于这里为什么会出现选中两个输出了多于两个的selectedId并且只有两个有值其他的为Null是因为你在选的时候从第几个开始选的原因，
            我们在input的name属性中指定了集合的角标并且从零开始，如果你不从零开始选那么参数绑定集合类型时也会跳过该集合的第零个，放到你
            指定的角标那里，这样的话对于集合来说它是从零开始的输出的时候肯定是从零开始，这样的话你指定角标的前几个你没赋值肯定会输出，
            并且值肯定是Null。要想得到选中的和输出的一样那就从角标从零开始的开始选，并且中间不能间隔这样就一样了。
            这里涉及到了集合的知识。集合和checkbox选没选中放在一起就可以理解了
         -->
                        <th>${emp.empId}</th>
                        <th>${emp.empName}</th>
                        <th>${emp.gender=="M"?"男":"女"}</th>
                        <th>${emp.email}</th>
                        <th>${emp.department.deptName}</th>
                        <th>
                            <a class="btn btn-success btn-sm" href="${APP_PATH}/employee/toUpdateJsp?empId=${emp.empId}">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                            </a>
                            <a class="btn btn-danger btn-sm" href="javascript:deleteUser(${emp.empId});">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
                            </a>
                        </th>
                    </tr>
                </c:forEach>
            </table>
            </form>
        </div>
    </div>
    <!-- 显示分页信息 -->
    <div class="row">
        <!-- 分页文字信息 -->
        <div class="col-md-6">
            当前${pageInfo.pageNum}页，共${pageInfo.pages}页，共${pageInfo.total}条记录
        </div>
        <!-- 分页条信息 -->
        <div class="col-md-6">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li>
                        <a href="${APP_PATH}/employee/emps?pageNumber=1">首页</a>
                    </li>
                    <c:if test="${pageInfo.hasPreviousPage}">
                        <li>
                            <a href="${APP_PATH}/employee/emps?pageNumber=${pageInfo.pageNum-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
                        <c:if test="${page_Num == pageInfo.pageNum}">
                            <li class="active"><a href="#">${page_Num}</a></li>
                        </c:if>
                        <c:if test="${page_Num != pageInfo.pageNum}">
                            <li><a href="${APP_PATH}/employee/emps?pageNumber=${page_Num}">${page_Num}</a></li>
                        </c:if>
                    </c:forEach>
                    <c:if test="${pageInfo.hasNextPage}">
                        <li>
                            <a href="${APP_PATH}/employee/emps?pageNumber=${pageInfo.pageNum+1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <li>
                        <a href="${APP_PATH}/employee/emps?pageNumber=${pageInfo.pages}">末页</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>

</div>
</body>
</html>
