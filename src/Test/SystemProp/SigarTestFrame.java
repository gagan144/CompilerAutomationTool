package Test.SystemProp;

import javax.swing.JTextArea;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;
import javax.swing.JTextArea;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Cpu;


public class SigarTestFrame extends javax.swing.JFrame {
    
    public SigarTestFrame() 
    {
        initComponents();
               
        new MyThread(jTextArea1,progBar);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        progBar = new javax.swing.JProgressBar();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        progBar.setBackground(new java.awt.Color(0, 0, 0));
        progBar.setForeground(new java.awt.Color(0, 204, 0));
        progBar.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(progBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(progBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SigarTestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SigarTestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SigarTestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SigarTestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SigarTestFrame().setVisible(true);
                
                
            }
        });
        
        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea jTextArea1;
    private javax.swing.JProgressBar progBar;
    // End of variables declaration//GEN-END:variables


    
    
}
class MyThread extends Thread
{
    JTextArea textA=null;
    JProgressBar pBar=null;

    public MyThread(JTextArea t,JProgressBar pb) 
    {
        textA=t;
        pBar=pb;
        start();
    }
    
    public void run()
    {
        //--------Variable declarations------------
        
        //--------end of declarations--------------
        
        while(true)
        {
            //-------Write your code here-----------------
            
            /* NOTE :
             * - use textA.setText() instead of System.out.println()
             *   this will display result in textArea instaed of console
             * 
             * - The purpose of this thread is that it will display calculate
             *   and display result in every 20 milliseconds
             * 
             * - Do not Touch anything other than blocks allowed
             * 
             * - You may erase these comments
             */ 
            
            /* Example :
             * long totalCPU = <some Method>
             * long currCPU = <some method>
             * textA.setText("CPU Usage : "+(currCPU/totalCPU*100) );
             * 
             * Do the same for RAM usage
             */
                        
             
            //cpuStats();
           
           getFreeCpuPercentage();
            
            //---------End of your code-------------
           /*
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted Exception in Sleep Thread!");
            }
            */
            textA.setText("");
        }
        
    }
    
    
     private static Logger logger = Logger.getLogger("cpuMonitor");
        
        
        
        public void cpuStats() {
                Sigar session = new Sigar();
                
                try {
                        
                        CpuPerc cpuPerc = session.getCpuPerc();
                        textA.setText("Cpu percentage usage combined: "+cpuPerc.getCombined()*100);
                        textA.setText("Cpu percentage usage system: "+cpuPerc.getSys()*100);
                        //textA.setText("Cpu percentage usage user: "+cpuPerc.getUser()*100);
                        //textA.setText("Free CPU percentage : "+cpuPerc.getIdle()*100);
                        
                        
                } catch (SigarException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

        }
        
        public void getCpuInfo() {
                Sigar session = new Sigar();
                
                try {
                        CpuInfo[] cpuInfoArray = session.getCpuInfoList();
                        textA.setText("CPU MHz: "+cpuInfoArray[0].getMhz());
                        textA.setText("CPU Model "+cpuInfoArray[0].getModel());
                        textA.setText("CPU total cores "+ cpuInfoArray[0].getTotalCores());
                        
                } catch (SigarException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

        }
        
        /**
         * getFreeCpuPercentage() :
         *      Gets the free global cpu percentage of the system. 
         * 
         * @return the value of the free cpu in percentage
         */
        public double getFreeCpuPercentage()
        {
                Sigar session = new Sigar();
                double freeCpu = 0;
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
            simbol.setDecimalSeparator('.');
                DecimalFormat df = new DecimalFormat("##.##",simbol);

                
                try {
                        CpuPerc cpuPerc = session.getCpuPerc();
                        freeCpu = cpuPerc.getIdle()*100;
                        freeCpu = Double.valueOf(df.format(freeCpu)); 
                } catch (SigarException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        textA.setText(e.getMessage());
                }
                //logger.info("CpuMonitor: free cpu percentage: "+ freeCpu);
                //textA.setText(100-freeCpu+"");
                pBar.setValue( (int) (100-freeCpu)  );
                return freeCpu;
                
        }
      
  
}