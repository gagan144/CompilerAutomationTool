package Test.Encryption;

import commanLib.Security.FileDESCipher;
import java.io.File;

public class MyTest 
{
    public static void main(String[] args) 
    {
        System.out.println("Encrypting with key : "+FileDESCipher.autoGenerateKey());
        //FileDESCipher cipher = new FileDESCipher("gagandeepsingh");
        FileDESCipher cipher = new FileDESCipher(FileDESCipher.autoGenerateKey());
        
        try
        {
            String source = "E:\\new\\CATRegist.creg";
            String encypt = "E:\\new\\Enrypted_CATRegist.creg";            

            System.out.println("Encrypting : "+source);
            cipher.encrypt(source, encypt);
            
            //Process p = Runtime.getRuntime().exec("attrib +H "+encypt);
            //p.waitFor();            
            
            System.out.println("Done! Target : "+encypt);
        }
        catch(Throwable t)
        {
            System.out.println("Error Encrypting...");
            t.printStackTrace();
        }        
        
        //Decrypt
        try
        {            
            String encypt = "E:\\new\\Enrypted_CATRegist.creg";            
            String decrypt = "E:\\new\\Decrypted_CATRegist.creg";

            System.out.println("Decrypting : "+encypt);
            cipher.decrypt(encypt, decrypt);
            
            System.out.println("Done! Target : "+decrypt);
        }
        catch(Throwable t)
        {
            System.out.println("Error Encrypting...");
            t.printStackTrace();
        }        

    }
    
}
