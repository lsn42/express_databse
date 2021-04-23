package cn.edu.scau.express.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import cn.edu.scau.express.utils.JDBCUtil;

public class TruckTruckingDAO {
  public static final String SELECT_BY_ID =
      "select * from `truck_tracking` where id=?;";

}
