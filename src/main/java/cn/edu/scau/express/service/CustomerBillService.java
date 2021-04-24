package cn.edu.scau.express.service;

import java.util.ArrayList;
import cn.edu.scau.express.dao.customer.BillDAO;
import cn.edu.scau.express.bean.customer.bill.item.*;

public class CustomerBillService {

  public ArrayList<Simple> selectSimple() {
    BillDAO bd = new BillDAO();
    return bd.selectSimple();
  }

  public ArrayList<Type> selectType() {
    BillDAO bd = new BillDAO();
    return bd.selectType();
  }

  public ArrayList<Each> selectEach() {
    BillDAO bd = new BillDAO();
    return bd.selectEach();
  }

}
