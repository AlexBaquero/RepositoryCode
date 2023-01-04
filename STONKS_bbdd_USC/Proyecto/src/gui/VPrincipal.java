package gui;

import aplicacion.Accion;
import aplicacion.Empresa;
import aplicacion.IVentana;
import aplicacion.Inversor;
import aplicacion.TipoUsuario;
import aplicacion.Usuario;
import java.awt.Color;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class VPrincipal extends javax.swing.JFrame {

    aplicacion.FachadaAplicacion fa;
    private VPrincipal vp;
    Usuario usuario;

    public VPrincipal(java.awt.Frame parent, boolean modal, aplicacion.FachadaAplicacion fa, Usuario user) {
        this.fa = fa;
        vp = this.vp;
        this.usuario = user;
        initComponents();
        this.getContentPane().setBackground(Color.white);
        txtBienvenida.setText("Hola " + user.getId_usuario());
        textoSaldo.setText("Saldo: " + usuario.getFondos() + " €");

        actualizarTabla("");

        if (usuario.getTipoUsuario() == TipoUsuario.inversores) {
            btnGenerarAcciones.setVisible(false);
            iconoGenerarAcciones.setVisible(false);
            iconoCrearAnuncio.setVisible(false);
            btnAnuncio.setVisible(false);
            iconoPagoDividendos.setVisible(false);
            btnPagoDividendos.setVisible(false);
            btnRetirarAcciones.setVisible(false);
       }

    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        Perfil1 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        textoFiltrado = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnBuscar = new javax.swing.JLabel();
        btnRefrescar = new javax.swing.JLabel();
        txtBienvenida = new javax.swing.JLabel();
        textoSaldo = new javax.swing.JLabel();
        btnOfertaVenta = new javax.swing.JLabel();
        btnRetirarAcciones = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JLabel();
        btnMiPerfil = new javax.swing.JLabel();
        iconoUser = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnCriptomonedas = new javax.swing.JLabel();
        iconoCrearAnuncio = new javax.swing.JLabel();
        btnAnuncio = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnComprarAcciones = new javax.swing.JLabel();
        btnGenerarAcciones = new javax.swing.JLabel();
        iconoGenerarAcciones = new javax.swing.JLabel();
        btnPagoDividendos = new javax.swing.JLabel();
        iconoPagoDividendos = new javax.swing.JLabel();
        iconoOfertas = new javax.swing.JLabel();
        btnOfertasActivas = new javax.swing.JLabel();

        jMenuItem1.setText("jMenuItem1");

        jMenu1.setText("jMenu1");

        Perfil1.setText("Mi Perfil");
        Perfil1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Perfil1MouseClicked(evt);
            }
        });
        jMenu1.add(Perfil1);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        textoFiltrado.setForeground(new java.awt.Color(102, 102, 102));

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Empresa");
        modelo.addColumn("Cantidad Acciones");
        modelo.addColumn("Fecha última operación");
        jTable1.setModel((TableModel) modelo);
        jScrollPane2.setViewportView(jTable1);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonBuscar.png"))); // NOI18N
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });

        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonRefrescar.png"))); // NOI18N
        btnRefrescar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefrescarMouseClicked(evt);
            }
        });

        txtBienvenida.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtBienvenida.setText("Hola (usuario)");

        textoSaldo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        textoSaldo.setText("Saldo:");

        btnOfertaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonOfertaVenta.png"))); // NOI18N
        btnOfertaVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOfertaVentaMouseClicked(evt);
            }
        });

        btnRetirarAcciones.setBackground(new java.awt.Color(255, 255, 255));
        btnRetirarAcciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/btnGenerales/botonRetirarParticipaciones.png"))); // NOI18N
        btnRetirarAcciones.setBorderPainted(false);
        btnRetirarAcciones.setContentAreaFilled(false);
        btnRetirarAcciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRetirarAccionesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtBienvenida)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textoSaldo)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnOfertaVenta)
                                    .addGap(324, 324, 324)
                                    .addComponent(btnRetirarAcciones, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(textoFiltrado, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnBuscar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnRefrescar)))))
                        .addGap(0, 45, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRetirarAcciones)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtBienvenida)
                        .addGap(18, 18, 18)
                        .addComponent(textoSaldo)
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRefrescar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textoFiltrado, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(btnOfertaVenta)))
                .addGap(71, 71, 71))
        );

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonSalir.png"))); // NOI18N

        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirMouseClicked(evt);
            }
        });

        btnMiPerfil.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMiPerfil.setForeground(new java.awt.Color(255, 255, 255));
        btnMiPerfil.setText("Mi Perfil");
        btnMiPerfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMiPerfilMouseClicked(evt);
            }
        });

        iconoUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonMiPerfil.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonCriptomonedas.png"))); // NOI18N

        btnCriptomonedas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCriptomonedas.setForeground(new java.awt.Color(255, 255, 255));
        btnCriptomonedas.setText("Criptomonedas");
        btnCriptomonedas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCriptomonedasMouseClicked(evt);
            }
        });

        iconoCrearAnuncio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonAnuncio.png"))); // NOI18N

        btnAnuncio.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAnuncio.setForeground(new java.awt.Color(255, 255, 255));
        btnAnuncio.setText("Crear Anuncio");
        btnAnuncio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnuncioMouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonComprarAcciones.png"))); // NOI18N

        btnComprarAcciones.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnComprarAcciones.setForeground(new java.awt.Color(255, 255, 255));
        btnComprarAcciones.setText("Comprar Acciones");
        btnComprarAcciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComprarAccionesMouseClicked(evt);
            }
        });

        btnGenerarAcciones.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnGenerarAcciones.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarAcciones.setText("Generar Acciones");
        btnGenerarAcciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGenerarAccionesMouseClicked(evt);
            }
        });

        iconoGenerarAcciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonGenerarAcciones.png"))); // NOI18N

        btnPagoDividendos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnPagoDividendos.setForeground(new java.awt.Color(255, 255, 255));
        btnPagoDividendos.setText("Pago Dividendos");
        btnPagoDividendos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPagoDividendosMouseClicked(evt);
            }
        });

        iconoPagoDividendos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonPagoBeneficios.png"))); // NOI18N

        iconoOfertas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/VPrincipal/botonOfertas.png"))); // NOI18N

        btnOfertasActivas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnOfertasActivas.setForeground(new java.awt.Color(255, 255, 255));
        btnOfertasActivas.setText("Mis Ofertas Activas");
        btnOfertasActivas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOfertasActivasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(iconoUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSalir)
                            .addComponent(btnMiPerfil)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(iconoCrearAnuncio))
                                    .addComponent(iconoGenerarAcciones)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel6))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(iconoOfertas)))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnOfertasActivas)
                                    .addComponent(btnCriptomonedas)
                                    .addComponent(btnAnuncio)
                                    .addComponent(btnGenerarAcciones)
                                    .addComponent(btnComprarAcciones)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(iconoPagoDividendos)
                                .addGap(20, 20, 20)
                                .addComponent(btnPagoDividendos)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconoPagoDividendos)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnPagoDividendos)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconoCrearAnuncio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(btnAnuncio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(iconoGenerarAcciones)
                    .addComponent(btnGenerarAcciones))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnComprarAcciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(btnCriptomonedas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOfertasActivas)
                    .addComponent(iconoOfertas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconoUser)
                    .addComponent(btnMiPerfil))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(btnSalir))
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /*
    private void BotonOfertaVentaActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        fa.visualizarVOfertaVenta(usuario);
    }                                                
     */
    private void Perfil1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Perfil1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Perfil1MouseClicked

    private void menuCriptomonedasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCriptomonedasMouseClicked
        //fa.visualizarVCriptoMonedas(usuario);
    }//GEN-LAST:event_menuCriptomonedasMouseClicked

    private void btnOfertaVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOfertaVentaMouseClicked
        int filaSeleccionada = jTable1.getSelectedRow();

        if (filaSeleccionada != -1) {
            String idempresa = jTable1.getValueAt(filaSeleccionada, 0).toString();
            String a = jTable1.getValueAt(filaSeleccionada, 1).toString();
            fa.visualizarVOfertaVenta(usuario, idempresa, a);
        } else {
            fa.muestraExcepcion("Selecciona una fila de la tabla");
        }

    }//GEN-LAST:event_btnOfertaVentaMouseClicked

    private void btnGenerarAccionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerarAccionesMouseClicked
        fa.visualizarSeleccionAcciones(usuario);
    }//GEN-LAST:event_btnGenerarAccionesMouseClicked

    private void btnComprarAccionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComprarAccionesMouseClicked
        IVentana iventana = new IVentana() {
            @Override
            public void onSuccess() {
                actualizarTabla("");
            }
        };
        fa.visualizarVentanaCompraAcciones(usuario, iventana);
    }//GEN-LAST:event_btnComprarAccionesMouseClicked

    private void btnAnuncioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnuncioMouseClicked
        Empresa empresa = fa.consultarUnaEmpresa(usuario);
        fa.visualizarCrearAnuncio(empresa);
    }//GEN-LAST:event_btnAnuncioMouseClicked

    private void btnCriptomonedasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCriptomonedasMouseClicked
        IVentana iVentana = new IVentana() {
            @Override
            public void onSuccess() {
                actualizarTabla("");
            }
        };
        fa.visualizarVCriptoMonedas(usuario, iVentana);
    }//GEN-LAST:event_btnCriptomonedasMouseClicked

    private void btnMiPerfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMiPerfilMouseClicked
        
        if (usuario.getTipoUsuario() == TipoUsuario.inversores) {
            Inversor inversor = fa.consultarUnInversor(usuario);
            this.dispose();
            if(inversor != null){
                fa.visualizarMiPerfil(inversor);
            }
            
        } else if (usuario.getTipoUsuario() == TipoUsuario.empresas) {
            Empresa empresa = fa.consultarUnaEmpresa(usuario);
            this.dispose();
            fa.visualizarPerfilEmpresa(empresa,1);
    }//GEN-LAST:event_btnMiPerfilMouseClicked
    }
    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        this.dispose();
        fa.iniciaInterfazUsuario();
    }//GEN-LAST:event_btnSalirMouseClicked

    private void btnRefrescarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefrescarMouseClicked
        actualizarTabla("");
    }//GEN-LAST:event_btnRefrescarMouseClicked

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        actualizarTabla(textoFiltrado.getText());
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void btnPagoDividendosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagoDividendosMouseClicked
        Empresa empresa = this.fa.consultarUnaEmpresa(usuario);
        fa.visualizarPagosDividendos(empresa);
        actualizarTabla("");
    }//GEN-LAST:event_btnPagoDividendosMouseClicked

    private void btnOfertasActivasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOfertasActivasMouseClicked
        fa.visualizarHistorialOfertaVenta(usuario);
    }//GEN-LAST:event_btnOfertasActivasMouseClicked

    private void btnRetirarAccionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRetirarAccionesMouseClicked
        int seleccion = jTable1.getSelectedRow();
        
        if  (jTable1.getValueAt(seleccion, 0).toString().equals(usuario.getId_usuario())){
            IVentana iventana = new IVentana() {
                @Override
                public void onSuccess() {
                    actualizarTabla("");
                }
            };
            fa.visualizarVRetirarParticipaciones(usuario, iventana, Integer.parseInt(jTable1.getValueAt(seleccion, 1).toString()), fa.consultarUnaEmpresa(usuario).getCIF() );
        }
    }//GEN-LAST:event_btnRetirarAccionesMouseClicked

    /**/
    //Vaciar tabla
    private void vaciarTabla() {
        int rowCount = jTable1.getModel().getRowCount();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
        jTable1.setModel(modelo);
    }

    //Actualiza el contenido de la tabla
    private void actualizarTabla(String cadenaContenida) {
        DefaultTableModel modelo = new DefaultTableModel();
        Iterator<Accion> accionesIterator = fa.accionesUsuarios(usuario.getId_usuario()).iterator();
        Accion auxiliar;
        modelo.addColumn("Empresa");
        modelo.addColumn("Cantidad Acciones");
        modelo.addColumn("Fecha última operación");
        while (accionesIterator.hasNext()) {
            auxiliar = accionesIterator.next();
            if (auxiliar.getIdEmpresa().contains(cadenaContenida)) {
                modelo.addRow(new Object[]{auxiliar.getIdEmpresa(), auxiliar.getCantidad(),
                    auxiliar.getFecha()});
            }
        }
        jTable1.setModel((TableModel) modelo);
        if (modelo.getRowCount() > 0) {
            jTable1.setRowSelectionInterval(0, 0);
        }

        textoSaldo.setText("Saldo: " + fa.consultarUnUsuario(usuario.getId_usuario()).getFondos() + " €");

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Perfil1;
    private javax.swing.JLabel btnAnuncio;
    private javax.swing.JLabel btnBuscar;
    private javax.swing.JLabel btnComprarAcciones;
    private javax.swing.JLabel btnCriptomonedas;
    private javax.swing.JLabel btnGenerarAcciones;
    private javax.swing.JLabel btnMiPerfil;
    private javax.swing.JLabel btnOfertaVenta;
    private javax.swing.JLabel btnOfertasActivas;
    private javax.swing.JLabel btnPagoDividendos;
    private javax.swing.JLabel btnRefrescar;
    private javax.swing.JButton btnRetirarAcciones;
    private javax.swing.JLabel btnSalir;
    private javax.swing.JLabel iconoCrearAnuncio;
    private javax.swing.JLabel iconoGenerarAcciones;
    private javax.swing.JLabel iconoOfertas;
    private javax.swing.JLabel iconoPagoDividendos;
    private javax.swing.JLabel iconoUser;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField textoFiltrado;
    private javax.swing.JLabel textoSaldo;
    private javax.swing.JLabel txtBienvenida;
    // End of variables declaration//GEN-END:variables
}
