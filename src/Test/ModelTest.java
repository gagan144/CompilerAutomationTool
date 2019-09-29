package Test;

import commanLib.ModelData;

public class ModelTest 
{
    public static void main(String args[])
    {
        /*
        ModelData data = new ModelData();
        data.addInputSet('d', "0,1,2,3,4,5,6,7,8,9");
        data.addInputSet('b', "0,1");
        
        System.out.println(data.doesSetContains('x', '0'));
        * 
        */
        
        try
        {
            throw new Exception("my Message");
        }catch(Exception e)
        { System.out.println(e); }
        
    }
    
}
