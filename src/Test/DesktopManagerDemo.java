package Test;

import java.beans.PropertyVetoException;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class DesktopManagerDemo extends JFrame implements ActionListener{
  protected int m_count;
  protected int m_tencount;
  protected JButton m_newFrame;
  protected JDesktopPane m_desktop;
  protected JComboBox m_UIBox;
  protected UIManager.LookAndFeelInfo[] m_infos;
  protected JLabel m_lActivates, m_lBegindrags, m_lBeginresizes, m_lCloses, 
    m_lDeactivates, m_lDeiconifies, m_lDrags, m_lEnddrags, m_lEndresizes,
    m_lIconifies, m_lMaximizes, m_lMinimizes, m_lOpens, m_lResizes, m_lSetbounds;
  protected MyDesktopManager m_myDesktopManager;
  protected DMEventCanvas m_dmEventCanvas;
  protected Timer m_eventTimer;
    
  public DesktopManagerDemo() {
    setTitle("Animated DesktopManager");
    m_count = m_tencount = 0;

    JPanel innerListenerPanel = new JPanel(new GridLayout(15,1));
    JPanel listenerPanel = new JPanel(new BorderLayout());
    m_dmEventCanvas = new DMEventCanvas();
        
    m_lActivates = new JLabel("activateFrame");
    m_lBegindrags = new JLabel("beginDraggingFrame");
    m_lBeginresizes = new JLabel("beginResizingFrame");
    m_lCloses = new JLabel("closeFrame");
    m_lDeactivates = new JLabel("deactivateFrame");
    m_lDeiconifies = new JLabel("deiconifyFrame");
    m_lDrags = new JLabel("dragFrame");
    m_lEnddrags = new JLabel("endDraggingFrame");
    m_lEndresizes = new JLabel("endResizingFrame");
    m_lIconifies = new JLabel("iconifyFrame");
    m_lMaximizes = new JLabel("maximizeFrame");
    m_lMinimizes = new JLabel("minimizeFrame");
    m_lOpens = new JLabel("openFrame");
    m_lResizes = new JLabel("resizeFrame");
    m_lSetbounds = new JLabel("setBoundsForFrame");

    innerListenerPanel.add(m_lActivates);
    innerListenerPanel.add(m_lBegindrags);
    innerListenerPanel.add(m_lBeginresizes);
    innerListenerPanel.add(m_lCloses);
    innerListenerPanel.add(m_lDeactivates);
    innerListenerPanel.add(m_lDeiconifies);
    innerListenerPanel.add(m_lDrags);
    innerListenerPanel.add(m_lEnddrags);
    innerListenerPanel.add(m_lEndresizes);
    innerListenerPanel.add(m_lIconifies);
    innerListenerPanel.add(m_lMaximizes);
    innerListenerPanel.add(m_lMinimizes);
    innerListenerPanel.add(m_lOpens);
    innerListenerPanel.add(m_lResizes);
    innerListenerPanel.add(m_lSetbounds);

    listenerPanel.add("Center", m_dmEventCanvas);
    listenerPanel.add("West", innerListenerPanel);
    listenerPanel.setOpaque(true);
    listenerPanel.setBackground(Color.white);

    m_myDesktopManager = new MyDesktopManager();
    m_desktop = new JDesktopPane();
    m_desktop.setDesktopManager(m_myDesktopManager);
    m_desktop.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
    m_newFrame = new JButton("New Frame");
    m_newFrame.addActionListener(this);
    m_infos = UIManager.getInstalledLookAndFeels();
    String[] LAFNames = new String[m_infos.length];
    for(int i=0; i<m_infos.length; i++) {
      LAFNames[i] = m_infos[i].getName();
    }
    m_UIBox = new JComboBox(LAFNames);
    m_UIBox.addActionListener(this);
    JPanel topPanel = new JPanel(true);
    topPanel.setLayout(new FlowLayout());
    topPanel.setBorder(new CompoundBorder(
      new SoftBevelBorder(BevelBorder.LOWERED),
      new CompoundBorder(new EmptyBorder(2,2,2,2),
        new SoftBevelBorder(BevelBorder.RAISED))));
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add("North", topPanel);
    getContentPane().add("Center", m_desktop);
    getContentPane().add("South", listenerPanel);
    ((JPanel) getContentPane()).setBorder(new CompoundBorder(
      new SoftBevelBorder(BevelBorder.LOWERED),
      new CompoundBorder(new EmptyBorder(1,1,1,1),
        new SoftBevelBorder(BevelBorder.RAISED))));
    topPanel.add(m_newFrame);
    topPanel.add(new JLabel("Look & Feel:",SwingConstants.RIGHT));
    topPanel.add(m_UIBox);
    setSize(645,600);
    Dimension dim = getToolkit().getScreenSize();
    setLocation(dim.width/2-getWidth()/2,
      dim.height/2-getHeight()/2);
    setVisible(true);
    WindowListener l = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    };       
    addWindowListener(l);
    m_eventTimer = new Timer(1000, this);
    m_eventTimer.setRepeats(true);
    m_eventTimer.start();
  }

  public void newFrame() {
    JInternalFrame jif = new JInternalFrame("Frame " + m_count, 
      true, true, true, true);
    jif.setBounds(20*(m_count%10) + m_tencount*80, 
      20*(m_count%10), 200, 200);
    JLabel label = new JLabel();
    label.setBackground(Color.white);
    label.setOpaque(true);
    jif.getContentPane().add(label);
    m_desktop.add(jif);
    try {            
      jif.setSelected(true);        
    }
    catch (PropertyVetoException pve) {
      System.out.println("Could not select " + jif.getTitle());
    }
    m_count++;
    if (m_count%10 == 0) {
      if (m_tencount < 3)
        m_tencount++;
      else 
        m_tencount = 0;
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == m_newFrame)
      newFrame();
    else if (e.getSource() == m_eventTimer) {
      m_dmEventCanvas.render(m_myDesktopManager.getCounts());
      m_myDesktopManager.clearCounts();
    }
    else if (e.getSource() == m_UIBox) {
      try {
        m_UIBox.hidePopup(); //BUG WORKAROUND
        UIManager.setLookAndFeel(m_infos[m_UIBox.getSelectedIndex()].getClassName());
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {
        System.out.println("Could not load " + 
          m_infos[m_UIBox.getSelectedIndex()].getClassName());
      }
    }
  }
 
  public static void main(String[] args) {
    new DesktopManagerDemo();
  }
}

