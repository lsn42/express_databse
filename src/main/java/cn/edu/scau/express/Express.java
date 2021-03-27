package cn.edu.scau.express;

public class Express {
  public String id;
  public String truckId;
  public String planeId;
  public String postmanId;
  public String storeId;
  public String createTime;
  public String startTime;
  public String endTime;
  public String inTime;
  public String outTime;

  public Express(String id) {
    this.id = id;
    this.truckId = null;
    this.planeId = null;
    this.postmanId = null;
    this.storeId = null;
    this.createTime = null;
    this.startTime = null;
    this.endTime = null;
    this.inTime = null;
    this.outTime = null;
  }
}
