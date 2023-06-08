<%@page contentType="text/html; charset=UTF-8" %>
<%@page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>欢迎使用oa系统</title>
</head>
<body>
    <!--前端超链接发送请求的时候，请求路径以/开始，并且要带着项目名字-->
    <%--    <a href="/oa/dept/list">查看部门列表</a>--%>
    <%--    以下是动态获取应用的根路径--%>
    <%--    <a href="<%=request.getContextPath()%>/list.jsp">查看部门列表</a>--%>
    <%--    执行一个servlet，查询数据库，收集数据--%>
    <%--<a href="<%=request.getContextPath()%>/dept/list">查看部门列表</a>--%>
   <%-- <hr>--%>
    <%--<%=request.getContextPath()%>--%>
    <h1>用户登录</h1>
    <hr>
    <form action="${pageContext.request.contextPath}/user/login" method="post">
        username: <input type="text" name="username" ><br>
        password: <input type="password" name="password"><br>
        <input type="checkbox" name="f" value="1">十天内免登录<br>
        <input type="submit" value="登录">
    </form>




</body>
</html>