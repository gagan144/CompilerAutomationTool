package commanLib;


public class ParseTreeNode 
{
    public String lb;       
    public ParseTreeNode nextNd;
    public LinkList_PrsTrNd links;

    public ParseTreeNode() 
    {
        lb="";        
        nextNd=null;
        links=null;
    }
    
    public ParseTreeNode(String label) 
    {
        lb=label; 
        nextNd=null;
        links=null;
    }
    
    public static ParseTreeNode addNodeToBeg(ParseTreeNode start,ParseTreeNode newNode)
    {
        if(start==null)
        { start=newNode; }
        else
        {
            newNode.nextNd=start;
            start=newNode;            
        }        
                
        return start;
    }
    
    
    public static ParseTreeNode addNodeToEnd(ParseTreeNode start,ParseTreeNode newNode)
    {
        if(start==null)
        { start=newNode; }
        else
        {
            ParseTreeNode ptr=start;
            while(ptr.nextNd!=null)
            { ptr=ptr.nextNd; }
        
            ptr.nextNd=newNode;
            newNode.nextNd=null; 
        }              
                
        return start;
    }
    
        
    public void addLinkToNBeg(ParseTreeNode ref)
    {        
        links=LinkList_PrsTrNd.addLinkToBeg(links, ref);
    }
  
   /*
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
                                 
            
            ptr=ptr.next;
        }
    }
    */
    
    /*
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
    */
    
   
    //temp
    public String toStringLinks()
    {
        String ret="";
        LinkList_PrsTrNd ptr=links;        
        
        if(ptr==null)
        {
            ret+="No links";
        }
        while(ptr!=null)
        {
            ret+=("|"+ptr.addr.lb+"|"+ptr.value+"| -> ");
            ptr=ptr.next;
        }
        
        return ret;
    }
    
    public String toString()
    {   
        return ("Node : "+lb+" : "+toStringLinks()); 
    }
    
}

/*
class LinkList_PrsTrNd
{
    ParseTreeNode addr;
    String value;
    LinkList_PrsTrNd next;

    public LinkList_PrsTrNd()
    {
        addr=null;
        value="";
        next=null;
    }
    
    public LinkList_PrsTrNd(ParseTreeNode address, String value)
    {
        addr=address;
        this.value=value;
        next=null;
    }
    
    public static LinkList_PrsTrNd addLinkToBeg(LinkList_PrsTrNd start, ParseTreeNode ref)
    {
        if(start==null)
        {  start = new LinkList_PrsTrNd(ref,""); }
        else    
        {
            LinkList_PrsTrNd newLink= new LinkList_PrsTrNd(ref,"");
            newLink.next=start;
            start=newLink;
        }
        return start;
    }
    
}
*/
