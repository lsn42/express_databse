package cn.edu.scau.express;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import com.google.gson.Gson;

@SpringBootApplication
@RestController
public class ExpressApplication {

	public static HashMap<String, String> USERS = new HashMap<String,String>();

	public static final String USERNAME = "root";
	public static final String PASSWORD = "20001005";
	public static final String URL =
			"jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong";
	public static final String EXPRESS_SQL =
			"WITH a AS(select ex_id, customer.express_list.create_time , customer.transport_list.start_time , customer.transport_list.end_time , customer.transport_list.truck_id , customer.transport_list.plane_id ,customer.transport_list.postman_id from customer.express_list inner join customer.transport_list on customer.express_list.trans_id = customer.transport_list.trans_id where customer.transport_list.start_time > customer.express_list.create_time),b AS(SELECT ex_id, customer.express_list.create_time, customer.stored_list.intime, customer.stored_list.outtime, customer.stored_list.stored_id FROM customer.express_list INNER JOIN customer.stored_list ON customer.express_list.store_id = customer.stored_list.stored_id where customer.stored_list.intime > customer.express_list.create_time)SELECT * FROM a NATURAL JOIN b WHERE a.ex_id=";

	public static void main(String[] args) {
		USERS.put("lsn", "lsn20001005");
		USERS.put("admin", "admin");
		SpringApplication.run(ExpressApplication.class, args);
	}

	@GetMapping(value = "/query/express/{id}")
	public String queryExpress(@PathVariable("id") String id) {
		Express e = new Express(id);
		try {
			Connection con;
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement statement = con.createStatement();
			String sql = EXPRESS_SQL + id + ";";
			ResultSet rs = statement.executeQuery(sql);
			rs.next();

			e.truckId = "" + rs.getInt("truck_id");
			e.planeId = "" + rs.getInt("plane_id");
			e.postmanId = "" + rs.getInt("postman_id");
			e.storeId = "" + rs.getInt("stored_id");
			e.createTime = rs.getString("create_time");
			e.startTime = rs.getDate("start_time").toString();
			e.endTime = rs.getDate("end_time").toString();
			e.inTime = rs.getString("intime");
			e.outTime = rs.getString("outtime");

			con.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (new Gson()).toJson(e);
	}

	@PostMapping(value = "/check_user")
	public String postMethodName(@RequestParam("name") String name,
			@RequestParam("password") String password) {
		if (USERS.containsKey(name)) {
			if (USERS.get(name).equals(password)) {
				return "login success";
			} else {
				return "wrong password";
			}
		}
		return "no such user";
	}

}
