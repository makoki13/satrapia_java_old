package satrapia;

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
        int index = investigaciones.FindIndex(
        delegate (Investigacion miInvestigacion)
        {
            return (miInvestigacion.getTipo() == tipo);
        });

        return investigaciones.ElementAt(index);
    }

    public bool sePuedeComprarSiguienteNivel(Investigacion.TiposInvestigacion tipo)
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

        List<Recurso> listaRecursos = v.costeDeNivelSiguiente();
        foreach(Recurso r in listaRecursos)
        {
            Recurso.TiposRecurso t = r.tipo;
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
                        if (r.cantidad > tenemos) return false; //Ya hemos terminado... No hay recurso... Habr�a que saber que recursos...
                        break;
                    }

                //En Palacio
                case Recurso.TiposRecurso.ORO:
                default:
                    {
                        Productor p = new Productor(this.jugador.getID(), celda, Productor.TiposProductor.CASTILLO);
                        long tenemos = p.stock(t);
                        if (r.cantidad > tenemos) return false; //Ya hemos terminado... No hay recurso... Habr�a que saber que recursos...
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

        List<Recurso> listaRecursos = v.costeDeNivelSiguiente();
        foreach (Recurso r in listaRecursos)
        {
            Recurso.TiposRecurso t = r.tipo;
            switch (t)
            {
                //En Banco
                case DINERO:
                    {
                        Productor p = new Productor(this.jugador.getID(), celda, Productor.TiposProductor.BANCO);
                        long dinero = p.stock(Recurso.TiposRecurso.DINERO);
                        if (r.cantidad > dinero) return -1; //Ya hemos terminado... No hay pasta.
                        p.saca(Recurso.TiposRecurso.DINERO, r.cantidad);
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
                        if (r.cantidad > tenemos) return -2; //Ya hemos terminado... No hay recurso... Habr�a que saber que recursos...
                        p.saca(t, r.cantidad);
                        break;
                    }

                //En Palacio
                case ORO:
                default:
                    {
                        Productor p = this.jugador.dirigente.palacio;
                        long tenemos = p.stock(t);
                        if (r.cantidad > tenemos) return -3; //Ya hemos terminado... No hay recurso... Habr�a que saber que recursos...
                        p.saca(t, r.cantidad);
                        break;
                    }
            }
        }

        //Ahora incluimos la investigaci�n en nuestra lista de investigaciones compradas.
        v.setInvestigada(this.jugador);

        return 0;
    }

    public int nivelActual (Investigacion inv)
    {
        return inv.nivelActual;
    }

    public int ultimoNivel(Investigacion inv)
    {
        return inv.maxNivel;
    }

    public int mejora (Productor prod, Potencial pot)
    {
        return 0;
    }

    public List<Investigacion> getLista() { return this.investigaciones; }
}
