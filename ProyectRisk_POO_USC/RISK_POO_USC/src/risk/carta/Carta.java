package risk.carta;

import risk.Pais;

public abstract class Carta {
    
    private String ID;
    private Pais pais;
    protected final Integer valorExtra;

    protected Carta(String ID, Pais pais, Integer valorExtra) {
        this.ID = ID;
        this.pais = pais;
        this.valorExtra = valorExtra;
    }
    
    //Devuelve el ID de la carta 
    public String getID(){
        return ID;
    }
    
    //Devuelve el valor de rearme de una carta
    public abstract int obetenerRearme();
    
    @Override
    public String toString() {
        return this.ID;
    }
    
}
