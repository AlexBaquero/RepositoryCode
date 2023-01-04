package risk.carta;

import risk.Pais;

public class DeCampanha extends Artilleria{
    
    public DeCampanha(String ID, Pais pais) {
        super(ID, pais, 4);
    }

    @Override
    public int obetenerRearme() {
        return 4;
    }
    
}
