package aplicacion;

import java.util.Date;

public class PagosDividendos {
    private Empresa empresa;
    private Anuncios anuncio;
    private float beneficio;

    public PagosDividendos(Empresa empresa, Anuncios anuncio, float beneficio) {
        this.empresa = empresa;
        this.anuncio = anuncio;
        this.beneficio = beneficio;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Anuncios getAnuncio() {
        return anuncio;
    }

    public float getBeneficio() {
        return beneficio;
    }
}
