package commanLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParsingTable 
{
    public HashMap<Integer, String> colNames= new HashMap<Integer, String>();
    private HashMap<String, Integer> revColNames= null;
    
    public HashMap<Integer, String> rowName= new HashMap<Integer, String>();
    private HashMap<String, Integer> revRowNames= null;
    
    public Integer tableData [][]= null;
    public HashMap<CellPos,Integer> extra= null;
    public ArrayList<String> errorRecv = new ArrayList<String>();
    
    public int getProdNo(String rowToken, String colToken) throws Exception
    {
        int r,c,ret;
        
        if(revColNames==null || revRowNames==null)
        { throw new Exception("Exception in LL1 - Reverse Map not calculated!"); }
        
        r=revRowNames.get(rowToken);
        c=revColNames.get(colToken);
        
        
        ret=tableData[r][c];
        
        //System.out.println(ret);
        
        return ret;
    }
    
    public boolean createReveseMap()
    {        
        if(colNames.isEmpty() || rowName.isEmpty())
        { return false; }
        
        revColNames=new HashMap<String, Integer>();
        for(Map.Entry<Integer,String> i : colNames.entrySet())
        {   revColNames.put(i.getValue(), i.getKey());  }
        
        revRowNames=new HashMap<String, Integer>();
        for(Map.Entry<Integer,String> i : rowName.entrySet())
        {   revRowNames.put(i.getValue(), i.getKey());  }
        
        //System.out.println(revColNames+"\n"+revRowNames);
        
        return true;
    }
    
}
