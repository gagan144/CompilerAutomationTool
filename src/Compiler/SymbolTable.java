package Compiler;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import commanLib.Token;



public class SymbolTable 
{
    private ArrayList<Token> tbData = new ArrayList<Token>();    
    private int keywrd,id,intr,chr,flt,str,punc,arith,relop,logop,bitop,assig,othrop;
    
    private volatile int linetrack=1;
    //private ArrayList<String> tknStrm=new ArrayList<String>();
    private ArrayList<Token> tknStrm=null;

    public SymbolTable(ArrayList<Token> tknStrmRef) 
    {
        tknStrm=tknStrmRef;
    }        
    
    public void insert(String lx,int tknID,int ln)
    {       
        if(ln>linetrack)
        {
            for(int i=0;i<(ln-linetrack);i++)
            {  tknStrm.add( new Token("\n", "",-1) );     }
            linetrack=ln;
        }
        
        
        Token currEntry=null;
        boolean flag=false;
        for(int i=0;i<tbData.size();i++)
        {
            currEntry=tbData.get(i);
            if(currEntry.lexeme.equals(lx))
            {
                currEntry.lineNo.add(ln);                
                flag=true;
                break;
            }
        }
        if(flag==false)
        { 
            currEntry=new Token(lx, resolveRetString(tknID), ln);
            tbData.add(currEntry);         
        }
        
        //add to Token STream    
        tknStrm.add(currEntry);
        /*
        String tkstr=currEntry.token;
        if(!tkstr.startsWith("id"))
        { tkstr=currEntry.lexeme; }    
        tknStrm.add(tkstr);
        * 
        */
        
    }
    
    private String resolveRetString(int tknID)
    {
        switch(tknID)        
        {
            case 1 : return "keyword"+(++keywrd);
            case 2 : return "id"+(++id);
            case 3 : return "int"+(++intr);
            case 4 : return "char"+(++chr);
            case 5 : return "flt"+(++flt);
            case 6 : return "str"+(++str);
            case 7 : return "punc"+(++punc);
            case 8 : return "arithOp"+(++arith);
            case 9 : return "relOp"+(++relop);
            case 10 : return "logicOp"+(++logop);
            case 11 : return "bitOp"+(++bitop);
            case 12 : return "assignOp"+(++assig);
            case 13 : return "othrOp"+(++othrop);
        }
        return "null";
    }
    
    public int lookup(String token)
    {
        int indx=-1;
        
        for(int i=0;i<tbData.size();i++)
        {
            if(tbData.get(i).token.equals(token))
            {
                indx=i;
                break;
            }
        }
        
        return indx;
    }
    
    public void resetSymTable()
    {
        keywrd=id=intr=chr=flt=str=punc=arith=relop=logop=bitop=assig=othrop=0;
        tbData.clear();
        linetrack=1;
    }
    
    public int displaySymbTbonJtable(JTable table)
    {
        if(tbData.isEmpty())
        { return 0; }
        
        int i=0;
        DefaultTableModel model=(DefaultTableModel)table.getModel();        
        Token row=null;
        for(i=0;i<tbData.size();i++)
        {
            row=tbData.get(i);
            model.insertRow(table.getRowCount(), new Object[]{row.lexeme,row.token,row.lineNo});
        }
        
        return i;
    }
    
    public ArrayList<Token> returnTokenStream()
    {
        return tknStrm;
    }
    
}

/*
class Token
{
        public String lexeme="";
        public String token="";
        public ArrayList<Integer> lineNo= new ArrayList<Integer>();

        public Token(String lx,String tkn,int ln) 
        {
            lexeme=lx;
            token=tkn;
            lineNo.add(ln);
        }
} 
*/