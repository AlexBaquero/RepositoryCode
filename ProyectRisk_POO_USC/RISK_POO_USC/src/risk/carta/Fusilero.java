package risk.carta;

import risk.Pais;

public class Fusilero extends Infanteria {
    
    public Fusilero(String ID, Pais pais) {
        super(ID, pais, 2);
    }

    @Override
    public int obetenerRearme() {
        return 2;
    }
    
}
