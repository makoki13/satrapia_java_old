package satrapia;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import postgresql.Jdbc;

public class Evento {    
    private long ID;

    private long jugador;

    private long duracion; //igual no se usa
    private Date inicio;
    private Date fin; //La palabra final es palabra reservada en algunos lenguajes y mejor no usarla.

    public Date getFin() { return this.fin; }

    private long receptor; //Quien recibe el evento
    private int accion;
    private String parametros;

    public Evento(long id)
    {
        this.ID = id;
        //Estas instrucciones desde la base de datos
        this.jugador = 0;
    }

    public Evento(long jugador, long duracion, Date fin, long receptorEvento, int accion, String parametros)
    {
        _interfaz_Evento.__evento e = _interfaz_Evento.crea(jugador, duracion, fin, receptor, accion, parametros);
    	
        this.ID = e.id;
        this.jugador = e.jugador;
        this.duracion = e.duracion;
        this.inicio = e.inicio;
        this.fin = e.fin;
        this.receptor = receptorEvento;
        this.accion = e.accion;
        this.parametros = e.parametros;
        
    }

    public void ejecuta()
    {
    	
        if ( LocalDateTime.now().compareTo(LocalDateTime.ofInstant(this.fin.toInstant(), ZoneId.systemDefault()))>0)
        {
            //Productor p = new Productor(this.receptor); ***TODO
            //p.ejecuta(this.accion, null); //Pendiente realizar una primitiva (funcion) que pase de cadena a lista de objetos. ***TODO
        }
    }

    public void termina()
    {
        //Por si queremos avisar y eso.
    }
    
    public long getID() {return this.ID; }
    public long getJugador() {return this.jugador; }
    public long duracion() {return this.duracion; }
    public Date getInicio() {return this.inicio; }
    public int getAccion() {return this.accion; }
    public String getParametros() {return this.parametros; }
}

class _interfaz_Evento {
	//Aqui defino una estructura que es un tipo de datos compuesto. Este tipo de datos lo usaré para comunicar los datos con la clase auxiliada. (Evento y otras clases que gasten eventos, como Dispatcher)
    static class __evento
    {
        public long id;
        public long jugador;
        public long duracion;
        public Date inicio;
        public Date fin;
        public long receptor; //Quien recibe el evento, por ejemplo un Productor
        public int accion;
        public String parametros;
    }

    //Rutina que crea un evento. O bien se crea desde el cliente (Movil, Consola) o desde el Dispatcher cuando es un evento que tiene que repetirse.
    public static __evento crea(long jugador, long duracion, Date fin, long receptor, int accion, String parametros)
    {
        __evento e = new __evento();
        long nuevoID = Mapa.getSiguienteID("EVENTO");

        e.inicio = new Date();
        e.fin = new Date(e.inicio.getTime() + (duracion * 1000));
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        String sqlInicio = sdf.format(e.inicio);             
        String sqlFin = sdf.format(e.fin);
        
        //Guardo el evento en la base de datos
        String sql =
            "INSERT INTO Evento (ID,Jugador,Duracion,Inicio,Fin,Receptor,Accion,Parametros) " +
            "VALUES (" + nuevoID + "," + jugador + "," + duracion + ",'" + sqlInicio + "','" +  sqlFin + "'," + receptor + "," + accion + ",'" + parametros + "') ";
        int filas = Jdbc.ejecuta(Mapa.conexion, sql);        
        if (filas == -1)
        {
            e.id = -1; return e; //Aqui mejor lanzar una excepcion
        }
        
        e.id = nuevoID;
        e.jugador = jugador;
        e.duracion = duracion;        
        e.receptor = receptor;
        e.accion = accion;
        e.parametros = parametros;
                    
        //Devuelvo el __evento creado para quien lo ha pedido.
        return e;
    }

    //Este método devuelve una lista de eventos que en la base de datos están por procesar. 
    //Lee todos los que tienen el campo 'Procesado' a 'N' y Fin < Now (Ya les toca)
    public static ArrayList<__evento> cargaEventos() 
    {
        __evento e = new __evento();
        ArrayList<__evento> lista = new ArrayList<__evento>();
                    
        //Leemos los eventos que toca y con cada uno primero lo agregamos a la lista y despues lo marcamos para que al final del bucle sean borrados. 
        String sql = "SELECT ID,Jugador,Duracion,Inicio,Fin,Receptor,Accion,Parametros FROM Evento WHERE COALESCE(Procesado,'N')='N'";
        ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
        try {
			while(resultado.next()) {
				e.id = resultado.getLong("Tipo");
				e.jugador = resultado.getLong("Jugador");
				e.duracion = resultado.getInt("Duracion");
				e.inicio = resultado.getTimestamp("Inicio");
				e.fin = resultado.getTimestamp("Fin");
				e.receptor = resultado.getLong("Receptor");
				e.accion = resultado.getInt("Accion");
				e.parametros = resultado.getString("Parametros");
				
				lista.add(e);
			}
		} catch (SQLException ex1) {
		// 	TODO Auto-generated catch block
			ex1.printStackTrace();
		}		   

        return lista;
        
    }
}
