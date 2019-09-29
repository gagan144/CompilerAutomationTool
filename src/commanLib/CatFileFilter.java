package commanLib;

import java.io.File;

public class CatFileFilter extends javax.swing.filechooser.FileFilter 
{    
    private String extn="";

    public CatFileFilter(String ext) 
    {  extn=ext;   }    

    public boolean accept(File f) 
    {
        if (f.isDirectory())
            return true;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        
        if (i > 0 && i < s.length() - 1)
            if (s.substring(i + 1).toLowerCase().equals(extn))
                return true;
        return false;
    }

    public String toString()
    {  return extn;  }
    
    public String getDescription() 
    {   return "*."+extn;  }
    
}
