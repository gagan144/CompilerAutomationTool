/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author Gagandeep
 */
public class CharacterTest 
{
    public static void main(String args[])
    {
        /*
        String s[]=new String[0];
        System.out.println(s[0]);
        System.out.println(Character.isLetter(';'));
        * 
        */
        /*
        String codeBlock="<#@COMMENT$9>";
        
        StringTokenizer st = new StringTokenizer(codeBlock, "$<>");
        if(codeBlock.startsWith("<#@COMMENT$"))
        {
            st.nextToken();
            System.out.println(st.nextToken());
                     //System.out.println("To add : "+Integer.parseInt(codeBlock.substring(11, codeBlock.length())));
        }
        */
        
        /*
        String code="singh int amount; int s;";
        String keywrd="int";
        
        System.out.println(code.indexOf(keywrd));
        * 
        */
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
        
        String s = sdf.format(new Date());
        System.out.println(s);
              
        
    }
    
}
