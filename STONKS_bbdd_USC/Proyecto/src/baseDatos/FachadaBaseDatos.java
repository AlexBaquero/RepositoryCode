package baseDatos;

import aplicacion.Accion;
import aplicacion.Anuncios;
import aplicacion.Criptomoneda;
import aplicacion.Empresa;
import aplicacion.HacerOfertaVenta;
import aplicacion.HacerOfertaVentaCripto;
import aplicacion.Inversor;
import aplicacion.Usuario;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class FachadaBaseDatos {

    private aplicacion.FachadaAplicacion fa;
    private java.sql.Connection conexion;
    private DAOUsuarios daoUsuarios;
    private DAOEmpresas daoEmpresas;
    private DAOInversores daoInversores;
    private DAOCompraVenta daoCompraVenta;
    private DAOCriptoMonedas daoCriptomoneda;
    private DAOAdministrador daoAdmin;

    public FachadaBaseDatos(aplicacion.FachadaAplicacion fa) {

        Properties configuracion = new Properties();
        this.fa = fa;
        FileInputStream arqConfiguracion;

        try {
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            this.conexion = java.sql.DriverManager.getConnection("jdbc:" + gestor + "://"
                    + configuracion.getProperty("servidor") + ":"
                    + configuracion.getProperty("puerto") + "/"
                    + configuracion.getProperty("baseDatos"),
                    usuario);

            daoEmpresas = new DAOEmpresas(conexion, fa);
            daoInversores = new DAOInversores(conexion, fa);
            daoUsuarios = new DAOUsuarios(conexion, fa);
            daoCompraVenta = new DAOCompraVenta(conexion, fa);

            daoAdmin = new DAOAdministrador(conexion, fa);

            daoCriptomoneda = new DAOCriptoMonedas(conexion, fa);


        } catch (FileNotFoundException f) {
            fa.muestraExcepcion(f.getMessage());
        } catch (IOException i) {
            fa.muestraExcepcion(i.getMessage());
        } catch (java.sql.SQLException e) {
            fa.muestraExcepcion(e.getMessage());
        }

    }

    //USUARIOS
    public void Registrar_usuario(Usuario u) {
        daoUsuarios.registrarUsuario(u);

    }

    public java.util.List<Usuario> consultarUsuariosFiltrados(String id_usuario, String tipo, int espera) {
        //return daoUsuarios.consultarUsuariosFiltrados(id_usuario, tipo, espera);
        return  daoAdmin.consultarUsuariosEnEspera(id_usuario, tipo, espera);
    }

    public java.util.List<Usuario> consultarUsuarios(String id_usuario) {
        return daoUsuarios.consultarUsuarios(id_usuario);
    }

    public Usuario comprobarAutentificacion(String id, String contrasena) {
        return daoUsuarios.validarUsuarioConHash(id, contrasena);
        
    }

    public Usuario consultarUnUsuario(String id_usuario) {
        return daoUsuarios.consultarUnUsuario(id_usuario);
    }

    public java.util.List<Empresa> consultarEmpresas(String id_usuario) {
        return daoEmpresas.consultarEmpresas(id_usuario);
    }

    public void guardarDatosEmpresa(String TextoCIF, String TextoCalle, String TextoID, String TextoNombre, Integer TextoNumero, Integer TextoParticipaciones, Integer TextoParticipacionesSinVende, String TextoPoblacion, Integer TextoTelefono) {
        daoEmpresas.guardarDatosEmpresa(TextoCIF, TextoCalle, TextoID, TextoNombre, TextoNumero,
                TextoParticipaciones, TextoParticipacionesSinVende, TextoPoblacion, TextoTelefono);
    }

    public int registrarUsuario(Usuario u) {
        //return daoUsuarios.registrarUsuario(u);
        return daoUsuarios.registrarUsuarioConHash(u);
    }

    public int registrarInversor(Inversor i) {
        return daoInversores.registrarInversor(i);
    }

    public void modificarDatosInversor(Inversor inversor) {
        daoInversores.modificarInversor(inversor);
    }

    public java.util.List<Inversor> consultarInversores(String id) {
        return daoInversores.consultarInversores(id);
    }

    public java.util.List<Accion> accionesUsuarios(String id_usuario) {
        return daoUsuarios.accionesUsuarios(id_usuario);
    }

    public boolean GenerarAcciones(Usuario user, int cantidad) {
        return daoEmpresas.GenerarAcciones(user, cantidad);
    }

    public Empresa consultarUnaEmpresa(Usuario user) {
        return daoEmpresas.consultarUnaEmpresa(user);
    }

    public Inversor consultarUnInversor(Usuario user) {
        return daoInversores.consultarUnInversor(user);
    }

    public int registrarEmpresa(Empresa empresa) {
        return daoEmpresas.registrarEmpresa(empresa);
    }

    public int UsuarioEnEspera(Usuario user) {
        return daoUsuarios.UsuarioEnEspera(user);
    }

    public void modificarUsuario(Usuario u, String id_usuarioantiguo) {
        daoUsuarios.modificarUsuario(u, id_usuarioantiguo);
    }
    

    public void borrarUsuario(String id) {
        daoUsuarios.borrarUsuario(id);
    }

    public void borrarInversor(String id) {
        daoInversores.borrarInversor(id);
    }

    public void eliminarEmpresa(String id) {
        daoEmpresas.eliminarEmpresa(id);
    }

    public java.util.List<Empresa> BuscarOfertaVenta(String e,String idusuario) {
        return daoCompraVenta.consultarOfertas(e,idusuario);
    }

    public ArrayList<Anuncios> obtenerAnuncios(String id, String info) {
        return daoEmpresas.obtenerAnuncios(id, info);
    }

    public java.util.List<Empresa> consultarEmpresasPorNombre(String nombre) {
        return daoEmpresas.consultarEmpresasPorNombre(nombre);
    }

    

    public java.util.List<Empresa> consultarOfertas(String nombreempresa,String idusuario) {
        return daoCompraVenta.consultarOfertas(nombreempresa,idusuario);
    }

    public Boolean Comprar(Usuario u, int cantidad, String idempresa) {
        return daoCompraVenta.Comprar(u, cantidad, idempresa);
    }

    public java.util.List<HacerOfertaVenta> buscarOferta(Date fecha, String cif, String idempresa, String idusuario) {
        return daoCompraVenta.buscarOferta(fecha, cif, idempresa, idusuario);
    }

    public java.util.List<HacerOfertaVenta> filtrarOfertasUsuario(Usuario u, String nombreempresa) {
        return daoCompraVenta.filtrarOfertasUsuario(u, nombreempresa);
    }

    public Boolean modificarOferta(HacerOfertaVenta oferta, int cantidad) {
        return daoCompraVenta.modificarOferta(oferta, cantidad);
    }

    public Boolean insertarOferta(HacerOfertaVenta oferta) {
        return daoCompraVenta.insertarOferta(oferta);
    }

    public Boolean borrarOferta(HacerOfertaVenta oferta) {
        return daoCompraVenta.borrarOferta(oferta);
    }

    public void crearAnuncio(String cif, Date fechaPago, String informacion) {
        daoEmpresas.crearAnuncio(cif, fechaPago, informacion);
    }

    public boolean crearOfertaVentaCriptomoneda(String usuario, String nombreCripto, float cantidad) {
        
        return daoCriptomoneda.crearOfertaVentaCriptomoneda(usuario, nombreCripto, cantidad);
    }

    public java.util.List<Criptomoneda> consultarCriptos(String id_usuario, String nombreCripto) {
        return daoCriptomoneda.consultarCriptos(id_usuario, nombreCripto);
    }

    public java.util.List<HacerOfertaVentaCripto> consultarOfertasCriptos(String tipo) {
        return daoCriptomoneda.consultarOfertasCriptos(tipo);
    }

    public Boolean ComprarCriptos(Usuario u, float cantidad, HacerOfertaVentaCripto oferta) {
        return daoCriptomoneda.ComprarCriptos(u, cantidad, oferta);
    }

    public boolean pagarParticipaciones(int precioAccion, Empresa empresa) {
        return daoEmpresas.pagarParticipaciones(precioAccion, empresa);
    }

    public void borrarAnuncio(int idanuncio) {
        daoEmpresas.borrarAnuncio(idanuncio);
    }
    
    public void retirarParticipaciones(String iduser, int cantidad, String cif) {
        daoCompraVenta.retirarParticipaciones(iduser, cantidad, cif);
    }
    

    public void hashContrasenasNoEncriptadas() {
        daoUsuarios.hashContrasenasNoEncriptadas();
    }

    public void darDeAlta(Usuario usuario){
        daoAdmin.darDeAlta(usuario);
    }
    
    public void modificarContrasena(Usuario usuario){
        daoUsuarios.modificarContrasena(usuario);
    }
    
    public Boolean usuarioPoseeAcciones(Usuario comprador) {
        return daoCompraVenta.usuarioPoseeAcciones(comprador);
    }
}
