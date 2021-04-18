package cn.edu.scau.express.web.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.scau.express.DAO.PackageTrackingDAO;
import cn.edu.scau.express.bean.PackageTrace;

@RestController
@CrossOrigin
public class PackageTrackingController {
  @ResponseBody
  @GetMapping(value = "/query/trace/{id}")
  public String Trace(@PathVariable("id") String id) {
    PackageTrackingDAO ptd = new PackageTrackingDAO();
    PackageTrace pt = ptd.selectPackageTrace(id);
    return (new Gson()).toJson(pt);
  }
}
