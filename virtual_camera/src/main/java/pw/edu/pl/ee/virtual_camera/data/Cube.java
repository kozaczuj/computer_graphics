package pw.edu.pl.ee.virtual_camera.data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import pw.edu.pl.ee.virtual_camera.configuration.Configuration;
import pw.edu.pl.ee.virtual_camera.model.Drawable;
import pw.edu.pl.ee.virtual_camera.model.Line;
import pw.edu.pl.ee.virtual_camera.model.Polygon;
import pw.edu.pl.ee.virtual_camera.model.Vector3;

@Data
public class Cube implements Drawable {

  private List<Vector3> points;
  private List<Line> lines;
  private List<Polygon> polygons;

  private float distanceFromCamera;
  private float distanceBetweenPoints;
  private Vector3 startPoint;
  private Color color;

  public Cube(Vector3 startPoint, float lineLength, Color color) {
    this.points = new ArrayList<>();
    this.lines = new ArrayList<>();
    this.polygons = new ArrayList<>();
    this.distanceFromCamera = Configuration.DISTANCE_FROM_CAMERA + startPoint.getZ();
    this.distanceBetweenPoints = lineLength;
    this.startPoint = startPoint;
    this.color = color;
    init();
  }

  private void init() {
    initPoints();
    initLines();
    initPolygons();
  }

  private void initPoints() {
    Vector3 v = startPoint;
    float d = distanceBetweenPoints;
    points.add(new Vector3(v.getX(), v.getY(), v.getZ()));
    points.add(new Vector3(v.getX() + d, v.getY(), v.getZ()));
    points.add(new Vector3(v.getX(), v.getY() + d, v.getZ()));
    points.add(new Vector3(v.getX() + d, v.getY() + d, v.getZ()));
    points.add(new Vector3(v.getX(), v.getY(), v.getZ() + d));
    points.add(new Vector3(v.getX() + d, v.getY(), v.getZ() + d));
    points.add(new Vector3(v.getX(), v.getY() + d, v.getZ() + d));
    points.add(new Vector3(v.getX() + d, v.getY() + d, v.getZ() + d));
  }

  private void initLines() {
    lines.add(new Line(points.get(0), points.get(1), color));
    lines.add(new Line(points.get(1), points.get(3), color));
    lines.add(new Line(points.get(3), points.get(2), color));
    lines.add(new Line(points.get(2), points.get(0), color));

    lines.add(new Line(points.get(4), points.get(5), color));
    lines.add(new Line(points.get(5), points.get(7), color));
    lines.add(new Line(points.get(7), points.get(6), color));
    lines.add(new Line(points.get(6), points.get(4), color));

    lines.add(new Line(points.get(0), points.get(4), color));
    lines.add(new Line(points.get(1), points.get(5), color));
    lines.add(new Line(points.get(3), points.get(7), color));
    lines.add(new Line(points.get(2), points.get(6), color));
  }

  private void initPolygons() {
    polygons.add(new Polygon(lines.get(0), lines.get(1), lines.get(3), lines.get(2)));
    polygons.add(new Polygon(lines.get(4), lines.get(5), lines.get(7), lines.get(6)));
    polygons.add(new Polygon(lines.get(9), lines.get(5), lines.get(10), lines.get(1)));
    polygons.add(new Polygon(lines.get(8), lines.get(3), lines.get(11), lines.get(7)));
    polygons.add(new Polygon(lines.get(2), lines.get(10), lines.get(6), lines.get(11)));
    polygons.add(new Polygon(lines.get(0), lines.get(9), lines.get(4), lines.get(8)));
  }
}