class MyDesktopManager 
extends DefaultDesktopManager
{
  protected int[] m_counts = new int[15];

  public void clearCounts() {
    for (int i=0; i<15; i++) {
      m_counts[i] = 0;
    }
  }

  public int[] getCounts() { return m_counts; }
    
  public void activateFrame(JInternalFrame f) {
    super.activateFrame(f);
    m_counts[0]++;
  }
  public void beginDraggingFrame(JComponent f) { 
    m_counts[1]++; 
  }
  public void beginResizingFrame(JComponent f, int direction) { 
    m_counts[2]++; 
  }
  public void closeFrame(JInternalFrame f) {
    super.closeFrame(f);
    m_counts[3]++;
  }
  public void deactivateFrame(JInternalFrame f) {
    super.deactivateFrame(f);
    m_counts[4]++;
  }
  public void deiconifyFrame(JInternalFrame f) {
    super.deiconifyFrame(f);
    m_counts[5]++;
  }
  public void dragFrame(JComponent f, int newX, int newY) {
    f.setLocation(newX, newY);
    m_counts[6]++;
  }
  public void endDraggingFrame(JComponent f) {
    m_counts[7]++;
  }
  public void endResizingFrame(JComponent f) {
    m_counts[8]++;
  }
  public void iconifyFrame(JInternalFrame f) {
    super.iconifyFrame(f);
    m_counts[9]++;
  }
  public void maximizeFrame(JInternalFrame f) {
    super.maximizeFrame(f);
    m_counts[10]++;
  }
  public void minimizeFrame(JInternalFrame f) {
    super.minimizeFrame(f);
    m_counts[11]++;
  }
  public void openFrame(JInternalFrame f) {
    m_counts[12]++;
  }
  public void resizeFrame(JComponent f,
   int newX, int newY, int newWidth, int newHeight) {
    f.setBounds(newX, newY, newWidth, newHeight);
    m_counts[13]++;
  }
  public void setBoundsForFrame(JComponent f,
   int newX, int newY, int newWidth, int newHeight) {
    f.setBounds(newX, newY, newWidth, newHeight);
    m_counts[14]++;
  }
}

class DMEventCanvas
extends JComponent
{
  protected Color[] m_colors = new Color[15];
  protected int[][] m_arrays = new int[15][12];

  public DMEventCanvas() {
    setPreferredSize(new Dimension(505,255));
    for (int i=0; i<15; i++) {
      m_arrays[i] = new int[12];
      m_colors[i] = new Color(37+i*4, 37+i*4, 37+i*4);
    }
  }

  public void paintEventSquare(Graphics g, int value, int currwidth, 
   int currheight, int cellwidth, int cellheight, Color color) {
    if(value != 0) {
      g.setColor(color);
      g.fillRect(currwidth, currheight, cellwidth, cellheight); 
      g.setColor(Color.green);
      g.drawString("" + value, currwidth + 5, currheight + 14);
    }
    g.setColor(Color.black);
    g.drawRect(currwidth, currheight, cellwidth, cellheight);
  }

  public void paintComponent(Graphics g) {
    int cellheight = 17;
    int cellwidth = 42;
    int currwidth = 0;
    int currheight = 0;

    for (int i=0; i < 12; i++) {
      for (int j=0; j < 15; j++) {
        paintEventSquare(g, m_arrays[j][i], currwidth, currheight,
          cellwidth, cellheight, m_colors[j]);
        currheight += cellheight;
      }
      currheight = 0;
      currwidth += cellwidth;
    }
  }

  public void render(int[] counts) {
    for (int i=0; i < 11; i++) {
      for (int j=0; j < 15; j++) {            
        m_arrays[j][i] = m_arrays[j][i+1];
      }
    }
    for (int k=0; k < 15; k++) {
      m_arrays[k][11] = counts[k];
    }
    paintImmediately(new Rectangle(20,20,505,255));
  }
}