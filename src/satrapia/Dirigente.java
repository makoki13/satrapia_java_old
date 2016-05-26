package satrapia;

import java.util.ArrayList;

public class Dirigente {
    public enum TiposDirigente {EMPERADOR, SATRAPA, JEFE};
    
    private TiposDirigente tipo;

    private long ID;

    private long region;

    private Jugador jugador;

    private Poblacion capital;
    public void _set_Capital(Poblacion valor) {capital=valor;}
    public Poblacion _get_Capital() {return capital;}
    
    //public Poblacion getCapital() { return this.capital; }

    private Palacio palacio;
    public void _set_Palacio(Palacio valor) {palacio=valor;}
    public Palacio _get_Palacio() {return palacio;}

    private Gobierno gobierno;
    public void _set_Gobierno(Gobierno valor) {gobierno=valor;}
    public Gobierno _get_Gobierno() {return gobierno;}
    
    private Almacen almacen;
    public void _set_Almacen(Almacen valor) {almacen=valor;}
    public Almacen _get_Almacen() {return almacen;}
    
    private Academia academia;
    private Cuartel cuartel;
    private CentroInteligencia centroInteligencia;
    private Banco banco;
    private Embajada embajada;

    private ArrayList<Productor> listaProductores = new ArrayList<Productor>();

    private ArrayList<Productor> listaGranjas = new ArrayList<Productor>();
    private ArrayList<Productor> listaMinasOro = new ArrayList<Productor>();
    private ArrayList<Productor> listaMinasHierro = new ArrayList<Productor>();
    private ArrayList<Productor> listaCanteras = new ArrayList<Productor>();
    private ArrayList<Productor> listaSerrerias = new ArrayList<Productor>();
    private ArrayList<Productor> listaEjercitos = new ArrayList<Productor>();

    public CentroInvestigacion centroInvestigacion;
                    
    public Dirigente(Jugador jugador)
    {
        _interfaz_Dirigente.__dirigente dirigente = new _interfaz_Dirigente.__dirigente();

        this.jugador = jugador;
        dirigente = _interfaz_Dirigente.loadDirigente(jugador.getID());
        this.tipo = (TiposDirigente)dirigente.tipo;
        this.ID = dirigente.id;
        this.region = dirigente.region;
        this.capital = new Poblacion(jugador);
        this.listaProductores = _interfaz_productor.getListaProductoresJugador(jugador.getID());
        foreach(Productor p in this.listaProductores)
        {
            switch (p.getTipo())
            {
                case Productor.TiposProductor.ACADEMIA: { this.academia = new Academia(p); break; }
                case Productor.TiposProductor.ALMACEN: { this.almacen = new Almacen(p); break; }
                case Productor.TiposProductor.BANCO: { this.banco = new Banco(p); break; }
                case Productor.TiposProductor.CASTILLO: { this.palacio = new Palacio(p); break; }
                case Productor.TiposProductor.CENTRO_INTELIGENCIA: { this.centroInteligencia = new CentroInteligencia(p); break; }
                case Productor.TiposProductor.CUARTEL: { this.cuartel = new Cuartel(p); break; }
                case Productor.TiposProductor.EMBAJADA: { this.embajada = new Embajada(p); break; }
                case Productor.TiposProductor.GOBIERNO: { this.gobierno = new Gobierno(p); break; }
                case Productor.TiposProductor.GRANJA: { listaGranjas.Add(p); break; }
                case Productor.TiposProductor.MINA_HIERRO: { listaMinasHierro.Add(p); break; }
                case Productor.TiposProductor.MINA_ORO: { listaMinasOro.Add(p); break; }
                case Productor.TiposProductor.CANTERA: { listaCanteras.Add(p); break; }
                case Productor.TiposProductor.SERRERIA: { listaSerrerias.Add(p); break; }
                case Productor.TiposProductor.EJERCITO: { listaEjercitos.Add(p); break; }
            }
        }

        this.centroInvestigacion = new CentroInvestigacion(jugador);
    }

    //Tratamiento de listas de productores

    public Posicion getPosicionCapital()
    {
        return this.capital.posicion;
    }

