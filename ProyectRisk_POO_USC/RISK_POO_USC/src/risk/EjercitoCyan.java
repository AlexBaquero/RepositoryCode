package risk;

//herencia de ejercito compuesto
public class EjercitoCyan extends EjercitoCompuesto {

    @Override
    public int[] ataque(int[] dado) {

        //si se tiro un dado, se sumara dos unidades 
        if (dado.length < 2) {
            dado[0] = dado[0] + 2;
        }

        return dado;
    }
}
