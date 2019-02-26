class FItem extends FBox {
  int x, y, ID;
  color c;
  boolean remove;
  FItem(int X, int Y, color C, int ID) {
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
  void act() {
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
  void show() {
  }
}
