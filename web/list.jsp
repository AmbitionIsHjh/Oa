<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>部门列表页面</title>
<%--    base标签代替  http://localhost:8080/oa/  下面的代码就可以省略${pageContext.request.contextPath}不写 --%>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>
<%--显示用户名，欢迎谁谁谁--%>
    <h3>欢迎${username}</h3>
    <a href="user/exit">[安全退出系统]</a>
    <script type='text/javascript'>
            function del(dno){
        if (window.confirm('亲！删了不可恢复噢！')){
            document.location.href = "${pageContext.request.contextPath}/dept/delete?deptno=" + dno;
        }
    }
    </script>

    <h1 align="center">部门列表</h1>
    <hr>
    <table border="1px" align="center" width="50%">
        <tr>
            <th>序号</th>
            <th>部门编号</th>
            <th>部门名称</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${deptList}" var="dept" varStatus="deptStatus">
            <tr>
                <td>${deptStatus.count}</td>
                <td>${dept.deptno}</td>
                <td>${dept.dname}</td>
                <td>
                    <a href="javascript:void(0)" onclick="del(${dept.deptno})">删除</a>
                    <a href="${pageContext.request.contextPath}/dept/detail?f=edit&dno=${dept.deptno}">修改</a>
                    <a href="${pageContext.request.contextPath}/dept/detail?f=detail&dno=${dept.deptno}">详情</a>
                </td>
            </tr>
        </c:forEach>


    </table>
    <hr>
    <a  href="${pageContext.request.contextPath}/add.jsp">新增部门</a>
</body>
</html>