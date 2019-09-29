package Test;

import java.util.StringTokenizer;

public class StringTokenizerTest 
{
    public static void main(String arg[])
    {
        String text="My name is gagandeep.\nI study CSE.";
        StringTokenizer st = new StringTokenizer(text," \t\n\r\f", true);   //All dilimeters
        //StringTokenizer st = new StringTokenizer(text," \t\f",true);
        
        int ctr=0;
        while(st.hasMoreTokens())
        {
            System.out.println("Token "+(++ctr)+" : "+st.nextToken());
            /*
            if(st.nextToken().equals("\n"))
            { System.out.println("NewLine"); }
            * 
            */
        }
        
        String n="\n";
        System.out.println("****"+n);
    }
 
}
