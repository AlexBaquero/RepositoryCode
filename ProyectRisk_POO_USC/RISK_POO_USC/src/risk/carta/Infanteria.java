
package risk.carta;

import risk.Pais;
import risk.carta.Carta;

public abstract class Infanteria extends Carta {
    
    public Infanteria(String ID, Pais pais,Integer valorExtra){
        super(ID, pais, valorExtra);
    }
}
