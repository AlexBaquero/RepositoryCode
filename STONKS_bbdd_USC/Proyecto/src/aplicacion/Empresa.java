
package aplicacion;

//Clase de empresas

import static java.lang.Boolean.*;

public class Empresa {
    private String CIF;
    private Usuario usuario;
    private String nombreComercial;
    private String ciudad;
    private String calle;
    private Integer numero;
    private Integer telefono;
    private Integer participaciones;
    private Integer participacionesSinVender;

    public Empresa(String CIF, Usuario usuario, String nombreComercial, String ciudad, String calle, Integer numero, Integer telefono, Integer participaciones, Integer participacionesSinVender) {
        this.CIF = CIF;
        this.usuario = usuario;
        this.nombreComercial = nombreComercial;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
        this.telefono = telefono;
        this.participaciones = participaciones;
        this.participacionesSinVender = participacionesSinVender;
    }

    public String getCIF() {
        return CIF;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public Integer getParticipaciones() {
        return participaciones;
    }

    public Integer getParticipacionesSinVender() {
        return participacionesSinVender;
    }

    public void setCIF(String CIF) {
        this.CIF = CIF;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public void setParticipaciones(Integer participaciones) {
        this.participaciones = participaciones;
    }

    public void setParticipacionesSinVender(Integer participacionesSinVender) {
        this.participacionesSinVender = participacionesSinVender;
    }

    
    @Override
    public String toString() {
        return "Empresa{" + "CIF=" + CIF + ", usuario=" + usuario + ", nombreComercial=" + nombreComercial + ", ciudad=" + ciudad + ", calle=" + calle + ", numero=" + numero + ", telefono=" + telefono + ", participaciones=" + participaciones + ", participacionesSinVender=" + participacionesSinVender + '}';
    }
}

