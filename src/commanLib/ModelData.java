package commanLib;

import Test.IntrFrameTest;
import editor.IntrFrm_GenProperty;
import editor.IntrFrm_RegExpr;
import editor.IntrFrm_transDiag;
import editor.CATModeler;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;


public class ModelData implements Serializable
{
    //-----variables-------------------
    
    //(0) General Properties
    protected String progLang;
    protected String mdlVersion;
    protected String author;
    protected String extension[];
    protected String descptn;
    protected int priority[]= new int[14];    
    
    //(1) Input Set
    protected InputSets inpSets[];
    
    //(2) Keyword
    protected String keywrds[];
    protected String keywrdRet;
    
    //(3) Identifier    
    protected String id;
    protected String idRet;
    protected GraphNode idGraph;
    
    //(4) Literal
    //(a) Integer
    protected String intgr;
    protected String intgrRet;
    protected GraphNode intgrGraph;
    
    //(b) Characater
    protected String ch;
    protected String chRet;
    protected GraphNode chGraph;
    
    //(c) Floating
    protected String flt;
    protected String fltRet;
    protected GraphNode fltGraph;
    
    //(b) String
    protected String strng;
    protected String strngRet;
    protected GraphNode strngGraph;
    
    //(5) Punctuator
    protected String punctuators[][];
    protected String puncRet;
    
    //(6) Operators
    protected TransDiagData operatr[];
    
    protected String otherOpr[];
    protected String otherOprRet;
    
    //(7) Comments
    //protected TransDiagData comments;    
    protected GraphNode comments;
    protected String commentsRet;
    
    
    //-----constructor
    public ModelData() 
    {
        //Default Priority
        /*
        for(int i=0;i<14;i++)
        {  priority[i]=i+1; }
        * 
        */
        
        idRet="id";
        intgrRet="int";
        chRet="char";
        fltRet="flt";
        strngRet="str";
        
        priority[0]=14;   //Coments
        priority[1]=2;    //Identifier
        priority[2]=1;    //Keyword
        for(int i=3;i<14;i++)
        {  priority[i]=i; }
        
        operatr= new TransDiagData[5];
        
        for(int i=0;i<operatr.length;i++)
        { operatr[i]= new TransDiagData(); }
        
        operatr[0].ret="arithOp";
        operatr[1].ret="relOp";
        operatr[2].ret="logicOp";
        operatr[3].ret="bitOp";
        operatr[4].ret="assignOp";
        
        commentsRet="comments";
        //comments=new TransDiagData();
        
    }
    
    public ModelData(ModelData d)
    {
        //(0) General Properties
        progLang=d.progLang;;
        mdlVersion=d.mdlVersion;
        author=d.author;
        extension=d.extension;
        descptn=d.descptn;
        priority=d.priority;    
    
    //(1) Input Set
        inpSets=d.inpSets;
    
    //(2) Keyword
        keywrds=d.keywrds;
        keywrdRet=d.keywrdRet;
    
    //(3) Identifier    
        id=d.id;
        idRet=d.idRet;
        idGraph=d.idGraph;
    
    //(4) Literal
    //(a) Integer
        intgr=d.intgr;
        intgrRet=d.intgrRet;
        intgrGraph=d.intgrGraph;
    
    //(b) Characater
        ch=d.ch;
        chRet=d.chRet;
        chGraph=d.chGraph;
    
    //(c) Floating
        flt=d.flt;
        fltRet=d.fltRet;
        fltGraph=d.fltGraph;
    
    //(b) String
        strng=d.strng;
        strngRet=d.strngRet;
        strngGraph=d.strngGraph;
    
    //(5) Punctuator
        punctuators=d.punctuators;
        puncRet=d.puncRet;
    
    //(6) Operators
        operatr=d.operatr;
    
        otherOpr=d.otherOpr;
        otherOprRet=d.otherOprRet;
    
    //(7) Comments
        comments=d.comments;
    }
    
    //-------------------------------------------
    
   
    //-------Methods---------------
    
    //(0) 
    public void addProperty(String pl,String ver, String auth, String ex[],String desc,int prior[])
    {
        progLang=pl;
        mdlVersion=ver;
        author=auth;
        extension=ex;
        descptn=desc;
        priority=prior;
    }
    
    /*
    public String[] getExtensions()
    {
        return extension;
    }
    * 
    */
     
    public boolean chkProperties()
    {
        if(progLang==null || progLang.equals(""))
        { return false; }
        
        if(mdlVersion==null || mdlVersion.equals(""))
        { return false; }
        
        if(author==null || author.equals(""))
        { return false; }
        
        if(extension==null)
        { return false; }
        
        if(descptn==null || descptn.equals(""))
        { return false; }
        
        return true;
    }
    
