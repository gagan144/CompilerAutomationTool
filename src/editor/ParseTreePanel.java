package editor;

import commanLib.GlobalFunctions;
import commanLib.parseNode;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Stack;

public class ParseTreePanel extends javax.swing.JPanel {
        
    parseNode start=null,ptr=null;    
    int w,h,maxX,maxY,minX=Integer.MAX_VALUE,minY=20;
    int refX=200,refY=20;
    
    int refreshCtr=0;
    
    
    public ParseTreePanel() {
        initComponents();        
        //ptr=start;        
        
        jButton1.setVisible(false);
        jButton2.setVisible(false);
        jButton3.setVisible(false);
        jButton4.setVisible(false);
    }
        
    //------------MY METHODS------------------
    public void drawTree(String rexpr)
    {
        refreshCtr=0;
        refX=200; refY=20;
        start=GlobalFunctions.generateParseTree(rexpr);                
    }
    //----------
    
    protected void paintComponent(Graphics g)
    {        
        super.paintComponents(g);
        
        
        //this.setBackground(Color.white);        
        Font f = new Font("Tahoma", Font.PLAIN , 11);        
        g.setFont(f);
        
        if(start!=null)
        {
            
            traverse(start,g,refX,refY); 
                
            //System.out.println("[minX,maxX]="+minX+","+maxX+" | "+"[minY,maxY]="+minY+","+maxY);
            //g.setColor(Color.red);
            //g.drawRect(minX-20 , 0 , w+60 , h+40 );
        
        
            if(refreshCtr<=0)       
            {                        
                w=maxX-minX;
                h=maxY-minY;
            
                w=(maxX-minX)+60;
                h=(maxY-minY)+40;
                refX=refX-minX+20;
            
            
                refreshCtr++;            
            }    
            setSize(w,h);
        }        
    }
    
    //----------
    
    private void traverse(parseNode p,Graphics g,int x,int y)
    {
        //calculate Area                 
        
        
          if(x<refX && x<minX)
          { minX=x; }
          else if(x>refX && x>maxX)
          { maxX=x; }
          
          if(y>maxY)         
          { maxY=y; }       
        
        
        if(p==null)
        { return; }
                
        if(p.l==null && p.m==null && p.r==null)
        {   g.setColor(new Color(0,153,0));    
            g.drawString(p.lb, x+8, y-6);
        }
        else
        {   g.setColor(Color.BLUE);    
            g.drawString(p.lb, x+4, y-6);
        }
        
        
        g.setColor(Color.BLACK);
        if(p.l!=null)
        {   
           g.drawLine(x,y,x-20,y+20 );                           
           traverse(p.l,g,x-40,y+40);//traverse(p.l,g);
        }
        
        if(p.m!=null)
        {  
           g.drawLine(x+10,y,x+10,y+20 );           
           traverse(p.m,g,x,y+40);  //traverse(p.m,g); 
        }
        
        if(p.r!=null)
        {  
           g.drawLine(x+20,y,x+40,y+20 );           
           traverse(p.r,g,x+40,y+40);  //traverse(p.r,g); 
        }
        
        
                
        
    }
    
    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(200, 200));

        jButton1.setText("Left");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Middle");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Right");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("reset");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(74, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:        
        if(ptr.l!=null)
        {  ptr=ptr.l;
           System.out.println("ptr : |"+ptr.lb+"|"+ptr.l+"|"+ptr.m+"|"+ptr.r+"|");
        }else
        { System.out.println("Dead End!"); }    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(ptr.m!=null)
        {  ptr=ptr.m;
           System.out.println("ptr : |"+ptr.lb+"|"+ptr.l+"|"+ptr.m+"|"+ptr.r+"|");
        }else
        { System.out.println("Dead End!"); } 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(ptr.r!=null)
        {  ptr=ptr.r;
           System.out.println("ptr : |"+ptr.lb+"|"+ptr.l+"|"+ptr.m+"|"+ptr.r+"|");
        }else
        { System.out.println("Dead End!"); } 
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        ptr=start;
        System.out.println("----------RESET--------\n"+"ptr : |"+ptr.lb+"|"+ptr.l+"|"+ptr.m+"|"+ptr.r+"|");
    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    // End of variables declaration//GEN-END:variables
}

//------------------------Classess---------
/*
class parseNode
{
    String lb;
    parseNode l,m,r;

    public parseNode() 
    {
        lb=null;
        l=null;
        m=null;
        r=null;
    }
    
    
}
*/


