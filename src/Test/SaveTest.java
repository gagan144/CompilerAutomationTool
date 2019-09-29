package Test;

import commanLib.ModelData;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveTest 
{
    public static void main(String args[])
    {
        String path="E:\\Gagan\\compiler.mdc";
        
        //SAving
        ModelData data = new ModelData();
        data.addIdentifier("l(l|d)*", "id",null);
        try {
            ModelData.saveData(data,path);
        } catch (Exception ex) {
            Logger.getLogger(SaveTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        //-----------------------------
        
        //opening
        ModelData rdData = new ModelData();
        try {
            rdData=ModelData.readData(path);
        } catch (Exception ex) {
            Logger.getLogger(SaveTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
}
