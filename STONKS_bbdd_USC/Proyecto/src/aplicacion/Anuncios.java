package aplicacion;

import java.util.Date;

//Clase Anuncios
public class Anuncios {
    private Integer id_anuncio;
    private Empresa empresaPago;
    private Date fechaPublicacion;
    private Date fechaPago;
    private String informacion;

    public Anuncios(Integer id_anuncio, Empresa empresaPago, Date fechaPublicacion, Date fechaPago, String informacion) {
        this.id_anuncio = id_anuncio;
        this.empresaPago = empresaPago;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaPago = fechaPago;
        this.informacion = informacion;
    }

    public Integer getId_anuncio() {
        return id_anuncio;
    }

    public Empresa getEmpresaPago() {
        return empresaPago;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public String getInformacion() {
        return informacion;
    }
}
