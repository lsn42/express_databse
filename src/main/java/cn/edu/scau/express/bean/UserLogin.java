package cn.edu.scau.express.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
    private String user_id; //用户id
    private String user_name;   //用户姓名
    private String user_psw;    //用户密码
    private String user_permission = "normal";  //用户权限 普通用户normal 不用post
    private int customer_id;    //顾客id 自动生成不用post
    private String customer_sex;    //顾客性别
    private String customer_tel;    //顾客电话号码
    private String customer_address;    //顾客地址
    public String getUsername() {
        return user_name;
    }
}

