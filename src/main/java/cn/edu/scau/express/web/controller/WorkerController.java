package cn.edu.scau.express.web.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.scau.express.service.WorkerService;

@RestController
@CrossOrigin
public class WorkerController {
  @ResponseBody
  @GetMapping(value = "/query_all/worker")
  public String trace() {
    WorkerService ws = new WorkerService();
    return (new Gson()).toJson(ws.selectHead(100));
  }

  @PostMapping(value = "/delete/worker")
  public boolean delete(@RequestParam("id") int id) {
    WorkerService ws = new WorkerService();
    return ws.delete(id);
  }

  @PostMapping(value = "/create/worker")
  public int create(@RequestParam("name") String name,
      @RequestParam("sex") String sex, @RequestParam("tel") String tel,
      @RequestParam("address") String address,
      @RequestParam("salary") double salary) {
    WorkerService ws = new WorkerService();
    return ws.create(name, sex, tel, address, salary);
  }
}
