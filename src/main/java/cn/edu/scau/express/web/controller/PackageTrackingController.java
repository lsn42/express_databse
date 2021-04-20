package cn.edu.scau.express.web.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scau.express.service.PackageTrackingService;

@RestController
@CrossOrigin
public class PackageTrackingController {
  @ResponseBody
  @GetMapping(value = "/query/trace/{id}")
  public String Trace(@PathVariable("id") String id) {
    return (new Gson()).toJson(PackageTrackingService.queryById(id));
  }
}
