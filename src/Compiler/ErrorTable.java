package Compiler;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ErrorTable 
{
    ArrayList<ErrorRow> errorList = new ArrayList<ErrorRow>();
    
    public void addError(String t,int l,int c,String recoveryMode)
    {
        errorList.add(new ErrorRow(t, l, c,recoveryMode));
    }
    
    public int displayErrorTable(JTable table)
    {
        if(errorList.isEmpty())
        { return 0; }
                
        DefaultTableModel model=(DefaultTableModel)table.getModel();        
        ErrorRow row=null;
        int i=0;
        for(i=0;i<errorList.size();i++)
        {
            row=errorList.get(i);
            model.insertRow(table.getRowCount(), new Object[]{i+1,row.type,"["+row.line+","+row.caret+"]",row.recvryMode });
        }        
        return i;
    }
    
    public void resetTable()
    {
        errorList.clear();        
    }
            
    
    class ErrorRow
    {
        String type="";
        int line,caret;
        String recvryMode="";

        public ErrorRow(String t,int l,int c,String rMode)                 
        {
            type=t;
            line=l;
            caret=c;
            recvryMode=rMode;
        }
    }
}
