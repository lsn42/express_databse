package cn.edu.scau.express.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("thymeleaf")
public class TestController {

  @RequestMapping("login")
  public String getLogin() {
    // TODO: this is only a static page test, rewrite needed
    return "login";
  }
}
