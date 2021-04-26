package cn.edu.scau.express.service;

import cn.edu.scau.express.bean.PackageTrace;
import cn.edu.scau.express.dao.PackageTrackingDAO;

public class PackageTrackingService {

  public static PackageTrace getPackageTrace(String id) {
    PackageTrackingDAO ptd = new PackageTrackingDAO();
    PackageTrace pt = ptd.getPackageTrace(Integer.parseInt(id));
    return pt;
  }
}
