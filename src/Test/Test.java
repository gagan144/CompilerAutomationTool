package Test;


import commanLib.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Test 
{
    public static void main(String args[])
    {
        
        
        //-------------INSERT CoNCAT OPR-------------
              
        
        //String s="(a|b)*abb";
        //String s="l(l|d)+";
        //String s="abb(a|c)*c+a*b";
        //String s="a+|b+|(bc*)";
        String s="a*(b)";
        
        s=GlobalFunctions.insertConcatOpr(s);
        
        System.out.println("After adding '.' : "+s);
                
        
        //----------INFIX TO POST-----------------
        /*
        String s="(a|b.c)";
        s=GlobalFunctions.infixToPostfixReg(s);
        System.out.println("postFix : "+s);
        
        */
        //--------automata node-------------------
        
        GraphNode start=null,node=null,ptr=null;
        
        /*
        node=new GraphNode();
        node.lb="4";
        start=node;
        
        /*
        ptr=start;
        
        node=new GraphNode();
        node.lb="0";
        ptr.nextSt=node;
        ptr=ptr.nextSt;
        
        node=new GraphNode();
        node.lb="2";
        ptr.nextSt=node;
        
        
        
        
        ptr=start;
        ptr.addLink(ptr.nextSt.nextSt, "a");
        */
        
        
        /*
        start=start.addStateToBeg(start, "2");        
        start=start.addStateToLast(start, "10");
        
        start.addLink(start,"10","x");
        start.nextSt.nextSt.addLink(start,"10",String.valueOf( (char)429 ));
        */            
        
        //--------Closure test
        /*
        node=new GraphNode();
        node.lb="0";
        start=node;
        
        start=start.addStateToLast(start, "1");
        start.addLink(start, "1", "a");
        
        
        //perform closure
        start=NfaFunctions.closure(start);
        //start=NfaFunctions.closure(start);
        
        //reset state name
        ptr=start;
        int lbCntr=0;
        while(ptr!=null)
        {
            ptr.lb=String.valueOf(lbCntr++);
            ptr=ptr.nextSt;
        }
        */
        //--------
        
        //--------ConcatTest & Union Test
        /*
        GraphNode nfa1=null, nfa2=null,nfa3=null;
        
        //NFA1
        node=new GraphNode();
        node.lb="0";
        nfa1=node;
        
        nfa1=nfa1.addStateToLast(nfa1, "1");
        nfa1.addLink(nfa1, "1", "a");
        
        //NFA2
        node=new GraphNode();
        node.lb="2";
        nfa2=node;
        
        nfa2=nfa2.addStateToLast(nfa2, "3");
        nfa2.addLink(nfa2, "3", "b");
        
        
        //NFA3
        node=new GraphNode();
        node.lb="10";
        nfa3=node;
        
        nfa3=nfa3.addStateToLast(nfa3, "11");
        nfa3.addLink(nfa3, "11", "z");
        
        
        //-----perform concate
        //nfa1=NfaFunctions.concatNFA(nfa1, nfa2);
        //nfa1=NfaFunctions.closure(nfa1);
        //nfa1=NfaFunctions.concatNFA(nfa1, nfa3);
        
        
        //----perform Union
        nfa1=NfaFunctions.unionNFA(nfa1, nfa2);
        
               

        
        ptr=nfa1;
        
        while(ptr!=null)
        {
            System.out.print("State : "+ptr.lb+" Links : ");
            ptr.showLinks();
            System.out.println();
            ptr=ptr.nextSt;
        }
        
        */
        
        //----------NFA-----------------
        
        
        
        GraphNode NFA=null;
        //String r="l(l|d)*";
        String r="(a|b)*abb";
        
        NFA=GlobalFunctions.generateNFA(r);
        
        
        //reset state name
        ptr=NFA;
        int lbCntr=0;
        while(ptr!=null)
        {
            ptr.lb=String.valueOf(lbCntr++);
            ptr=ptr.nextSt;
        }
        
        
        ptr=NFA;
        
        while(ptr!=null)
        {
            System.out.print("State : "+ptr.lb+" Links : ");
            ptr.showLinks();
            System.out.println();
            ptr=ptr.nextSt;
        }        
        
        
        //---------------NFA TO DFA------------
        //DfaFunctions.getInputSet(NFA);
        //DfaFunctions.genDFAfromNFA(NFA);
        
        //HashSet<String> s = new HashSet<String>();
        //s.add("0");
        //s.add("1");
        //s.add("2");
        //s.add("4");
       // s.add("7");
        //s.add("4");
        //s.add("9");
        
        
        
        //DfaFunctions.EClosure(s, NFA);
        //DfaFunctions.move(s,'a', NFA);
        DtransNode dTrans= DfaFunctions.genDFAfromNFA(NFA);
        GraphNode DFA=null;
        //DFA=DtransNode.DtransToGraphNode(DFA, dTrans);
        
        System.out.println("Generated DFA : ");
               ptr=DFA;
               while(ptr!=null)
               {
                   System.out.print("State : |"+ptr.lb+"| Links : ");
                   ptr.showLinks();
                   System.out.println();
                   ptr=ptr.nextSt;
               }
        
    }
    
}
