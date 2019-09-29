package Test;

 import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class InsertColumn{
  DefaultTableModel model;
  JTable table;
  public static void main(String[] args) {
  new InsertColumn();
  }

  public InsertColumn(){
  JFrame frame = new JFrame("Inserting a Column Example!");
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  JPanel panel = new JPanel();
  String data[][] = {{"Vinod","MCA","Computer"},
  {"Deepak","PGDCA","History"},
  {"Ranjan","M.SC.","Biology"},
  {"Radha","BCA","Computer"}};
  String col[] = {"Name","Course","Subject"};
  
  model = new DefaultTableModel(data,col);
  model.addColumn("Marks");
  table = new JTable(model);
  //add column at third position
  int c=table.getColumnCount();
  table.moveColumn(c-1, c-1);
  //positionColumn(table,c-1);
  //JTableHeader header = table.getTableHeader();
  //header.setBackground(Color.yellow);
  JScrollPane pane = new JScrollPane(table);
  panel.add(pane);
  frame.add(panel);
  frame.setUndecorated(true);
  frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
  frame.setSize(600,150);
  frame.setVisible(true);
  }

  public void positionColumn(JTable table,int col_Index) {
  table.moveColumn(table.getColumnCount()-1, col_Index);
  }
}