package cn.edu.scau.express;

import cn.edu.scau.express.DAO.UserLoginMapper;
import cn.edu.scau.express.pojo.UserLogin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
	@Autowired
	UserLoginMapper userLoginMapper;
	@Test
	public void toTest(){
		List<UserLogin> userLoginList = userLoginMapper.queryAll();
		userLoginList.forEach(e-> System.out.println(e));
	}
}
