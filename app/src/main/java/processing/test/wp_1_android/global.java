package processing.test.wp_1_android;

/**
 * Created by salas on 28/03/2016.
 */
import android.app.Application;

public class global extends Application {

    /*private String tipo;
    private String tipocolor;
    private boolean muelle;
    private boolean resistencia;

    private int r=255,g=255,b=255;*/

    private boolean palette=false;
    private float[] hsv = new float[3];
    private float intensity;
    private boolean is_modified=false;
    private boolean isPlayer=false;

    /*finger

    private int deditosx, deditosy;
    private boolean haydeditos;
    public boolean gethaydeditos(){return haydeditos;}
    public int getdeditosx(){return deditosx;}
    public int getdeditosy(){return deditosy;}
    public void sethaydeditos(boolean hay){haydeditos=hay;}
    public void setdeditosx(int x){deditosx=x;}
    public void setdeditosy(int y){deditosy=y;}



    */
    //fin finger


    public void set_palette(boolean palette){this.palette=palette;}
    public boolean get_palette(){return palette;}

    public void set_color(float[] hsv){this.hsv=hsv;}
    public float[] get_color(){return hsv;}

    public void set_modified(boolean is_modified){this.is_modified=is_modified;}
    public boolean get_modified(){return is_modified;}
   /* public String gettipo() {

        return tipo;
    }

    public void settipo(String atipo) {

        tipo = atipo;

    }
    public String gettipocolor() {

        return tipocolor;
    }




    public void settipocolor(String atipo) {

        tipocolor = atipo;

    }

    public boolean getmuelle() {

        return muelle;
    }

    public void setmuelle(boolean amuelle) {

        muelle=amuelle;

    }
    public boolean getresistencia() {

        return resistencia;
    }

    public void setresistencia(boolean aresistencia) {

        resistencia=aresistencia;

    }

    public void setcolor(int red, int green, int blue){
        r=red;
        g=green;
        b=blue;
        }
    public int getred(){return r;}
    public int getgreen(){return g;}
    public int getblue(){return b;}
    */

//musica, musica
    public void setIntensity(float Inten){intensity=Inten;}
    public float getIntensity(){return intensity;}
    //musica, musica
//listen or playing?
  public void setPlayer(boolean isplayer){isPlayer=isplayer;}
  public boolean getPlayer(){return isPlayer;}

//listen or playing?

}