    //(1)    
    public void resetInputSet()
    {
        inpSets=null;
    }
    
    public boolean addInputSet(char nm,String setString)
    {        
        if(inpSets==null)  //if null;
        {
            inpSets = new InputSets[1];
            inpSets[0] = new InputSets();
            if(inpSets[0].addSet(nm, setString)==false)
            { return false; }
        }
        else
        {
            int l=inpSets.length;
            InputSets tmp[] = new InputSets[l+1];     //create new one         
            System.arraycopy(inpSets, 0, tmp, 0, l);  //copy array
            tmp[l] = new InputSets();                 //add last element
            if(tmp[l].addSet(nm, setString)==false)
            {  return false;  }
            else
            { inpSets=tmp;   }                           // set inpSets            
        }
        
        return true;
    }
    
    public boolean isInputSet(char setNm)
    {
        if(inpSets==null)
        { return false; }
        
        for(int i=0;i<inpSets.length;i++)
        {
            if(inpSets[i].setName==setNm)
            {  return true; }
        }
        return false;
    }
    
    public boolean doesSetContains(char setNm, char inp)
    {
        boolean flag=true;
        
        if(setNm=='z')               // z as UNIVERSAL SET
        { return true; }
        
        //find set
        InputSets ref=null;
        for(int i=0;i<inpSets.length;i++)
        {
            if(inpSets[i].setName==setNm)
            { ref=inpSets[i]; flag=true; break; }
        }
        
        if(ref==null)
        { flag=false; }
        else
        {
            flag=ref.charSet.contains(inp); 
        }
        
        return flag;
    }
    
    //(2)
    public void addKeywords(String kywrd[],String ret)
    {
        keywrds=kywrd;
        keywrdRet=ret;
    }
    
    public boolean isKeyword(String str)
    {
        if(keywrds==null)
        { return false; }
        
        for(int i=0;i<keywrds.length;i++)
        {
            if(str.equals(keywrds[i]))
            { return true; }
        }
        return false;
    }
    
    //(3) 
    public void addIdentifier(String exp,String ret,GraphNode g)
    {
        id=exp;
        idRet=ret;
        idGraph=g;
    }
    
    //(4)
    //(a) Integer
    public void addInteger(String exp,String ret,GraphNode g)
    {
        intgr=exp;
        intgrRet=ret;
        intgrGraph=g;
    }
    
    //(b) Character
    public void addCharacter(String exp,String ret,GraphNode g)
    {
        ch=exp;
        chRet=ret;
        chGraph=g;
    }
    
    //(c) Floating
    public void addFloating(String exp,String ret,GraphNode g)
    {
        flt=exp;
        fltRet=ret;
        fltGraph=g;
    }
    
    //(d) String
    public void addString(String exp,String ret,GraphNode g)
    {
        strng=exp;
        strngRet=ret;
        strngGraph=g;
    }
    
    //(5) Punctuators
    public void addPunctuators(String p[][],String r)
    {
        punctuators=p;
        puncRet=r;
    }
    
    
    //(6) Operator or (7) Comments
    public void addOprtrOrCmm(GraphNode t, String r,int id)
    {
        if(id>=0 && id<=4)
        {
            operatr[id].t=t;
            operatr[id].ret=r;
        }                
        else  //Comments
        {
            comments=t;
            commentsRet=r;
        }
    }
    
    public void addOtherOpr(String list[],String ret)
    {
        otherOpr=list;
        otherOprRet=ret;
    }
    
        
    //--------------------------------------------
        
    public void loadGenProp(IntrFrm_GenProperty fRef)
    {
        PriorityMap prior = new PriorityMap();
        
        fRef.textF_prgmLang.setText(progLang);
        fRef.textF_mdl.setText(mdlVersion);
        fRef.textF_auth.setText(author);
        
        String ext="";
        if(extension!=null)
        {
        for(String i : extension)
        { 
            ext+=i;
            if(!i.equals(extension[extension.length-1]))
            {ext+=","; }
        }
        }
        fRef.textF_exts.setText(ext);
        
        fRef.textA_descp.setText(descptn);
        
        for(int i=0;i<14;i++)
        { 
            //System.out.println(priority[i]+" "+new PriorityMap().getModelName(priority[i])); 
            fRef.table_mdlPrior.getModel().setValueAt(priority[i], i, 0);
            fRef.table_mdlPrior.getModel().setValueAt(prior.getModelName(priority[i]), i, 1);
        }
        
    }
    
