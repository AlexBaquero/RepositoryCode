/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Usuario;
import baseDatos.AbstractDAO;
import baseDatos.AbstractDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author anaca
 */
public class DAOAdministrador extends AbstractDAO {

    public DAOAdministrador(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public java.util.List<Usuario> consultarUsuariosEnEspera(String id_usuario, String tipo, int espera) {
        java.util.List<Usuario> resultado = new java.util.ArrayList<Usuario>();
        Usuario usuarioActual;
        Connection con;
        PreparedStatement stmUsuarios = null;
        ResultSet rsUsuarios;

        con = this.getConexion();
        String consulta = "";

        

       
        try {
            //SET ISOLATION LEVEL READ UNCOMMITED
            
            
            if (espera == -2) {
                
                consulta = "SELECT * "
                        + "FROM usuarios "
                        + "WHERE id_usuario like ? and tipo_usuario like ?; ";

                stmUsuarios = con.prepareStatement(consulta);
                stmUsuarios.setString(1, "%" + id_usuario + "%");
                stmUsuarios.setString(2, "%" + tipo + "%");

            } else {
                consulta = "SELECT * "
                        + "FROM usuarios "
                        + "WHERE id_usuario like ? and tipo_usuario like ? and enespera = ?; ";
                

                stmUsuarios = con.prepareStatement(consulta);
                stmUsuarios.setString(1, "%" + id_usuario + "%");
                stmUsuarios.setString(2, "%" + tipo + "%");
                stmUsuarios.setInt(3, espera);
            }

            rsUsuarios = stmUsuarios.executeQuery();
            while (rsUsuarios.next()) {
                usuarioActual = new Usuario(rsUsuarios.getString("id_usuario"), rsUsuarios.getString("contrasena"), rsUsuarios.getInt("fondos"), rsUsuarios.getString("tipo_usuario"));
                usuarioActual.setEnEspera(rsUsuarios.getInt("enespera"));
                resultado.add(usuarioActual);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
            
        } finally {
            try {
                
                stmUsuarios.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public void darDeAlta(Usuario usuario) {
        Connection con;
        PreparedStatement stmUsuario = null;

        con = super.getConexion();
        //Se entiende que los fondos y el tipo de usuario no se pueden cambiar
        try {

            stmUsuario = con.prepareStatement("UPDATE usuarios SET enespera = 0 WHERE id_usuario =  ?; ");

            usuario.setEnEspera(0);
            stmUsuario.setString(1, usuario.getId_usuario());

            stmUsuario.executeUpdate();

            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                System.err.format("IOException: %s%n", e);
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
                con.commit();
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

    }

}
