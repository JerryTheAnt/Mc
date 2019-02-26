void keyPressed() {
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

void keyReleased() {
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
void genWorld(){
    for (int a=0; a<World.length; a++) {
    xoff+=0.4;
    xoff2 += 0.5;
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
void addOre() {
  color Temp=color(154, 255, 165);
  for (int a=1; a<World.length-1; a++) {
    for (int b=1; b<World[0].length-1; b++) {
      if (World[a][b]==Ore) {
        if (World[a+1][b]==Stone&&int(random(0, 2))==1) {
          World[a+1][b]=Temp;
        }
        if (World[a][b+1]==Stone&&int(random(0, 2))==1) {
          World[a][b+1]=Temp;
        }
        if (World[a-1][b]==Stone&&int(random(0, 2))==1) {
          World[a-1][b]=Temp;
        }
        if (World[a][b-1]==Stone&&int(random(0, 2))==1) {
          World[a][b-1]=Temp;
        }
        if (World[a+1][b+1]==Stone&&int(random(0, 2))==1) {
          World[a+1][b+1]=Temp;
        }
        if (World[a+1][b-1]==Stone&&int(random(0, 2))==1) {
          World[a+1][b-1]=Temp;
        }
        if (World[a-1][b+1]==Stone&&int(random(0, 2))==1) {
          World[a-1][b+1]=Temp;
        }
        if (World[a-1][b-1]==Stone&&int(random(0, 2))==1) {
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
