package risk;

import risk.carta.Infanteria;
import risk.carta.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import risk.exception.*;

public class Menu {

    // En esta clase se deberían de definir los atributos a los que será 
    // necesario acceder durante la ejecución del programa como, por ejemplo,
    // el mapa o los jugadores
    Mapa mapa;
    Jugador jugador;
    Pais pais;
    private LinkedHashMap<String, Jugador> JugadoresHash = new LinkedHashMap<String, Jugador>();
    private ArrayList<Jugador> JugadoresArray = new ArrayList<Jugador>();
    ListadoErrores ListadoErrores;
    ListadoCartasMision LisadoCartasMsion;
    public static ConsolaNormal consola = new ConsolaNormal();
    EscrituraFichero escritura = EscrituraFichero.getInstancia();

    boolean poderRearmar = true;
    boolean haberConquistado = false;
    boolean poderCarta = false;

    public Menu() throws ExcepcionComando, ExcepcionGeo, ExcepcionJugador, ExcepcionCarta, ExcepcionMision {
        // Iniciar juego leyendo el archivo
        String orden = null;
        BufferedReader bufferLector = null;
        try {
            File fichero = new File("comandos.csv");
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);
            while ((orden = bufferLector.readLine()) != null) {
                try {
                    System.out.println("$> " + orden);
                    String[] partes = orden.split(" ");
                    String comando = partes[0];
                    // COMANDOS INICIALES PARA EMPEZAR A JUGAR
                    //    crear mapa               
                    //    crear jugadores <nombre_fichero>
                    //    crear <nombre_jugador> <nombre_color>
                    //    asignar misiones
                    //    asignar paises <nombre_fichero>
                    //    asignar <nombre_pais> <nombre_jugador>

                    // COMANDOS DISPONIBLES DURANTE EL JUEGO
                    //    acabar
                    //    atacar <nombre_pais> <nombre_pais>
                    //    describir continente <nombre_continente>
                    //    describir frontera <nombre_pais>
                    //    describir frontera <nombre_continente>
                    //    describir jugador <nombre_jugador>
                    //    describir pais <nombre_pais>
                    //    jugador
                    //    repartir ejercitos
                    //    ver mapa
                    //    ver pais <nombre_pais>
                    switch (comando) {
                        case "crear":
                            if (partes.length == 2) {
                                if (partes[1].equals("mapa")) {
                                    // crearMapa es un método de la clase Menú desde el que se puede invocar
                                    // a otros métodos de las clases que contienen los atributos y los métodos
                                    // necesarios para realizar esa invocación 
                                    crearMapa();
                                } else {
                                    error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                                    throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());

                                }
                            }
                            if (partes.length == 3) {
                                if (partes[1].equals("jugadores")) {
                                    crearJugador(new File(partes[2]));
                                } else {
                                    crearJugador(partes[1], partes[2]);
                                }
                            }
                            break;
                        case "obtener":
                            if (partes.length == 3) {
                                if (partes[1].equals("frontera")) {
                                    mapa.obtenerFrontera(partes[2]);
                                } else if (partes[1].equals("continente")) {
                                    mapa.obtenerContinente(partes[2]);
                                } else if (partes[1].equals("color")) {
                                    mapa.obtenerColor(partes[2]);
                                } else if (partes[1].equals("paises")) {
                                    mapa.obtenerPaises(partes[2]);
                                }
                            } else {
                                error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                                throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                            }
                            break;
                        case "asignar":
                            if (partes.length < 3) {
                                error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                                throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                            }
                            if (partes.length == 3) {
                                if (partes[1].equals("paises")) {
                                    // asignarPaises es un método de la clase Menu que recibe como entrada el fichero
                                    // en el que se encuentra la asignación de países a jugadores. Dentro de este
                                    // método se invocará a otros métodos de las clases que contienen los atributos
                                    // y los métodos necesarios para realizar esa invocación
                                    //asignarPaises(new File(partes[2]));
                                    asignarPaises(partes[2]);
                                }
                                if (partes[1].equals("misiones")) {
                                    asignarMisiones(partes[2]);
                                }
                                if (partes[1].equals("carta")) {
                                    AsignarCarta(partes[2]);
                                }
                            }
                            if (partes.length == 4) {
                                if (partes[1].equals("pais")) {
                                    asignarPaises(partes[2], partes[3]);
                                } else if (partes[1].equals("mision")) {
                                    asignarMision(partes[2], partes[3]);
                                }
                            }
                            break;
                        case "repartir":
                            if (partes.length < 4) {
                                error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                                throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                            }
                            if (partes.length == 4) {
                                if (partes[1].equals("ejercitos")) {
                                    repartirEjercitos(Integer.valueOf(partes[2]), partes[3]);
                                }
                            }
                            break;
                        case "cambiar":
                            if (partes.length < 5) {
                                error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                                throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                            }
                            if (partes.length == 5) {
                                if (partes[1].equals("cartas")) {
                                    cambiarCartas(partes[2], partes[3], partes[4]);
                                }
                            }
                            break;
                        case "acabar":
                            if (partes.length > 2) {
                                error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                                throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                            }
                            if (partes.length == 2) {
                                AcabarTurno();
                            }
                            break;
                        case "jugador":
                            if (partes.length > 1) {
                                error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                                throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                            }
                            if (partes.length == 1) {
                                Jugador();
                            }
                            break;
                        case "describir":
                            if (partes.length < 3) {
                                error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                                throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                            }
                            if (partes.length == 3) {
                                if (partes[1].equals("jugador")) {
                                    describirJugador(partes[2]);
                                }

                                if (partes[1].equals("pais")) {
                                    describirPais(partes[2]);
                                }

                                if (partes[1].equals("continente")) {
                                    describirContinente(partes[2]);
                                }
                            }
                            break;
                        case "ver":
                            if (partes.length == 2) {
                                VerMapa();
                            } else {
                                error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                                throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                            }
                            break;

                        case "atacar":
                            if (partes.length < 3) {
                                error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                                throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                            }
                            if (partes.length == 3) {
                                atacar(partes[1], partes[2]);
                            }

                            if (partes.length == 5) {
                                atacarDados(partes[1], partes[2], partes[3], partes[4]);
                            }
                            break;
                        case "rearmar":
                            if (partes.length < 4) {
                                error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                                throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                            }
                            if (partes.length == 4) {
                                ReArmar(partes[1], Integer.valueOf(partes[2]), partes[3]);
                            }
                            break;
                        default:
                            error(comando,101,ListadoErrores.getError(101).getDescripcionError());
                            throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                    }
                } catch (ExcepcionRISK e) {
                    consola.imprimir(e.getMensajeError());
                }
            }
        } catch (Exception excepcion) {
            
        }

        //LECTURA DE COMANDOS POR TERMINAL
        while (true) {
            try {
                String comand = consola.leer("$>");
                String[] parts = comand.split(" ");

                switch (parts[0]) {
                    case "crear":
                        if (parts.length == 2) {
                            if (parts[1].equals("mapa")) {
                                // crearMapa es un método de la clase Menú desde el que se puede invocar
                                // a otros métodos de las clases que contienen los atributos y los métodos
                                // necesarios para realizar esa invocación 
                                crearMapa();
                            } else {
                                error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                                throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());

                            }
                        }
                        if (parts.length == 3) {
                            if (parts[1].equals("jugadores")) {
                                crearJugador(new File(parts[2]));
                            } else {
                                crearJugador(parts[1], parts[2]);
                            }
                        }
                        break;
                    case "obtener":
                        if (parts.length == 3) {
                            if (parts[1].equals("frontera")) {
                                mapa.obtenerFrontera(parts[2]);
                            } else if (parts[1].equals("continente")) {
                                mapa.obtenerContinente(parts[2]);
                            } else if (parts[1].equals("color")) {
                                mapa.obtenerColor(parts[2]);
                            } else if (parts[1].equals("paises")) {
                                mapa.obtenerPaises(parts[2]);
                            }
                        } else {
                            error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                            throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                        }
                        break;
                    case "asignar":
                        if (parts.length < 3) {
                            error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                            throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                        }
                        if (parts.length == 3) {
                            if (parts[1].equals("paises")) {
                                // asignarPaises es un método de la clase Menu que recibe como entrada el fichero
                                // en el que se encuentra la asignación de países a jugadores. Dentro de este
                                // método se invocará a otros métodos de las clases que contienen los atributos
                                // y los métodos necesarios para realizar esa invocación
                                //asignarPaises(new File(partes[2]));
                                asignarPaises(parts[2]);
                            }
                            if (parts[1].equals("misiones")) {
                                asignarMisiones(parts[2]);
                            }
                            if (parts[1].equals("carta")) {
                                AsignarCarta(parts[2]);
                            }
                        }
                        if (parts.length == 4) {
                            if (parts[1].equals("pais")) {
                                asignarPaises(parts[2], parts[3]);
                            } else if (parts[1].equals("mision")) {
                                asignarMision(parts[2], parts[3]);
                            }
                        }
                        break;
                    case "repartir":
                        if (parts.length < 4) {
                            error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                            throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                        }
                        if (parts.length == 4) {
                            if (parts[1].equals("ejercitos")) {
                                repartirEjercitos(Integer.valueOf(parts[2]), parts[3]);
                            }
                        }
                        break;
                    case "cambiar":
                        if (parts.length < 5) {
                            error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                            throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                        }
                        if (parts.length == 5) {
                            if (parts[1].equals("cartas")) {
                                cambiarCartas(parts[2], parts[3], parts[4]);
                            }
                        }
                        break;
                    case "acabar":
                        if (parts.length > 2) {
                            error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                            throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                        }
                        if (parts.length == 2) {
                            AcabarTurno();
                        }
                        break;
                    case "jugador":
                        if (parts.length > 1) {
                            error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                            throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                        }
                        if (parts.length == 1) {
                            Jugador();
                        }
                        break;
                    case "describir":
                        if (parts.length < 3) {
                            error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                            throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                        }
                        if (parts.length == 3) {
                            if (parts[1].equals("jugador")) {
                                describirJugador(parts[2]);
                            }

                            if (parts[1].equals("pais")) {
                                describirPais(parts[2]);
                            }

                            if (parts[1].equals("continente")) {
                                describirContinente(parts[2]);
                            }
                        }
                        break;
                    case "ver":
                        if (parts.length == 2) {
                            VerMapa();
                        } else {
                            error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                            throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                        }
                        break;

                    case "atacar":
                        if (parts.length < 3) {
                            error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                            throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                        }
                        if (parts.length == 3) {
                            atacar(parts[1], parts[2]);
                        }

                        if (parts.length == 5) {
                            atacarDados(parts[1], parts[2], parts[3], parts[4]);
                        }
                        break;
                    case "rearmar":
                        if (parts.length < 4) {
                            error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                            throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                        }
                        if (parts.length == 4) {
                            ReArmar(parts[1], Integer.valueOf(parts[2]), parts[3]);
                        }
                        break;
                    case "exit":case "EXIT":case "salir":case"SALIR":
                        EscrituraFichero.escribirFinComandos();
                        return;
                    default:
                        error(comand,101,ListadoErrores.getError(101).getDescripcionError());
                        throw new ExcepcionComando(101, ListadoErrores.getError(101).getDescripcionError());
                }

            } catch (ExcepcionRISK e) {
                consola.imprimir(e.getMensajeError());

            }
        }    
    }

    //Funcion para leer un error en el fichero
    public static void error(String comando, int codigo, String descripcion) {
        String mensaje;
        mensaje = "{\n"
                + "código de error: " + codigo + ",\n"
                + "descripción: \" " + descripcion + "\"\n"
                + "}";
        EscrituraFichero.escribirResultado(comando, mensaje, false);
    }

    //Funcion que llama a la creadora de la clase mapa 
    public void crearMapa() throws ExcepcionGeo {
        // Código necesario para crear el mapa
        if (mapa != null) {
            error("$> CREAR MAPA", 107, "El mapa ya ha sido creado");
            throw new ExcepcionGeo(107, ListadoErrores.getError(107).getDescripcionError());
        } else {
            this.mapa = new Mapa();
        }
    }

    //Funcion que añade un jugador al hashMap de jugadores
    private void AñadirJugadorHash(String StringJugador, Jugador JugadorNuevo) {
        JugadoresHash.put(StringJugador, JugadorNuevo);
    }

    //Funcion para crear a los jugadores del RISK leyendo de un archivo 
    private void crearJugador(File file) throws ExcepcionGeo, ExcepcionJugador {
        BufferedReader BufferLector2 = null;
        String Orden2 = null;
        if (mapa != null) { //si el tablero(mapa) existe...
            try {
                File fp = new File("jugadores.csv");
                FileReader Lector = new FileReader(fp);
                BufferLector2 = new BufferedReader(Lector);
                int count=0;
                while ((Orden2 = BufferLector2.readLine()) != null) {
                    String[] jugadorT = Orden2.trim().split(";");
                    this.jugador = new Jugador(jugadorT[0]);
                    jugador.setColor(jugadorT[1]);
                    if (JugadoresHash.get(jugador.getNombre()) != null) {
                        error("$> CREAR JUGADOR jugadores.csv", 114, "El color ya está asignado");
                        throw new ExcepcionJugador(114, ListadoErrores.getError(114).getDescripcionError());
                    }

                    if (jugador.getColor() == null) {
                        error("$> CREAR JUGADOR jugadores.csv", 100, "El país no pertenece al jugador");
                        throw new ExcepcionGeo(100, ListadoErrores.getError(100).getDescripcionError());
                    }

                    if (jugador.getColorAsignado() == null) {
                        error("$> CREAR JUGADOR jugadores.csv", 114, "El color ya está asignado");
                        throw new ExcepcionJugador(114, ListadoErrores.getError(114).getDescripcionError());
                    }

                    String comando = "$> CREAR JUGADORES jugadores.csv";

                    String mensaje = "{\n" + "color: " + jugador.getNombre().toString() + "\nnombre: "
                            + jugador.getColor().toString() + "\n}\n";
                    consola.imprimir(mensaje);
                    
                    if(count==0){
                        escritura.escribirResultado(comando, mensaje, false);
                        count++;
                    }
                    else{
                        escritura.escribirResultado("", mensaje, false);
                    }
                    AñadirJugadorHash(jugador.getNombre(), jugador);
                    if (JugadoresArray.isEmpty()) {
                        jugador.setTurno(true);
                    } else {
                        jugador.setTurno(false);
                    }
                    JugadoresArray.add(jugador);

                }
            } catch (Exception excepcion) {
                excepcion.printStackTrace();
            }
        } else {
            error("$> CREAR JUGADOR jugadores.csv", 106, "El mapa no está creado");
            throw new ExcepcionGeo(106, ListadoErrores.getError(106).getDescripcionError());
        }
    }

    //Funcion para crear a los jugadores del RISK apartir de su nombre y color
    private void crearJugador(String nombre, String color) throws ExcepcionJugador, ExcepcionGeo {
        if (mapa != null) {
            Jugador nombreJugador = JugadoresHash.get(nombre);
            for (Jugador jugador : JugadoresArray) {
                String colorJugador = jugador.getColor();
                if (colorJugador.equals(color)) {
                    error("$> CREAR JUGADOR" + nombre, 100, "Color no permitido");
                    throw new ExcepcionGeo(100, ListadoErrores.getError(100).getDescripcionError());
                }
            }
            if (nombreJugador == null) {

                this.jugador = new Jugador(nombre);
                if (jugador.getColorAsignado() != null) {
                    error("$> CREAR JUGADOR" + nombre, 114, "El color ya está asignado");
                    throw new ExcepcionJugador(114, ListadoErrores.getError(114).getDescripcionError());
                }
                jugador.setColor(color);

                String comando = "$> CREAR" + " " + nombre + " " + color;
                String mensaje = "{\n" + "color: " + jugador.getNombre().toString() + "\nnombre: "
                        + jugador.getColor().toString() + "\n}\n";
                consola.imprimir(mensaje);
                escritura.escribirResultado(comando, mensaje, false);
                AñadirJugadorHash(jugador.getNombre(), jugador);
                if (JugadoresArray.isEmpty()) {
                    jugador.setTurno(true);
                } else {
                    jugador.setTurno(false);
                }
                JugadoresArray.add(jugador);
            } else {
                error("$> CREAR JUGADOR" + nombre, 114, "El color ya está asignado");
                throw new ExcepcionJugador(104, ListadoErrores.getError(104).getDescripcionError());
            }
        } else {
            error("$> CREAR " + nombre + color, 106, "El mapa no está creado");
            throw new ExcepcionGeo(106, ListadoErrores.getError(106).getDescripcionError());
        }
    }

    //Funcion que nos permite saber que jugador tiene el turno
    public Jugador jugadorTurno() {
        for (Jugador j : JugadoresArray) {
            if (j.getTurno() == true) {
                return j;
            }
        }
        return null;
    }

    //Función que permite terminar el turno de un jugador y cederselo al otro
    public void AcabarTurno() {

        Jugador j = jugadorTurno();
        Jugador jnext;
        j.setTurno(false);
        int i = JugadoresArray.indexOf(j);
        if (JugadoresArray.get(i + 1) != null) {
            jnext = JugadoresArray.get(i + 1);
        } else {
            jnext = JugadoresArray.get(0);
        }

        jnext.setTurno(true);
        jnext.añadirEjercitosTotales((int) (jnext.getPaises().size()) / 3);
        jnext.setEjercitosRearmar((int) (jnext.getPaises().size()) / 3);
        poderRearmar = true;
        haberConquistado = false;
        poderCarta = false;

        String comando = "$> ACABAR TURNO";
        String mensaje = "{\n" + "nombre: " + jnext.getNombre() + "\nnumeroEjercitosRearmar: "
                + jnext.getNumeroEjercitosRearmar() + "\n}\n";
        consola.imprimir(mensaje);
        escritura.escribirResultado(comando, mensaje, false);
    }

    //Funcion que le asigna a un jugador una mision
    private void asignarMision(String nombreJugador, String identificadorMision) throws ExcepcionGeo, ExcepcionJugador, ExcepcionMision {

        if (JugadoresHash.isEmpty()) {
            error("$> ASIGNAR MISION" + " " + nombreJugador + " " + identificadorMision, 105, "Los jugadores no están creados");
            throw new ExcepcionJugador(105, ListadoErrores.getError(105).getDescripcionError());
        }

        Jugador j = JugadoresHash.get(nombreJugador);
        if (j == null) {
            error("$> ASIGNAR MISION" + " " + nombreJugador + " " + identificadorMision, 103, "El jugador no existe");
            throw new ExcepcionJugador(103, ListadoErrores.getError(103).getDescripcionError());
        }

        if (j.getMision() != null) {
            error("$> ASIGNAR MISION" + " " + nombreJugador + " " + identificadorMision, 117, "El jugador ya tiene asignada una misión");
            throw new ExcepcionMision(117, ListadoErrores.getError(117).getDescripcionError());
        }

        CartaMision m = ListadoCartasMision.getCartaMision(identificadorMision);
        if (m == null) {
            error("$> ASIGNAR MISION" + " " + nombreJugador + " " + identificadorMision, 116, "La misión no existe");
            throw new ExcepcionMision(116, ListadoErrores.getError(116).getDescripcionError());
        }

        if (m.getAsiganada()) {
            error("$> ASIGNAR MISION" + " " + nombreJugador + " " + identificadorMision, 115, "La misión ya está asignada");
            throw new ExcepcionMision(115, ListadoErrores.getError(115).getDescripcionError());
        }

        j.setMision(m);
        m.setAsignada(true);

        String comando = "$> ASIGNAR MISION" + " " + nombreJugador + " " + identificadorMision;
        String mensaje = "{\n" + "nombre: " + j.getNombre() + "\nmision: "
                + m.getDescripcion() + "\n}\n";
        consola.imprimir(mensaje);
        escritura.escribirResultado(comando, mensaje, false);
    }
    
    private void asignarMisionFichero(String nombreJugador, String identificadorMision) throws ExcepcionGeo, ExcepcionJugador, ExcepcionMision {

        if (JugadoresHash.isEmpty()) {
            error("$> ASIGNAR MISION" + " " + nombreJugador + " " + identificadorMision, 105, "Los jugadores no están creados");
            throw new ExcepcionJugador(105, ListadoErrores.getError(105).getDescripcionError());
        }

        Jugador j = JugadoresHash.get(nombreJugador);
        if (j == null) {
            error("$> ASIGNAR MISION" + " " + nombreJugador + " " + identificadorMision, 103, "El jugador no existe");
            throw new ExcepcionJugador(103, ListadoErrores.getError(103).getDescripcionError());
        }

        if (j.getMision() != null) {
            error("$> ASIGNAR MISION" + " " + nombreJugador + " " + identificadorMision, 117, "El jugador ya tiene asignada una misión");
            throw new ExcepcionMision(117, ListadoErrores.getError(117).getDescripcionError());
        }

        CartaMision m = ListadoCartasMision.getCartaMision(identificadorMision);
        if (m == null) {
            error("$> ASIGNAR MISION" + " " + nombreJugador + " " + identificadorMision, 116, "La misión no existe");
            throw new ExcepcionMision(116, ListadoErrores.getError(116).getDescripcionError());
        }

        if (m.getAsiganada()) {
            error("$> ASIGNAR MISION" + " " + nombreJugador + " " + identificadorMision, 115, "La misión ya está asignada");
            throw new ExcepcionMision(115, ListadoErrores.getError(115).getDescripcionError());
        }

        j.setMision(m);
        m.setAsignada(true);

        String mensaje = "{\n" + "nombre: " + j.getNombre() + "\nmision: "
                + m.getDescripcion() + "\n}\n";
        consola.imprimir(mensaje);
        escritura.escribirResultado("", mensaje, false);
    }

    //Funcion que le asigna a un jugador una mision leyendo de un fichero
    void asignarMisiones(String nombreFichero) throws ExcepcionGeo, ExcepcionJugador, ExcepcionMision {
        File archivo = new File(nombreFichero);
        FileReader lectorArchivo;
        try {
            lectorArchivo = new FileReader(archivo);
        } catch (FileNotFoundException e) {
            String comando = "$> ASIGNAR MISIONES" + "" + nombreFichero;
            String mensaje = "El archivo " + nombreFichero + "no se ha encontrado \n";
            consola.imprimir(mensaje);
            escritura.escribirResultado(comando, mensaje, false);
            return;
        }

        BufferedReader lector = new BufferedReader(lectorArchivo);

        String linea;
        try {
            String comando = "$> ASIGNAR MISIONES" + nombreFichero;
            escritura.escribirResultado(comando, "", false);
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");
                asignarMisionFichero(partes[0], partes[1]);
            }
        } catch (IOException e) {
            String comando = "$> ASIGNAR MISIONES" + nombreFichero;
            String mensaje = "Se ha producido un error al leer la linea\n";
            consola.imprimir(mensaje);
            escritura.escribirResultado(comando, mensaje, false);
        }
    }

    // Código necesario para asignar un país a un jugador
    public void asignarPaises(String nombreJugador, String abreviaturaPais) throws ExcepcionGeo, ExcepcionJugador, ExcepcionMision {

        jugador = JugadoresHash.get(nombreJugador);
        pais = this.mapa.getPaises().get(abreviaturaPais);

        if (jugador == null) {
            error("$> ASIGNAR PAIS " + nombreJugador + " " + abreviaturaPais, 103, "El jugador no existe");
            throw new ExcepcionJugador(103, ListadoErrores.getError(103).getDescripcionError());
        }

        if (jugador.getPaises() == null) {
            error("$> ASIGNAR PAIS " + nombreJugador + " " + abreviaturaPais, 113, "El país ya está asignado");
            throw new ExcepcionJugador(113, ListadoErrores.getError(113).getDescripcionError());
        }

        if (jugador.getMision() == null) {
            error("$> ASIGNAR PAIS " + nombreJugador + " " + abreviaturaPais, 118, "Las misiones no están asignadas");
            throw new ExcepcionMision(118, ListadoErrores.getError(118).getDescripcionError());
        }

        if (pais.getNombre() == null) {
            error("$> ASIGNAR PAIS " + nombreJugador + " " + abreviaturaPais, 109, "La misión ya está asignada");
            throw new ExcepcionGeo(109, ListadoErrores.getError(109).getDescripcionError());
        }

        String comando = "$> ASIGNAR PAIS " + nombreJugador + " " + abreviaturaPais;
        String mensaje = "{\n" + "nombre: " + jugador.getNombre() + "\npais: "
                + pais.getNombre() + "\ncontinente: " + pais.getContinente()
                + "\nfrontera: " + pais.getFronteras().toString() + "\n}\n";
        consola.imprimir(mensaje);
        escritura.escribirResultado(comando, mensaje, false);

        jugador.getPaises().add(pais);
        pais.setJugador(jugador);

    }
    
    // Código necesario para asignar un país a un jugador pero llamada desde la funcion asiganar paises <fichero>
    public void asignarPaisesFichero(String nombreJugador, String abreviaturaPais) throws ExcepcionGeo, ExcepcionJugador, ExcepcionMision {

        jugador = JugadoresHash.get(nombreJugador);
        pais = this.mapa.getPaises().get(abreviaturaPais);

        if (jugador == null) {
            error("$> ASIGNAR PAIS " + nombreJugador + " " + abreviaturaPais, 103, "El jugador no existe");
            throw new ExcepcionJugador(103, ListadoErrores.getError(103).getDescripcionError());
        }

        if (jugador.getPaises() == null) {
            error("$> ASIGNAR PAIS " + nombreJugador + " " + abreviaturaPais, 113, "El país ya está asignado");
            throw new ExcepcionJugador(113, ListadoErrores.getError(113).getDescripcionError());
        }

        if (jugador.getMision() == null) {
            error("$> ASIGNAR PAIS " + nombreJugador + " " + abreviaturaPais, 118, "Las misiones no están asignadas");
            throw new ExcepcionMision(118, ListadoErrores.getError(118).getDescripcionError());
        }

        if (pais.getNombre() == null) {
            error("$> ASIGNAR PAIS " + nombreJugador + " " + abreviaturaPais, 109, "La misión ya está asignada");
            throw new ExcepcionGeo(109, ListadoErrores.getError(109).getDescripcionError());
        }

        
        String mensaje = "{\n" + "nombre: " + jugador.getNombre() + "\npais: "
                + pais.getNombre() + "\ncontinente: " + pais.getContinente()
                + "\nfrontera: " + pais.getFronteras().toString() + "\n}\n";
        consola.imprimir(mensaje);
        escritura.escribirResultado("", mensaje, false);

        jugador.getPaises().add(pais);
        pais.setJugador(jugador);

    }

    // Código necesario para asignar paises a un jugador leyendo de fichero
    public void asignarPaises(String nombreFichero) throws ExcepcionGeo, ExcepcionJugador, ExcepcionMision {
        File archivo = new File(nombreFichero);
        FileReader lectorArchivo;
        try {
            lectorArchivo = new FileReader(archivo);
        } catch (FileNotFoundException e) {
            String comando = "$> ASIGNAR PAISES" + " " + nombreFichero;
            String mensaje = "El archivo " + nombreFichero + "no se ha encontrado \n";
            consola.imprimir(mensaje);
            escritura.escribirResultado(comando, mensaje, false);
            return;
        }

        BufferedReader lector = new BufferedReader(lectorArchivo);

        String linea;
        try {
            String comando = "$> ASIGNAR PAISES" + " " + nombreFichero;
            escritura.escribirResultado(comando, "", false);
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");
                asignarPaisesFichero(partes[0], partes[1]);
            }
        } catch (IOException e) {
            String comando = "$> ASIGNAR PAISES" + " " + nombreFichero;
            String mensaje = "Se ha producido un error al leer la linea\n";
            consola.imprimir(mensaje);
            escritura.escribirResultado(comando, mensaje, false);
        }
    }

    //Funcion que cambia tres cartas del jugador que tiene el turno por ejercitos
    public void cambiarCartas(String ID1, String ID2, String ID3) throws ExcepcionCarta {
        //Separo cada una des las cadanenas en dos partes (el tipo y el pais)
        Jugador j = jugadorTurno();
        int validez = 0;
        for (int i = 0; i < j.getCartasEquipamiento().size(); i++) {
            if (ID1.equals(j.getCartasEquipamiento().get(i).getID())) {
                validez++;
            }
        }
        for (int i = 0; i < j.getCartasEquipamiento().size(); i++) {
            if (ID2.equals(j.getCartasEquipamiento().get(i).getID())) {
                validez++;
            }
        }
        for (int i = 0; i < j.getCartasEquipamiento().size(); i++) {
            if (ID3.equals(j.getCartasEquipamiento().get(i).getID())) {
                validez++;
            }
        }
        if (validez != 3) {
            error("$> CAMBIAR CARTAS " + ID1 + " " + ID2 + " " + ID3, 122, "Algunas cartas no pertenecen al jugador");
            throw new ExcepcionCarta(122, ListadoErrores.getError(122).getDescripcionError());
        }

        String[] partsID1 = ID1.split("&");
        String tipoID1 = partsID1[0];
        String paisID1 = partsID1[1];
        String[] partsID2 = ID2.split("&");
        String tipoID2 = partsID2[0];
        String paisID2 = partsID2[1];
        String[] partsID3 = ID3.split("&");
        String tipoID3 = partsID3[0];
        String paisID3 = partsID3[1];

        Carta c1 = null;
        Carta c2 = null;
        Carta c3 = null;

        List<Carta> listaCartas = new ArrayList<>();
        listaCartas.add(c1);
        listaCartas.add(c2);
        listaCartas.add(c3);
        List<Pais> listaPaises = new ArrayList<>();
        listaPaises.add(mapa.getPaises().get(paisID1));
        listaPaises.add(mapa.getPaises().get(paisID2));
        listaPaises.add(mapa.getPaises().get(paisID3));
        List<String> listaID = new ArrayList<>();
        listaID.add(tipoID1);
        listaID.add(tipoID2);
        listaID.add(tipoID3);

        for (int i = 0; i < listaID.size(); i++) {  //Bucle donde para cada una de las cartas donde las instanciamos en funcion de su tipo
            switch (listaID.get(i)) {
                case "Granadero":
                    listaCartas.add(i, new Granadero(ID1, listaPaises.get(i)));
                    break;
                case "Fusilero":
                    listaCartas.add(i, new Fusilero(ID1, listaPaises.get(i)));
                    break;
                case "DeCaballo":
                    listaCartas.add(i, new DeCaballo(ID1, listaPaises.get(i)));
                    break;
                case "DeCamello":
                    listaCartas.add(i, new DeCamello(ID1, listaPaises.get(i)));
                    break;
                case "Antiaerea":
                    listaCartas.add(i, new Antiaerea(ID1, listaPaises.get(i)));
                    break;
                case "DeCampanha":
                    listaCartas.add(i, new DeCampanha(ID1, listaPaises.get(i)));
                    break;
                default:
                    error("$> CAMBIAR CARTAS " + ID1 + " " + ID2 + " " + ID3, 123, "Algunas cartas no existen");
                    throw new ExcepcionCarta(123, ListadoErrores.getError(123).getDescripcionError());
            }
        }

        Integer ejercitos = 0;//Variable donde se guarda el numero de ejercitos que debe sumarse
        for (int i = 0; i < listaID.size(); i++) {
            ejercitos += listaCartas.get(i).obetenerRearme();
        }

        if (listaCartas.get(0) instanceof Infanteria && listaCartas.get(1) instanceof Infanteria && listaCartas.get(2) instanceof Infanteria) {
            ejercitos += 6;
        } else if (listaCartas.get(0) instanceof Caballeria && listaCartas.get(1) instanceof Caballeria && listaCartas.get(2) instanceof Caballeria) {
            ejercitos += 8;
        } else if (listaCartas.get(0) instanceof Artilleria && listaCartas.get(1) instanceof Artilleria && listaCartas.get(2) instanceof Artilleria) {
            ejercitos += 10;
        } else if ((listaCartas.get(0) instanceof Artilleria && listaCartas.get(1) instanceof Caballeria && listaCartas.get(2) instanceof Infanteria) || (listaCartas.get(0) instanceof Caballeria && listaCartas.get(1) instanceof Artilleria && listaCartas.get(2) instanceof Infanteria) || (listaCartas.get(0) instanceof Infanteria && listaCartas.get(1) instanceof Caballeria && listaCartas.get(2) instanceof Artilleria) || (listaCartas.get(0) instanceof Caballeria && listaCartas.get(1) instanceof Infanteria && listaCartas.get(2) instanceof Artilleria) || (listaCartas.get(0) instanceof Infanteria && listaCartas.get(1) instanceof Artilleria && listaCartas.get(2) instanceof Caballeria) || (listaCartas.get(0) instanceof Artilleria && listaCartas.get(1) instanceof Infanteria && listaCartas.get(2) instanceof Caballeria)) {
            ejercitos += 12;
        } else {
            error("$> CAMBIAR CARTAS " + ID1 + " " + ID2 + " " + ID3, 121, "No hay configuración de cambio");
            throw new ExcepcionCarta(121, ListadoErrores.getError(121).getDescripcionError());
        }

        j.eliminarCartasMision(listaCartas.get(0));
        j.eliminarCartasMision(listaCartas.get(1));
        j.eliminarCartasMision(listaCartas.get(2));
        j.añadirEjercitosTotales(ejercitos);
        j.setEjercitosRearmar(ejercitos);

        String comando = "$> CAMBIAR CARTAS " + ID1 + " " + ID2 + " " + ID3;
        String mensaje = "cartasCambio: " + "[" + ID1 + "," + "," + ID2 + "," + ID3 + "]," + "\ncartasQuedan: " + j.getCartasEquipamiento().toString()
                + "," + "\nnumeroEjercitosCambiados: " + ejercitos.toString() + ","
                + "\nnumeroEjercitosRearme: " + j.getEjercitosTotales() + "," + "\n}\n";
        consola.imprimir(mensaje);
        escritura.escribirResultado(comando, mensaje, false);

    }

    //Funcion que reparte el numero de ejercitos introducido al país tambien introducido
    public void repartirEjercitos(int numero, String nombre_pais) throws ExcepcionGeo {
        Pais p = mapa.getPaises().get(nombre_pais);
        Continente c = mapa.getContinentes().get(p.getContinente());
        if (p == null) {
            error("$> REPARTIR EJERCITOS" + " " + numero + " " + nombre_pais, 109, "El país no existe");
            throw new ExcepcionGeo(109, ListadoErrores.getError(109).getDescripcionError());
        }
        p.setNumeroEjercitos(numero);
        c.setNumeroEjecitos(numero);
        p.getJugador().añadirEjercitosTotales(numero);
        StringBuilder mensaje = new StringBuilder();
        mensaje
                .append("{\npais:")
                .append(p.getNombre())
                .append("\njugador: ")
                .append(p.getJugador().getNombre())
                .append("\nnumeorEjercitosAsignados: ")
                .append(numero)
                .append("\nnumeroEjercitosTotales: ")
                .append(p.getJugador().getEjercitosTotales())
                .append("\npaisesOcupadosCont:");
        for (Pais var : p.getJugador().getPaises()) {
            mensaje.append("{").append(var.getNombre()).append(",").append(var.getNumeroEjercitos()).append("}");
        }
        mensaje.append("\n}\n");
        String comando = "$> REPARTIR EJERCITOS" + " " + numero + " " + nombre_pais;
        consola.imprimir(mensaje.toString());
        escritura.escribirResultado(comando, mensaje.toString(), false);
    }

    //Funcion que aporta datos de un determinado continente
    public void describirContinente(String abreviatura_continente) throws ExcepcionGeo {
        Continente c = mapa.getContinentes().get(abreviatura_continente);
        if (c == null) {
            error("$> DESCRIBIR CONTINENTE " + abreviatura_continente, 102, "El continente no existe");
            throw new ExcepcionGeo(102, ListadoErrores.getError(102).getDescripcionError());
        }

        String comando = "$> DESCRIBIR CONTINENTE " + abreviatura_continente;
        String mensaje = "{\n" + "nombre: " + c.getNombre() + "\nabreviatura: "
                + c.getAbreviatura() + "\n" + c.jugadoresPaises() + "\nnumeroEjercitos: "
                + c.getNumeroEjercitos() + "\nrearmeContinente: " + c.getEjercitosRearmar() + "\n}\n";
        consola.imprimir(mensaje);
        escritura.escribirResultado(comando, mensaje, false);

    }

    //Funcion que aporta datos de un determinado Pais
    public void describirPais(String abreviatura_pais) throws ExcepcionGeo {
        Pais p = mapa.getPaises().get(abreviatura_pais);
        if (p == null) {
            error("$> DESCRIBIR PAIS " + abreviatura_pais, 109, "El país no existe");
            throw new ExcepcionGeo(109, ListadoErrores.getError(109).getDescripcionError());
        }

        String comando = "$> DESCRIBIR PAIS " + abreviatura_pais;
        String mensaje = "{\n" + "nombre: " + p.getNombre() + "\nabreviatura:"
                + p.getAbreviaturaP() + "\ncontinente: " + p.getContinente()
                + "\nfrontera: " + p.getFronteras().toString() + "\njugador: " + p.getJugador().getNombre()
                + "\nnumeroEjercitos: " + p.getNumeroEjercitos() + "\nnumeroVecesOcupado: "
                + p.getVecesOcupado() + "\n}\n";
        consola.imprimir(mensaje);
        escritura.escribirResultado(comando, mensaje, false);
    }

    //Funcion que aporta datos de un determinado Jugador
    public void describirJugador(String nombre_jugador) throws ExcepcionJugador {
        Jugador j = JugadoresHash.get(nombre_jugador);

        if (j == null) {
            error("$> DESCRIBIR JUGADOR " + nombre_jugador, 103, "El jugador no existe");
            throw new ExcepcionJugador(103, ListadoErrores.getError(103).getDescripcionError());
        }

        String comando = "$> DESCRIBIR JUGADOR " + nombre_jugador;
        String mensaje = "{" + "\nnombre: " + j.getNombre() + ",\ncolor: " + j.getColor()
                + ",\nmision: " + j.getMision().getDescripcion() + ",\nnumeroEjercitos: "
                + j.getEjercitosTotales() + ",\npaises: " + j.getPaises().toString() + ","
                + "\ncontinentes: " + j.getContinentes().toString() + ",\ncartas: " + j.getCartasEquipamiento().toString()
                + ",\nnumeroEjercitosRearmar: [" + j.getNumeroEjercitosRearmar() + "]"
                + "\n}\n";
        consola.imprimir(mensaje);
        escritura.escribirResultado(comando, mensaje, false);
    }

    //Funcion que aporta datos del jugador que tiene el turno
    public void Jugador() throws ExcepcionJugador {
        String NombreJugador = jugadorTurno().getNombre();
        describirJugador(NombreJugador);
    }

    //Funcion que muestra el mapa por pantalla
    public void VerMapa() throws ExcepcionComando {
        if(JugadoresArray.isEmpty()){
            error("VER MAPA",99,ListadoErrores.getError(99).getDescripcionError());
            throw new ExcepcionComando(99, ListadoErrores.getError(99).getDescripcionError());
        }else{
            mapa.verMapa();
        }
    }

    //Funcion que permite al jugador que tiene el turno atacar con su pais a otro pais
    public void atacar(String abreviatura_pais1, String abreviatura_pais2) throws ExcepcionGeo, ExcepcionJugador, ExcepcionComando, ExcepcionCarta {
        if (poderRearmar) {
            Pais p1 = mapa.getPaises().get(abreviatura_pais1);
            Pais p2 = mapa.getPaises().get(abreviatura_pais2);
            if (p1 == null || p2 == null) {
                error("$> ATACAR" + abreviatura_pais1 + abreviatura_pais2, 109, "El país no existe");
                throw new ExcepcionGeo(109, ListadoErrores.getError(109).getDescripcionError());
            }

            if (!mapa.esFrontera(abreviatura_pais1, abreviatura_pais2)) {
                error("$> ATACAR" + abreviatura_pais1 + abreviatura_pais2, 112, "Los países no son frontera");
                throw new ExcepcionGeo(112, ListadoErrores.getError(112).getDescripcionError());
            }

            if (p1.getNumeroEjercitos() < 2) {
                error("$> ATACAR" + abreviatura_pais1 + abreviatura_pais2, 124, "No hay ejércitos suficientes");
                throw new ExcepcionJugador(124, ListadoErrores.getError(124).getDescripcionError());
            }

            Jugador j = p1.getJugador();
            Jugador j1 = jugadorTurno();
            if (!j.equals(j1)) {
                error("$> ATACAR" + abreviatura_pais1 + abreviatura_pais2, 110, "El país no pertenece al jugador");
                throw new ExcepcionJugador(110, ListadoErrores.getError(110).getDescripcionError());
            }

            Jugador j2 = p2.getJugador();

            Dados dados = new Dados();
            dados.lanzarDadosAtaque();
            dados.lanzarDadosDefensa();
            ArrayList<Integer> dadosAtaque = new ArrayList<>();
            ArrayList<Integer> dadosDefensa = new ArrayList<>();

            int dadosAtaqueint = 0, dadosDefensaint = 0;

            if (p1.getNumeroEjercitos() > 3) {
                dadosAtaque.add(dados.getDadoAtaque1());
                dadosAtaque.add(dados.getDadoAtaque2());
                dadosAtaque.add(dados.getDadoAtaque3());

                dadosAtaqueint = 3;
            } else if (p1.getNumeroEjercitos() == 3) {
                dadosAtaque.add(dados.getDadoAtaque1());
                dadosAtaque.add(dados.getDadoAtaque2());
                dadosAtaqueint = 2;
            } else if (p1.getNumeroEjercitos() == 2) {
                dadosAtaque.add(dados.getDadoAtaque1());
                dadosAtaqueint = 1;
            }

            if (p2.getNumeroEjercitos() >= 2) {
                dadosDefensa.add(dados.getDadoDefensa1());
                dadosDefensa.add(dados.getDadoDefensa2());
                dadosDefensaint = 2;
            } else if (p2.getNumeroEjercitos() == 1) {
                dadosDefensa.add(dados.getDadoDefensa1());
                dadosDefensaint = 1;
            }

            ArrayList<Integer> dadosAtaqueImprimir = new ArrayList<>();
            dadosAtaque.forEach((entero) -> {
                dadosAtaqueImprimir.add(entero);
            });
            ArrayList<Integer> dadosDefensaImprimir = new ArrayList<>();
            dadosDefensa.forEach((entero) -> {
                dadosDefensaImprimir.add(entero);
            });

            int[] dadosataqueprimitivo = new int[dadosAtaqueint];
            int[] dadosdefensaprimitivo = new int[dadosDefensaint];

            dadosAtaque.sort(Collections.reverseOrder());
            dadosDefensa.sort(Collections.reverseOrder());

            for (int i = 0; i < dadosAtaqueint; i++) {
                dadosataqueprimitivo[i] = Integer.parseInt("" + dadosAtaque.get(i));
            }

            for (int i = 0; i < dadosDefensaint; i++) {
                dadosdefensaprimitivo[i] = Integer.parseInt("" + dadosDefensa.get(i));
            }

            dadosataqueprimitivo = p1.getJugador().getColorEjercito().ataque(dadosataqueprimitivo);

            dadosAtaque.clear();
            dadosDefensa.clear();

            for (int i = 0; i < dadosAtaqueint; i++) {
                dadosAtaque.add(dadosataqueprimitivo[i]);
            }

            for (int i = 0; i < dadosDefensaint; i++) {
                dadosDefensa.add(dadosdefensaprimitivo[i]);
            }

            //GUARDAD CON UNA VARIABLE AUXILIAR LAS CANTIDAD DE EJERCITOS DE CADA JUGADOR ANTES DEL ATAQUE
            Integer ejercitos_previos_p1 = p1.getNumeroEjercitos();
            Integer ejercitos_previos_p2 = p2.getNumeroEjercitos();
            //GUARDO EL NUMERO DE EJERCITOS CON LOS QUE SE HA ATACADO
            Integer n_ejercitos_Ataque = dadosAtaque.size();
            //GUARDO EL NUMERO DE CONTINENTES QUE TIENE EL JUGADOR UNO ATNES DE ATACAR PARA LUEGO SABER SI HA CONQUISTADO UN NUEVO CONTINENTE
            int ncontinentes = j1.getContinentes().size();
            //DECLARO LA VARIABLE CONTINENTE
            Continente c = null;

            while (!dadosAtaque.isEmpty() && !dadosDefensa.isEmpty()) {
                Integer atq = Collections.max(dadosAtaque);
                Integer def = Collections.max(dadosDefensa);

                dadosAtaque.remove(atq);
                dadosDefensa.remove(def);

                if (atq > def) {//GANA EL ATACANTE 
                    p2.eliminar_n_ejercitos(1);
                } else {//GANA EL QUE DEFIENDE
                    p1.eliminar_n_ejercitos(1);
                }

                if (p2.getNumeroEjercitos() == 0) {
                    break;
                }
            }

            if (p2.getNumeroEjercitos() == 0) {
                c = mapa.continente_pais(p2);
                j1.ganar_pais(p2);
                j2.perder_pais(p2);

                if (c.pertenece_jugador(j1)) {
                    j1.getContinentes().add(c);
                }

                j1.ceder_ejercitos(n_ejercitos_Ataque, p1, p2);
                haberConquistado = true;
                //ASIGNAR POSIBILIDAD DE PEDIR CARTA DE EQUIPAMIENTO
                poderCarta = true;
            }

            String comando = "$> ATACAR" + abreviatura_pais1 + abreviatura_pais2;
            String mensaje = "{" + "\ndadosAtaque: " + dadosAtaqueImprimir.toString() + "," + "\ndadosDefensa:"
                    + dadosDefensaImprimir.toString() + "," + "\nejercitosPaisAtaque: [" + ejercitos_previos_p1.toString()
                    + "," + p1.getNumeroEjercitos() + "]" + "\nejercitosPaisDefensa: ["
                    + ejercitos_previos_p2.toString() + "," + p2.getNumeroEjercitos() + "]"
                    + "\npaisAtaquePerteneceA: \"" + p1.getJugador().toString() + "\"" + "\npaisDefensaPerteneceA: \" "
                    + p2.getJugador().toString() + "\"";
            consola.imprimir(mensaje);
            escritura.escribirResultado(comando, mensaje, false);
            if (j1.getContinentes().size() > ncontinentes) {
                String mensaje2 = "\ncontinenteConquistado: \"" + c.getNombre() + "\"" + "\n}\n";
                consola.imprimir(mensaje2);
                escritura.escribirResultado(comando, mensaje2, false);
            } else {
                String mensaje2 = "\ncontinenteConquistado: \"null\"" + "\n}\n";
                consola.imprimir(mensaje2);
                escritura.escribirResultado(comando, mensaje2, false);
            }
        } else {
            throw new ExcepcionComando(99, ListadoErrores.getError(99).getDescripcionError());
        }
    }

    //Funcion que permite al jugador que tiene el turno atacar con su pais a otro pais con un valor de dados determinado
    public void atacarDados(String abreviatura_pais1, String stDadosAtaque, String abreviatura_pais2, String stDadosDefensa) throws ExcepcionGeo, ExcepcionJugador, ExcepcionComando, ExcepcionCarta {
        if (poderRearmar) {
            Pais p1 = mapa.getPaises().get(abreviatura_pais1);
            Pais p2 = mapa.getPaises().get(abreviatura_pais2);
            if (p1 == null || p2 == null) {
                error("$> REARMAR " + abreviatura_pais1 + " " + stDadosAtaque + " " + abreviatura_pais2 + " " + stDadosDefensa, 109, "El país no existe");
                throw new ExcepcionGeo(109, ListadoErrores.getError(109).getDescripcionError());
            }

            if (!mapa.esFrontera(abreviatura_pais1, abreviatura_pais2)) {
                error("$> ATACAR " + abreviatura_pais1 + " " + stDadosAtaque + " " + abreviatura_pais2 + " " + stDadosDefensa, 112, "Los países no son frontera");
                throw new ExcepcionGeo(112, ListadoErrores.getError(112).getDescripcionError());
            }

            if (p1.getNumeroEjercitos() < 2) {
                error("$> ATACAR " + abreviatura_pais1 + " " + stDadosAtaque + " " + abreviatura_pais2 + " " + stDadosDefensa, 124, "No hay ejércitos suficientes");
                throw new ExcepcionJugador(124, ListadoErrores.getError(124).getDescripcionError());
            }

            Jugador j1 = p1.getJugador();
            Jugador j2 = p2.getJugador();

            ArrayList<Integer> dadosAtaque = new ArrayList<>();
            ArrayList<Integer> dadosDefensa = new ArrayList<>();

            for (int i = 0; i < stDadosAtaque.length(); i++) {
                char d = stDadosAtaque.charAt(i);
                if (d != 'x') {
                    Integer n = Character.getNumericValue(d);
                    dadosAtaque.add(n);
                }
            }
            for (int i = 0; i < stDadosDefensa.length(); i++) {
                char d = stDadosDefensa.charAt(i);
                if (d != 'x') {
                    Integer n = Character.getNumericValue(d);
                    dadosDefensa.add(n);
                }
            }
            ArrayList<Integer> dadosAtaqueImprimir = new ArrayList<>();
            dadosAtaque.forEach((entero) -> {
                dadosAtaqueImprimir.add(entero);
            });
            ArrayList<Integer> dadosDefensaImprimir = new ArrayList<>();
            dadosDefensa.forEach((entero) -> {
                dadosDefensaImprimir.add(entero);
            });
            //GUARDAD CON UNA VARIABLE AUXILIAR LAS CANTIDAD DE EJERCITOS DE CADA JUGADOR ANTES DEL ATAQUE
            Integer ejercitos_previos_p1 = p1.getNumeroEjercitos();
            Integer ejercitos_previos_p2 = p2.getNumeroEjercitos();
            //GUARDO EL NUMERO DE EJERCITOS CON LOS QUE SE HA ATACADO
            Integer n_ejercitos_Ataque = dadosAtaque.size();
            //GUARDO EL NUMERO DE CONTINENTES QUE TIENE EL JUGADOR UNO ATNES DE ATACAR PARA LUEGO SABER SI HA CONQUISTADO UN NUEVO CONTINENTE
            int ncontinentes = j1.getContinentes().size();
            //DECLARO LA VARIABLE CONTINENTE
            Continente c = null;

            int dadosAtaqueint = dadosAtaque.size();
            int dadosDefensaint = dadosDefensa.size();

            int[] dadosataqueprimitivo = new int[dadosAtaqueint];
            int[] dadosdefensaprimitivo = new int[dadosDefensaint];

            dadosAtaque.sort(Collections.reverseOrder());
            dadosDefensa.sort(Collections.reverseOrder());

            for (int i = 0; i < dadosAtaqueint; i++) {
                dadosataqueprimitivo[i] = Integer.parseInt("" + dadosAtaque.get(i));
            }

            for (int i = 0; i < dadosDefensaint; i++) {
                dadosdefensaprimitivo[i] = Integer.parseInt("" + dadosDefensa.get(i));
            }

            dadosataqueprimitivo = p1.getJugador().getColorEjercito().ataque(dadosataqueprimitivo);

            dadosAtaque.clear();
            dadosDefensa.clear();

            for (int i = 0; i < dadosAtaqueint; i++) {
                dadosAtaque.add(dadosataqueprimitivo[i]);
            }

            for (int i = 0; i < dadosDefensaint; i++) {
                dadosDefensa.add(dadosdefensaprimitivo[i]);
            }

            while (!dadosAtaque.isEmpty() && !dadosDefensa.isEmpty()) {
                Integer atq = Collections.max(dadosAtaque);
                Integer def = Collections.max(dadosDefensa);

                dadosAtaque.remove(atq);
                dadosDefensa.remove(def);

                if (atq > def) {//GANA EL ATACANTE 
                    p2.eliminar_n_ejercitos(1);
                } else {//GANA EL QUE DEFIENDE
                    p1.eliminar_n_ejercitos(1);
                }

                if (p2.getNumeroEjercitos() == 0) {
                    break;
                }
            }

            if (p2.getNumeroEjercitos() == 0) {
                c = mapa.continente_pais(p2);
                j1.ganar_pais(p2);
                j2.perder_pais(p2);

                if (c.pertenece_jugador(j1)) {
                    j1.getContinentes().add(c);
                }

                j1.ceder_ejercitos(n_ejercitos_Ataque, p1, p2);
                //Asignar CARTA DE EQUIPAMIENTO
                poderCarta = true;
            }

            String comando = "$> ATACAR " + abreviatura_pais1 + " " + stDadosAtaque + " " + abreviatura_pais2 + " " + stDadosDefensa;
            String mensaje = "{" + "\ndadosAtaque:" + dadosAtaqueImprimir.toString() + ","
                    + "\ndadosDefensa:" + dadosDefensaImprimir.toString() + "," + "\nejercitosPaisAtaque: ["
                    + ejercitos_previos_p1.toString() + ","
                    + p1.getNumeroEjercitos() + "]" + "\nejercitosPaisDefensa: [" + ejercitos_previos_p2.toString()
                    + "," + p2.getNumeroEjercitos() + "]" + "\npaisAtaquePerteneceA: \"" + p1.getJugador().toString()
                    + "\"" + "\npaisDefensaPerteneceA: " + " \""
                    + p2.getJugador().toString() + "\"" + "\n";
            consola.imprimir(mensaje);
            escritura.escribirResultado(comando, mensaje, false);

            if (j1.getContinentes().size() > ncontinentes) {
                String mensaje2 = "continenteConquistado: \"" + c.getNombre() + "\"" + "\n}\n";
                consola.imprimir(mensaje2);
                escritura.escribirResultado("", mensaje2, false);
            } else {
                String mensaje2 = "continenteConquistado: \"null\"" + "\n}\n";
                consola.imprimir(mensaje2);
                escritura.escribirResultado("", mensaje2, false);
            }
        } else {
            error("$> ATACAR " + abreviatura_pais1 + " " + stDadosAtaque + " " + abreviatura_pais2 + " " + stDadosDefensa, 99, "Comando no permitido en este momento");
            throw new ExcepcionComando(99, ListadoErrores.getError(99).getDescripcionError());
        }
    }

    public void ReArmar(String abreviaturaPais1, int numeroEjercitos, String abreviaturaPais2) throws ExcepcionGeo, ExcepcionJugador, ExcepcionComando {
        if (poderRearmar && haberConquistado) {
            String NombreJugador = JugadoresHash.entrySet().iterator().next().getKey();
            Jugador PrimerJugador = JugadoresHash.get(NombreJugador);
            Pais pais1 = mapa.getPaises().get(abreviaturaPais1);
            Pais pais2 = mapa.getPaises().get(abreviaturaPais2);

            if (pais1 == null || pais2 == null) {
                error("$> REARMAR " + abreviaturaPais1 + " " + numeroEjercitos + " " + abreviaturaPais2, 109, "El país no existe");
                throw new ExcepcionGeo(109, ListadoErrores.getError(109).getDescripcionError());
            }

            if (!mapa.esFrontera(abreviaturaPais1, abreviaturaPais2)) {
                error("$> REARMAR " + abreviaturaPais1 + " " + numeroEjercitos + " " + abreviaturaPais2, 112, "Los países no son frontera");
                throw new ExcepcionGeo(112, ListadoErrores.getError(112).getDescripcionError());
            }

            if (pais1.getNumeroEjercitos() == 0 || pais2.getNumeroEjercitos() == 0) {
                error("$> REARMAR " + abreviaturaPais1 + " " + numeroEjercitos + " " + abreviaturaPais2, 124, "No hay ejércitos suficientes");
                throw new ExcepcionJugador(124, ListadoErrores.getError(124).getDescripcionError());
            }

            if (pais1.getJugador().getNombre() == PrimerJugador.getNombre() || pais2.getJugador().getNombre() == PrimerJugador.getNombre()) {
                error("$> REARMAR " + abreviaturaPais1 + " " + numeroEjercitos + " " + abreviaturaPais2, 110, "El país no pertenece al jugador");
                throw new ExcepcionJugador(110, ListadoErrores.getError(110).getDescripcionError());
            }

            Integer EjercitosIniciales1 = pais1.getNumeroEjercitos();
            Integer EjercitosIniciales2 = pais2.getNumeroEjercitos();

            if (pais1.getNumeroEjercitos() < numeroEjercitos) {
                EjercitosIniciales1 = EjercitosIniciales1 - 1;
                pais2.setNumeroEjercitos(EjercitosIniciales1);
                int E = 1;
                pais1.setNumeroEjercitos(E);
            } else {
                pais2.setNumeroEjercitos(numeroEjercitos);
                pais1.eliminar_n_ejercitos(numeroEjercitos);
            }

            poderRearmar = false;

            String comando = "$> REARMAR " + abreviaturaPais1 + " " + numeroEjercitos + " " + abreviaturaPais2;
            String mensaje = "{" + "\nnumeroEjercitosInicialesOrigen: "
                    + EjercitosIniciales1.toString() + "\nnumeroEjercitosInicialesDestino: " + EjercitosIniciales2.toString() + "\nnumeroEjercitosFinalesOrigen: "
                    + pais1.getNumeroEjercitos() + "\nnumeroEjercitosFinalesDestino: "
                    + pais2.getNumeroEjercitos() + "\n}\n";
            consola.imprimir(mensaje);
            escritura.escribirResultado(comando, mensaje, false);

        } else {
            error("$> REARMAR " + abreviaturaPais1 + " " + numeroEjercitos + " " + abreviaturaPais2, 99, "Comando no permitido en este momento");
            throw new ExcepcionComando(99, ListadoErrores.getError(99).getDescripcionError());
        }
    }

    //Funcion que asigna una carta de un ID dado al jugador que tiene el turno
    void AsignarCarta(String IDCarta) throws ExcepcionCarta, ExcepcionComando {
        if (poderCarta) {
            Carta c;
            String[] partesID = IDCarta.split("&");
            String tipoID = partesID[0];
            String paisID = partesID[1];
            if (mapa.getPaises().get(paisID) == null) {
                error("$> ASIGNAR CARTA " + IDCarta, 125, "El identificador no sigue el formato correcto");
                throw new ExcepcionCarta(125, ListadoErrores.getError(125).getDescripcionError());
            }

            switch (tipoID) {
                case "Granadero":
                    c = new Granadero(IDCarta, mapa.getPaises().get(paisID));
                    break;
                case "Fusilero":
                    c = new Fusilero(IDCarta, mapa.getPaises().get(paisID));
                    break;
                case "DeCaballo":
                    c = new DeCaballo(IDCarta, mapa.getPaises().get(paisID));
                    break;
                case "DeCamello":
                    c = new DeCamello(IDCarta, mapa.getPaises().get(paisID));
                    break;
                case "Antiaerea":
                    c = new Antiaerea(IDCarta, mapa.getPaises().get(paisID));
                    break;
                case "DeCampanha":
                    c = new DeCampanha(IDCarta, mapa.getPaises().get(paisID));
                    break;
                default:
                    error("$> ASIGNAR CARTA " + IDCarta, 125, "El identificador no sigue el formato correcto");
                    throw new ExcepcionCarta(125, ListadoErrores.getError(125).getDescripcionError());
            }

            for (Jugador jugador : JugadoresArray) {
                if (jugador.getCartasEquipamiento().contains(c)) {
                    error("$> ASIGNAR CARTA " + IDCarta, 126, "Carta de equipamiento ya asignada");
                    throw new ExcepcionCarta(126, ListadoErrores.getError(126).getDescripcionError());
                }
            }

            Jugador j = jugadorTurno();
            j.setCartasEquipamiento(c);
            Pais p = null;
            Integer ejercitosDeRearme = 0;
            for (int i = 0; i < j.getPaises().size(); i++) {
                if (j.getPaises().get(i).getAbreviaturaP().equals(paisID)) {
                    p = j.getPaises().get(i);
                }
            }
            if (p != null) {
                ejercitosDeRearme = 1;
                p.setNumeroEjercitos(ejercitosDeRearme);
            }
            poderCarta = false;

            String comando = "$> ASIGNAR CARTA " + IDCarta;
            String mensaje = "{" + "\ntipoCarta: " + tipoID + "," + "\npaisAsociado: " + paisID + ","
                    + "\ncartasQuedan: " + j.getCartasEquipamiento().toString() + "," + "\nperteneceJugador: "
                    + j.getNombre() + "," + "\nejercitosDeRearme: " + ejercitosDeRearme.toString() + "\n}\n";
            consola.imprimir(mensaje);
            escritura.escribirResultado(comando, mensaje, false);
        } else {
            error("$> ASIGNAR CARTA " + IDCarta, 99, "Comando no permitido en este momento");
            throw new ExcepcionComando(99, ListadoErrores.getError(99).getDescripcionError());
        }
    }

}
