package commanLib.Security;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class SerialKeySecurity 
{   
    public static int generatePrivateKey()
    {   
        ArrayList<Integer> invalidKeyList = new ArrayList<Integer>();
        invalidKeyList.add(11111111);
        invalidKeyList.add(22222222);
        invalidKeyList.add(33333333);
        invalidKeyList.add(44444444);
        invalidKeyList.add(55555555);
        invalidKeyList.add(66666666);
        invalidKeyList.add(77777777);
        invalidKeyList.add(88888888);
        invalidKeyList.add(99999999);
        
        Random rand= new Random();
        int temp_key=0;
        do
        {
            temp_key=10000000+rand.nextInt(89999999);    // 8 digit range : [10000000-99999999]           
        }while(invalidKeyList.contains(temp_key));
        
        
        Stack<Integer> stack = new Stack<Integer>();
        while(temp_key > 0)             
        {
            stack.push(temp_key%10);//System.out.println( n % 10);
            temp_key=temp_key/10;
        }        
        
        int mul=10000000;
        temp_key=0;
        int top;
        while(!stack.isEmpty())
        {
            top=stack.pop();
            if(top==0)
            { top=2; }
            
            temp_key+=mul*top;
            mul/=10;
        }
        
        final int PRIVATE_KEY=temp_key;
        
        return PRIVATE_KEY;
    }    
     
    public static String generateActivationKey(final String SERIAL,final int PRIVATE_KEY) throws Exception
    {
        if(PRIVATE_KEY<10000000 || PRIVATE_KEY>99999999)
        { throw new Exception("Invalid Private key!"); }
        
        //(1) get Array
        int private_key_arr[]= new int[8];
        int n=PRIVATE_KEY;  
        int x=7;        
        while(n > 0) 
        {
            private_key_arr[x--]=(n%10);
            n=n/10;
        }        
        
        
        //(2) Generate Activation key
        int actvtn[]=new int[32];        
        try
        {     
            //fill odd
            int tmp,p=0,j=0;
            for(int i=0;i<SERIAL.length();i++)                
            {                 
                tmp=Integer.parseInt(String.valueOf(SERIAL.charAt(i))); 
                tmp=tmp%private_key_arr[p++];
                actvtn[j]=tmp;
                j=j+2;
                if(p==8)
                { p=0; }
            }  
            
            //fill even
            p=7;j=1;
            for(int i=0;i<SERIAL.length();i++)                
            {                 
                tmp=Integer.parseInt(String.valueOf(SERIAL.charAt(i))); 
                tmp=tmp%private_key_arr[p--];
                actvtn[j]=tmp;
                j=j+2;
                if(p==-1)
                { p=7; }
            }  
            
            //generate String
            String str="";            
            for(int i=0;i<actvtn.length;i++)
            { str+=actvtn[i]; }
            
            /*
            System.out.println("Activation Key :");
            for(int i=0;i<actvtn.length;i++)
            { System.out.print(actvtn[i]); }
            System.out.println("");
            */
            
            final String ACTIVATION_KEY=str;
            return ACTIVATION_KEY;
            
        }catch(Exception e)
        {   throw new Exception("Unable to generate Activation Key! Possibily an incorrect SERIAL or private key!");    }
    }
    
    public static boolean verifyActivationKey(final String SERIAL,final int PRIVATE_KEY,final String ACTIVATION_KEY) throws Exception
    {
        String correctActvtnKey=generateActivationKey(SERIAL, PRIVATE_KEY);
        if(correctActvtnKey.equals(ACTIVATION_KEY))
        { return true; }
        else
        { return false; }
    }
    
}
