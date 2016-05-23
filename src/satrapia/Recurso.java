package satrapia;

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
        //_interfaz_Recurso.load(this.ID, ref this._tipo, ref this._tipoReceptor, ref this.idReceptor, ref this._cantidad);
        //_interfaz_Recurso.load(this);
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
