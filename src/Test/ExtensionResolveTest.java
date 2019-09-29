package Test;

import java.util.StringTokenizer;

public class ExtensionResolveTest 
{
    public static void main(String[] args) 
    {
        String absPath="C:\\gagan\\myCode";
        String ext="cpp";
        
        //-----------------------------------
        String retPath="",ex="";
        try
        {
            StringTokenizer st =new StringTokenizer(absPath,".");
            retPath=st.nextToken();
            ex=st.nextToken();
            
            if(!ex.equals(ext))
            { 
                retPath+="."+ext;
            }
            else
            { retPath=absPath; }
            
            
        }catch(Exception e)
        { retPath=absPath+"."+ext;    }        
        //-----------------------------------------
        
        System.out.println("Return Path : "+retPath);
    }
    
}
