package cn.edu.scau.express.service;

import cn.edu.scau.express.bean.PackageTrace;
import cn.edu.scau.express.dao.PackageTrackingMapper;

public class PackageTrackingService {
  public static PackageTrace queryById(String id) {
    PackageTrackingMapper ptd = new PackageTrackingMapper();
    PackageTrace pt = ptd.queryById(id);
    return pt;
  }
}
