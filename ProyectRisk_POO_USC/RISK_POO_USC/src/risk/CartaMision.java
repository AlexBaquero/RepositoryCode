package risk;

public class CartaMision {
    private String id;
    private String descripcion;
    private boolean asignada; 
    
    public CartaMision(String id,String descripcion){
        this.id=id;
        this.descripcion=descripcion;
        this.asignada = false;
    }
    
    //devuelve si esta asignada
    public boolean getAsiganada(){
        return this.asignada;
    }
    
    //devuelve la descripcion de la carta mision
    public String getDescripcion(){
        return this.descripcion;
    }
    
    //establece si una carta mision esta asignada o no
    public void setAsignada(boolean asiganada){
        this.asignada = asiganada;
    }
    
    @Override
    public String toString(){
        return descripcion;
    }
    
}