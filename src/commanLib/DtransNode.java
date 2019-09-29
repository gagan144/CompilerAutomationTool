package commanLib;

import java.util.HashSet;

public class DtransNode
{   
    class DtLnkNode
    {
        DtransNode addr;
        String value;
        DtLnkNode next;

        public DtLnkNode() 
        {
            addr=null;
            value="";
            next=null;
        }
    }
    
    public String lb;
    public HashSet<String> set;
    boolean mark;
    public DtransNode nextSET;
    public DtLnkNode links;
    

    public DtransNode() 
    {        
        lb=null;
        set=null;
        mark=false;
        nextSET=null;
        links=null;
    }
    
    static DtransNode addDtransStateToLast(DtransNode st,String l,HashSet<String> s)
    {
        DtransNode ptr=null,newState;
        
        ptr=st;
        while(ptr.nextSET!=null)
        {
            ptr=ptr.nextSET;
        }    
        
        newState=new DtransNode();
        newState.lb=l;
        newState.set=s;
        
        ptr.nextSET=newState;
                
        return st;
    }
    
    void addDtransLink(DtransNode st,HashSet<String> s, String v)  //on the basis of set U
    {
        DtransNode dest=null,ptr=null;
        DtLnkNode ptrLnk=null;
        
        //find dest
        ptr=st;
        while(ptr!=null)
        {
            if(ptr.set.equals(s))
            { dest=ptr; break; }
            else
            { ptr=ptr.nextSET; }
        }
        
        //move to last of the links
        ptrLnk=links;
        if(ptrLnk==null)
        {
            ptrLnk=new DtLnkNode();
            ptrLnk.addr=dest;
            ptrLnk.value=v;
            ptrLnk.next=null;
            links=ptrLnk;
        }
        else
        {
            //travse to last
            while(ptrLnk.next!=null)
            {
                ptrLnk=ptrLnk.next;
            }  
            
            ptrLnk.next = new DtLnkNode();            
            ptrLnk=ptrLnk.next;
            ptrLnk.addr=dest;
            ptrLnk.value=v;
            ptrLnk.next=null;            
        }        
    }
    
    
    public void showLinks()
    {
        DtLnkNode ptr=links;        
        
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
    
    public static GraphNode DtransToGraphNode(GraphNode dfaSt, DtransNode dtrans, String lastStLb) 
    {
        GraphNode ptrDfa=null;
        DtransNode ptrDtrans=dtrans;
        
        //(1) copy All states lb        
        //(a) copy first state
        ptrDfa= new GraphNode();
        ptrDfa.lb=ptrDtrans.lb;
        ptrDfa.state='i';
        if(ptrDtrans.set.contains(lastStLb))
        {  ptrDfa.state='F';  }
        dfaSt=ptrDfa;
        ptrDtrans=ptrDtrans.nextSET;
        
        //(b) copy remaining
        while(ptrDtrans!=null)
        {
            ptrDfa.nextSt= new GraphNode();
            ptrDfa=ptrDfa.nextSt;
            ptrDfa.lb=ptrDtrans.lb;   
            if(ptrDtrans.set.contains(lastStLb))
            {  ptrDfa.state='F'; }
            ptrDtrans=ptrDtrans.nextSET;
        }
        
        //(2) Copy list        
        DtLnkNode ptrDtLnk=null;        
        
        ptrDtrans=dtrans;
        ptrDfa=dfaSt;
        while(ptrDtrans!=null)
        {            
            if(ptrDtrans.links!=null)
            {
                ptrDtLnk=ptrDtrans.links;
                
                while(ptrDtLnk!=null)
                {
                    ptrDfa.addLink(dfaSt,ptrDtLnk.addr.lb , ptrDtLnk.value);                
                        
                    ptrDtLnk=ptrDtLnk.next;
                }
                
            }           
            
            
            ptrDtrans=ptrDtrans.nextSET;
            ptrDfa=ptrDfa.nextSt;
        }
        
        
        return dfaSt;
    }
    
    public Object[] createTableRow(int c,Character[] inp)
    {
        Object data[]=new Object[c];
        
        data[0]=lb;
        data[1]=set;
        
        DtLnkNode ptr=links;
        int i=2;
        while(ptr!=null)
        {
            if(ptr.value.equals(String.valueOf(inp[i-2])))
            { data[i++]=ptr.addr.lb; ptr=ptr.next; }
            else
            { data[i++]=""; }
            
        }
        
        return data;
    }
            
    
}