    public int loadPunctFrm(javax.swing.JTable table, javax.swing.JTextField ret)
    {
        if(punctuators==null)
        { return 0; }
        
        DefaultTableModel model=(DefaultTableModel)table.getModel();                
        
        for(int i=0;i<punctuators.length;i++)
        {
            model.insertRow(table.getRowCount(), new Object[]{punctuators[i][0],punctuators[i][1]});
        }
        
        ret.setText(puncRet);
        
        return punctuators.length;
    }
    
    public void loadTransDiagFram(String fName,IntrFrm_transDiag tRef)
    {
        GraphNode gr=null;
        String r="";
        int id=-1;
        
        if(fName.equals("Arithmetic"))
        { gr=operatr[0].t; r=operatr[0].ret; id=0; }
        else if(fName.equals("Relational"))
        { gr=operatr[1].t; r=operatr[1].ret; id=1; }
        else if(fName.equals("Logical"))
        { gr=operatr[2].t; r=operatr[2].ret; id=2; }
        else if(fName.equals("Bitwise"))
        { gr=operatr[3].t; r=operatr[3].ret; id=3; }
        else if(fName.equals("Assignment"))
        { gr=operatr[4].t; r=operatr[4].ret; id=4; }        
        else if(fName.equals("Comments"))
        { gr=comments; r=commentsRet; id=7; }
         
        System.out.println("Loading data as [t,r,id] : "+gr+","+r+","+id);
        
        tRef.loadData(gr, r,id);
    }
    
    public void loadRegExprFram(String fName,IntrFrm_RegExpr fRef)
    {
        String expr="",ret="";
        
        if(fName.equals("Identifier"))
        { expr=id; ret=idRet; }
        else if(fName.equals("Integer"))
        { expr=intgr; ret=intgrRet; }
        else if(fName.equals("Character"))
        { expr=ch; ret=chRet; }
        else if(fName.equals("Floating (Decimal)"))
        { expr=flt; ret=fltRet; }
        else if(fName.equals("String"))
        { expr=strng; ret=strngRet; }
        
        fRef.loadData(expr, ret);
        
    }
    
    public int loadInputSetFrm(javax.swing.JTable table)
    {
        if(inpSets==null)
        { return 0; }
        
        DefaultTableModel model=(DefaultTableModel)table.getModel();        
        
        for(int i=0;i<inpSets.length;i++)
        {
            model.insertRow(table.getRowCount(), new Object[]{inpSets[i].setName,retInpSetString(inpSets[i].charSet)});
        }
        
        return inpSets.length;
    }
    
    public int loadKeywrdFrm(javax.swing.JTable table, javax.swing.JTextField ret)
    {
        if(keywrds==null)
        { return 0; }
        
        DefaultTableModel model=(DefaultTableModel)table.getModel();                
        for(int i=0;i<keywrds.length;i++)
        {
            model.insertRow(table.getRowCount(), new Object[]{keywrds[i]});
        }
        
        ret.setText(keywrdRet);
        
        return keywrds.length;
    }
    
    public int loadOtherOprFrm(javax.swing.JTable table, javax.swing.JTextField ret)
    {
        if(otherOpr==null)
        { return 0; }
        
        DefaultTableModel model=(DefaultTableModel)table.getModel();                
        for(int i=0;i<otherOpr.length;i++)
        {
            model.insertRow(table.getRowCount(), new Object[]{otherOpr[i]});
        }
        
        ret.setText(otherOprRet);
        
        return otherOpr.length;
    }
    
    
    //------------------------------
    
    private String retInpSetString(ArrayList<Character> set)
    {
        String str="";
        
        for(int i=0;i<set.size();i++)
        {   
            str+=set.get(i);
            if(i!=set.size()-1)
            { str+=","; }
        }
        
        return str;
    }
        
    //------------------File handling--------------
    public static void saveData(ModelData ref,String path) throws Exception
    {   
        FileOutputStream fout=null;
        ObjectOutputStream oos=null;
        
        try
        {
            fout = new FileOutputStream(path);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(ref);
        }
        finally
        {
            if(oos!=null)
            { oos.close(); }
        }
            
    }
    
    public static ModelData readData(String path) throws Exception
    {
        ModelData ref=null;
        FileInputStream fin=null;
        ObjectInputStream ois =null;
                
        try
        {
            fin = new FileInputStream(path);            
            ois = new ObjectInputStream(fin);            
            ref=(ModelData)ois.readObject();       
        }   
        finally
        {
            if(ois!=null)
            { ois.close(); }
        }
        
        return ref;
    }
    //----------------------------------------------
    
