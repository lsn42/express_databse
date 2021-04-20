package cn.edu.scau.express.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
    private String user_id;
    private String user_name;
    private String user_psw;
    private String user_permission;

    public String getUsername() {
        return user_name;
    }
}

