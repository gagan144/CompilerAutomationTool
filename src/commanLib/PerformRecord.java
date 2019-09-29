package commanLib;

import java.io.Serializable;
import org.apache.taglibs.standard.extra.spath.Path;

public class PerformRecord implements Serializable
{
    public final int loc;
    public final String srcCodePath;
    public final long lexiclTime;
    public final long totalTime;
    //public final long memoryUsage;
    //public final long cpuUsage;
    
    public PerformRecord(int loc, String srcCodePath, long lexiclTime, long totalTime)//, long memoryUsage, long cpuUsage) 
    {
        this.loc = loc;
        this.srcCodePath = srcCodePath;
        this.lexiclTime = lexiclTime;
        this.totalTime = totalTime;
        //this.memoryUsage = memoryUsage;
        //this.cpuUsage = cpuUsage;
    }
    
    public String toString()
    {
        String ret="";
        ret+="LOC : "+loc+"\n"+
             "Path : "+srcCodePath+"\n"+
             "Lexical Time : "+lexiclTime+"\n"+
             "Total Time : "+totalTime;
        return ret;
    }
}
