package cn.edu.scau.express.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.edu.scau.express.bean.CustomerBill;

public class CustomerBillMapper implements CustomerBillDAO {
    private static final String FIND_CUSTOMER_BILL_BY_ID = "";
    private static final String SELECT_ALL_BILL = "";

    private String jdbcURL = "jdbc:mysql://172.16.81.238:3306/customer?useSSL=false";
    private String jdbcUserName = "root";
    private String jdbcPassWord = "20001005";

    protected Connection getConnect() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassWord);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public CustomerBill selectCustomerBill(String id) {
        CustomerBill customerBill = new CustomerBill();

        try (Connection connection = getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_CUSTOMER_BILL_BY_ID);

            // preparedStatement.setString(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                customerBill.name = rs.getString("customer_name");
                customerBill.customerId = rs.getInt("customer_id");
                customerBill.payType = rs.getString("pay_type");
                customerBill.num = rs.getInt("ex_price");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customerBill;
    }

    @Override
    public ArrayList<CustomerBill> selectCustomerBillLists(String id) {
        ArrayList<CustomerBill> lists = new ArrayList<>();

        try (Connection connection = getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BILL);

            // preparedStatement.setString(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                CustomerBill cb = new CustomerBill();
                cb.name = rs.getString("customer_name");
                cb.billId = rs.getString("ex_id");
                cb.packageBill = cb.new PackageBill(rs.getString("goods_name"), rs.getString("ex_type"));
                cb.num = rs.getInt("ex_price");
                lists.add(cb);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lists;
    }
}
