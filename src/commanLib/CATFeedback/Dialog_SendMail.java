package commanLib.CATFeedback;

import commanLib.Dialog_ExceptionHandler;
import java.awt.Color;
import javax.swing.JOptionPane;

public class Dialog_SendMail extends javax.swing.JDialog 
{
    private final String FROM;       
    private final String SUBJECT;
    private final String MESSAGE_BODY;    
    private final int MSG_TYPE;
    
    private final String SEND_MSG_1 = "Your message has been send successfully!\n"+
                                      "Thank you for spending your valuable time and\n"+
                                      "providing us your views. Your feedback is the\n"+
                                      "most important asset of our project. We hope\n"+
                                      "you will continue providing your views whenever\n"+
                                      "required!";
    
    private final String SEND_MSG_2 = "Your message has been send successfully!\n"+
                                      "Thank you for contacting us.";


    public Dialog_SendMail(java.awt.Frame parent, boolean modal,int msgType,String FROM,String SUBJECT,String MESSAGE_BODY) 
    {
        super(parent, modal);
        this.FROM=FROM;
        this.SUBJECT=SUBJECT;
        this.MESSAGE_BODY=MESSAGE_BODY;
        MSG_TYPE=msgType;
        
        initComponents();
        this.setLocationRelativeTo(null); 
        
        if(MSG_TYPE==CATFeedback.REPORT)
        { checkBox_UseDefault.setEnabled(true); }
        else
        { checkBox_UseDefault.setEnabled(false); }  
        textF_email.setText(FROM);
        
        popup_MoreInfo.add(scrollPn_textA_moreInfo); 
        
        //DELETE 
        checkBox_UseDefault.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popup_MoreInfo = new javax.swing.JPopupMenu();
        scrollPn_textA_moreInfo = new javax.swing.JScrollPane();
        textA_moreInfo = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textF_email = new javax.swing.JTextField();
        passF_pass = new javax.swing.JPasswordField();
        btn_send = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();
        checkBox_UseDefault = new javax.swing.JCheckBox();
        btn_moreInfo = new javax.swing.JButton();

        popup_MoreInfo.setMaximumSize(new java.awt.Dimension(435, 284));
        popup_MoreInfo.setMinimumSize(new java.awt.Dimension(435, 284));

        scrollPn_textA_moreInfo.setPreferredSize(new java.awt.Dimension(435, 284));

        textA_moreInfo.setEditable(false);
        textA_moreInfo.setColumns(20);
        textA_moreInfo.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        textA_moreInfo.setLineWrap(true);
        textA_moreInfo.setRows(5);
        textA_moreInfo.setText("First of all we would like to thank you for spending your precious time for providing us with your feedback. However, we require another favor from your side. At this stage you are asked to enter the password for your specified email id in order to authenticate your account. This step is mandatory and cannot be skipped in order to complete the process. There are certain reasons for this requirement.\n\nAs you enter your details and proceed for submission, you are required to send all these details to us via email facility. In order to do so, this mail must be send from a valid account. Hence, it becomes necessary to authenticate your account by entering the password so that the mail being send has a legal source. As you authenticate yourself through your email id and password, a mail will be automatically composed from your email with all the summary of details entered by you in a formatted form and send to us on the following mail: singh.gagan144@gmail.com. Moreover, you may find the copy of this mail in your ‘Send Mail’ option of your account.\n\nHere we would like to say and guarantee that your PASSWORD WILL REMAIN YOUR CONFIDENTIAL ASSET AND WILL NOT BE EXPOSED TO US OR ANYONE ELSE IN ANY FORM. You may cross check this by viewing a copy of this mail from your ‘Send Mail’ option. \n\nIf you still feel insecure, you must know that this mailing process has been accomplished using JavaMail API 1.4.7 Library. You may go through the documentation of JavaMail API 1.4.7 by surfing on internet or visit the link http://www.oracle.com/technetwork/java/index-138643.html. \n\nMoreover, if you are still not convinced, you may simply compose a mail yourself with all the details and send to us.\n\nPlease feel free to authenticate yourself as we guarantee that your password will always remain your personal confidential asset. \n\nRegards\nSupport Team\nsingh.gagan144@gmail.com\n");
        textA_moreInfo.setWrapStyleWord(true);
        scrollPn_textA_moreInfo.setViewportView(textA_moreInfo);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Email Authentication");

        jLabel2.setText("Email Id :");

        jLabel3.setText("Password :");

        textF_email.setEditable(false);

        btn_send.setText("Send");
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });

        btn_cancel.setText("Cancel");
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        checkBox_UseDefault.setText("Use Default Settings");
        checkBox_UseDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox_UseDefaultActionPerformed(evt);
            }
        });

        btn_moreInfo.setForeground(new java.awt.Color(0, 51, 255));
        btn_moreInfo.setText("Why need authentication ?");
        btn_moreInfo.setBorderPainted(false);
        btn_moreInfo.setContentAreaFilled(false);
        btn_moreInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_moreInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_moreInfoActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addComponent(checkBox_UseDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textF_email)
                            .addComponent(passF_pass)
                            .addComponent(btn_moreInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 14, Short.MAX_VALUE)
                        .addComponent(btn_send, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passF_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_moreInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBox_UseDefault))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_send)
                    .addComponent(btn_cancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendActionPerformed
        // TODO add your handling code here:        
        String from=null,pass=null;
        if(!checkBox_UseDefault.isSelected())
        {
            if(passF_pass.getText().equals(""))
            { passF_pass.setBackground(new Color(255,185,185));  return; }
            from=FROM;
            pass=passF_pass.getText();
        }
                
        //System.out.println("from="+from+" , pass="+pass);
        EmailHandler email = new EmailHandler(from, pass, SUBJECT, MESSAGE_BODY);
        //System.out.println(email.toString());
        try
        {
            email.sendMail();
            //System.out.println("Your message send!");
            dispose();
            
            if(MSG_TYPE==CATFeedback.REPORT || MSG_TYPE==CATFeedback.SUGGESTION)
            { JOptionPane.showMessageDialog(this, SEND_MSG_1,"Messege Send!", JOptionPane.INFORMATION_MESSAGE); }
            else
            { JOptionPane.showMessageDialog(this, SEND_MSG_2,"Messege Send!", JOptionPane.INFORMATION_MESSAGE); }
            
        }catch(Exception ex)
        { 
            String msg="",title="";
            try{
                int expNo=Integer.parseInt(ex.getMessage());
                switch(expNo)
                {
                    case EmailHandler.AUTH_EXCPTN : 
                         {
                             title="Authentication Error!";
                             msg="Please verify your Email Id and Password.\nMake sure you are using a valid Gmail Id!";
                         }break;
                    case EmailHandler.CONNECT_EXCPTN :
                         {
                             title="Internet Connectivity Error!";
                             msg="Please make sure you are connected to internet.\nCheck your modem sockets or internet\nconnection settings and try again!";
                         }break;
                }
            }catch(Exception e)
            {
                title="Error Sending Mail!";
                msg="An error occured sending email.\nPlease try again later!";
            }
            
            JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();             
            //Dialog_ExceptionHandler.autoPopJOptionPane(this, "Error Sending Message...", e.getMessage(), e, JOptionPane.ERROR_MESSAGE);            
        }
    }//GEN-LAST:event_btn_sendActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void checkBox_UseDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBox_UseDefaultActionPerformed
        // TODO add your handling code here:
        if(checkBox_UseDefault.isSelected())
        {
            textF_email.setText(EmailHandler.CAT_EMAIL_ID);
            passF_pass.setText("");
            passF_pass.setBackground(new Color(240,240,240));
            passF_pass.setEnabled(false);            
        }
        else
        {
            textF_email.setText(FROM);
            passF_pass.setBackground(new Color(255,255,255));
            passF_pass.setEnabled(true);
        }
    }//GEN-LAST:event_checkBox_UseDefaultActionPerformed

    private void btn_moreInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_moreInfoActionPerformed
        // TODO add your handling code here:
        if(!popup_MoreInfo.isShowing())
        {
            popup_MoreInfo.show(this, btn_moreInfo.getLocation().x, btn_moreInfo.getLocation().y);
        }
    }//GEN-LAST:event_btn_moreInfoActionPerformed

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
            java.util.logging.Logger.getLogger(Dialog_SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dialog_SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dialog_SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dialog_SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dialog_SendMail dialog = new Dialog_SendMail(new javax.swing.JFrame(),true,1,null,null,null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_moreInfo;
    private javax.swing.JButton btn_send;
    private javax.swing.JCheckBox checkBox_UseDefault;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField passF_pass;
    private javax.swing.JPopupMenu popup_MoreInfo;
    private javax.swing.JScrollPane scrollPn_textA_moreInfo;
    private javax.swing.JTextArea textA_moreInfo;
    private javax.swing.JTextField textF_email;
    // End of variables declaration//GEN-END:variables
}
