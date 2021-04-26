package cn.edu.scau.express.web.controller;

import javax.servlet.http.HttpServletRequest;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scau.express.service.PackageTrackingService;

@RestController
@CrossOrigin
public class PackageTrackingController {

  protected static final Logger logger =
      LoggerFactory.getLogger(PackageTrackingController.class);

  @ResponseBody
  @GetMapping(value = "/query/trace/{id}")
  public String Trace(@PathVariable("id") String id,
      HttpServletRequest request) {

    logger.info("access " + id + " from: " + request.getRemoteAddr());
    return (new Gson()).toJson(PackageTrackingService.getPackageTrace(id));
  }
}
