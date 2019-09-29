package commanLib;


import commanLib.CellPos;
import java.util.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ParsingTableBtmUp 
{
    private HashMap<Integer, String> colNames= new HashMap<Integer, String>();
    private HashMap<String, Integer> revColNames= null;
    private int totRows;
    
    /*
    public HashMap<Integer, String> rowName= new HashMap<Integer, String>();
    private HashMap<String, Integer> revRowNames= null;
    */
    
    private ParseTblElmnt tableData [][]= null;
    private HashMap<CellPos,ParseTblElmnt> extra= new HashMap<CellPos, ParseTblElmnt>();
    public ArrayList<String> errorRecv = new ArrayList<String>();

    public ParsingTableBtmUp(int r, int c, HashMap<Integer,String> t, HashMap<Integer,String> nt) 
    {
        tableData=new ParseTblElmnt[r][c];
        for(int i=0;i<r;i++)
        {
            for(int j=0;j<c;j++)
            { tableData[i][j]=new ParseTblElmnt(); }
        }
        
        totRows=r;
        
        colNames.putAll(t);
        int count_t= t.size();
        int count_nt= nt.size();
        int x;
        for(int j=0; j<count_nt; j++)
        {
            x=j+count_t;
            colNames.put(x, nt.get(j));
        }
        
        createReveseMap();
    }
    
    
    public ParseTblElmnt getElement(int rowState, String colToken) throws Exception
    {
        int r,c;
        ParseTblElmnt ret;
        
        if(revColNames==null ) //|| revRowNames==null)
        { throw new Exception("Exception in ParsingTableBtmUp - Reverse Map not calculated!"); }
        
        r=rowState;
        c=revColNames.get(colToken);
        
        
        ret=tableData[r][c];
        
        //System.out.println(ret);
        
        return ret;
    }
    
    
    public void addElement(int row,String col, ParseTblElmnt element)
    {
        if(col.endsWith(String.valueOf(SpecialSymbol.EPSILON)))          //new addition
        {
            System.out.println("Adding element "+element.toString()+" at ["+row+","+col+"]");
            return;
        }
        
        
        int c= revColNames.get(col);
        
        if(tableData[row][c].retType()!=ParseTblElmnt.ERROR)
        {
            CellPos pos= new CellPos();
            pos.row= row;
            pos.col= c;
            extra.put(pos,element);
         }
         else
         {  tableData[row][c]= element; }
              
     }
    
    public boolean createReveseMap()
    {        
        if(colNames.isEmpty() ) //|| rowName.isEmpty())
        { return false; }
        
        revColNames=new HashMap<String, Integer>();
        for(Map.Entry<Integer,String> i : colNames.entrySet())
        {   revColNames.put(i.getValue(), i.getKey());  }
        
        /*
        revRowNames=new HashMap<String, Integer>();
        for(Map.Entry<Integer,String> i : rowName.entrySet())
        {   revRowNames.put(i.getValue(), i.getKey());  }
        */
        
        //System.out.println(revColNames+"\n"+revRowNames);        
        return true;
    }
    
    public void displayOnJTable(JTable table)
    {
        DefaultTableModel model=(DefaultTableModel)table.getModel();
        
        //clear table
        // clear rows        
        for( int i = model.getRowCount() - 1; i >= 0; i-- )
        {   
            model.removeRow(i);
        }
        
        //clear columns
        model.setColumnCount(0);
        
        //Add new columns    
        model.addColumn(new String("State"));
        for(int i=0;i<colNames.size();i++)
        {
            model.addColumn(colNames.get(i));
        }
        
        //fill rows        
        int columns=colNames.size();
        Object rowObj[]=null;
        ParseTblElmnt elmTmp;
        for(int r=0; r<totRows; r++)
        {
            rowObj=new Object[colNames.size()+1];            
            rowObj[0]=r;
            for(int c=0; c<columns; c++)
            {
                elmTmp=tableData[r][c];
                if( !(elmTmp.isError() && elmTmp.retValue()==0) )
                { rowObj[c+1]=elmTmp.toString(); }
            }
            model.insertRow(table.getRowCount(), rowObj);            
        }
        
        
        if(!extra.isEmpty())
        {
            //System.out.println("Total "+ll1_extra.size());
            Set<CellPos> extraSet= extra.keySet();
            CellPos tmp=null;
            
            try
            {
                for(Iterator<CellPos> it = extraSet.iterator();; it.hasNext() )
                {                
                    tmp=it.next(); 
                    if(tmp!=null)
                    { 
                        //System.out.println(tmp+" : "+ll1_extra.get(tmp)); 
                        table.setValueAt(table.getValueAt(tmp.row, tmp.col+1)+" , "+extra.get(tmp).toString()  , tmp.row, tmp.col+1);
                    }
                }
            }catch(Exception e){}
        }
    }
    
    public boolean isParsable()
    {
        if(extra.isEmpty())
        { return true; }
        else
        { return false; }
    }
    
    public int getTotalNoOfRows()
    { return totRows; }
    
    public boolean applyErrorVariables(JTable table)
    {
        boolean ret=true;
        try
        {
            for(int i=0;i<tableData.length;i++)
            {
                for(int j=0;j<tableData[i].length;j++)                    
                {                
                    if(tableData[i][j].isError())
                    {
                        String str=String.valueOf(table.getValueAt(i,j+1));  
                        if(str.equals("") || str.equals("null") || str==null)
                        { tableData[i][j]=new ParseTblElmnt(ParseTblElmnt.ERROR, 0); continue; }
                        
                        str=str.substring(1);
                        int errVal=Integer.parseInt(str);    //System.out.println("errVal : "+errVal);
                        if(errVal>errorRecv.size() || errVal<0)                        
                        { throw new Exception("Invalid Error Variable!"); }
                        
                        tableData[i][j]=new ParseTblElmnt(ParseTblElmnt.ERROR,errVal);
                    }
                }
            }
            
        }catch(Exception e)
        { System.out.println("Exception in ParseTableBtmUp - applyErrorVariable() : "+e.getMessage());
          ret=false; 
        }
        
          
        System.out.println("Error Table :");
        for(int i=0;i<tableData.length;i++)
            {
                for(int j=0;j<tableData[i].length;j++)                    
                {     
                    System.out.print(tableData[i][j].toString()+" ");
                }
                System.out.println("");
            }
        
        
        return ret;
    }
    
    public String getErrorString(int errorNo)
    {
        return errorRecv.get(errorNo-1);
    }
    
    public String toString()
    {
        String ret="";
        
        for(int i=0;i<tableData.length;i++)
        {
            for(int j=0;j<tableData[i].length;j++)
            {  System.out.print(tableData[i][j].toString()+" "); }
            System.out.println("");
        }
        return ret;
    }
}
