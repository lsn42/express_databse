package cn.edu.scau.express.DAO;

import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cn.edu.scau.express.bean.PackageTrace;

public class PackageTrackingDAO {
  private String jdbcURL = "jdbc:mysql://172.16.81.238:3306/test1?useSSL=false";
  private String jdbcUserName = "root";
  private String jdbcPassWord = "20001005";

  public static final String SELECT_PACKAGE_TRACE_BY_ID =
      "select * from package_tracking where id=?";

  protected Connection getConnect() {
    Connection connection = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassWord);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return connection;
  }

  public PackageTrace selectPackageTrace(String id) {
    PackageTrace pt = new PackageTrace();
    try (Connection connection = getConnect()) {
      PreparedStatement preparedStatement =
          connection.prepareStatement(SELECT_PACKAGE_TRACE_BY_ID);

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
          } else if (type.equals("out")) {
            pt.insertEnd(time, logitude, latitude);
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

  // public static void main(String[] args) {
  // PackageTrackingDAO ptd = new PackageTrackingDAO();
  // PackageTrace pt = ptd.selectPackageTrace("2");
  // String json = (new com.google.gson.Gson()).toJson(pt);
  // System.out.println(json);
  // try {
  // java.nio.file.Files.write(java.nio.file.Paths.get(
  // "src/main/java/cn/edu/scau/express/bean/package_tracking-example.json"),
  // json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }
}
