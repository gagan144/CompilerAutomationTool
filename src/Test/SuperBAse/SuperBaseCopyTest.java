package Test.SuperBAse;

import commanLib.GraphNode;
import commanLib.ModelData;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;



class ParentClass implements Serializable
{
    protected int i;    

    public ParentClass() 
    {
        i=0;        
    }

    
    
    public ParentClass(ParentClass p) 
    {
        i=p.i;               
    }   
    
    public void setPData(int x)
    { i=x; }
    
    public String toString()
    {
        return "Parent Class Data : ["+i+"]";
    }
}



class ChildClass extends ParentClass
{
    private char c;

    public ChildClass() 
    {
    }

    public ChildClass(ParentClass p,char a) 
    {
        super(p);
        c=a;
    }
    
    
    
    
    public void setCData(char v)
    { c=v; }
    
    public String toString()
    {
        return "Child Class Data : ["+i+","+c+"]";
    }
}

public class SuperBaseCopyTest 
{
    public static void main(String args[])
    {
       
        ParentClass p = new ParentClass();
        p.setPData(4);
        System.out.println(p);
        
        ParentClass p2 = new ParentClass(p);
        System.out.println(p2);
        
        
        ChildClass child = new ChildClass(p,'x');
        //child.setCData('g');
        System.out.println(child);
        
    }
        
}
