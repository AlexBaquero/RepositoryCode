package baseDatos;

import aplicacion.Accion;
import aplicacion.Empresa;
import aplicacion.HacerOfertaVenta;
import aplicacion.TipoUsuario;
import static aplicacion.TipoUsuario.inversores;
import java.util.Date;
import java.sql.*;
import aplicacion.Usuario;
import baseDatos.AbstractDAO;
import static java.lang.Integer.min;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumnogreibd
 */
public class DAOCompraVenta extends AbstractDAO {
    

    public DAOCompraVenta(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public java.util.List<Empresa> consultarOfertas(String nombreempresa, String idusuario) {
        java.util.List<Empresa> empresas;
        //listado de empresas con ese nombre
        empresas = this.getFachadaAplicacion().consultarEmpresasPorNombre(nombreempresa);
        java.util.List<Empresa> resultado = new java.util.ArrayList<Empresa>();

        //para cada una consulto las ofertas
        for (Empresa e : empresas) {
            resultado.addAll(this.consultarOfertasAux(e.getUsuario().getId_usuario(),idusuario));
        }

        return resultado;
    }
    //devolve ofertas de empresa según id

    public java.util.List<Empresa> consultarOfertasAux(String idempresa,String idusuario) {
        java.util.List<Empresa> resultado = new java.util.ArrayList<Empresa>();
        Empresa empresaActual;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();

        String consulta = "SELECT idempresa,empresa,sum(numparticipaciones) as total "//FORMAT (fechaoferta, 'dd/MM/yyyy, hh:mm:ss ') as fecha, precio,numparticipaciones,disponibilidad,empresa,idempresa,idusuario  "
                + "from hacerofertaventa "
                + "where idempresa like ? and idusuario!=?"
                +"group by idempresa,empresa ";

        try {
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, "%" + idempresa + "%");
            stmCatalogo.setString(2,idusuario);
            rsCatalogo = stmCatalogo.executeQuery();
            while (rsCatalogo.next()) {
               empresaActual=new Empresa(rsCatalogo.getString("empresa"),new Usuario(rsCatalogo.getString("idempresa"),null,0,"inversores"),null,null,null,0,rsCatalogo.getInt("total"),0,0);
                resultado.add(empresaActual);
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

        
    public Boolean Comprar(Usuario u, int cantidad,String idempresa){
        Boolean resultado=false;
        Connection con;
        HacerOfertaVenta ofertaparcial=null;
        String consulta;
        int i=0, cantidadacomprar=0;
        
     
        
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;
        con = this.getConexion();
        int cantidadcomprada=0;
        try {
            con.setAutoCommit(false);
            
            while(cantidadcomprada<cantidad){                                  
                
                                    //Buscar oferta más barata
                                    consulta = "select * " +
                        "            from hacerofertaventa " +
                        "            where idempresa=? and idusuario!=? " +
                        "            order by precio asc, fechaoferta asc";
                                    stmCatalogo = con.prepareStatement(consulta);
                                    stmCatalogo.setString(1,idempresa );
                                    stmCatalogo.setString(2,u.getId_usuario() );
                                    rsCatalogo = stmCatalogo.executeQuery();
                                    if (rsCatalogo.next()) {
                                        //existe alguna oferta
                                        ofertaparcial= new HacerOfertaVenta(rsCatalogo.getTimestamp("fechaoferta"),rsCatalogo.getFloat("precio"),rsCatalogo.getBoolean("disponibilidad"),new Empresa(rsCatalogo.getString("empresa"),new Usuario(rsCatalogo.getString("idempresa"),null,0,"empresas"),null,null,null,0,0,0,0),new Usuario(rsCatalogo.getString("idusuario"),null,0,"inversores"),rsCatalogo.getInt("numparticipaciones"));
                                        
                                    }
                                    else{
                                        this.getFachadaAplicacion().muestraExcepcion("No existe oferta.");
                                        if(i==0){
                                                con.rollback();
                                                return false;
                                        }
                                    }
                                    //Comprobación fondos
                                    
                                        consulta = "select * " +
                                            "from usuarios\n" +
                                            "where id_usuario=? and fondos>= ( " +
                                            "            select precio*? " +
                                            "            from hacerofertaventa " +
                                            "            where idusuario=? and idempresa=? and fechaoferta=? and empresa=? )";
                                        stmCatalogo = con.prepareStatement(consulta);
                                        stmCatalogo.setString(1,u.getId_usuario() );
                                        stmCatalogo.setInt(2,min(cantidad-cantidadcomprada,ofertaparcial.getNumeroParticipaciones()) );
                                        stmCatalogo.setString(3,ofertaparcial.getUsuario().getId_usuario());                                        
                                        stmCatalogo.setString(4,ofertaparcial.getEmpresa().getUsuario().getId_usuario() );
                                        stmCatalogo.setTimestamp(5, (Timestamp) ofertaparcial.getFechaOferta());
                                        stmCatalogo.setString(6,ofertaparcial.getEmpresa().getCIF() );
                                        rsCatalogo = stmCatalogo.executeQuery();
                                        if (rsCatalogo.next()) {
                                            //hay fondos
                                            }
                                        else{
                                            this.getFachadaAplicacion().muestraExcepcion("No hay fondos suficientes");
                                            if(i==0){
                                                con.rollback();
                                                return false;
                                            }
                                        }
                                    
                                    cantidadacomprar=min(cantidad-cantidadcomprada,ofertaparcial.getNumeroParticipaciones());
                                    PreparedStatement stmUsuario = null;
                                    //Insertar tupla compraventa
                                    stmUsuario = con.prepareStatement("insert into compraventa(idusuariovendedor, idusuariocomprador, empresa, idempresa, fechacompra, numeroparticipaciones, comision ) "
                                            + "values (?,?,?,?, ?, ?, 0.05)");
                                    stmUsuario.setString(1, ofertaparcial.getUsuario().getId_usuario());
                                    stmUsuario.setString(2, u.getId_usuario());
                                    stmUsuario.setString(3, ofertaparcial.getEmpresa().getCIF());
                                    stmUsuario.setString(4, ofertaparcial.getEmpresa().getUsuario().getId_usuario());
                                    stmUsuario.setTimestamp(5, new Timestamp(new Date().getTime()));
                                    stmUsuario.setInt(6, cantidadacomprar);
                                    int afectedrows=stmUsuario.executeUpdate();
                                    if(afectedrows>0){
                                    }
                                    else{
                                        this.getFachadaAplicacion().muestraExcepcion("Error insertando tupla compraventa");
                                        con.rollback();
                                        return false;
                                    }
                                    //Comprobación cantidad igual a oferta
                                    
                                    if (cantidadacomprar==ofertaparcial.getNumeroParticipaciones()) {//igual
                                        
                                        /*//Borrar poseedor
                                        stmUsuario = con.prepareStatement("delete from poseer where idusuario=? and idempresa= ? and empresa= ?");
                                        stmUsuario.setString(1, ofertaparcial.getUsuario().getId_usuario());
                                        stmUsuario.setString(2, ofertaparcial.getEmpresa().getUsuario().getId_usuario());

                                        stmUsuario.setString(3, ofertaparcial.getEmpresa().getCIF());
                                        afectedrows=stmUsuario.executeUpdate();
                                        if(afectedrows>0){
                                        }
                                        else{
                                            this.getFachadaAplicacion().muestraExcepcion("Error al borrar al antiguo poseedor");
                                            con.rollback();
                                            return false;
                                        }*/
                                        
                                        
                                        //modificar poseedor
                                        stmUsuario = con.prepareStatement("update poseer "
                                            + "set cantidad = cantidad - ?,  "
                                            + "fecha = ?  "
                                            + "where idusuario=? and idempresa= ? and empresa= ?");
                                        stmUsuario.setInt(1, cantidadacomprar);
                                        stmUsuario.setTimestamp(2, new Timestamp(new Date().getTime()));
                                        stmUsuario.setString(3, ofertaparcial.getUsuario().getId_usuario());
                                        stmUsuario.setString(4, ofertaparcial.getEmpresa().getUsuario().getId_usuario());
                                        stmUsuario.setString(5, ofertaparcial.getEmpresa().getCIF());
                                        afectedrows=stmUsuario.executeUpdate();
                                        if(afectedrows>0){
                                        }
                                        else{
                                            this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar el poseedor vendedor");
                                            con.rollback();
                                            return false;
                                        }
                                        
                                        
                                        //borrar poseedor si tiene 0 acciones
                                        stmUsuario = con.prepareStatement("delete from poseer "
                                            + "where cantidad <= 0");
                                        afectedrows=stmUsuario.executeUpdate();
                                                                                
                                        
                                        //Borrar oferta
                                        stmUsuario = con.prepareStatement("delete from hacerofertaventa where idusuario=? and idempresa= ? and fechaoferta=? and empresa= ?");
                                        stmUsuario.setString(1, ofertaparcial.getUsuario().getId_usuario());
                                        stmUsuario.setString(2, ofertaparcial.getEmpresa().getUsuario().getId_usuario());

                                        stmUsuario.setTimestamp(3, (Timestamp) ofertaparcial.getFechaOferta());
                                        stmUsuario.setString(4, ofertaparcial.getEmpresa().getCIF());
                                        afectedrows=stmUsuario.executeUpdate();
                                        if(afectedrows>0){
                                        }
                                        else{
                                            this.getFachadaAplicacion().muestraExcepcion("No se pudo borrar correctamente la oferta");
                                            con.rollback();
                                            return false;
                                        }

                                        
                                    }
                                    else{//diferente
                                        //Modificar oferta
                                         stmUsuario = con.prepareStatement("update hacerofertaventa "
                                            + "set numparticipaciones= numparticipaciones - ?  "
                                            + "where idusuario=? and idempresa= ? and fechaoferta=? and empresa= ?");

                                        stmUsuario.setInt(1, cantidadacomprar);
                                        stmUsuario.setString(2, ofertaparcial.getUsuario().getId_usuario());
                                        stmUsuario.setString(3, ofertaparcial.getEmpresa().getUsuario().getId_usuario());
                                        stmUsuario.setTimestamp(4, (Timestamp) ofertaparcial.getFechaOferta());
                                        stmUsuario.setString(5, ofertaparcial.getEmpresa().getCIF());
                                        afectedrows=stmUsuario.executeUpdate();
                                        if(afectedrows>0){
                                        }
                                        else{
                                            this.getFachadaAplicacion().muestraExcepcion("No se pudo actualizar la oferta");
                                            con.rollback();
                                            return false;
                                        }
                                        //modificar poseedor
                                        
                                        
                                        stmUsuario = con.prepareStatement("update poseer "
                                            + "set cantidad = cantidad - ?,  "
                                            + "fecha = ?  "
                                            + "where idusuario=? and idempresa= ? and empresa= ?");
                                        stmUsuario.setInt(1, cantidadacomprar);
                                        stmUsuario.setTimestamp(2, new Timestamp(new Date().getTime()));
                                        stmUsuario.setString(3, ofertaparcial.getUsuario().getId_usuario());
                                        stmUsuario.setString(4, ofertaparcial.getEmpresa().getUsuario().getId_usuario());
                                        stmUsuario.setString(5, ofertaparcial.getEmpresa().getCIF());
                                        afectedrows=stmUsuario.executeUpdate();
                                        if(afectedrows>0){
                                        }
                                        else{
                                            this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar el poseedor vendedor");
                                            con.rollback();
                                            return false;
                                        }
                                    }
                                    //Comprobación comprador xa ten da empresa?
                                    consulta = "select *  "
                                        + "from poseer "
                                        + "where idusuario=? and idempresa=? and empresa=? ";
                                    stmCatalogo = con.prepareStatement(consulta);
                                    stmCatalogo.setString(1, u.getId_usuario());
                                    stmCatalogo.setString(2, ofertaparcial.getEmpresa().getUsuario().getId_usuario());
                                    stmCatalogo.setString(3, ofertaparcial.getEmpresa().getCIF());

                                    rsCatalogo = stmCatalogo.executeQuery();

                                    if (rsCatalogo.next()) {//
                                        //modificar poseedor
                                        stmUsuario = con.prepareStatement("update poseer "
                                            + "set cantidad = cantidad + ?,  "
                                            + "fecha = ?  "
                                            + "where idusuario=? and idempresa= ? and empresa= ?");
                                        stmUsuario.setInt(1, cantidadacomprar);
                                        stmUsuario.setTimestamp(2, new Timestamp(new Date().getTime()));
                                        stmUsuario.setString(3, u.getId_usuario());
                                        stmUsuario.setString(4, ofertaparcial.getEmpresa().getUsuario().getId_usuario());
                                        stmUsuario.setString(5, ofertaparcial.getEmpresa().getCIF());
                                        afectedrows=stmUsuario.executeUpdate();
                                        if(afectedrows>0){
                                        }
                                        else{
                                            this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar al poseedor comprador");
                                            con.rollback();
                                            return false;
                                        }
                                    }
                                    else{//aínda non ten
                                        //insertar poseedor
                                        stmUsuario = con.prepareStatement("insert into poseer(empresa,idempresa,idusuario,fecha,cantidad ) "
                                            + "values (?,?,?, ?, ?)");
                                        stmUsuario.setString(1, ofertaparcial.getEmpresa().getCIF());
                                        stmUsuario.setString(2, ofertaparcial.getEmpresa().getUsuario().getId_usuario());
                                        stmUsuario.setString(3, u.getId_usuario());
                                        stmUsuario.setTimestamp(4, new Timestamp(new Date().getTime()));
                                        stmUsuario.setInt(5, cantidadacomprar);
                                        afectedrows=stmUsuario.executeUpdate();
                                        if(afectedrows>0){
                                        }
                                        else{
                                            this.getFachadaAplicacion().muestraExcepcion("No se pudo insertar al nuevo poseedor");
                                            con.rollback();
                                            return false;
                                        }

                                    }
                                    //Intercambio de fondos
                                    //Vendedor
                                    stmUsuario = con.prepareStatement("update usuarios "
                                            + "set fondos = fondos + ?  "
                                            + "where id_usuario=? ");
                                    stmUsuario.setFloat(1, 0.95f*cantidadacomprar*ofertaparcial.getPrecio());
                                    stmUsuario.setString(2, ofertaparcial.getUsuario().getId_usuario());

                                    afectedrows=stmUsuario.executeUpdate();
                                    if(afectedrows>0){
                                    }
                                    else{
                                        this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar fondos vendedor");
                                        con.rollback();
                                        return false;
                                    }
                                    //Comprador
                                    stmUsuario = con.prepareStatement("update usuarios "
                                    + "set fondos = fondos - ?  "
                                    + "where id_usuario=? ");
                                    stmUsuario.setFloat(1, cantidadacomprar*ofertaparcial.getPrecio());
                                    stmUsuario.setString(2, u.getId_usuario());

                                    afectedrows=stmUsuario.executeUpdate();
                                    if(afectedrows>0){
                                    }
                                    else{
                                        this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar fondos comprador");
                                        con.rollback();
                                        return false;
                                    }
                                    //Ingresar comisión
                                    stmUsuario = con.prepareStatement("update usuarios "
                                    + "set fondos = fondos + ?  "
                                    + "where id_usuario='0' ");
                                    stmUsuario.setFloat(1, 0.05f*cantidadacomprar*ofertaparcial.getPrecio());

                                    afectedrows=stmUsuario.executeUpdate();
                                    if(afectedrows>0){
                                    }
                                    else{
                                        this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar fondos comprador");
                                        con.rollback();
                                        return false;
                                    }
                                    cantidadcomprada+=cantidadacomprar;
                                    i++;
                                    
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
                stmCatalogo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
        
    }
    
    
    
    
    
    
    
    
    
    public Boolean fondossuficientes(Usuario u, HacerOfertaVenta o, int cantidad){
        Boolean resultado=false;
        Connection con;
        
        
        
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;
        con = this.getConexion();
        
        
        
        String consulta = "select precio from hacerofertaventa where idusuario=? and idempresa=? and fechaoferta=? and empresa= ? and (precio*?)<= (select fondos from usuarios where id_usuario = ?)    and  numparticipaciones>=?  ";
        
        try {
            con.setAutoCommit(false);
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1,o.getUsuario().getId_usuario() );
            stmCatalogo.setString(2,o.getEmpresa().getUsuario().getId_usuario() );
            stmCatalogo.setTimestamp(3, (Timestamp) o.getFechaOferta());
            stmCatalogo.setString(4,o.getEmpresa().getCIF());
            stmCatalogo.setInt(5,cantidad );
            stmCatalogo.setString(6,u.getId_usuario());
            stmCatalogo.setInt(7,cantidad );
            rsCatalogo = stmCatalogo.executeQuery();
            if (rsCatalogo.next()) {
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
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
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
    public Boolean cantidadigualaoferta(HacerOfertaVenta o, int cantidad){
        Boolean resultado=false;
        Connection con;
        
        
        
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;
        con = this.getConexion();
        
        
        
        String consulta = "select precio from hacerofertaventa where idusuario=? and idempresa=? and fechaoferta=? and empresa= ? and numparticipaciones=? ";
        
        try {
            con.setAutoCommit(false);
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1,o.getUsuario().getId_usuario() );
            stmCatalogo.setString(2,o.getEmpresa().getUsuario().getId_usuario() );
            stmCatalogo.setTimestamp(3, (Timestamp) o.getFechaOferta());
            stmCatalogo.setString(4,o.getEmpresa().getCIF());
            stmCatalogo.setInt(5,cantidad);
            rsCatalogo = stmCatalogo.executeQuery();
            if (rsCatalogo.next()) {
                resultado=true;
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
             try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
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
    public Boolean modificarFondosVendedor(String idusuario, float cantidadtotal){
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean resultado=false;
        con = super.getConexion();
        try {
            con.setAutoCommit(false);
            stmUsuario = con.prepareStatement("update usuarios "
                    + "set fondos = fondos + ?  "
                    + "where id_usuario=? ");
            stmUsuario.setFloat(1, cantidadtotal);
            stmUsuario.setString(2, idusuario);

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
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
            }
            return resultado;
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }
    public Boolean modificarFondosComprador(String idusuario, float cantidadtotal){
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean resultado=false;
        con = super.getConexion();
        try {
            con.setAutoCommit(false);
            stmUsuario = con.prepareStatement("update usuarios "
                    + "set fondos = fondos - ?  "
                    + "where id_usuario=? ");
            stmUsuario.setFloat(1, cantidadtotal);
            stmUsuario.setString(2, idusuario);

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
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
            }
            return resultado;
        } finally {
            try {
                stmUsuario.close();
                con.commit();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }
    public Boolean borrarPoseedor(HacerOfertaVenta oferta) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean resultado=false;
        con = super.getConexion();

        try {
            con.setAutoCommit(false);
            stmUsuario = con.prepareStatement("delete from poseer where idusuario=? and idempresa= ? and empresa= ?");
            stmUsuario.setString(1, oferta.getUsuario().getId_usuario());
            stmUsuario.setString(2, oferta.getEmpresa().getUsuario().getId_usuario());

            stmUsuario.setString(3, oferta.getEmpresa().getCIF());
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
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public Boolean insertarPoseedor(Usuario comprador, int cantidad, HacerOfertaVenta oferta) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean resultado=false;
        con = super.getConexion();

        try {
            con.setAutoCommit(false);
            stmUsuario = con.prepareStatement("insert into poseer(empresa,idempresa,idusuario,fecha,cantidad ) "
                    + "values (?,?,?, CURRENT_TIMESTAMP, ?)");
            stmUsuario.setString(1, oferta.getEmpresa().getCIF());
            stmUsuario.setString(2, oferta.getEmpresa().getUsuario().getId_usuario());
            stmUsuario.setString(3, comprador.getId_usuario());
            stmUsuario.setInt(4, cantidad);
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
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
            }
            return resultado;
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public Boolean modificarPoseedorVendedor(int cantidad, HacerOfertaVenta oferta) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean resultado=false;
        con = super.getConexion();
        try {
            con.setAutoCommit(false);
            stmUsuario = con.prepareStatement("update poseer "
                    + "set cantidad = cantidad - ?,  "
                    + "fecha = CURRENT_TIMESTAMP  "
                    + "where idusuario=? and idempresa= ? and empresa= ?");
            stmUsuario.setInt(1, cantidad);
            stmUsuario.setString(2, oferta.getUsuario().getId_usuario());
            stmUsuario.setString(3, oferta.getEmpresa().getUsuario().getId_usuario());
            stmUsuario.setString(4, oferta.getEmpresa().getCIF());
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
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
            }
            return resultado;
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public Boolean modificarPoseedorComprador(Usuario u, int cantidad, HacerOfertaVenta oferta) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean resultado=false;

        con = super.getConexion();
        try {
            con.setAutoCommit(false);
            stmUsuario = con.prepareStatement("update poseer "
                    + "set cantidad = cantidad + ?,  "
                    + "fecha = CURRENT_TIMESTAMP  "
                    + "where idusuario=? and idempresa= ? and empresa= ?");
            stmUsuario.setInt(1, cantidad);
            stmUsuario.setString(2, u.getId_usuario());
            stmUsuario.setString(3, oferta.getEmpresa().getUsuario().getId_usuario());

            //java.sql.Date fecha = new java.sql.Date (oferta.getFechaOferta().getTime());
            //stmUsuario.setTimestamp(4,(Timestamp) oferta.getFechaOferta());
            stmUsuario.setString(4, oferta.getEmpresa().getCIF());
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
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
            }
            return resultado;
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public Boolean  modificarOferta(HacerOfertaVenta oferta, int cantidadvendida) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean resultado=false;
        con = super.getConexion();
        try {
            con.setAutoCommit(false);
            stmUsuario = con.prepareStatement("update hacerofertaventa "
                    + "set numparticipaciones= ?  "
                    + "where idusuario=? and idempresa= ? and fechaoferta=? and empresa= ?");

            stmUsuario.setInt(1, cantidadvendida);
            stmUsuario.setString(2, oferta.getUsuario().getId_usuario());
            stmUsuario.setString(3, oferta.getEmpresa().getUsuario().getId_usuario());

            stmUsuario.setTimestamp(4, (Timestamp) oferta.getFechaOferta());
            stmUsuario.setString(5, oferta.getEmpresa().getCIF());
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
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
            }
            return resultado;
        } finally {
            try {
                con.commit();
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }
    
    
   

    public Boolean borrarOferta(HacerOfertaVenta oferta) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean resultado=false;
        con = super.getConexion();

        try {
            con.setAutoCommit(false);
            stmUsuario = con.prepareStatement("delete from hacerofertaventa where idusuario=? and idempresa= ? and fechaoferta=? and empresa= ?");
            stmUsuario.setString(1, oferta.getUsuario().getId_usuario());
            stmUsuario.setString(2, oferta.getEmpresa().getUsuario().getId_usuario());

            stmUsuario.setTimestamp(3, (Timestamp) oferta.getFechaOferta());
            stmUsuario.setString(4, oferta.getEmpresa().getCIF());
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
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
            }
            return resultado;
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public Boolean Insertartuplacompraventa(String comprador, String vendedor, String cifempresa, String idempresa, int cantidad) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean resultado=false;

        con = super.getConexion();

        try {
            con.setAutoCommit(false);
            stmUsuario = con.prepareStatement("insert into compraventa(idusuariovendedor, idusuariocomprador, empresa, idempresa, fechacompra, numeroparticipaciones, comision ) "
                    + "values (?,?,?,?, CURRENT_TIMESTAMP, ?, 0.05)");
            stmUsuario.setString(1, vendedor);
            stmUsuario.setString(2, comprador);
            stmUsuario.setString(3, cifempresa);
            stmUsuario.setString(4, idempresa);
            stmUsuario.setInt(5, cantidad);
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
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    
    public java.util.List<HacerOfertaVenta> buscarOferta(java.util.Date fecha, String cif, String idempresa, String idusuario) {
        java.util.List<HacerOfertaVenta> resultado = new java.util.ArrayList<HacerOfertaVenta>();
        HacerOfertaVenta ofertaActual;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;
        con = this.getConexion();
        
        String consulta = "select * "
                + "from hacerofertaventa "
                + "where fechaoferta = ? and empresa = ? and idempresa = ? and idusuario = ? ";

        try {
            stmCatalogo = con.prepareStatement(consulta);
           
            stmCatalogo.setTimestamp(1, (Timestamp) fecha);
            stmCatalogo.setString(2, cif);
            stmCatalogo.setString(3, idempresa);
            stmCatalogo.setString(4, idusuario);
            rsCatalogo = stmCatalogo.executeQuery();
            while (rsCatalogo.next()) {
                Usuario u = this.getFachadaAplicacion().consultarUsuarios(rsCatalogo.getString("idusuario")).get(0);
                ofertaActual = new HacerOfertaVenta(rsCatalogo.getTimestamp("fechaoferta"), rsCatalogo.getFloat("precio"), rsCatalogo.getBoolean("disponibilidad"), this.getFachadaAplicacion().consultarEmpresas(rsCatalogo.getString("idempresa")).get(0), u, rsCatalogo.getInt("numparticipaciones"));
                resultado.add(ofertaActual);
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

    public Boolean poseerEmpresa(Usuario comprador, HacerOfertaVenta oferta) {
        Boolean resultado = false;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();

        String consulta = "select *  "
                + "from poseer "
                + "where idusuario=? and idempresa=? and empresa=? ";

        try {
            con.setAutoCommit(false);
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, comprador.getId_usuario());
            stmCatalogo.setString(2, oferta.getEmpresa().getUsuario().getId_usuario());

            //java.sql.Date fecha = new java.sql.Date (oferta.getFechaOferta().getTime());
            //stmCatalogo.setTimestamp(3, (Timestamp) oferta.getFechaOferta());
            stmCatalogo.setString(3, oferta.getEmpresa().getCIF());
            
            rsCatalogo = stmCatalogo.executeQuery();
            if (rsCatalogo.next()) {
                resultado = true;
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
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
            }
            return resultado;
        } finally {
            try {
                stmCatalogo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public java.util.List<HacerOfertaVenta> filtrarOfertasUsuario(Usuario u, String nombreempresa) {
        java.util.List<Empresa> empresas;
        //listado de empresas con ese nombre
        empresas = this.getFachadaAplicacion().consultarEmpresasPorNombre(nombreempresa);
        java.util.List<HacerOfertaVenta> resultado = new java.util.ArrayList<HacerOfertaVenta>();
        HacerOfertaVenta ofertaActual;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();
        String consulta = "SELECT * "+ "from hacerofertaventa "
                + "where idempresa like ? and idusuario=? ";
        //para cada una consulto las ofertas
        
        try {
            con.setAutoCommit(false);
             for (Empresa e : empresas) {
            
            
                stmCatalogo = con.prepareStatement(consulta);
                stmCatalogo.setString(1, "%" + e.getUsuario().getId_usuario() + "%");
                stmCatalogo.setString(2, u.getId_usuario());
                rsCatalogo = stmCatalogo.executeQuery();
                while (rsCatalogo.next()) {
                    ofertaActual = new HacerOfertaVenta(rsCatalogo.getTimestamp("fechaoferta"), rsCatalogo.getFloat("precio"), rsCatalogo.getBoolean("disponibilidad"), this.getFachadaAplicacion().consultarEmpresas(rsCatalogo.getString("idempresa")).get(0), new Usuario(u.getId_usuario(),null,0,"inversores"), rsCatalogo.getInt("numparticipaciones"));
                    resultado.add(ofertaActual);
                }
            
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
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
            }
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

    public java.util.List<HacerOfertaVenta> filtrarOfertasUsuarioaux(Usuario user, String idempresa) {
        java.util.List<HacerOfertaVenta> resultado = new java.util.ArrayList<HacerOfertaVenta>();
        HacerOfertaVenta ofertaActual;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();

        String consulta = "SELECT * "//FORMAT (fechaoferta, 'dd/MM/yyyy, hh:mm:ss ') as fecha, precio,numparticipaciones,disponibilidad,empresa,idempresa,idusuario  "
                + "from hacerofertaventa "
                + "where idempresa like ? and idusuario=? ";

        try {
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, "%" + idempresa + "%");
            stmCatalogo.setString(2, user.getId_usuario());
            rsCatalogo = stmCatalogo.executeQuery();
            while (rsCatalogo.next()) {
                Usuario u = this.getFachadaAplicacion().consultarUsuarios(rsCatalogo.getString("idusuario")).get(0);
                ofertaActual = new HacerOfertaVenta(rsCatalogo.getTimestamp("fechaoferta"), rsCatalogo.getFloat("precio"), rsCatalogo.getBoolean("disponibilidad"), this.getFachadaAplicacion().consultarEmpresas(rsCatalogo.getString("idempresa")).get(0), u, rsCatalogo.getInt("numparticipaciones"));
                resultado.add(ofertaActual);
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

    public Boolean insertarOferta(HacerOfertaVenta oferta) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean resultado=false;
        ResultSet rsCatalogo;

        con = super.getConexion();

        try {
            con.setAutoCommit(false);
            String consulta = "select * from poseer where idusuario=? and idempresa=? and ?<=cantidad-(select COALESCE(sum(numparticipaciones),0) from hacerofertaventa where idusuario=? and idempresa=? )";
            stmUsuario = con.prepareStatement(consulta);
            stmUsuario.setString(1, oferta.getUsuario().getId_usuario());
            stmUsuario.setString(2, oferta.getEmpresa().getUsuario().getId_usuario());
            stmUsuario.setInt(3, oferta.getNumeroParticipaciones());
            stmUsuario.setString(4, oferta.getUsuario().getId_usuario());
            stmUsuario.setString(5, oferta.getEmpresa().getUsuario().getId_usuario());
            
            rsCatalogo = stmUsuario.executeQuery();
            if(rsCatalogo.next()){
                con.setAutoCommit(false);
                 stmUsuario = con.prepareStatement("insert into hacerofertaventa(fechaoferta,precio,numparticipaciones,disponibilidad,empresa,idempresa,idusuario ) "
                        + "values (CURRENT_TIMESTAMP, ?,?,true,?,?,? )");

                stmUsuario.setFloat(1, oferta.getPrecio());
                stmUsuario.setInt(2, oferta.getNumeroParticipaciones());
                stmUsuario.setString(3, oferta.getEmpresa().getCIF());
                stmUsuario.setString(4, oferta.getEmpresa().getUsuario().getId_usuario());
                stmUsuario.setString(5, oferta.getUsuario().getId_usuario());
                int afectedrows=stmUsuario.executeUpdate();
                if(afectedrows>0){
                    resultado=true;
                }
                else{
                    con.rollback();
                }
            }
            else{
                fa.muestraExcepcion("No posees suficientes acciones para ofertar esa cantidad.");
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
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }
    
    //Retiramos las participaciones
    public void retirarParticipaciones(String iduser, int cantidad, String cif){
        Connection con;
        PreparedStatement stmUsuario = null;
        con = super.getConexion();
        /*
        String consulta = "DO $$ " +
                          "BEGIN " +
                          "IF ((select COUNT (cif) from empresas where idusuario = ? and cif = ? ) = 1) " +
                          "THEN " +
                          "UPDATE poseer SET cantidad = cantidad - ? where idusuario = ? ; " +
                          "END IF; " +
                          "END $$;";*/
        String consulta= "UPDATE poseer SET cantidad = cantidad - ? where idusuario = ?";
        try {
            stmUsuario = con.prepareStatement(consulta);
            
            stmUsuario.setInt(1, cantidad);
            stmUsuario.setString(2, iduser);
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
    
    
    
     public Boolean usuarioPoseeAcciones(Usuario comprador) {
        Boolean resultado = false;
        Connection con;
        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;

        con = this.getConexion();

        String consulta = "select *  "
                + "from poseer "
                + "where idusuario=? and idempresa like '%' and empresa like '%' ";

        try {
            con.setAutoCommit(false);
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, comprador.getId_usuario());
            
            
            rsCatalogo = stmCatalogo.executeQuery();
            if (rsCatalogo.next()) {
                resultado = true;
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
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException er) {
                System.out.println(er.getMessage());
            }
            return resultado;
        } finally {
            try {
                stmCatalogo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

}


