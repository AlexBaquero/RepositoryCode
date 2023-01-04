
package aplicacion;

/**
 *
 * @author diego
 */
public class Criptomoneda {
    private String id_usuario;
    private float cantidad;
    private String nombre;
    private float precioActual;

    public Criptomoneda(String id_usuario, float cantidad, String nombre) {
        this.id_usuario = id_usuario;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    public Criptomoneda(String id_usuario, float cantidad, String nombre, float precioActual) {
        this.id_usuario = id_usuario;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.precioActual = precioActual;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public float getCantidad() {
        return cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecioActual() {
        return precioActual;
    }
   
}