/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.FachadaAplicacion;
import aplicacion.TipoUsuario;
import aplicacion.Usuario;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author anaca
 */
public class ModeloTablaUsuarios extends AbstractTableModel{ 
    
    private java.util.List<Usuario> usuarios;
    
    public void ModeloTablaUsuarios(){
        this.usuarios = new java.util.ArrayList<Usuario>();
    }
    
    public int getColumnCount(){
        return 5;
    }
    
    @Override
    public int getRowCount(){
        return usuarios == null ? 0 : usuarios.size();
    }
    
    @Override
    public String getColumnName(int columnNumber){
        String nombre = " ";        
        
        switch(columnNumber){
            case 0:
                nombre = "Id";
                break;
            case 1:
                nombre = "Contrasena";
                break;
            case 2:
                nombre = "Fondos";
                break;
            case 3:
                nombre = "Tipo";
                break;
            case 4:
                nombre = "Autorizado";
                break;
             
          
                
        }
        
        return nombre;
    }
    
    @Override
    public Class getColumnClass(int columnNumber){
        Class clase = null;
        
        switch(columnNumber){
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
        }
        return clase;
    }
    
    
    @Override
    public boolean isCellEditable(int row, int col) {
            return false;
    }
    
    public Object getValueAt(int row, int col){
        Object resultado = null;
        
        switch (col) {
            case 0:
                resultado = usuarios.get(row).getId_usuario();
                break;
            case 1:
                resultado = usuarios.get(row).getContrasena();
                break;
            case 2:
                resultado = usuarios.get(row).getFondos();
                break;
            
            case 3:
                if(usuarios.get(row).getTipoUsuario()== TipoUsuario.empresas){
                    resultado = "Empresa";
                }
                else if (usuarios.get(row).getTipoUsuario()== TipoUsuario.inversores){
                    resultado = "Inversor";
                }
                else if (usuarios.get(row).getTipoUsuario()== TipoUsuario.regulador){
                    resultado = "Regulador";
                }
                break;
            case 4:
                
                if(usuarios.get(row).getEnEspera() == 1){
                    resultado = "Dar de alta";
                }
                else if(usuarios.get(row).getEnEspera() == 0){
                    resultado = "Operativo";
                }
                else if(usuarios.get(row).getEnEspera() == -1){
                    resultado = "Dar de baja";
                }
                else if(usuarios.get(row).getEnEspera() == -3){
                    resultado = "Eliminado";
                }
                break;
                
        }
        
        return resultado;
    }
    
    public void setFilas(java.util.List<Usuario> usuarios){
        this.usuarios = usuarios;
        fireTableDataChanged();
    }
    
    public Usuario obtenerUsuario(int index){
        return this.usuarios.get(index);
    }
}

