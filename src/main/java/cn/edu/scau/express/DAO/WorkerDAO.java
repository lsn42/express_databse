package cn.edu.scau.express.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import cn.edu.scau.express.utils.JDBCUtil;
import cn.edu.scau.express.bean.Worker;

public class WorkerDAO {
  public static final String SELECT_ALL = "select * from worker;";

  public ArrayList<Worker> selectAll() {
    ArrayList<Worker> result = new ArrayList<>();
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement preparedStatement =
          connection.prepareStatement(SELECT_ALL);
      ResultSet rs = preparedStatement.executeQuery();
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
}
