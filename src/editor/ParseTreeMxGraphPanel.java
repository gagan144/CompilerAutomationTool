package editor;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import commanLib.GlobalFunctions;
import commanLib.Panel_mxGraphDisplay;
import commanLib.parseNode;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Stack;
import javax.swing.JScrollPane;

public class ParseTreeMxGraphPanel extends javax.swing.JPanel 
{
        
    private parseNode start=null,ptr=null;   
    
    //final String STYLE_START="shape=ellipse;perimeter=100;fillColor=#ffad07;strokeColor=#990033;fontColor=#990033;fontSize=15";
    private final String STYLE_R="shape=ellipse;perimeter=100;fillColor=#ffffcc;strokeColor=#990033;fontColor=#990033;fontSize=15";
    private final String STYLE_SYM="shape=ellipse;perimeter=100;fillColor=#7CDE7C;strokeColor=#0C7849;fontColor=black;fontSize=15";
    private final String STYLE_EDGE="strokeColor=black;fontColor=black;fontSize=18";
    private Object parent=null;
    private final int R=35,HGAP=50,VGAP=60; 
    
    private Panel_mxGraphDisplay panel_mxGraph=null;
    
    public ParseTreeMxGraphPanel() {
        initComponents();                       
        
        //scrollpane corner button
        //scrollPane_mxGraph.setCorner(JScrollPane.LOWER_RIGHT_CORNER, tglBtn_outline);
    }
        
    //------------MY METHODS------------------
    public void drawMxGraphTree(String rexpr) throws Exception
    {        
        start=GlobalFunctions.generateParseTree(rexpr);                
        
        if(start==null)
        { return; }
        
        //draw mxGraph               
        int x=10,y=10;
        
        mxGraph graph = new mxGraph(); 
        parent = graph.getDefaultParent();           
        graph.setAutoOrigin(true);
        
	graph.getModel().beginUpdate();
        Object vertx;
        try
        {
            traverse(graph, start, x, y, null);
        }
        finally
        { graph.getModel().endUpdate(); }
	
        graph.setCellsResizable(false);
        graph.setDisconnectOnMove(false);
        graph.getOutgoingEdges(false);
        graph.setCellsEditable(false);
        graph.setAllowDanglingEdges(false);
        //graph.setCellsLocked(true);
        
        graph.setKeepEdgesInBackground(true);
        
                
	mxGraphComponent graphComponent = new mxGraphComponent(graph);        
        //graphComponent.setBackground(Color.red);
        graphComponent.setConnectable(false);        
        
        //new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());   //Automatic Layout
        new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());   //Prevent parallel edge overlaping
        
        
        //Set Graph     
        panel.removeAll();
        
        panel_mxGraph = new Panel_mxGraphDisplay(graphComponent);
        panel_mxGraph.setSize(panel.getSize());
        
        mxGraphComponent graphCmp=panel_mxGraph.getMxGraphComponent();
        graphCmp.setSize(panel.getSize());        
        graphCmp.setCorner(JScrollPane.LOWER_RIGHT_CORNER, tglBtn_outline);
        panel.add(graphCmp);
        panel.paintComponents(panel.getGraphics());        
        
        /*
        panel_mxGraph = new Panel_mxGraphDisplay(graphComponent);
        scrollPane_mxGraph.setViewportView(panel_mxGraph);   
        */
        
