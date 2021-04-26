package cn.edu.scau.express.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cn.edu.scau.express.bean.Order;
import cn.edu.scau.express.utils.JDBCUtil;

public class TransportDAO {

  public static final String SELECT_ORDERS_BY_TRANSPORT_EVENT_ID =
      "select * from `order` where `id` in (select `order_id` from `transport_event` join `transport` on `transport`.`event_id` = `transport_event`.`id` where `transport_event`.`id` = ?);";

  public ArrayList<Order> getOrderByTransportMethodId(int id) {

    ArrayList<Order> result = new ArrayList<>();

    try (Connection connection = JDBCUtil.getConnection()) {

      PreparedStatement ps = connection
          .prepareStatement(TransportDAO.SELECT_ORDERS_BY_TRANSPORT_EVENT_ID);
      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        Order o = new Order();

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
        result.add(o);
      }

      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }
}
