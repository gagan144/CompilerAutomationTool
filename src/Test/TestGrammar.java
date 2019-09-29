package Test;

import commanLib.Grammar;
import commanLib.Production;
import java.util.StringTokenizer;

public class TestGrammar 
{
    public static void main(String[] args) 
    {
        /*
        String gramStr="E → E+T|T\nT→T*F|F\nF→(E)|id";
        //String gramStr="W→\nT→T*F|F\nF→(E)|i";
        
        Grammar gram = new Grammar();
        gram.addGrammar(gramStr);
     
        System.out.println(gram);
        System.out.println(gram.returnGrammarType());
        */
        
        
        /*
        Production prod = new Production("T", "i");
        Grammar gram = new Grammar();
        
        System.out.println(gram.isProdctnType3(prod));
        */
        
        
        
        /*
        Production prod = new Production("S", "s");
        Grammar gram = new Grammar();
        
        System.out.println(gram.isProdctnType1(prod));
        * 
        */
        
        StringTokenizer st = new StringTokenizer("$", "$");
        System.out.println("Total : "+st.countTokens());
        while(st.hasMoreTokens())
        {
            System.out.println("#"+st.nextToken()+"#");
        }
        
        /*
        String str=" x | ";
        StringTokenizer st = new StringTokenizer(str, "|");
        StringTokenizer stSpace=null;
        
        String tmp="";
        while(st.hasMoreTokens())
        {
            tmp=st.nextToken();        System.out.println("First Tokenizer : $"+tmp+"$");
            
            stSpace = new StringTokenizer(tmp);
            while(stSpace.hasMoreTokens())
            {
                tmp=stSpace.nextToken();    System.out.println("Second Tokenizer : #"+tmp+"#");
            }
            
        }
        */
    }
    
}
