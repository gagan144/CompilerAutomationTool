package Test;

import commanLib.ModelData;
import commanLib.PerformRecord;
import commanLib.StatisticData;
import commanLib.SystemProperties;
import java.io.File;

public class PDFTest 
{
    public static void main(String[] args) 
    {
        StatisticData stData = new StatisticData(new ModelData(),"C:/noSuchFolder/Anonymous.mdc", new SystemProperties());        
        
        PerformRecord p1 = new PerformRecord(259, "Unitiled", 10, 10);
        PerformRecord p2 = new PerformRecord(259, "Unitiled", 20, 20);
        PerformRecord p3 = new PerformRecord(690, "E:\\Gagan\\Java\\3 - Analyser\\Data\\code.cpp", 40, 40);
        PerformRecord p4 = new PerformRecord(690, "E:\\Gagan\\Java\\3 - Analyser\\Data\\code.cpp", 20, 20);
        PerformRecord p5 = new PerformRecord(690, "E:\\Gagan\\Java\\3 - Analyser\\Data\\code.cpp", 30, 30);
        PerformRecord p6 = new PerformRecord(690, "E:\\Gagan\\Java\\3 - Analyser\\Data\\code.cpp", 25, 25);
        PerformRecord p7 = new PerformRecord(690, "E:\\Gagan\\Java\\3 - Analyser\\Data\\code.cpp", 35, 35);
        PerformRecord p8 = new PerformRecord(690, "E:\\Gagan\\Java\\3 - Analyser\\Data\\code.cpp", 50, 50);
        PerformRecord p9 = new PerformRecord(1526, "Unitiled", 70, 70);
        PerformRecord p10 = new PerformRecord(9736, "Unitiled", 553, 553);
        PerformRecord p11 = new PerformRecord(6842, "Unitiled", 199, 199);
        stData.addNewRecord(p1);
        stData.addNewRecord(p2);
        stData.addNewRecord(p3);
        stData.addNewRecord(p4);
        stData.addNewRecord(p5);
        stData.addNewRecord(p6);
        stData.addNewRecord(p7);
        stData.addNewRecord(p8);
        stData.addNewRecord(p9);
        stData.addNewRecord(p10);
        stData.addNewRecord(p11);        
        stData.addNewRecord(p1);
        stData.addNewRecord(p2);
        stData.addNewRecord(p3);
        stData.addNewRecord(p4);
        stData.addNewRecord(p5);
        stData.addNewRecord(p6);
        stData.addNewRecord(p7);
        stData.addNewRecord(p8);
        stData.addNewRecord(p9);
        stData.addNewRecord(p10);
        stData.addNewRecord(p11); 
        
        //stData= StatisticData.readFromFile();
        
        
        File file = new File("E:/CATPdfTest.pdf");
        try{
            stData.generatePDG(file,true);
        }catch(Exception e)
        {            
            e.printStackTrace();
            file.delete();
        }
        
    }
    
}
