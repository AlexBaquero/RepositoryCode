package aplicacion;

import java.util.Date;

/**
 *
 * @author diego
 */
public class HacerOfertaVentaCripto {
    private String id_usuario;
    private String tipo;
    private Date fechaPublicacion;
    private float Cantidad;
    private float precio;

    public HacerOfertaVentaCripto(String id_usuario, String tipo, Date fechaPublicacion, float Cantidad, float precio) {
        this.id_usuario = id_usuario;
        this.tipo = tipo;
        this.fechaPublicacion = fechaPublicacion;
        this.Cantidad = Cantidad;
        this.precio = precio;
    }

    public HacerOfertaVentaCripto(String id_usuario, String tipo, Date fechaPublicacion, float Cantidad) {
        this.id_usuario = id_usuario;
        this.tipo = tipo;
        this.fechaPublicacion = fechaPublicacion;
        this.Cantidad = Cantidad;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public float getPrecio() {
        return precio;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public float getCantidad() {
        return Cantidad;
    }
    
}
