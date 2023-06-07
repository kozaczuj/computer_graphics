package pw.edu.pl.ee.virtual_camera.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import pw.edu.pl.ee.virtual_camera.configuration.Configuration;
import pw.edu.pl.ee.virtual_camera.controller.CamerListener;

public class Window extends JFrame {
  private int screenHeight = Configuration.SCREEN_HEIGHT;
  private int screenWidth = Configuration.SCREEN_WIDTH;

  public Window(VirtualCamera camera, CamerListener cameraListener) {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().add(camera);
    this.setSize(screenWidth, screenHeight);
    int x = (int)((dimension.getWidth() - screenWidth) / 2);
    int y = (int)((dimension.getHeight() - screenHeight) / 2);
    this.addKeyListener(cameraListener);
    this.setLocation(x, y);
    this.setVisible(true);
  }
}
