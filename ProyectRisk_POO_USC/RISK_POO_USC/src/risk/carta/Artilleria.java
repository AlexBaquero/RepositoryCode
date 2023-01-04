package risk.carta;

import risk.Pais;


public abstract class Artilleria extends Carta{
    
    protected Artilleria(String ID, Pais pais,Integer valorExtra){
        super(ID, pais,valorExtra);    
    }
}
