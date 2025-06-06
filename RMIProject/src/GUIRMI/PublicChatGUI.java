/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUIRMI;

import Controlers.PublicChatControler;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import javax.swing.*;

/**
 *
 * @author igork
 */
public class PublicChatGUI extends javax.swing.JFrame {
    private PublicChatControler controler;
    private static PublicChatGUI instance;
    /**
     * Creates new form GUIPublico
     */
    private PublicChatGUI() {
        controler = new PublicChatControler(this);
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static PublicChatGUI getInstance() {
        if (instance == null) {
            instance = new PublicChatGUI();
        }
        return instance;
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
        cmdSendMessage = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessagesHistory = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmdSendMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUIRMI/Images/SendMessageIcon.png"))); // NOI18N
        cmdSendMessage.setBorder(null);
        cmdSendMessage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSendMessageActionPerformed(evt);
            }
        });
        jPanel1.add(cmdSendMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 480, 40, 40));

        txtMessagesHistory.setEditable(false);
        txtMessagesHistory.setColumns(20);
        txtMessagesHistory.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtMessagesHistory.setRows(5);
        jScrollPane1.setViewportView(txtMessagesHistory);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 660, 350));

        txtMessage.setColumns(20);
        txtMessage.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtMessage.setRows(5);
        jScrollPane2.setViewportView(txtMessage);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 660, 110));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Historial Publico");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSendMessageActionPerformed
        // TODO add your handling code here:
        controler.sendMessage();
    }//GEN-LAST:event_cmdSendMessageActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PublicChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PublicChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PublicChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PublicChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PublicChatGUI().setVisible(true);
            }
        });
    }



    //GETTERS SETTERS

    public JTextArea getTxtMessage() {
        return txtMessage;
    }

    public void setTxtMessage(JTextArea txtMessage) {
        this.txtMessage = txtMessage;
    }

    public JTextArea getTxtMessagesHistory() {
        return txtMessagesHistory;
    }

    public void setTxtMessagesHistory(JTextArea txtMessagesHistory) {
        this.txtMessagesHistory = txtMessagesHistory;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdSendMessage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtMessage;
    private javax.swing.JTextArea txtMessagesHistory;
    // End of variables declaration//GEN-END:variables
}
