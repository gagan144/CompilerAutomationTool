package StatStudio;

import commanLib.Diagnosis;
import commanLib.Dialog_ExceptionHandler;
import commanLib.Registration.Dialog_Registration;
import commanLib.Registration.Registration;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class CATStatStudio_Loader extends javax.swing.JWindow 
{       
    public CATStatStudio_Loader() 
    {
        try{
              //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e)
        { 
             //System.out.println("Windows isn't available");               
        }
        
        initComponents();        
        
        this.setLocationRelativeTo(null);    
      
    }
    
    
    public static void main( String args[] )  
    {  
        try
        {
        CATStatStudio_Loader loader= new CATStatStudio_Loader();
        loader.setVisible( true );       
        loader.setAlwaysOnTop(false);
        
        //(1) Verifying installation files
        lbl_load.setText("Verifying installation files...");
        ArrayList<String> missingFiles=Diagnosis.checkFiles();
        if(missingFiles!=null)
        {
            lbl_load.setText("Error! Installation file(s) missing!");
            String msg=" List of missing files :\n";
            for(int i=0;i<missingFiles.size();i++)
            {
                msg+="      "+missingFiles.get(i)+"\n";
            }
            Dialog_ExceptionHandler.autoPopMessageJOptionPane(loader, "Installation Files(s) missing...", "Some of the necessary installation files have been\nfound missing. Please reinstall the application.\nClick 'OK' button to exit.", msg, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        bar(30);
                
        //(2) Verifying registration
        lbl_load.setText("Verifying registration...");
        try{
            Registration reg = Registration.readRegFile();
            lbl_name.setText(reg.firstName+" "+reg.lastName);
            lbl_cmp.setText(reg.company);
        }catch(Exception e)
        {
            if(e.getMessage().equals("0"))
            {
                JOptionPane.showMessageDialog(null,
                        "The registration found on this machine is either obsolete or corrupted."+"\n"+
                        "To resolve this issue, you must register yourself again inorder to use "+"\n"+
                        "this product."+"\n\n"+
                        "One of the possible reason for corrupted registration may be the change"+"\n"+
                        "in system property(s) such as computer name. If you have made such"+"\n"+
                        "changes we regret that you have to register again."+"\n\n"+
                        "However, if you believe that the registration on your machine has been"+"\n"+
                        "tempered without your knowledge, you may contact us on following"+"\n"+
                        "email : singh.gagan144@gmail.com",
                        
                        "Obsolete or corrupted Registration"
                        , JOptionPane.ERROR_MESSAGE
                );
            }
            Dialog_Registration d_reg = new Dialog_Registration(null, true);
            d_reg.setVisible(true);            
        }
        bar(60);
                
        //(3) Preparing to start
        lbl_load.setText("Preparing to start...");
        CATStatStudio APP = new CATStatStudio();        
        bar(90);        
        
        //(4) Done        
        APP.setVisible(true);        
        lbl_load.setText("Done!");
        bar(100);
        loader.dispose();
        
        }catch(Throwable t)
        {
            Dialog_ExceptionHandler d = new Dialog_ExceptionHandler(null, true, "Exception occurred...", t.getMessage(), t);
            d.setVisible(true);
            System.exit(0);
        }
    }  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_exit = new javax.swing.JButton();
        lbl_load = new javax.swing.JLabel();
        pbar_trans = new javax.swing.JLabel();
        pbar = new javax.swing.JLabel();
        lbl_regTo = new javax.swing.JLabel();
        lbl_name = new javax.swing.JLabel();
        lbl_cmp = new javax.swing.JLabel();
        textA_bottom = new javax.swing.JTextArea();
        BG = new javax.swing.JLabel();

        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        setMinimumSize(new java.awt.Dimension(450, 300));
        getContentPane().setLayout(null);

        btn_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/StatStudio/images/exit_BootSc.png"))); // NOI18N
        btn_exit.setToolTipText("Exit");
        btn_exit.setBorderPainted(false);
        btn_exit.setContentAreaFilled(false);
        btn_exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_exit.setFocusPainted(false);
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });
        getContentPane().add(btn_exit);
        btn_exit.setBounds(420, 10, 20, 20);

        lbl_load.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbl_load.setForeground(new java.awt.Color(212, 119, 59));
        lbl_load.setText("Starting CAT Statistic Studio...");
        getContentPane().add(lbl_load);
        lbl_load.setBounds(20, 177, 210, 13);

        pbar_trans.setBackground(new java.awt.Color(212, 119, 59));
        pbar_trans.setOpaque(true);
        getContentPane().add(pbar_trans);
        pbar_trans.setBounds(20, 195, 8, 2);

        pbar.setBackground(new java.awt.Color(255, 173, 7));
        pbar.setOpaque(true);
        getContentPane().add(pbar);
        pbar.setBounds(20, 195, 8, 5);

        lbl_regTo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbl_regTo.setText("Registered to :");
        getContentPane().add(lbl_regTo);
        lbl_regTo.setBounds(20, 210, 70, 13);

        lbl_name.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbl_name.setForeground(new java.awt.Color(0, 102, 0));
        getContentPane().add(lbl_name);
        lbl_name.setBounds(20, 225, 250, 14);

        lbl_cmp.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbl_cmp.setForeground(new java.awt.Color(0, 102, 0));
        getContentPane().add(lbl_cmp);
        lbl_cmp.setBounds(20, 240, 250, 14);

        textA_bottom.setEditable(false);
        textA_bottom.setColumns(20);
        textA_bottom.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        textA_bottom.setForeground(new java.awt.Color(0, 152, 218));
        textA_bottom.setRows(5);
        textA_bottom.setText("© 2013 Gagandeep, Hargeet, Amarpreet, Harpreet\nsingh.gagan144@gmail.com | 9717568636");
        textA_bottom.setOpaque(false);
        getContentPane().add(textA_bottom);
        textA_bottom.setBounds(20, 260, 300, 30);

        BG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/StatStudio/images/Stat_BootSc_BG.jpg"))); // NOI18N
        BG.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 152, 218)));
        getContentPane().add(BG);
        BG.setBounds(0, 0, 450, 300);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btn_exitActionPerformed

    static void bar(int n)
    {
        pbar.setSize(n*4, 5);
        pbar_trans.setSize(n*4, 2);
    }
    
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BG;
    private javax.swing.JButton btn_exit;
    public static javax.swing.JLabel lbl_cmp;
    private static javax.swing.JLabel lbl_load;
    public static javax.swing.JLabel lbl_name;
    private javax.swing.JLabel lbl_regTo;
    private static javax.swing.JLabel pbar;
    private static javax.swing.JLabel pbar_trans;
    private javax.swing.JTextArea textA_bottom;
    // End of variables declaration//GEN-END:variables
}