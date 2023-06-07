package pw.edu.pl.ee.virtual_camera.controller;

import java.awt.event.KeyEvent;

public class CamerListener implements java.awt.event.KeyListener {
  private CameraController cameraController;

  public CamerListener(CameraController cameraController) {
    this.cameraController = cameraController;
  }

  public void keyTyped(KeyEvent event) {

  }

  public void keyPressed(KeyEvent event) {
    if(event.getKeyChar() == 'a') {
      cameraController.rotateY(1);
    }
    if(event.getKeyChar() == 'd') {
      cameraController.rotateY(-1);
    }
    if(event.getKeyChar() == 'w') {
      cameraController.rotateX(1);
    }
    if(event.getKeyChar() == 's') {
      cameraController.rotateX(-1);
    }
    if(event.getKeyChar() == 'q') {
      cameraController.rotateZ(-1);
    }
    if(event.getKeyChar() == 'e') {
      cameraController.rotateZ(1);
    }
    if(event.getKeyCode() == KeyEvent.VK_SPACE) {
      cameraController.moveUp();
    }
    if(event.getKeyCode() == KeyEvent.VK_CONTROL) {
      cameraController.moveDown();
    }
    if(event.getKeyCode() == KeyEvent.VK_LEFT) {
      cameraController.moveLeft();
    }
    if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
      cameraController.moveRight();
    }
    if(event.getKeyCode() == KeyEvent.VK_UP) {
      cameraController.moveForward();
    }
    if(event.getKeyCode() == KeyEvent.VK_DOWN) {
      cameraController.moveBack();
    }
    if(event.getKeyChar() == 'z') {
      cameraController.scaleUp();
    }
    if(event.getKeyChar() == 'c') {
      cameraController.scaleDown();
    }
    if(event.getKeyChar() == 'x') {
      cameraController.setWallHackActive(!cameraController.isWallHackActive());
    }
  }

  public void keyReleased(KeyEvent event) {

  }
}
