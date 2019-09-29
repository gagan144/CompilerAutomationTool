package commanLib;

public class LinkList_PrsTrNd
{
    public ParseTreeNode addr;
    public String value;
    public LinkList_PrsTrNd next;

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
