package risk;

import risk.carta.Carta;
import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private String nombre;
    private String color;
    private int ejercitosTotales;
    private int ejercitosRearme;
    private ArrayList<Pais> paises;
    private CartaMision misionActual;
    private ArrayList<Continente> continentes = new ArrayList<>();
    private ArrayList<Carta> cartasEquipamiento = new ArrayList<>();
    private boolean turno;
    private Ejercito colorEjercito;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.paises = new ArrayList<>();
    }

    //devolvemos el turno de un jugador
    public boolean getTurno() {
        return turno;
    }

    //establecemos el turno de un jugador
    public void setTurno(boolean turno) {
        this.turno = turno;
    }

    //establecemos la mision que tendra que realizar el jugador
    public void setMision(CartaMision misionActual) {
        this.misionActual = misionActual;
    }

    //devolvemos el color 
    public String getColorAsignado() {
        return this.color;
    }

    //establecemos el color de un jugador y a su vez el de su Ejercito
    public void setColor(String color) {
        if (color.equals("AMARILLO")
                || color.equals("AZUL")
                || color.equals("CYAN")
                || color.equals("ROJO")
                || color.equals("VERDE")
                || color.equals("VIOLETA")) {
            this.color = color;

            switch (this.color) {
                case "AMARILLO":
                    this.colorEjercito = new EjercitoAmarillo();
                    break;
                case "AZUL":
                    this.colorEjercito = new EjercitoAzul();
                    break;
                case "CYAN":
                    this.colorEjercito = new EjercitoCyan();
                    break;
                case "ROJO":
                    this.colorEjercito = new EjercitoRojo();
                    break;
                case "VERDE":
                    this.colorEjercito = new EjercitoVerde();
                    break;
                case "VIOLETA":
                    this.colorEjercito = new EjercitoVioleta();
                    break;
            }

        }

    }

    //devolvemos el color del ejercito
    public Ejercito getColorEjercito() {
        return this.colorEjercito;
    }

    //devolvemos el nombre del jugador
    public String getNombre() {
        return nombre;
    }

    //devolvemos el color del jugador
    public String getColor() {
        return color;
    }

    //devolvemos el codigo del color del jugador
    public String getCodigoColor() {
        if ("AMARILLO".equals(this.color)) {
            return "\033[33m";
        }
        if ("AZUL".equals(this.color)) {
            return "\033[34m";
        }
        if ("CYAN".equals(this.color)) {
            return "\033[36m";
        }
        if ("ROJO".equals(this.color)) {
            return "\033[31m";
        }
        if ("VERDE".equals(this.color)) {
            return "\033[32m";
        }
        if ("VIOLETA".equals(this.color)) {
            return "\033[35m";
        }
        return "";
    }

    //devolvemos la mision actual del jugador
    public CartaMision getMision() {
        return this.misionActual;
    }

    //devolvemos los paises que pertenencen al jugador
    public List<Pais> getPaises() {
        return this.paises;
    }

    //devolvemos los ejercitos totales
    public int getEjercitosTotales() {
        return ejercitosTotales;
    }

    //devolvemos un array de los continentes del jugador
    public ArrayList<Continente> getContinentes() {
        return continentes;
    }

    //creamos los continentes que pertenecerán al jugador
    public void setContinentes(Continente continente) {
        this.continentes.add(continente);
    }
    
    //establacemos las cartas de equipamiento de cada jugador
    public void setCartasEquipamiento(Carta cartaEquipamiento) {
        this.cartasEquipamiento.add(cartaEquipamiento);
    }

    //devolvemos las cartas de equipamiento del jugadoor
    public ArrayList<Carta> getCartasEquipamiento() {
        return cartasEquipamiento;
    }

    //añadimos un numero de ejercitos a los ejercitosTotales del jugador, que seran lso ejercitosRearme
    public void añadirEjercitosTotales(int ejercitos) {
        this.ejercitosTotales += ejercitos;
    }
    
    //Dice cuantso ejercitos tiene un jugador en un continente
    public int ejercitosEnContinente(Continente continente) {    
        ArrayList<Pais> Listapaises = continente.getPaises();
        int n = 0;

        for (Pais pais : Listapaises) {
            if (pais.getJugador() == this) {
                n += pais.getNumeroEjercitos();;
            }
        }

        return n;
    }

    //eliminamos el numero de ejercitos
    public void eliminar_n_ejercitos(int n) {
        this.ejercitosTotales -= n;
    }

    //elimina un pais si pierde
    public void perder_pais(Pais p) {
        this.paises.remove(p);
    }

    //añade un pais si gana
    public void ganar_pais(Pais p) {
        this.paises.add(p);
        p.setJugador(this);
    }

    //le damos un numero de ejerctios de un pais a otro
    public void ceder_ejercitos(int n, Pais p1, Pais p2) {
        if (p1.getNumeroEjercitos() < 4) {
            int i = 0;
            while (p1.getNumeroEjercitos() > 1 || i < n) {
                p1.setNumeroEjercitos(-1);
                p2.setNumeroEjercitos(1);
                i++;
            }
        } else {
            p1.setNumeroEjercitos(-n);
            p2.setNumeroEjercitos(n);
        }
    }

    //devuelve los ejertictosaRearmar
    public int getNumeroEjercitosRearmar() {
        return ejercitosRearme;
    }
    
    public void setEjercitosRearmar(int ejercitos){
        this.ejercitosRearme+=ejercitos;
    }
    
    public void eliminarCartasMision(Carta c){
        this.cartasEquipamiento.remove(c);
    }

    @Override
    public String toString() {
        return this.nombre;
    }

}
