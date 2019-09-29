/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test.tableTest;

/**
 *
 * @author mini
 */
import javax.swing.*;  
import javax.swing.table.*;  
import java.awt.event.*;  
import java.awt.*;  
   
/** 
 * 
 * @author smummane 
 */  
public class TableTest extends JFrame{  
      
    // The table has 10 rows and 3 columns  
    private final JTable table = new JTable(10, 3);  
      
    /** Creates a new instance of TableTest */  
    public TableTest() {  
   
      table.getColumnModel().getColumn(2).setCellRenderer(  
        new TextAreaRenderer());  
   
      table.setRowHeight(0, 50);  
      table.setRowHeight(5, 100);  
   
      getContentPane().add(new JScrollPane(table));  
   
      String test = "The dog jumped over the lazy brown fox";  
      table.getModel().setValueAt(test, 0, 0);  
      table.getModel().setValueAt(test, 0, 1);  
      table.getModel().setValueAt(test, 0, 2);  
      table.getModel().setValueAt(test, 4, 0);  
      table.getModel().setValueAt(test, 4, 1);  
      table.getModel().setValueAt(test, 4, 2);  
      table.doLayout();  
    }  
      
    /** 
     * @param args the command line arguments 
     */  
    public static void main(String[] args) {  
        // TODO code application logic here  
       TableTest test = new TableTest();  
       test.setSize(600, 600);  
       test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
       test.setVisible(true);  
    }  
      
}  
   
class TextAreaRenderer extends JTextArea  
      implements TableCellRenderer {  
   
    public TextAreaRenderer() {  
      setLineWrap(true);  
      setWrapStyleWord(true);  
    }  
   
    public Component getTableCellRendererComponent(JTable jTable,  
        Object obj, boolean isSelected, boolean hasFocus, int row,  
        int column) {  
        setText((String)obj);  
        int height_wanted = (int)getPreferredSize().getHeight();  
        if (height_wanted != jTable.getRowHeight(row))  
        jTable.setRowHeight(row, height_wanted);  
        return this;  
    }  
  }  

