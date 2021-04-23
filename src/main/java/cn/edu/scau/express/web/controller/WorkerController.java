package cn.edu.scau.express.web.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.scau.express.service.WorkerService;

@RestController
@CrossOrigin
public class WorkerController {
  @ResponseBody
  @GetMapping(value = "/query_all/worker")
  public String Trace() {
    WorkerService ws = new WorkerService();
    return (new Gson()).toJson(ws.selectAll());
  }
}
