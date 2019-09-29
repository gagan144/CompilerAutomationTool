package Test.SystemProp;

import java.util.*;
import java.io.*;
import java.lang.*;
import com.sun.servicetag.SystemEnvironment;
import java.lang.management.ManagementFactory;

public class Getsystemproperties 
{
    
    public static void main(String[] args) {
         //Properties prop = System.getProperties();
                 
                 //System.out.println("Printing all System properties");
                 
                 /*
                  * To print all system properties use
                  * static void list(PrintStream ps) method of System
                  * class.
                  *
                  * Hint : To print properties on console, paas
                  * System.out to list method.
                  */
                 
                 // prop.list(System.out);
      SystemEnvironment se = SystemEnvironment.getSystemEnvironment();
      com.sun.management.OperatingSystemMXBean mxbean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
                 
      String uname = System.getProperty("user.name");
      String oversion = System.getProperty("os.version");

String oname = System.getProperty("os.name"); 
System.out.println("the user name is :"  + uname);
System.out.println("the OS name is :"  + oname);
System.out.println("the OS version is :"  + oversion);

System.out.println("the processor is :" + System.getenv("PROCESSOR_IDENTIFIER"));
//System.out.println(System.getenv("PROCESSOR_ARCHITECTURE"));
//System.out.println(System.getenv("PROCESSOR_ARCHITEW6432"));
//System.out.println(System.getenv("NUMBER_OF_PROCESSORS"));
System.out.println("the system name is :" + System.getenv("COMPUTERNAME"));
System.out.println("the system model is :" + se.getSystemModel());
System.out.println("the system manufacturer is :" + se.getSystemManufacturer());
System.out.println("the system TYPE is :" + System.getProperty("os.arch"));
System.out.println("the windows directory is :" + System.getenv("PATH"));
System.out.println("Total Memory"+Runtime.getRuntime().totalMemory());    
System.out.println("Free Memory"+Runtime.getRuntime().freeMemory());

System.out.println("RAM SIZE : "+(mxbean.getTotalPhysicalMemorySize())); // /(1024*1024*1024)));
System.out.println("TOTAL RAM SIZE : "+mxbean.getTotalSwapSpaceSize());  // /(1024*1024*1024));

        

}

    
    
}
