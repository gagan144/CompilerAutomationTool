package commanLib;

import java.util.ArrayList;
import java.util.HashSet;

public class ItemProduction extends Production
{
    private int dotPos;                       //if dotPos==1 then it means dot is present before the element at 1 
    private boolean chkflag;
    private HashSet<String> lookAhead=null;    

       
    public ItemProduction(ArrayList<String> lft, ArrayList<String> rt) 
    {
        super(lft, rt);
        dotPos=0;
        /*
        if(isEpsilonProduction())
        { dotPos=right.size(); }
        else
        { dotPos=0; }
        */
        chkflag = false;
        
        lookAhead=new HashSet<String>();
        
        if(isEpsilonProduction())
        {
            incrementDotPositon();
            chkflag=true;
        }
    }
    
    public ItemProduction(ArrayList<String> lft, ArrayList<String> rt, HashSet<String> lahead) 
    {
        super(lft, rt);
        dotPos=0;
        
        /*
        if(isEpsilonProduction())      
        { dotPos=right.size(); }
        else
        { dotPos=0; }
        */
        chkflag = false;
        
        //if(lookAhead==null)
        lookAhead= new HashSet<String>();
        lookAhead.addAll(lahead); 
        
        if(isEpsilonProduction())
        {
            incrementDotPositon();
            chkflag=true;
        }
    }
    
    /*
    public boolean isEpsilonProduction()     //in production class
    {        
        if(right.get(0).equals(String.valueOf(SpecialSymbol.EPSILON)))
        { return true; }
        else
        { return false; }
    }
    */
    
    public HashSet<String> getLookAhead()
    {
        return lookAhead;
    }
    
    public void addLookAhead(HashSet<String> lookAhd)                     //Self Added
    {
        lookAhead.addAll(lookAhd);
    }
    
    public void incrementDotPositon()
    {
        if(!isDotAtLast())
        { dotPos++; }    
        
        
        if(dotPos==right.size())
        { chkflag=true; }
        
    }
    
    public String getSymbolAfterDot()
    {
        if(!isDotAtLast())
        {
            return right.get(dotPos);
        }        
        return null;
    }
    
    public String getSecondSymbolAfterDot()
    {
        if(!isDotAtLast())
        {
            if(dotPos+1 == right.size())
                return "empty";
            else
                return right.get(dotPos+1);
        }        
        return "empty";
    }
    
    
    public boolean hasProdBeenProcessed()
    {
        /*
        if(dotPos==right.size())
        { return true; }
        else
        { return false; }
        */
        return chkflag;
    }
    
    
    public void setProcessed()
    { chkflag=true; } 
    
    
    public boolean isDotAtLast()
    {
        if(dotPos==right.size())
        { return true; }
        else
        { return false; }
    }
    
    public boolean isPresent(String grmSymb)
    {                
        if(dotPos!=right.size() && right.get(dotPos).equals(grmSymb) )
        {
            return true;                
        }        
        return false;        
    }
    
    public boolean isEqualTo(ItemProduction prod)
    {
        if(left.size()==prod.left.size() && right.size()==prod.right.size() && dotPos==prod.dotPos)
        {   
            if(lookAhead!=null || !lookAhead.isEmpty())
            {
                if(!lookAhead.equals(prod.lookAhead))
                { return false; }
            }
            
            //left side            
            for(int i=0;i<left.size();i++)
            {
                if(! left.get(i).equals(prod.left.get(i)) )
                { return false; }
            }
            
            //right side            
            for(int i=0;i<right.size();i++)
            {
                if(! right.get(i).equals(prod.right.get(i)) )
                { return false; }
            }
            
            return true;
        }
        else    
        { return false; }
    }
    
    public boolean isCoreEqualTo(ItemProduction prod)
    {
        if(left.size()==prod.left.size() && right.size()==prod.right.size() && dotPos==prod.dotPos)
        {   
            //left side            
            for(int i=0;i<left.size();i++)
            {
                if(! left.get(i).equals(prod.left.get(i)) )
                { return false; }
            }
            
            //right side            
            for(int i=0;i<right.size();i++)
            {
                if(! right.get(i).equals(prod.right.get(i)) )
                { return false; }
            }
            
            return true;
        }
        else    
        { return false; }
    }
    
    public boolean mergeLookAheads(ItemProduction itmProd)
    {
        if(!this.isCoreEqualTo(itmProd))
        { return false; }
        
        for(Object i : itmProd.lookAhead.toArray())
        { 
            if(!lookAhead.contains(i.toString()))
            { lookAhead.add(i.toString()); }
        }
        
        return true;    
    }
    
    public boolean isEqualToProduction(Production prod)
    {
        if(left.size()==prod.left.size() && right.size()==prod.right.size())
        {                           
            //left side            
            for(int i=0;i<left.size();i++)
            {
                if(! left.get(i).equals(prod.left.get(i)) )
                { return false; }
            }
            
            //right side            
            for(int i=0;i<right.size();i++)
            {
                if(! right.get(i).equals(prod.right.get(i)) )
                { return false; }
            }
            
            return true;
        }
        else    
        { return false; }
    }

    public ItemProduction createCopy()
    {
        ArrayList<String> cpyLft = (ArrayList<String>) left.clone();
        ArrayList<String> cpyRght = (ArrayList<String>) right.clone();
        
        ItemProduction copyVar = new ItemProduction(cpyLft, cpyRght);
        copyVar.dotPos=dotPos;
        copyVar.lookAhead = (HashSet<String>) lookAhead.clone();
        
        return copyVar;
    }
    
    @Override
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
            if(i==dotPos)
            { ret+="."; }
            if(!right.get(i).equals(String.valueOf(SpecialSymbol.EPSILON)))
            { ret+=right.get(i); }
        }
        
        if(dotPos==right.size())
        { ret+="."; }
        
        if(!lookAhead.isEmpty())
        {
            ret+=" , ";//+lookAhead;
        
            for(Object i : lookAhead.toArray())
            { ret+=i.toString()+"/"; }
            ret=ret.substring(0, ret.length()-1);
        }
        
        
        return ret;
    }
    
    
    
    
}
