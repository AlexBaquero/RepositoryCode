
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

/**
 *
 * @author ECL
 */
import aplicacion.Inversor;
import aplicacion.TipoUsuario;
import java.sql.*;
import aplicacion.Usuario;

/**
 *
 * @author alumnogreibd
 */
public class DAOInversores extends AbstractDAO {

    public DAOInversores(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    //Registrar Inversor: 
    public int registrarInversor(Inversor i) {
        Connection con;
        PreparedStatement stmUsuario = null;

        con = super.getConexion();

        try {
            stmUsuario = con.prepareStatement("insert into inversores(idusuario, dni, nombre, apellido1, apellido2, calle, portal, piso, puerta, telefono) "
                    + "values (?,?,?,?,?,?,?,?,?,?)");
            stmUsuario.setString(1, i.getId_usuario().getId_usuario());
            stmUsuario.setString(2, i.getDNI());
            stmUsuario.setString(3, i.getNombre());
            stmUsuario.setString(4, i.getApellido1());
            stmUsuario.setString(5, i.getApellido2());
            stmUsuario.setString(6, i.getCalle());
            stmUsuario.setInt(7, i.getPortal());
            stmUsuario.setInt(8, i.getPiso());
            stmUsuario.setString(9, i.getPuerta());
            stmUsuario.setInt(10, i.getTelefono());
            stmUsuario.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    this.getFachadaAplicacion().muestraExcepcion("Ese DNI ya está registrado.\n");
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
        return 0;//Finaliza correctamente
    }

    //Obtiene los datos del inversor utilizando el DNI
    public java.util.List<Inversor> consultarInversores(String id) {
        java.util.List<Inversor> resultado = new java.util.ArrayList<Inversor>();
        Inversor inversorActual;
        Usuario usuarioasociado;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();

        String consulta = "select idusuario, dni, nombre, apellido1, apellido2, calle, portal, piso, puerta, telefono"
                + "from inversores as i "
                + "where idusuario like ?";

        try {
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, "%" + id + "%");

            rsCatalogo = stmCatalogo.executeQuery();
            while (rsCatalogo.next()) {
                usuarioasociado = this.getFachadaAplicacion().consultarUnUsuario(rsCatalogo.getString("idusuario"));
                inversorActual = new Inversor(usuarioasociado, rsCatalogo.getString("dni"), rsCatalogo.getString("nombre"), rsCatalogo.getString("apellido1"), rsCatalogo.getString("apellido2"), rsCatalogo.getString("calle"), rsCatalogo.getInt("portal"), rsCatalogo.getInt("piso"), rsCatalogo.getString("puerta"), rsCatalogo.getInt("telefono"));

                resultado.add(inversorActual);
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
    
    //Obtiene los datos del inversor utilizando el DNI
    public Inversor consultarUnInversor(Usuario user) {
        Inversor inversorActual = null;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();

        String consulta = "select idusuario, dni, nombre, apellido1, apellido2, calle, portal, piso, puerta, telefono "
                + "from inversores "
                + "where idusuario = ?";

        try {
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, user.getId_usuario());

            rsCatalogo = stmCatalogo.executeQuery();
            if (rsCatalogo.next()) {
                inversorActual = new Inversor(user, rsCatalogo.getString("dni"), rsCatalogo.getString("nombre"), rsCatalogo.getString("apellido1"), rsCatalogo.getString("apellido2"), rsCatalogo.getString("calle"), rsCatalogo.getInt("portal"), rsCatalogo.getInt("piso"), rsCatalogo.getString("puerta"), rsCatalogo.getInt("telefono"));
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
        return inversorActual;
    }
    
    public void modificarInversor(Inversor i) {
        //presupongo que el dni es inmutable
        Connection con;
        PreparedStatement stmInversor = null;

        con = super.getConexion();
        try {
            stmInversor = con.prepareStatement("update inversores "
                    + "set idusuario=? , "
                    + "    nombre=? , "
                    + "    apellido1=? , "
                    + "    apellido2=? , "
                    + "    calle=? , "
                    + "    portal=? , "
                    + "    piso=? , "
                    + "    puerta=? , "
                    + "    telefono=?, "
                    + "    dni=? "
                    + "    where idusuario=?");
            stmInversor.setString(1, i.getId_usuario().getId_usuario());
            stmInversor.setString(2, i.getNombre());
            stmInversor.setString(3, i.getApellido1());
            stmInversor.setString(4, i.getApellido2());
            stmInversor.setString(5, i.getCalle());
            stmInversor.setInt(6, i.getPortal());
            stmInversor.setInt(7, i.getPiso());
            stmInversor.setString(8, i.getPuerta());
            stmInversor.setInt(9, i.getTelefono());
            stmInversor.setString(10, i.getDNI());
            stmInversor.setString(11, i.getId_usuario().getId_usuario());
            stmInversor.executeUpdate();

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
                stmInversor.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    public void borrarInversor(String id) {
        Connection con;
        PreparedStatement stmUsuario = null;

        con = super.getConexion();

        try {
            stmUsuario = con.prepareStatement("delete from inversores where idusuario = ?");
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

    public void SolicitarCuenta(String dni) {
        Connection con;
        PreparedStatement stmInversor = null;

        con = super.getConexion();

        try {
            stmInversor = con.prepareStatement("update inversores "
                    + "set enEspera=? , "
                    + "where dni=?");
            stmInversor.setBoolean(1, true);
            stmInversor.setString(2, dni);
            stmInversor.executeUpdate();

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
                stmInversor.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    
}
