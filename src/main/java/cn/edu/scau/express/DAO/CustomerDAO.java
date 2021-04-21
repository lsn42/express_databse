package cn.edu.scau.express.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import cn.edu.scau.express.utils.JDBCUtil;

public class CustomerDAO {
  public static final String SELECT_ID_BY_NAME =
      "select `customer`.`id` from `customer` where name=?;";
  public static final String INSERT_CUSTOMER =
      "insert into `customer` values(1 + (select * from(select count(*) from `customer`) intermediate_table), ?, ?, ?, ?);";

  public int getId(String name) {
    int id = -1;
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(CustomerDAO.SELECT_ID_BY_NAME);
      ps.setString(1, name);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        id = rs.getInt(1);
      }
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
  }

  public void insertCustomer(String name, String sex, String tel, String addr) {
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(CustomerDAO.INSERT_CUSTOMER);
      ps.setString(1, name);
      ps.setString(2, sex);
      ps.setString(3, tel);
      ps.setString(4, addr);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void insertCustomerOnlyName(String name) {
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(CustomerDAO.INSERT_CUSTOMER);
      ps.setString(1, name);
      ps.setNull(2, Types.VARCHAR);
      ps.setNull(3, Types.VARCHAR);
      ps.setNull(4, Types.VARCHAR);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
