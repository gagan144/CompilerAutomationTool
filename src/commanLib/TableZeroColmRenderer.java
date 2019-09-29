package commanLib;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class TableZeroColmRenderer extends JLabel implements TableCellRenderer
{
    public TableZeroColmRenderer()
    {
        super();
        setOpaque(true);
        
        setBackground(new Color(200,200,200));  // Gray  
        //setBackground(UIManager.getColor(java.awt.SystemColor.DESKTOP));
        //setBackground(UIManager.getColor("Table.selectionBackground"));
    }
  
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        /*
        if (isSelected) 
        {  setBackground(UIManager.getColor("Table.selectionBackground"));   }
        */
        setText(String.valueOf(value));
        return this;
    }
    
}
