package Test;

import commanLib.ModelData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class SetBreakTest 
{    
    public static void main(String args[])
    {
        /*
        String str="a, b,c,d,e,f,g";
        //String str="[a-z]";
        
        ArrayList<Character> set = new ArrayList<Character>();
        
        StringTokenizer token = new StringTokenizer(str, ",");
        char c;
        while(token.hasMoreTokens())
        {
            c=token.nextToken().charAt(0);
            if(c==' ')
            { break; }
            set.add(c);
            //System.out.println(token.nextToken());
        }
        
        
        
        System.out.println("Sets : "+set);
        
        /*
        Character ar[]=null;
        ar=set.toArray(ar);
        
        System.out.println(ar);
        */
        
        //------------------------------
        ModelData data = new ModelData();
        data.addInputSet('l', "a,b,c,d,e,f,g,h,i");
        data.addInputSet('d', "0,1,2,3,4,5,6,7,8,9");
        
        
        
    }
    
}
