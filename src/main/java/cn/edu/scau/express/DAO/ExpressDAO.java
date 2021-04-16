package cn.edu.scau.express.DAO;
import cn.edu.scau.express.Express;

import java.sql.SQLException;


public interface ExpressDAO {

    void insertExpress(Express express) throws SQLException;

    Express selectExpress(String id);
}