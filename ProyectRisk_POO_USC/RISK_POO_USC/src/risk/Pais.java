package risk;

import java.util.ArrayList;

public class Pais {

    private int numeroEjercitos;
    private String nombre;
    private Jugador jugador;
    private String color;
    private ArrayList<Pais> fronteras;
    private String continente;
    public String abreviaturaP;
    public String nombreColor;
    int getNumeroEjercitos;
    private int veces_ocupado;

    public Pais(String continente, String nombre, String color, String abreviaturaP, String nombreColor) {
        this.continente = continente;
        this.nombre = nombre;
        this.color = color;
        this.abreviaturaP = abreviaturaP;
        this.nombreColor=nombreColor;
    }

    //devuelve el continente del cual es el pais
    public String getContinente() {
        return continente;
    }

    //devuelve el nombre del continente
    public String getNombre() {
        return nombre;
    }

    //devuelve el color del continente
    public String getColor() {
        return color;
    }

    //devuelve la abreviatura del pais
    public String getAbreviaturaP() {
        return abreviaturaP;
    }

    //devuelve el nombre del color del continente
    public String getNombreColor() {
        return nombreColor;
    }

    //devuelve las fronteras del pais
    public ArrayList getFronteras() {
        return fronteras;
    }

    //devuelve el jugador del pais
    public Jugador getJugador() {
        return this.jugador;
    }

    //devuelve el numero de ejercitos asociado al pais
    public int getNumeroEjercitos() {
        return this.numeroEjercitos;
    }

    //crea las fronteras que hay entre un pais y otro
    public void setFronteras(ArrayList<Pais> fronteras) {
        this.fronteras = fronteras;
    }

    //establece el numero de ejercitos de cada pais
    public void setNumeroEjercitos(int ejercitos) {
        this.numeroEjercitos = this.numeroEjercitos + ejercitos;
    }

    //establece el jugador de cada pais
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    //establece las veces ocupado que estuvo el pais
    public void setVecesOcupado(int n) {
        this.veces_ocupado = this.veces_ocupado + n;
    }

    //devuelve el numer de veces ocupado
    public int getVecesOcupado() {
        return veces_ocupado;
    }

    //eliminamos un numero de ejerctios a total
    public void eliminar_n_ejercitos(int n) {
        this.numeroEjercitos -= n;
        this.jugador.eliminar_n_ejercitos(n);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Pais) {
            Pais pais = (Pais) object;
            return this.nombre == pais.nombre;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

}
