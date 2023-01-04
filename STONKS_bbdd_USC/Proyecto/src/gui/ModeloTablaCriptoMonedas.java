package gui;

import aplicacion.Criptomoneda;
import aplicacion.FachadaAplicacion;
import aplicacion.HacerOfertaVenta;
import aplicacion.TipoUsuario;
import aplicacion.Usuario;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author anaca
 */
public class ModeloTablaCriptoMonedas extends AbstractTableModel {

    private java.util.List<Criptomoneda> criptomoneda;

    public void ModeloTablaUsuarios() {
        this.criptomoneda = new java.util.ArrayList<Criptomoneda>();
    }

    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return criptomoneda == null ? 0 : criptomoneda.size();
    }

    @Override
    public String getColumnName(int columnNumber) {
        String nombre = " ";

        switch (columnNumber) {
            case 0:
                nombre = "CriptoMoneda";
                break;
            case 1:
                nombre = "Cantidad";
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
                resultado = criptomoneda.get(row).getNombre();
                break;
            case 1:
                resultado = criptomoneda.get(row).getCantidad();
                break;

        }

        return resultado;
    }

    public void setFilas(java.util.List<Criptomoneda> criptomoneda) {
        this.criptomoneda = criptomoneda;
        fireTableDataChanged();
    }

    public Criptomoneda obtenerCriptoMoneda(int index) {
        return this.criptomoneda.get(index);
    }
    
}