package Test.Key;

import commanLib.Security.FileDESCipher;

public class KeyEncTest 
{
    public static void main(String[] args) 
    {
        String key=FileDESCipher.autoGenerateKey();
        System.out.println("Key : "+key);        

        String eKey=FileDESCipher.encryptKey(key);
        System.out.println("Encrypted key : "+eKey);
        
        try{
            System.out.println("Decrypted Key : "+FileDESCipher.decryptKey(eKey));
        }catch(Exception e)     
        { System.out.println(e); }
        
        
    }    
}
