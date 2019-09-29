package commanLib;

import editor.CATModeler;
import java.io.Serializable;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class GraphNode implements Serializable
{
    public String lb;   
    public char state;
    public GraphNode nextSt;
    public IntrnlNode links;

    public GraphNode() 
    {
        lb="";
        state='o'; //state='i';
        nextSt=null;
        links=null;
    }
    
    public GraphNode addStateToBeg(GraphNode st,String l)
    {
        GraphNode newState=new GraphNode();
        newState.lb=l;
        newState.nextSt=st;
        st=newState;        
                
        return st;
    }
    
    public GraphNode addStateToLast(GraphNode st,String l)
    {
        GraphNode ptr=null,newState;
        
        ptr=st;
        while(ptr.nextSt!=null)
        {
            ptr=ptr.nextSt;
        }    
        
        newState=new GraphNode();
        newState.lb=l;
        ptr.nextSt=newState;
                
        return st;
    }
    
    public void resetLink()
    {
        links=null;
    }
        
    public void addLink(GraphNode st, String stLbl, String v)  //start of the graph, state label to which to link, value
    {        
        GraphNode n=null,p=st;
        
        //find reference to the node
        while(p!=null)
        {
            if(p.lb.equals(stLbl))
            { 
                n=p;
                break;
            }
            p=p.nextSt;
        }
                
        IntrnlNode ptr=links;
        
        if(ptr==null)
        {
            ptr=new IntrnlNode();               
            ptr.addr=n;
            ptr.value=v;
            ptr.next=null;     
            links=ptr;
        }
        else
        {
            //travse to last
            while(ptr.next!=null)
            {
                ptr=ptr.next;
            }    
            
            ptr.next = new IntrnlNode();            
            ptr=ptr.next;
            ptr.addr=n;
            ptr.value=v;
            ptr.next=null;
        }
        
             
        //System.out.println("Link Added to State "+lb+" as : |"+ptr.addr.lb+"|"+ptr.value+"|null|");
    }
    
    public boolean isConnectedToState(String stNm)
    {
        IntrnlNode p=links;
            while(p!=null)
            {
                if(p.addr.lb.equals(stNm))
                { return true; }
                p=p.next;
            }
         
            return false;
    }
    
    public GraphNode getNextState(char c,boolean setChk,ModelData MdlDtRef)
    {
        if(setChk==true)
        {
            IntrnlNode p=links;
            while(p!=null)
            {
                if(MdlDtRef.doesSetContains(p.value.charAt(0), c) )
                { return p.addr; }
                p=p.next;
            }
        }
        else
        {  //Transition Diag
            
            String in=String.valueOf(c);
            IntrnlNode p=links;              
            while(p!=null)
            {                
                if(in.equals(p.value) )
                { return p.addr; }
                else
                if(p.value.equals("z"))    //z as universal set
                { return p.addr; } 
                p=p.next;
            }
            
            // Retract 
            if(p==null)
            {
                p=links;
                while(p!=null)
                {
                    if(p.addr.state=='*')
                    { return p.addr; }
                    p=p.next;
                }
            }
            
        }
        
        /*
        //String in=String.valueOf(c);
        IntrnlNode p=links;
        while(p!=null)
        {
            if(LanguageEditor.data.doesSetContains(p.value.charAt(0), c) )
            { return p.addr; }
            p=p.next;
            
            /*
            if(in.equals(p.value) )
            { return p.addr; }
            p=p.next;
            * 
            
            
        }
        */
        //System.out.println("*");
        return null;
    }
    
    
    public void showLinksOnTable(JTable table,JComboBox comboBox)
    {
        IntrnlNode ptr=links;        
        
        if(ptr==null)
        {
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableColumn stCol;
        while(ptr!=null)
        {            
            model.insertRow(table.getRowCount(), new Object[]{ptr.value, ptr.addr.lb});            
            
            /*
            stCol = table.getColumnModel().getColumn(1);
            comboBox.setSelectedItem(ptr.addr.lb);
            stCol.setCellEditor(new DefaultCellEditor(comboBox));
            * 
            */
            
            
            ptr=ptr.next;
        }
    }
    
    public void showLinks(JTextArea disp)
    {
        IntrnlNode ptr=links;        
        
        if(ptr==null)
        {
            disp.append("No links");
        }
        while(ptr!=null)
        {
            disp.append("|"+ptr.addr.lb+"|"+ptr.value+"|");
            if(ptr.next!=null)
            {
                disp.append(" → ");
            }
            ptr=ptr.next;
        }
    }
    
    public static String getLastStateLabelforNFA(GraphNode start)
    {
        String lb="";
        GraphNode ptr=start;
        
        while(ptr.nextSt!=null)
        {
            ptr=ptr.nextSt;
        }
        lb=ptr.lb;
        
        return lb;
    }
    
    //temp
    public void showLinks()
    {
        IntrnlNode ptr=links;        
        
        if(ptr==null)
        {
            System.out.print("No links");
        }
        while(ptr!=null)
        {
            System.out.print("|"+ptr.addr.lb+"|"+ptr.value+"| -> ");
            ptr=ptr.next;
        }
    }
    
}

class IntrnlNode implements Serializable
{
    GraphNode addr;
    String value;
    IntrnlNode next;

    public IntrnlNode() 
    {
        addr=null;
        value="";
        next=null;
    }
    
}
