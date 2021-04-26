package cn.edu.scau.express.dao.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.edu.scau.express.bean.customer.bill.item.*;
import cn.edu.scau.express.utils.JDBCUtil;

public class BillDAO {

  public static final String SELECT_SIMPLE_BILL =
      "select * from `simple_bill`;";
  public static final String SELECT_EACH_BILL =
      "select * from `each_shipment_bill`;";
  public static final String SELECT_TYPE_BILL =
      "select * from `type_of_service_bill`;";

  public ArrayList<Simple> getSimple() {

    ArrayList<Simple> result = new ArrayList<>();

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(BillDAO.SELECT_SIMPLE_BILL);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Simple s = new Simple();
        s.id = rs.getInt("id");
        s.name = rs.getString("name");
        s.address = rs.getString("address");
        s.amount = rs.getString("amount");
        result.add(s);
      }
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

  public ArrayList<Type> getType() {

    ArrayList<Type> result = new ArrayList<>();

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(BillDAO.SELECT_TYPE_BILL);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Type t = new Type();
        t.id = rs.getInt("id");
        t.name = rs.getString("name");
        t.address = rs.getString("address");
        t.method = rs.getString("pay_method");
        t.amount = rs.getString("amount");
        result.add(t);
      }
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

  public ArrayList<Each> getEach() {

    ArrayList<Each> result = new ArrayList<>();

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(BillDAO.SELECT_EACH_BILL);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Each e = new Each();
        e.id = rs.getInt("id");
        e.name = rs.getString("name");
        e.address = rs.getString("address");
        e.sender = rs.getString("sender");
        e.recipient = rs.getString("recipient");
        e.type = rs.getString("type");
        e.weight = rs.getString("weight");
        e.create_time = rs.getString("create_time");
        e.timeliness = rs.getString("timeliness");
        e.fare = rs.getString("fare");
        result.add(e);
      }
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }
}
