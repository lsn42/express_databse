package cn.edu.scau.express.service;

import cn.edu.scau.express.dao.customer.CustomerDAO;

public class CustomerService {

  public String getName(int id) {
    CustomerDAO c = new CustomerDAO();
    return c.getName(id);
  }
}
