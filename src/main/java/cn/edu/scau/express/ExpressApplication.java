package cn.edu.scau.express;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@SpringBootApplication
@RestController
public class ExpressApplication {

	public static HashMap<String, String> USERS = new HashMap<String,String>();

	public static void main(String[] args) {
		USERS.put("lsn", "lsn20001005");
		USERS.put("admin", "admin");
		SpringApplication.run(ExpressApplication.class, args);
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
