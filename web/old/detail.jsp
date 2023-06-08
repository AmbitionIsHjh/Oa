<%@ page import="javaweb.oa.bean.Dept" %>
<%@page contentType="text/html; charset=UTF-8" %>
<%
    //从request域中取出数据
    Dept d = (Dept) request.getAttribute("dept");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>部门详情</title>
</head>
<body>
    <h1>部门详情</h1>
    <hr>
    部门编号：<%=d.getDeptno()%> <br>
    部门名称：<%=d.getDname()%> <br>
    部门位置：<%=d.getLoc()%> <br>
    <hr>
    <input type="button" value="返回" onclick="window.history.back()">
</body>
</html>