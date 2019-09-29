
package Test.Editor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class PopulateTextAreaFromFile extends JFrame {
    public PopulateTextAreaFromFile() {
        initialize();
    }

    private void initialize() {
        setSize(300, 300);
        setTitle("Populate JTextArea from File");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        try {
            //
            // Read some text from the resource file to display in
            // the JTextArea.
            //
            
            JFileChooser chooser = new JFileChooser();
int returnVal = chooser.showOpenDialog(null); //replace null with your swing container
String file="";
if(returnVal == JFileChooser.APPROVE_OPTION)     
{ file = chooser.getSelectedFile().getAbsolutePath();   }
            
            textArea.read(new InputStreamReader(
                    getClass().getResourceAsStream("E:\\ESET-installation-phase2.log")),null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PopulateTextAreaFromFile().setVisible(true);
            }
        });
    }
}