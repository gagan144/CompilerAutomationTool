package Test.Key;

import java.util.Random;
import java.util.Stack;

public class KeyTest 
{
    public static void main(String[] args) 
    {
        String serial="1245981376534718";
        
        //final int PRIVATE_KEY=23498137;
        
        //Generate Private key
        Random rand= new Random();
        int temp_key=10000000+rand.nextInt(89999999);
        
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
        //----------------------
        
        System.out.println("Serial : "+serial);
        System.out.println("Private key : "+PRIVATE_KEY);
                
        //(1) get Array
        int private_key_arr[]= new int[8];
        int n=PRIVATE_KEY;  
        int x=7;        
        while(n > 0) 
        {
            private_key_arr[x--]=(n%10);//System.out.println( n % 10);
            n=n/10;
        }        
        //System.out.println(private_key_arr[5]);
        
        //(2) Generate Activation key
        int actvtn[]=new int[32];        
        try
        {     
            //fill odd
            int tmp,p=0,j=0;
            for(int i=0;i<serial.length();i++)                
            {                 
                tmp=Integer.parseInt(String.valueOf(serial.charAt(i))); 
                tmp=tmp%private_key_arr[p++];
                actvtn[j]=tmp;
                j=j+2;
                if(p==8)
                { p=0; }
            }  
            
            //fill even
            p=7;j=1;
            for(int i=0;i<serial.length();i++)                
            {                 
                tmp=Integer.parseInt(String.valueOf(serial.charAt(i))); 
                tmp=tmp%private_key_arr[p--];
                actvtn[j]=tmp;
                j=j+2;
                if(p==-1)
                { p=7; }
            }  
            
            //display
            System.out.println("Activation Key :");
            for(int i=0;i<actvtn.length;i++)
            { System.out.print(actvtn[i]); }
            System.out.println("");
        }catch(Exception e)
        {e.printStackTrace();}
        
        
        
        
    }
    
}
