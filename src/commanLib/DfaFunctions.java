package commanLib;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.util.*;

public class DfaFunctions 
{
    private static Character inputs[]=null;   
    
    public static Character[] retInputArr() throws Exception
    {
        if(inputs==null)
        { throw new Exception("DfaFunctions | No inputs Calculated. Run genDFAfromNFA()."); }
        
        return inputs;
    }
    
    //------------------------
    private static Character[] getInputSet(GraphNode nfa)
    {
        GraphNode ptr=null;
        IntrnlNode ptrLinks=null;
        
        //get all input names
        HashSet<Character> inputs = new HashSet<Character>();
        
        char c;               
        
        ptr=nfa;
        while(ptr!=null)
        {
            ptrLinks=ptr.links;
            while(ptrLinks!=null)
            {                
                c=ptrLinks.value.charAt(0);                
                if(c!=SpecialSymbol.EPSILON)
                {
                    if(!inputs.contains(c))
                    { inputs.add(c); }                    
                }
                ptrLinks=ptrLinks.next;
            }
            ptr=ptr.nextSt;
        }
        
        Character in[]=new Character[inputs.size()];
        in=inputs.toArray(in);
        
        return in;      
                
    }
    //-------------------------
    
    private static HashSet<String> EClosure(HashSet<String> T, GraphNode nfa)
    {
        HashSet<String> U = new HashSet<String>();
        
        
              
        try
        {
            GraphNode ptr=null,u=null; 
            IntrnlNode ptrLink=null;
            String ctmp;
            Stack<String> stack = new Stack<String>();
            
            //(1)push state to stack 
              String setStates[] = new String[T.size()];
              setStates=T.toArray(setStates);
              
              for(int i=0;i<setStates.length;i++)
              {
                  stack.push(setStates[i]);
              }
                     
              
              
            //(2)initialize eclosure(T) U to T
              U.addAll(T);
            
            //(3)while loop
              String t=null;
              while(!stack.empty())
              {
                  //(4)pop top element
                    t=stack.pop();
                    
                  //(5) for each state u with edge from t ->u with E
                    //get the state to ptr
                    //System.out.println("Searching "+t+" ...");
                    
                    ptr=nfa;
                    while(ptr.nextSt!=null)
                    { 
                        //System.out.println(ptr.lb);
                        if(ptr.lb.equals( String.valueOf(t) ) )
                        {  break; }
                        ptr=ptr.nextSt; 
                    }
                    
                    //System.out.println("|"+ptr.lb+"|"+ptr.links+"|"+ptr.nextSt);
                    
                    //for t -> u 
                    ptrLink=ptr.links;
                    while(ptrLink!=null)
                    {
                        ctmp=ptrLink.value;                        
                        if(ctmp.equals(String.valueOf(SpecialSymbol.EPSILON)) )
                        {  
                            //System.out.println(ptrLink.addr.lb);                        
                            //(6) u not in U
                            ctmp=ptrLink.addr.lb;
                            if(!U.contains(ctmp))
                            {
                                U.add(ctmp);        //(7) add to U
                                stack.push(ctmp);   //(8) push to stack
                            }
                        }
                        ptrLink=ptrLink.next;
                    }
                    
              }
              
            
            
        }catch(Exception e)
        {   System.out.println("EClosure() | Error in stack : "+e); }
        
        
        //System.out.println("### U : "+U+" ###");
        return U;
    }
    
    //-------------------------
    
    private static HashSet<String> move(HashSet<String> T, char input, GraphNode nfa)
    {
        HashSet<String> retSet = new HashSet<String>();
        GraphNode ptr=null;
        IntrnlNode ptrLink=null;
        
        //get all states in T
        String setStates[] = new String[T.size()];
        setStates=T.toArray(setStates);
        
        //check for each state
        String c=null;
        for(int i=0;i<setStates.length;i++)
        {
            ptr=nfa;
            while(ptr.nextSt!=null)
            {
                c=ptr.lb;
                if(c.equals(setStates[i]))
                {
                    //search for input transition in the list
                    ptrLink=ptr.links;
                    while(ptrLink!=null)
                    {
                        if(String.valueOf(input).equals(ptrLink.value))
                        {
                            retSet.add(ptrLink.addr.lb);
                        }
                        ptrLink=ptrLink.next;
                    }                    
                    break;
                }
                ptr=ptr.nextSt;
            }
            
        }
        
        //System.out.println("### retSet : "+retSet+" ###");        
        return retSet;
        
    }
    
