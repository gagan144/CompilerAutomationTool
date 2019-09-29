package commanLib;

import java.util.ArrayList;
import java.io.File;

public class Diagnosis 
{
    private static final String FILE_LIST[]={ "lib/jcommon-1.0.17.jar",
                                      "lib/jfreechart-1.0.14.jar",
                                      "lib/JgraphXLib.jar",
                                      "lib/mail.jar",
                                      "lib/sigar.jar",
                                      "lib/sigar-x86-winnt.dll",
                                      "lib/web-all-10.0-build-20080812.jar",
                                      "lib/itext-1.3.jar"
                                    };
    
    public static ArrayList<String> checkFiles()
    {
        ArrayList<String> missingFiles=new ArrayList<String>();
        
        for(int i=0;i<FILE_LIST.length;i++)
        {
            File file = new File(FILE_LIST[i]);            
            if(!file.exists())
            {   missingFiles.add(file.getAbsolutePath());  } //{   missingFiles.add(FILE_LIST[i]);  }
        }
        
        if(missingFiles.isEmpty())
        { missingFiles=null; }
        
        return missingFiles;
    }
    
}
