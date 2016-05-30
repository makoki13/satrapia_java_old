package satrapia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import postgresql.Jdbc;

public class Jugador {
	enum TiposJugador { 
		SIN_TIPO, JEFE, SATRAPA, EMPERADOR;
		
		public static TiposJugador getElemento(String texto) {
			for(TiposJugador v : values()){
		        if( TiposJugador.valueOf(texto).equals(texto)){
		            return v;
		        }
		    }
		    return null;
		}
	};
    enum EstadosJugador { DESCONECTADO, CONECTADO };

    private long ID;

    private String nombre;
    public void _set_Nombre(String valor) {nombre=valor; /* _interfaz_Jugador.setNombre(Mapa.conexion, this.ID, _nombre); ***TODO */}
    public String _get_Nombre() {return nombre;}
    
    private String pass;
    public void _set_Pass(String valor) {nombre=valor; /* _interfaz_Jugador.setPass(Mapa.conexion, this.ID, _pass); ***TODO */}
    public String _get_Pass() {return pass;}
    

    private int nivelTutorial;
    public void _set_NivelTutorial(int valor) {nivelTutorial=valor; /* _interfaz_Jugador.setNivelTutorial(this.ID, _nivelTutorial); ***TODO */}
    public int _get_NivelTutorial() {return nivelTutorial;}
    
    private TiposJugador tipo;
    private EstadosJugador estado;
    private long region_ID;
            
    public Dirigente dirigente;
            
    public Jugador(long id)
    {
        this.ID = id;

        _interfaz_Jugador.__usuario misDatosUsuario = _interfaz_Jugador.datosUsuario(id);
        this.nombre = misDatosUsuario.nombre;
        this.tipo = TiposJugador.values()[misDatosUsuario.tipo];
        this.estado = EstadosJugador.DESCONECTADO;
        this.region_ID = misDatosUsuario.region;
        this.dirigente = new Dirigente(this);
        this.nivelTutorial = misDatosUsuario.nivelTutorial;            
    }
           
    public String getTipo() { return this.tipo.toString(); } // Ejemplo de conversión de tipo enum -> string
    public void setTipo(String tipo) {
        //this.tipo = (TiposJugador)Enum.Parse(typeof(TiposJugador), tipo, true); // Ejemplo de conversión de tipo string -> enum
    	this.tipo = TiposJugador.getElemento(tipo); // Ejemplo de conversión de tipo string -> enum
        _interfaz_Jugador.setTipo(this.ID, this.tipo.ordinal());
    }

    public long getID() { return this.ID; }

    public boolean estaConectado() {
        boolean conectado;
        conectado = (this.estado.toString() == "CONECTADO");
        return conectado;
    }
    public void conecta() { this.estado = EstadosJugador.CONECTADO; }
    public void desconecta() { this.estado = EstadosJugador.DESCONECTADO; }

    public long getRegion() { return this.region_ID; }

    //Esta función la ejecutamos al crear el usuario
    public void creaCapital()
    {
        Posicion p = new Posicion();
        if (this.tipo==TiposJugador.EMPERADOR)
        {
            p = Mapa.getPosicionNuevoEmperador(); //En realidad la posición de la capital
        }

        //En la posicion generaremos una poblacion de tipo Capital Y en esa celda meteremos Muuuucha poblacion (recurso trabajadores)            
        Celda c = Mapa.getCelda(p);
        Recurso r = new Recurso(Recurso.TiposRecurso.TRABAJADORES,1000000,c.getID(),Recurso.TiposReceptor.CELDA);            
        c.addRecurso(r);

        this.dirigente._set_Palacio(new Palacio(Productor.TiposProductor.CASTILLO, c.getID(), this.ID, 1));
        this.dirigente._get_Palacio().addRecurso(Recurso.TiposRecurso.TRABAJADORES);
        this.dirigente._get_Palacio().addRecurso(Recurso.TiposRecurso.ORO);
        
        this.dirigente._set_Gobierno(new Gobierno(Productor.TiposProductor.GOBIERNO, c.getID(), this.ID, 1));

        long duracion = 600; //10 horas        
        LocalDateTime fin = LocalDateTime.now().plusMinutes((long) duracion);
        Instant instant = fin.atZone(ZoneId.systemDefault()).toInstant();
        new Evento(this.dirigente._get_Palacio().getJugador(), (long)duracion, Date.from(instant), this.dirigente._get_Palacio().getID(), _Productor_rutinas.AUMENTA, "");

        //Creamos Almacen...        
        this.dirigente._set_Almacen(new Almacen(Productor.TiposProductor.ALMACEN, c.getID(), this.ID, 1));
        this.dirigente._get_Almacen().addRecurso(Recurso.TiposRecurso.COMIDA);
        this.dirigente._get_Almacen().addRecurso(Recurso.TiposRecurso.HIERRO);
        this.dirigente._get_Almacen().addRecurso(Recurso.TiposRecurso.MADERA);
        this.dirigente._get_Almacen().addRecurso(Recurso.TiposRecurso.PIEDRA);

        //Creamos la poblacion
        this.dirigente._set_Capital(new Poblacion(this, Poblacion.TiposPoblacion.CAPITAL, c.getID()));         
    }
}

