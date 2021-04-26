package cn.edu.scau.express.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import cn.edu.scau.express.bean.UserLogin;
import cn.edu.scau.express.dao.UserLoginMapper;

@Service
public class UserLoginServicesImpl implements UserLoginServicesI {

    @Autowired
    UserLoginMapper userLoginMapper;

    @Override
    public List<UserLogin> queryAll() {
        return userLoginMapper.queryAll();
    }

    @Override
    public int add(UserLogin userLogin) {
        return userLoginMapper.add(userLogin);
    }

    @Override
    public UserLogin queryByName(String user_name, String user_psw) {
        return userLoginMapper.queryByName(user_name, user_psw);
    }

    @Override
    public UserLogin queryById(String user_id, String user_psw) {
        return userLoginMapper.queryById(user_id, user_psw);
    }

    @Override
    public String queryPswById(String user_id) {
        return userLoginMapper.queryPswById(user_id);
    }
}
