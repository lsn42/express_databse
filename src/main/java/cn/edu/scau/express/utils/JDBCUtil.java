package cn.edu.scau.express.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class JDBCUtil {
  // useSSL=false 是Mysql数据库的SSL连接问题，提示警告不建议使用没有带服务器身份验证的SSL连接
  private static final String DRIVER = "com.mysql.jdbc.Driver";
  private static final String URL =
      "jdbc:mysql://172.16.81.238:3306/finalex?useSSL=false";
  private static final String USER = "root";
  private static final String PASSWORD = "20001005";
  static {
    try {
      Class.forName(DRIVER);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    try {
      return DriverManager.getConnection(URL, USER, PASSWORD);
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
