package cn.edu.scau.express.web.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.scau.express.service.CustomerBillService;

@RestController
@CrossOrigin
public class CustomerBillController {

  @ResponseBody
  @GetMapping(value = "/query_all/bill/simple")
  public String simple() {
    CustomerBillService cbs = new CustomerBillService();
    return (new Gson()).toJson(cbs.selectSimple());
  }

  @ResponseBody
  @GetMapping(value = "/query_all/bill/type")
  public String type() {
    CustomerBillService cbs = new CustomerBillService();
    return (new Gson()).toJson(cbs.selectType());
  }

  @ResponseBody
  @GetMapping(value = "/query_all/bill/each")
  public String each() {
    CustomerBillService cbs = new CustomerBillService();
    return (new Gson()).toJson(cbs.selectEach());
  }
}
