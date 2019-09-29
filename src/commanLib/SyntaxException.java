package commanLib;

public class SyntaxException extends Exception
{
    public static final int TYPE_CHK=0;
    public static final int LEFT_REC=1;
    public static final int LEFT_FACT=2;
    public static final int FIRST_FOLLOW=3;
    public static final int LL1_TABLE=4;
    public static final int LL1_SIM=5;
    public static final int SLR_ITEMSET=6;    
    public static final int SLR_TABLE=7;
    public static final int SLR_SIM=8;
    public static final int CLR_ITEMSET=9;    
    public static final int CLR_TABLE=10;
    public static final int CLR_SIM=11;
    
    private int errNo;
    private String msg;

    public SyntaxException(int exceptionValue,String message) 
    {
        super(message);
        
        if(message==null)
        {    msg="Unanticipated Error Found! Possibly an incorrect grammar!";   }
        else
        { msg=message; }
        
        errNo=exceptionValue;
    }
    
    public String toString()
    {
        String ret="";
        
        switch(errNo)
        {
            case 0 : ret="Grammar Type Checking : "; break;
            case 1 : ret="Left Recursion : "; break;
            case 2 : ret="Left Factoring : "; break;
            case 3 : ret="First & Follow : "; break;
            case 4 : ret="Construction of LL(1) Table : "; break;
            case 5 : ret="Simulation of LL(1) : "; break;
            case 6 : ret="Construction of SLR Item Sets : "; break;
            case 7 : ret="Construction of SLR Table : "; break;
            case 8 : ret="Simulation of SLR : "; break;
            case 9 : ret="Construction of CLR Item Sets : "; break;
            case 10 : ret="Construction of CLR Table : "; break;
            case 11 : ret="Simulation of CLR : "; break;
        }
        
        ret+=msg;
        
        return ret;
    }
    
    
}
