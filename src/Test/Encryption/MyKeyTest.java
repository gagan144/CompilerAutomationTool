package Test.Encryption;

import commanLib.Security.Transposition;
import commanLib.SystemProperties;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class MyKeyTest 
{
    private String generateKey()
    {
        SystemProperties sysProp = new SystemProperties();    
        
        String str=sysProp.OS_NAME+ sysProp.OS_VER+sysProp.SYS_TYPE+sysProp.COMPUTER_NAME;  
        //    System.out.println(str);  
    
        //Remove White spaces
        str=str.replaceAll("\\s","");
        //    System.out.println(str);
    
        //Remove Anything that is not character
        str=str.replaceAll("\\W","");
        //    System.out.println(str);
    
        //Apply transposition
        String transKey="";
        for(int i=0;i<sysProp.CPU_SPEED.length();i++)
        {
            transKey+=(char)((int)sysProp.CPU_SPEED.charAt(i) - 48 + 65);
            //System.out.println( (char)((int)sysProp.CPU_SPEED.charAt(i) - 48 + 65) );
        }
        transKey+="CAT";
        //    System.out.println("TransKey : "+transKey);
    
        Transposition trans = new Transposition(transKey);
        
        String key=trans.encrypt(str);    
        return key;
    }
    
   
    
   public static void main(String ...args)
   {
       MyKeyTest tc= new MyKeyTest();
       System.out.println("Final Key: "+tc.generateKey());
   }     
}
