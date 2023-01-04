package risk.carta;

import risk.Pais;

public class Granadero extends Infanteria {
    
    public Granadero(String ID, Pais pais) {  
        super(ID, pais, 1);
    }

    @Override
    public int obetenerRearme() {
        return 1;
    }
    
}
