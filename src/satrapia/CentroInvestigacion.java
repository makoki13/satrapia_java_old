package satrapia;

import java.util.ArrayList;
import java.util.Iterator;

public class CentroInvestigacion {
	private Jugador jugador;

    private ArrayList<Investigacion> investigaciones;

    public CentroInvestigacion(Jugador jugador)
    {
        this.jugador = jugador;
        this.investigaciones = _interfaz_investigacion.getInvestigaciones(this.jugador.getID());       
    }

    public Investigacion getInvestigacion(Investigacion.TiposInvestigacion tipo)
    {
    	Investigacion r;
    	Iterator<Investigacion> recursosIterator = investigaciones.iterator();    	
		while (recursosIterator.hasNext()) {
			r=recursosIterator.next();
			if (r.getTipo()==tipo) return r;			
		}
		throw new NullPointerException();    	
    }

    public boolean sePuedeComprarSiguienteNivel(Investigacion.TiposInvestigacion tipo)
    {
        //Primero: Hay siguiente nivel;
        Investigacion v = this.getInvestigacion(tipo);
        int nivelActual = this.nivelActual(v);
        int siguienteNivel = this.ultimoNivel(v);
        if (nivelActual >= siguienteNivel) return false;

        //Segundo: Tenemos los recursos necesarios
        //Hay que determinar una manera de obtener los recursos que tenemos en Palacio -> Recursos naturales: Almacenes, Recursos artificiales y Oro: Palacio, Dinero: Banco.
        Poblacion capital = this.jugador.dirigente.capital;
        int celda = capital.idCelda();
        
        ArrayList<Recurso> listaRecursos = v.costeDeNivelSiguiente();
        for(Recurso r : listaRecursos)
        {
            Recurso.TiposRecurso t = r._get_Tipo();
            switch(t)
            {
                //En Banco
                case DINERO:                    
                    {
                        Productor p = new Productor(this.jugador.getID(), celda, Productor.TiposProductor.BANCO);
                        long dinero = p.stock(Recurso.TiposRecurso.DINERO);
                        if (r._get_Cantidad() > dinero) return false; //Ya hemos terminado... No hay pasta.
                        break;
                    }

                //En Almacen
                case COMIDA:
                case HIERRO:
                case MADERA:
                case PIEDRA:
                    {
                        Productor p = new Productor(this.jugador.getID(), celda, Productor.TiposProductor.ALMACEN);
                        long tenemos = p.stock(t);
                        if (r._get_Cantidad() > tenemos) return false; //Ya hemos terminado... No hay recurso... Habría que saber que recursos...
                        break;
                    }

                //En Palacio
                case ORO:
                default:
                    {
                        Productor p = new Productor(this.jugador.getID(), celda, Productor.TiposProductor.CASTILLO);
                        long tenemos = p.stock(t);
                        if (r._get_Cantidad() > tenemos) return false; //Ya hemos terminado... No hay recurso... Habría que saber que recursos...
                        break;
                    }
            }
        }

        return true;
    }

    public int compraSiguienteNivelInvestigacion(Investigacion.TiposInvestigacion tipo)
    {
        Poblacion capital = this.jugador.dirigente.capital;
        int celda = capital.idCelda();

        Investigacion v = this.getInvestigacion(tipo);

        ArrayList<Recurso> listaRecursos = v.costeDeNivelSiguiente();
        for (Recurso r : listaRecursos)
        {
            Recurso.TiposRecurso t = r._get_Tipo();
            switch (t)
            {
                //En Banco
                case DINERO:
                    {
                        Productor p = new Productor(this.jugador.getID(), celda, Productor.TiposProductor.BANCO);
                        long dinero = p.stock(Recurso.TiposRecurso.DINERO);
                        if (r._get_Cantidad() > dinero) return -1; //Ya hemos terminado... No hay pasta.
                        p.saca(Recurso.TiposRecurso.DINERO, r._get_Cantidad());
                        break;
                    }

                //En Almacen
                case COMIDA:
                case HIERRO:
                case MADERA:
                case PIEDRA:
                    {
                        Productor p = new Productor(this.jugador.getID(), celda, Productor.TiposProductor.ALMACEN);
                        long tenemos = p.stock(t);
                        if (r._get_Cantidad() > tenemos) return -2; //Ya hemos terminado... No hay recurso... Habría que saber que recursos...
                        p.saca(t, r._get_Cantidad());
                        break;
                    }

                //En Palacio
                case ORO:
                default:
                    {
                        Productor p = this.jugador.dirigente.palacio;
                        long tenemos = p.stock(t);
                        if (r._get_Cantidad() > tenemos) return -3; //Ya hemos terminado... No hay recurso... Habría que saber que recursos...
                        p.saca(t, r._get_Cantidad());
                        break;
                    }
            }
        }

        //Ahora incluimos la investigación en nuestra lista de investigaciones compradas.
        v.setInvestigada(this.jugador);

        return 0;
    }

    public int nivelActual (Investigacion inv)
    {
        return inv._get_NivelActual();
    }

    public int ultimoNivel(Investigacion inv)
    {
        return inv._get_MaxNivel();
    }

    public int mejora (Productor prod, Potencial pot)
    {
        return 0;
    }

    public ArrayList<Investigacion> getLista() { return this.investigaciones; }
}
