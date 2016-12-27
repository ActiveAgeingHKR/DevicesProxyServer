package mainserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Gui extends javax.swing.JFrame {

    private Gui gui;
    private MainServer server;
    ServerSocket serverSocket = null;
	Socket clientSocket = null;

    public Gui() {
        super("Proxy Server GUI");
        initComponents();
        //setPortAsLocked(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        portLabel = new javax.swing.JLabel();
        portNumber = new javax.swing.JTextField();
        ChangePort = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        start = new javax.swing.JButton();
        stop = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        portLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        portLabel.setText("Port:");
        portLabel.setPreferredSize(new java.awt.Dimension(27, 22));

        portNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        portNumber.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        portNumber.setPreferredSize(new java.awt.Dimension(34, 22));
        portNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portNumberActionPerformed(evt);
            }
        });

        ChangePort.setText("Change Port");
        ChangePort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangePortActionPerformed(evt);
            }
        });

        lblStatus.setText("Status");

        start.setText("Start");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        stop.setText("Stop");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(portLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(portNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ChangePort, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(start)
                        .addGap(18, 18, 18)
                        .addComponent(stop))
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChangePort)
                    .addComponent(start)
                    .addComponent(stop))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void portNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portNumberActionPerformed

    }//GEN-LAST:event_portNumberActionPerformed

    private void ChangePortActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ChangePortActionPerformed
    {//GEN-HEADEREND:event_ChangePortActionPerformed
        if (Config.setPortNumber(portNumber.getText())) {
            //TODO: stop server and then start it again to read the new port number or leave for the new buttons
            lblStatus.setText("Port has been Changed to:" + Config.getPortNumber());
        } else {
            //fail to change the port number
            lblStatus.setText("Fail to change port number:" + Config.getPortNumber());

        }
    }//GEN-LAST:event_ChangePortActionPerformed

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        
          // TODO add your handling code here:
        
           // lblStatus.setText("Existing port is :" + Config.getPortNumber());
        while(true){
           new ProxyThread(Server.getInstance().establishContact()).start();
        }
        
        

    }//GEN-LAST:event_startActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        
            // TODO add your handling code here:

           
            
        
       
    }//GEN-LAST:event_stopActionPerformed

//    public void setPortAsLocked(boolean x) {
//        listen.setSelected(x);
//        portNumber.setEditable(!x);
//
//    }
//
//     public void registerButtonListener(ActionListener Listen) {
//        listen.addActionListener(Listen);
//       // sendButton.addActionListener(listen);
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChangePort;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField portNumber;
    private javax.swing.JButton start;
    private javax.swing.JButton stop;
    // End of variables declaration//GEN-END:variables
}
