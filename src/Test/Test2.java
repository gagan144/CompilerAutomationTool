package Test;


import commanLib.GraphNode;
import commanLib.NfaFunctions;

public class Test2 
{
    public static void main(String args[])
    {
        GraphNode nfa1=null, nfa2=null,nfa3=null,node=null,ptr=null,start;
        
        //NFA1
        node=new GraphNode();
        node.lb="0";
        nfa1=node;
        
        nfa1=nfa1.addStateToLast(nfa1, "1");
        nfa1.addLink(nfa1, "1", "l");
        
        //NFA2
        node=new GraphNode();
        node.lb="2";
        nfa2=node;
        
        nfa2=nfa2.addStateToLast(nfa2, "3");
        nfa2.addLink(nfa2, "3", "d");
        
        
        //l.(l|d)*  = l(ld|)*.
        
        //(1) (l|d)
        start=NfaFunctions.unionNFA(nfa1, nfa2);
        
        //(2) (l|d)*
                
        start=NfaFunctions.closure(start);
        
        //(3) l. (l|d)*
        node=new GraphNode();
        node.lb="0";
        nfa1=node;        
        nfa1=nfa1.addStateToLast(nfa1, "1");
        nfa1.addLink(nfa1, "1", "l");
        
        start=NfaFunctions.concatNFA(nfa1, start);
        
        
        
        //reset state name
        
        ptr=start;
        int lbCntr=0;
        while(ptr!=null)
        {
            ptr.lb=String.valueOf(lbCntr++);
            ptr=ptr.nextSt;
        }
        
        //--------

        
        ptr=start;
        //ptr=nfa1;
        
        while(ptr!=null)
        {
            System.out.print("State : "+ptr.lb+" Links : ");
            ptr.showLinks();
            System.out.println();
            ptr=ptr.nextSt;
        }
        
    }
    
}