        //outline popUP menu
        popMenu_outline.removeAll();
        popMenu_outline.add(panel_mxGraph.getGraphOutline());
    }
       
    private void traverse(mxGraph graph,parseNode p,int x,int y,Object prntVrtx)
    {        
        Object vrtx=null;
        
        if(p==null)   //null node
        { return; }
        else if(p.l==null && p.m==null && p.r==null)   //leaf node
        {
            vrtx=graph.insertVertex(parent, null, p.lb, x, y ,R, R,STYLE_SYM);
            if(prntVrtx!=null)
            { graph.insertEdge(parent, null, "", prntVrtx,vrtx ,STYLE_EDGE);   }
        }
        else
        {
            vrtx=graph.insertVertex(parent, null, p.lb, x, y ,R, R,STYLE_R);
            if(prntVrtx!=null)
            { graph.insertEdge(parent, null, "", prntVrtx,vrtx ,STYLE_EDGE);   }
            
            if(p.l!=null)
            {
                traverse(graph, p.l, x-HGAP, y+VGAP, vrtx);
            }
            
            if(p.m!=null)
            {
                traverse(graph, p.m, x, y+VGAP, vrtx);
            }
            
            if(p.r!=null)
            {
                traverse(graph, p.r, x+HGAP, y+VGAP, vrtx);
            }
        }
                  
        
    }
    
    public void resizeGraphPanel()
    {
        if(panel_mxGraph!=null)
        { panel_mxGraph.adjustSizeOfGraphComp(panel.getSize()); }
    }
    
    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tglBtn_outline = new javax.swing.JToggleButton();
        popMenu_outline = new javax.swing.JPopupMenu();
        btn_mxGraph_zoomIn = new javax.swing.JButton();
        btn_mxGraph_zoomOut = new javax.swing.JButton();
        btn_mxGraph_fit = new javax.swing.JButton();
        btn_mxGraph_autoLayt = new javax.swing.JButton();
        panel = new javax.swing.JPanel();

        tglBtn_outline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/commanLib/images/eye.png"))); // NOI18N
        tglBtn_outline.setToolTipText("Adjust View");
        tglBtn_outline.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        tglBtn_outline.setFocusPainted(false);
        tglBtn_outline.setFocusable(false);
        tglBtn_outline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglBtn_outlineActionPerformed(evt);
            }
        });

        popMenu_outline.setMaximumSize(new java.awt.Dimension(101, 101));
        popMenu_outline.setMinimumSize(new java.awt.Dimension(101, 101));
        popMenu_outline.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                popMenu_outlinePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        setPreferredSize(new java.awt.Dimension(500, 400));

        btn_mxGraph_zoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editor/Images/zoomIn.png"))); // NOI18N
        btn_mxGraph_zoomIn.setToolTipText("Zoom In");
        btn_mxGraph_zoomIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_mxGraph_zoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mxGraph_zoomInActionPerformed(evt);
            }
        });

        btn_mxGraph_zoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editor/Images/zoomOut.png"))); // NOI18N
        btn_mxGraph_zoomOut.setToolTipText("Zoom Out");
        btn_mxGraph_zoomOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_mxGraph_zoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mxGraph_zoomOutActionPerformed(evt);
            }
        });

        btn_mxGraph_fit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editor/Images/zoomFit.png"))); // NOI18N
        btn_mxGraph_fit.setText("Fit on Screen");
        btn_mxGraph_fit.setToolTipText("Fit on Screen");
        btn_mxGraph_fit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_mxGraph_fit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mxGraph_fitActionPerformed(evt);
            }
        });

        btn_mxGraph_autoLayt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/commanLib/images/autoLayout.png"))); // NOI18N
        btn_mxGraph_autoLayt.setText("Auto Layout");
        btn_mxGraph_autoLayt.setToolTipText("Automatically set layout");
        btn_mxGraph_autoLayt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_mxGraph_autoLayt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mxGraph_autoLaytActionPerformed(evt);
            }
        });

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                panelComponentResized(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_mxGraph_zoomIn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_mxGraph_zoomOut, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_mxGraph_fit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_mxGraph_autoLayt)
                        .addGap(0, 165, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_mxGraph_zoomIn)
                    .addComponent(btn_mxGraph_zoomOut)
                    .addComponent(btn_mxGraph_fit)
                    .addComponent(btn_mxGraph_autoLayt))
                .addGap(9, 9, 9))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_mxGraph_zoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mxGraph_zoomInActionPerformed
        // TODO add your handling code here:
        if(panel_mxGraph!=null)
        { panel_mxGraph.zoomIn(); }
    }//GEN-LAST:event_btn_mxGraph_zoomInActionPerformed

    private void btn_mxGraph_zoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mxGraph_zoomOutActionPerformed
        // TODO add your handling code here:
        if(panel_mxGraph!=null)
        { panel_mxGraph.zoomOut(); }
    }//GEN-LAST:event_btn_mxGraph_zoomOutActionPerformed

    private void btn_mxGraph_fitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mxGraph_fitActionPerformed
        // TODO add your handling code here:
        if(panel_mxGraph!=null)
        { panel_mxGraph.zoomToFit(panel.getSize()); }
    }//GEN-LAST:event_btn_mxGraph_fitActionPerformed

    private void tglBtn_outlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglBtn_outlineActionPerformed
        // TODO add your handling code here:

        if(!popMenu_outline.isShowing() && tglBtn_outline.isSelected())
        {
            //System.out.println("SHOW");
            int x=tglBtn_outline.getX();
            int y=tglBtn_outline.getY();

            x=x-76;
            y=y-90;
            popMenu_outline.show(this, x,y);
        }
        else
        { tglBtn_outline.setSelected(false); }
    }//GEN-LAST:event_tglBtn_outlineActionPerformed

    private void popMenu_outlinePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_popMenu_outlinePopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        tglBtn_outline.setSelected(false);
    }//GEN-LAST:event_popMenu_outlinePopupMenuWillBecomeInvisible

    private void btn_mxGraph_autoLaytActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mxGraph_autoLaytActionPerformed
        // TODO add your handling code here:
        if(panel_mxGraph!=null)
        { panel_mxGraph.autolayout(); }
    }//GEN-LAST:event_btn_mxGraph_autoLaytActionPerformed

    private void panelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panelComponentResized
        // TODO add your handling code here:
        if(panel_mxGraph!=null)
        { panel_mxGraph.adjustSizeOfGraphComp(panel.getSize()); }
    }//GEN-LAST:event_panelComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_mxGraph_autoLayt;
    private javax.swing.JButton btn_mxGraph_fit;
    private javax.swing.JButton btn_mxGraph_zoomIn;
    private javax.swing.JButton btn_mxGraph_zoomOut;
    private javax.swing.JPanel panel;
    private javax.swing.JPopupMenu popMenu_outline;
    private javax.swing.JToggleButton tglBtn_outline;
    // End of variables declaration//GEN-END:variables
}



