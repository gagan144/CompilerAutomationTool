package Test.SystemProp;

import java.text.NumberFormat;

public class Memory 
{
    public static void main(String[] args) 
    {
        
        
        Runtime runtime = Runtime.getRuntime();
        
    NumberFormat format = NumberFormat.getInstance();
    
    long maxMemory = runtime.maxMemory();
    long allocatedMemory = runtime.totalMemory();
    long freeMemory = runtime.freeMemory();

    System.out.println("free memory: " + format.format(freeMemory / 1024));
    System.out.println("allocated memory: " + format.format(allocatedMemory / 1024));
    System.out.println("max memory: " + format.format(maxMemory / 1024));
    System.out.println("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
        
        
    
    }
    
}
