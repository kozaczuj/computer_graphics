package pw.edu.pl.ee.virtual_camera.model;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class Polygon implements Comparable<Polygon>{
  private List<Line> lines;

  public Polygon(Line... lines) {
    this.lines = new ArrayList<Line>();
    for (Line line : lines) {
      this.lines.add(line);
    }
  }

  public float findMinZ() {
    float minZ = Float.MAX_VALUE;
    for (Line line : lines) {
      minZ = min(min(line.getA().getZ(), line.getB().getZ()),minZ);
    }
    return minZ;
  }

  public float findMaxZ() {
    float maxZ = Float.MIN_VALUE;
    for (Line line : lines) {
      maxZ = max(max(line.getA().getZ(), line.getB().getZ()),maxZ);
    }
    return maxZ;
  }

  public float findMinY() {
    float minY = Float.MAX_VALUE;
    for (Line line : lines) {
      minY = min(min(line.getA().getY(), line.getB().getY()),minY);
    }
    return minY;
  }

  public float findMaxY() {
    float maxY = Float.MIN_VALUE;
    for (Line line : lines) {
      maxY = max(max(line.getA().getY(), line.getB().getY()),maxY);
    }
    return maxY;
  }

  public float findMinX() {
    float minX = Float.MAX_VALUE;
    for (Line line : lines) {
      minX = min(min(line.getA().getX(), line.getB().getX()),minX);
    }
    return minX;
  }

  public float findMaxX() {
    float maxX = Float.MIN_VALUE;
    for (Line line : lines) {
      maxX = max(max(line.getA().getX(), line.getB().getX()),maxX);
    }
    return maxX;
  }

  public Vector3 findMidpoint() {
    Vector3 centroid = new Vector3((findMaxX()+findMinX())/2.0f,(findMaxY()+findMinY())/2.0f,(findMaxZ()+findMinZ())/2.0f);
    return centroid;
  }

  public Polygon changeLineOrder() {
    Polygon p = new Polygon();
    int i = 0;
    Vector3 lastPoint = null;

    for(Line line : lines) {
      if(i == 0) {
        if(getLines().get(0).getB().equals(getLines().get(1).getA()) || getLines().get(0).getB().equals(getLines().get(1).getB())) {
          p.getLines().add(line);
        } else {
          p.getLines().add(new Line(lines.get(i).getB(), lines.get(i).getA(), lines.get(i).getColor()));
        }
      } else {
        if(lines.get(i).getA().equals(lastPoint)) {
          p.getLines().add(line);
        } else {
          p.getLines().add(new Line(lines.get(i).getB(), lines.get(i).getA(), lines.get(i).getColor()));
        }
      }

      lastPoint = p.getLines().get(i).getB();
      i++;
    }
    return p;
  }

  private ArrayList<Polygon> dividePolygons(ArrayList<Polygon> polygons) {
    ArrayList<Polygon> divided = new ArrayList<>();
    for (int i = 0; i < polygons.size(); i++) {
      Vector3 midpoint = polygons.get(i).findMidpoint();
      for (int j = 0; j < polygons.get(i).lines.size(); j++) {
        divided.add(new Polygon(
            new Line(polygons.get(i).lines.get(j).getA(), polygons.get(i).lines.get(j).getB()),
            new Line(polygons.get(i).lines.get(j).getA(), midpoint),
            new Line(polygons.get(i).lines.get(j).getA(), midpoint)
          )
        );
      }
    }
    return divided;
  }

  private boolean areClose(Polygon o) {
    Vector3 m_other = o.findMidpoint();
    if (m_other.getX() < findMaxX()
      && m_other.getX() > findMinX()
      && m_other.getY() < findMaxY()
      && m_other.getY() > findMinY()
    ) return true;
    return false;
  }

  @Override
  public int compareTo(Polygon o) {
    ArrayList<Polygon> polygons_other = new ArrayList<>();
    ArrayList<Polygon> polygons_this = new ArrayList<>();
    float result = o.findMidpoint().sqrMagnitude() - findMidpoint().sqrMagnitude();

    polygons_other.add(o);
    polygons_this.add(this);

    for (int i = 0; i < 3; i++) {
      polygons_other = dividePolygons(polygons_other);
      polygons_this = dividePolygons(polygons_this);
    }

    for (int i = 0; i < polygons_this.size(); i++) {
      ArrayList<Float> results = new ArrayList<>();
      for (int j = 0; j < polygons_other.size(); j++) {
        if(polygons_this.get(i).areClose(polygons_other.get(j)))
        {
          results.add(polygons_other.get(j).findMidpoint().sqrMagnitude() - polygons_this.get(i).findMidpoint().sqrMagnitude());
        }
      }
      if (results.size() > 0)
        result += Collections.min(results);
    }

    return (int) (result);
  }
}
