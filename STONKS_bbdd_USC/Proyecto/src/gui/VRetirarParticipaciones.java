/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.IVentana;
import aplicacion.Usuario;
import java.awt.Color;

/**
 *
 * @author Diego
 */
public class VRetirarParticipaciones extends javax.swing.JDialog {

    private Usuario usuario;
    aplicacion.FachadaAplicacion fa;
    IVentana ventana;
    int cantidad;
    String cif;

    public VRetirarParticipaciones(java.awt.Frame parent, boolean modal, aplicacion.FachadaAplicacion fa, Usuario usuario, IVentana ventana, int cantidad, String cif) {
        super(parent, modal);
        this.fa = fa;
        this.usuario = usuario;
        this.ventana = ventana;
        this.cantidad = cantidad;
        this.cif = cif;
        initComponents();
        this.getContentPane().setBackground(Color.white);

        String a = String.valueOf(cantidad);
        this.criptosEnPosesion.setText(a);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        partAVender = new javax.swing.JTextField();
        botonVender = new javax.swing.JButton();
        BotonSalir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        criptosEnPosesion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("¿Cuantas participaciones quieres dar de baja?");

        botonVender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/btnGenerales/BotonAceptar.png"))); // NOI18N
        botonVender.setBorderPainted(false);
        botonVender.setContentAreaFilled(false);
        botonVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVenderActionPerformed(evt);
            }
        });

        BotonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/btnGenerales/botonCancelar.png"))); // NOI18N
        BotonSalir.setBorderPainted(false);
        BotonSalir.setContentAreaFilled(false);
        BotonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSalirActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("NÚMERO DE PARTICIPACIONES EN POSESIÓN");

        criptosEnPosesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criptosEnPosesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(criptosEnPosesion, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(criptosEnPosesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(partAVender, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonVender, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BotonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(partAVender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonVender)
                    .addComponent(BotonSalir))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVenderActionPerformed
        try {
            int cantidadVender = Integer.parseInt(partAVender.getText());
            if (cantidad >= cantidadVender) {
                fa.retirarParticipaciones(usuario.getId_usuario(), cantidadVender, cif);
                this.dispose();
                ventana.onSuccess();
            } else {
                fa.muestraExcepcion("No se pueden vender más monedas de las que tienes.");
            }
        } catch (Exception e) {
            fa.muestraExcepcion("Valores introducidos incorrectos.");
        }
    }//GEN-LAST:event_botonVenderActionPerformed

    private void BotonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSalirActionPerformed
        this.dispose();
        ventana.onFailure();
    }//GEN-LAST:event_BotonSalirActionPerformed

    private void criptosEnPosesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criptosEnPosesionActionPerformed

    }//GEN-LAST:event_criptosEnPosesionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonSalir;
    private javax.swing.JButton botonVender;
    private javax.swing.JTextField criptosEnPosesion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField partAVender;
    // End of variables declaration//GEN-END:variables
}
