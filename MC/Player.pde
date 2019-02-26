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
  void act() {
  
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
      T-=T*0.1;
      if (Dx<0) {
        T=T*-1;
      }
      Dx=int(T);
    }
    if (Dy<=0) {
      float T=Dy;
      T-=T*0.1;
      Dy=int(T);
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
  void show(){
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
