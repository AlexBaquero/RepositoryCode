/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.FachadaAplicacion;
import aplicacion.HacerOfertaVenta;
import aplicacion.TipoUsuario;
import aplicacion.Usuario;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author anaca
 */
public class ModeloTablaOfertaVenta extends AbstractTableModel {

    private java.util.List<HacerOfertaVenta> ofertaventa;

    public void ModeloTablaUsuarios() {
        this.ofertaventa = new java.util.ArrayList<HacerOfertaVenta>();
    }

    public int getColumnCount() {
        return 7;
    }

    @Override
    public int getRowCount() {
        return ofertaventa == null ? 0 : ofertaventa.size();
    }

    @Override
    public String getColumnName(int columnNumber) {
        String nombre = " ";

        switch (columnNumber) {
            case 0:
                nombre = "Precio";
                break;
            case 1:
                nombre = "FechaOferta";
                break;
            case 2:
                nombre = "Número de participaciones";
                break;
            case 3:
                nombre = "Disponibilidad";
                break;
            case 4:
                nombre = "Empresa";
                break;
            case 5:
                nombre = "CIF";
                break;
            case 6:
                nombre = "IDEmpresa";
                break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int columnNumber) {
        Class clase = null;

        switch (columnNumber) {
            case 0:
                clase = java.lang.String.class;
                break;
            case 1:
                clase = java.lang.String.class;
                break;
            case 2:
                clase = java.lang.Integer.class;
                break;
            case 3:
                clase = java.lang.String.class;
                break;
            case 4:
                clase = java.lang.String.class;
                break;
            case 5:
                clase = java.lang.String.class;
                break;
            case 6:
                clase = java.lang.String.class;
                break;
        }
        return clase;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Object getValueAt(int row, int col) {
        Object resultado = null;

        switch (col) {
            case 0:
                resultado = ofertaventa.get(row).getPrecio();
                break;
            case 1:
                resultado = ofertaventa.get(row).getFechaOferta();
                break;
            case 2:
                resultado = ofertaventa.get(row).getNumeroParticipaciones();
                break;
            case 3:
                resultado = ofertaventa.get(row).isDisponibilidad();
                break;
            case 4:
                resultado = ofertaventa.get(row).getEmpresa().getNombreComercial();
                break;
            case 5:
                resultado = ofertaventa.get(row).getEmpresa().getCIF();
                break;
            case 6:
                resultado = ofertaventa.get(row).getEmpresa().getUsuario().getId_usuario();
                break;

        }

        return resultado;
    }

    public void setFilas(java.util.List<HacerOfertaVenta> ofertaventa) {
        this.ofertaventa = ofertaventa;
        fireTableDataChanged();
    }

    public HacerOfertaVenta obtenerOfertaVenta(int index) {
        return this.ofertaventa.get(index);
    }
}
