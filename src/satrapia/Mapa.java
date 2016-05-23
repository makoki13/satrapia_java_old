package satrapia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import postgresql.Jdbc;
import satrapia.Celda.TiposTerreno;

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
    	String servidor = "vinylegs.com";
		String baseDeDatos = "satrapia";
		String usuario = "postgres";
		String password = "mak0k1";
		conexion = Jdbc.conecta(servidor, baseDeDatos, usuario, password);
		
        celdas = _interfaz_Mapa.cargaMapa(1);
        _interfaz_Mapa.setRecursosCeldas(1);
    }
                
    public static ArrayList<Celda> getCeldas() { return celdas; }

    public static Celda getCelda(Posicion p) {
        int indice=-1;
        //indice = _interfaz_Celda.getID(p); ***TODO
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

        //int emperadoresActuales = (int)_interfaz_Jugador.getNumeroEmperadores(); ***TODO
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
    
    //Mapping Modelo
    public static long getSiguienteID(String tipo) { return _interfaz_Mapa.getSiguienteID(tipo); }
}

//clase asociada para Modelo **************
final class _interfaz_Mapa {
	public static long getSiguienteID(String tipo)
    {
		long numRegistros = 1;
		        
		long filas = Jdbc.ejecuta(Mapa.conexion, "BEGIN");
		if (filas>0) {
			String sql = "SELECT COALESCE(ID,1) FROM ID_Elementos WHERE Tipo=$$" + tipo + "$$ FOR UPDATE";
			ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
			try {
				while(resultado.next()) {
					numRegistros = resultado.getLong("id");
					numRegistros++;
				}
			} catch (SQLException e) {
			// 	TODO Auto-generated catch block
				e.printStackTrace();
			}	
        
			if (numRegistros==1)
				sql = "INSERT INTO ID_Elementos (ID,Tipo) VALUES (" + numRegistros + ",$$" + tipo + "$$)";
			else
				sql = "UPDATE ID_Elementos SET ID = " + numRegistros + " WHERE Tipo=$$" + tipo + "$$";
			filas = Jdbc.ejecuta(Mapa.conexion, sql);
        
			filas = Jdbc.ejecuta(Mapa.conexion, "END");			
		}
		return numRegistros;
    }

	//Esta función solo se llama cuando se genera el mapa en el servidor
    public static int creaMapa(int mapa, int FILAS, int COLUMNAS)
    { 
        int id = 0;
        int region = 0;
        int tipoTerreno = Celda.TiposTerreno.CAMPO.ordinal() ;
        int jugador = -1;
        int filas;

        for (int i = 1; i < FILAS; i++ )
        {
            for (int j = 1; j < COLUMNAS; j++)
            {
                region = 1; //Aqui hay que calcular la región según FILAS y COlUMNAS
                String sql = ""+
                    "INSERT INTO Celda (ID,Mapa,Posicion_X,Posicion_Y,Region,Tipo_Terreno,Jugador) "+
                    "VALUES (" + id + "," + 1 + "," + i + "," + j + "," + region + "," + tipoTerreno + "," + jugador + ")" +
                "";
                filas = Jdbc.ejecuta(Mapa.conexion, sql);
                if (filas == -1) return -1; //Aqui mejor lanzar una excepcion

                id++;
            }
        }
     
        return 0;
    }

    public static ArrayList<Celda> cargaMapa(long mapaID)
    {
    	ArrayList<Celda> lista = new ArrayList<Celda>();
        int id,i,j,region,tipoTerreno;
        long jugador;
        Posicion p;
            
        Celda c;
        String sql ="select id,posicion_x,posicion_y,region,tipo_terreno,COALESCE(Jugador,-1) AS Jugador from celda where mapa = " + Long.toString(mapaID);
		ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
		try {
			while(resultado.next()) {
				id = resultado.getInt("id");
				jugador = resultado.getLong("Jugador");
				i = resultado.getInt("posicion_x");
				j = resultado.getInt("posicion_y");
				p = new Posicion(i, j);
				region = resultado.getInt("region");
				tipoTerreno = resultado.getInt("Tipo_Terreno");
				
				c=new Celda(id,mapaID,jugador,p,region,TiposTerreno.values()[tipoTerreno]);
				lista.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			resultado.close();
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return lista;
    }

    public static void setRecursosCeldas(long mapaID)
    {
        int idCelda;
        long idRecurso;
        Celda c;
            
        String sql ="select id,id_celda from Recursos WHERE ID_Celda in (select id from celda where mapa= " + Long.toString(mapaID) + ")";
        ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
    	try {
    		while(resultado.next()) {
				idCelda = resultado.getInt("id_celda");
				c = Mapa.getCelda(idCelda);
				idRecurso = resultado.getLong("id");								
				Recurso r = new Recurso(idRecurso);
                c.setRecurso(r);
			}
    	}
    	
    	catch (SQLException e) {
    	// 	TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	try {
    		resultado.close();
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
}