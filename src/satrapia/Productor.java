package satrapia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import postgresql.Jdbc;

public class Productor {
	public enum TiposProductor {
		CASTILLO, //Produce dinero mediante impuestos
        ACADEMIA, //Genera puntos de investigacion
        CUARTEL, //Genera tropas que van a ejercitos
        CENTRO_INTELIGENCIA, //Generan tropas especiales (espias)
        ALMACEN, //NO genera pero almacena
        BANCO, //Es un almacen que almacena y si genera
        EMBAJADA, //Para satrapas - Gestion de ordenes y eventos
        GOBIERNO, // Para Emperadores y Jefes - Gestion de ordenes y eventos
        GRANJA, // Genera comida
        MINA_HIERRO, // Genera hierro
        MINA_ORO, // Genera oro
        SERRERIA, // Genera madera
        CANTERA, // Genera piedra
        EJERCITO //Almacena tropas
    };

    public TiposProductor tipo;

    protected long ID;
    public long getID() { return this.ID; }
    public void setID( long id) { this.ID = id; }

    protected int celda;
    public void _set_Celda(int valor) {celda=valor;_interfaz_Productor.setCelda(this.ID, this.celda);}
    public int _get_Celda() {return celda;}
        
    public void setCelda(int celda) { this.celda = celda; }

    public long jugador;
    public long getJugador() { return this.jugador; }

    private ArrayList<Potencial> potenciales = new ArrayList<Potencial>();
    
    public int nivel;
    public void _set_Nivel(int valor) {
    	nivel=valor;
    	_interfaz_Productor.setNivel(this.ID, this.nivel);
    	Double rendimiento = _interfaz_Productor.getRendimientoNivel(this.tipo.ordinal(), this.nivel);
        for (Potencial pot : this.potenciales){
        	pot.setRendimiento(rendimiento);
        }        
    }
    public int _get_Nivel() {return nivel;}
    
    public void setNivel(int nivel) { this.nivel = nivel; }

    protected int cardinal;
    public int getCardinal() { return this.cardinal; }

    private ArrayList<Recurso> recursos = new ArrayList<Recurso>();    
    private ArrayList<Recurso> costes = new ArrayList<Recurso>();
    
    public ArrayList<Recurso> getCostes() {return costes;}
                
    public Productor() {}

    public Productor(long jugador, int celda, TiposProductor tipo) {
    	_interfaz_Productor.__productor p = _interfaz_Productor.loadProductor(jugador,celda,tipo);
        this.tipo = tipo;
        this.celda = celda;
        this.jugador = jugador;            
        this.recursos = p.recursos;
        this.nivel = p.nivel;
        this.cardinal = p.cardinal;

        this.getPotenciales();
    }

    public Productor(long id) {
        this.ID = id;
        _interfaz_Productor.__productor p = _interfaz_Productor.loadProductor(id);
        this.tipo = TiposProductor.values()[p.tipo];
        this.celda = p.celda;
        this.jugador = p.jugador;
        this.recursos = p.recursos;
        this.nivel = p.nivel;
        this.cardinal = p.cardinal;

        this.getPotenciales();
    }

    public Productor(TiposProductor tipo, int celda, long jugador, int nivel) {
    	_interfaz_Productor.__productor p = _interfaz_Productor.creaProductor(tipo.ordinal(), celda, jugador, nivel, 1);
        this.ID = p.id;
        this.tipo = TiposProductor.values()[p.tipo];
        this.celda = p.celda;
        this.jugador = p.jugador;
        this.nivel = nivel;
        this.cardinal = 1;
            
        this.getPotenciales();
    }

    public TiposProductor getTipo() { return this.tipo;}

    public void setRecursos(ArrayList<Recurso> r) {
    	this.recursos = r;
    }

    public ArrayList<Recurso> getRecursos() { return this.recursos; }

        //Gestión de movimiento
    public void setPosicion(Posicion p)
    {
    	Celda c = Mapa.getCelda(p);
        this.celda = c.getID(); //Automaticamente se guarda en la BD
    }
                
