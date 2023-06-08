package javaweb.oa.web.action.DeptListServlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javaweb.oa.bean.Dept;
import javaweb.oa.utils.DButil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet({"/dept/list","/dept/detail","/dept/delete","/dept/save","/dept/modify"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //只获取当前的session，获取不到就返回null
        //以下代码被我注释掉是因为在LoginCheckFilter过滤器里面我写了，所以就不用这样写啦
       /* HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null){
            String servletPath = request.getServletPath();
            if ("/dept/list".equals(servletPath)){
                dolist(request,response);
            }else if ("/dept/detail".equals(servletPath)){
                doDetail(request,response);
            }else if ("/dept/delete".equals(servletPath)){
                deDel(request,response);
            }else if ("/dept/save".equals(servletPath)){
                doSave(request,response);
            }else if ("/dept/modify".equals(servletPath)){
                doModify(request,response);
            }
        }else {
            //未登陆过，跳转到登陆页面
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }*/
        //以下代码改写还得写
        String servletPath = request.getServletPath();
        if ("/dept/list".equals(servletPath)){
            dolist(request,response);
        }else if ("/dept/detail".equals(servletPath)){
            doDetail(request,response);
        }else if ("/dept/delete".equals(servletPath)){
            deDel(request,response);
        }else if ("/dept/save".equals(servletPath)){
            doSave(request,response);
        }else if ("/dept/modify".equals(servletPath)){
            doModify(request,response);
        }
    }

    private void doModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取表单中的数据
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        //连接数据库执行更新语句
        Connection conn = null;
        PreparedStatement ps =null;
        int count = 0;
        try {
            conn = DButil.getConnection();
            String sql = "update dept set dname = ?,loc = ? where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,dname);
            ps.setString(2,loc);
            ps.setString(3,deptno);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.close(conn,ps,null);
        }
        if (count==1){
            //更新成功，跳转到部门列表页面，需要执行另一个Servlet  以下代码是转发器
            //request.getRequestDispatcher("/dept/list").forward(request,response);
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else {
            //更新失败
            //request.getRequestDispatcher("/error.html").forward(request,response);

        }
    }

    private void doSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取部门的信息
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        //连接数据库，执行insert语句
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DButil.getConnection();
            String sql = "insert into dept(deptno,dname,loc) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            ps.setString(2,dname);
            ps.setString(3,loc);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DButil.close(conn,ps,null);
        }
        if (count==1){
            //保存成功跳转页面
            request.getRequestDispatcher("/dept/list").forward(request,response);
        }else{
            //保存失败跳转到错误页面
            request.getRequestDispatcher("/error.html").forward(request,response);
        }
    }

    private void deDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取部门编号
        String deptno = request.getParameter("deptno");
        //连接数据库，删除部门
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DButil.getConnection();
            String sql = "delete from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            count = ps.executeUpdate();
            if (count==1){
                //删除成功，重定向到list页面
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath+"/dept/list");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.close(conn,ps,null);
        }
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Dept dept = new Dept();
        //获取部门编号
        String dno = request.getParameter("dno");
        //根据部门编号，获取部门信息，将部门信息封装成咖啡豆，
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取链接
            conn = DButil.getConnection();
            //执行sql语句
            String sql = "select dname,loc from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,dno);
            rs = ps.executeQuery();
            //这个结果集中只有一条记录，不需要while循环
            if (rs.next()){
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                //封装对象（创建对象）

                dept.setDeptno(dno);
                dept.setDname(dname);
                dept.setLoc(loc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.close(conn,ps,rs);
        }
        //这个咖啡豆只有一个，不需要封装，只需要将这个咖啡都放在request域当中即可
        request.setAttribute("dept",dept);
        //request.getRequestDispatcher("/detail.jsp").forward(request,response);
       /* String f = request.getParameter("f");
        if ("m".equals(f)){
            //是转发到修改页面
            request.getRequestDispatcher("/edit.jsp").forward(request,response);
        }else if ("d".equals(f)){
            //转发到详情页面
            request.getRequestDispatcher("/detail.jsp").forward(request,response);
        }*/
        String forward = "/"+request.getParameter("f") + ".jsp";
        request.getRequestDispatcher(forward).forward(request,response);
    }

    private void dolist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //准备一个容器，用来专门存储部门
        ArrayList<Dept> depts = new ArrayList<>();
        //连接数据库,查询所有的部门信息
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取链接
            conn = DButil.getConnection();
            //执行sql语句
            String sql = "select deptno,dname,loc from dept";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            //遍历结果集
            while (rs.next()) {
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                //将以上的零散的数据封装成java对象
                Dept dept = new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);

                //将部门对象放到list集合当中
                depts.add(dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.close(conn,ps,rs);
        }

        //将一个集合放到请求域当中
        request.setAttribute("deptList",depts);
        //转发 不做重定向 路径不需要写项目名字
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }
}

