package cn.edu.scau.express.web.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.scau.express.service.TruckTrackingService;

@RestController
@CrossOrigin
public class TruckTrackingController {
  @ResponseBody
  @GetMapping(value = "/query/truck_trace/{id}")
  public String Trace(@PathVariable("id") String id) {
    TruckTrackingService tts = new TruckTrackingService();
    return (new Gson()).toJson(tts.selectById(id));
  }

}
