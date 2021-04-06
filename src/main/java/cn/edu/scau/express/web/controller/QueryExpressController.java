package cn.edu.scau.express.web.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.scau.express.Express;

@RestController
@CrossOrigin
public class QueryExpressController {
  public static final String USERNAME = "root";
  public static final String PASSWORD = "20001005";
  public static final String URL =
      "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong";

  public static final String EXPRESS_SQL =
      "WITH a AS(select ex_id, customer.express_list.create_time , customer.transport_list.start_time , customer.transport_list.end_time , customer.transport_list.truck_id , customer.transport_list.plane_id ,customer.transport_list.postman_id from customer.express_list inner join customer.transport_list on customer.express_list.trans_id = customer.transport_list.trans_id where customer.transport_list.start_time > customer.express_list.create_time),b AS(SELECT ex_id, customer.express_list.create_time, customer.stored_list.intime, customer.stored_list.outtime, customer.stored_list.stored_id FROM customer.express_list INNER JOIN customer.stored_list ON customer.express_list.store_id = customer.stored_list.stored_id where customer.stored_list.intime > customer.express_list.create_time)SELECT * FROM a NATURAL JOIN b WHERE (b.intime >= a.end_time or b.outtime <= a.start_time) and a.ex_id=";

  @ResponseBody
  @GetMapping(value = "/query/express/{id}")
  public String queryExpress(@PathVariable("id") String id) {
    try {
      Connection con;
      con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      Statement statement = con.createStatement();
      String sql = EXPRESS_SQL + id + ";";
      ResultSet rs = statement.executeQuery(sql);
      if (rs.next()) {
        Express e = new Express(id);

        e.createTime = rs.getString("create_time");
        e.postmanId = "" + rs.getInt("postman_id");

        // first route
        String t = "" + rs.getInt("plane_id");
        e.firstRoute.type = 0;
        if (rs.wasNull()) {
          t = "" + rs.getInt("truck_id");
          e.firstRoute.type = 1;
        }
        e.firstRoute.id = t;
        e.firstRoute.startTime = rs.getDate("start_time").toString();
        e.firstRoute.endTime = rs.getDate("end_time").toString();

        // second route
        e.secondRoute.type = 2;
        e.secondRoute.id = "" + rs.getInt("stored_id");
        e.secondRoute.startTime = rs.getString("intime");
        e.secondRoute.endTime = rs.getString("outtime");
        con.close();
        return (new Gson()).toJson(e);
      } else {
        con.close();
        return null;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
