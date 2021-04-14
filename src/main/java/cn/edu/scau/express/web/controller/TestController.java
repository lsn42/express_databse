package cn.edu.scau.express.web.controller;

import cn.edu.scau.express.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
//@RequestMapping("thymeleaf")
public class TestController {


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
    public String main(User user, HttpSession session, Model model) {
      // TODO: this is only a static page test, rewrite needed
//    return "login";
      if (!StringUtils.isEmpty(user.getUsername()) && "123456".equals(user.getPassword())) {
        session.setAttribute("loginUser", user);
        return "redirect:/main.html";
      } else {
        model.addAttribute("msg", "账号密码错误");
        return "login";
      }
    }

    @GetMapping("/main.html")
    public String mainPage(HttpSession session, Model model) {
      Object loginUser = session.getAttribute("loginUser");
      if (loginUser != null) {
        return "main";
      } else {
        model.addAttribute("msg", "请重新登录");
        return "login";
      }
    }
  }
