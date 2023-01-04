/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Anuncios;
import aplicacion.FachadaAplicacion;
import aplicacion.Usuario;
import aplicacion.Empresa;
import aplicacion.Poseer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author anaca
 */
public class DAOEmpresas extends AbstractDAO {

    public DAOEmpresas(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    //Buscar empresa o listado Empresa
    public Empresa consultarUnaEmpresa(Usuario user) {
        Connection con;
        Empresa empresaActual = null;
        PreparedStatement stmEmpresa = null;
        ResultSet rsEmpresas;

        con = this.getConexion();

        try {
            stmEmpresa = con.prepareStatement("select * from empresas where idusuario = ?");
            stmEmpresa.setString(1, user.getId_usuario());
            rsEmpresas = stmEmpresa.executeQuery();

            //devolvemos una empresa que en el campo  Usuario no tenga contraseña 
            if (rsEmpresas.next()) {

                empresaActual = new Empresa(rsEmpresas.getString("CIF"), user, rsEmpresas.getString("nombrecomercial"),
                        rsEmpresas.getString("ciudad"), rsEmpresas.getString("calle"), rsEmpresas.getInt("numero"), rsEmpresas.getInt("telefono"), rsEmpresas.getInt("participaciones"), rsEmpresas.getInt("participacionessinvender"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            try {
                stmEmpresa.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return empresaActual;
    }

    //Buscar empresa o listado Empresa
    public java.util.List<Empresa> consultarEmpresas(String id) {
        Connection con;
        ArrayList<Empresa> empresasBuscadas = new ArrayList<>();
        Empresa empresaActual;
        PreparedStatement stmEmpresa = null;
        ResultSet rsEmpresas;

        con = this.getConexion();

        try {
            stmEmpresa = con.prepareStatement("select * from empresas where idusuario like ?");
            stmEmpresa.setString(1, "%" + id + "%");
            rsEmpresas = stmEmpresa.executeQuery();

            //devolvemos una empresa que en el campo  Usuario no tenga contraseña 
            while (rsEmpresas.next()) {

                empresaActual = new Empresa(rsEmpresas.getString("CIF"), this.getFachadaAplicacion().consultarUsuarios(id).get(0), rsEmpresas.getString("nombrecomercial"),
                        rsEmpresas.getString("ciudad"), rsEmpresas.getString("calle"), rsEmpresas.getInt("numero"), rsEmpresas.getInt("telefono"), rsEmpresas.getInt("participaciones"), rsEmpresas.getInt("participacionessinvender"));
                empresasBuscadas.add(empresaActual);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            try {
                stmEmpresa.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return empresasBuscadas;
    }

    //Registro de empresa, inicializar participacionesSinVender
    //devuelve 0 si no hay errores, 1 si hay campos nulos, 2 si usuario repetido, 3 en otro caso
    public int registrarEmpresa(Empresa empresa) {
        Connection con;
        PreparedStatement stmEmpresa = null;
        int devolver = 0;
        con = this.getConexion();

        try {
            stmEmpresa = con.prepareStatement("insert into empresas values (?, ?, ?, ?, ?, ?, ?, 0, ?, 0)");
            stmEmpresa.setString(1, empresa.getUsuario().getId_usuario());
            stmEmpresa.setString(2, empresa.getCIF());
            stmEmpresa.setString(3, empresa.getNombreComercial());
            stmEmpresa.setString(4, empresa.getCiudad());
            stmEmpresa.setString(5, empresa.getCalle());
            stmEmpresa.setInt(6, empresa.getNumero());
            stmEmpresa.setInt(7, empresa.getTelefono());
            stmEmpresa.setInt(8, empresa.getParticipacionesSinVender());

            stmEmpresa.executeUpdate();
            devolver = 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            switch (e.getErrorCode()) {
                case 23502:
                    devolver = 1;
                    //this.getFachadaAplicacion().muestraExcepcion("Revisa aquellos campos que deban ser no nulos.\n");
                    break;
                case 23505:
                    devolver = 2;
                    //this.getFachadaAplicacion().muestraExcepcion("El id_usuario introducido ya se encuentra en uso.\n");
                    break;
                default:
                    devolver = 3;
                    //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    break;
            }
            return 1;

        } finally {
            try {
                stmEmpresa.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return devolver;
    }

    //Dar de baja empresa
    public void eliminarEmpresa(String id) {
        Connection con;
        PreparedStatement stmEmpresa = null;

        con = this.getConexion();

        try {
            stmEmpresa = con.prepareStatement("delete from empresas where idusuario = ?");

            stmEmpresa.setString(1, id);

            stmEmpresa.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmEmpresa.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    public void guardarDatosEmpresa(String TextoCIF, String TextoCalle, String TextoID, String TextoNombre, Integer TextoNumero,
            Integer TextoParticipaciones, Integer TextoParticipacionesSinVende, String TextoPoblacion, Integer TextoTelefono) {
        Connection con;
        PreparedStatement stmEmpresa = null;

        con = this.getConexion();

        try {
            stmEmpresa = con.prepareStatement("update empresas "
                    + "set calle=?, "
                    + "    nombrecomercial=?, "
                    + "    numero=?, "
                    + "    participaciones=?, "
                    + "    participacionessinvender=?, "
                    + "    telefono=?, "
                    + "    ciudad=?, "
                    + "    cif=? "
                    + "where idusuario=?");

            stmEmpresa.setString(1, TextoCalle);
            stmEmpresa.setString(2, TextoNombre);
            stmEmpresa.setInt(3, TextoNumero);
            stmEmpresa.setInt(4, TextoParticipaciones);
            stmEmpresa.setInt(5, TextoParticipacionesSinVende);
            stmEmpresa.setInt(6, TextoTelefono);
            stmEmpresa.setString(7, TextoPoblacion);
            stmEmpresa.setString(9, TextoID);
            stmEmpresa.setString(8, TextoCIF);

         

            stmEmpresa.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmEmpresa.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    //Función para buscar empresas por su nombre
    public java.util.List<Empresa> consultarEmpresasPorNombre(String nombre) {
        Connection con;
        ArrayList<Empresa> empresasBuscadas = new ArrayList<Empresa>();
        Empresa empresaActual;
        PreparedStatement stmEmpresa = null;
        ResultSet rsEmpresas;

        con = this.getConexion();

        try {
            stmEmpresa = con.prepareStatement("select * from empresas where nombrecomercial like ? and idusuario in (select idempresa from hacerofertaventa) ");
            stmEmpresa.setString(1, "%" + nombre + "%");
            rsEmpresas = stmEmpresa.executeQuery();

            while (rsEmpresas.next()) {
                empresaActual = new Empresa(rsEmpresas.getString("CIF"), this.getFachadaAplicacion().consultarUsuarios(rsEmpresas.getString("idusuario")).get(0), rsEmpresas.getString("nombrecomercial"), rsEmpresas.getString("ciudad"), rsEmpresas.getString("calle"), rsEmpresas.getInt("numero"), rsEmpresas.getInt("telefono"), rsEmpresas.getInt("participaciones"), rsEmpresas.getInt("participacionessinvender"));
                empresasBuscadas.add(empresaActual);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            try {
                stmEmpresa.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return empresasBuscadas;
    }

    //Sacar acciones a la venta
    public Boolean GenerarAcciones(Usuario usuario, int cantidad) {
        Boolean resultado=false;
        Empresa emp = this.consultarEmpresas(usuario.getId_usuario()).get(0);
        System.out.println(emp.getCIF());
        Connection con;
        //PreparedStatement stmUsuario = null;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;
        
        
        con = this.getConexion();
        try {
            con.setAutoCommit(false);
            
             String consulta = "select * from poseer where idusuario=? and idempresa=? and empresa= ? ";
        
            
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1,usuario.getId_usuario() );
            stmCatalogo.setString(2,usuario.getId_usuario() );
            stmCatalogo.setString(3, emp.getCIF());
            rsCatalogo = stmCatalogo.executeQuery();
            PreparedStatement stmUsuario = null;
            //xa ten de si mesmo, actualizo
            if (rsCatalogo.next()) {
                stmUsuario = con.prepareStatement("update poseer "
                    + "set cantidad = cantidad + ?,  "
                    + "fecha = CURRENT_TIMESTAMP  "
                    + "where idusuario=? and idempresa= ? and empresa= ?");
                stmUsuario.setInt(1, cantidad);
                stmUsuario.setString(2, usuario.getId_usuario());
                stmUsuario.setString(3, usuario.getId_usuario());
                stmUsuario.setString(4, emp.getCIF());
                int afectedrows=stmUsuario.executeUpdate();
                if(afectedrows>0){
                }
                else{
                    con.rollback();
                }
                
            }
            //non ten, inserto tupla en poseer
            else{
                stmUsuario = con.prepareStatement("insert into poseer(empresa,idempresa,idusuario,fecha,cantidad ) "
                    + "values (?,?,?, CURRENT_TIMESTAMP, ?)");
                stmUsuario.setString(1, emp.getCIF());
                stmUsuario.setString(2, usuario.getId_usuario());
                stmUsuario.setString(3, usuario.getId_usuario());
                stmUsuario.setInt(4, cantidad);
                int afectedrows=stmUsuario.executeUpdate();
                if(afectedrows>0){
                }
                else{
                    con.rollback();
                }
            }
            stmUsuario = con.prepareStatement("update empresas "
                    + "set participaciones = participaciones + ?,  "
                    + "participacionessinvender = participacionessinvender + ?"
                    + "where idusuario= ? and cif= ?");
                stmUsuario.setInt(1, cantidad);
                stmUsuario.setInt(2, cantidad);
                stmUsuario.setString(3, usuario.getId_usuario());
                stmUsuario.setString(4, emp.getCIF());
                int afectedrows=stmUsuario.executeUpdate();
                if(afectedrows>0){
                    resultado=true;
                }
                else{
                    con.rollback();
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
            return resultado;
        } finally {
            try {
                con.commit();
                stmCatalogo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }
    
    public ArrayList<Anuncios> obtenerAnuncios(String id, String informacion) {
        Connection con;
        ArrayList<Anuncios> anunciosBusqueda = new ArrayList<>();
        Anuncios anuncioActual;
        PreparedStatement stmAnuncio = null;
        ResultSet rsAnuncios;

        con = this.getConexion();

        try {
            stmAnuncio = con.prepareStatement("select *"
                    + " from anuncios"
                    + " where empresa = ? and informacion like ?");
            stmAnuncio.setString(1, id);
            stmAnuncio.setString(2, "%" + informacion + "%");

            rsAnuncios = stmAnuncio.executeQuery();

            //devolvemos una empresa que en el campo  Usuario no tenga contraseña 
            while (rsAnuncios.next()) {

                anuncioActual = new Anuncios(rsAnuncios.getInt("idanuncio"), null, rsAnuncios.getDate("fechapubli"),
                        rsAnuncios.getDate("fechapago"), rsAnuncios.getString("informacion"));
                anunciosBusqueda.add(anuncioActual);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            try {
                stmAnuncio.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return anunciosBusqueda;
    }
    
   public int crearAnuncio(String cif, Date fechaPago, String informacion) {
        Connection con;
        PreparedStatement stmEmpresa = null;
        int devolver = 0;
        con = this.getConexion();
        ResultSet resul;
        
        int id=0;
        
        try {
           stmEmpresa = con.prepareStatement("select idanuncio from anuncios");
           resul = stmEmpresa.executeQuery();
        
           while(resul.next()){
               id = resul.getInt("idanuncio");
           }
        } catch (SQLException e) {
            return 1;
        }
        
        id++;
        
        try {
            
            stmEmpresa = con.prepareStatement("insert into anuncios (idanuncio, informacion, fechapubli, fechapago, empresa) values (?, ?, CURRENT_TIMESTAMP, ?, ?)");
            
            stmEmpresa.setInt(1, id);
            stmEmpresa.setString(2, informacion);
            stmEmpresa.setTimestamp(3, new Timestamp(fechaPago.getTime()));
            stmEmpresa.setString(4,cif);
           

            stmEmpresa.executeUpdate();
            
            devolver = 0;
            
        } catch (SQLException e) {
            return 1;
        } finally {
            try {
                stmEmpresa.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return devolver;
    }
   
   
   
    public void borrarAnuncio(int id) {
        Connection con;
        PreparedStatement stmEmpresa = null;

        con = this.getConexion();

        try {
            stmEmpresa = con.prepareStatement("delete from anuncios where idanuncio = ?");

            stmEmpresa.setInt(1, id);

            stmEmpresa.executeUpdate();
            
            fa.muestraExcepcion("Anuncio borrado correctamente");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmEmpresa.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    
  public boolean pagarParticipaciones(float precioAccion, Empresa empresa) {
        Connection con;
        PreparedStatement stmEmpresa = null;
        PreparedStatement stmPago = null;
        PreparedStatement stmAccion = null;
        PreparedStatement stmFondosEmpresa = null;


        ResultSet rsAccion;
        ResultSet rsEmpresa;

        con = this.getConexion();

        int afectedrows = 0;

        try {
            con.setAutoCommit(false);

            stmEmpresa = con.prepareStatement("select * from usuarios " +
                                                " where id_usuario = ?" +
                                                " and fondos >= (select sum(cantidad)* ?" +
                                                " from poseer" +
                                                " where idempresa = ?" +
                                                " and idusuario != ?" +
                                                " and idusuario in(select id_usuario from usuarios where enespera = 0))");
            stmEmpresa.setString(1, empresa.getUsuario().getId_usuario());
            stmEmpresa.setFloat(2, precioAccion);
            stmEmpresa.setString(3, empresa.getUsuario().getId_usuario());
            stmEmpresa.setString(4, empresa.getUsuario().getId_usuario());


            rsEmpresa = stmEmpresa.executeQuery();

            if (rsEmpresa.next()) {
            } else {
                this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar fondos vendedor. La empresa no tiene fondos suficientes para pagar");
                con.rollback();
                return false;
            }

            stmAccion = con.prepareStatement("select idusuario, cantidad from poseer where idempresa = ?" +
                                                " and idusuario != ?" +
                                                " and idusuario in (select id_usuario from usuarios where enespera = 0)");
            stmAccion.setString(1, empresa.getUsuario().getId_usuario());
            stmAccion.setString(2, empresa.getUsuario().getId_usuario());

            rsAccion = stmAccion.executeQuery();
            


            while (rsAccion.next()) {
                stmPago = con.prepareStatement("update usuarios "
                        + "set fondos= fondos + ? "
                        + "where id_usuario = ?");

                stmPago.setFloat(1, rsAccion.getInt("cantidad") * precioAccion);
                stmPago.setString(2, rsAccion.getString("idusuario"));

                afectedrows = stmPago.executeUpdate();
                if (afectedrows > 0) {
                } else {
                    this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar fondos vendedor");
                    con.rollback();
                    return false;
                }
                
                stmFondosEmpresa = con.prepareStatement("update usuarios "
                        + "set fondos= fondos - ? "
                        + "where id_usuario = ?");

                stmFondosEmpresa.setFloat(1, rsAccion.getInt("cantidad") * precioAccion);
                stmFondosEmpresa.setString(2, empresa.getUsuario().getId_usuario());

                afectedrows = stmFondosEmpresa.executeUpdate();
                if (afectedrows > 0) {
                } else {
                    this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar fondos vendedor");
                    con.rollback();
                    return false;
                }

            }
            
           
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
            }
            return false;
        } finally {
            try {
                con.commit();
                stmEmpresa.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return true;
    }

}
