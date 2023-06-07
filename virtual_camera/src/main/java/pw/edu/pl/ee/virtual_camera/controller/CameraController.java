package pw.edu.pl.ee.virtual_camera.controller;

import pw.edu.pl.ee.virtual_camera.configuration.Configuration;
import lombok.Data;
import pw.edu.pl.ee.virtual_camera.model.Drawable;
import pw.edu.pl.ee.virtual_camera.model.Line;
import pw.edu.pl.ee.virtual_camera.model.Vector3;
import pw.edu.pl.ee.virtual_camera.model.Polygon;
import pw.edu.pl.ee.virtual_camera.model.Scene;
import pw.edu.pl.ee.virtual_camera.view.VirtualCamera;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Data
public class CameraController {
  private Scene scene;
  private VirtualCamera camera;
  private int screenHeight, screenWidth;
  private float rotationDegree, movingStep, scalingStep, scale, distanceFromCamera;
  private boolean wallHackActive;

  public CameraController(Scene scene, VirtualCamera camera) {
    this.scene = scene;
    this.camera = camera;
    this.rotationDegree = Configuration.ROTATION_DEGREE;
    this.movingStep = Configuration.MOVE_STEP;
    this.scalingStep = Configuration.SCALE_STEP;
    this.distanceFromCamera = Configuration.DISTANCE_FROM_CAMERA;
    this.screenHeight = Configuration.SCREEN_HEIGHT;
    this.screenWidth = Configuration.SCREEN_WIDTH;
    this.scale = 1.0f;
    this.wallHackActive = true;
    camera.setController(this);
  }

  public void rotateX(float sign) {
    for(Drawable d : scene.getDrawables()) {
      for(Vector3 p : d.getPoints()) {
        float x = p.getX();
        float y = p.getY();
        float z = p.getZ();
        p.setX(x);
        p.setY(y * (float)cos(rotationDegree*sign) - z * (float)sin(rotationDegree*sign));
        p.setZ(y * (float)sin(rotationDegree*sign) + z * (float)cos(rotationDegree*sign));
      }
    }
  }

  public void rotateY(float sign) {
    for(Drawable d : scene.getDrawables()) {
      for (Vector3 p : d.getPoints()) {
        float x = p.getX();
        float y = p.getY();
        float z = p.getZ();
        p.setX(x * (float)cos(rotationDegree*sign) + z * (float)sin(rotationDegree*sign));
        p.setY(y);
        p.setZ(-x * (float)sin(rotationDegree*sign) + z * (float)cos(rotationDegree*sign));
      }
    }
  }

  public void rotateZ(float sign) {
    for(Drawable d : scene.getDrawables()) {
      for (Vector3 p : d.getPoints()) {
        float x = p.getX();
        float y = p.getY();
        float z = p.getZ();
        p.setX(x * (float)cos(rotationDegree*sign) - y * (float)sin(rotationDegree*sign));
        p.setY(x * (float)sin(rotationDegree*sign) + y * (float)cos(rotationDegree*sign));
        p.setZ(z);
      }
    }
  }

  public void moveForward() {
    for(Drawable d : scene.getDrawables()) {
      for (Vector3 p : d.getPoints()) {
        p.setZ(p.getZ() - movingStep);
      }
    }
  }

  public void moveBack() {
    for(Drawable d : scene.getDrawables()) {
      for (Vector3 p : d.getPoints()) {
        p.setZ(p.getZ() + movingStep);
      }
    }
  }

  public void moveLeft() {
    for(Drawable d : scene.getDrawables()) {
      for (Vector3 p : d.getPoints()) {
        p.setX(p.getX() + movingStep);
      }
    }
  }

  public void moveRight() {
    for(Drawable d : scene.getDrawables()) {
      for (Vector3 p : d.getPoints()) {
        p.setX(p.getX() - movingStep);
      }
    }
  }

  public void moveUp() {
    for(Drawable d : scene.getDrawables()) {
      for (Vector3 p : d.getPoints()) {
        p.setY(p.getY() - movingStep);
      }
    }
  }

  public void moveDown() {
    for(Drawable d : scene.getDrawables()) {
      for (Vector3 p : d.getPoints()) {
        p.setY(p.getY() + movingStep);
      }
    }
  }

  public void scaleUp() {
    scale += scalingStep;
  }

  public void scaleDown() {
    scale -= scalingStep;
  }

  public void draw(Graphics g) {
    g.clearRect(0, 0, screenWidth, screenHeight);
    if(wallHackActive == false){
      drawWithoutWallhack(g);
    } else {

      for(Drawable d : scene.getDrawables()) {
        for (Line l : d.getLines()) {
          l = l.trimLine().scaleLine(scale).project2D().center().flip();
          float ax = l.getA().getX();
          float ay = l.getA().getY();
          float bx = l.getB().getX();
          float by = l.getB().getY();

          g.setColor(l.getColor());
          if (l.getA().getZ() >= 0 && l.getB().getZ() >= 0) {
            g.drawLine((int) ax, (int) ay, (int) bx, (int) by);
          }
        }
      }
    }
  }

  private void drawWithoutWallhack(Graphics g) {
    List<Polygon> sortedPolygonList = sortPolygons();
    int j = 0;
    for (Polygon p : sortedPolygonList) {
      Polygon pWithChangedOrder = p.changeLineOrder();
      int[] xpoints = new int[pWithChangedOrder.getLines().size() * 2];
      int[] ypoints = new int[pWithChangedOrder.getLines().size() * 2];
      int i = 0;
      j++;
      for (Line l : pWithChangedOrder.getLines()) {
        l = l.trimLine().scaleLine(scale).project2D().center().flip();
        if (l.getA().getZ() >= 0 && l.getB().getZ() >= 0) {
          xpoints[i] = (int) l.getA().getX();
          xpoints[i+1] = (int) l.getB().getX();
          ypoints[i] = (int) l.getA().getY();
          ypoints[i+1] = (int) l.getB().getY();
          i += 2;
          g.setColor(l.getColor());
        }
      }
      g.fillPolygon(xpoints, ypoints, i);
    }
  }

  // sortuje poligony na podstawie środków ciężkości
  private List<Polygon> sortPolygons() {
    List<Polygon> sortedPolygonList = new ArrayList<>();
    for(Drawable d : scene.getDrawables()) {
      for(Polygon p : d.getPolygons()) {
        sortedPolygonList.add(p);
      }
    }
    Collections.sort(sortedPolygonList);
    return sortedPolygonList;
  }
}
