package cn.edu.scau.express.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import cn.edu.scau.express.bean.Order;
import cn.edu.scau.express.utils.JDBCUtil;

public class OrderDAO {

  public static final String INSERT_ORDER =
      "insert into `order` values(?, ?, ?, ?, ?, ?, now(), date_add(now(), interval ? day), ?);";
  public static final String SELECT_BY_ID = "select * from `order` where id=?;";
  public static final String SELECT_MAX_ORDER_ID =
      "select max(`id`) from `order`";

  public int createOrder(int customerId, String source, String destination,
      String type, double weight, double timeliness, double fare) {

    int id = getMaxOrderID() + 1;

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(OrderDAO.INSERT_ORDER);
      ps.setInt(1, id);
      ps.setInt(2, customerId);
      ps.setString(3, source);
      ps.setString(4, destination);
      ps.setString(5, type);
      ps.setDouble(6, weight);
      ps.setDouble(7, timeliness);
      ps.setDouble(8, fare);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return id;
  }

  public Order getOrder(int id) {

    Order o = new Order();

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(OrderDAO.SELECT_BY_ID);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        o.id = rs.getInt("id");
        o.customerId = rs.getInt("customer_id");
        o.sender = rs.getString("sender");
        o.recipient = rs.getString("recipient");
        o.type = rs.getString("type");
        o.weight = rs.getDouble("weight");
        o.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(rs.getTimestamp("create_time"));
        o.timeliness = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(rs.getTimestamp("timeliness"));
        o.fare = rs.getDouble("fare");
      }
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return o;
  }

  public int getMaxOrderID() {

    int result = 0;

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(OrderDAO.SELECT_MAX_ORDER_ID);
      ResultSet rs = ps.executeQuery();
      rs.next();
      result = rs.getInt(1);
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }
}
