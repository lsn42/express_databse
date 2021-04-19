package cn.edu.scau.express.web.controller;

import cn.edu.scau.express.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
@Controller
//@RequestMapping("thymeleaf")
public class UserGroupController {
    private User current_user;
    @Autowired
    JdbcTemplate jdbcTemplate ;
    @GetMapping(value = {"/", "/login"})
    public String loginPage() {
        return "login"; //这里不用加html后缀 因为thymeleaf默认就是html后缀
    }

    @RequestMapping("/demo")
    public String demo() {
        //重定向到静态资源
        return "redirect:no1.html";
    }

    @PostMapping("/login")
    //实现拦截登录
    public String main(User user, HttpSession session, Model model) {

        if (!StringUtils.isEmpty(user.getUser_id())) {
            //根据login页面输入的账号去数据库
            String user_msg_sql = "select * from user_group where user_id = " + "'"+user.getUser_id() +"';";
            System.out.println("sql:" + user_msg_sql);
            //User类实现rowmapper接口 用jdbcTemplate 的query去获得数据库记录
            List<User> users = jdbcTemplate.query(user_msg_sql,new User());
            System.out.println(users);
            if(users == null){
                model.addAttribute("msg", "用户不存在！");
                return "login";
            }
            String user_id = users.get(0).getUser_id();
            String user_name = users.get(0).getUser_name();
            String user_psw = users.get(0).getUser_psw();
            String user_permission = users.get(0).getUser_permission();
//            System.out.println(map);
            if (user_psw.equals(user.getUser_psw())) {
                current_user = new User(user_id, user_name, user_psw, user_permission);
                session.setAttribute(user_id, user);
                //如何表达权限？
                return "redirect:/main.html";
            }
            else {
                model.addAttribute("msg", "账号密码错误！");
                return "login";
            }
        } else {
            model.addAttribute("msg", "不能为空白！");
            return "login";
        }

    }



    @GetMapping("/main.html")
    public String mainPage(HttpSession session, Model model) {
        Object loginUser = session.getAttribute(current_user.getUser_id());
        if (loginUser != null) {
            return "main";
        } else {
            model.addAttribute("msg", "请重新登录");
            return "login";
        }
    }
}
