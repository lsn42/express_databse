package cn.edu.scau.express.service;

import java.util.ArrayList;
import cn.edu.scau.express.bean.CustomerBill;
import cn.edu.scau.express.dao.CustomerBillMapper;

public class CustomerBillService {
    public static CustomerBill FindBill(String id) {

        CustomerBillMapper customerBillMapper = new CustomerBillMapper();
        CustomerBill cb = customerBillMapper.selectCustomerBill(id);
        return cb;
    }

    // time ?
    public static ArrayList<CustomerBill> FindListsBill(String id) {

        ArrayList<CustomerBill> lists = new ArrayList<>();
        CustomerBillMapper customerBillMapper = new CustomerBillMapper();

        lists = customerBillMapper.selectCustomerBillLists(id);
        return lists;
    }

}
