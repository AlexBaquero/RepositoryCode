package baseDatos;

import aplicacion.Criptomoneda;
import aplicacion.HacerOfertaVenta;
import aplicacion.HacerOfertaVentaCripto;
import aplicacion.Usuario;
import java.sql.*;

/**
 *
 * @author alex
 */
public class DAOCriptoMonedas extends AbstractDAO {

    public DAOCriptoMonedas(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    //Corregir el try catch aspodfuaposdufaposdfuaopsdufoiausoidfuasoidufoasufouos
    public java.util.List<Criptomoneda> consultarCriptos(String id_usuario, String nombreCripto) {
        java.util.List<Criptomoneda> toReturn = new java.util.ArrayList<Criptomoneda>();
        Connection con;
        PreparedStatement stmConsultaCriptomonedas = null;
        ResultSet listaCriptomonedas;
        Criptomoneda auxiliar;
        con = this.getConexion();
        String consulta = "Select * "
                + "From monederos, tablaactualizacionesprecios "
                + "Where id_usuario like ? and tipo like ? "
                + "and tipo = nombrecripto";
        try {
            stmConsultaCriptomonedas = con.prepareStatement(consulta);
            stmConsultaCriptomonedas.setString(1, "%" + id_usuario + "%");
            stmConsultaCriptomonedas.setString(2, "%" + nombreCripto + "%");
            listaCriptomonedas = stmConsultaCriptomonedas.executeQuery();
            while (listaCriptomonedas.next()) {
                auxiliar = new Criptomoneda(listaCriptomonedas.getString("id_usuario"), listaCriptomonedas.getFloat("cantidad"), listaCriptomonedas.getString("tipo"), listaCriptomonedas.getFloat("precio"));
                toReturn.add(auxiliar);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmConsultaCriptomonedas.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return toReturn;
    }

    public java.util.List<HacerOfertaVentaCripto> consultarOfertasCriptos(String tipo) {
        java.util.List<HacerOfertaVentaCripto> toReturn = new java.util.ArrayList<HacerOfertaVentaCripto>();
        Connection con;
        PreparedStatement stmConsultaCriptomonedas = null;
        ResultSet listaOfertasCriptomonedas;
        HacerOfertaVentaCripto auxiliar;

        con = this.getConexion();
        String consulta = "Select * "
                + "From hacerofertaventacripto, tablaactualizacionesprecios "
                + "Where tipo like ? "
                + "and tipo = nombrecripto";
        try {
            stmConsultaCriptomonedas = con.prepareStatement(consulta);
            stmConsultaCriptomonedas.setString(1, "%" + tipo + "%");
            listaOfertasCriptomonedas = stmConsultaCriptomonedas.executeQuery();
            while (listaOfertasCriptomonedas.next()) {
                auxiliar = new HacerOfertaVentaCripto(listaOfertasCriptomonedas.getString("idusuario"),
                        listaOfertasCriptomonedas.getString("tipo"), listaOfertasCriptomonedas.getTimestamp("fechaoferta"),
                        listaOfertasCriptomonedas.getFloat("cantidad"), listaOfertasCriptomonedas.getFloat("precio"));
                toReturn.add(auxiliar);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmConsultaCriptomonedas.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return toReturn;
    }

    //Corregir el try catch aspodfuaposdufaposdfuaopsdufoiausoidfuasoidufoasufouos
    public boolean crearOfertaVentaCriptomoneda(String usuario, String nombreCripto, float cantidadAVender) {
        boolean toReturn = false;
        Connection con;
        PreparedStatement stmConsultaCriptomonedas = null;
        ResultSet resultadoOperacion;
        Criptomoneda auxiliar;

        con = this.getConexion();
        String consulta = "insert into hacerofertaventacripto values (current_timestamp,?,?,?)";
        String consulta2 = "UPDATE monederos SET cantidad = cantidad - ? where id_usuario = ? and tipo = ?";
        try {
            con.setAutoCommit(false);
            stmConsultaCriptomonedas = con.prepareStatement(consulta);
            stmConsultaCriptomonedas.setFloat(1, cantidadAVender);
            stmConsultaCriptomonedas.setString(2, nombreCripto);
            stmConsultaCriptomonedas.setString(3, usuario);
            int afectedrows = stmConsultaCriptomonedas.executeUpdate();
                if (afectedrows > 0) {
                } else {
                    this.getFachadaAplicacion().muestraExcepcion("No se pudo insertar la oferta.");
                    con.rollback();
                    return false;
                }
            
            stmConsultaCriptomonedas = con.prepareStatement(consulta2);
            stmConsultaCriptomonedas.setFloat(1, cantidadAVender);
            stmConsultaCriptomonedas.setString(2, usuario);
            stmConsultaCriptomonedas.setString(3, nombreCripto);
            afectedrows = stmConsultaCriptomonedas.executeUpdate();
                if (afectedrows > 0) {
                } else {
                    this.getFachadaAplicacion().muestraExcepcion("No se pudo actualizar el monedero.");
                    con.rollback();
                    return false;
                }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
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
                stmConsultaCriptomonedas.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return true;
    }

    public Boolean ComprarCriptos(Usuario u, float cantidad, HacerOfertaVentaCripto oferta) {
        Boolean resultado = false;
        Connection con;

        PreparedStatement stmCatalogo = null;
        ResultSet rsCatalogo;
        con = this.getConexion();

        try {
            con.setAutoCommit(false);

            //Primeira comprobación
            String consulta = "select precio from hacerofertaventacripto, tablaactualizacionesprecios where idusuario=? and fechaoferta=? and tipo = ? and tipo = nombrecripto and (precio*?)<= (select fondos from usuarios where id_usuario = ?) and cantidad >= ?";
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, oferta.getId_usuario());
            stmCatalogo.setTimestamp(2, (Timestamp) oferta.getFechaPublicacion());
            stmCatalogo.setString(3, oferta.getTipo());
            stmCatalogo.setFloat(4, cantidad);
            stmCatalogo.setString(5, u.getId_usuario());
            stmCatalogo.setFloat(6, cantidad);

            rsCatalogo = stmCatalogo.executeQuery();
            if (rsCatalogo.next()) {
                resultado = true;
            } else {
                this.getFachadaAplicacion().muestraExcepcion("Los fondos no son suficientes o la oferta ya ha sido comprada.");
                con.rollback();
                return false;
            }

            PreparedStatement stmUsuario = null;
            //Insertar tupla compraventa

            //Comprobación cantidad igual a oferta
            consulta = "select precio from hacerofertaventacripto, tablaactualizacionesprecios "
                    + "where tipo=nombrecripto and tipo=? and fechaoferta=? and idusuario= ? and cantidad=? ";
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, oferta.getTipo());
            stmCatalogo.setTimestamp(2, (Timestamp) oferta.getFechaPublicacion());
            stmCatalogo.setString(3, oferta.getId_usuario());
            stmCatalogo.setFloat(4, cantidad);
            rsCatalogo = stmCatalogo.executeQuery();
            if (rsCatalogo.next()) {//igual
                //Borrar oferta
                stmUsuario = con.prepareStatement("delete from hacerofertaventacripto where idusuario=? and tipo= ? and fechaoferta=?");
                stmUsuario.setString(1, oferta.getId_usuario());
                stmUsuario.setString(2, oferta.getTipo());

                stmUsuario.setTimestamp(3, (Timestamp) oferta.getFechaPublicacion());
                int afectedrows = stmUsuario.executeUpdate();
                if (afectedrows > 0) {
                } else {
                    this.getFachadaAplicacion().muestraExcepcion("No se pudo borrar correctamente la oferta");
                    con.rollback();
                    return false;
                }

                //Borrar poseedor
                stmUsuario = con.prepareStatement("UPDATE monederos SET cantidad = 0 where id_usuario=? and tipo= ?");
                stmUsuario.setString(1, oferta.getId_usuario());
                stmUsuario.setString(2, oferta.getTipo());
                afectedrows = stmUsuario.executeUpdate();

                if (afectedrows > 0) {
                } else {
                    this.getFachadaAplicacion().muestraExcepcion("Error al borrar al antiguo poseedor");
                    con.rollback();
                    return false;
                }
            } else {//diferente
                //Modificar oferta
                stmUsuario = con.prepareStatement("update hacerofertaventacripto "
                        + "set cantidad=cantidad - ?  "
                        + "where idusuario=? and tipo= ? and fechaoferta=?");

                stmUsuario.setFloat(1, cantidad);
                stmUsuario.setString(2, oferta.getId_usuario());
                stmUsuario.setString(3, oferta.getTipo());
                stmUsuario.setTimestamp(4, (Timestamp) oferta.getFechaPublicacion());
                int afectedrows = stmUsuario.executeUpdate();
                if (afectedrows > 0) {
                } else {
                    this.getFachadaAplicacion().muestraExcepcion("No se pudo actualizar la oferta");
                    con.rollback();
                    return false;
                }
                //modificar poseedor
                stmUsuario = con.prepareStatement("update monederos "
                        + "set cantidad = cantidad - ?  "
                        + "where id_usuario=? and tipo= ?");
                stmUsuario.setFloat(1, cantidad);
                stmUsuario.setString(2, oferta.getId_usuario());
                stmUsuario.setString(3, oferta.getTipo());
                afectedrows = stmUsuario.executeUpdate();
                if (afectedrows > 0) {
                } else {
                    this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar el poseedor vendedor");
                    con.rollback();
                    return false;
                }
            }
            //Comprobación comprador xa ten da empresa?
            consulta = "select *  "
                    + "from monederos "
                    + "where id_usuario=? and tipo=?";
            stmCatalogo = con.prepareStatement(consulta);
            stmCatalogo.setString(1, u.getId_usuario());
            stmCatalogo.setString(2, oferta.getTipo());

            rsCatalogo = stmCatalogo.executeQuery();

            if (rsCatalogo.next()) {//
                //modificar poseedor
                stmUsuario = con.prepareStatement("update monederos "
                        + "set cantidad = cantidad + ?  "
                        + "where id_usuario=? and tipo=?");
                stmUsuario.setFloat(1, cantidad);
                stmUsuario.setString(2, u.getId_usuario());
                stmUsuario.setString(3, oferta.getTipo());
                int afectedrows = stmUsuario.executeUpdate();
                if (afectedrows > 0) {
                } else {
                    this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar al poseedor comprador");
                    con.rollback();
                    return false;
                }
            } else {//aínda non ten
                //insertar poseedor
                stmUsuario = con.prepareStatement("insert into monederos "
                        + "values (?,?,?)");
                stmUsuario.setString(1, u.getId_usuario());
                stmUsuario.setString(2, oferta.getTipo());
                stmUsuario.setFloat(3, cantidad);
                int afectedrows = stmUsuario.executeUpdate();
                if (afectedrows > 0) {
                } else {
                    this.getFachadaAplicacion().muestraExcepcion("No se pudo insertar al nuevo poseedor");
                    con.rollback();
                    return false;
                }

            }
            //Intercambio de fondos
            //Vendedor
            stmUsuario = con.prepareStatement("update usuarios "
                    //Cantidad
                    + "set fondos = fondos + (select precio*? from tablaactualizacionesprecios where nombrecripto = ?) "
                    + "where id_usuario=? ");
            stmUsuario.setFloat(1, cantidad);
            stmUsuario.setString(2, oferta.getTipo());
            stmUsuario.setString(3, oferta.getId_usuario());

            int afectedrows = stmUsuario.executeUpdate();
            if (afectedrows > 0) {
            } else {
                this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar fondos vendedor");
                con.rollback();
                return false;
            }
            //Comprador
            stmUsuario = con.prepareStatement("update usuarios "
                    + "set fondos = fondos - (select precio*? from tablaactualizacionesprecios where nombrecripto = ?) "
                    + "where id_usuario=? ");
            stmUsuario.setFloat(1, cantidad);
            stmUsuario.setString(2, oferta.getTipo());
            stmUsuario.setString(3, u.getId_usuario());

            afectedrows = stmUsuario.executeUpdate();
            if (afectedrows > 0) {
            } else {
                this.getFachadaAplicacion().muestraExcepcion("No se pudo modificar fondos comprador");
                con.rollback();
                return false;
            }

            stmUsuario = con.prepareStatement("insert into compraventacriptos "
                    + "values (?,?,?,CURRENT_TIMESTAMP, ?, ?)");
            stmUsuario.setString(2, oferta.getId_usuario());
            stmUsuario.setString(1, u.getId_usuario());
            stmUsuario.setString(3, oferta.getTipo());
            stmUsuario.setFloat(4, oferta.getCantidad());
            stmUsuario.setFloat(5, oferta.getPrecio());
            afectedrows = stmUsuario.executeUpdate();
            if (afectedrows > 0) {
            } else {
                this.getFachadaAplicacion().muestraExcepcion("Error insertando tupla compraventa");
                con.rollback();
                return false;
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
}