class _interfaz_Jugador {
	static class __usuario
    {
        public long id;
        public String nombre;
        public String pass;
        public int tipo;
        public long region;
        public int nivelTutorial;
    }

    public static long existe(String usuario, String pass)
    {
        String sql = "SELECT ID FROM Usuarios WHERE UPPER(Nombre)='" + usuario.toUpperCase() + "' AND Pass='" + pass + "'";
        ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
        long id = -1;
		try {			
			while(resultado.next()) {
				id = resultado.getLong("ID");				
			}		
		} catch (SQLException e) {
		// 	TODO Auto-generated catch block
			e.printStackTrace();
		}
        return id;
    }

    public static long existe(String usuario)
    {
        String sql = "SELECT ID FROM Usuarios WHERE UPPER(Nombre)='" + usuario.toUpperCase() + "'";
        ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
        long id = -1;
		try {			
			while(resultado.next()) {
				id = resultado.getLong("ID");				
			}		
		} catch (SQLException e) {
		// 	TODO Auto-generated catch block
			e.printStackTrace();
		}
        return id;
    }

    public static long creaUsuario(String usuario, String pass)
    {
        long nuevoID;
        nuevoID = Mapa.getSiguienteID("USUARIO");

        String sql = "INSERT INTO Usuarios (ID,Nombre,Pass) VALUES (" + nuevoID + ",'" + usuario + "','" + pass + "')";
        int filas = Jdbc.ejecuta(Mapa.conexion, sql);
        if (filas == -1) return -1; //Aqui mejor lanzar una excepcion

        return nuevoID;
    }
    
    public static __usuario datosUsuario(long id)
    {
        __usuario miUsuario = new __usuario();

        String sql = "SELECT Nombre,Pass,Tipo,Region,COALESCE(NivelTutorial,1) AS NivelTutorial FROM Usuarios WHERE ID=" + id;
        ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);        
		try {			
			while(resultado.next()) {
				id = resultado.getLong("ID");
				miUsuario.id = id;				
	            miUsuario.nombre = resultado.getString("Nombre");
	            miUsuario.pass = resultado.getString("Pass");
	            miUsuario.tipo = resultado.getInt("Tipo");
	            miUsuario.region = resultado.getLong("Region");
	            miUsuario.nivelTutorial = resultado.getInt("NivelTutorial");
			}		
		} catch (SQLException e) {
		// 	TODO Auto-generated catch block
			e.printStackTrace();
		}
		return miUsuario;		
    }

    public static int setNombre(long id, String nombre)
    {
        String sql = "UPDATE Usuarios SET Nombre='" + nombre + "' WHERE ID=" + id;
        int filas = Jdbc.ejecuta(Mapa.conexion, sql);
        if (filas == -1) return -1; //Aqui mejor lanzar una excepcion
        return 0;
    }

    public static int setPass(long id, String pass)
    {
        String sql = "UPDATE Usuarios SET Pass='" + pass + "' WHERE ID=" + id;
        int filas = Jdbc.ejecuta(Mapa.conexion, sql);
        if (filas == -1) return -1; //Aqui mejor lanzar una excepcion
        return 0;
    }

    public static int setTipo(long id, int tipo)
    {
        String sql = "UPDATE Usuarios SET Tipo='" + tipo + "' WHERE ID=" + id;
        int filas = Jdbc.ejecuta(Mapa.conexion, sql);
        if (filas == -1) return -1; //Aqui mejor lanzar una excepcion
        return 0;
    }

    public static long getNumeroEmperadores()
    {
        String sql = "SELECT COUNT(*) AS NumRegistros FROM Usuarios WHERE Tipo=3";
        ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
        long numRegistros = 0;
		try {			
			while(resultado.next()) {
				numRegistros = resultado.getLong(0);				
			}		
		} catch (SQLException e) {
		// 	TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return numRegistros;
    }

    public static int setNivelTutorial(long id, long nivel)
    {
        String sql = "UPDATE Usuarios SET NivelTutorial=" + nivel + " WHERE ID=" + id;
        int filas = Jdbc.ejecuta(Mapa.conexion, sql);
        if (filas == -1) return -1; //Aqui mejor lanzar una excepcion
        return 0;
    }
}