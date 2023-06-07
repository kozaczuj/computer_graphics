package pw.edu.pl.ee.virtual_camera.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Scene {
  private List<Drawable> drawables;

  public Scene() {
    drawables = new ArrayList<Drawable>();
  }

  public void add(Drawable drawable) {
    drawables.add(drawable);
  }
}
