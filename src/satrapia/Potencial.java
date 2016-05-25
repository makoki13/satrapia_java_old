package satrapia;

public class Potencial {	
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
    
    public long getRecurso() {return recurso; }
}

