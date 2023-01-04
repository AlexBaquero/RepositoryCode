package aplicacion;

import java.util.ArrayList;
import java.util.Date;

public class FachadaAplicacion {

    gui.FachadaGui fgui;
    baseDatos.FachadaBaseDatos fbd;

    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd = new baseDatos.FachadaBaseDatos(this);
        
    }

    public static void main(String args[]) {
        FachadaAplicacion fa;

        fa = new FachadaAplicacion();
        fa.iniciaInterfazUsuario();
        //fa.hashContrasenasNoEncriptadas();
        //fa.modificarContrasena(new Usuario("0", "0", 0, "regulador"));
    }

    public void iniciaInterfazUsuario() {
        fgui.iniciaVista();
    }

    public void muestraExcepcion(String e) {
        fgui.muestraExcepcion(e);
    }

    //USUARIOS
    public java.util.List<Usuario> consultarUsuariosFiltrados(String id_usuario, String tipo, int espera) {
        return fbd.consultarUsuariosFiltrados(id_usuario, tipo, espera);
    }

    public java.util.List<Usuario> consultarUsuarios(String id_usuario) {
        return fbd.consultarUsuarios(id_usuario);
    }

    public Usuario consultarUnUsuario(String id_usuario) {
        return fbd.consultarUnUsuario(id_usuario);
    }

    public Usuario comprobarAutentificacion(String id, String contrasena) {
        return fbd.comprobarAutentificacion(id, contrasena);
    }

    public void visualizarRegistro() {
        fgui.visualizarRegistro();
    }

    public void visualizarVentanaPrincipal(Usuario user) {
        fgui.visualizarVentanaPrincipal(user);
    }
    
    public void visualizarVRetirarParticipaciones(Usuario user, IVentana ventana, int cantidad, String cif) {
        fgui.VRetirarParticipaciones(user, ventana, cantidad, cif);
    }
    
    public void visualizarPerfilEmpresa(Empresa empresa, int opcion) {
        fgui.visualizarPerfilEmpresa(empresa, opcion);
    }

    public void visualizarMiPerfil(Inversor i) {
        fgui.visualizarMiPerfil(i);
    }

    public void visualizarSeleccionAcciones(Usuario user) {
        fgui.visualizarSeleccionAcciones(user);
    }

    public void visualizarComprarAcciones(Usuario user, IVentana iventana) {
        fgui.visualizarVentanaVentaAcciones(user, iventana);
    }

    public void visualizarRegistroEmpresa() {
        fgui.visualizarRegistroEmpresa();
    }

    public void visualizarVentanaCompraAcciones(Usuario user, IVentana iventana) {
        fgui.visualizarVentanaVentaAcciones(user, iventana);
    }

    public java.util.List<Empresa> consultarEmpresas(String id_usuario) {
        return fbd.consultarEmpresas(id_usuario);
    }

    public void guardarDatosEmpresa(String TextoCIF, String TextoCalle, String TextoID, String TextoNombre, Integer TextoNumero, Integer TextoParticipaciones, Integer TextoParticipacionesSinVende, String TextoPoblacion, Integer TextoTelefono) {
        fbd.guardarDatosEmpresa(TextoCIF, TextoCalle, TextoID, TextoNombre, TextoNumero,
                TextoParticipaciones, TextoParticipacionesSinVende, TextoPoblacion, TextoTelefono);
    }

    public int registrarUsuario(Usuario u) {
        return fbd.registrarUsuario(u);
    }

    public int registrarInversor(Inversor i) {
        return fbd.registrarInversor(i);
    }

    public void modificarDatosInversor(Inversor inversor) {
        fbd.modificarDatosInversor(inversor);
    }

    public java.util.List<Inversor> consultarInversores(String id) {
        return fbd.consultarInversores(id);
    }

    public java.util.List<Accion> accionesUsuarios(String id_usuario) {
        return fbd.accionesUsuarios(id_usuario);
    }

    public boolean GenerarAcciones(Usuario user, int cantidad) {
        return fbd.GenerarAcciones(user, cantidad);
    }

    public Empresa consultarUnaEmpresa(Usuario user) {
        return fbd.consultarUnaEmpresa(user);
    }

    public Inversor consultarUnInversor(Usuario user) {
        return fbd.consultarUnInversor(user);
    }

    public int registrarEmpresa(Empresa empresa) {
        return fbd.registrarEmpresa(empresa);
    }

    public void visualizarVentanaPrincipalAdmin() {
        fgui.visualizarVentanaPrincipalAdmin();
    }

    public int UsuarioEnEspera(Usuario user) {
        return fbd.UsuarioEnEspera(user);
    }

    public void modificarUsuario(Usuario u, String id_usuarioantiguo) {
        fbd.modificarUsuario(u, id_usuarioantiguo);
    }

    public void borrarUsuario(String id) {
        fbd.borrarUsuario(id);
    }

    public void borrarInversor(String id) {
        fbd.borrarInversor(id);
    }

    public void eliminarEmpresa(String id) {
        fbd.eliminarEmpresa(id);
    }

    public void visualizarVOfertaVenta(Usuario usuario, String idempresa, String a) {
        fgui.visualizarVOfertaVenta(usuario, idempresa, a);
    }

    public void visualizarHistorialOfertaVenta(Usuario usuario) {
        fgui.visualizarVHistorialOfertaVenta(usuario);
    }
