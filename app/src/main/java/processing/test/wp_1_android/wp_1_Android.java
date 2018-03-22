package processing.test.wp_1_android;

import android.content.Context;

import processing.core.*;
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import oscP5.*; 
import netP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class wp_1_Android extends PApplet {

int Y_AXIS = 1;
int X_AXIS = 2;
int b1, b2;

               
                











int numeroparticulas, tipoparticulas, colorfondo;
String titulo;
float Factor=.9f;
float frame=60;
int radial=0;
int centro=0;
int tension=0;
int opacidad;
ArrayList<Storsimple> estorninos;
float flujo =1;
Atractor central, lateral1, lateral2, lateral3,lateral4;
int color_fondo, color_particula;
Context context;
//Variables de OSC
OscP5 oscP5;
NetAddress dest;
final global dataglobal;
boolean player;

public wp_1_Android(Context context){
    super();
    this.context=context;
    dataglobal = (global) context;
}

public void setup (){//size (800,750, P2D);
              
              
            frameRate(60);
            oscP5 = new OscP5(this,6448 ); //
          
           //numeroparticulas=int(Xnum.getContent());
           numeroparticulas=750;
           tipoparticulas=1;
           colorfondo=0;
           //Factor=float(Xfactor.getContent());
           Factor=1;
          
           player=dataglobal.getPlayer();
  
             
     
     
     
     
      b1 = color(90);
  b2 = color(0);
     
            
            
    opacidad=255;
    
    estorninos=new ArrayList<Storsimple>() ;
    for(int i=0; i<2; i++){if (i==0){tipoparticulas=1;}else{tipoparticulas=1;}
                  estorninos.add(new Storsimple(numeroparticulas,tipoparticulas,0,height,0,width));
                  //estorninos.get(i).colorea(125,255,0,1,0,1, 50,180);
            
    }
    
    central=new Atractor(1);
    lateral1=new Atractor(1);
    lateral2=new Atractor(1);
    lateral3=new Atractor(1);
     lateral4=new Atractor(1);
    central.posicion=new PVector(width/2, height/2); 
 lateral1.posicion=new PVector(width/2, height/8);
 lateral2.posicion=new PVector(width/8, height/2);
 lateral3.posicion=new PVector(7*(width/8), height/2);
 lateral4.posicion=new PVector((width/2), 7*(height/8));
     color_fondo=color(colorfondo);   
    //blendMode(ADD);  

    }


public void draw(){


background(color_fondo);
 //setGradient(0, 0, width/2, height, b2, b1, X_AXIS);
  //setGradient(width/2, 0, width/2, height, b1, b2, X_AXIS);
  noFill();
    player=dataglobal.getPlayer();

    if (player==true){flujo=dataglobal.getIntensity();}

  central.sentido=-1-flujo; 
lateral1.sentido=-0.5f*flujo;
lateral2.sentido=-0.5f*flujo;
lateral3.sentido=-0.5f*flujo;
lateral4.sentido=-0.5f*flujo;
   for (int i = 0; i < 2; i++) {
                                         Storsimple s = estorninos.get(i);

s.aceleradorparticulas(central);
if (i==0){
s.aceleradorparticulas(lateral1);
s.aceleradorparticulas(lateral2);}
else{
s.aceleradorparticulas(lateral3);
s.aceleradorparticulas(lateral4);
}
s.dibujaparticulas(radial, centro,tension);

   }



}

//This is called automatically when OSC message is received
public void oscEvent(OscMessage theOscMessage) {
 if (theOscMessage.checkAddrPattern("/intensidad")==true && player==false) {
     
        flujo = theOscMessage.get(0).floatValue();
       
     } else {
        println("Error: unexpected OSC message received by Processing: ");
        theOscMessage.print();
      }
 }
 public void setGradient(int x, int y, float w, float h, int c1, int c2, int axis ) {

  noFill();

  if (axis == Y_AXIS) {  // Top to bottom gradient
    for (int i = y; i <= y+h; i++) {
      float inter = map(i, y, y+h, 0, 1);
      int c = lerpColor(c1, c2, inter);
      stroke(c);
      line(x, i, x+w, i);
    }
  }  
  else if (axis == X_AXIS) {  // Left to right gradient
    for (int i = x; i <= x+w; i++) {
      float inter = map(i, x, x+w, 0, 1);
      int c = lerpColor(c1, c2, inter);
      stroke(c);
      line(i, y, i, y+h);
    }
  }
}
class Atractor {
            PVector posicion, origen_icono;
            float sentido;
            int tipo_atractor;
            int interaccion;
            
            Atractor (int clase){
                                posicion=new PVector(random(width), random(height));
                              interaccion=0;
                              sentido=-1;
                              tipo_atractor=clase;
                              origen_icono=new PVector (0,0);
                                }
            public PVector fuerza (PVector posicionobjeto){
            
                                                    PVector f=posicionobjeto.get();
                                                    f.sub(posicion);
            
                                                    float modulo=f.mag();
                                                    if (modulo <0) {f.mult(-1);}
                                                    f.normalize();
                                                                         //noria
            
            //if (modulo<100){f.rotate(HALF_PI);}
            //noria
                                                    switch(tipo_atractor) {
                                                                         case 1: 
                                                                              f.mult(modulo/50);
                                                                         break;
                                                                         case 2: 
                                                                              f.mult(150/modulo);
                                                                         break;
                                                                         case 3: 
                                                                              f.mult(4);
                                                                         break;
                                                                         case 4: 
                                                                              f.mult(150/modulo*modulo);
                                                                         break;
                                                                           }
                                                    f.mult(sentido);
                                                    return f;
                                                    }
           public void visible(){stroke (255,255,255);
                            strokeWeight(1);
                            //if (sentido>0) {fill(0,0,0);} else {fill(255,255,255);}
                            noFill();
                            ellipse (posicion.x, posicion.y, 10, 10);
 
                          }
                          
}
class Particula {
int r,g,b,a;
  PVector posicion, velocidad, aceleracion, gravedad;
  float limite;
  float masa;
  boolean resistencia;
  float coefroz;
  float lifespan;
  boolean eterna;
  int decay;
  int limsup, liminf, limizq, limder; 
  Particula() {
    limsup=0;
    liminf=height;
    limizq=0;
    limder=width;
    posicion=new PVector(random(limder-limizq), random(liminf-limsup));
    velocidad=new PVector (0, 0);
    aceleracion=new PVector (0, 0);
    gravedad=new PVector (0, 0.02f);
    limite=15;
    masa=random(3, 18);
    resistencia=false;
         r=PApplet.parseInt(random(0,255));
          g=PApplet.parseInt(random(0,255));
          b=PApplet.parseInt(random(0,255));
          a=PApplet.parseInt(random(0,255));
    lifespan=255;
    eterna=false;
    decay=2;
    //masa=30;
  }

  public void acelerar(PVector acelerador) {
    PVector a=PVector.div(acelerador, masa);
    aceleracion.add(a);
  }
  public void caer() {
    velocidad.add(gravedad);
  }
  public void resistencia(float coeficiente) {
    resistencia=true; 
    coefroz=coeficiente;
  }
  
  public boolean muerta(){
          if (lifespan<0){return true;}else{return false;}
                
  
  }
  
  public void actualizar() { 
if (eterna==false){lifespan-=decay;}
    velocidad.add(aceleracion);
    if (resistencia) {
      PVector friccion=velocidad.get();

      friccion.normalize();
      friccion.mult(-1*coefroz);
      velocidad.add(friccion);
    }
    velocidad.limit(limite);
    posicion.add(velocidad);
    
    
    
    aceleracion.mult(0);

    if (posicion.x > limder ) {
      velocidad.x = velocidad.x*-1;
      posicion.x=limder;
    } 
    if ( posicion.x < limizq) {
      velocidad.x = velocidad.x*-1;
      posicion.x=limizq;
    } 
    if (posicion.y > liminf ) {
      velocidad.y = velocidad.y*-1;
      posicion.y=liminf;
    }
    if (posicion.y < limsup) {
      velocidad.y = velocidad.y*-1;
      posicion.y=limsup;
    }
  }
    public void mostrar() {  
      if (eterna==false){ a=PApplet.parseInt(lifespan);}
                    stroke (r,g,b,a);
                    
                                                                        strokeWeight(masa);
                                                                point (posicion.x, posicion.y);
                                                                strokeWeight(1);
                      }
 public void lanzar(){
          actualizar();
          mostrar();
        
  
  
  }         
 
}
class Burbuja extends Particula{
        
  
         Burbuja(PVector origen, PVector vinicial, float masap){

         
         super();
       
         posicion.set(origen);
         masa=masap;
         velocidad=vinicial;
        
        }
            
}


class Astilla extends Particula{
      
        float angular;
         Astilla(PVector origen, PVector vinicial, float masap){

         
         super();
          posicion.set(origen);
         masa=masap;
         velocidad=vinicial;
          angular=0;
     }
    public void mostrar() {
                   if (eterna==false){ a=PApplet.parseInt(lifespan);}
                    stroke (r,g,b,a);
                    strokeWeight(1);
                      fill(r,g,b,a);
                      //angular=atan2(velocidad.y,velocidad.x);
                      angular=velocidad.heading()+(PI);
                      //angular=constrain (angular,-0.1,0.1);
                      
                      
                      rectMode (CENTER);
                     pushMatrix();
                     translate(posicion.x, posicion.y);
                     rotate(angular);
                    rect (0, 0, masa,2*masa);
                    popMatrix();
                      }
                      
                      
                      public void lanzar(){
                          actualizar();
                          mostrar();
                         
                                     }        
}





class Dardo extends Particula{
      
        float angular;
         Dardo(PVector origen, PVector vinicial, float masap){

         
         super();
          posicion.set(origen);
         masa=masap;
         velocidad=vinicial;
          angular=0;
     }
    public void mostrar() {
                   if (eterna==false){ a=PApplet.parseInt(lifespan);}
                          stroke (r,g,b,a);
                    strokeWeight(1);
                      fill(r,g,b,a);
                      //angular=atan2(velocidad.y,velocidad.x);
                      angular=velocidad.heading()+(3*PI/2);
                      //angular=constrain (angular,-0.1,0.1);
                      
                      
                      //rectMode (CENTER);
                     pushMatrix();
                     translate(posicion.x, posicion.y);
                     rotate(angular);
                    triangle (0, 0, masa/2, 2*masa,masa,0);
                    popMatrix();
                      }
                      
                      
                      public void lanzar(){
                          actualizar();
                          mostrar();
                         
                                     }        
}

class Foto extends Particula{
       PImage img;
        float angular;
        int masafoto;
         Foto(PVector origen, PVector vinicial, float masap){
          
         
         super();
          posicion.set(origen);
         masafoto=PApplet.parseInt(masap);
         velocidad=vinicial;
          angular=0;
         img=loadImage("texture.png");
     }
    public void mostrar() {
                   if (eterna==false){ a=PApplet.parseInt(lifespan);}
                          //stroke (r,g,b,a);
                   // strokeWeight(1);
                     tint(r,g,b,a);
                      //angular=atan2(velocidad.y,velocidad.x);
                      angular=velocidad.heading()+(3*PI/2);
                      //angular=constrain (angular,-0.1,0.1);
                      
                      
                      imageMode (CENTER);
                     pushMatrix();
                     translate(posicion.x, posicion.y);
                     rotate(angular);
                     //img.resize(masafoto, masafoto);
                    image(img,0,0);
                    popMatrix();
                      }
                      
                      
                      public void lanzar(){
                          actualizar();
                          mostrar();
                         
                                     }        
}
class Storsimple {
  
  
        float magbrowniano;
        float numeroparticulas, masaparticula;
        int claseparticula;
        PVector velocidadinicial;
        PVector origen;
        PVector browniano;
        int limsup, liminf, limizq, limder; 
        
        boolean esbrowniano;
        //float magbrowniano;
        ArrayList<Particula> particulas;
        
      
      Storsimple(float numpart,  int claspart, int sup, int inf, int izq, int der){
                    limsup=sup;
                    liminf=inf;
                    limizq=izq;
                    limder=der;
                numeroparticulas=numpart;
                claseparticula=claspart;
               particulas=new ArrayList<Particula>() ;
               
               
                origen=new PVector(random(width), random(height));
                //origen=new PVector((width/2)+30, (height/2)+30);
                esbrowniano=true;
               magbrowniano=.8f;
                
                for(int i=0; i<numeroparticulas; i++){
                          //velocidadinicial=new PVector (0,50+random(-10,10));
                          velocidadinicial=new PVector (random (width),random(height));
                          //velocidadinicial=new PVector (random (-15,15),random(-15,15));
                          //velocidadinicial=new PVector (10+random(-3,3),10+random(-3,3));
                          masaparticula=random (3,10);
                          switch(claseparticula) {
                                                 case 1: 
                                                  particulas.add(new Astilla(origen, velocidadinicial, masaparticula));
                                                 break;
                                                 case 2: 
                                                   particulas.add(new Burbuja(origen, velocidadinicial, masaparticula));
                                                 break;
                                                case 3: 
                                                   particulas.add(new Dardo(origen, velocidadinicial, masaparticula));
                                                 break;
                                                 
                                                 
                                                 
                                                case 4: 
                                                   particulas.add(new Foto(origen, velocidadinicial, masaparticula));
                                                 break;
                                                  }         
                           particulas.get(i).eterna=true;
                           particulas.get(i).liminf=inf;
                           particulas.get(i).limsup=sup;
                           particulas.get(i).limizq=izq;
                          particulas.get(i).limder=der;
                                                  }
                                                  
      
      }                       
      
            //fin constructor Storsimple
           

  public void aceleradorparticulas(Atractor a){
                                for (int i = 0; i < particulas.size(); i++) {
                                              Particula p = particulas.get(i);
                                               p.acelerar(a.fuerza(p.posicion));
                                               if(esbrowniano==true){
                                                                     browniano=new PVector (0, magbrowniano);
                                                                     browniano.rotate(p.velocidad.heading());
                                                                     p.acelerar(browniano);
                                                                   }
                                               }

}






public void dibujaparticulas(int rad, int origen, int tension){
   
                        for (int i = 0; i < particulas.size(); i++) {
                                         Particula p = particulas.get(i);
                                          //noFill();
                                          //stroke (p.r,p.g,p.b,55);
                                          //if (p.posicion.x>(limder-limizq)/2){factor=5;}else{factor=-5;}
                                          
                                          //bezier(p.posicion.x, p.posicion.y, p.posicion.x+factor*10, p.posicion.y+180, (limder-limizq)/2+factor, (liminf-limsup)/2,(limder-limizq)/2,(liminf-limsup));
                                          p.caer();
                                          p.lanzar();
                                          if (rad!=0){
                                          radiales(p.r,p.g,p.b,p.posicion.x, p.posicion.y,rad, origen, tension);}
                                          
                                          
                         }
                       

}


public void radiales(int rojo, int verde, int azul, float px, float py, int clase, int origen, int tension){                                           
                    
                      float factor=0;
                      float centrox,centroy;
                      float factor1x,factor1y;
                      float factorx,factory;
                      centrox=(limder-limizq)/2;
                      centroy=(liminf-limsup)/2;
                      switch(origen){
                                    case 0:
                                    centrox=(limder-limizq)/2;
                                    centroy=(liminf-limsup)/2;
                                    break;
                                  case 1:
                                    centrox=(limder-limizq)/2;
                                    centroy=(liminf-limsup);
                                    break;
                      
                                  case 2:
                                    centrox=(limder-limizq);
                                    centroy=(liminf-limsup)/2;
                                    break;                      
                      
                                    case 3:
                                    centrox=(limder-limizq)/2;
                                    centroy=limsup;
                                    break; 
                                  case 4:
                                    centrox=limizq;
                                    centroy=(liminf-limsup)/2;
                                    break;                   
                      
                      }
                      
                      
                                            
                      
                                         //noFill();
                                        stroke (rojo, verde, azul,30);
                                        
  
                                          switch(clase) {
                                                 case 1:
                                                 case 2:
                                                   if (clase==1){noFill();} else {fill (rojo, verde, azul,20);}
                                                
                                                      if (px>centrox){factorx=tension;factor1x=-tension;}else{factorx=-tension;factor1x=+tension;}
                                                      if (py>centroy){factory=50+tension;factor1y=-tension*10;}else{factory=-50-tension;factor1y=-tension*10;}
                                                     
                                                  bezier(px, py, px+factor1x, py+factor1y, centrox+factorx, centroy+factory,centrox, centroy);
                                                 break;
                                                 case 3: 
                                                  line(px, py, centrox, centroy);
                                                  break;                   }
                                          }






public void colorea(int iniR,int finR,int iniG,int finG,int iniB, int finB, int iniA, int finA)
                    {
                    
                    if (finG-iniG>0 && finG-iniG<255)
                                                    {
                                                      for (int i = 0; i < particulas.size(); i++) {
                                                                                                    particulas.get(i).g=PApplet.parseInt(random(iniG,finG));
                                                                                                     }
                                                    }
                                           else
                                                 {
                                                     for (int i = 0; i < particulas.size(); i++) {
                                                                                                    particulas.get(i).g=0;
                                                                                                     }
                                                 
                                                 
                                                 }
                  if (finR-iniR>0 && finR-iniR<255)
                                                    {
                                                      for (int i = 0; i < particulas.size(); i++) {
                                                                                                    particulas.get(i).r=PApplet.parseInt(random(iniR,finR));
                                                                                                     }
                                                    }
                                           else
                                                 {
                                                     for (int i = 0; i < particulas.size(); i++) {
                                                                                                    particulas.get(i).r=0;
                                                                                                     }
                                                 
                                                 
                                                 }
                 if (finB-iniB>0 && finB-iniB<255)
                                                    {
                                                      for (int i = 0; i < particulas.size(); i++) {
                                                                                                    particulas.get(i).b=PApplet.parseInt(random(iniB,finB));
                                                                                                     }
                                                    }
                                           else
                                                 {
                                                     for (int i = 0; i < particulas.size(); i++) {
                                                                                                    particulas.get(i).b=0;
                                                                                                     }
                                                 
                                                 
                                                 }
                if (finA-iniA>0 && finA-iniA<255)
                                                    {
                                                      for (int i = 0; i < particulas.size(); i++) {
                                                                                                    particulas.get(i).a=PApplet.parseInt(random(iniA,finA));
                                                                                                     }
                                                    }
                                           else
                                                 {
                                                     for (int i = 0; i < particulas.size(); i++) {
                                                                                                    particulas.get(i).a=0;
                                                                                                     }
                                                 
                                                 
                                                 }
                    
                    
                    
                    }



}//fin class Storsimple
  public void settings() {  fullScreen(P2D);  smooth(8); }
}
