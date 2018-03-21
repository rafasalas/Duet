package processing.test.wp_1_android;
        
import android.content.Context;
import android.media.audiofx.Visualizer;
import android.util.Log;

import processing.android.PWallpaper;
import processing.core.PApplet;
        
public class MainService extends PWallpaper {
  private Context context;
  private Visualizer mVisualizer;
  @Override
  public PApplet createSketch() {
    context=this.getApplicationContext();

    PApplet sketch = new wp_1_Android(context);
    init_music();
    return sketch;
  }
  //music!!
  private void init_music(){
    final global dataglobal = (global) getApplicationContext();
    mVisualizer = new Visualizer(0);
    mVisualizer.setEnabled(false);

    mVisualizer.setCaptureSize(128);

    Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener()
    {
      @Override
      public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
                                        int samplingRate)
      { float sum=0;

        for (int i = 0; i < bytes.length; i++) {
          sum=sum+(float)bytes[i];
        }
        //if (sum<-16383){sum=1;}else{sum=sum/1000;}
        if (sum<-12000 || sum>12000){sum=1;}else{sum=sum/1000;}

        dataglobal.setIntensity(sum);
        Log.v("sumatorio"," "+sum);
      }

      @Override
      public void onFftDataCapture(Visualizer visualizer, byte[] bytes,
                                   int samplingRate)
      {
        Log.v("fft", " " + bytes.length+bytes[0] + " "+bytes[60] + " " + bytes[125]);
      }
    };

    mVisualizer.setDataCaptureListener(captureListener,
            Visualizer.getMaxCaptureRate() / 2, true, true);

    // Enabled Visualizer and disable when we're done with the stream

    mVisualizer.setEnabled(true);

  }


  public void release()
  {
    mVisualizer.release();
  }

}

//////////////

//music

