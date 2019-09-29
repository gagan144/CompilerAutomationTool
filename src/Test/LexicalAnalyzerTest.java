package Test;

import Compiler.CompilerModelData;
import commanLib.ModelData;

public class LexicalAnalyzerTest 
{
    public static void main(String arg[])
    {
        ModelData model=null;
        try
        {
            model=ModelData.readData("C:\\Users\\Gagandeep\\Documents\\COMPILER\\cppModels3.mdc");            
            //System.out.println(model);
        }catch(Exception e)
        { 
            System.out.println(e);
            System.exit(0);
        }
        //---------------------------------------
        
        
        CompilerModelData cmMdlData = new CompilerModelData(model);
        
        //String code="a1fg=bg1+cf1";
        String code="#b1+c1)";
        //String code="cout<<value=id8";
        //String code="/*ggan*/import java.lang.*;/*dgd*/\ngagan";
        //String code="/*ggan*/i/*dgd*/gagan";
        
        //cmMdlData.simulateDFA(code);
        //cmMdlData.lexicalAnaylser(code);
        cmMdlData.simulateWithPriority(7, code);
        
        
    }
    
}
