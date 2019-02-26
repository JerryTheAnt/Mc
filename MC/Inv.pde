class Inv{
int ID,Amount;
  color c;
  boolean isFull=false;
  boolean isEmpty=true;
Inv(int ID,int Amount,color c){
this.ID=ID;
this.Amount=Amount;
this.c=c;
isEmpty=false;
}
Inv(){

}
void addItem(int a){
  if(Amount+a>64){
    int T=Amount+a-64;
  Amount=64;
  }else{
  Amount+=a;
  }
  
}

}
