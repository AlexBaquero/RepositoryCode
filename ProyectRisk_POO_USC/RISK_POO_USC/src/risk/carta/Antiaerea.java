package risk.carta;

import risk.Pais;

public class Antiaerea extends Artilleria {
    
    
    public Antiaerea(String ID, Pais pais) {
        super(ID, pais,3);
    }

    @Override
    public int obetenerRearme() {
        return 3;
    }
    
}
