package pw.edu.pl.ee.virtual_camera.model;

import java.util.List;

public interface Drawable {
  List<Vector3> getPoints();
  List<Line> getLines();
  List<Polygon> getPolygons();
}
