package gui;

import aplicacion.Empresa;
import aplicacion.IVentana;
import aplicacion.Inversor;
import aplicacion.Usuario;

public class FachadaGui {

    aplicacion.FachadaAplicacion fa;
    VPrincipal vp;
    VMiEmpresa ve;
    VPerfil vmi;
    VSeleccionAcciones sa;
    VPrincipalAdministrador vad;
    VHistorialOfertaVenta vov;
    VHacerOfertaVenta vhov;
    VVentaAcciones vVentaAcciones;
    VGestionCriptomonedas vGestionCriptomonedas;
    VCrearAnuncio vCrearAnuncio;
    VModificarFondos vf;
    VVenderCriptoMonedas vcm;

    VCambiarContrasena vpwd;

     VCompraCriptos vcc;
    VPagoPorAccion vppa;
    VPagosDividendos vpd;
    VRetirarParticipaciones vrp;


    public FachadaGui(aplicacion.FachadaAplicacion fa) {
        this.fa = fa;
        //this.vp = new VPrincipal(vp,true,this.fa);
    }

    public void muestraExcepcion(String txtExcepcion) {
        VAviso va;

        va = new VAviso(vp, true, txtExcepcion);
        va.setLocationRelativeTo(null);
        va.setVisible(true);
    }

    public void iniciaVista() {
        VAutentificacion va;
        va = new VAutentificacion(vp, true, fa);
        //vp.setLocationRelativeTo(null);
        va.setLocationRelativeTo(null);
        //vp.setVisible(true);
        va.setVisible(true);
    }
    
    public void VRetirarParticipaciones(Usuario user, IVentana ventana, int cantidad, String cif) {
        vrp = new VRetirarParticipaciones(vp, true, fa, user, ventana, cantidad, cif);
        vrp.setLocationRelativeTo(null);
        vrp.setVisible(true);
    }

    public void visualizarRegistro() {
        VRegistro vr;
        vr = new VRegistro(vp, true, fa);
        vr.setLocationRelativeTo(null);
        vr.setVisible(true);
    }

    public void visualizarVentanaPrincipal(Usuario user) {
        vp = new VPrincipal(vp, true, fa, user);
        vp.setLocationRelativeTo(null);
        vp.setVisible(true);
    }

    public void visualizarPerfilEmpresa(Empresa empresa, int opcion) {
        ve = new VMiEmpresa(vp, true, fa, empresa, opcion);
        ve.setLocationRelativeTo(null);
        ve.setVisible(true);
    }

    public void visualizarMiPerfil(Inversor i) {
        vmi = new VPerfil(vp, true, fa, i);
        vmi.setLocationRelativeTo(null);
        vmi.setVisible(true);
    }

    public void visualizarRegistroEmpresa() {
        VRegistroEmpresa vre;
        vre = new VRegistroEmpresa(vp, true, fa);
        vre.setLocationRelativeTo(null);
        vre.setVisible(true);
    }

    public void visualizarSeleccionAcciones(Usuario user) {
        sa = new VSeleccionAcciones(vp, true, fa, user);
        sa.setLocationRelativeTo(null);
        sa.setVisible(true);
    }

    public void visualizarVentanaPrincipalAdmin() {
        vad = new VPrincipalAdministrador(vp, true, fa);
        vad.setLocationRelativeTo(null);
        vad.setVisible(true);
    }

    public void visualizarVOfertaVenta(Usuario usuario, String idempresa, String a) {
        vhov = new VHacerOfertaVenta(vp, true, fa, usuario, idempresa, a);
        vhov.setLocationRelativeTo(null);
        vhov.setVisible(true);
    }

    public void visualizarVHistorialOfertaVenta(Usuario usuario) {
        vov = new VHistorialOfertaVenta(vp, true, fa, usuario);
        vov.setLocationRelativeTo(null);
        vov.setVisible(true);
    }

    public void visualizarVentanaVentaAcciones(Usuario user, IVentana iventana) {
        vVentaAcciones = new VVentaAcciones(vp, true, fa, user, iventana);
        vVentaAcciones.setLocationRelativeTo(null);
        vVentaAcciones.setVisible(true);
    }

    public void visualizarGestionCriptomonedas(Usuario user, IVentana ventana) {
        vGestionCriptomonedas = new VGestionCriptomonedas(vp, true, fa, user, ventana);
        vGestionCriptomonedas.setLocationRelativeTo(null);
        vGestionCriptomonedas.setVisible(true);
    }

    public void visualizarCrearAnuncio(Empresa empresa) {
        vCrearAnuncio = new VCrearAnuncio(vp, true, fa, empresa);
        vCrearAnuncio.setLocationRelativeTo(null);
        vCrearAnuncio.setVisible(true);
    }

    public void visualizarVModificarFondos(Usuario user, IVentana iVentana) {
        vf = new VModificarFondos(vp, true, fa, user, iVentana);
        vf.setLocationRelativeTo(null);
        vf.setVisible(true);
    }

    public void visualizarVVenderCripto(Usuario usuario, String nombrecripto, float cantidad, IVentana ventana) {
        vcm = new VVenderCriptoMonedas(vp, true, fa, usuario, nombrecripto,cantidad, ventana);
        vcm.setLocationRelativeTo(null);
        vcm.setVisible(true);
    }
    
    public void visualizarCambiarContrasena(Usuario usuario){
        vpwd = new VCambiarContrasena(vp, true, fa, usuario);
        vpwd.setLocationRelativeTo(null);
        vpwd.setVisible(true);
    }
  
    public void visualizarCompraCriptos(Usuario user, IVentana iventana) {
        vcc = new VCompraCriptos(vp,true,fa,user, iventana);
        vcc.setLocationRelativeTo(null);
        vcc.setVisible(true);
    }

public void visualizarPagoParticipaciones(Empresa empresa, int opcion, int idanuncio){
        vppa = new VPagoPorAccion(vp, true, fa, empresa, opcion, idanuncio);
        vppa.setLocationRelativeTo(null);
        vppa.setVisible(true);
    }
    
    public void visualizarPagosDividendos(Empresa empresa){
        vpd = new VPagosDividendos(vp, true, fa, empresa);
        vpd.setLocationRelativeTo(null);
        vpd.setVisible(true);

    }

}
