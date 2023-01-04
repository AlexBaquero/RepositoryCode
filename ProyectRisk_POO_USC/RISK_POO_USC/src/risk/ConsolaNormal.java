
package risk;
import java.util.Scanner;

public class ConsolaNormal implements Consola{
    private String nombre;
    
    //Constructor de la clase
    public ConsolaNormal(){
       
        
    }
   
    //Implementamos los métodos de la interfaz consola
    public void imprimir(String mensaje){
        //imprimimos el string introducimos por pantalla
        System.out.print(mensaje);
    }
    
    //leemos el comando introducido
    public String leer(String descripcion){
        imprimir(descripcion);
        Scanner scanner= new Scanner(System.in);
        String lectura= scanner.nextLine();
        return lectura;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
}