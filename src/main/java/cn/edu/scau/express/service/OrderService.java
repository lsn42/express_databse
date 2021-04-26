package cn.edu.scau.express.service;

import java.util.ArrayList;

import cn.edu.scau.express.bean.Order;
import cn.edu.scau.express.dao.TransportDAO;

public class OrderService {

  public ArrayList<Order> getOrderByTransportMethodId(String id) {
    TransportDAO t = new TransportDAO();
    return t.getOrderByTransportMethodId(Integer.parseInt(id));
  }
}
