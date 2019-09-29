package Test.SystemProp;

import java.util.Map;

public class GetEnvTest 
{
    public static void main(String[] args) 
    {
        Map<String,String> map=System.getenv();
        
        for(String i : map.keySet())
        {
            System.out.println(i+" : "+map.get(i));
        }
        
                
    }
    
}
