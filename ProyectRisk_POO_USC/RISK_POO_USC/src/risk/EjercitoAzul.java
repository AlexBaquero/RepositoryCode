package risk;

//hacemos herencia con ejercito base
public class EjercitoAzul extends EjercitoBase {

    @Override
    public int[] ataque(int[] dado) {

        //si se tiro mas de un dado, se sumara una unidad al dado mayor
        if (dado.length > 2) {
            if (dado[0] > dado[1] && dado[0] > dado[2]) {
                dado[0] = dado[0] + 1;
            }

            if (dado[1] > dado[0] && dado[1] > dado[2]) {
                dado[1] = dado[1] + 1;
            }

            if (dado[2] > dado[1] && dado[2] > dado[0]) {
                dado[2] = dado[2] + 1;
            }

        }
        //se tiro solo un dado
        //no hace nada, no se suma
        return dado;
    }

}
