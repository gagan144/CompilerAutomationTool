package Test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InternalFrameToFront extends JPanel {
   private static final int FRAME_MAX = 21;
   private static final int DT_WIDTH = 700;
   private static final int DT_HEIGHT = 500;
   private static final Dimension DESKTOP_SIZE = new Dimension(DT_WIDTH, DT_HEIGHT);
   private static final int IF_WIDTH = 150;
   private static final int IF_HEIGHT = 100;
   private static final Dimension INT_FRAME_SIZE = new Dimension(IF_WIDTH, IF_HEIGHT);
   private Random random = new Random();
   private JInternalFrame[] internalFrames = new JInternalFrame[FRAME_MAX];

   public InternalFrameToFront() {
      JDesktopPane desktop = new JDesktopPane();
      desktop.setPreferredSize(DESKTOP_SIZE);
      for (int i = 0; i < internalFrames.length; i++) {
         JInternalFrame intFrame = new JInternalFrame("Frame Number " + i);
         intFrame.setSize(INT_FRAME_SIZE);
         int x = random.nextInt(DT_WIDTH - IF_WIDTH);
         int y = random.nextInt(DT_HEIGHT - IF_HEIGHT);
         intFrame.setLocation(x, y);
         intFrame.setVisible(true);
         desktop.add(intFrame);

         internalFrames[i] = intFrame;
      }
      
      JSlider slider = new JSlider(0, FRAME_MAX - 1, 0);
      slider.setMajorTickSpacing(5);
      slider.setMinorTickSpacing(1);
      slider.setPaintLabels(true);
      slider.setPaintTicks(true);
      JPanel sliderPanel = new JPanel();
      sliderPanel.add(slider);
      slider.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent ce) {
            JSlider slider = (JSlider) ce.getSource();
            if (!slider.getValueIsAdjusting()) {
               int value = slider.getValue();
               internalFrames[value].toFront();
            }
         }
      });

      setLayout(new BorderLayout());
      add(desktop, BorderLayout.CENTER);
      add(sliderPanel, BorderLayout.SOUTH);
   }

   private static void createAndShowUI() {
      JFrame frame = new JFrame("InternalFrameToFront");
      frame.getContentPane().add(new InternalFrameToFront());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public static void main(String[] args) {
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            createAndShowUI();
         }
      });
   }
}
