package pw.edu.pl.ee.virtual_camera.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector3 {
  // static properties
  public static Vector3 forward = new Vector3(0f, 0f, 1f);
  public static Vector3 back = new Vector3(0f, 0f, -1f);
  public static Vector3 right = new Vector3(1f, 0f, 0f);
  public static Vector3 left = new Vector3(-1f, 0f, 0f);
  public static Vector3 up = new Vector3(0f, 1f, 0f);
  public static Vector3 down = new Vector3(0f, -1f, 0f);
  public static Vector3 one = new Vector3(1f, 1f, 1f);
  public static Vector3 zero = new Vector3(0f, 0f, 0f);
  public static Vector3 positiveInfinity = new Vector3(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
  public static Vector3 negativeInfinity = new Vector3(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

  // properties
  private float x, y, z;

  // methods
  public float magnitude() {
    return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
  }

  public float sqrMagnitude() {
    return (float) (Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
  }

  public Vector3 normalized() {
    float magnitude = this.magnitude();
    return new Vector3(x / magnitude, y / magnitude, z / magnitude);
  }

  public void set(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Vector3 v && this.equals(v)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean equals(Vector3 v) {
    if (x == v.x && y == v.y && z == v.z) {
      return true;
    } else {
      return false;
    }
  }

  // static methods

  public static float distance(Vector3 left, Vector3 right) {
    return (float) Math.sqrt(
      Math.pow(right.x - left.x, 2) + Math.pow(right.y - left.y, 2) + Math.pow(right.z - left.z,
        2));
  }

  // operators

  public static Vector3 addVectors(Vector3 left, Vector3 right) {
    return new Vector3(left.x + right.x, left.y + right.y, left.z + right.z);
  }

  public static Vector3 subVectors(Vector3 left, Vector3 right) {
    return new Vector3(left.x - right.x, left.y - right.y, left.z - right.z);
  }

  public static Vector3 multiply(Vector3 v, float scalar) {
    return new Vector3(v.x * scalar, v.y * scalar, v.z * scalar);
  }

  public static Vector3 divide(Vector3 v, float scalar) {
    return new Vector3(v.x / scalar, v.y / scalar, v.z / scalar);
  }

  public static boolean equals(Vector3 left, Vector3 right) {
    return left.equals(right);
  }


  @Override
  public int hashCode() {
    return Float.hashCode(x) ^ Float.hashCode(y) << 2 ^ Float.hashCode(z) >> 2;
  }

  @Override
  public String toString() {
    return ("[" + getX() + ", " + getY() + ", " + getZ() + "]");
  }
}