    public List<Productor> getListaProductores(Productor.TiposProductor tipo)
    {
        switch (tipo)
        {
            case Productor.TiposProductor.ACADEMIA: { List<Productor> lista = new List<Productor>(); lista.Add(this.academia); return lista; }
            case Productor.TiposProductor.ALMACEN: { List<Productor> lista = new List<Productor>(); lista.Add(this.almacen); return lista; }
            case Productor.TiposProductor.BANCO: { List<Productor> lista = new List<Productor>(); lista.Add(this.banco); return lista; }
            case Productor.TiposProductor.CASTILLO: { List<Productor> lista = new List<Productor>(); lista.Add(this.palacio); return lista; }
            case Productor.TiposProductor.CENTRO_INTELIGENCIA: { List<Productor> lista = new List<Productor>(); lista.Add(this.centroInteligencia); return lista; }
            case Productor.TiposProductor.CUARTEL: { List<Productor> lista = new List<Productor>(); lista.Add(this.cuartel); return lista; }
            case Productor.TiposProductor.EMBAJADA: { List<Productor> lista = new List<Productor>(); lista.Add(this.embajada); return lista; }
            case Productor.TiposProductor.GOBIERNO: { List<Productor> lista = new List<Productor>(); lista.Add(this.gobierno); return lista; }

            case Productor.TiposProductor.GRANJA: { return this.listaGranjas; }
            case Productor.TiposProductor.MINA_HIERRO: { return this.listaMinasHierro; }
            case Productor.TiposProductor.MINA_ORO: { return this.listaMinasOro; }
            case Productor.TiposProductor.SERRERIA: { return this.listaSerrerias; }
            case Productor.TiposProductor.CANTERA: { return this.listaCanteras; }
            case Productor.TiposProductor.EJERCITO: { return this.listaEjercitos; }
            default: return null;
        }
    }

    private Productor getProductor(List<Productor> p, long id)
    {
        int index = p.FindIndex(
        delegate (Productor miProductor)
        {
            return (miProductor.getID() == id);
        });

        return p.ElementAt(index);
    }

    //Operaciones que puede realizar cualquier dirigente
    public void recaudarImpuestos() { }

    public void creaMinaDeOro(Posicion pos, int nivel)
    {
        Celda c = Mapa.getCelda(pos);
        MinaOro p = new MinaOro(Productor.TiposProductor.MINA_ORO, c.getID(), this.jugador.getID(), nivel);
        p.addRecurso(Recurso.TiposRecurso.ORO);
        this.listaMinasOro.Add(p);
        double duracion = 0;
        DateTime fin = DateTime.Now.AddMinutes(duracion);
        Evento e = new Evento(p.getJugador(), (long)duracion, fin, p.getID(), _Productor_rutinas.EXTRAE, "");
    }

    public int creaBanco(Posicion pos, int nivel)
    {
        //Para crear un banco debemos tener comprada la investigación BANCA Nivel 1
        if (_interfaz_investigacion.estaInvestigada(Investigacion.TiposInvestigacion.BANCA, 1, this.jugador.getID()) == false)  return -1;
        //Construimos el banco en la capital
        Celda c = Mapa.getCelda(pos);
        Banco b = new Banco(Productor.TiposProductor.BANCO, c.getID(), this.jugador.getID(), nivel);
        b.addRecurso(Recurso.TiposRecurso.DINERO);
        this.banco=b;
        return 0;
    }

    public void creaGranja(Posicion pos, int nivel)
    {
        Celda c = Mapa.getCelda(pos);
        Granja p = new Granja(Productor.TiposProductor.GRANJA, c.getID(), this.jugador.getID(), nivel);
        p.addRecurso(Recurso.TiposRecurso.COMIDA);
        this.listaGranjas.Add(p);
        double duracion = 0;
        DateTime fin = DateTime.Now.AddMinutes(duracion);
        Evento e = new Evento(p.getJugador(), (long)duracion, fin, p.getID(), _Productor_rutinas.EXTRAE, "");
    }

    public void creaMinaDeHierro(Posicion pos, int nivel)
    {
        Celda c = Mapa.getCelda(pos);
        MinaHierro p = new MinaHierro(Productor.TiposProductor.MINA_HIERRO, c.getID(), this.jugador.getID(), nivel);
        p.addRecurso(Recurso.TiposRecurso.HIERRO);
        this.listaMinasHierro.Add(p);
        double duracion = 0;
        DateTime fin = DateTime.Now.AddMinutes(duracion);
        Evento e = new Evento(p.getJugador(), (long)duracion, fin, p.getID(), _Productor_rutinas.EXTRAE, "");
    }

    public void creaSerreria(Posicion pos, int nivel)
    {
        Celda c = Mapa.getCelda(pos);
        Serreria p = new Serreria(Productor.TiposProductor.SERRERIA, c.getID(), this.jugador.getID(), nivel);
        p.addRecurso(Recurso.TiposRecurso.MADERA);
        this.listaSerrerias.Add(p);
        double duracion = 0;
        DateTime fin = DateTime.Now.AddMinutes(duracion);
        Evento e = new Evento(p.getJugador(), (long)duracion, fin, p.getID(), _Productor_rutinas.EXTRAE, "");
    }

    public void creaCantera(Posicion pos, int nivel)
    {
        Celda c = Mapa.getCelda(pos);
        Cantera p = new Cantera(Productor.TiposProductor.CANTERA, c.getID(), this.jugador.getID(), nivel);
        p.addRecurso(Recurso.TiposRecurso.PIEDRA);
        this.listaCanteras.Add(p);
        double duracion = 0;
        DateTime fin = DateTime.Now.AddMinutes(duracion);
        Evento e = new Evento(p.getJugador(), (long)duracion, fin, p.getID(), _Productor_rutinas.EXTRAE, "");
    }

