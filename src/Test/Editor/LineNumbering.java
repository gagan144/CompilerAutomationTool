package Test.Editor;

import java.awt.Color;
 
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
 
 
public class LineNumbering extends JFrame{
	private static JTextArea jta;
	private static JTextArea lines;
 
	public LineNumbering(){
		super("Line Numbering Example");
	}
 
	public static void createAndShowGUI(){
		JFrame frame = new LineNumbering();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
		JScrollPane jsp = new JScrollPane();
		jta = new JTextArea();
		lines = new JTextArea("1");
 
		lines.setBackground(Color.LIGHT_GRAY);
		lines.setEditable(false);
 
		jta.getDocument().addDocumentListener(new DocumentListener(){
			public String getText(){
				int caretPosition = jta.getDocument().getLength();
				Element root = jta.getDocument().getDefaultRootElement();
				String text = "1" + System.getProperty("line.separator");
				for(int i = 2; i < root.getElementIndex( caretPosition ) + 2; i++){
					text += i + System.getProperty("line.separator");
				}
				return text;
			}
			@Override
			public void changedUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
			@Override
			public void insertUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
			@Override
			public void removeUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
		});
 
		jsp.getViewport().add(jta);
		jsp.setRowHeaderView(lines);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
 
		frame.add(jsp);
		frame.pack();
		frame.setSize(500,500);
		frame.setVisible(true);
	}
 
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}