package cn.edu.scau.express.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLIntegrityConstraintViolationException;

import cn.edu.scau.express.bean.UserLogin;
import cn.edu.scau.express.service.UserLoginServicesImpl;

@RestController
@CrossOrigin
public class UserLoginController {

    protected static final Logger logger =
            LoggerFactory.getLogger(UserLoginController.class);
    @Autowired
    UserLoginServicesImpl userLoginServicesImpl;

    @PostMapping("/toLogin")
    public String toLogin() {
        return "login_test";
    }

    @PostMapping("/LoginSuccess")
    public String LoginSuccess(Model model, UserLogin userLogin) {
        UserLogin userLogin1 = userLoginServicesImpl
                .queryById(userLogin.getUser_id(), userLogin.getUser_psw());

        if (userLogin1 != null) {
            logger.info(String.format("user: %s(id:%s) login as %s",
                    userLogin1.getUser_name(), userLogin1.getUser_id(),
                    userLogin1.getUser_permission()));

            if (userLogin1.getUser_permission().equals("admin")) {

                return "admin";
            } else if (userLogin1.getUser_permission().equals("normal")) {
                return "normal";
            }

        } else {
            logger.info(String.format("user: %s(id:%s) login failed",
                    userLogin.getUser_name(), userLogin.getUser_id()));

        }
        return "login failed";
    }

    @PostMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @PostMapping("/RegisterSuccess")
    public String toRegisterSuccess(Model model, UserLogin userLogin)
            throws SQLIntegrityConstraintViolationException {
        try {
            userLoginServicesImpl.add(userLogin);
            System.out.println(userLogin);
            logger.info(String.format(
                    "user: %s(id:%s) registered as %s, password: %s",
                    userLogin.getUser_name(), userLogin.getUser_id(),
                    userLogin.getUser_permission(), userLogin.getUser_psw()));
            // model.addAttribute("data", "注册成功，请登录！");
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(String.format(
                    "user: %s(id:%s) registered failed: duplicate id, password: %s",
                    userLogin.getUser_name(), userLogin.getUser_id(),
                    userLogin.getUser_psw()));
            // model.addAttribute("data", "已有该用户存在");
        }
        return "false";
    }

}