    public int creaAcademia(Posicion pos, int nivel)
    {
        if (_interfaz_investigacion.estaInvestigada(Investigacion.TiposInvestigacion.FILOSOFIA_Y_CIENCIA, 1, this.jugador.getID()) == false) return -1;
        Celda c = Mapa.getCelda(pos);
        Academia p = new Academia(Productor.TiposProductor.ACADEMIA, c.getID(), this.jugador.getID(), nivel);
        this.academia = p;
        return 0;
    }

    public int creaCuartel(Posicion pos, int nivel)
    {
        if (_interfaz_investigacion.estaInvestigada(Investigacion.TiposInvestigacion.INSTRUCCION_MILITAR, 1, this.jugador.getID()) == false) return -1;
        Celda c = Mapa.getCelda(pos);
        Cuartel p = new Cuartel(Productor.TiposProductor.CUARTEL, c.getID(), this.jugador.getID(), nivel);
        this.cuartel = p;
        return 0;
    }

    public int creaCentroInteligencia(Posicion pos, int nivel)
    {
        if (_interfaz_investigacion.estaInvestigada(Investigacion.TiposInvestigacion.TACTICAS_MILITARES, 1, this.jugador.getID()) == false) return -1;
        Celda c = Mapa.getCelda(pos);
        CentroInteligencia p = new CentroInteligencia(Productor.TiposProductor.CENTRO_INTELIGENCIA, c.getID(), this.jugador.getID(), nivel);
        this.centroInteligencia = p;
        return 0;
    }

    public int creaEmbajada(Posicion pos, int nivel)
    {
        if (_interfaz_investigacion.estaInvestigada(Investigacion.TiposInvestigacion.TACTICAS_MILITARES, 2, this.jugador.getID()) == false) return -1;
        Celda c = Mapa.getCelda(pos);
        Embajada p = new Embajada(Productor.TiposProductor.EMBAJADA, c.getID(), this.jugador.getID(), nivel);
        this.embajada = p;
        return 0;
    }

    public void muestraMapa()
    {

    }

    public void muestraEstadisticas()
    {

    }

    public bool compraInvestigacion(Investigacion.TiposInvestigacion tipo)
    {            
        if (this.centroInvestigacion.sePuedeComprarSiguienteNivel(tipo) == false) return false; //Aquí habría que avisar de porque no.            
        this.centroInvestigacion.compraSiguienteNivelInvestigacion(tipo);
        return true;
    }

    public void transfiereOroDesdeMinas(long idMina) 
    {
        long cantidadOroAcumulada = 0;

        if (idMina==-1) //transfiere desde todas
        {
            List<Productor> lista = this.listaMinasOro;
            foreach (Productor p in lista)
            {
                cantidadOroAcumulada += p.saca(Recurso.TiposRecurso.ORO, p.stock(Recurso.TiposRecurso.ORO));
            }
        }
        else
        {
            Productor p = getProductor(this.listaMinasOro, idMina);
            cantidadOroAcumulada = p.saca(Recurso.TiposRecurso.ORO, p.stock(Recurso.TiposRecurso.ORO));
        }

        this.palacio.mete(Recurso.TiposRecurso.ORO, cantidadOroAcumulada);
    }


    static private Recurso getRecurso(Satrapia.Recurso.TiposRecurso tipo, List<Recurso> lista)
    {
        int index = lista.FindIndex(
        delegate (Satrapia.Recurso miRecurso)
        {
            return (miRecurso.tipo == tipo);
        });

        if (index == -1) return null;
        return lista.ElementAt(index);
    }

    public List<Recurso> getRiqueza()
    {
        List<Satrapia.Recurso> totales = new List<Satrapia.Recurso>();
        Satrapia.Recurso elemTotales;

        if (this.listaProductores != null)
        {
            foreach (Satrapia.Productor prod in this.listaProductores)
            {
                List<Satrapia.Recurso> recursos = prod.getRecursos();
                if (recursos.Count > 0)
                {
                    foreach (Satrapia.Recurso rec in recursos)
                    {
                        elemTotales = getRecurso(rec.tipo, totales);
                        if (elemTotales == null)
                        {
                            Satrapia.Recurso r = new Satrapia.Recurso(rec.id());
                            totales.Add(r);
                        }
                        else
                        {
                            elemTotales.addCantidad(rec.cantidad);
                        }
                    }
                }
            }
        }

        return totales;
    }
}

class Emperador extends Dirigente
{
    public Emperador(Jugador id)
    {
    	super(id);
    }

    //Operaciones que pueden realizar los emperadores
    public void pideRecursosSatrapa(Recurso r, Satrapa s, long cantidad) { }
}

class Satrapa extends Dirigente
{
    public Satrapa(Jugador id)
    {
    	super(id);
    }

    //Operaciones que pueden realizar los sátrapas
    public void mueveEjercito(Ejercito ejercito, Posicion pos) { }
}
