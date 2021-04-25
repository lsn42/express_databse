package cn.edu.scau.express.dao;

import java.text.SimpleDateFormat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cn.edu.scau.express.bean.PackageTrace;
import cn.edu.scau.express.utils.JDBCUtil;

@Mapper
@Repository
public class PackageTrackingMapper {
  public static final String SELECT_PACKAGE_TRACE_BY_ID =
      "select * from package_tracking where id=?;";

  public PackageTrace queryById(String id) {
    PackageTrace pt = new PackageTrace();
    try (Connection connection = JDBCUtil.getConnection()) {
      PreparedStatement preparedStatement = connection
          .prepareStatement(PackageTrackingMapper.SELECT_PACKAGE_TRACE_BY_ID);

      preparedStatement.setString(1, id);
      ResultSet rs = preparedStatement.executeQuery();

      pt.id = id;
      while (rs.next()) {
        String time, source, destination, method, type;
        double logitude = 0, latitude = 0;
        // do not change the order of rs.getXX(), some of them are used for judging null value
        time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(rs.getTimestamp("time"));
        method = rs.getString("method");
        source = rs.getString("source_name");
        if (rs.wasNull()) {
          destination = rs.getString("destination_name");
          if (rs.wasNull()) {
            continue;
          }
          logitude = rs.getBigDecimal("destination_longitude").doubleValue();
          latitude = rs.getBigDecimal("destination_latitude").doubleValue();
          pt.insertStart(time, method, logitude, latitude);
        }
        type = rs.getString("type");
        destination = rs.getString("destination_name");
        if (rs.wasNull()) {
          logitude = rs.getBigDecimal("source_longitude").doubleValue();
          latitude = rs.getBigDecimal("source_latitude").doubleValue();
          if (type.equals("in")) {
            pt.insertEnd(time, logitude, latitude);
          } else if (type.equals("out")) {
            pt.insertDispatch(time, method, logitude, latitude);
          }
        } else {
          if (type.equals("in")) {
            logitude = rs.getBigDecimal("destination_longitude").doubleValue();
            latitude = rs.getBigDecimal("destination_latitude").doubleValue();
          } else if (type.equals("out")) {
            logitude = rs.getBigDecimal("source_longitude").doubleValue();
            latitude = rs.getBigDecimal("source_latitude").doubleValue();
          }
          pt.insertTransporting(time, type, method, source, destination,
              logitude, latitude);
        }
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return pt;
  }
}
