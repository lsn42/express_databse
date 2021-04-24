package cn.edu.scau.express.service;

import java.util.Random;
import cn.edu.scau.express.dao.OrderDAO;
import cn.edu.scau.express.dao.customer.CustomerDAO;

public class PlaceOrderService {
  public void insert(String n, String s, String d, String t, double w,
      double tm) {
    OrderDAO o = new OrderDAO();
    CustomerDAO c = new CustomerDAO();
    int id = c.getId(n);
    int fare = (new Random()).nextInt(32 - 4) + 4;
    if (id == -1) {
      c.insertCustomerOnlyName(n);
      id = c.getId(n);
    }
    o.insertOrder(id, s, d, t, w, tm, fare);
  }
}
