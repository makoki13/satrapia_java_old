package satrapia;

import java.util.ArrayList;
import java.util.Iterator;

public class Celda {
	public enum TiposTerreno { 
		MAR, MONTAÑA, CAMPO, CIUDAD;
		@Override
        public String toString() {
          switch(this) {
          	case MAR: return "MAR";
          	case MONTAÑA: return "MONTAÑA";
          	case CAMPO: return "CAMPO";
          	case CIUDAD: return "CIUDAD";          	
          	default: throw new IllegalArgumentException();
          }
		}
	};

    private static ArrayList<Recurso> recursos = new ArrayList<Recurso>();

    private int ID;
    private long mapa_id;
    
    //private silueta mapa_de_bits; para 2.0
    
    private TiposTerreno terreno;
    public void _set_Terreno(TiposTerreno valor) {terreno=valor;}
    public TiposTerreno _get_Terreno() {return terreno;}
    
    private Posicion posicion;
    public void _set_Posicion(Posicion valor) {posicion=valor;}
    public Posicion _get_Posicion() {return posicion;}

    private long regionID;
    public void _set_Region(long valor) {regionID=valor;}
    public long _get_Region() {return regionID;}
    
    private long jugadorID;
    public void _set_Jugador(long valor) {jugadorID=valor;}
    public long _get_Jugador() {return jugadorID;}
    
    public Celda(int id,long mapa, long jugador, Posicion posicion, int region, TiposTerreno tipoTerreno)
    {
        this.ID = id;            
        this.mapa_id = mapa ;
        this.jugadorID = jugador;
        this.posicion = posicion;
        this.regionID = region;
        this.terreno = tipoTerreno;
    }

    public int getID() { return this.ID; }

    public long getMapa() { return this.mapa_id; }                

    public void addRecurso(Recurso r) {
        recursos.add(r);
        _interfaz_Recurso.guarda(r);
    }

    public void setRecurso(Recurso r)
    {
        recursos.add(r);
    }

    public Recurso getRecurso(Recurso.TiposRecurso tipo)
    {
    	Recurso r;
    	Iterator<Recurso> recursosIterator = recursos.iterator();    	
		while (recursosIterator.hasNext()) {
			r=recursosIterator.next();
			if (r._get_Tipo()==tipo) return r;			
		}
		throw new NullPointerException();
    }
}
