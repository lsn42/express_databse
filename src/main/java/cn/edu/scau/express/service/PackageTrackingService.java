package cn.edu.scau.express.service;

import cn.edu.scau.express.bean.PackageTrace;
import cn.edu.scau.express.dao.PackageTrackingMapper;

public class PackageTrackingService {
  public static PackageTrace selectById(String id) {
    PackageTrackingMapper ptd = new PackageTrackingMapper();
    PackageTrace pt = ptd.selectById(Integer.parseInt(id));
    return pt;
  }
}
