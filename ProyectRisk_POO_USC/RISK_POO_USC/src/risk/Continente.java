package risk;

import java.util.ArrayList;

public class Continente {

    private int numeroEjercitos;
    private String nombre;
    private ArrayList<Pais> paises;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private String color;
    private String abreviaturaC;
    private int ejercitosRearmar;
    public static ConsolaNormal consola = new ConsolaNormal();

    //establecemos los atributos que definiran a un continente
    public Continente(ArrayList<Pais> paises, String nombre, String color, String abreviaturaC) {
        this.paises = paises;
        this.nombre = nombre;
        this.color = color;
        this.abreviaturaC = abreviaturaC;
    }

    //devolvemos el nombre del continente
    public String getNombre() {
        return nombre;
    }

    //establecemos el nombre del continente
    public void setNombre(String nombreContinente) {
        if (nombre.equals("África")
                || nombre.equals("América del Norte")
                || nombre.equals("America del Sur")
                || nombre.equals("Asia")
                || nombre.equals("Australia")
                || nombre.equals("Europa")) {
            this.nombre = nombreContinente;
        } else {
            String mensaje = "No existe el contienente";
            consola.imprimir(mensaje);  
        }
    }

    //devolvemos el arraylist de paises del continente
    public ArrayList getPaises() {
        return paises;
    }

    //devolvemos la abreviatura del continente
    public String getAbreviatura() {
        return abreviaturaC;
    }

    //Definimos un numero de ejercitos para un continente
    public void setNumeroEjecitos(int n) {
        this.numeroEjercitos = this.numeroEjercitos + n;
    }

    //devolvemos el numero de ejercitos
    public int getNumeroEjercitos() {
        return numeroEjercitos;
    }

    //devolvemos el numero de ejercitos a rearmar
    public int getEjercitosRearmar() {
        return ejercitosRearmar;
    }

    //establecemos el numero de ejerctios a rearmar
    public void setEjercitosRearmar(int n) {
        this.ejercitosRearmar = n;
    }

    //vemos si un jugador pertenece a unos paises de un continente
    public boolean pertenece_jugador(Jugador j) {
        if (j.getPaises().containsAll(this.paises)) {
            return true;
        } else {
            return false;
        }
    }

    //Guardamos en un arrayList los jugadores que hay en el continente
    public void jugadores() {
        for (Pais pais : paises) {
            Jugador j = pais.getJugador();
            if (!jugadores.contains(j)) {
                jugadores.add(j);
            }
        }
    }

    //Para saber los jugadores que hay en un continente y con cuantos ejercitos
 public String jugadoresPaises() {    
        this.jugadores();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("jugadores: [");
        for (Jugador j : jugadores) {
            stringBuilder.append("{").append(j.getNombre()).append(",");
            stringBuilder.append(j.ejercitosEnContinente(this));
            stringBuilder.append("}");
        }
        stringBuilder.append("],");
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
