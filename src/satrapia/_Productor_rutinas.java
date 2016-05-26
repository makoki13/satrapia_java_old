package satrapia;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class _Productor_rutinas {
	public static final int EXTRAE = 1;
    public static final int AUMENTA = 2;

    public static int EXTRAE_MINA_ORO(MinaOro m, long cantidad)
    {
        //get recurso de celda (mina natural)
        int idCelda = m.celda;
        Celda c = Mapa.getCelda(idCelda);
        Recurso r = c.getRecurso(Recurso.TiposRecurso.ORO);

        //Si existe cantidad posible entonces quita de la mina natural
        if (r.agotada() == true) return -1;
        long cantidadRecurso = r._get_Cantidad();
        if (cantidadRecurso < cantidad) cantidad = cantidadRecurso;

        //Pon esa cantidad en la mina de extraccion
        r.extrae(cantidad);
        m.mete(Recurso.TiposRecurso.ORO, cantidad);

        //Si me queda capacidad ponme como Evento
        if (r.agotada() == false)
        {
            double duracion = 600; //Hay que estudiar como hacemos la granularidad de los eventos
            LocalDateTime fin = LocalDateTime.now().plusMinutes((long) duracion);
            Instant instant = fin.atZone(ZoneId.systemDefault()).toInstant(); 
            new Evento(m.getJugador(), (long)duracion, Date.from(instant), m.getID(), EXTRAE, "");
        }

        return 0;
    }

    public static int EXTRAE_COMIDA(Granja m, long cantidad)
    {
        int idCelda = m.celda;
        Celda c = Mapa.getCelda(idCelda);
        Recurso r = c.getRecurso(Recurso.TiposRecurso.COMIDA);

        //Si se agota la comida poner más
        if (r.agotada() == true) r.addCantidad(cantidad * 2);
        long cantidadRecurso = r._get_Cantidad();
        if (cantidadRecurso < cantidad) cantidad = cantidadRecurso;

        //Pon esa cantidad en la mina de extraccion
        r.extrae(cantidad);
        m.mete(Recurso.TiposRecurso.COMIDA, cantidad);

        //Si me queda capacidad ponme como Evento
        if (r.agotada() == false)
        {
            double duracion = 600; //Hay que estudiar como hacemos la granularidad de los eventos            
            LocalDateTime fin = LocalDateTime.now().plusMinutes((long) duracion);
            Instant instant = fin.atZone(ZoneId.systemDefault()).toInstant();
            new Evento(m.getJugador(), (long)duracion, Date.from(instant), m.getID(), EXTRAE, "");            
        }

        return 0;
    }

    public static int EXTRAE_HIERRO(MinaHierro m, long cantidad)
    {
        int idCelda = m.celda;
        Celda c = Mapa.getCelda(idCelda);
        Recurso r = c.getRecurso(Recurso.TiposRecurso.HIERRO);

        if (r.agotada() == true) return -1;
        long cantidadRecurso = r._get_Cantidad();
        if (cantidadRecurso < cantidad) cantidad = cantidadRecurso;

        //Pon esa cantidad en la mina de extraccion
        r.extrae(cantidad);
        m.mete(Recurso.TiposRecurso.HIERRO, cantidad);

        //Si me queda capacidad ponme como Evento
        if (r.agotada() == false)
        {
            double duracion = 600; //Hay que estudiar como hacemos la granularidad de los eventos
            LocalDateTime fin = LocalDateTime.now().plusMinutes((long) duracion);
            Instant instant = fin.atZone(ZoneId.systemDefault()).toInstant();
            new Evento(m.getJugador(), (long)duracion, Date.from(instant), m.getID(), EXTRAE, "");            
        }

        return 0;
    }

    public static int EXTRAE_MADERA(Serreria m, long cantidad)
    {
        int idCelda = m.celda;
        Celda c = Mapa.getCelda(idCelda);
        Recurso r = c.getRecurso(Recurso.TiposRecurso.MADERA);

        //Si se agota la comida poner más
        if (r.agotada() == true) r.addCantidad(cantidad * 2); //Aqui tenemos que poner un control de regeneración de bosque.
        long cantidadRecurso = r._get_Cantidad();
        if (cantidadRecurso < cantidad) cantidad = cantidadRecurso;

        //Pon esa cantidad en la mina de extraccion
        r.extrae(cantidad);
        m.mete(Recurso.TiposRecurso.MADERA, cantidad);

        //Si me queda capacidad ponme como Evento
        if (r.agotada() == false)
        {
            double duracion = 600; //Hay que estudiar como hacemos la granularidad de los eventos
            LocalDateTime fin = LocalDateTime.now().plusMinutes((long) duracion);
            Instant instant = fin.atZone(ZoneId.systemDefault()).toInstant();
            new Evento(m.getJugador(), (long)duracion, Date.from(instant), m.getID(), EXTRAE, "");                
        }

        return 0;
    }

    public static int EXTRAE_PIEDRA(Cantera m, long cantidad)
    {
        //get recurso de celda (mina natural)
        int idCelda = m.celda;
        Celda c = Mapa.getCelda(idCelda);
        Recurso r = c.getRecurso(Recurso.TiposRecurso.PIEDRA);

        //Si se agota la comida poner más
        if (r.agotada() == true) return -1;
        long cantidadRecurso = r._get_Cantidad();
        if (cantidadRecurso < cantidad) cantidad = cantidadRecurso;

        //Pon esa cantidad en la mina de extraccion
        r.extrae(cantidad);
        m.mete(Recurso.TiposRecurso.PIEDRA, cantidad);
        
        //Si me queda capacidad ponme como Evento
        if (r.agotada() == false)
        {
            double duracion = 600; //Hay que estudiar como hacemos la granularidad de los eventos
            LocalDateTime fin = LocalDateTime.now().plusMinutes((long) duracion);
            Instant instant = fin.atZone(ZoneId.systemDefault()).toInstant();
            new Evento(m.getJugador(), (long)duracion, Date.from(instant), m.getID(), EXTRAE, "");                
        }

        return 0;
    }

    public static int AUMENTA_POBLACION(Palacio m, long cantidad)
    {
        //get recurso de celda (celda del palacio / ciudad)
        int idCelda = m.celda;
        Celda c = Mapa.getCelda(idCelda);
        Recurso r = c.getRecurso(Recurso.TiposRecurso.TRABAJADORES);

        //Si existe cantidad posible entonces quita de la mina natural
        if (r.agotada() == true) return -1;
        long cantidadRecurso = r._get_Cantidad();
        if (cantidadRecurso < cantidad) cantidad = cantidadRecurso;

        //Pon esa cantidad en la mina de extraccion
        r.extrae(cantidad);
        m.mete(Recurso.TiposRecurso.TRABAJADORES, cantidad);
        
        //Si me queda capacidad ponme como Evento
        if (r.agotada() == false)
        {
            double duracion = 600; //Hay que estudiar como hacemos la granularidad de los eventos
            LocalDateTime fin = LocalDateTime.now().plusMinutes((long) duracion);
            Instant instant = fin.atZone(ZoneId.systemDefault()).toInstant();
            new Evento(m.getJugador(), (long)duracion, Date.from(instant), m.getID(), AUMENTA, "");                
        }

        return 0;
    }

    public static int RECAUDA_IMPUESTOS(Palacio m, float cantidadPorHabitante)
    {
        long cantidadPoblacion = m.stock(Recurso.TiposRecurso.TRABAJADORES);
        float cantidadRecaudada = cantidadPoblacion * cantidadPorHabitante;
        m.mete(Recurso.TiposRecurso.DINERO, (long)cantidadRecaudada);

        double duracion = 600; //Hay que estudiar como hacemos la granularidad de los eventos
        LocalDateTime fin = LocalDateTime.now().plusMinutes((long) duracion);
        Instant instant = fin.atZone(ZoneId.systemDefault()).toInstant();
        new Evento(m.getJugador(), (long)duracion, Date.from(instant), m.getID(), EXTRAE, "");
        return 0;

    }

    public static int RECLUTA(Ejercito c, Recurso.TiposRecurso tipoUnidad, long cantidad)
    {
        //Si no existe el recurso tipoUnidad entonces se crea
        if (c.existeRecurso(tipoUnidad)==false) c.addRecurso(tipoUnidad); 
        c.mete(tipoUnidad, cantidad);                
        return 0;
    }
}
