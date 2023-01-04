package baseDatos;

import aplicacion.Accion;
import aplicacion.Empresa;
import java.sql.*;
import aplicacion.Usuario;
import java.util.ArrayList;

/**
 *
 * @author alumnogreibd
 */
public class DAOUsuarios extends AbstractDAO {

    PWDConfig configuracion;
    
    public DAOUsuarios(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    //Registrar Usuario: inserta los datos del usuario a la base de datos
    public int registrarUsuario(Usuario u) {
        Connection con;
        PreparedStatement stmUsuario = null;

        con = super.getConexion();

        try {
            stmUsuario = con.prepareStatement("insert into usuarios(id_usuario, contrasena, fondos, tipo_usuario, enespera ) "
                    + "values (?,?,?,?,?)");
            stmUsuario.setString(1, u.getId_usuario());
            stmUsuario.setString(2, u.getContrasena());
            stmUsuario.setInt(3, u.getFondos());
            stmUsuario.setString(4, u.getTipoUsuario().toString());
            stmUsuario.setInt(5, 1);
            stmUsuario.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
            return 1;
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return 0;
    }

    //Consultar un usuario
    public Usuario consultarUnUsuario(String id) {
        Usuario usuarioActual = null;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();

        String consulta = "select * "
                + "from usuarios as u "
                + "where id_usuario = ?";

        try {
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, id);

            rsCatalogo = stmCatalogo.executeQuery();

            if (rsCatalogo.next()) {
                usuarioActual = new Usuario(rsCatalogo.getString("id_usuario"), rsCatalogo.getString("contrasena"), rsCatalogo.getInt("fondos"), rsCatalogo.getString("tipo_usuario"));
                usuarioActual.setEnEspera(rsCatalogo.getInt("enespera"));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
        } finally {
            try {
                stmCatalogo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return usuarioActual;
    }

    //Consultar Usuarios: comprueba si el id de la base de datos ya ha sido utilizado
    public java.util.List<Usuario> consultarUsuarios(String id_usuario) {
        java.util.List<Usuario> resultado = new java.util.ArrayList<Usuario>();
        Usuario usuarioActual;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();

        String consulta = "select id_usuario, contrasena, fondos, tipo_usuario, enespera "
                + "from usuarios as u "
                + "where id_usuario like ? ";

        try {
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, "%" + id_usuario + "%");
            rsCatalogo = stmCatalogo.executeQuery();
            while (rsCatalogo.next()) {
                usuarioActual = new Usuario(rsCatalogo.getString("id_usuario"), rsCatalogo.getString("contrasena"), rsCatalogo.getInt("fondos"), rsCatalogo.getString("tipo_usuario"));
                usuarioActual.setEnEspera(rsCatalogo.getInt("enespera"));
                resultado.add(usuarioActual);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
        } finally {
            try {
                stmCatalogo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    //Consultar Usuarios: comprueba si el id de la base de datos ya ha sido utilizado
    public java.util.List<Usuario> consultarUsuariosFiltrados(String id_usuario, String tipo, int espera) {
        java.util.List<Usuario> resultado = new java.util.ArrayList<Usuario>();
        Usuario usuarioActual;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();
        String consulta = null;

        if (tipo.equals("") && espera == -2) {
            consulta = "select id_usuario, contrasena, fondos, tipo_usuario, enespera "
                    + "from usuarios as u "
                    + "where id_usuario like ? ";
        } else if (!tipo.equals("") && espera == -2) {
            consulta = "select id_usuario, contrasena, fondos, tipo_usuario, enespera "
                    + "from usuarios as u "
                    + "where id_usuario like ? "
                    + " and tipo_usuario = ? ";
        } else if (tipo.equals("") && espera != -2) {
            consulta = "select id_usuario, contrasena, fondos, tipo_usuario, enespera "
                    + "from usuarios as u "
                    + "where id_usuario like ? "
                    + " and enespera = ? ";
        } else if (!tipo.equals("") && espera != -2) {
            consulta = "select id_usuario, contrasena, fondos, tipo_usuario, enespera "
                    + "from usuarios as u "
                    + "where id_usuario like ? "
                    + " and tipo_usuario = ? "
                    + " and enespera = ? ";

        }
        try {
            stmCatalogo = con.prepareStatement(consulta);

            if (tipo.equals("") && espera == -2) {
                stmCatalogo.setString(1, "%" + id_usuario + "%");
            } else if (!tipo.equals("") && espera == -2) {
                stmCatalogo.setString(1, "%" + id_usuario + "%");
                stmCatalogo.setString(2, tipo);
            } else if (tipo.equals("") && espera != -2) {
                stmCatalogo.setString(1, "%" + id_usuario + "%");
                stmCatalogo.setInt(2, espera);
            } else if (!tipo.equals("") && espera != -2) {
                stmCatalogo.setString(1, "%" + id_usuario + "%");
                stmCatalogo.setString(2, tipo);
                stmCatalogo.setInt(3, espera);
            }
            rsCatalogo = stmCatalogo.executeQuery();
            while (rsCatalogo.next()) {
                usuarioActual = new Usuario(rsCatalogo.getString("id_usuario"), rsCatalogo.getString("contrasena"), rsCatalogo.getInt("fondos"), rsCatalogo.getString("tipo_usuario"));
                usuarioActual.setEnEspera(rsCatalogo.getInt("enespera"));
                resultado.add(usuarioActual);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
        } finally {
            try {
                stmCatalogo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    //Validar Usuario: Comprueba que la id de usuario y la contraseña esté en la base de datos
    //Empleado para autentificar el usuario
    public Usuario validarUsuario(String idUsuario, String contrasena) {
        Usuario resultado = null;
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;

        con = this.getConexion();

        try {
            stmUsuario = con.prepareStatement("select id_usuario, contrasena, fondos, tipo_usuario, enespera "
                    + " from usuarios "
                    + " where id_usuario = ? and contrasena = ?");
            stmUsuario.setString(1, idUsuario);
            stmUsuario.setString(2, contrasena);
            rsUsuario = stmUsuario.executeQuery();
            if (rsUsuario.next()) {
                resultado = new Usuario(rsUsuario.getString("id_usuario"), rsUsuario.getString("contrasena"), rsUsuario.getInt("fondos"), rsUsuario.getString("tipo_usuario"));
                resultado.setEnEspera(rsUsuario.getInt("enespera"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }
    
    

    //Modificar Usuario: Se encarga de modificar los datos del usuario
    public void modificarUsuario(Usuario u, String id_usuarioantiguo) {
        Connection con;
        PreparedStatement stmUsuario = null;

        con = super.getConexion();
        //Se entiende que los fondos y el tipo de usuario no se pueden cambiar
        try {

            stmUsuario = con.prepareStatement("update usuarios "
                    + "set contrasena=? , "
                    + "    id_usuario=? , "
                    + "    fondos=? , "
                    + "    enespera=? "
                    + "where id_usuario=?");
            stmUsuario.setString(1, u.getContrasena());
            stmUsuario.setString(2, u.getId_usuario());
            stmUsuario.setInt(3, u.getFondos());
            stmUsuario.setInt(4, u.getEnEspera());
            stmUsuario.setString(5, id_usuarioantiguo);
            stmUsuario.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    //Borrar Usuario: Se encarga de borrar al usuario
    //CAMBIAR CASCADE O MODIFICAR MÉTODO
    public void borrarUsuario(String id) {
        Connection con;
        PreparedStatement stmUsuario = null;

        con = super.getConexion();

        try {

            stmUsuario = con.prepareStatement("delete from usuarios where id_usuario = ?");

            stmUsuario.setString(1, id);
            stmUsuario.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    //Devuelve un listado de cantidad de acciones por empresa
    public java.util.List<Accion> accionesUsuarios(String id_usuario) {
        java.util.List<Accion> resultado = new java.util.ArrayList<Accion>();
        Accion AccionActual;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();

        String consulta = "select idempresa, cantidad, fecha "
                + "from poseer "
                + "where idusuario = ? ";

        try {
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, id_usuario);

            rsCatalogo = stmCatalogo.executeQuery();
            while (rsCatalogo.next()) {
                AccionActual = new Accion(rsCatalogo.getString("idempresa"), rsCatalogo.getInt("cantidad"), rsCatalogo.getTimestamp("fecha"));
                resultado.add(AccionActual);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
        } finally {
            try {
                stmCatalogo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public java.util.List<Accion> GenerarAccionesUsuarios(Usuario usuario, Accion accion, String nAccion) {
        java.util.List<Accion> resultado = new java.util.ArrayList<Accion>();

        Accion AccionActual;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();

        String consulta = "insert into poseer(idempresa, cantidad, fecha) "
                + "values (?,?,?)" + " where idusuario = ?";

        try {
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, accion.getIdEmpresa());
            stmCatalogo.setInt(2, accion.getCantidad());
            //stmCatologo.setDate(3, accion.getFecha());
            stmCatalogo.setString(4, usuario.getId_usuario());
            rsCatalogo = stmCatalogo.executeQuery();
            while (rsCatalogo.next()) {
                AccionActual = new Accion(rsCatalogo.getString("idempresa"), rsCatalogo.getInt("cantidad"), rsCatalogo.getTimestamp("fecha"));
                //resultado.add(AccionActual);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
        } finally {
            try {
                stmCatalogo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public java.util.List<Accion> ComprarAccionesUsuarios(Usuario usuario, Accion accion, String nAccion) {
        java.util.List<Accion> resultado = new java.util.ArrayList<Accion>();

        Accion AccionActual;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();
        return resultado;
    }

    public int UsuarioEnEspera(Usuario user) {

        int estado = 1;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();

        String consulta = "select enespera from usuarios where id_usuario = ?";

        try {
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, user.getId_usuario());

            rsCatalogo = stmCatalogo.executeQuery();

            if (rsCatalogo.next()) {
                estado = rsCatalogo.getInt("enespera");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
        } finally {
            try {
                stmCatalogo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return estado;
    }

    
    //Autentificación
    public Usuario validarUsuarioConHash(String idUsuario, String contrasena) {
        Usuario resultado = null;
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;

        con = this.getConexion();

        
        try {
            stmUsuario = con.prepareStatement("select id_usuario, contrasena, fondos, tipo_usuario, enespera "
                    + " from usuarios "
                    + " where id_usuario = ? and contrasena = ?");
            stmUsuario.setString(1, idUsuario);
            
            //Encriptacion
            System.out.println("\nCONTRASENA INICIAR SESION = " + contrasena);
            this.configuracion = new PWDConfig(contrasena);            
            contrasena = this.configuracion.getContrasenaEncriptada();
            System.out.println("\nCONTRASENA COMPARAR = " + contrasena);
            
            System.out.println("\nselect id_usuario, contrasena, fondos, tipo_usuario, enespera" +
"                    +  from usuarios  where id_usuario = " + idUsuario + "and contrasena = " + contrasena);
            stmUsuario.setString(2, contrasena);
            rsUsuario = stmUsuario.executeQuery();
            
            if (rsUsuario.next()) {
                resultado = new Usuario(rsUsuario.getString("id_usuario"), rsUsuario.getString("contrasena"), rsUsuario.getInt("fondos"), rsUsuario.getString("tipo_usuario"));
                resultado.setEnEspera(rsUsuario.getInt("enespera"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    //Registrar Usuario: inserta los datos del usuario a la base de datos
    public int registrarUsuarioConHash(Usuario u) {
        Connection con;
        PreparedStatement stmUsuario = null;

        con = super.getConexion();

        try {
            stmUsuario = con.prepareStatement("insert into usuarios(id_usuario, contrasena, fondos, tipo_usuario, enespera ) "
                    + "values (?,?,?,?,?)");
            stmUsuario.setString(1, u.getId_usuario());
            
            //Encriptacion    
            System.out.println("\nCONTRASENA INICIAL REGISTRO " + u.getContrasena());
            this.configuracion = new PWDConfig(u.getContrasena());            
            u.setContrasena(this.configuracion.getContrasenaEncriptada());
            System.out.println("\nCONTRASENA FINAL HASH " + u.getContrasena());
            
            stmUsuario.setString(2, u.getContrasena());
            stmUsuario.setInt(3, u.getFondos());
            stmUsuario.setString(4, u.getTipoUsuario().toString());
            stmUsuario.setInt(5, 1);
            stmUsuario.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
            return 1;
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return 0;
    }
    
    public void modificarContrasena(Usuario usuario){
        Connection con;
        PreparedStatement stmContrasena = null;

        con = super.getConexion();
        
        try {
            stmContrasena = con.prepareStatement("update usuarios " 
                    + "set contrasena = ?"
                    + "    where id_usuario=?");
            
            
            System.out.println("\nCONTRASENA ANTES");
            System.out.println(usuario.getContrasena());
            
            //Encriptacion               
            this.configuracion = new PWDConfig(usuario.getContrasena());            
            usuario.setContrasena(this.configuracion.getContrasenaEncriptada());
            
            System.out.println("\nCONTRASENA DESPUES");
            System.out.println(usuario.getContrasena());
            stmContrasena.setString(1, usuario.getContrasena());
            stmContrasena.setString(2, usuario.getId_usuario());

            stmContrasena.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
        } finally {
            try {
                stmContrasena.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }
    public void hashContrasenasNoEncriptadas(){
        
        for(Usuario usuario: consultarUsuarios("")){            
            modificarContrasena(usuario);
        }
    }
}