    //-------------------------
    public static DtransNode genDFAfromNFA(GraphNode nfa)
    {
        GraphNode ptrNFA=null;
        DtransNode dTrans=null,ptrDTrans=null;;
        inputs = getInputSet(nfa);
        
        /*
        System.out.println("Inputs are :");
        for(int i=0;i<inputs.length;i++)
        {
            System.out.println(inputs[i]);
        }
        * 
        */
                        
            int dfaStCntr=0;                     
            
            ptrNFA=nfa;
            
            //(1) Initialse Eclosure(S0) as unmarked state in dTans
               dTrans=new DtransNode();               
               
               HashSet<String> U =new HashSet<String>();               
               U.add(ptrNFA.lb);
               U=EClosure(U, nfa);               
               dTrans.set=U;           
                              
               dTrans.lb=String.valueOf(dfaStCntr++);               
           
               dTrans.mark=false;
               
            //(2) while there is unmarked state in dtrans
               DtransNode T=null;
               
               ptrDTrans=dTrans;
               boolean addFlag=false;
               while(ptrDTrans!=null)
               {
                   if(ptrDTrans.mark==false)
                   { 
                       //System.out.println("Unmarked State : "+ptrDTrans.lb+"|"+ptrDTrans.set); 
                       //(3) Mark State
                       ptrDTrans.mark=true;                  
                       T=ptrDTrans;
                   }
                   else
                   { ptrDTrans=ptrDTrans.nextSET; continue; }
                   
                                      
                   //(4) for each input
                   for(int i=0;i<inputs.length;i++)
                   {
                       //(5) U=eclosure(move(T,a));
                       U=move(T.set,inputs[i],nfa);
                       U=EClosure( U , nfa);                       
                       
                       //(5.1) if U is empty
                       if(U.isEmpty())
                       {  continue; }
                       
                       //(6) Travrse Dtrans to check if set mactches with anyone
                       ptrDTrans=dTrans;
                       addFlag=true;
                       while(ptrDTrans!=null)
                       {
                           if(ptrDTrans.set.equals(U))
                           {  addFlag=false; break; }    //found
                           else
                           {  ptrDTrans=ptrDTrans.nextSET; }   
                       }
                       //set back ptrDtrans
                       ptrDTrans=T;
                       
                       if(addFlag==true)
                       { 
                           //(7) add as new state at last
                           dTrans=DtransNode.addDtransStateToLast(dTrans, String.valueOf(dfaStCntr++), U);
                       }
                       
                       //(8) draw edge T -> U through input 
                       T.addDtransLink(dTrans, U, String.valueOf(inputs[i]));
                       
                   }//end of for
                   
               }//end of while
               
               
               return dTrans;
               
               /*
               System.out.println("Dtrans List : ");
               ptrDTrans=dTrans;
               while(ptrDTrans!=null)
               {
                   System.out.print("State : |"+ptrDTrans.lb+"| "+ptrDTrans.set+"|"+ptrDTrans.mark+" Links : ");
                   ptrDTrans.showLinks();
                   System.out.println();
                   ptrDTrans=ptrDTrans.nextSET;
               }
               */
               
               /*
               GraphNode DFA=null,ptr=null;
               DFA=DtransNode.DtransToGraphNode(DFA, dTrans);
               
               
               System.out.println("Generated DFA : ");
               ptr=DFA;
               while(ptr!=null)
               {
                   System.out.print("State : |"+ptr.lb+"| Links : ");
                   ptr.showLinks();
                   System.out.println();
                   ptr=ptr.nextSt;
               }
               * 
               */
      
                        
        
          
        
    }
    
    //-------------------------
    
    /*
     *
     public static void getInputSet(GraphNode nfa)
    {
        GraphNode ptr=null;
        IntrnlNode ptrLinks=null;
        
        //get all input names
        char c;
        HashSet<Character> inputs = new HashSet<Character>();
        //ArrayList<Character> inputs = new ArrayList<Character>();        
        int i;
        boolean flag=true;
        
        ptr=nfa;
        while(ptr!=null)
        {
            ptrLinks=ptr.links;
            while(ptrLinks!=null)
            {
                flag=true;
                c=ptrLinks.value.charAt(0);
                
                if(c!=SpecialSymbol.EPSILON)
                {
                    for(i=0;i<inputs.size();i++)
                    {
                        if(c==inputs.get(i))
                        { flag=false; }
                    } 
                    
                    if(flag)
                    { inputs.add(c); }
                }
                
                
                
                
                ptrLinks=ptrLinks.next;
            }
            ptr=ptr.nextSt;
        }
        
        System.out.println("Inputs are : "+inputs);
        
        ArrayList<Character> ls = new ArrayList<Character>();
        
        ls.add('b');
        ls.add('a');
        System.out.println("ls : "+ls);
        System.out.println("inputs equals ls :"+(inputs==ls) );
        
    }
     * 
     */
    
