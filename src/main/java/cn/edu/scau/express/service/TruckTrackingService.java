package cn.edu.scau.express.service;

import cn.edu.scau.express.bean.TruckTrace;
import cn.edu.scau.express.dao.TruckTrackingDAO;

public class TruckTrackingService {
  public TruckTrace selectById(String id) {
    TruckTrackingDAO ttd = new TruckTrackingDAO();
    TruckTrace tt = ttd.selectById(id);
    return tt;
  }
}
