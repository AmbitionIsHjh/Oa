package javaweb.oa.web.action.DeptListServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import javaweb.oa.utils.DButil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet({"/user/login","/user/exit"})
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/user/login".equals(servletPath)) {
            doLogin(request,response);
        }else if ("/user/exit".equals(servletPath)){
            doExit(request,response);
        }
    }

    private void doExit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session对象，销毁session
        HttpSession session = request.getSession(false);
        if (session!=null) {
            //手动销毁session对象
            session.invalidate();
            //退出系统，系统将销毁Cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null){
                for(Cookie cookie : cookies){
                    //设置cookie的有效期为0，表示删除cookie
                    cookie.setMaxAge(0);
                    //设置下一个cookie路径
                    cookie.setPath(request.getContextPath());
                    //响应cookie给浏览器，浏览器会将之前的cookie覆盖
                    response.addCookie(cookie);
                }
            }
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }

    }


    protected void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean success = false;
        //获取用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DButil.getConnection();
           String sql = "select * from t_user where username = ? and password = ?";
            //编译sql
            ps = conn.prepareStatement(sql);
            //给？传值
            ps.setString(1,username);
            ps.setString(2,password);
            //执行sql
            rs = ps.executeQuery();
            //这个结果集只有一条记录
            if (rs.next()) {
                //登陆成功
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.close(conn,ps,rs);
        }

        //登录成功/失败
        if (success) {
            //获取session对象(这里不能加false，因为这里必须获取session)
            HttpSession session = request.getSession();//这个sesion一定不是空的
            session.setAttribute("username",username);
        //登录成功了并且用户确实选择了十天免登录功能
            String f = request.getParameter("f");
            if ("1".equals(f)){
                //创建Cookie对象,存储登录名
                Cookie cookie1 = new Cookie("username",username);
                //在创建一个对象，存储登录密码
                Cookie cookie2 = new Cookie("password",password);
                //设置Cookie的有效期为十天
                cookie1.setMaxAge(60*60*24*10);
                cookie2.setMaxAge(60*60*24*10);
                //设置Cookie的关联路径(只要是访问这应用，浏览器就一定会携带这两个Cookie)
                cookie1.setPath(request.getContextPath());
                cookie2.setPath(request.getContextPath());
                //响应Cookie给浏览器
                response.addCookie(cookie1);
                response.addCookie(cookie2);

            }
            //登陆成功，跳转到用户列表页面
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }else {
            //登录失败，跳转到失败页面
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }

    }
}
