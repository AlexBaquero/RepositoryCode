/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author anaca
 */
public class PWDConfig {
    
    String contrasenaEncriptada = "";
    
    public PWDConfig(String contrasena){
        this.contrasenaEncriptada = obtenerStringHash(hashPWD(contrasena), contrasena);
    }
    
    public PWDConfig(Integer contrasena){
        this.contrasenaEncriptada = obtenerStringHash(hashPWD(contrasena.toString()), contrasena.toString());
    }

    public String getContrasenaEncriptada() {
        return contrasenaEncriptada;
    }
    
    //Función hash a utilizar
    //Pwd se procesa por cada 2 bytes, cada segmento de 2 bytes como un long
    //se suman los valores en ascii de cada segmento
    //se convierte el resultado a tamano
    public int hashPWD(String pwd) {

        long suma = 0, producto = 1, valor = 0;

        for (int i = 0; i < pwd.length(); i++) {
            producto = (i % 2 == 0) ? 1 : producto * 256;
            suma += pwd.charAt(i) * producto;
        }

        
        return (int) (Math.abs(suma));

    }
    
    //Obtener String a partir de hash
    public String obtenerStringHash(int hash, String pwd) {
        String contrasenaEncriptada = "";
        Integer entero = new Integer(hash);
        Character primerCaracterString;
        
        
        if (hash % 10 >= 1 && (pwd.length() - hash%10) > 0) {
            primerCaracterString = new Character(pwd.charAt(hash % 10));
            contrasenaEncriptada = primerCaracterString.toString();
        }
        else
            contrasenaEncriptada = "a3f";

        contrasenaEncriptada = contrasenaEncriptada + entero.toString();
        return contrasenaEncriptada;
    }

    
}
