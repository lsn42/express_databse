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

  public boolean delete(int id) {
    WorkerDAO wd = new WorkerDAO();
    return wd.delete(id);
  }

  public int create(String name, String sex, String tel, String address,
      double salary) {
    WorkerDAO wd = new WorkerDAO();
    return wd.create(name, sex, tel, address, salary);
  }
}
