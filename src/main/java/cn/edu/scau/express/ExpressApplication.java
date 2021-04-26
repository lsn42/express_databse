package cn.edu.scau.express;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@RestController
public class ExpressApplication {

	public static void main(String[] args) {
		log.info("launch the application");
		SpringApplication.run(ExpressApplication.class, args);
	}

	@GetMapping(value = "/")
	public String index() {
		return "hello";
	}
}
