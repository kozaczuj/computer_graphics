package pw.edu.pl.ee.virtual_camera.model;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
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

  @Override
  public int compareTo(Polygon o) {
    Vector3 centroid = findMidpoint();
    Vector3 oCentroid = o.findMidpoint();
    float myDistance = centroid.getX() * centroid.getX() + centroid.getY() * centroid.getY() + centroid.getZ()*centroid.getZ();
    float oDistance = oCentroid.getX() * oCentroid.getX() + oCentroid.getY() * oCentroid.getY() + oCentroid.getZ() * oCentroid.getZ();
    return (int) (oDistance - myDistance);
  }
}
