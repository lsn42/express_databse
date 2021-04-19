package cn.edu.scau.express.DAO;

import cn.edu.scau.express.pojo.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserLoginMapper {

        public List<UserLogin> queryAll();
        public int add(UserLogin userLogin);
        public UserLogin queryByName(String user_name,String user_psw);
        public UserLogin queryById(String user_id,String user_psw);
        public String queryPswById(String user_id);
}
