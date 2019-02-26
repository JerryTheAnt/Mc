import fisica.*;
import java.util.*; 
color[][]World=new color[16][20];
float xoff = 0.1;
float xoff2 = 5;
color Dirt=color(155, 0, 0);
color Stone=color(150);
color Ore=color(0, 0, 200);
Inv[] I=new Inv[9];
boolean[] Key=new boolean[5];
Player P;
int OffX,OffY;

ArrayList<FBlock>BList=new ArrayList<FBlock>();
ArrayList<FItem>IList=new ArrayList<FItem>();
ArrayList<Chunk> C=new ArrayList<Chunk>();
FWorld world;
void settings(){  fullScreen(FX2D);
}
void setup() {
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
void fillFromBot(int x, int y, color c) {
  for (int a=World[x].length-1; a>=y; a--) {
    World[x][a]=c;
  }
}

void draw() {

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
void Debug(){
  //textSize(10);
  //fill(255);
  //text((mouseX-P.x)+":"+(mouseY-P.y),mouseX-width/2,mouseY-height/2);
}
