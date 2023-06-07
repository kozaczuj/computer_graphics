package pw.edu.pl.ee.virtual_camera.POJO;

import lombok.Data;

@Data
public class ModelPOJO {
  private CubePOJO[] cube;
  private PyramidPOJO[] pyramid;

  public CubePOJO getCube(int i) {
    return cube[i];
  }

  public PyramidPOJO getPyramid(int i) {
    return pyramid[i];
  }
}
