package cn.edu.scau.express.bean;

import java.util.ArrayList;

public class PackageTrace {
  public static final String TRANSPORTING = "运输中";
  public static final String DISPATCHING = "派送中";
  public static final String RECIEVED = "已取件";
  public static final String ARRIVED = "已签收";

  public static final String TRANSPORTING_INFO_1 = "快递由%s运输";
  public static final String TRANSPORTING_INFO_OUT = "快件在【%s】完成分拣，由 %s 发往【%s】";
  public static final String TRANSPORTING_INFO_IN = "快件到达【%s】";

  public static final String RECIEVED_INFO = "东风快递 %s 已收取快件。";

  public static final String DISPATCHING_INFO =
      "快件交给%s，正在派送途中（已开启“安全呼叫”保护您的电话隐私，请放心接听！）";
  public static final String ARRIVED_INFO = "已签收，感谢使用东风，期待再次为您服务";

  public String id;
  public ArrayList<Row> trace;
  public ArrayList<Point> points;

  public PackageTrace() {
    this.trace = new ArrayList<>();
    this.points = new ArrayList<>();
  }

  public class Row {
    public String status;
    public String time;
    public String info;

    public Row() {};
  }
  public class Point {
    public double longitude, latitude;

    public Point(double longitude, double latitude) {
      this.longitude = longitude;
      this.latitude = latitude;
    };

    public boolean equals(Point p) {
      return (this.longitude - p.longitude) < 1e-7
          && (this.latitude - p.latitude) < 1e-7;
    }
  }

  /**
   * 
   * @param t time
   * @param type type
   * @param tmn transport method name
   * @param s source
   * @param d destination
   */
  public void insertTransporting(String t, String type, String tmn, String s,
      String d, double lnt, double lat) {
    Row r = new Row();
    Point p = new Point(lnt, lat);
    r.status = PackageTrace.TRANSPORTING;
    r.time = t;
    if (this.points.isEmpty()) {
      this.points.add(p);
    } else {
      if (!this.points.get(0).equals(p)) {
        this.points.add(0, p);
      }
    }
    if (type.equals("in")) {
      r.info = String.format(PackageTrace.TRANSPORTING_INFO_IN, d);
    } else if (type.equals("out")) {
      r.info = String.format(PackageTrace.TRANSPORTING_INFO_OUT, s, tmn, d);
    }
    this.trace.add(r);
  }

  public void insertStart(String t, String n, double lnt, double lat) {
    Row r = new Row();
    Point p = new Point(lnt, lat);
    r.status = PackageTrace.RECIEVED;
    r.time = t;
    if (this.points.isEmpty()) {
      this.points.add(p);
    } else {
      if (!this.points.get(0).equals(p)) {
        this.points.add(0, p);
      }
    }
    r.info = String.format(PackageTrace.RECIEVED_INFO, n);
    this.trace.add(r);
  }

  public void insertDispatch(String t, String n, double lnt, double lat) {
    Row r = new Row();
    Point p = new Point(lnt, lat);
    r.status = PackageTrace.DISPATCHING;
    r.time = t;
    if (this.points.isEmpty()) {
      this.points.add(p);
    } else {
      if (!this.points.get(0).equals(p)) {
        this.points.add(0, p);
      }
    }
    r.info = String.format(PackageTrace.DISPATCHING_INFO, n);
    this.trace.add(r);
  }

  public void insertEnd(String t, double lnt, double lat) {
    Row r = new Row();
    Point p = new Point(lnt, lat);
    r.status = PackageTrace.ARRIVED;
    r.time = t;
    if (this.points.isEmpty()) {
      this.points.add(p);
    } else {
      if (!this.points.get(0).equals(p)) {
        this.points.add(0, p);
      }
    }
    r.info = String.format(PackageTrace.ARRIVED_INFO);
    this.trace.add(r);
  }
}
