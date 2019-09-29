package commanLib;

import java.util.ArrayList;

public class Production 
{
    ArrayList<String> left = new ArrayList<String>();
    ArrayList<String> right = new ArrayList<String>();
    //String left="";
    //String right="";
    
    public Production(ArrayList<String> lft,ArrayList<String> rt) 
    {
        left=lft;
        right=rt;
    }   
    
    public boolean isLeftSideSame(Production p)
    {
        if(left==null || right==null || left.size()!=p.left.size())
        { return false; }
        
        int limit=Math.min(left.size(),p.left.size());
        
        for(int i=0;i<limit;i++)
        {
            if(!left.get(i).equals(p.left.get(i)))
            { return false; }
        }
        
        return true;
    }
    
    public int retShortestCommanSequenceIndx(Production p)
    {
        ArrayList<String> right2=p.right;
        
        if(p.left==null || p.right==null)
        { return -1; }
        
        int limit=Math.min(right.size(),p.right.size());
        int retIdx=-1;        
        for(int i=0;i<limit;i++)
        {
            if(right.get(i).equals(right2.get(i)))
            {  retIdx++;    }
            else
            { break; }
        }
        
        if(retIdx==-1)
        { retIdx=Integer.MAX_VALUE; }
        
        return retIdx;
    }
    
    public boolean isEpsilonProduction()
    {        
        if(right.get(0).equals(String.valueOf(SpecialSymbol.EPSILON)))
        { return true; }
        else
        { return false; }
    }
    
    public Production createCopy()
    {
        Production newProd = new Production(null,null);
        newProd.left=(ArrayList<String>) left.clone();
        newProd.right=(ArrayList<String>) right.clone();
        
        return newProd;
    }
            
    public String toString()
    {
        String ret="";
        for(int i=0;i<left.size();i++)
        {
            ret+=left.get(i);
        }
        ret+=SpecialSymbol.PRODUCTION_ARROW;
        for(int i=0;i<right.size();i++)
        {
            ret+=right.get(i);
        }
        
        return ret;
    }
}
