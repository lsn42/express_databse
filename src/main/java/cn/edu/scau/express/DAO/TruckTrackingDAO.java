package cn.edu.scau.express.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import cn.edu.scau.express.bean.TruckTrace;
import cn.edu.scau.express.utils.JDBCUtil;

public class TruckTrackingDAO {

  public static final String SELECT_BY_ID =
      "select * from `truck_tracking` where id=?;";

  public TruckTrace selectById(String id) {
    TruckTrace tt = new TruckTrace();
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement ps =
          connection.prepareStatement(TruckTrackingDAO.SELECT_BY_ID);

      ps.setString(1, id);
      ResultSet rs = ps.executeQuery();

      tt.id = id;
      while (rs.next()) {
        int eventId;
        String time, source, destination, transport_type;
        double logitude = 0, latitude = 0;
        eventId = rs.getInt("event_id");
        tt.plate = rs.getString("plate");
        tt.type = rs.getString("type");
        // do not change the order of rs.getXX(), some of them are used for judgig null value
        time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(rs.getTimestamp("time"));
        transport_type = rs.getString("transport_type");
        source = rs.getString("source_name");
        destination = rs.getString("destination_name");
        if (transport_type.equals("in")) {
          logitude = rs.getBigDecimal("destination_longitude").doubleValue();
          latitude = rs.getBigDecimal("destination_latitude").doubleValue();
        } else if (transport_type.equals("out")) {
          logitude = rs.getBigDecimal("source_longitude").doubleValue();
          latitude = rs.getBigDecimal("source_latitude").doubleValue();
        }
        tt.insert(eventId, time, transport_type, source, destination, logitude,
            latitude);
      }
      ps.close();
      rs.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return tt;
  }
}
