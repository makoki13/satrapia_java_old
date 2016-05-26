package satrapia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import postgresql.Jdbc;
import satrapia.Recurso.TiposRecurso;

public class Investigacion {        
    public enum TiposInvestigacion {
        BANCA, EXTRACCION_ORO, AGRICULTURA, EXTRACCION_PIEDRA, EXTRACCION_METAL, EXPLOTACION_FORESTAL, URBANISMO, INSTRUCCION_MILITAR, TACTICAS_MILITARES, FILOSOFIA_Y_CIENCIA,
    };

    private TiposInvestigacion ID;

    public TiposInvestigacion getTipo() { return this.ID; }
            
    private ArrayList<Recurso> costeSiguienteNivel;

    private String nombre;
    public void _set_Nombre(String valor) {nombre=valor;}
    public String _get_Nombre() {return nombre;}
    
    private int maxNivel;
    public void _set_MaxNivel(int valor) {maxNivel=valor;}
    public int _get_MaxNivel() {return maxNivel;}
    
    private int nivelActual;
    public void _set_NivelActual(int valor) {nivelActual=valor;}
    public int _get_NivelActual() {return nivelActual;}
    
    private int tiempo; //En segundos
    public void _set_Tiempo(int valor) {tiempo=valor;}
    public int _get_Tiempo() {return tiempo;}
                   
    private boolean investigada;
    public void _set_Investigada(boolean valor) {investigada=valor;}
    public boolean _get_Investigada() {return investigada;}
    
    public Investigacion(TiposInvestigacion id, String nombre, int nivelActual, int maxNivel, ArrayList<Recurso> listaRecursos, int tiempo)
    {
        this.ID = id;            
        this.nombre = nombre;
        this.nivelActual = nivelActual;
        this.maxNivel = maxNivel; //Vendrá dado por el número de registros en la BD.
        this.costeSiguienteNivel = listaRecursos;
        this.tiempo = tiempo;
    }

    public ArrayList<Recurso> costeDeNivelSiguiente()
    {
        return this.costeSiguienteNivel;
    }

    public void setInvestigada(Jugador j)
    {
        this.nivelActual++;
        _interfaz_investigacion.guardaInvestigacionJugador(this.getTipo(), this.nivelActual, j.getID(), false);
    }
}

class _interfaz_investigacion {
	public static ArrayList<Investigacion> getInvestigaciones(long jugador)
    {
        int id;
        String nombre;
        Investigacion.TiposInvestigacion tipo;
        int nivelActual;
        int maxNivel;
        int tiempo = 0;            
        ArrayList<Investigacion> lista = new ArrayList<Investigacion>();
        ArrayList<Recurso> listaRecursosSiguienteNivel; 

        Investigacion d;            

        String sql = ""+
            "SELECT "+
                "ID,Nombre,"+                    
                "COALESCE((SELECT Nivel FROM Investigaciones_Usuarios WHERE ID_Investigacion=P.ID AND ID_Usuario= "+ jugador + "),0) AS Nivel,"+
                "COALESCE((SELECT Max(Nivel) FROM Investigaciones_Niveles WHERE ID_Investigacion = P.ID),1) AS MaxNivel " +
            "FROM Investigaciones P "+
            "ORDER BY ID"+                
        "";
        ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
		try {
			while(resultado.next()) {
				id = resultado.getInt("ID");
				nombre = resultado.getString("Nombre");
				tipo = Investigacion.TiposInvestigacion.values()[id];
				nivelActual = resultado.getInt("Nivel");
				maxNivel = resultado.getInt("MaxNivel");

				listaRecursosSiguienteNivel = null;
	            if (nivelActual < maxNivel) //Si quedan niveles para investigar determinamos que se necesita
	            {
	                int siguienteNivel = nivelActual + 1;
	                int idRecurso = -1;
	                Long cantidad = (long) 0;                    
	                Recurso r;

	                sql = "SELECT COALESCE(Tiempo,0) AS Tiempo FROM Investigaciones_Niveles WHERE ID_Investigacion = " + id + " AND Nivel = " + siguienteNivel;
	                ResultSet resultado2 = Jdbc.consulta(Mapa.conexion, sql);
	                try {
	        			while(resultado.next()) {
	        				tiempo = resultado2.getInt("Tiempo");
	        			}
	                } catch (SQLException e) {
	            			e.printStackTrace();
	            	}
	                
	                listaRecursosSiguienteNivel = new ArrayList<Recurso>();
	                sql = "SELECT ID_Recurso,Cantidad FROM Investigaciones_Niveles_Recursos WHERE ID_Investigacion = " + id + " AND Nivel = " + siguienteNivel;
	                ResultSet resultado3 = Jdbc.consulta(Mapa.conexion, sql);
	                try {
	        			while(resultado.next()) {
	        				idRecurso = resultado3.getInt("ID_Recurso");
	        				cantidad = resultado3.getLong("Cantidad");
	        				r = new Recurso(Recurso.TiposRecurso.values()[idRecurso], cantidad, -1, Recurso.TiposReceptor.INVESTIGACION);
	                        listaRecursosSiguienteNivel.add(r);
	        			}
	                } catch (SQLException e) {
	            			e.printStackTrace();
	            	}
	            }
	            d = new Investigacion(Investigacion.TiposInvestigacion.values()[id], nombre, nivelActual, maxNivel, listaRecursosSiguienteNivel, tiempo);
	            lista.add(d);
			}
		} catch (SQLException e) {
		// 	TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;            
    }

    public static int guardaInvestigacionJugador(Investigacion.TiposInvestigacion tipo, int nivel, long jugador, boolean pendiente)
    {
        String sql = "SELECT COUNT(*) AS NumRegistros FROM Investigaciones_Usuarios WHERE ID_Usuario = " + jugador + " AND ID_Investigacion = " + (int)tipo;
        ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
        Long numRegistros = 0;
        try {
			while(resultado.next()) {
				numRegistros = resultado.getLong(0);
				
			}
        } catch (SQLException e) {
    			e.printStackTrace();
    	}
        
        if (numRegistros==0)
            sql = "INSERT INTO Investigaciones_Usuarios (ID_Usuario,ID_Investigacion,Nivel, Pendiente) VALUES (" + jugador + "," + (int)tipo + "," + nivel + ",'N')";
        else
            sql = "UPDATE Investigaciones_Usuarios SET Nivel = " + nivel + " WHERE ID_Usuario = " + jugador + " AND ID_Investigacion = " + (int)tipo;
        int filas = Mapa.conexion.ejecuta(sql);
        if (filas == -1) return -1; //Aqui mejor lanzar una excepcion
        return 0;
    }

    public static bool estaInvestigada(Investigacion.TiposInvestigacion tipo, int nivel, long idJugador) {
        string sql = "SELECT COUNT(*) AS NumRegistros FROM Investigaciones_Usuarios WHERE ID_Usuario = " + idJugador + " AND ID_Investigacion = " + (int)tipo + " AND Nivel = " + nivel;
        DataTable dt;
        dt = Mapa.conexion.consulta(sql);
        Int64 numRegistros = 0;
        foreach (DataRow row in dt.Rows)
        {
            numRegistros = row.Field<Int64>("NumRegistros");
        }

        return (numRegistros>0);
    }
}
