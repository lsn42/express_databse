package cn.edu.scau.express;

public class Express {
  public String id;
  public String createTime;
  public String postmanId;
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
