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

@WebServlet("/welecome")
public class WelecomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取Cookie
        Cookie[] cookies = request.getCookies();
        String username = null;
        String password = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("username".equals(name)){
                     username = cookie.getValue();
                }else if ("password".equals(name)){
                     password = cookie.getValue();
                }
            }
        }
        if (username !=null && password != null){
            //验证用户名和密码是否正确
            //正确登录成功
            //错误登陆失败
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            boolean success = false;
            try {
                conn = DButil.getConnection();
                String sql = "select * from t_user where username = ? and password = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1,username);
                ps.setString(2,password);
                rs = ps.executeQuery();
                if (rs.next()) {
                    //登陆成功
                    success = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DButil.close(conn,ps,rs);
            }
            if (success){
                //获取session对象(这里不能加false，因为这里必须获取session)
                HttpSession session = request.getSession();//这个sesion一定不是空的
                session.setAttribute("username",username);
                //登陆成功
                response.sendRedirect(request.getContextPath() + "/dept/list");
            }else {
                //登陆失败
                response.sendRedirect(request.getContextPath() +"/index.jsp");
            }

        }else{
            //因为cookie为空，所以没有登陆过，需要跳转到登陆页面
            response.sendRedirect(request.getContextPath() +"/index.jsp");
        }
    }
}
