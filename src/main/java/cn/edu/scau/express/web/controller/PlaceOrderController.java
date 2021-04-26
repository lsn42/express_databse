package cn.edu.scau.express.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.scau.express.service.CustomerService;
import cn.edu.scau.express.service.PlaceOrderService;

@RestController
@CrossOrigin
public class PlaceOrderController {
  public static final String INFO = "%s, 你的快递已登记完毕，运单号为 %d，请等待运输。";
  protected static final Logger logger =
      LoggerFactory.getLogger(PlaceOrderController.class);

  @PostMapping(value = "/submit/order")
  public String placeOrder(@RequestParam("sender") String senderId,
      @RequestParam("start_address") String startAddress,
      @RequestParam("recipient") String recipient,
      @RequestParam("end_address") String endAddress,
      @RequestParam("type") String type, @RequestParam("weight") String weight,
      @RequestParam("timeliness") String timeliness) {
    CustomerService c = new CustomerService();
    String name = c.getName(Integer.parseInt(senderId));
    String d40 = "----------------------------------------"; // dash*40
    logger.info("new order!\n" + d40 + "\n" + "from: " + startAddress + " "
        + name + "\n" + "to: " + endAddress + " " + recipient + "\n" + "type: "
        + type + ", weight: " + weight + ", timeliness: " + timeliness + "\n"
        + d40);

    PlaceOrderService pos = new PlaceOrderService();
    return String.format(PlaceOrderController.INFO, name,
        pos.createCommon(Integer.parseInt(senderId), startAddress,
            endAddress + " " + recipient, type, Double.parseDouble(weight),
            Double.parseDouble(timeliness)));
  }
}
