import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import fisica.*; 
import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MC extends PApplet {


 
int[][]World=new int[16][20];
float xoff = 0.1f;
float xoff2 = 5;
int Dirt=color(155, 0, 0);
int Stone=color(150);
int Ore=color(0, 0, 200);
Inv[] I=new Inv[9];
boolean[] Key=new boolean[5];
Player P;
int OffX,OffY;

ArrayList<FBlock>BList=new ArrayList<FBlock>();
ArrayList<FItem>IList=new ArrayList<FItem>();
ArrayList<Chunk> C=new ArrayList<Chunk>();
FWorld world;
public void settings(){  fullScreen(FX2D);
}
public void setup() {
  fullScreen(FX2D);
  for(int a=0;a<9;a++){
  I[a]=new Inv();
  }
  OffY=100;
  Fisica.init(this);
  for (int a=0; a<World.length; a++) {
    for (int b=0; b<World[0].length; b++) {
      World[a][b]=color(255);
    }
  }
  world = new FWorld(-5000,-1000,5000,1000);
  world.setGravity(0, 900);

  int i=0;
  for(int c=-2;c<=2;c++){
    int D=OffX;
    //int
    //FBody [][] T=new FBody[16][40];
    
    genWorld();
    
  for (int a=0; a<World.length; a++) {
    for (int b=0; b<World[0].length; b++) {
      if (World[a][b]!=color(255)) {
        FBlock B=new FBlock((c*16*Math.min(height/World[0].length,width/World.length))+(a)*Math.min(height/World[0].length,width/World.length), (b)*Math.min(height/World[0].length,width/World.length), Math.min(height/World[0].length,width/World.length), Math.min(height/World[0].length,width/World.length), World[a][b]);
        BList.add(B);
        world.add(B);
       // T[a][b]=B;
        
      i++;}
    }
  }
  //C.add(new Chunk(T,D));
  }
  P=new Player(0,0);

}
public void fillFromBot(int x, int y, int c) {
  for (int a=World[x].length-1; a>=y; a--) {
    World[x][a]=c;
  }
}

public void draw() {

  background(100,100,255);
  pushMatrix();
  translate(width/2-P.x,height/2-P.y);
  P.act();

    world.draw();
  world.step();
  
  for(int a=0;a<BList.size();a++){
  BList.get(a).act();
  BList.get(a).show();
  if(BList.get(a).remove){
  world.remove(BList.get(a));
  BList.remove(a); a--;}
  }
    for(int a=0;a<IList.size();a++){
  IList.get(a).act();
  IList.get(a).show();
  if(IList.get(a).remove){
  world.remove(IList.get(a));
  IList.remove(a); a--;}
  }
    P.show();
  Debug();
  popMatrix();
  
}
public void Debug(){
  //textSize(10);
  //fill(255);
  //text((mouseX-P.x)+":"+(mouseY-P.y),mouseX-width/2,mouseY-height/2);
}
class FBlock extends FBox {
  int x, y, ID;
  int c;
  boolean Sel=false;
  boolean remove;
  int Health=100;
  FBlock(int X, int Y, int W, int H, int C) {
    super(Math.min(W,H),Math.min(W,H));
    super.setPosition(X+W/2, Y+H/2);
    super.setStatic(true);
    super.setGrabbable(false);
    super.setName("Block");
    super.setRestitution(0);
    ID=getID(C);
    x=X+W/2;
    y=Y+H/2;
    super.setFillColor(C);
    c=C;
  }
  public int getID(int C) {
    if (C==Dirt) {
      return 1;
    }
    if (C==Stone) {
      return 2;
    }
    if (C==Ore) {
      return 3;
    }
    return 0;
  }
  public void act() {
  }
  public void show() {
    if(Health<=0){remove=true;
FItem I=new FItem(x,y,c,ID); 
world.add(I);
IList.add(I);

}
    if(Sel){
      if(mousePressed){Health--;}
      fill(255);
    ellipse(x,y,5,5);
  }
  noStroke();
  rectMode(CENTER);
  fill(0,map(Health,100,0,0,255));
  rect(x,y,Math.min(height/World[0].length,width/World.length), Math.min(height/World[0].length,width/World.length));

  }
}
class Chunk{
FBody[][] C=new FBody[16][40];
float Off;
Chunk(FBody[][] I,float off){
C=I;
Off=off;
}
}
class Inv{
int ID,Amount;
  int c;
  boolean isFull=false;
  boolean isEmpty=true;
Inv(int ID,int Amount,int c){
this.ID=ID;
this.Amount=Amount;
this.c=c;
isEmpty=false;
}
Inv(){

}
public void addItem(int a){
  if(Amount+a>64){
    int T=Amount+a-64;
  Amount=64;
  }else{
  Amount+=a;
  }
  
}

}
class FItem extends FBox {
  int x, y, ID;
  int c;
  boolean remove;
  FItem(int X, int Y, int C, int ID) {
    super(Math.min(height/World[0].length,width/World.length)/3, Math.min(height/World[0].length,width/World.length)/3);
    super.setPosition(X, Y);
    super.setGrabbable(false);
    //super.setSensor(true);
    super.setName("Item");
    super.setRestitution(1);
    this.setFillColor(C);
    this.ID=ID;
    c=C;
  }
  public void act() {
    super.setVelocity(0, super.getVelocityY());
    ArrayList <FBody>T=super.getTouching();
    for (int a=0; a<T.size(); a++) {
      if ((T.get(a)).getName()=="Player") {
        remove=true;
        for (int b=0; b<9; b++) {
          if (I[b].ID==ID) {
            I[b].addItem(1);
            
            return;
          }
          if (I[b].isEmpty) {
            I[b]=new Inv(ID, 1, c);
            return;
          }
        }
        return;
      }
    }
  }
  public void show() {
  }
}
public void keyPressed() {
  if (keyCode==UP) {
    Key[0]=true;
  }
  if (keyCode==DOWN) {
    Key[1]=true;
  }
  if (keyCode==LEFT) {
    Key[2]=true;
  }
  if (keyCode==RIGHT) {
    Key[3]=true;
  }
  if (key==' ') {
    Key[4]=true;
  }
}

public void keyReleased() {
  if (keyCode==UP) {
    Key[0]=false;
  }
  if (keyCode==DOWN) {
    Key[1]=false;
  }
  if (keyCode==LEFT) {
    Key[2]=false;
  }
  if (keyCode==RIGHT) {
    Key[3]=false;
  }
  if (key==' ') {
    Key[4]=false;
  }
}
public void genWorld(){
    for (int a=0; a<World.length; a++) {
    xoff+=0.4f;
    xoff2 += 0.5f;
    fillFromBot(a, (int)(noise(xoff)*5), Dirt);
    fillFromBot(a, (int)((noise(xoff)*5)+(noise(xoff2)*5)), Stone);
  }
  for (int a=26; a>0; a--) {
    int x=(int)random(0, World.length);
    int y=(int)random(0, World[0].length);
    if (World[x][y]==Stone) {
      World[x][y]=Ore;
    }
  }
  addOre();


}
public void addOre() {
  int Temp=color(154, 255, 165);
  for (int a=1; a<World.length-1; a++) {
    for (int b=1; b<World[0].length-1; b++) {
      if (World[a][b]==Ore) {
        if (World[a+1][b]==Stone&&PApplet.parseInt(random(0, 2))==1) {
          World[a+1][b]=Temp;
        }
        if (World[a][b+1]==Stone&&PApplet.parseInt(random(0, 2))==1) {
          World[a][b+1]=Temp;
        }
        if (World[a-1][b]==Stone&&PApplet.parseInt(random(0, 2))==1) {
          World[a-1][b]=Temp;
        }
        if (World[a][b-1]==Stone&&PApplet.parseInt(random(0, 2))==1) {
          World[a][b-1]=Temp;
        }
        if (World[a+1][b+1]==Stone&&PApplet.parseInt(random(0, 2))==1) {
          World[a+1][b+1]=Temp;
        }
        if (World[a+1][b-1]==Stone&&PApplet.parseInt(random(0, 2))==1) {
          World[a+1][b-1]=Temp;
        }
        if (World[a-1][b+1]==Stone&&PApplet.parseInt(random(0, 2))==1) {
          World[a-1][b+1]=Temp;
        }
        if (World[a-1][b-1]==Stone&&PApplet.parseInt(random(0, 2))==1) {
          World[a-1][b-1]=Temp;
        }
      }
    }
  }
  for (int a=0; a<World.length; a++) {
    for (int b=0; b<World[0].length; b++) {
      if (World[a][b]==Temp) {
        World[a][b]=Ore;
      }
    }
  }
}
class Player {
  float Dx, Dy, x, y;
  boolean onGround=false;
  FBox Body;
  FBox Sen;
  FCircle C;
  PVector H;
  Player(int X, int Y) {
    x=X;
    y=Y;
    Body=new FBox(Math.min(height/World[0].length,width/World.length)-1,Math.min(height/World[0].length,width/World.length)-1);
    C=new FCircle(1);
    C.setPosition(X, Y);
    C.setRestitution(0);
    C.setSensor(true);
    C.setRotatable(false);
    Body.setPosition(X, Y);
    Body.setRestitution(0);
    Body.setRotatable(false);
    Body.setName("Player");
    Sen=new FBox(Math.min(height/World[0].length,width/World.length)-5,5);
    Sen.setSensor(true);
    Sen.setPosition(x, y+Math.min(height/World[0].length,width/World.length)+2);
    Sen.setDrawable(false);
    world.add(Body);
    world.add(Sen);
    world.add(C);
  }
  public void act() {
  
    if (dist(x, y, mouseX-width/2+x, mouseY-height/2+y)>22) {
      H=new PVector(mouseX-width/2, mouseY-height/2);
      
        if (dist(x, y, mouseX-width/2+x, mouseY-height/2+y)<200) {
          C.setPosition( (mouseX-width/2)+x, y+mouseY-height/2);
        } else {
          C.setPosition(x+cos(H.heading())*200,  y+sin(H.heading())*200);
        }
       ArrayList<FBody>T=C.getTouching();
          for (int b=0; b<T.size(); b++) {
            if (T.get(b) instanceof FBlock) {
                FBlock B=(FBlock)T.get(b);
              B.Sel=true;
            }
          }
      //for (int a=1; a<=8; a++) {
      //  if (dist(x, y, mouseX-width/2+x, mouseY-height/2+y)<200) {
      //    C.setPosition(int(map(a, 0, 8, x, mouseX-width/2)+x), int(map(a, 0, 8, y, y+mouseY-height/2)));
      //  } else {
      //    C.setPosition(int(map(a, 0, 8, x, x+cos(H.heading())*200)), int(map(a, 0, 8, y, y+sin(H.heading())*200)));
      //  }
      //}
    }

    Dx=Body.getVelocityX();
    Dy=Body.getVelocityY();
    if (Dx!=0) {
      float T=Math.abs(Dx);
      T-=T*0.1f;
      if (Dx<0) {
        T=T*-1;
      }
      Dx=PApplet.parseInt(T);
    }
    if (Dy<=0) {
      float T=Dy;
      T-=T*0.1f;
      Dy=PApplet.parseInt(T);
    }


    x=Body.getX();
    y=Body.getY();

    if (Key[0]&&onGround) {
      Dy=-500;
    }
    if (Key[2]) {
      Dx-=50;
    } else
      if (Key[3]) {
        Dx+=50;
      }
    Sen.setPosition(x, y+Math.min(height/World[0].length,width/World.length)-10);
    if (Sen.getTouching().size()>1) {
      onGround=true;
    } else {
      onGround=false;
    }
    //  for(int a=0;a<Sen.getTouching().size();a++){
    //  
    //  }
    //}
    Body.setVelocity(Dx, Dy);
  }
  public void show(){
    rectMode(CENTER);
  fill(0);
  stroke(190);
  strokeWeight(5);
  rect(x,height/2-100+y,450,50);
  for(int a=0;a<9;a++){
      fill(0);
  stroke(190);
  strokeWeight(5);
  rect(-225+55*a+x,height/2-100+y,50,50);
  if(!I[a].isEmpty){
  noStroke();
  fill(I[a].c);
  rect(-225+55*a+x,height/2-100+y,30,30);
  fill(255);
  text(I[a].Amount+"",-225+55*a+x+5,height/2-100+y+10);
  }
  }
  }
}
public void mouseSensor(){
FCircle c=new FCircle(1);
c.setSensor(true);
}
public void rMouseSensor(){}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MC" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
