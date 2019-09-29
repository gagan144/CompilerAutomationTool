package commanLib;

public class ParseTblElmnt 
{    
    public static final int ERROR=-1;
    public static final int STATENO=0;
    public static final int SHIFT=1;
    public static final int REDUCE=2;
    public static final int ACCEPT=3;
    
    private int type,value;
    
    public ParseTblElmnt()
    {
        type=ParseTblElmnt.ERROR;
        value=0;
    }
    
    public ParseTblElmnt(int type, int value ) 
    {
        if(type<-1 || type>4)
        { this.type=-1; }
        else
        { this.type=type; }
        
        this.value=value;        
    }        
    
    public boolean isError()
    {
        if(type==ERROR)
        { return true; }
        else
        { return false; }
    }
    
    public boolean isStateNo()
    {
        if(type==STATENO)
        { return true; }
        else
        { return false; }
    }
    
    public boolean isShift()
    {
        if(type==SHIFT)
        { return true; }
        else
        { return false; }
    }
    
    public boolean isReduce()
    {
        if(type==REDUCE)
        { return true; }
        else
        { return false; }
    }
    
    public boolean isAccept()
    {
        if(type==ACCEPT)
        { return true; }
        else
        { return false; }
    }
    
    public int retValue()
    { return value; }
    
    public int retType()
    { return type; }
 
    public String toString()
    {
        String ret="";
        
        switch(type)
        {
            case ERROR : ret="e"+value; break; 
            case STATENO : ret=String.valueOf(value); break;
            case SHIFT : ret="s"+value; break;
            case REDUCE : ret="r"+value; break;
            case ACCEPT : ret="acc"; break;
            default : ret="Inavlid Entry";
        }
        
        return ret;
    }
}
