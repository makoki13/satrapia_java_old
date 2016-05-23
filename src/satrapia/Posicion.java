package satrapia;

public class Posicion
{
     private long X; private long Y;

     public Posicion()
     {
    	 this.X = this.Y = 0;
     }

     public Posicion(long posX, long posY)
     {
    	 X = posX; Y = posY;
     }

     public long getX() { return X; }
     public long getY() { return Y; }
     public Posicion getPosicion () { return this; }

     public void setX(long posX) { X = posX; }
     public void setY(long posY) { Y = posY; }
     public void setPosicion(long posX, long posY) { X = posX; Y = posY; }
 }

