package commanLib;

import java.util.HashMap;

public class PriorityMap 
{
    private HashMap<Integer,String> PRIORITY_MAP = new HashMap<Integer, String>();

    public PriorityMap() 
    {
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
    }
    
    public String getModelName(int key)
    {
        return PRIORITY_MAP.get(key);
    }
    
}
