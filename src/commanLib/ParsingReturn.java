package commanLib;

public class ParsingReturn 
{
    public final String message;
    public final ParseTreeNode treeRoot;
    
    public ParsingReturn(String result,ParseTreeNode root)
    {
        message=result;
        treeRoot=root;
    }
    
    public ParsingReturn(String result)
    {
        message=result;
        treeRoot=null;
    }
}
