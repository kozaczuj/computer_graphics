package pw.edu.pl.ee.virtual_camera.model;

import java.awt.Color;
import lombok.Data;
import pw.edu.pl.ee.virtual_camera.configuration.Configuration;

@Data
public class Line {

  private Vector3 a, b;
  private Color color;

  public Line(Vector3 a, Vector3 b) {
    this.a = a;
    this.b = b;
    this.color = Color.black;
  }

  public Line(Vector3 a, Vector3 b, Color color) {
    this.a = a;
    this.b = b;
    this.color = color;
  }

  public Line project2D() {
    float ax = getA().getX() / getA().getZ() * Configuration.DISTANCE_FROM_CAMERA;
    float ay = getA().getY() / getA().getZ() * Configuration.DISTANCE_FROM_CAMERA;
    float az = getA().getZ();

    float bx = getB().getX() / getB().getZ() * Configuration.DISTANCE_FROM_CAMERA;
    float by = getB().getY() / getB().getZ() * Configuration.DISTANCE_FROM_CAMERA;
    float bz = getB().getZ();

    return new Line(new Vector3(ax, ay, az), new Vector3(bx, by, bz), getColor());
  }

  // On canvas Y coordinates are flipped
  public Line flip() {
    float ax = getA().getX();
    float ay = Configuration.SCREEN_HEIGHT - getA().getY();
    float az = getA().getZ();

    float bx = getB().getX();
    float by = Configuration.SCREEN_HEIGHT - getB().getY();
    float bz = getB().getZ();

    return new Line(new Vector3(ax, ay, az), new Vector3(bx, by, bz), getColor());
  }

  // camera coordinates start in center of screen not top left corner
  // objects need to be moved accordingly
  public Line center(){
    float ax = getA().getX() + Configuration.SCREEN_WIDTH/2f;
    float ay = getA().getY() + Configuration.SCREEN_HEIGHT/2f;
    float az = getA().getZ();

    float bx = getB().getX() + Configuration.SCREEN_WIDTH/2f;
    float by = getB().getY() + Configuration.SCREEN_HEIGHT/2f;
    float bz = getB().getZ();

    return new Line(new Vector3(ax, ay, az), new Vector3(bx, by, bz), getColor());
  }

  public Line trimLine() {
    if(getA().getZ() * getB().getZ() < 0) {
      float dx = getB().getX() - getA().getX();
      float dy = getB().getY() - getA().getY();
      float dz = getB().getZ() - getA().getZ();
      float y = -dy * (getA().getZ() - 1) / dz + getA().getY();
      float x = -dx * (getA().getZ() - 1) / dz + getA().getX();
      if (getA().getZ() < 0) {
        return new Line(new Vector3(x, y, 0), getB(), getColor());
      } else {
        return new Line(getA(), new Vector3(x, y, 0), getColor());
      }
    }
    return this;
  }

  public Line scaleLine(float scale) {
    float ax = getA().getX() * scale;
    float ay = getA().getY() * scale;
    float az = getA().getZ();
    float bx = getB().getX() * scale;
    float by = getB().getY() * scale;
    float bz = getB().getZ();
    return new Line(new Vector3(ax, ay, az), new Vector3(bx, by, bz), getColor());
  }
}
