package cn.edu.scau.express.bean;



import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements RowMapper<User>, Serializable {
    private String user_id;
    private String user_name;
    private String user_psw;
    private String user_permission;
    public User(){;}
    public User(String user_id, String user_name, String user_psw, String user_permission) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_psw = user_psw;
        this.user_permission = user_permission;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_psw() {
        return user_psw;
    }

    public void setUser_psw(String user_psw) {
        this.user_psw = user_psw;
    }

    public String getUser_permission() {
        return user_permission;
    }

    public void setUser_permission(String user_permission) {
        this.user_permission = user_permission;
    }
    @Override
    public String toString(){
        return "user_id: " + this.user_id + "\n" + "user_name: " + this.user_name + "\n" + "user_permission: " + this.user_permission + "\n" + "user_psw: " + this.user_psw;
    }
    @Override
    public User mapRow(ResultSet rs,int rowNum) throws SQLException{
        User user = new User();
        user.setUser_id(rs.getString("user_id"));
        user.setUser_name(rs.getString("user_name"));
        user.setUser_permission(rs.getString("user_permission"));
        user.setUser_psw(rs.getString("user_psw"));
        return user;
    }
}
