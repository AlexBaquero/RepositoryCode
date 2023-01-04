package risk;

//hacemos herencia con ejercito base
public class EjercitoAmarillo extends EjercitoBase {
    @Override
    
    public int[] ataque(int[] dado) {

        //si se tiro un único dado, se sumara dos unidades al dado 
        if(dado.length<2){
            dado[0]=dado[0]+2;
        }
        
        return dado;
    }
}
