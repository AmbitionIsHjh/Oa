package javaweb.oa.utils;
import java.sql.*;
import java.util.ResourceBundle;

//jdbc的工具类
public class DButil {

    //静态变量：在类加载的时候执行，并且是自上而下的顺序
    //属性资源文件绑定
    private static ResourceBundle bundle = ResourceBundle.getBundle("resources.jdbc");
    //根据属性资源文件的key获取value
    private  static String driver = bundle.getString("driver");
    private  static String url= bundle.getString("url");
    private  static String user= bundle.getString("user");
    private  static String password= bundle.getString("password");

    static  {
        //注册驱动(注册驱动只需要注册一次，放在静态代码块当中，Dbutil类加载的时候执行)
        try {
            //com.mysql.jdbc.Driver是连接数据库的驱动，不能写死，因为以后可能还会连接oracle数据库
            //如果连接oracle数据库还需要修改java代码，显然违背了OCP开闭原则（对扩展开放，对修改关闭）
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        /**
         * 获取数据库连接对象
         * 返回数据库conn连接对象
         */
        //获取连接
        Connection conn = DriverManager.getConnection(url,user,password);
        return conn;
    }

    /**
     * 释放资源
     * @param conn 连接对象
     * @param ps 数据库操作对象
     * @param rs 连接对象
     */
    public static void close(Connection conn, Statement ps, ResultSet rs){
        if (rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}

















