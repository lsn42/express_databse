package cn.edu.scau.express.dao;

import java.util.ArrayList;

import cn.edu.scau.express.bean.CustomerBill;

public interface CustomerBillDAO {
    CustomerBill selectCustomerBill(String id);

    ArrayList<CustomerBill> selectCustomerBillLists(String id);
}
