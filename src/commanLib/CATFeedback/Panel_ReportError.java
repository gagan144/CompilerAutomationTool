package commanLib.CATFeedback;

public class Panel_ReportError extends javax.swing.JPanel 
{
    public Panel_ReportError() {
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        textF_areaOfCrn = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textA_input = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textA_Response = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        textA_outputMsg = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        textA_sugg = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setPreferredSize(new java.awt.Dimension(500, 447));

        jLabel2.setText("Area of Concern :");

        jLabel3.setText("Your Input :");

        textA_input.setColumns(20);
        textA_input.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        textA_input.setRows(5);
        jScrollPane1.setViewportView(textA_input);

        jLabel4.setText("Program Response :");

        textA_Response.setColumns(20);
        textA_Response.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        textA_Response.setLineWrap(true);
        textA_Response.setRows(5);
        textA_Response.setWrapStyleWord(true);
        jScrollPane2.setViewportView(textA_Response);

        textA_outputMsg.setColumns(20);
        textA_outputMsg.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        textA_outputMsg.setRows(5);
        jScrollPane3.setViewportView(textA_outputMsg);

        jLabel5.setText("Any Output Message :");

        jLabel6.setText("Suggestions :");

        textA_sugg.setColumns(20);
        textA_sugg.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        textA_sugg.setLineWrap(true);
        textA_sugg.setRows(5);
        textA_sugg.setWrapStyleWord(true);
        jScrollPane4.setViewportView(textA_sugg);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                                    .addComponent(textF_areaOfCrn, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addComponent(jSeparator2)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textF_areaOfCrn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea textA_Response;
    private javax.swing.JTextArea textA_input;
    private javax.swing.JTextArea textA_outputMsg;
    private javax.swing.JTextArea textA_sugg;
    private javax.swing.JTextField textF_areaOfCrn;
    // End of variables declaration//GEN-END:variables

    public void setData(String areaOfCrn, String input, String response, String output, String suggestns )
    {
        if(areaOfCrn!=null)
        { textF_areaOfCrn.setText(areaOfCrn); }
        else
        { textF_areaOfCrn.setText(""); }
        
        if(input!=null)
        { textA_input.setText(input); }
        else
        { textA_input.setText(""); }
        
        if(response!=null)
        { textA_Response.setText(response); }
        else
        { textA_Response.setText(""); }
        
        if(output!=null)
        { textA_outputMsg.setText(output); }
        else
        { textA_outputMsg.setText(""); }
        
        if(suggestns!=null)
        { textA_sugg.setText(suggestns); }
        else
        { textA_sugg.setText(""); }
        
        
    }
    
    public String getSubject() throws Exception
    {
        if(textF_areaOfCrn.getText().equals("") )
        { throw new Exception("Invalid Area of Concern!"); }
        else
        { return textF_areaOfCrn.getText(); }
    }
    
    public String getString() throws Exception
    {
        //chk values
        if(textF_areaOfCrn.getText().equals("") || textA_input.getText().equals("") || textA_Response.getText().equals("") || textA_outputMsg.getText().equals("") )
        { throw new Exception("Incomplete Entry(s) !"); }
        
        String ret="";
        ret+="Area of Concern : "+textF_areaOfCrn.getText()+"\n"+
             "Your Input : "+textA_input.getText()+"\n"+
             "Program Response : "+textA_Response.getText()+"\n"+
             "Any Output Message : "+textA_outputMsg.getText()+"\n"+
             "Your Suggestions : "+textA_sugg.getText();
        return ret;
    }
}
