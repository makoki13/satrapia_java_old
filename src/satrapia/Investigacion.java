package satrapia;

import java.util.ArrayList;

import satrapia.Recurso.TiposRecurso;

public class Investigacion {        
    public enum TiposInvestigacion {
        BANCA, EXTRACCION_ORO, AGRICULTURA, EXTRACCION_PIEDRA, EXTRACCION_METAL, EXPLOTACION_FORESTAL, URBANISMO, INSTRUCCION_MILITAR, TACTICAS_MILITARES, FILOSOFIA_Y_CIENCIA,
    };

    private TiposInvestigacion ID;

    public TiposInvestigacion getTipo() { return this.ID; }
            
    private ArrayList<Recurso> costeSiguienteNivel;

    private String nombre;
    public void _set_Nombre(String valor) {nombre=valor;}
    public String _get_Nombre() {return nombre;}
    
    private int maxNivel;
    public void _set_MaxNivel(int valor) {maxNivel=valor;}
    public int _get_MaxNivel() {return maxNivel;}
    
    private int nivelActual;
    public void _set_NivelActual(int valor) {nivelActual=valor;}
    public int _get_NivelActual() {return nivelActual;}
    
    private int tiempo; //En segundos
    public void _set_Tiempo(int valor) {tiempo=valor;}
    public int _get_Tiempo() {return tiempo;}
                   
    private boolean investigada;
    public void _set_Investigada(boolean valor) {investigada=valor;}
    public boolean _get_Investigada() {return investigada;}
    
    public Investigacion(TiposInvestigacion id, String nombre, int nivelActual, int maxNivel, ArrayList<Recurso> listaRecursos, int tiempo)
    {
        this.ID = id;            
        this.nombre = nombre;
        this.nivelActual = nivelActual;
        this.maxNivel = maxNivel; //Vendrá dado por el número de registros en la BD.
        this.costeSiguienteNivel = listaRecursos;
        this.tiempo = tiempo;
    }

    public ArrayList<Recurso> costeDeNivelSiguiente()
    {
        return this.costeSiguienteNivel;
    }

    public void setInvestigada(Jugador j)
    {
        this.nivelActual++;
        _interfaz_investigacion.guardaInvestigacionJugador(this.getTipo(), this.nivelActual, j.getID(), false);
    }

}
