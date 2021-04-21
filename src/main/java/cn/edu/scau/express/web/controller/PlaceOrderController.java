package cn.edu.scau.express.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scau.express.service.PlaceOrderService;

@RestController
@CrossOrigin
public class PlaceOrderController {

  @PostMapping(value = "/submit/order")
  public String placeOrder(@RequestParam("sender") String s,
      @RequestParam("start_address") String sa,
      @RequestParam("recipient") String r,
      @RequestParam("end_address") String ea, @RequestParam("type") String t,
      @RequestParam("weight") String w, @RequestParam("timeliness") String ti) {
    String ss = "from: " + sa + " " + s + "\n" + "to: " + ea + " " + r + "\n"
        + "type: " + t + ", weight: " + w + ", timeliness: " + ti;
    System.out.println("new order!");
    System.out.println(ss);
    PlaceOrderService pos = new PlaceOrderService();
    pos.insert(s, sa + " " + s, ea + " " + r, t, Double.parseDouble(w),
        Double.parseDouble(ti));
    return ss;
  }

}
