package Test.Encryption;

import commanLib.SystemProperties;
import java.util.Scanner;


public class Hargeet2 
{
    
    private int retASCIIvalue(char ch)
    {
        int val= (int)ch;
        
        return val;
    }
    
    private int addDigits(int n)
    {
        int sum=0;
        int r;
        while(n!=0)
        {
            r=n%10;
            sum+= r;
            n/= 10;
        }
        return sum;
    }
   
    private char retDigit(int n)
    {
        if(n==1) return '1';
        else if(n==2) return '2';
        else if(n==3) return '3';
        else if(n==4) return '4';
        else if(n==5) return '5';
        else if(n==6) return '6';
        else if(n==7) return '7';
        else if(n==8) return '8';
        else if(n==9) return '9';
        else return '0';
    }
    
    /*private int exp(int a, int b)
    {
        int val=1;
        if(b>9)
            b=b%10;
        for (int i=1; i<=b; i++)
            val*=a;
        return val;
    }
    */
    private int handleAlphanumeric(String s)
    {
        int val=0;
        for(int i=0; i<s.length(); i++)
        {
            if(s.charAt(i)>='0' && s.charAt(i)<='9')             
              val= val+(int)s.charAt(i)-48; 
            else
               val+= retASCIIvalue(s.charAt(i));                
        }
        while(val>99999)
        {  val= addDigits(val); }
        
        return val;
    }
    
   public static void main(String ...args)
   { 
        SystemProperties sysProp = new SystemProperties();
        System.out.println(sysProp.toString());
        //System.out.println("Enter string :");
        //String str= new Scanner(System.in).next();
        Hargeet2 obj= new Hargeet2();
        int comp_name= obj.handleAlphanumeric(sysProp.COMPUTER_NAME);
       // int cpu_cores= obj.handleAlphanumeric(sysProp.CPU_CORES);
       // int cpu_manuf= obj.handleAlphanumeric(sysProp.CPU_MANUF);
        //int cpu_model= obj.handleAlphanumeric(sysProp.CPU_MODEL);
        //int cpu_speed= obj.handleAlphanumeric(sysProp.CPU_SPEED);
        int os_name= obj.handleAlphanumeric(sysProp.OS_NAME);
        int os_ver= obj.handleAlphanumeric(sysProp.OS_VER);
        int os_arch= obj.handleAlphanumeric(sysProp.SYS_TYPE);
        
        //int sys_manuf= obj.handleAlphanumeric(sysProp.SYS_MANUF);
        //int sys_model= obj.handleAlphanumeric(sysProp.SYS_MODEL);
        ///int sys_type= obj.handleAlphanumeric(sysProp.SYS_TYPE);
        
        //System.out.println("Value for the user name :"+sysProp.USER_NAME);
        System.out.println("Value for the computer name :"+comp_name);
        //System.out.println("Value for the cpu cores :"+cpu_cores);
        //System.out.println("Value for the cpu manufacturer :"+cpu_manuf);
        //System.out.println("Value for the cpu model :"+cpu_model);
        //System.out.println(sysProp.CPU_MODEL.length());
        //System.out.println("Value for the cpu speed :"+cpu_speed);
        System.out.println("Value for the os name :"+os_name);
        System.out.println("Value for the os version :"+os_ver);
        System.out.println("Value for the os_arch :"+os_arch);
        //System.out.println("Value for the system manufacturer :"+sys_manuf);
        //System.out.println("Value for the system model :"+sys_model);
        //System.out.println("Value for the system type :"+sys_type);
      /*
        int key1, key2, key3, key4;
        int x= obj.exp(comp_name,cpu_cores);
        key1= x%cpu_manuf;
        key2= (key1+cpu_model)%cpu_speed;
        key3= (key2+os_name)%os_ver;
        key4= (key3+ram+sys_manuf)%sys_model;
        key5= Integer.rotateLeft((key4+sys_type),2);
     
        System.out.println("Key 1: "+key1);           
        System.out.println("Key 2: "+key2);
        System.out.println("Key 3: "+key3);
        System.out.println("Key 4: "+key4);
        System.out.println("Key 5: "+key5);
     */
        char[] arr= new char[30];
        
        arr[0]='c';
        arr[1]='a';
        arr[2]='t';
        int j=3;
        
        while(os_name!=0)
        {
            arr[j++]= obj.retDigit(os_name%10);
            os_name/=10;
        }
       while(os_ver!=0)
        {
            arr[j++]=obj.retDigit(os_ver%10);
            os_ver/=10;
        }
        while(comp_name!=0)
        {
            arr[j++]=obj.retDigit(comp_name%10);
            comp_name/=10;
        }
        while(os_arch!=0)
        {
            arr[j++]=obj.retDigit(os_arch%10);
            os_arch/=10;
        }
        
        String key= new String(arr); 
        System.out.println("Final Key: "+key);
   }
}
