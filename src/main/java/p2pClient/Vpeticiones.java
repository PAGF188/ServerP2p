/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2pClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author pablo
 */
public class Vpeticiones extends javax.swing.JFrame {

    Vin vin;
    
    public Vpeticiones(Vin vin) {
        initComponents();
        this.vin=vin;

        this.setLocationRelativeTo(null);
        this.setResizable(false);

        /*Para salir de la ventana -> volver a vin*/
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    e.getWindow().dispose();
                    vin.setVisible(true);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        this.actualizarPeticiones();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        amigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        enviar = new javax.swing.JButton();
        jPanel4 = new JPanel();
        jPanel3 = new JPanel();
        jPanel2 = new javax.swing.JPanel();
        atras = new javax.swing.JButton();
        error = new javax.swing.JLabel();
        //actualizar = new javax.swing.JButton();

        /*actualizar.setBackground(new java.awt.Color(109, 0, 134));
        actualizar.setFont(new java.awt.Font("Cantarell", 1, 18));
        actualizar.setForeground(new java.awt.Color(254, 254, 254));
        actualizar.setText("Actualizar");
        actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarActionPerformed(evt);
            }
        });*/

        amigo.setFont(new java.awt.Font("Cantarell", 0, 24));
        amigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        amigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                amigoFocusGained(evt);
            }
        });

        error.setBackground(new java.awt.Color(254, 254, 254));
        error.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        error.setForeground(new java.awt.Color(109, 0, 134));
        error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        error.setText(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(23, 138, 28));

        jLabel2.setFont(new java.awt.Font("Cantarell", 1, 17)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(254, 254, 254));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Usuario: ");

        enviar.setBackground(new java.awt.Color(71, 103, 176));
        enviar.setFont(new java.awt.Font("Cantarell", 1, 18));
        enviar.setForeground(new java.awt.Color(254, 254, 254));
        enviar.setText("Enviar");
        enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(amigo, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(60, Short.MAX_VALUE))
                        .addComponent(error, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(amigo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(enviar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(error))
        );

        jPanel2.setBackground(new java.awt.Color(23, 138, 28));

        atras.setBackground(new java.awt.Color(71, 103, 176));
        atras.setFont(new java.awt.Font("Cantarell", 1, 24));
        atras.setForeground(new java.awt.Color(254, 254, 254));
        atras.setText("Atrás");
        atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atrasActionPerformed(evt);
            }
        });




        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(267, 267, 267)
                    .addComponent(atras, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(267, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(atras)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 626, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 273, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(267, 267, 267)
                                //.addComponent(actualizar)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        //.addComponent(actualizar)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*Actualizar peticiones*/

    private void actualizarActionPerformed(java.awt.event.ActionEvent evt) {
        this.actualizarPeticiones();
    }

    /*Enviar peticion*/
    private void enviarActionPerformed(java.awt.event.ActionEvent evt) {
        if(!amigo.getText().equals("")){
            try{
                P2pClient.server.peticionAmistad(P2pClient.yoNombre,amigo.getText());
                error.setText("Petición enviada");
            }catch(Exception e){
                error.setText(e.getMessage());
            }
            amigo.setText(null);
        }
    }

    /*Ir para atras*/
    private void atrasActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        vin.setVisible(true);
    }

    private void amigoFocusGained(java.awt.event.FocusEvent evt) {
        error.setText(null);
    }

    /**
     * Recorremos todas las peticiones de amistad, y las mostramos como JT... con 2 botones aceptar/rechazar
     */
    public void actualizarPeticiones(){
        jPanel4.removeAll();
        int incremento = 20;
        for(String pet: P2pClient.peticionesAmmistad){
            JTextField amigo = new JTextField();
            amigo.setText(pet);
            amigo.setFont(new java.awt.Font("Cantarell", 1, 14));
            amigo.setSize(140,38);
            amigo.setLocation(100, incremento);
            amigo.setEnabled(false);
            amigo.setForeground(new java.awt.Color(0, 0, 0));
            jPanel4.add(amigo);

            /*Botone aceptar*/
            JButton aceptar = new JButton();
            aceptar.setText("Aceptar");
            aceptar.setFont(new java.awt.Font("Cantarell", 1, 14));
            aceptar.setForeground(new java.awt.Color(254, 254, 254));
            aceptar.setBackground( new Color(71,103,176));
            aceptar.setSize(140,38);
            aceptar.setLocation(260, incremento);
            jPanel4.add(aceptar);

            /*Botone rechazar*/
            JButton rechazar = new JButton();
            rechazar.setText("Rechazar");
            rechazar.setFont(new java.awt.Font("Cantarell", 1, 14));
            rechazar.setForeground(new java.awt.Color(254, 254, 254));
            rechazar.setBackground( new Color(71,103,176));
            rechazar.setSize(140,38);
            rechazar.setLocation(420, incremento);
            jPanel4.add(rechazar);

            /*Controlador para aceptar*/
            aceptar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    aceptarActionPerformed(evt,true,pet);
                }
            });

            /*Controlador para rechazar*/
            rechazar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    aceptarActionPerformed(evt,false,pet);
                }
            });
            incremento=incremento+60;
        }
        this.repaint();
    }

    /*Gestiona pulsar en aceptar/rechazar*/
    private void aceptarActionPerformed(java.awt.event.ActionEvent evt, boolean aceptar, String pet) {
        try{
            P2pClient.server.aceptarPeticion(aceptar,pet,P2pClient.yoNombre);
            P2pClient.peticionesAmmistad.remove(pet);
            this.actualizarPeticiones();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    // Variables declaration
    //private javax.swing.JButton actualizar;
    private javax.swing.JButton atras;
    private javax.swing.JButton enviar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField amigo;
    private javax.swing.JLabel error;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration
}
