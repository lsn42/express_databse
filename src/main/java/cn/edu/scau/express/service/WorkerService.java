package cn.edu.scau.express.service;

import java.util.ArrayList;
import cn.edu.scau.express.bean.Worker;
import cn.edu.scau.express.dao.WorkerDAO;

public class WorkerService {
  public ArrayList<Worker> selectAll() {
    WorkerDAO wd = new WorkerDAO();
    return wd.selectAll();
  }

  public ArrayList<Worker> selectHead(int count) {
    WorkerDAO wd = new WorkerDAO();
    return wd.selectHead(count);
  }
}
