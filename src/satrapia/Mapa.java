package satrapia;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

import postgresql.Jdbc;

public final class Mapa {	
    private static String nombre = "ASIA MENOR";
    
    private static int COLUMNAS = 128;

    public static Connection conexion;

    public static ArrayList<Celda> celdas;

    public static String getNombre() { return nombre; }
    public static void setNombre (String nombreMapa) { nombre = nombreMapa; }
    
	private Mapa() {}
		
    public static void carga(Jdbc oBD) //Al inicializar el mapa
    {
    	String servidor = "172.16.0.72";
		String baseDeDatos = "Produccion";
		String usuario = "postgres";
		String password = "vimvsp";
		conexion = Jdbc.conecta(servidor, baseDeDatos, usuario, password);
		
        //celdas = _interfaz_Mapa.cargaMapa(ID);
        //_interfaz_Mapa.setRecursosCeldas(ID);
    }

    //Pasar a _interfaz_Mapa
    /*
    public static long getSiguienteID(String tipo)
    {
        DataTable dt;

        string sql = "SELECT COALESCE(ID,1) FROM ID_Elementos WHERE Tipo=$$" + tipo + "$$ FOR UPDATE";
        
        conexion.ejecuta("BEGIN");

        dt = conexion.consulta(sql);
        Int64 numRegistros = 1;
        foreach (DataRow row in dt.Rows)
        {
            numRegistros = row.Field<Int64>(0);
            numRegistros++;
        }
        if (numRegistros==1)
            sql = "INSERT INTO ID_Elementos (ID,Tipo) VALUES (" + numRegistros + ",$$" + tipo + "$$)";
        else
            sql = "UPDATE ID_Elementos SET ID = " + numRegistros + " WHERE Tipo=$$" + tipo + "$$";
        long filas = Mapa.conexion.ejecuta(sql);
        conexion.ejecuta("END");

        return (long)numRegistros;
    }
    */
            
    public static ArrayList<Celda> getCeldas() { return celdas; }

    public static Celda getCelda(Posicion p) {
        int indice=-1;
        //indice = _interfaz_Celda.getID(p);
        return getCelda(indice);
        
    }
    public static Celda getCelda(int id)
    {
    	Celda r;
    	Iterator<Celda> recursosIterator = celdas.iterator();    	
		while (recursosIterator.hasNext()) {
			r=recursosIterator.next();
			if (r.getID()==id) return r;			
		}
		throw new NullPointerException();		
    }

    public static void setCeldaTerreno(Posicion p, Celda.TiposTerreno tipo)
    {
        Celda c = getCelda(p);
        c._set_Terreno(tipo);            
    }

    public static void setCeldaRegion(Posicion p, int region)
    {
        Celda c = getCelda(p);
        c._set_Region(region);
    }

    public static void setCeldaJugador(Posicion p, long jugador)
    {
        Celda c = getCelda(p);
        c._set_Jugador(jugador);
    }

    public static Posicion getPosicionNuevoEmperador()
    {
        Posicion p = new Posicion();

        //int emperadoresActuales = (int)_interfaz_Jugador.getNumeroEmperadores();
        int emperadoresActuales=0; //Eliminar esta fila cuando se implemente _interfaz_jugador

        int numEmperador = emperadoresActuales;
        double emperadoresPorFila = COLUMNAS / 35;
        double emperadoresPosiblesPorFila = Math.ceil(emperadoresPorFila);
        
        double filaBloque = Math.ceil(numEmperador / emperadoresPosiblesPorFila);
        double columnaBloque = Math.ceil(numEmperador / emperadoresPosiblesPorFila) + 1;

        double fila = (columnaBloque * 15) + 18;           
        double columna = (filaBloque * 35) + 18;

        p.setX((long)fila);
        p.setY((long)columna);
        
        return p;
    }
}