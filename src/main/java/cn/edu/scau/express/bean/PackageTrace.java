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

  public ArrayList<Row> trace;

  public PackageTrace(int count) {
    this.trace = new ArrayList<>();
  }

  public class Row {
    public String status;
    public String time;
    public String info;

    public Row() {};
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
      String d) {
    Row r = new Row();
    r.status = PackageTrace.TRANSPORTING;
    r.time = t;
    if (type.equals("in")) {
      r.info = String.format(PackageTrace.TRANSPORTING_INFO_IN, d);
    } else if (type.equals("out")) {
      r.info = String.format(PackageTrace.TRANSPORTING_INFO_OUT, s, tmn, d);
    }
    this.trace.add(r);
  }
}
