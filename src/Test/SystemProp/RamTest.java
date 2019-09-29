package Test.SystemProp;

import java.util.Map;
import org.hyperic.sigar.Sigar;

public class RamTest 
{
    public static void main(String[] args) 
    {
        boolean tempFlag;
        final String RAM;
        
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
        
        System.out.println(RAM);
        
    }
    
}
