package cn.edu.scau.express.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import cn.edu.scau.express.utils.JDBCUtil;

public class OrderDAO {
  public static final String INSERT_ORDER =
      "insert into `order` values(1 + (select * from(select count(*) from `order`) intermediate_table), ?, ?, ?, ?, ?, now(), date_add(now(), interval ? day), ?);";

  public void insertOrder(int id, String s, String d, String t, double w,
      double tm, int f) {
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(OrderDAO.INSERT_ORDER);
      ps.setInt(1, id);
      ps.setString(2, s);
      ps.setString(3, d);
      ps.setString(4, t);
      ps.setDouble(5, w);
      ps.setDouble(6, tm);
      ps.setInt(7, f);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
