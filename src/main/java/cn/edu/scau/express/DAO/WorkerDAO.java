package cn.edu.scau.express.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.edu.scau.express.utils.JDBCUtil;
import cn.edu.scau.express.bean.Worker;

public class WorkerDAO {

  public static final String INSERT_WORKER =
      "insert into `worker` values(?, ?, ?, ?, ?, ?);";
  public static final String DELETE_BY_ID = "delete from `worker` where id=?;";
  public static final String SELECT_ALL =
      "select * from `worker` where id > 0;";
  public static final String SELECT_MAX_WORKER_ID =
      "select max(`id`) from `worker`";

  public int create(String name, String sex, String tel, String address,
      double salary) {
    int id = this.getMaxWorkerID() + 1;
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(WorkerDAO.INSERT_WORKER);
      ps.setInt(1, id);
      ps.setString(2, name);
      ps.setString(3, sex);
      ps.setString(4, tel);
      ps.setString(5, address);
      ps.setDouble(6, salary);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
  }

  public boolean delete(int id) {
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(WorkerDAO.DELETE_BY_ID);
      ps.setInt(1, id);
      ps.executeUpdate();
      ps.close();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public ArrayList<Worker> getAll() {
    ArrayList<Worker> result = new ArrayList<>();
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(WorkerDAO.SELECT_ALL);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Worker w = new Worker();
        w.id = rs.getInt("id");
        w.name = rs.getString("name");
        w.sex = rs.getString("sex");
        w.tel = rs.getString("tel");
        w.address = rs.getString("address");
        w.salary = rs.getInt("salary");
        result.add(w);
      }
      ps.close();
      rs.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return result;
  }

  public ArrayList<Worker> getHead(int count) {
    ArrayList<Worker> result = new ArrayList<>();
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(WorkerDAO.SELECT_ALL);
      ResultSet rs = ps.executeQuery();
      while (rs.next() && count-- > 0) {
        Worker w = new Worker();
        w.id = rs.getInt("id");
        w.name = rs.getString("name");
        w.sex = rs.getString("sex");
        w.tel = rs.getString("tel");
        w.address = rs.getString("address");
        w.salary = rs.getInt("salary");
        result.add(w);
      }
      ps.close();
      rs.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return result;
  }

  public int getMaxWorkerID() {
    int result = 0;
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(WorkerDAO.SELECT_MAX_WORKER_ID);
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