    public void getPotenciales()
    {
    	ArrayList<__potencial> p = _interfaz_Productor.loadPotenciales(this.ID);    	
        for (__potencial registro : p)
        {
        	Potencial pot = new Potencial(registro.id, registro.tipo, registro.baseRecurso, registro.rendimiento);
            potenciales.add(pot);
        }
    }    
                
    private Potencial getPotencial(Recurso.TiposRecurso tipo)
    {
    	Potencial r;
    	Iterator<Potencial> recursosIterator = potenciales.iterator();    	
		while (recursosIterator.hasNext()) {
			r=recursosIterator.next();
			if (r.getTipo()==tipo.ordinal()) return r;			
		}
		throw new NullPointerException();		
    }

    public int getCantidadExtraccion(Recurso.TiposRecurso tipo)
    {
        Potencial p = this.getPotencial(tipo);
        return p.valor();
    }

    //Gestión de los recursos
    public void addRecurso(Recurso.TiposRecurso tipo)
    {
        __recurso p = _interfaz_Productor.creaRecurso(tipo, this.ID);
        Recurso r = new Recurso(p.id);            
        recursos.add(r);                            
    }

    public void asignaRecursoToLista(Recurso r) { recursos.add(r); }

    private Recurso getRecurso(Recurso.TiposRecurso indice)
    {
    	Recurso r;
    	Iterator<Recurso> recursosIterator = recursos.iterator();    	
		while (recursosIterator.hasNext()) {
			r=recursosIterator.next();
			if (r._get_Tipo()==indice) return r;			
		}
		throw new NullPointerException();    		
    }

    public boolean existeRecurso(Recurso.TiposRecurso tipo)
    {        	
        for (Recurso r : this.recursos)
        {
            if (r._get_Tipo() == tipo) return true;
        }
        return false;
    }

    public long stock(Recurso.TiposRecurso recurso)
    {
        //Determinar el recursos en recursos y devolver su stock;
        Recurso c = getRecurso(recurso);
        return c._get_Cantidad();
    }

    public void mete(Recurso.TiposRecurso recurso, long cantidad)
    {
        //Determinar el recursos en recursos y sumarle stock;
        Recurso c = getRecurso(recurso);
        c.addCantidad(cantidad);
    }

    public long saca(Recurso.TiposRecurso recurso, long cantidad)
    {
        //Determinar el recurso en recursos y restarle stock;
        Recurso c = getRecurso(recurso);
        if (c._get_Cantidad() < cantidad) return -1;
        c.extrae(cantidad);
        return cantidad;
    }

    public void extrae(Recurso.TiposRecurso recurso)
    {
        //Primero se determina segun su potencial cuanto va a extraer cada vez. Entonces se genera una tarea. Cuando se cumpla la tarea se asignara la cantidad de recurso al recurso del Productor.
        //Si no está lleno se vuelve a empezar. Si está lleno se paraliza la extracción y se envía un correo.
    }
    
    public static ArrayList<Productor> getListaProductoresJugadorTipo(long jugador, int tipo) { return _interfaz_Productor.getListaProductoresJugadorTipo(jugador, tipo);}
    public static ArrayList<Productor> getListaProductoresJugador(long jugador) { return _interfaz_Productor.getListaProductoresJugador(jugador);}
            
