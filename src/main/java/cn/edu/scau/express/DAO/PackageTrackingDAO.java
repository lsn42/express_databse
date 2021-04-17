package cn.edu.scau.express.DAO;

import com.google.gson.Gson;
import cn.edu.scau.express.bean.PackageTrace;

public class PackageTrackingDAO {
  public static void main(String[] args) {
    PackageTrace pt = new PackageTrace(8);
    pt.insertTransporting("2021-04-18 03:29:57", "out", "大货车 粤A12345",
        "华南农业大学泰山区宿舍仓库", "潮州西湖仓库");
    System.out.println((new Gson()).toJson(pt));
  }
}
