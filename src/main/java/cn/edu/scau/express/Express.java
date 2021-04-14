package cn.edu.scau.express;

public class Express {
  public String id;
  public String createTime;

  public int postmanId;
  public int customerId;
  public int receiverId;

  public String startAddress;
  public String endAddress;

  public Route firstRoute;
  public Route secondRoute;

  public Express(String id) {
    this.id = id;
    this.firstRoute = new Route();
    this.secondRoute = new Route();
  }

  public class Route {
    public int type;
    public String id;
    public String startTime;
    public String endTime;

    public Route() {
    };
  }
}
