package cn.edu.scau.express.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.edu.scau.express.bean.UserLogin;
import cn.edu.scau.express.service.UserLoginServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class UserLoginController {
//    protected static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    @Autowired
    UserLoginServicesImpl userLoginServicesImpl;

    @PostMapping("/toLogin")
    public String toLogin() {
        return "login_test";
    }

    @PostMapping("/LoginSuccess")
    public String LoginSuccess(Model model, UserLogin userLogin) {
        log.info("into the login page");
        UserLogin userLogin1 = userLoginServicesImpl
                .queryById(userLogin.getUser_id(), userLogin.getUser_psw());
        System.out.println(userLogin1);
        List<UserLogin> userLoginList = userLoginServicesImpl.queryAll();

        if (userLogin1 != null) {
            String psw =
                    userLoginServicesImpl.queryPswById(userLogin1.getUser_id());

            if(userLogin1.getUser_permission().equals("admin")){
                log.info("userID: " + userLogin1.getUser_id() + " permission: " + userLogin1.getUser_permission() + " is logging");
                return "admin";
            }
            else if (userLogin1.getUser_permission().equals("normal")){
                log.info("userID: " + userLogin1.getUser_id() + " permission: " + userLogin1.getUser_permission() + " is logging");
                return "normal";
            }
        }
        else{
            log.info("userID: " + userLogin1.getUser_id() + " is logging failure");
            model.addAttribute("data", "该用户不存在，或密码错误");

        }
        return "login failed";
    }

    @PostMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @PostMapping("/RegisterSuccess")
    public String toRegisterSuccess(Model model, UserLogin userLogin) throws SQLIntegrityConstraintViolationException {
        try{
            userLoginServicesImpl.add(userLogin);
            System.out.println("插入成功");
            model.addAttribute("data", "注册成功，请登录！");
            return "true";
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("data","已有该用户存在");
        }
            return "false";
    }

}
