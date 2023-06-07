package pw.edu.pl.ee.virtual_camera.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JPanel;
import lombok.Data;
import pw.edu.pl.ee.virtual_camera.POJO.CubePOJO;
import pw.edu.pl.ee.virtual_camera.POJO.ModelPOJO;
import pw.edu.pl.ee.virtual_camera.POJO.PyramidPOJO;
import pw.edu.pl.ee.virtual_camera.POJO.StartPointPOJO;
import pw.edu.pl.ee.virtual_camera.POJO.TopPointPOJO;
import pw.edu.pl.ee.virtual_camera.controller.CamerListener;
import pw.edu.pl.ee.virtual_camera.controller.CameraController;
import pw.edu.pl.ee.virtual_camera.data.Cube;
import pw.edu.pl.ee.virtual_camera.data.Pyramid;
import pw.edu.pl.ee.virtual_camera.model.Scene;
import pw.edu.pl.ee.virtual_camera.model.Vector3;

@Data
public class VirtualCamera extends JPanel {

  private CameraController controller;

  public void paint(Graphics g) {
    super.paint(g);
    controller.draw(g);
  }

  public static void main(String[] args) {
    final VirtualCamera camera = new VirtualCamera();
    Scene scene = new Scene();
    CameraController controller = new CameraController(scene, camera);
    CamerListener camerListener = new CamerListener(controller);
    Window window = new Window(camera, camerListener);

    ModelPOJO model = new ModelPOJO();
    ArrayList<Cube> cubes = new ArrayList<>();
    ArrayList<Pyramid> pyramids = new ArrayList<>();
    CubePOJO cube;
    PyramidPOJO pyramid;
    StartPointPOJO s;
    TopPointPOJO t;

    ObjectMapper mapper = new ObjectMapper();

    try {
      model = mapper.readValue(new File("./src/main/java/pw/edu/pl/ee/virtual_camera/configuration/Setup.json"), ModelPOJO.class);
    } catch (Exception e) {
      e.printStackTrace();
    }

    cube = model.getCube(0);
    s = cube.getStartPoint();
    cubes.add(new Cube(new Vector3(s.getX(), s.getY(), s.getZ()), cube.getLineLength(), Color.GREEN));

    cube = model.getCube(1);
    s = cube.getStartPoint();
    cubes.add(new Cube(new Vector3(s.getX(), s.getY(), s.getZ()), cube.getLineLength(), Color.CYAN));

    cube = model.getCube(2);
    s = cube.getStartPoint();
    cubes.add(new Cube(new Vector3(s.getX(), s.getY(), s.getZ()), cube.getLineLength(), Color.YELLOW));

    cube = model.getCube(3);
    s = cube.getStartPoint();
    cubes.add(new Cube(new Vector3(s.getX(), s.getY(), s.getZ()), cube.getLineLength(), Color.MAGENTA));

    pyramid = model.getPyramid(0);
    s = pyramid.getStartPoint();
    t = pyramid.getTopPoint();
    pyramids.add(new Pyramid(new Vector3(s.getX(), s.getY(), s.getZ()), new Vector3(t.getX(), t.getY(), t.getZ()), Color.RED));

    pyramid = model.getPyramid(1);
    s = pyramid.getStartPoint();
    t = pyramid.getTopPoint();
    pyramids.add(new Pyramid(new Vector3(s.getX(), s.getY(), s.getZ()), new Vector3(t.getX(), t.getY(), t.getZ()), Color.BLUE));

    for (Cube c : cubes) {
      scene.add(c);
    }

    for (Pyramid p : pyramids) {
      scene.add(p);
    }

    while (true) camera.repaint();
  }
}
