/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.Accion;
import aplicacion.Empresa;
import aplicacion.HacerOfertaVenta;
import aplicacion.HacerOfertaVentaCripto;
import aplicacion.IVentana;
import aplicacion.Usuario;
import java.awt.Color;
import static java.lang.Integer.parseInt;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author diego
 */
public class VCompraCriptos extends javax.swing.JDialog {

    aplicacion.FachadaAplicacion fa;
    Usuario usuario;
    IVentana ventana;

    public VCompraCriptos(java.awt.Frame parent, boolean modal, aplicacion.FachadaAplicacion fa, Usuario user, IVentana ventana) {
        super(parent, modal);
        this.fa = fa;
        this.usuario = user;
        this.ventana = ventana;
        initComponents();
        this.getContentPane().setBackground(Color.white);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonComprar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        numeroCompra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textoFiltro = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        botonRefresco = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        botonComprar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/btnGenerales/botonComprarActivo.png"))); // NOI18N
        botonComprar.setBorderPainted(false);
        botonComprar.setContentAreaFilled(false);
        botonComprar.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/btnGenerales/botonComprarDesactivo.png"))); // NOI18N
        botonComprar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonComprarMouseClicked(evt);
            }
        });
        botonComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonComprarActionPerformed(evt);
            }
        });

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre Usuario");
        modelo.addColumn("Nombre Criptos");
        modelo.addColumn("Cantidad Ofertada");
        modelo.addColumn("Precio actual por criptomoneda");
        modelo.addColumn("Fecha");
        jTable1.setModel((TableModel) modelo);
        jTable1.setCellSelectionEnabled(true);
        jScrollPane1.setViewportView(jTable1);
        actualizarTabla("");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Criptos Ofertadas");

        numeroCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeroCompraActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Filtro:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonBuscar.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        botonRefresco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonRefrescar.png"))); // NOI18N
        botonRefresco.setToolTipText("");
        botonRefresco.setBorderPainted(false);
        botonRefresco.setContentAreaFilled(false);
        botonRefresco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRefrescoActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VAdministrador/botonSalir.png"))); // NOI18N
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Número de criptos a comprar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(206, 206, 206))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(textoFiltro)
                                .addGap(21, 21, 21)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonRefresco, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(numeroCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(75, 75, 75)))
                                        .addComponent(botonComprar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(64, 64, 64))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(botonRefresco, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSalir)
                            .addComponent(botonComprar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numeroCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void numeroCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeroCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numeroCompraActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        actualizarTabla(textoFiltro.getText());

    }//GEN-LAST:event_jButton1ActionPerformed

    private void botonComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonComprarActionPerformed

    }//GEN-LAST:event_botonComprarActionPerformed

    private void botonRefrescoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRefrescoActionPerformed
        // TODO add your handling code here:
        actualizarTabla("");
    }//GEN-LAST:event_botonRefrescoActionPerformed

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        this.dispose();
        ventana.onFailure();
    }//GEN-LAST:event_btnSalirMouseClicked

    private void botonComprarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonComprarMouseClicked
        // TODO add your handling code here:
        if (numeroCompra.getText() != null) {
            int Seleccion = jTable1.getSelectedRow();

            HacerOfertaVentaCripto oferta = new HacerOfertaVentaCripto(jTable1.getValueAt(Seleccion, 0).toString(),
                    jTable1.getValueAt(Seleccion, 1).toString(),
                    (Date) jTable1.getValueAt(Seleccion, 4),
                    Float.parseFloat(jTable1.getValueAt(Seleccion, 2).toString()),
                    Float.parseFloat(jTable1.getValueAt(Seleccion, 3).toString()));
            
            try {
                
                Float cantidad = Float.parseFloat(numeroCompra.getText());
                if(cantidad>0){
                    fa.ComprarCriptos(usuario, Float.parseFloat(numeroCompra.getText()), oferta);
                    this.dispose();
                }else{
                                    fa.muestraExcepcion("Introduce un número positivo");
                }
            } catch (NumberFormatException e) {
                fa.muestraExcepcion("Introduce un número");
            }
            ventana.onSuccess();
        } else {
            fa.muestraExcepcion("Introduce una cantidad");
        }

    }//GEN-LAST:event_botonComprarMouseClicked

    private void actualizarTabla(String cripto) {
        DefaultTableModel modelo = new DefaultTableModel();
        Iterator<HacerOfertaVentaCripto> ofertasCriptosIterator = fa.consultarOfertasCriptos(cripto).iterator();
        HacerOfertaVentaCripto auxiliar;
        modelo.addColumn("Nombre Usuario");
        modelo.addColumn("Nombre Criptos");
        modelo.addColumn("Cantidad Ofertada");
        modelo.addColumn("Precio actual por criptomoneda");
        modelo.addColumn("Fecha Publicación");

        while (ofertasCriptosIterator.hasNext()) {
            auxiliar = ofertasCriptosIterator.next();
            if  (!auxiliar.getId_usuario().equals(usuario.getId_usuario())){
                modelo.addRow(new Object[]{auxiliar.getId_usuario(), auxiliar.getTipo(),
                    auxiliar.getCantidad(), auxiliar.getPrecio(), auxiliar.getFechaPublicacion()});

            }

        }

        jTable1.setModel((TableModel) modelo);
        if (modelo.getRowCount() > 0) {
            jTable1.setRowSelectionInterval(0, 0);
            

        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonComprar;
    private javax.swing.JButton botonRefresco;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField numeroCompra;
    private javax.swing.JTextField textoFiltro;
    // End of variables declaration//GEN-END:variables

    
}
