class FBlock extends FBox {
  int x, y, ID;
  color c;
  boolean Sel=false;
  boolean remove;
  int Health=100;
  FBlock(int X, int Y, int W, int H, color C) {
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
  int getID(color C) {
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
  void act() {
  }
  void show() {
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
