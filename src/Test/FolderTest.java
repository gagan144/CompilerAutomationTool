package Test;

import java.io.File;

public class FolderTest 
{
    public static void main(String[] args) 
    {
        //File path = new File("C:\\Users\\Gagandeep\\Desktop"+"\\AnonymsFolder");
        File path = new File("G:\\CATTestFolder");
        path.mkdirs();
        
        System.out.println("exist : "+path.exists());
        System.out.println("Can write : "+path.canWrite());
        
    }
    
}
