package Test;

import commanLib.Production;
import java.util.ArrayList;

public class LeftFactTest 
{
    public static void main(String[] args) 
    {
        
        ArrayList<String> l1,l2,r1,r2;
        l1=new ArrayList<String>();
        l2=new ArrayList<String>();
        r1=new ArrayList<String>();
        r2=new ArrayList<String>();
        
        l1.add("A");
        l2.add("A");
        
        r1.add("B");
        r1.add("C");
        r1.add("D");
        r1.add("E");
        
        r2.add("B");
        r2.add("C");
        r2.add("D");
        r2.add("F");
        
        Production p1,p2;
        p1=new Production(null,null);//p1=new Production(l1,r1);
        p2=new Production(l2,r2);
        
        System.out.println(p1.retShortestCommanSequenceIndx(p2));
    }
    
}
