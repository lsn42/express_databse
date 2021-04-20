package cn.edu.scau.express.service;

import java.util.List;
import cn.edu.scau.express.bean.UserLogin;

public interface UserLoginServicesI {
    public List<UserLogin> queryAll();

    public int add(UserLogin userLogin);

    public UserLogin queryByName(String user_name, String user_psw);

    public UserLogin queryById(String user_id, String user_psw);

    public String queryPswById(String user_id);
}
