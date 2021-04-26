package cn.edu.scau.express.service;

import java.util.ArrayList;

import cn.edu.scau.express.bean.Worker;
import cn.edu.scau.express.dao.WorkerDAO;

public class WorkerService {

  public ArrayList<Worker> selectAll() {
    WorkerDAO w = new WorkerDAO();
    return w.getAll();
  }

  public ArrayList<Worker> selectHead(int count) {
    WorkerDAO w = new WorkerDAO();
    return w.getHead(count);
  }

  public boolean delete(int id) {
    WorkerDAO w = new WorkerDAO();
    return w.delete(id);
  }

  public int create(String name, String sex, String tel, String address,
      double salary) {
    WorkerDAO w = new WorkerDAO();
    return w.create(name, sex, tel, address, salary);
  }
}
