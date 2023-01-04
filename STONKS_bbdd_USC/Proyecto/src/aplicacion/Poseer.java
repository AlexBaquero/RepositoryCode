package aplicacion;

import java.util.Date;

public class Poseer {
    private Empresa CIF;
    private Usuario usuario;
    private Integer cantidad;
    private Date fecha;

    public Poseer(Empresa CIF, Usuario usuario, Integer cantidad, Date fecha) {
        this.CIF = CIF;
        this.usuario = usuario;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public Empresa getCIF() {
        return CIF;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Date getFecha() {
        return fecha;
    } 
}



