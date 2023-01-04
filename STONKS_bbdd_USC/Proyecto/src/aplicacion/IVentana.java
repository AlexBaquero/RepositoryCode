/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

/**
 *
 * @author Diego
 */
public interface IVentana {
    
    default void onSuccess() {
        
    }
    
    default void onFailure() {
        
    }
}
