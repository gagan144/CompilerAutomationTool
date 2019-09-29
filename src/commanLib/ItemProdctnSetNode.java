package commanLib;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ItemProdctnSetNode
{
    public int label;
    public ArrayList<ItemProduction> prodSet = null; //new ArrayList<ItemProduction>();
    
    public ItemProdctnSetNode nextNode=null;
    public ItmPrdSt_InternalNode links=null;

    public ItemProdctnSetNode(int lb) 
    {
        label=lb;
        prodSet = new ArrayList<ItemProduction>();
    }
    
    public ItemProdctnSetNode(int lb,ArrayList<ItemProduction> prodSet) 
    {
        label=lb;
        this.prodSet=prodSet;
    }
    
    public void addLink(ItemProdctnSetNode nodeRef,String value)
    {
        if(links==null)
        {
            links = new ItmPrdSt_InternalNode(nodeRef,value);            
        }
        else
        {
            ItmPrdSt_InternalNode.addIntrnlNodeToLast(links, nodeRef, value);
        }
    }
    
    
    public boolean isEqualTo(ItemProdctnSetNode node)
    {
        //boolean ret=false;
        
        if(prodSet.size()!=node.prodSet.size())   //Label chk ???
        { return false; }        
        else
        {
            for(int i=0;i<prodSet.size();i++)
            {
                boolean flag=false;
                for(int j=0;j<node.prodSet.size();j++)
                {
                    if(prodSet.get(i).isEqualTo(node.prodSet.get(j)))
                    { flag=true; break; }
                    else
                    { flag=false; }
                }
                if(flag==false)
                { return flag; }
            }
        }
        
        return true;
    }
    
    public boolean isItProdPresentInSet(ItemProduction itProd)
    {
        for(int i=0;i<prodSet.size();i++)
        {
            if(prodSet.get(i).isEqualTo(itProd))
            { return true; }
        }
        
        return false;
    }
    
    void SLRClosure(String symbl, Grammar gramr, int no)
    {
        Production currProd=null;
        ItemProduction newItProd=null;
        
        //System.out.println("Remaining Grammar : \n"+gramr.toString());
        
        for(int i=0;i<gramr.grmrProdctns.size();i++)
        {
            currProd=gramr.grmrProdctns.get(i);
            if(gramr.isNonTerminal(symbl) && currProd.left.get(0).equals(symbl))
            //if(currProd.left.get(0).equals(symbl))
            {
                newItProd=new ItemProduction(currProd.left, currProd.right);
                
                if(!isItProdPresentInSet(newItProd))
                { 
                    //System.out.println("Adding : "+newItProd.toString());
                    prodSet.add(newItProd); 
                    
                    //System.out.println("Removing : "+gramr.grmrProdctns.get(i));
                    gramr.grmrProdctns.remove(i);
                    i--;
                }
            }                
        }
        no++;
        
        if(no!=prodSet.size())   
        {
            if(!prodSet.get(no).isEpsilonProduction())    // EPSILON ADD
            { SLRClosure(prodSet.get(no).getSymbolAfterDot(), gramr, no); }
        }
    }
    
    public String getProdtcString()
    {
        String ret="";
                
        for(int i=0;i<prodSet.size();i++)
        {
            ret+=prodSet.get(i).toString()+"\n";
        }
       
        return ret;        
    }
    
    
    void closureCLR(String symbl, Grammar gramr, int no, String nextSymbl, HashSet<String> la)
    {   
        /*
        Production currProd=null;
        ItemProduction newItProd=null;
        HashSet<String> new_la;
        //System.out.println("Remaining Grammar : "+gramr.toString());
        
        String e= new String();
        e= String.valueOf(SpecialSymbol.EPSILON);
        try
        {
            gramr.calculateFisrtFollow();              //&&&&&&&&&&&&&&&&&&&&&&&&&  cmnt it
        }catch(Exception ex)
        { System.out.println("Closure CLR - exception in  calculating first follow!"); }
        
        int i;
        for(i=0;i<gramr.grmrProdctns.size();i++)
        {
            new_la= new HashSet<String>();
            currProd=gramr.grmrProdctns.get(i);
            if(gramr.isNonTerminal(symbl) && currProd.left.get(0).equals(symbl))
            {
                //making new lookahead for the new item production to be added
                
                if(!nextSymbl.equals("empty"))
                {
                    if(!gramr.isNonTerminal(nextSymbl))
                        new_la.add(nextSymbl);
                    else
                    {
                        HashMap<String,HashSet<String>> xyz= gramr.returnAllFirst();
                        //System.out.println(xyz);
                        if(xyz.containsKey(nextSymbl))
                            new_la.addAll(xyz.get(nextSymbl));
                        
                        //for(int k=0; k<xyz.size(); k++)
                           // new_la.addAll(xyz);
                        if(new_la.contains(e))
                        {
                            new_la.remove(e);
                            new_la.addAll(la);                                
                        }
                    }//end of else ie. if next symbol is a non terminal
                }//end of if(!nextSymbl.equals("empty"))
                else
                    new_la.addAll(la);
                //System.out.println(new_la);
                //finish of making of lookahead
                newItProd=new ItemProduction(currProd.left, currProd.right, new_la);
                if(!isItProdPresentInSet(newItProd))
                { 
                    prodSet.add(newItProd); 
                    //gramr.grmrProdctns.remove(i);
                    //i--;
                }
            }
        }//end of for loop
        no++;
        
        if(no!=prodSet.size())
        {
            //change made here only
            //gramr.grmrProdctns.add(i, currProd);
            for(int k=0; k<(prodSet.size()-no); k++)
            {   
                int m= no+k;
                closureCLR(prodSet.get(m).getSymbolAfterDot(), gramr, m, prodSet.get(m).getSecondSymbolAfterDot(), prodSet.get(m).getLookAhead());
            }
        }
        */
        
        Production currProd=null;
        ItemProduction newItProd=null;
        HashSet<String> new_la, all_rec_la; //yaha change hai.........................
        all_rec_la= new HashSet<String> ();  //yaha change hai.........................
        //System.out.println("Remaining Grammar : "+gramr.toString());
        
        String e= new String();
        e= String.valueOf(SpecialSymbol.EPSILON);
        try
        {
            gramr.calculateFisrtFollow();              //&&&&&&&&&&&&&&&&&&&&&&&&&  cmnt it
        }catch(Exception ex)
        { System.out.println("Closure CLR - exception in  calculating first follow!"); }
        
        int i;
	//yaha change hai.........................this for loop is newly added
        for(i=0;i<gramr.grmrProdctns.size();i++)
        {
            currProd=gramr.grmrProdctns.get(i);
            if(currProd.left.get(0).equals(symbl) && currProd.right.get(0).equals(symbl))
            {
                all_rec_la.add(currProd.right.get(1));
            }
        }
	//change till here................
        for(i=0;i<gramr.grmrProdctns.size();i++)
        {
            new_la= new HashSet<String>();
            currProd=gramr.grmrProdctns.get(i);
            if(currProd.left.get(0).equals(symbl))    //yaha change hai.............
            {
                //making new lookahead for the new item production to be added
                
                if(!nextSymbl.equals("empty"))
                {
                    if(!gramr.isNonTerminal(nextSymbl))
                        new_la.add(nextSymbl);
                    else
                    {
                        HashMap<String,HashSet<String>> xyz= gramr.returnAllFirst();
                        //System.out.println(xyz);
                        if(xyz.containsKey(nextSymbl))
                            new_la.addAll(xyz.get(nextSymbl));
                        
                        //for(int k=0; k<xyz.size(); k++)
                           // new_la.addAll(xyz);
                        if(new_la.contains(e))
                        {
                            new_la.remove(e);
                            new_la.addAll(la);                                
                        }
                    }//end of else ie. if next symbol is a non terminal
                }//end of if(!nextSymbl.equals("empty"))
                else
                    new_la.addAll(la);
                if(!all_rec_la.isEmpty())     //yaha change hai.........................
                    {new_la.addAll(all_rec_la);}     //yaha change hai.........................

                //finish of making of lookahead
                newItProd=new ItemProduction(currProd.left, currProd.right, new_la);
                if(!isItProdPresentInSet(newItProd))
                { 
                    prodSet.add(newItProd); 
                    gramr.grmrProdctns.remove(i);    //yaha change hai.........................
                    i--;        //yaha change hai.........................
                }
            }
        }//end of for loop
        no++;
        
        if(no!=prodSet.size())
        {
            //change made here only
            gramr.grmrProdctns.add(i, currProd);       //yaha change hai.........................
            for(int k=0; k<(prodSet.size()-no); k++)
            {   
                int m= no+k;
                if(!prodSet.get(m).isEpsilonProduction() && gramr.isNonTerminal(prodSet.get(m).getSymbolAfterDot()))     //MYCHANGE      //yaha change hai.........................
                	closureCLR(prodSet.get(m).getSymbolAfterDot(), gramr, m, prodSet.get(m).getSecondSymbolAfterDot(), prodSet.get(m).getLookAhead());
            }
        }
    }
    
    public void simplifyItemSetNode()
    {
                   if(prodSet.size()>1)
                   {
                      
                        ItemProduction currentItProd;
                        for(int x=0; x< prodSet.size(); x++)
                        {
                                currentItProd = prodSet.get(x);
                                
                                for(int y=0; y<prodSet.size(); y++)
                                {
                                    if(x!=y && currentItProd.isCoreEqualTo(prodSet.get(y)))
                                    {
                                        currentItProd.addLookAhead(prodSet.remove(y).getLookAhead());
                                        y--;
                                    }
                                }
                        }
                   }
    }
    
    public boolean isCoreEqual(ItemProdctnSetNode node)
    {   
        if(prodSet.size()!= node.prodSet.size())
        { return false; } 
        else
        {
            for(int i=0;i<prodSet.size();i++)
            {
                boolean flag=false;
                for(int j=0;j<node.prodSet.size();j++)
                {
                    if(prodSet.get(i).isCoreEqualTo(node.prodSet.get(j)))
                    { flag=true; break; }
                    else
                    { flag=false; }
                }
                if(flag==false)
                { return flag; }
            }
        }
        
        return true;
    }
    
    
    
    public boolean mergeSameCoreNodeIntoIt(ItemProdctnSetNode node)
    {
        if(!this.isCoreEqual(node))
        { return false; }
        
        for(int i=0;i<prodSet.size();i++)
        {                
                for(int j=0;j<node.prodSet.size();j++)
                {
                    //if(prodSet.get(i).isCoreEqualTo(node.prodSet.get(j)))
                    if(prodSet.get(i).mergeLookAheads(node.prodSet.get(j))) 
                    { break;  }                    
                }                
        }
        
        return true;
    }
    
    
    //staic functions
    
    public static ItemProdctnSetNode addSetNodeToGraph(ItemProdctnSetNode start, ItemProdctnSetNode newNode)
    {
        ItemProdctnSetNode ptr=start;
        while(ptr.nextNode!=null)
        { ptr=ptr.nextNode; }
        
        ptr.nextNode=newNode;
        
        return start;
    }
    
    public static ItemProdctnSetNode doesSetNodeExistInGraph(ItemProdctnSetNode start, ItemProdctnSetNode node)
    {
        ItemProdctnSetNode ptr=start,ref=null;
        
        while(ptr!=null)
        {
            if(ptr.isEqualTo(node))
            {
                ref=ptr;
            }
            ptr=ptr.nextNode;
        }
        
        return ref;
    }
    
    public static ItemProdctnSetNode getNodeRefFromGraph(ItemProdctnSetNode start,int label)
    {
        ItemProdctnSetNode ptr;
        
        ptr=start;
        while(ptr!=null)
        {
            if(ptr.label==label)
            { return ptr; }
            ptr=ptr.nextNode;
        }
        
        return null;
    }
    
    public static ItemProdctnSetNode createCopyOfEntireGraph(ItemProdctnSetNode start)
    {
        ItemProdctnSetNode newStart=null,ptr,ptrNew,ptrNew2;
        
        if(start==null)
        { return null; }
        
        //(A)copy all nodes        
        
        //copy start state       
        newStart= new ItemProdctnSetNode(start.label);
        for(int i=0;i<start.prodSet.size();i++)
        {
                newStart.prodSet.add(start.prodSet.get(i).createCopy());
        }
        
        ptr=start.nextNode;      
        ptrNew=newStart;
        while(ptr!=null)
        {
            ptrNew2= new ItemProdctnSetNode(ptr.label);
            for(int i=0;i<ptr.prodSet.size();i++)
            {
                ptrNew2.prodSet.add(ptr.prodSet.get(i).createCopy());
            }
            ptrNew.nextNode=ptrNew2;
            
            ptrNew=ptrNew2;            
            ptr=ptr.nextNode;
        }
                
        
        //(B) Create Links
        ptr=start;
        ptrNew=newStart;
        ItmPrdSt_InternalNode ptrLnk,newPtrLnk;        
        ItemProdctnSetNode ref;
        while(ptr!=null)
        {
            ptrLnk=ptr.links;
            while(ptrLnk!=null)
            {
                //get ref
                ref = ItemProdctnSetNode.getNodeRefFromGraph(newStart, ptrLnk.addr.label );
                
                //add link to new Graph
                ptrNew.addLink(ref,ptrLnk.value);
                
                ptrLnk=ptrLnk.nextLnk;
            }
            
            ptr=ptr.nextNode;
            ptrNew=ptrNew.nextNode;                   
        }
        
        /*
        //temp
        System.out.println("New DFA : ");
        ptrNew=newStart;
        while(ptrNew!=null)
        {
            System.out.println(ptrNew.toString());
            ptrNew=ptrNew.nextNode;
        }
        */       
        
        
        return newStart;
    }
    
    public static int getTotalNoOfNodes(ItemProdctnSetNode start)
    {
        int count=0;
        ItemProdctnSetNode ptr=start;
        while(ptr!=null)
        {
            count++;
            ptr=ptr.nextNode;
        }
        return count;
    }
    
    public static int getNodeIndex(ItemProdctnSetNode start,ItemProdctnSetNode node)
    {
        int idx=-1;
        ItemProdctnSetNode ptr=start;
        while(ptr!=null)
        {
            idx++;
            if(ptr==node)
            { return idx; }
            
            ptr=ptr.nextNode;
        }
        
        return idx;
    }
    
    public static void renameNodesInOrder(ItemProdctnSetNode start)
    {
        int idx=-1;
        ItemProdctnSetNode ptr=start;
        while(ptr!=null)
        {
            ptr.label=++idx;
            
            ptr=ptr.nextNode;
        }
        
    }
    
    private static int getNoOfLines(String str)
    {        
        if(str==null || str.equals(""))
        { return 0; }
     
        int lines=1;
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i)=='\n')
            { lines++; }
        }
        
        return lines;
    }
    
    public static mxGraphComponent createMxGraph(ItemProdctnSetNode start) throws Exception
    {
        if(start==null)
        { return null; }
        
        int x=10,y=10,h;
        final int W=120,H=15,HGAP=80,VGAP=80;
        String name="";
        ItemProdctnSetNode ptr=start;
        ItmPrdSt_InternalNode ptrLnk=null;
        HashMap<ItemProdctnSetNode,Object> map = new HashMap<ItemProdctnSetNode, Object>();
        
        mxGraph graph = new mxGraph(); 
        Object parent = graph.getDefaultParent();           
	graph.getModel().beginUpdate();
        Object vertx;
        try
        {
            name=ptr.toStringNode();
            h=H*getNoOfLines(name);
            vertx=graph.insertVertex(parent, null, name, x, y ,W, h,"fillColor=#ffffcc;strokeColor=#990033;fontColor=#990033");
            x=HGAP+W;
            map.put(start, vertx);
                        
            while(ptr!=null)
            {
                ptrLnk=ptr.links;
                while(ptrLnk!=null)
                {
                    if(!map.containsKey(ptrLnk.addr))  //chk if node has already been created
                    {
                        //if not create new vertex
                        name=ptrLnk.addr.toStringNode();
                        h=H*getNoOfLines(name);
                        vertx=graph.insertVertex(parent, null, name, x, y ,W, h,"fillColor=#ffffcc;strokeColor=#990033;fontColor=#990033");
                        y+=VGAP+h;
                        map.put(ptrLnk.addr, vertx);
                        
                        
                    }
                    //create edge
                    //graph.insertEdge(parent, null, ptrLnk.value, map.get(ptr), map.get(ptrLnk.addr));  
                    graph.insertEdge(parent, null, ptrLnk.value, map.get(ptr), map.get(ptrLnk.addr),"strokeColor=black;fontColor=black;fontSize=15");  
                    
                    ptrLnk=ptrLnk.nextLnk;
                }
                
                /*
                name=ptr.toStringNode();
                h=H*getNoOfLines(name);
                graph.insertVertex(parent, null, name, x, y ,W, h,"fillColor=#ffffcc");
                x=HGAP+W;
                */
                if(ptr.links!=null)
                {
                    x+=HGAP+W;
                    y=10;                    
                }
                
                ptr=ptr.nextNode;
            }   
            
        }        
        finally
        { graph.getModel().endUpdate(); }
	
        graph.setCellsResizable(false);
        graph.setDisconnectOnMove(false);
        graph.getOutgoingEdges(false);
        graph.setCellsEditable(false);
        graph.setAllowDanglingEdges(false);        
                
	mxGraphComponent graphComponent = new mxGraphComponent(graph);        
        //graphComponent.setBackground(Color.red);
        graphComponent.setConnectable(false);
        
        new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());   //Automatic Layout
        new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());   //Prevent parallel edge overlaping
        
        return graphComponent;
        
        
    }
    
    
    //-----------
    
    public String toStringNode() 
    { 
        String ret="";
        
        ret+="Label : "+label+"\n\nItem Productions : \n";
        for(int i=0;i<prodSet.size();i++)
        {
            ret+=prodSet.get(i).toString()+"\n";
        }
                
        return ret;
        
    }

    public String toStringLinks()
    {
        String ret="";
        ItmPrdSt_InternalNode ptr=links;        
        
        if(ptr==null)
        {
            return "No links";
        }
        while(ptr!=null)
        {
            ret+="|"+ptr.addr.label+"|"+ptr.value+"|";
            
            if(ptr.nextLnk!=null)
            {
                ret+=" "+SpecialSymbol.PRODUCTION_ARROW+" ";
            }
            ptr=ptr.nextLnk;
        }
        return ret;
    }
    
    @Override
    public String toString() 
    {
        String ret="----------------------------\n";
        
        ret+="Label : "+label+"\n\nProductions : \n";
        for(int i=0;i<prodSet.size();i++)
        {
            ret+=prodSet.get(i).toString()+"\n";
        }
        
        //ret+="\nNext Node : "+nextNode+"\nLinks : "+links;
        ret+="\nNext Node : ";
        if(nextNode==null)
        { ret+="null"; }
        else
        { ret+=nextNode.label; }
        
        ret+="\nLinks : "+toStringLinks();
        
        ret+="\n----------------------------\n";        
        return ret;
        
    }
    
    
    
}
class ItmPrdSt_InternalNode
{
    ItemProdctnSetNode addr=null;
    String value="";
    ItmPrdSt_InternalNode nextLnk=null;

    public ItmPrdSt_InternalNode() 
    {
        addr=null;
        value="";
        nextLnk=null;
    }
    
    public ItmPrdSt_InternalNode(ItemProdctnSetNode addr,String value)
    {
        this.addr=addr;
        this.value=value;
        nextLnk=null;
    }
       
    
    //static methods
    public static ItmPrdSt_InternalNode addIntrnlNodeToLast(ItmPrdSt_InternalNode start,ItemProdctnSetNode nodeRef,String value)
    {
        ItmPrdSt_InternalNode ptr=start;
        
        while(ptr.nextLnk!=null)
        {
            ptr=ptr.nextLnk;
        }
        
        ptr.nextLnk= new ItmPrdSt_InternalNode(nodeRef, value);
        
        return start;
        
    } 
    
    
            
}
