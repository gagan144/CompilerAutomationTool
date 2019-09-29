package Test;
import java.io.File;


public class FileSeperatorTest 
{
    public static void main(String[] args) 
    {
        String filSep=System.getProperty("file.separator");
        System.out.println("File Separator \""+System.getProperty("file.separator")+"\"");
        
        //File file = new File("/root/Desktop/Gagan/TestFolder/subFolder");
        File file = new File("/root/Desktop/Gagan/TestFolder\\subFolder");
        //File file = new File("/root/Desktop/Gagan/TestFolder"+filSep+"subFolder");
        System.out.println("getName() : "+file.getPath());
        //file.mkdirs();
        System.out.println(file.exists());
        
    }
    
}
