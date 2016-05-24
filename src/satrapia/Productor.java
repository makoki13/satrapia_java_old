package satrapia;

import java.util.ArrayList;
import java.util.Iterator;

import satrapia.Recurso.TiposRecurso;

class Potencial
{
    private long recurso;
    private int tipoRecurso;        
    private Double rendimiento ;
    private int valorBase;
            
    public Potencial(long recurso, int tipoRecurso, int valorBase, Double rendimiento)
    {
        this.recurso = recurso;
        this.tipoRecurso = tipoRecurso;
        this.valorBase = valorBase;
        this.rendimiento = rendimiento;            
    }

    public int getTipo() { return this.tipoRecurso; }

    public int valor() { return (int)(this.valorBase * this.rendimiento);}
    
    public void setRendimiento(Double r)
    {
        this.rendimiento = r;
    }
}

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
    public void _set_Celda(int valor) {celda=valor;_interfaz_productor.setCelda(this.ID, this._celda);}
    public int _get_Celda() {return celda;}
        
    public void setCelda(int celda) { this.celda = celda; }

    public long jugador;
    public long getJugador() { return this.jugador; }

    public int nivel;
    public void _set_Nivel(int valor) {
    	nivel=valor;
    	_interfaz_productor.setNivel(this.ID, this._nivel);
    	Double rendimiento = _interfaz_productor.getRendimientoNivel((int)this.tipo, this._nivel);
        foreach (Potencial p in this.potenciales)
        {
            p.setRendimiento(rendimiento);
        }
    }
    public int _get_Nivel() {return nivel;}
    
    public void setNivel(int nivel) { this.nivel = nivel; }

    protected int cardinal;
    public int getCardinal() { return this.cardinal; }

    private ArrayList<Recurso> recursos = new ArrayList<Recurso>();
    private ArrayList<Potencial> potenciales = new ArrayList<Potencial>();
    private ArrayList<Recurso> costes = new ArrayList<Recurso>();
                
    public Productor() {}

    public Productor(long jugador, int celda, TiposProductor tipo) {
    	_interfaz_productor.__productor p = _interfaz_productor.loadProductor(jugador,celda,tipo);
        this.tipo = tipo;
        this._celda = celda;
        this.jugador = jugador;            
        this.recursos = p.recursos;
        this._nivel = p.nivel;
        this.cardinal = p.cardinal;

        this.getPotenciales();
    }

    public Productor(long id) {
        this.ID = id;
        _interfaz_productor.__productor p = _interfaz_productor.loadProductor(id);
        this.tipo = (TiposProductor)p.tipo;
        this._celda = p.celda;
        this.jugador = p.jugador;
        this.recursos = p.recursos;
        this._nivel = p.nivel;
        this.cardinal = p.cardinal;

        this.getPotenciales();
    }

    public Productor(TiposProductor tipo, int celda, long jugador, int nivel) {
    	_interfaz_productor.__productor p = _interfaz_productor.creaProductor((int)tipo, celda, jugador, nivel, 1);
        this.ID = p.id;
        this.tipo = (TiposProductor)p.tipo;
        this._celda = p.celda;
        this.jugador = p.jugador;
        this._nivel = nivel;
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
    	List<_interfaz_productor.__potencial> p = _interfaz_productor.loadPotenciales(this.ID);
        foreach (_interfaz_productor.__potencial registro in p)
        {
        	Potencial pot = new Potencial(registro.id, registro.tipo, registro.baseRecurso, registro.rendimiento);
            potenciales.Add(pot);
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
            _interfaz_Recurso.__recurso p = _interfaz_productor.creaRecurso(tipo, this.ID);
            Recurso r = new Recurso(p.id);            
            recursos.Add(r);                            
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
                
        public void ejecuta(int accion, ArrayList<String> parametros) { }
    }

    class Ejercito extends Productor
    {
        private long numero;

        public Ejercito(long id) {
        	super(id);
            //Estas instrucciones desde la base de datos
            this.numero = 1; //Primer ejercito
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

    public class Serreria extends Productor
    {

        public Serreria(long id) : base(id)
        {

        }

        public Serreria(TiposProductor tipo, int celda, long jugador, int nivel) : base(tipo, celda, jugador, nivel)
        {

        }
        
        public override void ejecuta(int accion, List<object> parametros)
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

    public class Cantera extends Productor
    {

        public Cantera(long id) : base(id)
        {

        }

        public Cantera(TiposProductor tipo, int celda, long jugador, int nivel) : base(tipo, celda, jugador, nivel)
        {

        }

        public override void ejecuta(int accion, List<object> parametros)
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

    public class MinaOro extends Productor
    {       
        public MinaOro(long id) : base(id)
        {

        }

        public MinaOro(TiposProductor tipo, int celda, long jugador, int nivel) : base (tipo, celda, jugador, nivel)
        {

        }
        
        public override void ejecuta(int accion, List<object> parametros)
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

    public class Palacio extends Productor
    {
        public Palacio(long id) : base(id)
        {

        }

        public Palacio(TiposProductor tipo, int celda, long jugador, int nivel) : base(tipo, celda, jugador, nivel)
        {

        }

        public Palacio(Productor productor)
        {
            this.ID = productor.getID();
            this._nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }

        public override void ejecuta(int accion, List<object> parametros)
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

    public class Gobierno extends Productor
    {
        public Gobierno(long id) : base(id)
        {

        }

        public Gobierno(TiposProductor tipo, int celda, long jugador, int nivel) : base(tipo, celda, jugador, nivel)
        {

        }

        public Gobierno(Productor productor)
        {
            this.ID = productor.getID();
            this._nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }                        
    }

    public class Almacen extends Productor
    {
        public Almacen(long id) : base(id)
        {

        }

        public Almacen(TiposProductor tipo, int celda, long jugador, int nivel) : base(tipo, celda, jugador, nivel)
        {

        }

        public Almacen(Productor productor)
        {
            this.ID = productor.getID();
            this._nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }
    }

    public class Banco extends Productor
    {
        public Banco(long id) : base(id)
        {

        }

        public Banco(TiposProductor tipo, int celda, long jugador, int nivel) : base(tipo, celda, jugador, nivel)
        {

        }

        public Banco(Productor productor)
        {
            this.ID = productor.getID();
            this._nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }
    }

    public class Academia extends Productor
    {
        public Academia(long id) : base(id)
        {

        }

        public Academia(TiposProductor tipo, int celda, long jugador, int nivel) : base(tipo, celda, jugador, nivel)
        {

        }

        public Academia(Productor productor)
        {
            this.ID = productor.getID();
            this._nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }
    }

    public class Cuartel extends Productor
    {
        public Cuartel(long id) : base(id)
        {

        }

        public Cuartel(TiposProductor tipo, int celda, long jugador, int nivel) : base(tipo, celda, jugador, nivel)
        {

        }

        public Cuartel(Productor productor)
        {
            this.ID = productor.getID();
            this._nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.jugador = productor.getJugador();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
            this.cardinal = productor.getCardinal();
        }

        public void entrena(Recurso_Militar.TiposRecurso tipoRecurso, int cantidad, int ejercito = 1)
        {
            Ejercito miEjercito;

            Jugador j = new Jugador(this.getJugador());
            //Si no existe el ejercito se crea
            List<Productor> lista = j.dirigente.getListaProductores(Productor.TiposProductor.EJERCITO);
            if (lista.Count == 0)
            {
                Poblacion capital = j.dirigente.capital;
                int celda = capital.idCelda();
                miEjercito = new Ejercito(Productor.TiposProductor.EJERCITO, celda, j.getID(), 1);
            }
            else
            {
                miEjercito = new Ejercito(lista.ElementAt(0));
            }
            //Se asigna al recurso ejercito el numero de soldados reclutados. Esto se hará con un evento
            _Productor_rutinas.RECLUTA(miEjercito, tipoRecurso, cantidad);
        }
    }

    public class CentroInteligencia extends Productor
    {
        public CentroInteligencia(long id) : base(id)
        {

        }

        public CentroInteligencia(TiposProductor tipo, int celda, long jugador, int nivel) : base(tipo, celda, jugador, nivel)
        {

        }

        public CentroInteligencia(Productor productor)
        {
            this.ID = productor.getID();
            this._nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }
    }

    public class Embajada extends Productor
    {
        public Embajada(long id) : base(id)
        {

        }

        public Embajada(TiposProductor tipo, int celda, long jugador, int nivel) : base(tipo, celda, jugador, nivel)
        {

        }

        public Embajada(Productor productor)
        {
            this.ID = productor.getID();
            this._nivel = productor.nivel;
            this.tipo = productor.getTipo();
            this.setRecursos(productor.getRecursos());
            this.celda = productor.celda;
        }
    }
}
