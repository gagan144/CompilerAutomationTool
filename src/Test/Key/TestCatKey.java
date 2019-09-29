package Test.Key;

import commanLib.Security.SerialKeySecurity;

public class TestCatKey 
{
    public static void main(String[] args) 
    {
        try{
            
            //String actvtnKey=KeySecurity.generateActivationKey("1245981376534718", 23498137);        
            String actvtnKey=SerialKeySecurity.generateActivationKey("1245981376534718", SerialKeySecurity.generatePrivateKey());        
            System.out.println(actvtnKey);
            
            
            //System.out.println(KeySecurity.verifyActivationKey("1245981376534718", 23498137,"11220055100011311000103344031110"));
        }catch(Exception e)
        { System.out.println(e.getMessage()); }
        
    }
    
}
