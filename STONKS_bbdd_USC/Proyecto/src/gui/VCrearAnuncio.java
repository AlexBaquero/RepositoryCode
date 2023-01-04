package gui;

import aplicacion.Empresa;
import aplicacion.FachadaAplicacion;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VCrearAnuncio extends javax.swing.JDialog {

    Empresa empresa;
    FachadaAplicacion fa;

    public VCrearAnuncio(java.awt.Frame parent, boolean modal, aplicacion.FachadaAplicacion fa, Empresa empresa) {
        super(parent, modal);
        this.empresa = empresa;
        this.fa = fa;
        initComponents();
        this.getContentPane().setBackground(Color.white);
        textoError.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        informacion = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textoError = new javax.swing.JLabel();
        dateTimePicker1 = new com.github.lgooddatepicker.components.DateTimePicker();
        btnAceptar = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Crear Anuncio");
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel2.setText("DESCRIPCIÓN");
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel3.setText("FECHA DE PAGO");
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        textoError.setText("* textoError");
        textoError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        textoError.setForeground(new java.awt.Color(255, 0, 0));

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/btnGenerales/BotonAceptar.png"))); // NOI18N
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptarMouseClicked(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/btnGenerales/botonCancelar.png"))); // NOI18N
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(informacion, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateTimePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addGap(48, 48, 48)
                        .addComponent(btnAceptar)))
                .addGap(14, 14, 14))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textoError)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(informacion, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(dateTimePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textoError)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        this.crearAnuncio();
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        this.dispose();    }//GEN-LAST:event_btnCancelarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnAceptar;
    private javax.swing.JLabel btnCancelar;
    private com.github.lgooddatepicker.components.DateTimePicker dateTimePicker1;
    private javax.swing.JTextField informacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel textoError;
    // End of variables declaration//GEN-END:variables

    public void crearAnuncio() {
        if (!informacion.getText().isEmpty() && dateTimePicker1.getDateTimePermissive() != null) {
            try {
                Date date;
                
                String pattern = "dd-M-yyyy hh:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date1 = simpleDateFormat.format(new Date());
                Date date2 = null;
                try{
                    date2 =new SimpleDateFormat(pattern).parse(date1);
                    
                    LocalDateTime now = LocalDateTime.now();
                    ZoneId zone = ZoneId.of("Europe/Berlin"); 
                    ZoneOffset zoneOffSet = zone.getRules().getOffset(now);
                    
                    date = Date.from(dateTimePicker1.getDateTimePermissive().toInstant(zoneOffSet));

                    if (date.after(date2)) {
                        fa.crearAnuncio(empresa.getCIF(), date, informacion.getText());

                        fa.muestraExcepcion("Anuncio creado correctamente correctamente.");
                        this.dispose();

                    } else {
                        fa.muestraExcepcion("Fecha introducida no valida. Introduzca una fecha posterior a la actual.");
                    }
                }catch(ParseException e){
                        fa.muestraExcepcion("Error al obtener fechas");
                }
                
            } catch (NumberFormatException e) {
                fa.muestraExcepcion("Alguno de los campos contienen datos no válidos.");
            }
        } else {
            fa.muestraExcepcion("Completa todos los campos.");
        }
    }

}
