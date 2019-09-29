package Test;

import java.util.Map;

public class EnvTest 
{
    public static void main(String[] args) 
    {
    
        Map<String, String> variables = System.getenv();        
    for (Map.Entry<String, String> entry : variables.entrySet())  
    {  
       String name = entry.getKey();  
       String value = entry.getValue();  
       System.out.println(name + "=" + value);  
    }  
        
    }
    
}
