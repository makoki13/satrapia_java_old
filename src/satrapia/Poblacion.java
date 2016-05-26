package satrapia;

import java.sql.ResultSet;
import java.sql.SQLException;

import postgresql.Jdbc;

public class Poblacion {
	public enum TiposPoblacion {PUEBLO, CIUDAD, CAPITAL}; //Ciudad: Capital del satrapa. Pueblo: Ciudad colonia

    private TiposPoblacion tipo;
    private int celda;

    private long ID;
    public long getID() { return this.ID; }

    private Posicion posicion;
    public void _set_Posicion(Posicion valor) {posicion=valor;}
    public Posicion _get_Capital() {return posicion;}
    
    public Poblacion (Jugador j)
    {
        _interfaz_Poblacion.__poblacion poblacion;
        poblacion = _interfaz_Poblacion.getCapital(j.getID());
        this.ID = poblacion.id;
        this.celda = poblacion.idCelda;
        this.posicion = poblacion.posicion;
    }

    public Poblacion(Jugador j, TiposPoblacion tipo, int idCelda)
    {
        _interfaz_Poblacion.__poblacion p = _interfaz_Poblacion.creaCapital(idCelda, j.getID());
        this.tipo = tipo;
        this.celda = idCelda;
        this.ID = p.id;
        this.posicion = p.posicion;
    }

    public int idCelda() { return this.celda; }
    
    public TiposPoblacion getTipoPoblacion() {return tipo;}
}

class _interfaz_Poblacion {
	static class __poblacion
	{
	    public long id;
	    public String nombre;
	    public long jugador;
	    public int tipo;
	    public int idCelda;
	    public Posicion posicion;
	}
	
	static public __poblacion creaCapital(int idCelda, long jugador)
	{
	    __poblacion poblacion = new __poblacion();
	
	    poblacion.id = Mapa.getSiguienteID("POBLACION");
	    poblacion.nombre = "CAPITAL";
	    poblacion.jugador = jugador;
	    poblacion.tipo = 2;
	    poblacion.idCelda = idCelda;
	
	    Celda c = Mapa.getCelda(idCelda);
	    poblacion.posicion = c._get_Posicion();
	    
	    String sql = "INSERT INTO Poblacion (Id,Nombre,Jugador,tipo,Id_Celda) VALUES ("+poblacion.id+",'"+poblacion.nombre+"',"+poblacion.jugador+","+poblacion.tipo+","+poblacion.idCelda+")";
	    int filas = Jdbc.ejecuta(Mapa.conexion, sql);
        if (filas == -1) return null; //Aqui mejor lanzar una excepcion
	
	    return poblacion;
	}
	
	static public __poblacion getCapital(long jugador)
	{
	    __poblacion poblacion = new __poblacion();
	
	    String sql = "SELECT ID,Nombre,Tipo,ID_Celda FROM Poblacion WHERE Jugador = " + jugador;
	    ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
		try {
			while(resultado.next()) {
				poblacion.nombre = resultado.getString("Nombre");
				poblacion.jugador = jugador;
				poblacion.tipo = resultado.getInt("Tipo");
				poblacion.idCelda = resultado.getInt("ID_Celda");
				Celda c = Mapa.getCelda(poblacion.idCelda);
				poblacion.posicion = c._get_Posicion();				
			}
		} catch (SQLException e) {
		// 	TODO Auto-generated catch block
			e.printStackTrace();
		}		         
		
		return poblacion;	    
	}
}


