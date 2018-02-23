package processing.test.wp_1_android;
        
import processing.android.PWallpaper;
import processing.core.PApplet;
        
public class MainService extends PWallpaper {  
  @Override
  public PApplet createSketch() {
    PApplet sketch = new wp_1_Android();
    
    return sketch;
  }
}
