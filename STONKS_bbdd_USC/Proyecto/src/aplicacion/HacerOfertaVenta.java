package aplicacion;

import java.util.Date;

public class HacerOfertaVenta {
    private Date fechaOferta;
    private Float Precio;
    private boolean disponibilidad;
    private Empresa empresa;
    private Usuario usuario;
    private Integer numeroParticipaciones;
    
    public HacerOfertaVenta(Date fechaOferta, Float precio, boolean disponibilidad, Empresa empresa, Usuario usuario, Integer numeroParticipaciones) {
        this.fechaOferta = fechaOferta;
        Precio = precio;
        this.disponibilidad = disponibilidad;
        this.empresa = empresa;
        this.usuario = usuario;
        this.numeroParticipaciones = numeroParticipaciones;
    }

    public Date getFechaOferta() {
        return fechaOferta;
    }

    public Float getPrecio() {
        return Precio;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Integer getNumeroParticipaciones() {
        return numeroParticipaciones;
    }

    @Override
    public String toString() {
        return "HacerOfertaVenta{" + "fechaOferta=" + fechaOferta + ", Precio=" + Precio + ", disponibilidad=" + disponibilidad + ", empresa=" + empresa + ", usuario=" + usuario + ", numeroParticipaciones=" + numeroParticipaciones + '}';
    }
}