    public static mxGraphComponent createMxGraph(GraphNode start) throws Exception
    {
        
        final String STYLE_START="shape=ellipse;fillColor=#ffad07;strokeColor=#990033;fontColor=#990033;fontSize=15";
        final String STYLE_NORMAL="shape=ellipse;fillColor=#ffffcc;strokeColor=#990033;fontColor=#990033;fontSize=15";
        final String STYLE_ACC="shape=doubleEllipse;fillColor=#82DAFF;strokeColor=#1186C0;fontColor=#255372;fontSize=15";
        final String STYLE_RT="shape=doubleEllipse;fillColor=#b4b4b4;strokeColor=#000000;fontColor=#000000;fontSize=15";
       
        if(start==null)
        { return null; }
        
        int x=10,y=10;
        //final int W=120,H=15,HGAP=80,VGAP=80;
        final int R=35,HGAP=50,VGAP=50;
        String style="",retract="";
        GraphNode ptr=start;
        IntrnlNode ptrLnk=null;
        HashMap<GraphNode,Object> map = new HashMap<GraphNode, Object>();
        
        mxGraph graph = new mxGraph(); 
        Object parent = graph.getDefaultParent();           
	graph.getModel().beginUpdate();
        Object vertx;
        try
        {
            switch(ptr.state)
            {
                case 'i' : style=STYLE_START; retract=""; break;                
                case 'F' : style=STYLE_ACC; retract=""; break;
                case '*' : style=STYLE_RT; retract="*"; break;
                default : style=STYLE_NORMAL; retract=""; break;
            }
            vertx=graph.insertVertex(parent, null, ptr.lb+retract, x, y ,R, R,style);
            x=HGAP+R;
            map.put(start, vertx);
                        
            while(ptr!=null)
            {
                ptrLnk=ptr.links;
                while(ptrLnk!=null)
                {
                    if(!map.containsKey(ptrLnk.addr))  //chk if node has already been created
                    {
                        //if not create new vertex
                        switch(ptrLnk.addr.state)
                        {
                            case 'i' : style=STYLE_START; retract=""; break;                
                            case 'F' : style=STYLE_ACC; retract=""; break;
                            case '*' : style=STYLE_RT; retract="*"; break;
                            default : style=STYLE_NORMAL; retract=""; break;
                        }                       
                        vertx=graph.insertVertex(parent, null, ptrLnk.addr.lb+retract, x, y ,R, R,style);
                        y+=VGAP+R;
                        map.put(ptrLnk.addr, vertx);
                        
                        
                    }
                    //create edge
                    //graph.insertEdge(parent, null, ptrLnk.value, map.get(ptr), map.get(ptrLnk.addr));  
                    graph.insertEdge(parent, null, ptrLnk.value, map.get(ptr), map.get(ptrLnk.addr),"strokeColor=black;fontColor=black;fontSize=16");  
                    
                    ptrLnk=ptrLnk.next;
                }
                
                /*
                name=ptr.toStringNode();
                h=H*getNoOfLines(name);
                graph.insertVertex(parent, null, name, x, y ,W, h,"fillColor=#ffffcc");
                x=HGAP+W;
                */
                if(ptr.links!=null)
                {
                    x+=HGAP+R;
                    y=10;                    
                }
                
                ptr=ptr.nextSt;
            }   
            
        }        
        finally
        { graph.getModel().endUpdate(); }
	
        graph.setCellsResizable(false);
        graph.setDisconnectOnMove(false);
        graph.getOutgoingEdges(false);
        graph.setCellsEditable(false);
        graph.setAllowDanglingEdges(false);     
        //graph.setCellsLocked(true);
                
	mxGraphComponent graphComponent = new mxGraphComponent(graph);        
        //graphComponent.setBackground(Color.red);
        graphComponent.setConnectable(false);
        
        //new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());   //Automatic Layout
        new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());   //Prevent parallel edge overlaping
        
        return graphComponent;
        
        
    }
    
}


