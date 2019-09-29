package Test.Encryption;

import commanLib.SystemProperties;
import java.util.Scanner;

public class Hargeet3 {
    
private String generateKey()
{
    SystemProperties sysProp = new SystemProperties();
       
        //System.out.println("Value for the computer name :"+sysProp.COMPUTER_NAME);
        //System.out.println("Value for the os name :"+sysProp.OS_NAME);
        //System.out.println("Value for the os version :"+sysProp.OS_VER);
        //System.out.println("Value for the os_arch :"+sysProp.OS_ARCH);
        
      final int len= 3+ sysProp.OS_NAME.length()+ sysProp.OS_VER.length()+ sysProp.COMPUTER_NAME.length()+ sysProp.SYS_TYPE.length();
      char arr[]= new char[len+1];
      arr[0]= 'c';
      arr[1]= 'a';
      arr[2]= 't';
      int j=3;
      
        for(int i=0; i<sysProp.OS_NAME.length(); i++)
            arr[j++]= sysProp.OS_NAME.charAt(i);
        for(int i=0; i<sysProp.OS_VER.length(); i++)
            arr[j++]= sysProp.OS_VER.charAt(i);
        for(int i=0; i<sysProp.COMPUTER_NAME.length(); i++)
            arr[j++]= sysProp.COMPUTER_NAME.charAt(i);
        for(int i=0; i<sysProp.SYS_TYPE.length(); i++)
            arr[j++]= sysProp.SYS_TYPE.charAt(i);  
       
        
        //bubble shuffling
        for(int k=3; k<len; k+=2)
        {
            char temp=arr[k];
            arr[k]= arr[k+1];
            arr[k+1]= temp;
        }
        
        //substitution cipher encryption of alternate characters
        for(int k=3; k<len; k+=2)
        {
            char temp= arr[k];
            int temp_new= (int)temp +3;
            arr[k]= (char)temp_new;
        }
        
        String key= new String(arr);
        return key;

}
    
   
    

    
   public static void main(String ...args)
   {
       Hargeet3 tc= new Hargeet3();
       System.out.println("Final Key: "+tc.generateKey());
   }     
}