    public DefaultListModel retEmptyModelList()
    {
        DefaultListModel LstMdl = new DefaultListModel();
        
        if(keywrds==null)
        { LstMdl.addElement("Keywords"); }
        
        if(id==null || id.equals(""))
        { LstMdl.addElement("Identifier"); }
        
        if(intgr==null || intgr.equals(""))
        { LstMdl.addElement("Integer (Literals)"); }
        
        if(ch==null || ch.equals(""))
        { LstMdl.addElement("Characater (Literals)"); }
        
        if(flt==null || flt.equals(""))
        { LstMdl.addElement("Floating (Literals)"); }
        
        if(strng==null || strng.equals(""))
        { LstMdl.addElement("String (Literals)"); }
        
        if(punctuators==null)
        { LstMdl.addElement("Punctuators"); }

        if(operatr[0].t==null)
        { LstMdl.addElement("Arithmatic (Operators)"); }
        
        if(operatr[1].t==null)
        { LstMdl.addElement("Relational (Operators)"); }
        
        if(operatr[2].t==null)
        { LstMdl.addElement("Logical (Operators)"); }
        
        if(operatr[3].t==null)
        { LstMdl.addElement("Bitwise (Operators)"); }
        
        if(operatr[4].t==null)
        { LstMdl.addElement("Assignment (Operators)"); }
        
        if(otherOpr==null)
        { LstMdl.addElement("Others (Operators)"); }
        
        if(comments==null)
        { LstMdl.addElement("Comments"); }
        
        return LstMdl;
    }
    
    //temp---------
    /*
    public void showData()
    {
        
        System.out.println("Input Sets : ");
        for(int i=0;i<inpSets.length;i++)
        {   System.out.println(inpSets[i].setName+" : "+inpSets[i].charSet); }
         
        
         
        
        
        System.out.println("Keywords : ");
        for(int i=0;i<keywrds.length;i++)
        {   System.out.println(keywrds[i]); }
        System.out.println("Return Type : "+keywrdRet);
        
        
        
        
        
        //System.out.println("Identifier : "+id+"  Return Type : "+idRet);
        
        
        System.out.println(operatr.length);
        
        for(int i=0; i<operatr.length;i++)
        {
            System.out.println(i+" - Graph ref : "+operatr[i].t+" , Return : "+operatr[i].ret);
        }
        
       
        
        if(punctuators!=null)
        {
        
        for(int i=0;i<punctuators.length;i++)
        {
            for(int j=0;j<2;j++)
            {
                System.out.print(punctuators[i][j]+" ");
            }
            System.out.println();
        }
        
        }
        System.out.println("Return Type : "+puncRet);
        
        
    }
    */
    
    public String toString()
    {
        String ret="";
        
        ret+="\nData of ModelData\n-----------------------------";
        ret+="\nGenreal Properties : \n";
        ret+=" Programing Language : "+progLang+"\n";
        ret+=" Model Version : "+mdlVersion+"\n";
        ret+=" Author : "+author+"\n";
        ret+=" Extension(s) : ";
        if(extension==null)
        { ret+="null"; }
        else
        {
            for(String i : extension)
            { ret+=i+","; }
        }
        ret+=" \nDescription : "+descptn+"\n";
        ret+=" Priority : ";
        for(int i : priority)
        { ret+=i+" "; }
        
        
        
        ret+="\n\nTotal Input Sets : "; ret+=inpSets!=null?inpSets.length : "null";
        
        
        ret+="\n\nTotal Keywords : "; ret+=keywrds!=null?keywrds.length : "null";
        
        ret+="\n\nIdentifiers : \n";
        ret+=" Regular Expr : "+id+"\n";
        ret+=" Token Type : "+idRet+"\n";
        ret+=" Graph (Root) : ";  ret+=idGraph!=null?idGraph.lb:"null"; ret+="\n";
        
        
        ret+="\n-----------------------------\n";
        
        return ret;
    }
    
    
}
class InputSets implements Serializable
{    
    char setName;        
    ArrayList<Character> charSet;   
    
    public boolean addSet(char nm, String str)
    {
        boolean flag=true;
        
        ArrayList<Character> set = new ArrayList<Character>();        
        StringTokenizer token = new StringTokenizer(str, ",");
        char c;
        String tmp="";
        while(token.hasMoreTokens())
        {
            tmp=token.nextToken();
            if(tmp.length()!=1)
            { flag=false; break; }
            
            c=tmp.charAt(0);
            if(c==' ')
            { flag=false; break; }
            set.add(c);       
        }
        
        if(flag)
        { 
            setName=nm;
            charSet=set; 
        }
        
        return flag;
    }
}

/*
class TransDiagData implements Serializable
{
    public GraphNode t;
    String ret="";
}

*/