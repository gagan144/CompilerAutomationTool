/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test.tableTest;

/**
 *
 * @author mini
 */
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * @version 1.0 11/09/98
 */
public class MultiLineCellExample extends JFrame {
  MultiLineCellExample() {
    super("Multi-Line Cell Example");

    DefaultTableModel dm = new DefaultTableModel() {
      public Class getColumnClass(int columnIndex) {
        return String.class;
      }
    };
    dm.setDataVector(new Object[][] { { "a\na", "b\nb", "c\nc" },
        { "A\nA", "B\nB", "C\nC" } }, new Object[] { "1", "2", "3" });

    JTable table = new JTable(dm);

    int lines = 3;
    table.setRowHeight(table.getRowHeight() * lines);

    //
    // table.setRowHeight(0);
    //
    // I got "java.lang.IllegalArgumentException: New row height less than
    // 1"
    //
    table.setDefaultRenderer(String.class, new MultiLineCellRenderer());
    JScrollPane scroll = new JScrollPane(table);
    getContentPane().add(scroll);
    setSize(400, 130);
    setVisible(true);
  }

  public static void main(String[] args) {
    MultiLineCellExample frame = new MultiLineCellExample();
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}

/**
 * @version 1.0 11/09/98
 */

class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

  public MultiLineCellRenderer() {
    setLineWrap(true);
    setWrapStyleWord(true);
    setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(table.getBackground());
    }
    setFont(table.getFont());
    if (hasFocus) {
      setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
      if (table.isCellEditable(row, column)) {
        setForeground(UIManager.getColor("Table.focusCellForeground"));
        setBackground(UIManager.getColor("Table.focusCellBackground"));
      }
    } else {
      setBorder(new EmptyBorder(1, 2, 1, 2));
    }
    setText((value == null) ? "" : value.toString());
    return this;
  }
}
