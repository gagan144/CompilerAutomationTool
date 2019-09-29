package Test.SystemProp;

import com.sun.servicetag.SystemEnvironment;
import java.util.HashMap;
import java.util.Map;

public class SystemEnvTest 
{
    public static void main(String[] args) 
    {
        SystemEnvironment se = SystemEnvironment.getSystemEnvironment();
        
        System.out.println("Host Name : "+se.getHostname());
        System.out.println("Host ID : "+se.getHostId());
        System.out.println("Os Name : "+se.getOsName());
        System.out.println("OS Version : "+se.getOsVersion());
        System.out.println("OS Arch : "+se.getOsArchitecture());
        System.out.println("System Model : "+se.getSystemModel());
        System.out.println("System Manufacture : "+se.getSystemManufacturer());
        System.out.println("Serial no : "+se.getSerialNumber());
        System.out.println("CPU Manufacture : "+se.getSystemManufacturer());
                
    }
}
