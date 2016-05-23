package satrapia;

import java.sql.ResultSet;
import java.sql.SQLException;

import postgresql.Jdbc;

public class Recurso {
    public enum TiposRecurso 
    {
        COMIDA, HIERRO, ORO, MADERA, PIEDRA, DINERO, EJERCITO, TRABAJADORES, ARQUEROS, INFANTERIA, CABALLERIA, ASALTO, GENERAL;
    	
        @Override
        public String toString() {
          switch(this) {
            case COMIDA: return "COMIDA";
            case HIERRO: return "HIERRO";            
            case ORO: return "ORO";
            case MADERA: return "MADERA";
            case PIEDRA: return "PIEDRA";
            case DINERO: return "DINERO";
            case EJERCITO: return "EJERCITO";
            case TRABAJADORES: return "TRABAJADOREs";
            case ARQUEROS: return "ARQUEROS";
            case INFANTERIA: return "INFANTERIA";
            case CABALLERIA: return "CABALLERIA";
            case ASALTO: return "ASALTO";
            case GENERAL: return "GENERAL";
            default: throw new IllegalArgumentException();
          }
        }
    };
    public enum TiposReceptor { CELDA, PRODUCTOR, INVESTIGACION};
    
    private TiposRecurso tipo;
    public void _set_Tipo(TiposRecurso valor) {tipo=valor;}
    public TiposRecurso _get_Tipo() {return tipo;}
    
    private TiposReceptor tipoReceptor;
    public void _set_TipoReceptor(TiposReceptor valor) {tipoReceptor=valor;}
    public TiposReceptor _get_TipoReceptor() {return tipoReceptor;}
    
    private long ID;
    public void _set_ID(long valor) {ID=valor;}
    public long id() { return ID; }

    private long idReceptor;
    public void _set_Receptor(long valor) {idReceptor=valor;}
    public long _get_Receptor() {return idReceptor;}
    
    private long cantidad;
    public void _set_Cantidad(long valor) {cantidad=valor;}
    public long _get_Cantidad() {return cantidad;}
    
    public Recurso(TiposRecurso tipo, long cantidadInicial, long idReceptor, TiposReceptor tipoReceptor)
    {
        this.ID = -1;
        this.tipo = tipo;
        this.cantidad = cantidadInicial;
        this.idReceptor = idReceptor;
        this.tipoReceptor = tipoReceptor;            
    }

    public Recurso(long ID)
    {
        this.ID = ID;
        _interfaz_Recurso.load(this);
    }
    
    public String nombre() { return tipo.toString(); }

    public void addCantidad(long incremento)
    {
        this.cantidad = this.cantidad + incremento;
    }

    public long extrae(long cantidadRecurso)
    {
        long cantidadExtraida = 0;

        if (cantidadRecurso > cantidad)
        {
            cantidadExtraida = this.cantidad;
            this.cantidad = 0;
        }
        else
        {
            this.cantidad -= cantidadRecurso;
            cantidadExtraida = cantidadRecurso;
        }

        return cantidadExtraida;
    }

    public Boolean agotada() { return cantidad == 0; }
}

//clase asociada para Modelo **************
final class _interfaz_Recurso
{
    class __recurso
    {
        public long id;
        public String nombre;
        public int tipo;
        public long cantidad;
        public int tipoReceptor;
        public long receptor;
        public long idCelda;
        public long idProductor;
    }
    
    public static long guarda(Recurso r)
    {
        long nuevoID;
        nuevoID = Mapa.getSiguienteID("RECURSO");

        String nombreRecurso = r._get_Tipo().toString();
        int tipoRecurso = r._get_Tipo().ordinal();

        String idCelda = "NULL";  String idProductor = "NULL";
        if (r._get_TipoReceptor().ordinal() == 0) idCelda = Long.toString(r._get_Receptor()); else idProductor = Long.toString(r._get_Receptor());

        String sql = 
            "INSERT INTO Recursos (ID,Nombre,Tipo,Cantidad,ID_Celda,ID_Productor,Base) "+
            "VALUES (" + 
                nuevoID + ",'" + 
                nombreRecurso + "'," + 
                tipoRecurso + "," + 
                r._get_Cantidad() + "," + 
                idCelda + "," + 
                idProductor + "," + 
                "(select base from tipos_productor_recursos where id_tipo_recurso= " + tipoRecurso + " and id_tipo_productor=(select tipo from productor where id= " + idProductor + ")) " +
            ")";
        int filas = Jdbc.ejecuta(Mapa.conexion, sql);
        if (filas == -1) return -1; //Aqui mejor lanzar una excepcion

        return nuevoID;
    }

    public static long guarda(__recurso r)
    {
        long nuevoID;
        nuevoID = Mapa.getSiguienteID("RECURSO");
        
        Recurso.TiposRecurso tr = Recurso.TiposRecurso.values()[r.tipo];
        String nombreRecurso = tr.toString();
        int tipoRecurso = r.tipo;

        String idCelda = "NULL"; String idProductor = "NULL";
        if (r.tipoReceptor == 0) idCelda = Long.toString(r.receptor); else idProductor = Long.toString(r.receptor);

        String sql = 
            "INSERT INTO Recursos (ID,Nombre,Tipo,Cantidad,ID_Celda,ID_Productor,Base) " +
            "VALUES (" + 
                nuevoID + ",'" + 
                nombreRecurso + "'," + 
                tipoRecurso + "," + 
                r.cantidad + "," + 
                idCelda + "," + 
                idProductor + "," +
                "(select base from tipos_productor_recursos where id_tipo_recurso= " + tipoRecurso + " and id_tipo_productor=(select tipo from productor where id= " + idProductor + ")) " +
                ")";
        int filas = Jdbc.ejecuta(Mapa.conexion, sql);
        if (filas == -1) return -1; //Aqui mejor lanzar una excepcion

        return nuevoID;
    }

    public static void load(Recurso r)
    {
        String sql = "SELECT Tipo,Cantidad,COALESCE(ID_Celda,-1) AS IdCelda, COALESCE(ID_Productor,-1) as IdProductor FROM Recursos WHERE ID=" + r.id();
        ResultSet resultado = Jdbc.consulta(Mapa.conexion, sql);
		try {
			while(resultado.next()) {
				r._set_Tipo(Recurso.TiposRecurso.values()[resultado.getInt("Tipo")]);
				r._set_Cantidad(resultado.getLong("Tipo"));
				r._set_ID(resultado.getLong("IdCelda"));
				//r._set_Receptor(resultado.getLong("IdProductor")); Debería ser esto...
				r._set_Receptor(1); //PENDIENTE
			}
		} catch (SQLException e) {
		// 	TODO Auto-generated catch block
			e.printStackTrace();
		}		            
    }

    public static int setCantidad(long id, long cantidad)
    {
        String sql = "UPDATE Recursos SET Cantidad=" + cantidad + " WHERE ID=" + id;
        int filas = Jdbc.ejecuta(Mapa.conexion, sql);
        if (filas == -1) return -1; //Aqui mejor lanzar una excepcion
        return 0;
    }
}
