package cn.edu.scau.express.service;

import java.util.Random;
import cn.edu.scau.express.dao.OrderDAO;
import cn.edu.scau.express.dao.customer.CustomerDAO;

public class PlaceOrderService {
  public int createCommon(int customerId, String source, String destination,
      String type, double weight, double timeliness) {
    OrderDAO o = new OrderDAO();
    CustomerDAO c = new CustomerDAO();

    int fare = (new Random()).nextInt(32 - 4) + 4;
    if (!c.isExist(customerId)) {
      c.createCustomer(customerId);
    }
    return o.createOrder(customerId, source + c.getName(customerId),
        destination, type, weight, timeliness, Double.valueOf(fare));
  }

  @Deprecated
  public int createHazardous() {
    // TODO: Is this necessary?
    return 0;
  }

  @Deprecated
  public int createInternational() {
    // TODO: Is this necessary?
    return 0;
  }
}
