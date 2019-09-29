package Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jvnet.hk2.component.MultiMap;

public class HashMapTest 
{
    public static void main(String args[])
    {
        HashMap<Integer,String> PRIORITY_MAP = new HashMap<Integer, String>();
        
        
        /*
Keyword
Identifier
Integer (Literal)
Character (Literal)
Floating (Literal)
String (Literal)
Punctuator
Arithmetic (Operator)
Relational (Operator)
Logical (Operator)
Bitwise (Operator)
Assignment (Operator)
Others (Operator)
Comments
          
         */
        
        
        PRIORITY_MAP.put(1, "Keyword");        
        PRIORITY_MAP.put(2, "Identifier");
        PRIORITY_MAP.put(3, "Integer (Literal)");
        PRIORITY_MAP.put(4, "Character (Literal)");
        PRIORITY_MAP.put(5, "Floating (Literal)");
        PRIORITY_MAP.put(6, "String (Literal)");
        PRIORITY_MAP.put(7, "Punctuator");
        PRIORITY_MAP.put(8, "Arithmetic (Operator)");
        PRIORITY_MAP.put(9, "Relational (Operator)");
        PRIORITY_MAP.put(10, "Logical (Operator)");
        PRIORITY_MAP.put(11, "Bitwise (Operator)");
        PRIORITY_MAP.put(12, "Assignment (Operator)");
        PRIORITY_MAP.put(13, "Others (Operator)");
        PRIORITY_MAP.put(14, "Comments");        
              
        
        
        System.out.println(PRIORITY_MAP.get(4));
        
        Set<Map.Entry<Integer,String>> set = PRIORITY_MAP.entrySet();
        for(Map.Entry<Integer,String> i : set)
        {
            System.out.println(i.getKey()+" "+i.getValue());
        }
        
        
    }
    
}
