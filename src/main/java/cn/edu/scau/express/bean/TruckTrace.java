package cn.edu.scau.express.bean;

import java.util.ArrayList;

public class TruckTrace {
  public String id;
  public String plate;
  public String type;
  public ArrayList<Row> trace;
  public ArrayList<Point> points;

  public TruckTrace() {
    this.trace = new ArrayList<>();
    this.points = new ArrayList<>();
  }

  public class Row {
    public String time;
    public String source;
    public String destination;
    public String type;

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

  public void insert(String time, String type, String source,
      String destination, double lnt, double lat) {
    Row r = new Row();
    Point p = new Point(lnt, lat);
    r.time = time;
    r.source = source;
    r.destination = destination;
    r.type = type;
    if (this.points.isEmpty()) {
      this.points.add(p);
    } else {
      if (!this.points.get(0).equals(p)) {
        this.points.add(0, p);
      }
    }
    this.trace.add(r);
  }
}
