package cn.edu.scau.express.bean;

public class CustomerBill {
    public int customerId;
    public String name;
    public int num;

    // Debt TotalCount FindLists
    public String payType;

    public String billId;
    public PackageBill packageBill;

    public class PackageBill {
        public String name;
        public String exType;

        public PackageBill(String exname, String encasementType) {
            name = exname;
            exType = encasementType;
        }
    }

    // public void findCustomerBillLists(String customerId) {
    // // Sql ?
    // }

}
