package cn.edu.scau.express.DAO;

import cn.edu.scau.express.Express;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpressDAOEntry implements ExpressDAO {
    private String jdbcURL = "jdbc:mysql://172.16.81.238:3306/customer?useSSL=false";
    private String jdbcUserName = "root";
    private String jdbcPassWord = "20001005";

    public static final String SELECT_EXPRESS_BY_ID = "WITH a AS(select ex_id, customer.express_list.create_time , express_list.customer_id, express_list.receiver_id, customer.transport_list.start_time , customer.transport_list.end_time , customer.transport_list.truck_id , customer.transport_list.plane_id ,customer.transport_list.postman_id, transport_list.start_address, transport_list.end_address from customer.express_list inner join customer.transport_list on customer.express_list.trans_id = customer.transport_list.trans_id where customer.transport_list.start_time > customer.express_list.create_time),b AS(SELECT ex_id, customer.express_list.create_time, customer.stored_list.intime, customer.stored_list.outtime, customer.stored_list.stored_id FROM customer.express_list INNER JOIN customer.stored_list ON customer.express_list.store_id = customer.stored_list.stored_id where customer.stored_list.intime > customer.express_list.create_time) SELECT * FROM a NATURAL JOIN b WHERE (b.intime >= a.end_time or b.outtime <= a.start_time) and a.ex_id=";
    private static final String INSERT_EXPRESS_SQL = "";

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
    public void insertExpress(Express express) throws SQLException {
        try (Connection connection = getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EXPRESS_SQL);

            preparedStatement.setString(1, express.id);
            // More to come
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Express selectExpress(String id) {
        Express e = new Express(id);

        try (Connection connection = getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXPRESS_BY_ID);

            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                e.createTime = rs.getString("create_time");
                e.postmanId = rs.getInt("postman_id");
                e.customerId = rs.getInt("customer_id");
                e.receiverId = rs.getInt("receiver_id");

                e.startAddress = rs.getString("start_address");
                e.endAddress = rs.getString("end_address");

                // first route
                String t = "" + rs.getInt("plane_id");
                e.firstRoute.type = 0;
                if (rs.wasNull()) {
                    t = "" + rs.getInt("truck_id");
                    e.firstRoute.type = 1;
                }
                e.firstRoute.id = t;
                e.firstRoute.startTime = rs.getString("start_time");
                e.firstRoute.endTime = rs.getString("end_time");

                // second route
                e.secondRoute.type = 2;
                e.secondRoute.id = "" + rs.getInt("stored_id");
                e.secondRoute.startTime = rs.getString("intime");
                e.secondRoute.endTime = rs.getString("outtime");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return e;
    }

}
