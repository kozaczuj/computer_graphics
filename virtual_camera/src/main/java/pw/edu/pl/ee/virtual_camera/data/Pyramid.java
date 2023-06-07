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
public class Pyramid implements Drawable {

  private List<Vector3> points;
  private List<Line> lines;
  private List<Polygon> polygons;

  private float distanceFromCamera;
  private float distanceBetweenPoints;
  private Vector3 startPoint;
  private Vector3 topPoint;
  private Color color;

  public Pyramid(Vector3 startPoint, Vector3 topPoint, Color color) {
    this.points = new ArrayList<>();
    this.lines = new ArrayList<>();
    this.polygons = new ArrayList<>();
    this.distanceFromCamera = Configuration.DISTANCE_FROM_CAMERA + startPoint.getZ();
    this.distanceBetweenPoints = Math.abs(topPoint.getX() - startPoint.getX()) * 2f;
    this.startPoint = startPoint;
    this.topPoint = topPoint;
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
    Vector3 u = topPoint;
    float d = distanceBetweenPoints;
    points.add(new Vector3(v.getX(), v.getY(), v.getZ()));
    points.add(new Vector3(v.getX() + d, v.getY(), v.getZ()));
    points.add(new Vector3(v.getX(), v.getY(), v.getZ() + d));
    points.add(new Vector3(v.getX() + d, v.getY(), v.getZ() + d));
    points.add(new Vector3(u.getX(), u.getY(), u.getZ()));
  }

  private void initLines() {
    lines.add(new Line(points.get(0), points.get(1), color));
    lines.add(new Line(points.get(1), points.get(3), color));
    lines.add(new Line(points.get(3), points.get(2), color));
    lines.add(new Line(points.get(2), points.get(0), color));

    lines.add(new Line(points.get(0), points.get(4), color));
    lines.add(new Line(points.get(1), points.get(4), color));
    lines.add(new Line(points.get(3), points.get(4), color));
    lines.add(new Line(points.get(2), points.get(4), color));
  }

  private void initPolygons() {
    polygons.add(new Polygon(lines.get(0), lines.get(1), lines.get(2), lines.get(3)));
    polygons.add(new Polygon(lines.get(0), lines.get(5), lines.get(4)));
    polygons.add(new Polygon(lines.get(1), lines.get(6), lines.get(5)));
    polygons.add(new Polygon(lines.get(2), lines.get(7), lines.get(6)));
    polygons.add(new Polygon(lines.get(3), lines.get(4), lines.get(7)));
  }
}
