package satrapia;

import java.time.LocalTime;

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
            
    //public Dirigente dirigente; ***TODO
            
    public Jugador(long id)
    {
        this.ID = id;

        //_interfaz_Jugador.__usuario misDatosUsuario = _interfaz_Jugador.datosUsuario(Mapa.conexion, id); ***TODO
        //this.nombre = misDatosUsuario.nombre; ***TODO
        //this.tipo = (TiposJugador)misDatosUsuario.tipo; ***TODO
        this.estado = EstadosJugador.DESCONECTADO;
        //this.region_ID = misDatosUsuario.region; ***TODO
        //this.dirigente = new Dirigente(this); ***TODO
        //this._nivelTutorial = misDatosUsuario.nivelTutorial; ***TODO            
    }
           
    public String getTipo() { return this.tipo.toString(); } // Ejemplo de conversión de tipo enum -> string
    public void setTipo(String tipo) {
        //this.tipo = (TiposJugador)Enum.Parse(typeof(TiposJugador), tipo, true); // Ejemplo de conversión de tipo string -> enum
    	this.tipo = TiposJugador.getElemento(tipo); // Ejemplo de conversión de tipo string -> enum
        //_interfaz_Jugador.setTipo(Mapa.conexion, this.ID, (int)this.tipo); ***TODO
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

        //this.dirigente.palacio = new Palacio(Productor.TiposProductor.CASTILLO, c.getID(), this.ID, 1); ***TODO
        //this.dirigente.palacio.addRecurso(Recurso.TiposRecurso.TRABAJADORES); ***TODO
        //this.dirigente.palacio.addRecurso(Recurso.TiposRecurso.ORO); ***TODO
        
        //this.dirigente.gobierno = new Gobierno(Productor.TiposProductor.GOBIERNO, c.getID(), this.ID, 1); ***TODO

        long duracion = 600; //10 horas        
        LocalTime fin = LocalTime.now().plusMinutes(duracion);        
        //Evento e = new Evento(this.dirigente.palacio.getJugador(), (long)duracion, fin, this.dirigente.palacio.getID(), _Productor_rutinas.AUMENTA, ""); ***TODO

        //Creamos Almacen...        
        //this.dirigente.almacen = new Almacen(Productor.TiposProductor.ALMACEN, c.getID(), this.ID, 1); ***TODO
        //this.dirigente.almacen.addRecurso(Recurso.TiposRecurso.COMIDA); ***TODO
        //this.dirigente.almacen.addRecurso(Recurso.TiposRecurso.HIERRO); ***TODO
        //this.dirigente.almacen.addRecurso(Recurso.TiposRecurso.MADERA); ***TODO
        //this.dirigente.almacen.addRecurso(Recurso.TiposRecurso.PIEDRA); ***TODO

        //Creamos la poblacion
        //this.dirigente.capital = new Poblacion(this, Poblacion.TiposPoblacion.CAPITAL, c.getID());  ***TODO         
    }
}
