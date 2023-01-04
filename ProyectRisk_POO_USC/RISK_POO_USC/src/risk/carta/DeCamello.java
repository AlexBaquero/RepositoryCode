
package risk.carta;

import risk.Pais;

public class DeCamello extends Caballeria {
    
    public DeCamello(String ID, Pais pais) {
        super(ID, pais,2);
    }

    @Override
    public int obetenerRearme() {
        return 2;
    }
    
}
