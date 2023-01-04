package risk.carta;

import risk.Pais;

public class DeCaballo extends Caballeria {
    
    
    public DeCaballo(String ID, Pais pais) {
        super(ID, pais,3);
    }

    @Override
    public int obetenerRearme() {
        return 3;
    }
    
}
