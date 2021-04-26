package cn.edu.scau.express.dao.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import cn.edu.scau.express.utils.JDBCUtil;

public class CustomerDAO {

  public static final String INSERT_CUSTOMER =
      "insert into `customer` values(?, ?, ?, ?, ?);";
  public static final String SELECT_NAME_BY_ID =
      "select `customer`.`name` from `customer` where id=?;";
  public static final String SELECT_MAX_CUSTOMER_ID =
      "select max(`id`) from `customer`;";

  public int createCustomer(String name, String sex, String tel, String addr) {

    int result = getMaxCustomerId() + 1;

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(CustomerDAO.INSERT_CUSTOMER);
      ps.setInt(1, result);
      ps.setString(2, name);
      ps.setString(3, sex);
      ps.setString(4, tel);
      ps.setString(5, addr);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

  public int createCustomer() {

    int result = getMaxCustomerId() + 1;

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(CustomerDAO.INSERT_CUSTOMER);
      ps.setInt(1, result);
      ps.setNull(2, Types.VARCHAR);
      ps.setNull(3, Types.VARCHAR);
      ps.setNull(4, Types.VARCHAR);
      ps.setNull(5, Types.VARCHAR);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

  public void createCustomer(int id) {

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(CustomerDAO.INSERT_CUSTOMER);
      ps.setInt(1, id);
      ps.setNull(2, Types.VARCHAR);
      ps.setNull(3, Types.VARCHAR);
      ps.setNull(4, Types.VARCHAR);
      ps.setNull(5, Types.VARCHAR);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public int createCustomer(String name) {

    int result = getMaxCustomerId() + 1;

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(CustomerDAO.INSERT_CUSTOMER);
      ps.setInt(1, result);
      ps.setString(2, name);
      ps.setNull(3, Types.VARCHAR);
      ps.setNull(4, Types.VARCHAR);
      ps.setNull(5, Types.VARCHAR);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

  public boolean isExist(int id) {

    boolean result = false;

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(CustomerDAO.SELECT_NAME_BY_ID);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      result = rs.next();
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

  public String getName(int id) {

    String name = "";

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(CustomerDAO.SELECT_NAME_BY_ID);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        name = rs.getString(1);
      }
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return name;
  }

  public int getMaxCustomerId() {

    int result = 0;

    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(CustomerDAO.SELECT_MAX_CUSTOMER_ID);
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
