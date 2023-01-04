
package risk;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class EscrituraFichero {
    private static EscrituraFichero escritura= null;
    private static PrintWriter ficheroSalida= null;
        
    private EscrituraFichero() { 
        BufferedWriter bufferEscritura= null;
        try {
            bufferEscritura= new BufferedWriter(new FileWriter("resultados.txt"));
            ficheroSalida= new PrintWriter(bufferEscritura);
        } catch(IOException exc) {
            System.out.println(exc.getMessage());
        }
    }
    
    private static void crearInstancia() {
        escritura= new EscrituraFichero();
    }

    public static EscrituraFichero getInstancia() {
        if(escritura==null) crearInstancia();
        return escritura;
    }
    
    public static void escribirResultado(String comando, String resultado, boolean fin) {
        ficheroSalida.println(comando);
        ficheroSalida.println(resultado + "\n");
    }
    
    public static void escribirFinComandos() {
        ficheroSalida.println("EOF");
        ficheroSalida.close();
    }
}