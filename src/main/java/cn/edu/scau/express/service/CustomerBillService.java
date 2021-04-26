package cn.edu.scau.express.service;

import java.util.ArrayList;

import cn.edu.scau.express.dao.customer.BillDAO;
import cn.edu.scau.express.bean.customer.bill.item.*;

public class CustomerBillService {

  public ArrayList<Simple> selectSimple() {
    BillDAO b = new BillDAO();
    return b.getSimple();
  }

  public ArrayList<Type> selectType() {
    BillDAO b = new BillDAO();
    return b.getType();
  }

  public ArrayList<Each> selectEach() {
    BillDAO b = new BillDAO();
    return b.getEach();
  }
}