    public void ejecuta(int accion, ArrayList<String> parametros) { }
}

    class Ejercito extends Productor
    {
        private long numero;

        public Ejercito(long id) {
        	super(id);
            //Estas instrucciones desde la base de datos
            numero = 1; //Primer ejercito
        }

        public Ejercito(TiposProductor tipo, int celda, long jugador, int nivel) { 
        	super (tipo, celda, jugador, nivel);
        }

        public Ejercito(Productor productor)
        {
            this.ID = productor.getID();
            this.nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
            this.cardinal = productor.getCardinal();
        }
        
        public long getNumero() {return numero; }

        public void ejecuta(int accion, ArrayList<String> parametros)
        {

        }
    }

    class Granja extends Productor
    {

        public Granja(long id)
        {
        	super(id);
        }

        public Granja(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }

        public Granja(Productor productor)
        {
            this.ID = productor.getID();
            this.nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }
        
        public void setPotencialExtraccion()
        {
            //this.addPotencial("EXTRACCION", 100, 1);
        }

        public void ejecuta(int accion, ArrayList<String> parametros)
        {
            switch (accion)
            {
                case _Productor_rutinas.EXTRAE:
                    {
                        int cantidad = this.getCantidadExtraccion(Recurso.TiposRecurso.COMIDA) ; //La cantidad de comida que extraemos.

                        _Productor_rutinas.EXTRAE_COMIDA(this, cantidad);
                        break;
                    }
            }
        }
    }

    class MinaHierro extends Productor
    {

        public MinaHierro(long id)
        {
        	super(id);
        }

        public MinaHierro(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }
        
        public void ejecuta(int accion, ArrayList<String> parametros)
        {
            switch (accion)
            {
                case _Productor_rutinas.EXTRAE:
                    {
                        int cantidad = this.getCantidadExtraccion(Recurso.TiposRecurso.HIERRO); //La cantidad de hierro que extraemos.

                        _Productor_rutinas.EXTRAE_HIERRO(this, cantidad);
                        break;
                    }
            }
        }
    }

    class Serreria extends Productor
    {

        public Serreria(long id)
        {
        	super(id);
        }

        public Serreria(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }
        
        public void ejecuta(int accion, ArrayList<String> parametros)
        {
            switch (accion)
            {
                case _Productor_rutinas.EXTRAE:
                    {
                        int cantidad = this.getCantidadExtraccion(Recurso.TiposRecurso.MADERA);
                        _Productor_rutinas.EXTRAE_MADERA(this, (long)cantidad);
                        break;
                    }
            }
        }
    }

    class Cantera extends Productor
    {

        public Cantera(long id)
        {
        	super(id);
        }

        public Cantera(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }

        public void ejecuta(int accion, ArrayList<String> parametros)
        {
            switch (accion)
            {
                case _Productor_rutinas.EXTRAE:
                    {
                        int cantidad = this.getCantidadExtraccion(Recurso.TiposRecurso.PIEDRA);
                        _Productor_rutinas.EXTRAE_PIEDRA(this, (long)cantidad);
                        break;
                    }
            }
        }
    }

    class MinaOro extends Productor
    {       
        public MinaOro(long id) 
        {
        	super(id);
        }

        public MinaOro(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }
        
        public void ejecuta(int accion, ArrayList<String> parametros)
        {
            switch (accion)
            {
                case _Productor_rutinas.EXTRAE:
                    {
                        int cantidad = this.getCantidadExtraccion(Recurso.TiposRecurso.ORO);
                        _Productor_rutinas.EXTRAE_MINA_ORO(this,(long)cantidad);
                        break;
                    }
            }
        }
    }

    class Palacio extends Productor
    {
        public Palacio(long id)
        {
        	super(id);
        }

        public Palacio(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }

        public Palacio(Productor productor)
        {
            this.ID = productor.getID();
            this.nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }

        public void ejecuta(int accion, ArrayList<String> parametros)
        {
            switch (accion)
            {
                case _Productor_rutinas.AUMENTA:
                    {
                        int cantidad = this.getCantidadExtraccion(Recurso.TiposRecurso.TRABAJADORES);
                        _Productor_rutinas.AUMENTA_POBLACION(this, (long)cantidad);
                        break;
                    }

                case _Productor_rutinas.EXTRAE:
                    {
                        int cantidad = this.getCantidadExtraccion(Recurso.TiposRecurso.DINERO);
                        _Productor_rutinas.RECAUDA_IMPUESTOS(this,(float)cantidad);
                        break;
                    }
            }
        }
    }

    class Gobierno extends Productor
    {
        public Gobierno(long id)
        {
        	 super(id);
        }

        public Gobierno(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }

        public Gobierno(Productor productor)
        {
            this.ID = productor.getID();
            this.nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }                        
    }

    class Almacen extends Productor
    {
        public Almacen(long id)
        {
        	super(id);
        }

        public Almacen(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }

        public Almacen(Productor productor)
        {
            this.ID = productor.getID();
            this.nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }
    }

    class Banco extends Productor
    {
        public Banco(long id)
        {
        	super(id);
        }

        public Banco(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }

        public Banco(Productor productor)
        {
            this.ID = productor.getID();
            this.nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }
    }

    class Academia extends Productor
    {
        public Academia(long id)
        {
        	super(id);
        }

        public Academia(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }

        public Academia(Productor productor)
        {
            this.ID = productor.getID();
            this.nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }
    }

    class Cuartel extends Productor
    {
        public Cuartel(long id)
        {
        	super(id);
        }

        public Cuartel(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }

        public Cuartel(Productor productor)
        {
            this.ID = productor.getID();
            this.nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.jugador = productor.getJugador();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
            this.cardinal = productor.getCardinal();
        }

        public void entrena(Recurso.TiposRecurso tipoRecurso, int cantidad, int ejercito) //ejercito=1 en C#
        {
            Ejercito miEjercito;
            
            Jugador j = new Jugador(this.getJugador());
            //Si no existe el ejercito se crea
            ArrayList<Productor> lista = j.dirigente.getListaProductores(Productor.TiposProductor.EJERCITO);
            if (lista.size() == 0)
            {
                Poblacion capital = j.dirigente._get_Capital();
                int celda = capital.idCelda();
                miEjercito = new Ejercito(Productor.TiposProductor.EJERCITO, celda, j.getID(), 1);
            }
            else
            {
                miEjercito = new Ejercito(lista.get(0));
            }
            //Se asigna al recurso ejercito el numero de soldados reclutados. Esto se hará con un evento
            _Productor_rutinas.RECLUTA(miEjercito, tipoRecurso, cantidad);
        }
    }

    class CentroInteligencia extends Productor
    {
        public CentroInteligencia(long id)
        {
        	super(id);
        }

        public CentroInteligencia(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }

        public CentroInteligencia(Productor productor)
        {
            this.ID = productor.getID();
            this.nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }
    }

    class Embajada extends Productor
    {
        public Embajada(long id)
        {
        	super(id);
        }

        public Embajada(TiposProductor tipo, int celda, long jugador, int nivel)
        {
        	super(tipo, celda, jugador, nivel);
        }

        public Embajada(Productor productor)
        {
            this.ID = productor.getID();
            this.nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }
    }

