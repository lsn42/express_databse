package cn.edu.scau.express.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {

    //useSSL=false 是Mysql数据库的SSL连接问题，提示警告不建议使用没有带服务器身份验证的SSL连接
    private static final String driver="com.mysql.jdbc.Driver";
    private static final String url="jdbc:mysql://localhost:3306/finalex?useSSL=false";
    private static final String user="root";
    private static final String password="123456";
    static{
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){

        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    // 查询使用的关闭
    public static void close(ResultSet resultSet,
                             PreparedStatement preparedStatement, Connection connection) {
        try {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 增删查改的关闭
    public static void close(PreparedStatement preparedStatement,
                             Connection connection) {
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
