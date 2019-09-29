package editor;

import commanLib.*;
import java.awt.Container;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class IntrFrm_RegExpr extends javax.swing.JInternalFrame {

    public String regExpr="",frmTitle="";
    GraphNode nfa=null,dfa=null;
    DtransNode Dtrans=null;  
    
    public boolean save=false;
    
    public IntrFrm_RegExpr() {
        initComponents();
        
               
        /*
        //Adding Scrollbars
          Container c= getContentPane();        
          JScrollPane scroll = new JScrollPane(c );  
          setContentPane( scroll );
          * 
          */
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textF_regExpr = new javax.swing.JTextField();
        btn_gen = new javax.swing.JButton();
        tabPane = new javax.swing.JTabbedPane();
        parseTreeMxGraphPanel = new editor.ParseTreeMxGraphPanel();
        nfaPanel = new editor.NfaPanel();
        dfaPanel = new editor.DfaPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        simRegPanel = new editor.SimulateRegExpr();
        jLabel2 = new javax.swing.JLabel();
        textF_ret = new javax.swing.JTextField();
        btn_Cancel = new javax.swing.JButton();
        btn_ok = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(600, 500));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Regular Expression", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 51, 255))); // NOI18N

        jLabel1.setText("  r →");

        textF_regExpr.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        textF_regExpr.setNextFocusableComponent(btn_gen);
        textF_regExpr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textF_regExprKeyTyped(evt);
            }
        });

        btn_gen.setText("Generate Models");
        btn_gen.setNextFocusableComponent(tabPane);
        btn_gen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_genActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textF_regExpr)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_gen)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(textF_regExpr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_gen))
        );

        tabPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Models", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 51, 255))); // NOI18N
        tabPane.setNextFocusableComponent(textF_ret);
        tabPane.setPreferredSize(new java.awt.Dimension(500, 455));
        tabPane.addTab("Parse Tree", parseTreeMxGraphPanel);
        tabPane.addTab("NFA", nfaPanel);
        tabPane.addTab("Equivalent DFA", dfaPanel);

        jScrollPane1.setViewportView(simRegPanel);

        tabPane.addTab("Simulate DFA", jScrollPane1);

        jLabel2.setText("Token Name :");

        textF_ret.setEditable(false);
        textF_ret.setNextFocusableComponent(textF_regExpr);

        btn_Cancel.setText("Cancel");
        btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelActionPerformed(evt);
            }
        });

        btn_ok.setText("Ok");
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textF_ret, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_ok, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tabPane, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPane, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(textF_ret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_Cancel)
                            .addComponent(btn_ok))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_genActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_genActionPerformed
        // TODO add your handling code here:
        genModel();
    }//GEN-LAST:event_btn_genActionPerformed

    private void textF_regExprKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textF_regExprKeyTyped
        // TODO add your handling code here:
        save=false;        
    }//GEN-LAST:event_textF_regExprKeyTyped

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        
        //int result=JOptionPane.showOptionDialog(this, "Save Regular Expression ?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,new Object[]{"Save","Dont Save","Cancel"},null);
        //System.out.println("Closing Reg Frame "+result);
        
        int response=JOptionPane.showConfirmDialog(this, "Apply Changes ?", "Apply", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if(response==JOptionPane.YES_OPTION)
        {
            if(chkAndLoadToObject())
            { dispose(); }
        }
        else if(response==JOptionPane.NO_OPTION)
        { dispose(); }
        
    }//GEN-LAST:event_formInternalFrameClosing

    private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_CancelActionPerformed

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        // TODO add your handling code here:
        if(chkAndLoadToObject())
        { dispose(); }
    }//GEN-LAST:event_btn_okActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        parseTreeMxGraphPanel.resizeGraphPanel();
        nfaPanel.resizeGraphPanel();
        dfaPanel.resizeGraphPanel();
    }//GEN-LAST:event_formComponentResized

    
    //--------my methods-------------------
    
    
    private void genModel()
    {
        regExpr = textF_regExpr.getText();

        try {
            if (!regExpr.equals("")) 
            {
                
                //chk expression
                char c;
                for(int i=0;i<regExpr.length();i++)
                {
                    c=regExpr.charAt(i);
                    if(c>='a' && c<'z')
                    {
                        if(!CATModeler.data.isInputSet(c))
                        { throw new Exception("Set Name '"+c+"' does not exist!"); }
                    }
                    else if( c=='|' && regExpr.charAt(i+1)=='|')
                    { throw new Exception("Invalid use of '|' operator."); }
                    else if( c=='z' || c=='(' || c==')' || c=='|' || c=='.' || c=='*' || c=='+' )  //if( (c>='a' && c<='z') || c=='(' || c==')' || c=='|' || c=='.' || c=='*' || c=='+' )
                    {}
                    else
                    { throw new Exception("Invalid Regular Expression!"); }
                }
                
                CATModeler.setMessages(frmTitle+" | Generating Models for "+regExpr+" ...","Generating Models...",0);
                
                //(1) Parse Tree                            
                try{
                    CATModeler.setMessages(frmTitle+" | Generating Parse Tree...Done.",null,33);
                    parseTreeMxGraphPanel.drawMxGraphTree(regExpr);
                }catch(Exception e)
                { CATModeler.setMessages(frmTitle+" | Unable to display parse tree! ", "", 100); }
                //parseTree.drawTree(regExpr);
                

                //(2) NFA
                nfa = GlobalFunctions.generateNFA(regExpr);
                nfaPanel.drawNfa(nfa);
                CATModeler.setMessages(frmTitle+" | Generating NFA...Done","Generating Models...",66);
                               

                //(3) DFA            
                CATModeler.setMessages(frmTitle+" | Generating DFA from NFA...Done","Generating Models...",99);
                Dtrans = DfaFunctions.genDFAfromNFA(nfa);
                dfaPanel.drawDFA(Dtrans);
                dfa = DtransNode.DtransToGraphNode(dfa, Dtrans,GraphNode.getLastStateLabelforNFA(nfa));
                try{
                    dfaPanel.displayDFAGraph(dfa);
                }catch(Exception e)
               { CATModeler.setMessages(frmTitle+" | DFA - Unable to display graph! ", "", 100); }
                

                // Simulate
                simRegPanel.getRefrence(dfa,true);
                
                save=true;                
                tabPane.setEnabled(true);
                CATModeler.setMessages(frmTitle+" | All Models genertated!","Generating Models...Done!",100);
                
                setEnableTabs(true);
            }
            else
            {setEnableTabs(false); }
        }catch (Exception e) 
        {
            setEnableTabs(false);
            save=false;            
            //System.out.println(e.getMessage());
            CATModeler.setMessages(frmTitle+" | "+e.getMessage(),"Generating Models...Error!",0);
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void resolveData(String ex, String r)
    {
        if(frmTitle.equals("Identifier"))
        { CATModeler.data.addIdentifier(ex, r,dfa); }
        else if(frmTitle.equals("Integer"))
        { CATModeler.data.addInteger(ex, r,dfa); }
        else if(frmTitle.equals("Character"))
        { CATModeler.data.addCharacter(ex, r,dfa); }
        else if(frmTitle.equals("Floating"))
        { CATModeler.data.addFloating(ex, r,dfa); }
        else if(frmTitle.equals("String"))
        { CATModeler.data.addString(ex, r,dfa); }
                
    }
    
    public boolean chkAndLoadToObject()
    {
        boolean flag=save;
        
        if(textF_regExpr.getText().equals(""))
        { resolveData("", textF_ret.getText()); dfa=null; return true; }
        
        if(save)
        {
            String ret=textF_ret.getText();
            if(ret.equals(""))
            {
                CATModeler.setMessages(frmTitle+" | Denied! Please enter return string!", "", 0);
                flag=false;
            }
            else
            {                
                resolveData(regExpr, ret);
                //LanguageEditor.data.addIdentifier(regExpr, ret);
                //LanguageEditor.setMessages("Regular Expression Saved!", "Saved!", 0);
            }
            
        }
        else
        {
            CATModeler.setMessages(frmTitle+" | Denied! Please successfully generate models for your regular Expression!", "", 0);
        }
        
        return flag;
    }
    
    public void loadData(String expr, String ret)
    {        
        textF_regExpr.setText(expr);        
        textF_ret.setText(ret);
        frmTitle=getTitle();
        
        StringTokenizer st = new StringTokenizer(frmTitle, "| ");
        frmTitle=st.nextToken();        
        genModel();        
    }
    
    private void setEnableTabs(boolean value)
    {
        tabPane.setEnabledAt(0, value);
        tabPane.setEnabledAt(1, value);
        tabPane.setEnabledAt(2, value);
        tabPane.setEnabledAt(3, value);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Cancel;
    private javax.swing.JButton btn_gen;
    private javax.swing.JButton btn_ok;
    private editor.DfaPanel dfaPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private editor.NfaPanel nfaPanel;
    private editor.ParseTreeMxGraphPanel parseTreeMxGraphPanel;
    private editor.SimulateRegExpr simRegPanel;
    private javax.swing.JTabbedPane tabPane;
    private javax.swing.JTextField textF_regExpr;
    private javax.swing.JTextField textF_ret;
    // End of variables declaration//GEN-END:variables
}