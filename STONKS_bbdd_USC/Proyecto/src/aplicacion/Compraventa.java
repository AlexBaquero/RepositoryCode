package aplicacion;

import java.util.Date;

//Clase CompraVenta
public class Compraventa {
    private Usuario comprador;
    private Usuario vendedor;
    private Empresa empresa;
    private Date fecha;
    private Integer numeroParticipaciones;
    private Float comision;

    public Compraventa(Usuario comprador, Usuario vendedor, Empresa empresa, Date fecha, Integer numeroParticipaciones, Float comis) {
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.empresa = empresa;
        this.fecha = fecha;
        this.numeroParticipaciones = numeroParticipaciones;
        this.comision= comis;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Date getFecha() {
        return fecha;
    }

    public Integer getNumeroParticipaciones() {
        return numeroParticipaciones;
    }
}