/******************************************************************************************************************************************************************/
/******************************************************************************************************************************************************************/
/******************************************************************************************************************************************************************/

    final class __potencial
    {
        public long id;
        public int tipo;
        public Double rendimiento;
        public int baseRecurso;            
    }
    
    class _interfaz_Productor {
    	static class __productor
        {
            public long id;
            public int tipo;
            public int celda;            
            public long jugador;
            public int nivel;
            public int cardinal;
            public Double rendimiento;
            public ArrayList<Recurso> recursos;
        }
    	
        public static __productor loadProductor(long id)
        {
            __productor p = new __productor();
            String sql = "" +
                "SELECT Tipo,Celda,Jugador,Nivel,"
                + "COALESCE((SELECT COALESCE(Rendimiento,1) FROM Productor_Niveles WHERE Tipo_Productor=P.Tipo AND Nivel=P.Nivel),1) AS Rendimiento,"
                + "COALESCE(Cardinal,0) AS Cardinal " +
                "FROM Productor P WHERE ID=" + id;
            ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
    		try {
    			while(resultado.next()) {
    				p.id = id;
    				p.tipo = resultado.getInt("Tipo");
    				p.celda = resultado.getInt("Celda");
    				p.jugador = resultado.getLong("Jugador");
    				p.nivel = resultado.getInt("Nivel");
    				p.rendimiento = resultado.getDouble("Rendimiento");
    				p.cardinal = resultado.getInt("Cardinal");
    				p.recursos  = new ArrayList<Recurso>();
    				
    				sql = " select id from Recursos where id_Productor = " + id;
    				ResultSet resultado2 = Jdbc.consulta(Mapa.conexion, sql);
    				long idRecurso;
    				try {
    	    			while(resultado2.next()) {
    	    				 idRecurso = resultado2.getLong("Jugador");
    	                     Recurso r = new Recurso(idRecurso);
    	                     p.recursos.add(r);
    	    			}
    				} catch (SQLException e) {
    	    		// 	TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		}
    				resultado2.close();
    			}
    			resultado.close();
    		} catch (SQLException e) {
    		// 	TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		return p;
    	}

        public static __productor loadProductor(long jugador, int celda, Productor.TiposProductor tipo)
        {
            __productor p = new __productor();
            String sql = "SELECT ID,Nivel FROM Productor P WHERE Tipo =" + tipo.ordinal() + " AND Jugador = " + jugador + " AND Celda = " + celda;
            ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);			
			try {
    			while(resultado.next()) {
    				p.id = resultado.getLong("ID");
                    p.tipo = tipo.ordinal();
                    p.celda = celda;
                    p.jugador = jugador;
                    p.nivel = resultado.getInt("Nivel");
    				    				
                    sql = " select id from Recursos where id_Productor = " + p.id;
                    ResultSet resultadoRecursos = Jdbc.consulta(Mapa.conexion, sql);
    				long idRecurso;
    				try {
    	    			while(resultadoRecursos.next()) {
    	    				 idRecurso = resultadoRecursos.getLong("Jugador");
    	                     Recurso r = new Recurso(idRecurso);
    	                     p.recursos.add(r);
    	    			}
    				} catch (SQLException e) {
    	    		// 	TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		}
    			}
			} catch (SQLException e) {
    		// 	TODO Auto-generated catch block
    			e.printStackTrace();
    		}
			
			return p;
        }

        public static __productor creaProductor(int tipo, int celda, long jugador, int nivel, float rendimiento)
        {
            long nuevoID;
            __productor p = new __productor();
            
            nuevoID = Mapa.getSiguienteID("PRODUCTOR");
            String sql = "INSERT INTO Productor (ID,Tipo, Celda, Jugador, Nivel, Cardinal, Rendimiento) VALUES (" + nuevoID + "," + tipo + "," + celda + "," + jugador + "," + nivel + ",1," + rendimiento + ")";
            int filas = Jdbc.ejecuta(Mapa.conexion, sql);
            if (filas == -1) return null; //Aqui mejor lanzar una excepcion
            
            p.id = nuevoID;
            p.tipo = tipo;
            p.celda = celda;
            p.jugador = jugador;
            p.nivel = nivel;
            p.cardinal = 1;
            p.rendimiento = 0.0; // *****NO SE GASTA
            
            return p;
        }

        public static int setCelda(long id, int celda)
        {
            String sql = "UPDATE Productor SET Celda=" + celda + " WHERE ID=" + id;
            int filas = Jdbc.ejecuta(Mapa.conexion, sql);
            if (filas == -1) return -1; //Aqui mejor lanzar una excepcion
            return 0;
        }

        public static __recurso creaRecurso(Recurso.TiposRecurso tipo, long ID)
        {
            __recurso recurso = new __recurso(); 

            recurso.tipo = tipo.ordinal();
            recurso.tipoReceptor = Recurso.TiposReceptor.PRODUCTOR.ordinal();
            recurso.cantidad = 0;
            recurso.receptor = ID;

            recurso.id =_interfaz_Recurso.guarda(recurso);
            return recurso;
        }
     
        public static ArrayList<__potencial> loadPotenciales(long idProductor)
        {
            ArrayList<__potencial> lista = new ArrayList<__potencial>();

            String sql =
                "SELECT ID,Tipo,COALESCE(Base,0) AS Base,"+
                "COALESCE((SELECT Rendimiento FROM Productor_Niveles WHERE (Tipo_Productor,Nivel) = (SELECT Tipo,Nivel FROM Productor WHERE ID=" + idProductor + ") ) , 1) AS Rendimiento " +
                "FROM Recursos P " + 
                "WHERE ID_Productor = " + idProductor;
            ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
    		try {
    			while(resultado.next()) {
    				__potencial p = new __potencial();
    				
    				p.id = resultado.getLong("ID");
    				p.tipo = resultado.getInt("Tipo");
    				p.baseRecurso = resultado.getInt("Base");
    				p.rendimiento = resultado.getDouble("Rendimiento");
    				
    				lista.add(p);
    			}
    		} catch (SQLException e) {
    		// 	TODO Auto-generated catch block
    			e.printStackTrace();
    		}	
    		return lista;            
        }

        public static Posicion getPosicionCapital(long jugador)
        {
            Posicion pos;
            int posX=0; int posY=0;
            String sql = "SELECT Posicion_X,Posicion_Y FROM Celda WHERE ID=(SELECT Celda FROM Productor WHERE Tipo=0 AND Jugador=" + jugador+")";
            ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
    		try {
    			while(resultado.next()) {    				
    				posX = resultado.getInt("Posicion_X");
    				posY = resultado.getInt("Posicion_Y");    				
    			}
    		} catch (SQLException e) {
    		// 	TODO Auto-generated catch block
    			e.printStackTrace();
    		}	
    		pos = new Posicion(posX,posY);
            return pos;    		
        }

        public static ArrayList<Productor> getListaProductoresJugadorTipo(long jugador, int tipo)
        {
            ArrayList<Productor> lista = new ArrayList<Productor>();
            Long id;
            
            String sql = "SELECT ID FROM Productor P WHERE Jugador = " + jugador + " AND Tipo = " + tipo + " ORDER BY ID";
            ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
    		try {
    			while(resultado.next()) {    				
    				id = resultado.getLong("ID");
    				Productor productor = new Productor(id);
    				lista.add(productor);    				
    			}
    		} catch (SQLException e) {
    		// 	TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return lista;
        }

        public static ArrayList<Productor> getListaProductoresJugador(long jugador)
        {
            ArrayList<Productor> lista = new ArrayList<Productor>();
            Long id;

            String sql = "SELECT ID FROM Productor P WHERE Jugador = " + jugador + " ORDER BY ID";
            ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
    		try {
    			while(resultado.next()) {    				
    				id = resultado.getLong("ID");
    				Productor productor = new Productor(id);
    				lista.add(productor);    				
    			}
    		} catch (SQLException e) {
    		// 	TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return lista;
        }

        public static int setNivel(long id, int nivel) 
        {
            String sql = "UPDATE Productor SET Nivel =" + nivel + " WHERE ID=" + id;
            int filas = Jdbc.ejecuta(Mapa.conexion, sql);
            if (filas == -1) return -1; //Aqui mejor lanzar una excepcion
            return 0;
        }

        public static Double getRendimientoNivel(int tipo, int nivel)
        {
            Double rendimiento = 1.0;

            String sql = "SELECT Rendimiento FROM Productor_Niveles WHERE Tipo_Productor = " + tipo + " AND Nivel = " + nivel;
            ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
            try {
    			while(resultado.next()) {    				
    				rendimiento = resultado.getDouble("ID");    				    				
    			}
    		} catch (SQLException e) {
    		// 	TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            return rendimiento;
        }

        public static ArrayList<Integer> getListaTipos()
        {
        	ArrayList<Integer> lista = new ArrayList<Integer>();
            int id;
            
            String sql = "SELECT ID,Nombre FROM Tipos_Productor ORDER BY ID";            
            ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
            try {
    			while(resultado.next()) {    				
    				id = resultado.getInt("ID");
    				lista.add(id);
    			}
    		} catch (SQLException e) {
    		// 	TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            return lista;
        }
    }