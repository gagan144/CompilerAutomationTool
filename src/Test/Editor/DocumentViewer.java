package Test.Editor;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

class DocumentViewer {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                try{
              UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
          }catch(Exception e)
          {  System.out.println("Windows isn't available"); }
                
                final JFrame f = new JFrame("Document Viewer");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                final JFileChooser fileChooser = new JFileChooser();

                JPanel gui = new JPanel(new BorderLayout());

                final JTextPane document = new JTextPane();
                //final JEditorPane document = new JEditorPane();
                gui.add(new JScrollPane(document), BorderLayout.CENTER);

                JButton open = new JButton("Open");
                open.addActionListener( new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        int result = fileChooser.showOpenDialog(f);
                        if (result==JFileChooser.APPROVE_OPTION) {
                            File file = fileChooser.getSelectedFile();
                            try {
                                document.setPage(file.toURI().toURL());
                                
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                gui.add(open, BorderLayout.NORTH);

                f.setContentPane(gui);
                f.pack();
                f.setSize(400,300);
                f.setLocationByPlatform(true);

                f.setVisible(true);
            }
        });
    }
}