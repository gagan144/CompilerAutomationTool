package commanLib;

public class NfaFunctions 
{
    public static GraphNode closure(GraphNode r)
    {
        GraphNode start=r,last=null,ptr=null,newStateI=null,newStateF=null;
        
        //find last
        ptr=start;
        while(ptr.nextSt!=null)
        {
            ptr=ptr.nextSt;
        }
        last=ptr;
        
        //create i & f state
        newStateI=new GraphNode();
        newStateI.lb="i";
        newStateF=new GraphNode();
        newStateF.lb="f";
        
        newStateI.nextSt=start;
        newStateI.addLink(start, start.lb,String.valueOf( SpecialSymbol.EPSILON ));
        newStateI.addLink(newStateF, newStateF.lb,String.valueOf( SpecialSymbol.EPSILON ));
        
        last.nextSt=newStateF;
        last.addLink(start, start.lb, String.valueOf( SpecialSymbol.EPSILON ));
        last.addLink(newStateF, newStateF.lb, String.valueOf( SpecialSymbol.EPSILON ));
        
        start=newStateI;
        
        return start;
        
    }
    
    //------------
    
    public static GraphNode concatNFA(GraphNode r, GraphNode t)
    {
        GraphNode startR=null,lastR=null,ptr=null,R=null;
        
        startR=r;
        //find last of NFA(r)
        ptr=r;
        while(ptr.nextSt!=null)
        {
            ptr=ptr.nextSt;
        }
        lastR=ptr;
        
        //make connections
        lastR.nextSt=t.nextSt;         //t.nextSt=null;
        lastR.links=t.links;    //t.links=null;
                
        return startR;
    }
    
    //------------
    
    public static GraphNode unionNFA(GraphNode r, GraphNode t)
    {
        GraphNode startR=null,lastR=null,startT=null,lastT=null,ptr=null,newStateI=null, newStateF=null;
        
        startR=r; startT=t;
        //find last refereneces
        ptr=r;
        while(ptr.nextSt!=null)
        {
            ptr=ptr.nextSt;
        }
        lastR=ptr;
        
        ptr=t;
        while(ptr.nextSt!=null)
        {
            ptr=ptr.nextSt;
        }
        lastT=ptr;
        
        //create i & f states
        newStateI=new GraphNode();
        newStateI.lb="i";
        newStateF=new GraphNode();
        newStateF.lb="f";
        
        //(1) & (2)
        newStateI.addLink(startR, startR.lb, String.valueOf( SpecialSymbol.EPSILON )); 
        newStateI.addLink(startT, startT.lb, String.valueOf( SpecialSymbol.EPSILON )); 
        
        //(3) & (4)
        lastR.addLink(newStateF, newStateF.lb, String.valueOf( SpecialSymbol.EPSILON ));
        lastT.addLink(newStateF, newStateF.lb, String.valueOf( SpecialSymbol.EPSILON ));
        
        //(5) & (6)
        lastR.nextSt=startT;
        lastT.nextSt=newStateF;
        
        //(7)
        newStateI.nextSt=startR;
        
        startR=newStateI;
        
        return startR;       
    }
    
}
