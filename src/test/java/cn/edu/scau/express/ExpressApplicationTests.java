package cn.edu.scau.express;

import cn.edu.scau.express.bean.UserLogin;
import cn.edu.scau.express.dao.UserLoginMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@SpringBootTest
class ExpressApplicationTests {
	@Autowired
	DataSource dataSource;

	@Test
	void contextLoads() throws SQLException {
		System.out.println(dataSource.getClass());
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
		connection.close();
	}

	@Test
	void mytest() {
		ArrayList<Integer> a = new ArrayList<>();
		System.out.println(a);
		a.add(0, 1);
		System.out.println(a);
		a.add(2);
		System.out.println(a);
		a.add(0, 3);
		System.out.println(a);
	}

	@Autowired
	UserLoginMapper userLoginMapper;

	@Test
	public void toTest() {
		log.info("call the test");
		List<UserLogin> userLoginList = userLoginMapper.queryAll();
		userLoginList.forEach(e -> System.out.println(e));
	}
}
