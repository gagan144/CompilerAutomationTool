package commanLib;

import com.sun.servicetag.SystemEnvironment;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.io.Serializable;
import java.util.Map;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class SystemProperties implements Serializable
{
    final public String USER_NAME;
    final public String COMPUTER_NAME;
    final public String OS_NAME;
    final public String OS_VER;
    final public String SYS_MANUF;
    final public String SYS_MODEL;
    final public String SYS_TYPE;
    final public String CPU_MODEL;
    final public String CPU_SPEED;
    final public String CPU_CORES;
    final public String CPU_MANUF;    
    final public String RAM;

    public SystemProperties() 
    {
        SystemEnvironment se = SystemEnvironment.getSystemEnvironment();
        OperatingSystemMXBean mxbean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
        Sigar session = new Sigar();
        
        USER_NAME=System.getProperty("user.name");
        COMPUTER_NAME=se.getHostname();
        OS_NAME=se.getOsName();
        OS_VER=se.getOsVersion();
        SYS_MANUF=se.getSystemManufacturer();
        SYS_MODEL=se.getSystemModel();
        SYS_TYPE=se.getOsArchitecture();
        
        boolean tempFlag=true;
        CpuInfo[] cpuInfoArray=null;
        try {
            cpuInfoArray = session.getCpuInfoList();            
        } catch (SigarException e) 
        {  tempFlag=false;    }
        
        if(tempFlag)
        {
            CPU_MODEL=cpuInfoArray[0].getModel();
            CPU_SPEED=String.valueOf(cpuInfoArray[0].getMhz());
            CPU_CORES=String.valueOf(cpuInfoArray[0].getTotalCores());
            CPU_MANUF=cpuInfoArray[0].getVendor();
        }                
        else
        {
            CPU_MODEL="Error obtaining Information!";
            CPU_SPEED="Error obtaining Information!";
            CPU_CORES="Error obtaining Information!";
            CPU_MANUF="Error obtaining Information!";
        }
        
        //RAM=(mxbean.getTotalSwapSpaceSize()/(1024*1024))+" MB";
        //RAM=(mxbean.getTotalPhysicalMemorySize()/(1024*1024))+" MB";
        
        Map<String, String> map2=null;
        try{
            Sigar sigar = new Sigar(); 
            map2 = sigar.getMem().toMap();            
            tempFlag=true;
        }catch(Exception e)
        { tempFlag=false; }
        
        if(tempFlag)
        { RAM=Sigar.formatSize(Long.valueOf(map2.get("Total"))); }
        else
        { RAM="Error obtaining Information!"; }
        
    }
    
    @Override
    public String toString() 
    {
        String ret="";
        
        ret="User Name : "+USER_NAME+"\n"
                + "Computer Name : "+COMPUTER_NAME+"\n"
                + "OS Name : "+OS_NAME+"\n"
                + "OS Version : "+OS_VER+"\n"
                + "System Manufacturer : "+SYS_MANUF+"\n"
                + "System Model : "+SYS_MODEL+"\n"
                + "System Type : "+SYS_TYPE+"\n"
                + "CPU Model : "+CPU_MODEL+"\n"
                + "CPU Speed : "+CPU_SPEED+"\n"
                + "Total CPU Cores : "+CPU_CORES+"\n"
                + "CPU Manufacturer : "+CPU_MANUF+"\n"
                + "RAM : "+RAM;
                
        return ret;
    }
    
    
    
}
