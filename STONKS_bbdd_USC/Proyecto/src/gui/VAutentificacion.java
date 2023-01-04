package gui;

import aplicacion.TipoUsuario;
import aplicacion.Usuario;
import java.awt.Color;

public class VAutentificacion extends javax.swing.JDialog {

    aplicacion.FachadaAplicacion fa;
    VPrincipal vp;
    VRegistro vr;
    java.awt.Frame parent;
    boolean modal;

    public VAutentificacion(java.awt.Frame parent, boolean modal, aplicacion.FachadaAplicacion fa) {

        super(parent, modal);

        this.fa = fa;
        initComponents();
        this.getContentPane().setBackground(Color.white);
        //Creamos aquí la ventana principal e indicamos que no sea visible
        //vp = new VPrincipal(parent, modal, fa);
        //vp.setVisible(false);
        //Etiqueta de error, en caso de que no se ponga un usuario correcto aparece, desactivada por defecto
        etiquetaFallo.setVisible(false);

        //Código de java swing para que funcione la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        textoID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        registroComoEmpresa = new javax.swing.JLabel();
        etiquetaFallo = new javax.swing.JLabel();
        botonInicio = new javax.swing.JLabel();
        textoContrasena = new javax.swing.JPasswordField();
        botonRegistro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VAutentificacion/BaseLogo.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("ID");

        textoID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoIDActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Contraseña");

        registroComoEmpresa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        registroComoEmpresa.setForeground(new java.awt.Color(255, 153, 0));
        registroComoEmpresa.setText("Registrarme como empresa");
        registroComoEmpresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registroComoEmpresaMouseClicked(evt);
            }
        });

        etiquetaFallo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        etiquetaFallo.setForeground(new java.awt.Color(255, 0, 0));
        etiquetaFallo.setText("! Datos de inicio de sesión incorrectos");

        botonInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VAutentificacion/Boton.png"))); // NOI18N
        botonInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonInicioMouseClicked(evt);
            }
        });

        botonRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VAutentificacion/BotonRegistrarse.png"))); // NOI18N
        botonRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonRegistroMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(etiquetaFallo)
                            .addComponent(textoID)
                            .addComponent(textoContrasena)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botonRegistro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                .addComponent(botonInicio))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(registroComoEmpresa)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(textoID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(textoContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(etiquetaFallo)
                        .addGap(54, 54, 54)
                        .addComponent(botonRegistro))
                    .addComponent(botonInicio))
                .addGap(18, 18, 18)
                .addComponent(registroComoEmpresa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonRegistroMouseClicked
        fa.visualizarRegistro();
    }//GEN-LAST:event_botonRegistroMouseClicked

    private void botonInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonInicioMouseClicked
        this.comprobarAutentificacion();
    }//GEN-LAST:event_botonInicioMouseClicked

    private void registroComoEmpresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registroComoEmpresaMouseClicked
        //Abrir ventana Registro Usuario Empresa
        fa.visualizarRegistroEmpresa();
    }//GEN-LAST:event_registroComoEmpresaMouseClicked

    private void textoIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoIDActionPerformed

    }//GEN-LAST:event_textoIDActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botonInicio;
    private javax.swing.JLabel botonRegistro;
    private javax.swing.JLabel etiquetaFallo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel registroComoEmpresa;
    private javax.swing.JPasswordField textoContrasena;
    private javax.swing.JTextField textoID;
    // End of variables declaration//GEN-END:variables

//Funcion comprobar autentificacion: llama a fachada aplicación a la función de comprobar autentificación
// Si son correctas las credenciales, entra al sistema, sino salta mensaje de fallo y no entra
    public void comprobarAutentificacion() {
        etiquetaFallo.setVisible(false);
        Usuario usuario = null;
        if (!textoID.getText().isEmpty() && !textoContrasena.getText().isEmpty()) {
            usuario = fa.comprobarAutentificacion(textoID.getText(), textoContrasena.getText());
            if (usuario != null) {
                if (usuario.getEnEspera() == -3) {
                    etiquetaFallo.setVisible(true);
                    etiquetaFallo.setText(" Cuenta eliminada");
                } else if (usuario.getEnEspera() == 1) {
                    etiquetaFallo.setVisible(true);
                    etiquetaFallo.setText(" Pendiente de dar de alta");
                } else if (usuario.getEnEspera() == -1) {
                    etiquetaFallo.setVisible(true);
                    etiquetaFallo.setText(" Pendiente de dar de baja");
                } else {
                    //Si el usuario es un inversor
                    if (usuario.getTipoUsuario() == TipoUsuario.inversores) {
                        this.dispose();
                        fa.visualizarVentanaPrincipal(usuario);
                    } //Si el usuario es una empresa
                    else if (usuario.getTipoUsuario() == TipoUsuario.empresas) {
                        this.dispose();
                        fa.visualizarVentanaPrincipal(usuario);
                    } //Si el usuario es el regulador
                    else if (usuario.getTipoUsuario() == TipoUsuario.regulador) {
                        this.dispose();
                        fa.visualizarVentanaPrincipalAdmin();
                    }
                }

            } else {
                etiquetaFallo.setVisible(true);
                etiquetaFallo.setText("! Datos de inicio de sesión incorrectos");
            }
        } else {
            etiquetaFallo.setVisible(true);
            etiquetaFallo.setText("Completa todos los campos");
        }
    }
}