/*
    public java.util.List<Empresa> BuscarOfertaVenta(String e) {
        return fbd.BuscarOfertaVenta(e);
    }*/

    public ArrayList<Anuncios> obtenerAnuncios(String id, String info) {
        return fbd.obtenerAnuncios(id, info);
    }

    public java.util.List<Empresa> consultarEmpresasPorNombre(String nombre) {
        return fbd.consultarEmpresasPorNombre(nombre);
    }

    

    public java.util.List<Empresa> consultarOfertas(String nombreempresa,String idusuario) {
        return fbd.consultarOfertas(nombreempresa,idusuario);
    }

    public Boolean Comprar(Usuario u, int cantidad, String idempresa) {
        return fbd.Comprar(u, cantidad, idempresa);
    }

    public java.util.List<HacerOfertaVenta> buscarOferta(Date fecha, String cif, String idempresa, String idusuario) {
        return fbd.buscarOferta(fecha, cif, idempresa, idusuario);
    }

    public void visualizarCrearAnuncio(Empresa empresa) {
        fgui.visualizarCrearAnuncio(empresa);
    }
    
    public void visualizarVModificarFondos(Usuario user, IVentana iVentana){
        fgui.visualizarVModificarFondos(user, iVentana);
    }
    
    public java.util.List<HacerOfertaVenta> filtrarOfertasUsuario(Usuario u, String nombreempresa) {
        return fbd.filtrarOfertasUsuario(u, nombreempresa);
    }

    public Boolean insertarOferta(HacerOfertaVenta oferta) {
        return fbd.insertarOferta(oferta);
    }
   
     public Boolean borrarOferta(HacerOfertaVenta oferta){
         return fbd.borrarOferta(oferta);
     }
     
     public void crearAnuncio(String cif, Date fechaPago, String informacion){
         fbd.crearAnuncio( cif, fechaPago,  informacion);
    }
     

    public Boolean modificarOferta(HacerOfertaVenta oferta, int cantidad) {
        return fbd.modificarOferta(oferta, cantidad);
    }

    public void visualizarVCriptoMonedas(Usuario usuario, IVentana ventana) {
        fgui.visualizarGestionCriptomonedas(usuario, ventana);
    }
    
     public void visualizarVVenderCripto(Usuario usuario, String nombrecripto, float cantidad, IVentana ventana){
        fgui.visualizarVVenderCripto(usuario,nombrecripto,cantidad, ventana);
    }
    
    public void visualizarCambiarContrasena(Usuario usuario){        
        fgui.visualizarCambiarContrasena(usuario);
    }
    
    public boolean crearOfertaVentaCriptomoneda(String usuario, String nombreCripto, float cantidad){
        return fbd.crearOfertaVentaCriptomoneda(usuario, nombreCripto, cantidad);

    }
    
    
    public java.util.List<Criptomoneda> consultarCriptos(String id_usuario, String nombreCripto){
        return fbd.consultarCriptos(id_usuario, nombreCripto);
    }
    
    public java.util.List<HacerOfertaVentaCripto> consultarOfertasCriptos(String tipo) {
        return fbd.consultarOfertasCriptos(tipo);
    }
    
    public void visualizarCompraCriptos(Usuario user,IVentana iventana) {
        fgui.visualizarCompraCriptos(user, iventana);
    }
    
    public Boolean ComprarCriptos (Usuario u, float cantidad, HacerOfertaVentaCripto oferta) {
        return fbd.ComprarCriptos(u, cantidad, oferta);
    }

    
    //actualizar contrasenas antiguas
    public void hashContrasenasNoEncriptadas(){
        fbd.hashContrasenasNoEncriptadas();
    }
    
    public void modificarContrasena(Usuario usuario){
        fbd.modificarContrasena(usuario);
    }
    
    
    
    
    //FUNCIONALIDADES ADICIONALES: EJEMPLO
    //Ejecucion lectura no comprometida: LECTURAS SUCIAS
    public void darDeAlta(Usuario usuario){
        fbd.darDeAlta(usuario);
    }
    
    public void ejemploLecturaNoComprometida(Usuario usuario){
        //Dar de alta con retraso
        darDeAlta(usuario);
        
    }

     public boolean pagarParticipaciones(int precioAccion, Empresa empresa){
        return fbd.pagarParticipaciones(precioAccion, empresa);
    } 
    
    public void visualizarPagoParticipaciones(Empresa empresa, int opcion, int idanuncio){
        fgui.visualizarPagoParticipaciones(empresa, opcion, idanuncio);
    }
    
    public void visualizarPagosDividendos(Empresa empresa){
        fgui.visualizarPagosDividendos(empresa);
    }
    
    public void borrarAnuncio(int idanuncio){
        fbd.borrarAnuncio(idanuncio);
    } 
    
    public void retirarParticipaciones(String iduser, int cantidad, String cif) {
        fbd.retirarParticipaciones(iduser, cantidad, cif);
    }
    
    public Boolean usuarioPoseeAcciones(Usuario comprador) {
        return fbd.usuarioPoseeAcciones(comprador);
    }
    
}
