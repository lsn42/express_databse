package cn.edu.scau.express.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import cn.edu.scau.express.utils.JDBCUtil;
import cn.edu.scau.express.bean.Worker;

public class WorkerDAO {
  public static final String SELECT_ALL = "select * from `worker`;";
  public static final String DELETE_BY_ID = "delete from `worker` where id=?;";

  public ArrayList<Worker> selectAll() {
    ArrayList<Worker> result = new ArrayList<>();
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
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
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return result;
  }

  public ArrayList<Worker> selectHead(int count) {
    ArrayList<Worker> result = new ArrayList<>();
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
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
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return result;
  }

  public boolean delete(int id) {
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID);
      ps.setInt(1, id);
      ps.executeUpdate();
      return true;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }
}
