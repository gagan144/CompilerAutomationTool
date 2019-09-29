package commanLib; 

import Compiler.SymbolTable;
import com.mxgraph.swing.mxGraphComponent;
import java.awt.Color;
import java.awt.Component;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Grammar implements Cloneable
{
    ArrayList<Production> grmrProdctns = new ArrayList<Production>();
    private HashMap<String,HashSet<String>> allFirst = new HashMap<String, HashSet<String>>();
    private HashMap<String,HashSet<String>> allFollow = new HashMap<String, HashSet<String>>();
    
    private HashMap<Integer, String> terminalSet = null;
    private HashMap<Integer, String> nonTerminalSet = null;
    
    /*
    private HashMap<Integer, String> ll1_colNames= new HashMap<Integer, String>();
    private HashMap<Integer, String> ll1_rowName= new HashMap<Integer, String>();
    private Integer ll1_table [][]= null;
    private HashMap<CellPos,Integer> ll1_extra= null;
    private ArrayList<String> ll1_errorRecv = new ArrayList<String>();
    */
    private ParsingTable ll1Table = new ParsingTable();
    private boolean isLL1Flag;
    
    private ParsingTableBtmUp slrTable = null;
    private boolean isSLR;
    private ItemProdctnSetNode slrDfaStart=null;
    
    private ParsingTableBtmUp clrTable = null;
    private boolean isCLR;
    private ItemProdctnSetNode clrDfaStart=null;
    
    private ParsingTableBtmUp lalrTable = null;
    private boolean isLALR;
    private ItemProdctnSetNode lalrDfaStart=null;
    
    private int parseChoice=-2;
    
    public static final int LL = -1;
    public static final int SLR = 0;
    public static final int CLR = 1;
    public static final int LALR = 2;
    
    
    public boolean addGrammar(String grammarText)
    {           
        grmrProdctns.clear();        
        
        if(grammarText.equals(""))
        {
            return false;
        }
        else if(grammarText.contains("$"))
        { return false; }
            
        
        String line="";
        String tmp="",tmp2="";
        ArrayList<String> left =new ArrayList<String>();
        ArrayList<String> right =new ArrayList<String>();
        try
        {
            StringTokenizer stLine = new StringTokenizer(grammarText,"\n\r");
            
            while(stLine.hasMoreTokens())
            {
                left = new ArrayList<String>();
                right = new ArrayList<String>();
                
                line=stLine.nextToken();
                if(!line.contains(String.valueOf(SpecialSymbol.PRODUCTION_ARROW)))
                {
                    throw new Exception("No Arrow!");
                }
                
                StringTokenizer stArrow = new StringTokenizer(line, String.valueOf(SpecialSymbol.PRODUCTION_ARROW));
                if(stArrow.countTokens()==0)
                {
                    throw new Exception("No left and Right Side!");
                }
                
                while(stArrow.hasMoreTokens())
                {
                    //Left Side
                    tmp=stArrow.nextToken(); 
                    if(tmp.length()==0)
                    { throw new Exception("No Left Side"); }
                    
                    StringTokenizer stSpace = new StringTokenizer(tmp);
                    while(stSpace.hasMoreTokens())
                    {
                        tmp2=stSpace.nextToken();
                        if(tmp2.contains("|"))
                        { throw new Exception("Or in Left Side"); }
                        left.add(tmp2);
                    }
                                        
                    //Right Side
                    tmp=stArrow.nextToken(); 
                    if(tmp.length()==0)
                    { throw new Exception("No Right Side"); }
                    
                    StringTokenizer stOr = new StringTokenizer(tmp, "|");                    
                    while(stOr.hasMoreTokens())
                    {
                        stSpace = new StringTokenizer(stOr.nextToken());
                        while(stSpace.hasMoreTokens())
                        {
                            tmp=stSpace.nextToken(); 
                            if(!Character.isWhitespace(tmp.charAt(0)))
                            {right.add(tmp); }
                        }
                        if(!right.isEmpty())
                        { grmrProdctns.add(new Production(left, right));  }
                        right=new ArrayList<String>();
                    }
                    
                }
                
            }
                        
        }catch(Exception e)
        { 
            grmrProdctns.clear(); 
            //System.out.println("Invalid Grammar! "+e);   
            return false;
        }        
        
        return true;
    }
    
    public void addProductionToGrammar(Production prod)
    {
        grmrProdctns.add(prod);
    }
        
    
    private int getProductionType(Production prod)
    {        
        
        if(prod.left.size()==1 &&  isNonTerminal( prod.left.get(0) ) )  // type 2 or 3
        {
            int rtSize=prod.right.size();
            if(rtSize==1 && !isNonTerminal(prod.right.get(0)) )  // type 3 : single terminal
            {
                return 3;
            }
            else if(rtSize==2)    //type 3 : aB or Ba
            {
                if( !isNonTerminal(prod.right.get(0)) &&  isNonTerminal(prod.right.get(1)) )  // aB
                { return 3; }
                else if( isNonTerminal(prod.right.get(0)) &&  !isNonTerminal(prod.right.get(1)) )  // Ba
                { return 3; }
                else
                { return 2; }
            }
            else
            { return 2; }
        }
        else    //type 0 or 1
        {
            if(prod.left.size()<=prod.right.size())  //type 1
            {
                //chk for atleast one NT in left side
                boolean flag=false;
                for(int i=0;i<prod.left.size();i++)
                {
                    if(isNonTerminal(prod.left.get(i)))
                    { flag=true; break; }
                }                
                if(flag)     
                { return 1; }
                else
                { return 0; }
            }
            else
            { return 0; }
        }
                
    }
    
    public int getGrammarType()
    {
        int type=3;
        
        //Production currProd=null;
        int prodType;
        for(int i=0;i<grmrProdctns.size();i++)        
        {
            prodType=getProductionType(grmrProdctns.get(i));
            
            switch(prodType)
            {
                case 3 : type=Math.min(type,3);break;
                case 2 : type=Math.min(type,2);break;
                case 1 : type=Math.min(type,1);break;
                case 0 : type=Math.min(type,0);break;
                default : type=-1;                
            }        
            
        }//for
        
        if(type==1 || type==3)
        {   
            String startSym=grmrProdctns.get(0).left.get(0);
            
            //chk for S→ϵ
            boolean found =false;
            Production currProd=null;
            for(int i=0;i<grmrProdctns.size();i++)
            {
                currProd=grmrProdctns.get(i);
                if(currProd.left.get(0).equals(startSym) && currProd.right.get(0).charAt(0)==SpecialSymbol.EPSILON)
                {
                    found=true; break;
                }
            }
            
            if(found)
            {
                //chk if S on right side
                boolean flag=false;
                for(int i=0;i<grmrProdctns.size();i++)
                {
                    currProd=grmrProdctns.get(i);
                    for(int j=0;j<currProd.right.size();j++)
                    {
                        if(currProd.right.get(j).equals(startSym))
                        { flag=true; break; }
                    }
                }
                if(flag)
                { type--; }
            }
        }
        
        return type;
    }
    
   /* 
    public int getGrammarType()
    {
        int type=3;
        Production currProd=null;       
        ArrayList<ArrayList<String>> epsilnTermnl = new ArrayList<ArrayList<String>>();
        
        //chk S→ϵ            
        //(1) add S if S→ϵ
        for(int i=0;i<grmrProdctns.size();i++)        
        {
            currProd=grmrProdctns.get(i);
            
            if(currProd.right.get(0).charAt(0)==SpecialSymbol.EPSILON)
            {
                epsilnTermnl.add(currProd.left);
            }
        }
        
        //(2) chk if its on right side
        for(int i=0;i<grmrProdctns.size();i++)        
        {
            currProd=grmrProdctns.get(i);
            
            for(int s=0;s<epsilnTermnl.size();s++)
            {
                if(currProd.right.size()==epsilnTermnl.get(s).size())
                {
                    ArrayList curlistElm=epsilnTermnl.get(s);
                    for(int k=0;k<curlistElm.size();k++)
                    {
                        if(!currProd.right.get(k).equals(curlistElm.get(k)))
                        { return 0; }
                    }
                }                
            }
        }
        
        for(int i=0;i<grmrProdctns.size();i++)        
        {
            currProd=grmrProdctns.get(i);
        
            if(isProdctnType3(currProd))
            {
                //type=3;
                type=Math.min(type,3);
            }
            else if(isProdctnType2(currProd))
            {
                //type=Math.max(type,2);
                type=Math.min(type,2);
            }
            else if(isProdctnType1(currProd))
            {
                //type=Math.max(type,1);
                type=Math.min(type,1);
            }
            else if(isProdctnType0(currProd))
            {
                //type=Math.max(type,0);
                type=Math.min(type,0);
            }            
            else
            {
                type=-1;
            }
            
            //System.out.println("Type : "+type);  **
            
        }//for
        
        return type;
    }
    
    private boolean isProdctnType0(Production prod)
    {
        boolean flag=false;
        ArrayList<String> lft=prod.left;
        
        for(int i=0;i<lft.size();i++)
        {
            if(isNonTerminal(lft.get(i)))
            {
                flag=true;
                break;
            }
        }
        
        return flag;        
    }
    
    private boolean isProdctnType1(Production prod)
    {
        boolean flag=false;        
                
        ArrayList<String> lft=prod.left;
        ArrayList<String> rt=prod.right;
        
        if( (lft.size()<=rt.size()) && lft.size()!=0 )
        {
            for(int i=0;i<lft.size();i++)
            {
                if(lft.get(i).length()==1)
                {
                    if( isNonTerminal(lft.get(i)) )
                    {
                        flag=true;
                        break;
                    }
                }
                
            }
        }
        else
        {
            flag=false;
        }
        
        return flag;
        
    }
    
    private boolean isProdctnType2(Production prod)
    {
        boolean flag=true;
        
        if(prod.left.size()==1 && isNonTerminal(prod.left.get(0)))   //left
        {
            flag=true;
        }
        else
        {
            flag=false;
        }
        
        return flag;
    }
    
    private boolean isProdctnType3(Production prod)   //ERROR
    {
        boolean flag=true;
        
        if(prod.left.size()==1 && isNonTerminal(prod.left.get(0)))   //left
        {
            //right
            ArrayList<String> rt=prod.right;
            boolean race=false;
            
            if(rt.size()==1 && isNonTerminal(rt.get(0)) )    //only one element
            {
                flag=false; 
            }
            else
            {
                for(int i=0;i<rt.size()-1;i++)
                {         
                    
                    if(isNonTerminal(rt.get(i)))  //non Terminal
                    {
                        if(isNonTerminal(rt.get(i)))  //non Terminal
                        { flag=false; break; }
                        else                             //terminal                        
                        {
                            if(race)
                            { flag=false; break;}
                            else
                            { race=true; }
                        }
                    }else   //terminal
                    {
                        if(isNonTerminal(rt.get(i+1)))  //non termninal
                        {
                            if(race)
                            { flag=false; break;}
                            else
                            { race=true; }
                        }
                        else                              //terminal
                        { flag=false; break; }
                    }
                }//for
                
            }//if(rt.length()==1 && isNonTerminal(rt.charAt(0)))
            
        }
        else
        {
            flag=false;
        }
             
        
        return flag;
    }
    
 */
    
    public boolean isNonTerminal(String str)
    {
        if(str.length()==1 ) //if(str.length()==1 || (str.length()==2 && str.charAt(1)=='\'') )
        {
            char c=str.charAt(0);
            if(c>='A' && c<='Z')
            { return true; }
        }
        else
        {            
            char c=str.charAt(0);
            if(c>='A' && c<='Z')
            { 
                for(int i=1;i<str.length();i++)
                {
                    if(str.charAt(i)!='\'')
                    { return false; }
                }
                return true;
            }
        }
        return false;
    }
    
    public String retGrammarString()
    {
        String ret="";
        
        if(!grmrProdctns.isEmpty())
        {
            Production temp=null;
            for(int i=0;i<grmrProdctns.size();i++)
            {
                temp=grmrProdctns.get(i);
                
                ret+=temp;
                
                if(i!=grmrProdctns.size()-1)
                { ret+="\n"; }
            }
        }
        
        return ret;
    }
    
    public void displayGrammarOnTextPane(JTextPane txtPn)
    {
        Style nonTermStyle = txtPn.addStyle("NonTer", null); 
        StyleConstants.setForeground(nonTermStyle, new Color(0,153,0));     //GREEN    
        
        Style termBoldStyle = txtPn.addStyle("termBold", null);        
        //StyleConstants.setForeground(nonTermStyle, new Color(0,0,0));     //BLACk    
        StyleConstants.setBold(termBoldStyle, true);
        
        Style arrowStyle = txtPn.addStyle("arrow", null);
        StyleConstants.setForeground(arrowStyle, new Color(222,122,0));    //ORANGE
        
        Style eplsnStyle = txtPn.addStyle("epsilon", null);
        StyleConstants.setForeground(eplsnStyle, Color.red);    
                
        txtPn.setText("");
        StyledDocument doc = txtPn.getStyledDocument();               
        
        String str="";
        char c;
        try
        {   
            if(!grmrProdctns.isEmpty())
            {
                Production temp=null;
                for(int i=0;i<grmrProdctns.size();i++)
                {
                    temp=grmrProdctns.get(i);
                    
                    //left
                    for(int j=0;j<temp.left.size();j++)   //for no of left elements
                    {
                        str=temp.left.get(j);
                        
                        if(isNonTerminal(str))
                        {
                            doc.insertString(doc.getLength(), String.valueOf(str), nonTermStyle );
                        }
                        else if(str.length()>1) 
                        {
                            doc.insertString(doc.getLength(), str, termBoldStyle );
                        }
                        else
                        {
                            doc.insertString(doc.getLength(), str, null );
                        }
                        
                    }
                
                    //Arrow
                    doc.insertString(doc.getLength(), String.valueOf(SpecialSymbol.PRODUCTION_ARROW), arrowStyle );
                    
                    //right
                    for(int j=0;j<temp.right.size();j++)   //for no of right elements
                    {
                        str=temp.right.get(j);
                        
                        
                        if(isNonTerminal(str))
                        {
                            doc.insertString(doc.getLength(), String.valueOf(str), nonTermStyle ); // System.out.println(str); **
                        }
                        else if(str.length()>1) 
                        {
                            doc.insertString(doc.getLength(), str, termBoldStyle );  
                        }
                        else
                        {
                            if(str.charAt(0)==SpecialSymbol.EPSILON)
                            {
                                doc.insertString(doc.getLength(), str, eplsnStyle );
                            }
                            else
                            {
                                doc.insertString(doc.getLength(), str, null );                                
                            }
                            
                        }                        
                    }
                    
                    
                    if(i!=grmrProdctns.size()-1)
                    { 
                        doc.insertString(doc.getLength(), "\n", null );
                    }
                }
            }
            
        }
        catch(Exception e) 
        { System.out.println(e); }
    }
    
    public Grammar eliminateLeftRecr() throws Exception  //Only for type 2
    {
        Grammar newGramr = new Grammar();
        
        //Production currProd=null;
        ArrayList<String> curPrdLeft=null,curPrdRight=null;
        ArrayList<String> recNonTerm = new ArrayList<String>();
        
        //(1) Identify Left recursive Non Term        
        boolean flag;
        for(int i=0;i<grmrProdctns.size();i++)    //for each production to be chk
        {
            curPrdLeft=grmrProdctns.get(i).left;
            curPrdRight=grmrProdctns.get(i).right;            
                
            for(int p=0;p<grmrProdctns.size();p++)  //to match against each production            
            {
                if(curPrdLeft.get(0).equals(grmrProdctns.get(p).left.get(0)) &&  curPrdLeft.get(0).equals(grmrProdctns.get(p).right.get(0)) )
                {  
                    //System.out.println(curPrdLeft.get(0));   //FOUND
                    if(!recNonTerm.contains(curPrdLeft.get(0)))
                    {  recNonTerm.add(curPrdLeft.get(0)); }
                    break;
                }
   
            }           
            
        }        
        //System.out.println(recNonTerm);
        if(recNonTerm.isEmpty())
        {
            return this;
        }
        
        //(2) elimate left recur        
        ArrayList<String> newProdLeft=null,newProdRight=null;
                
        for(int i=0;i<grmrProdctns.size();i++)    //for each production to be converted
        {
            curPrdLeft=grmrProdctns.get(i).left;
            curPrdRight=grmrProdctns.get(i).right;            
                
            //for(int p=0;p<grmrProdctns.size();p++)  //to match against each production            
            //{                                
                if(curPrdLeft.get(0).equals(curPrdRight.get(0)) ) //A->Aa
                {   
                    //left 
                    newProdLeft = new ArrayList<String>();
                    newProdLeft.add(curPrdLeft.get(0)+"'");
                    
                    //right
                    //newProdRight = new ArrayList<String>();
                    newProdRight=(ArrayList<String>) curPrdRight.clone();
                    newProdRight.remove(0);
                    newProdRight.add(curPrdLeft.get(0)+"'");
                }
                else if(recNonTerm.contains(curPrdLeft.get(0)))  //A->b
                {
                    //left no change
                    newProdLeft=curPrdLeft;
                    
                    //right                    
                    newProdRight=(ArrayList<String>) curPrdRight.clone();                    
                    newProdRight.add(curPrdLeft.get(0)+"'");
                    
                    for(int e=0;e<newProdRight.size();e++)
                    {
                        if(newProdRight.get(e).contains(String.valueOf(SpecialSymbol.EPSILON)))
                        {
                            newProdRight.remove(e);
                        }
                    }
                }
                else
                {
                    newProdLeft=curPrdLeft;
                    newProdRight=curPrdRight;
                }
                
                newGramr.addProductionToGrammar(new Production(newProdLeft, newProdRight));
                
            //}           
            
        } 
        
        //add epsilon prod
        if(!recNonTerm.isEmpty())
        {
            for(int i=0;i<recNonTerm.size();i++)
            {
                newProdLeft=new ArrayList<String>();
                newProdLeft.add(recNonTerm.get(i)+"'");
                
                newProdRight=new ArrayList<String>();
                newProdRight.add(String.valueOf(SpecialSymbol.EPSILON));
                
                newGramr.addProductionToGrammar(new Production(newProdLeft, newProdRight));
                
                //System.out.println("Adding epsiln prod as : "+new Production(newProdLeft, newProdRight));
            }
        }
        
        
        
        return newGramr;
    }    
    

    public Grammar leftFactoring() throws Exception
    {
        Grammar lftFctGram = new Grammar();
        /*
        try {
            lftFctGram=(Grammar) this.clone();            
        } catch (CloneNotSupportedException ex) 
        {
            return null;
        }
        */
        lftFctGram=this.createCopy();       System.out.println("COPIED GRAMMAR : \n"+lftFctGram.toString()+"\n");
                
        
        //HashMap<String,Integer> factNTTrack = new HashMap<String, Integer>();
        
        Production iProd=null,jProd=null,currProd=null;
        ArrayList<String> newPLeft = null;
        ArrayList<String> newPRight = null;
        
        ArrayList<String> currPrdRight=null;
        
        int minLength,intTmp,currNTBar=-1;
        ArrayList<Integer> toFactProdList = null;
        for(int i=0;i<lftFctGram.grmrProdctns.size();i++)
        {
            //System.out.println("Examing : "+lftFctGram.grmrProdctns.get(i));
            
            iProd=lftFctGram.grmrProdctns.get(i);
            minLength=Integer.MAX_VALUE;
            intTmp=0;
            toFactProdList = new ArrayList<Integer>();
            currNTBar=-1;
            
            //(A) find scs
            for(int j=i+1;j<lftFctGram.grmrProdctns.size();j++)
            {
                jProd=lftFctGram.grmrProdctns.get(j);
                
                if(iProd.isLeftSideSame(jProd))
                { 
                    intTmp=iProd.retShortestCommanSequenceIndx(jProd);
                    if(intTmp<=minLength && intTmp!=Integer.MAX_VALUE)
                    {
                        minLength=intTmp;
                        toFactProdList.add(j);
                    }
                }
            }
            
            //(B) Reduce
            if(toFactProdList.isEmpty())
            { continue; }            
            
                       
            currNTBar=retCurNTBar(lftFctGram, iProd.left.get(0));
            
            String newNT = iProd.left.get(0);
            for(int j=0;j<currNTBar+1;j++)
            {
                newNT+='\'';
            }
            
            //toFactProdList.add(0, i);
            
            System.out.println("To Reduce : "+toFactProdList+" | minLength : "+minLength+" | newNT : "+newNT);
            
            
            //(b)add new productions
            newPLeft= new ArrayList<String>();
            newPLeft.add(newNT);
            
            int flag=0;
            for(int j=toFactProdList.size()-1;j>=0;j--)
            {                
                currProd=lftFctGram.grmrProdctns.get(toFactProdList.get(j)+flag);
                
                newPRight= new ArrayList<String>();
                for(int k=minLength+1;k<currProd.right.size();k++)
                {
                    newPRight.add(currProd.right.get(k));
                }
                
                if(newPRight.isEmpty())
                { newPRight.add(String.valueOf(SpecialSymbol.EPSILON)); }
                lftFctGram.grmrProdctns.add(i+1, new Production(newPLeft, newPRight));
                
                
                System.out.println("New Production "+lftFctGram.grmrProdctns.get(i+1)+" at "+(i+1));
                
                flag++;
                
                if(toFactProdList.get(j)!=i)
                { lftFctGram.grmrProdctns.remove(toFactProdList.get(j)+flag); }
                
                
            }
            
            //(a) manupulate i production            
            currPrdRight=lftFctGram.grmrProdctns.get(i).right;
            
            newPRight= new ArrayList<String>();
            for(int j=minLength+1;j<currPrdRight.size();j++)
            {
                newPRight.add(currPrdRight.get(j));
            }
            if(newPRight.isEmpty())
            { newPRight.add(String.valueOf(SpecialSymbol.EPSILON)); }
            lftFctGram.grmrProdctns.add(i+1, new Production(newPLeft, newPRight));
            
            
            intTmp=currPrdRight.size()-minLength-1;
            for(int j=0;j<intTmp;j++)
            {
                currPrdRight.remove(currPrdRight.size()-1);
            }
            
            currPrdRight.add(newNT);
            System.out.println("i th Production : "+ lftFctGram.grmrProdctns.get(i));
            
            System.out.println("i th Production of Orignal Grammar: "+ grmrProdctns.get(i));
            
            
            System.out.println("Changed Grammar : \n"+lftFctGram+"\n---------------------\n");
            
            
            System.out.println("ORIGNAL GRAMMAR : \n"+this.toString());
            
        }
        
        return lftFctGram;
    }
    
    private int retCurNTBar(Grammar g, String NT)
    {
        int ret=-1;
        
        String currLeftSide=null;
        for(int i=0;i<g.grmrProdctns.size();i++)
        {
            currLeftSide=g.grmrProdctns.get(i).left.get(0);
            if(NT.charAt(0)==currLeftSide.charAt(0))
            {
                ret = Math.max( ret,  Math.abs( NT.length()- currLeftSide.length() )  ); 
            }
        }
        
        return ret;
    }
    
    /*
    public Grammar leftFactoring()
    {
        Grammar newGramr=new Grammar();        
        HashMap<String,Integer> newNonTermTrack = new HashMap<String, Integer>();
        HashSet<Integer> NtDone = new HashSet<Integer>();
        ArrayList<Integer> toBeFactrdPrdIdx = new ArrayList<Integer>();
        
        Production newProd=null;
        ArrayList<String> newPrdRight=null;
        
        boolean leftFactr=true;
        int matchIdx=Integer.MAX_VALUE,curMchIdx;
        int currProdNo=0;
        while(leftFactr && currProdNo<grmrProdctns.size())
        {
            //find the production to be factor
            for(int i=0;i<grmrProdctns.size();i++)
            {
                if(i!=currProdNo)
                {
                    if(grmrProdctns.get(currProdNo).isLeftSideSame(grmrProdctns.get(i)))
                    {
                        curMchIdx=retShortestCommanSequence(grmrProdctns.get(currProdNo), grmrProdctns.get(i));
                        if(curMchIdx!=-1)
                        { 
                            toBeFactrdPrdIdx.add(i);
                            matchIdx=Math.min(matchIdx, curMchIdx);
                        }                        
                        
                    }                    
                }
            }
            
            //left factor
            if(matchIdx!=-1)
            {
                newPrdRight = new ArrayList<String>();
                for(int i=0;i<=matchIdx;i++)
                {
                    newPrdRight.add(grmrProdctns.get(currProdNo).right.get(i));
                }                
                
                newProd=new Production(grmrProdctns.get(currProdNo).left, newPrdRight);
                newGramr.addProductionToGrammar(newProd);
                
                
                for(int i=0;i<grmrProdctns.size();i++)
                {
                    //if(!NtDone.contains(i))
                }
            }            
        }
        
        return newGramr;
    }
    * /
    
    /*
    private int retShortestCommanSequence(Production p1,Production p2)
    {
        ArrayList<String> right1,right2;
        right1=p1.right;
        right2=p2.right;
        int retIdx=-1;
        
        for(int i=0;i<right1.size();i++)
        {
            if(right1.get(i).equals(right2.get(i)))
            {  retIdx++;    }
            else
            { break; }
        }
        
        return retIdx;
    }
    */
    
    public HashSet<String> retFirst(String NT) throws Error
    {
        HashSet<String> first = new HashSet<String>();
        Production temp= null;
        //int[] arr= new int[100];
        
        String e= new String();
        e= String.valueOf(SpecialSymbol.EPSILON);
        
        int dummy[]= new int[grmrProdctns.size()];
        int x=0;
        for(int i=grmrProdctns.size()-1; i>=0 ;i--)
        {                 
            temp= grmrProdctns.get(i);
            String lhs= temp.left.get(0);
            
            if(lhs.equals(NT))
            {
                String rhs1= temp.right.get(0);
                if(!isNonTerminal(rhs1))
                    first.add(rhs1);
                else
                 dummy[x++]=i;           
            }
        }
        
       /* if(root.contains(NT))
        {
            return first;
            //continue;
        }
        else
        {
            root.add(NT);
            //if(!first.isEmpty())
              //  return first;
        }*/
        
        
            for(int i=0, k=0; i<x; i++)
            {
                temp= grmrProdctns.get(dummy[i]);
                int len_rhs= temp.right.size();
                //System.out.println("k= "+k);
                if(!(temp.right.get(k).equals(NT)))
                {
                    if(!isNonTerminal(temp.right.get(k)))
                    {
                        first.add(temp.right.get(k));
                    }
                    else
                    {
                        HashSet<String> y= new HashSet<String>();
                        HashSet<String> y1= new HashSet<String>();
                        HashSet<String> y2= new HashSet<String>();
                        int j=k;
                        int from_here=0;
                        if(root.contains(NT) && from_here==0)
                            continue;
                        else
                        {
                            root.add(NT); 
                            from_here=1;
                        }
                        String xyz= temp.right.get(j++);
                        if(allFirst.containsKey(xyz))
                            y1= allFirst.get(xyz);
                        else
                            y1= retFirst(xyz);
                        y.addAll(y1);
                        
                       /* if(root.contains(NT))
                            continue;
                        else
                            root.add(NT);*/
                        int flag=0;
                        while (y.contains(e) && j<len_rhs)
                        {
                            String next= temp.right.get(j++);
                            
                            if(isNonTerminal(next))
                            {
                                if(root.contains(NT) && from_here==0)
                                {   flag=1;
                                    break;
                                }
                                else
                                    root.add(NT);
                            
                                y.remove(e);
                                if(allFirst.containsKey(next))
                                    y2= allFirst.get(next);
                                else
                                    y2= retFirst(next);
                                y.addAll(y2);
                            
                            }
                            else
                            {
                                y.remove(e);
                                y.add(next); 
                            }
                           /* if(root.contains(NT))
                                continue;
                            else
                                root.add(NT);*/
                        }
                        if (flag==1) continue;
                         first.addAll(y);
                    }
                }
                else //if(temp.right.get(k).equals(NT))
                {
                    if(first.contains(e))
                    { 
                        i--;
                        if((k+1)<len_rhs)
                        k++;
                    }
                }
            }
            
        allFirst.put(NT, first); //always put value of first of NT in allFirst before returning
        return first;
    }
    
        
    private static HashSet<String> root;
    private static HashSet<String> rootfol;
    public HashSet<String> retFollow(String NT) throws Throwable
    {
        HashSet<String> follow = new HashSet<String>();
        Production temp= null;
        //String rhs= new String();
        //String next_to_NT= new String();
        
        String e= new String();
        e= String.valueOf(SpecialSymbol.EPSILON);
        
        HashSet<String> y;
        HashSet<String> y1;
        int k;
        
        if(NT.equals(grmrProdctns.get(0).left.get(0)))
            follow.add("$");
        for(int i=0; i<grmrProdctns.size(); i++)
        {
            temp= grmrProdctns.get(i);
            int len_rhs= temp.right.size();
            for(int j=0; j<len_rhs; j++)
            {
                //rhs= temp.right.get(j);
                if(NT.equals(temp.right.get(j)))
                {
                    k=j;
                    if(k!=(len_rhs-1))
                    {
                       k++;
                       if(!isNonTerminal(temp.right.get(k)))
                           follow.add(temp.right.get(k));
                       else
                       {
                           y= new HashSet<String>();
                           y1= new HashSet<String>();
                           String c= temp.right.get(k);
                           y= allFirst.get(c);
                           k++;
                           while(y.contains(e) && k<len_rhs)
                           {
                               y.remove(e);
                               c= temp.right.get(k);
                               if(isNonTerminal(c))
                               {
                                   //root = new HashSet<String>();
                                   y1= allFirst.get(c);
                                   y.addAll(y1);
                                   k++;
                               }
                               else y.add(c);
                           }//end of while
                           follow.addAll(y);
                           if(follow.contains(e))
                           {
                               follow.remove(e);
                               String r= temp.left.get(0);
                               HashSet<String> ff= new HashSet<String>();
                               if(rootfol.contains(NT))
                               {
                                    continue;
                            //allFollow.put(NT,follow);
                            //return follow;
                                }
                                else
                                {
                                    //rootfol = new HashSet<String>();
                                    rootfol.add(NT);
                                    ff= retFollow(r);
                                    follow.addAll(ff);
                                }
                           }
                       }
                       
                    }
                    else //if(j==len_rhs-1)
                    {
                        String r= temp.left.get(0);
                        HashSet<String> ff= new HashSet<String>();
                        if(rootfol.contains(NT))
                        {
                            continue;
                            //allFollow.put(NT,follow);
                            //return follow;
                        }
                        else
                        {
                            //rootfol = new HashSet<String>();
                            rootfol.add(NT);
                            ff= retFollow(r);
                            follow.addAll(ff);
                        }
                    }
                        
                }//close of if(rhs.equal(NT))
                //else continue;//break;
            }//close of inner for
        }//close of outer for
        allFollow.put(NT,follow);
        return follow;
    }
    
    public void calculateFisrtFollow() throws Exception//displayFirst()
    {   
        //clear all
        allFirst = new HashMap<String, HashSet<String>>();
        allFollow = new HashMap<String, HashSet<String>>();
        
        try{
        for(int i=grmrProdctns.size()-1; i>=0; i--)//for first
        {
            String c= grmrProdctns.get(i).left.get(0);
            HashSet<String> f= new HashSet<String>();
            if(!allFirst.containsKey(c))
            {
                root = new HashSet<String>();
                f= retFirst(c);
            }
          }
        }//end of for   
        //end of for
        catch(Error x)            
        {
             //System.out.println("Enter correct grammar !! First cant be calculated");
             allFirst=null;
        }
        //System.out.println("First ==>\n"+allFirst);
         
        try{
        for(int i=0; i<grmrProdctns.size(); i++)//for follow
        {
            
            String c= grmrProdctns.get(i).left.get(0);
            HashSet<String> f= new HashSet<String>();
            if(!allFollow.containsKey(c))
            {
                rootfol = new HashSet<String>();
                f= retFollow(c);
            }
        }//end of for   
            
        }//end of try block
        catch(Throwable y)            
        {
             //System.out.println("Enter correct grammar !! Follow cant be calculated");
             allFollow=null;
        }    
               
        //System.out.println("Follow ==>\n"+allFollow);
    } 
    
    
    public HashMap<String,HashSet<String>> returnAllFirst()
    {
        return allFirst;
    }
    
    public HashMap<String,HashSet<String>> returnAllFollow()
    {
        return allFollow;
    }
    
    
    
    //to delete
    /*
    public HashMap<String,HashSet<String>> retAllFirsts()
    {
        allFirst.clear();
        
        int []ar= new int[100];
        for(int i=0; i<grmrProdctns.size(); i++)
            ar[0]=0; 
        
        for(int i=0; i<grmrProdctns.size(); i++)
        {
            String c= grmrProdctns.get(i).left.get(0);
            HashSet<String> f= new HashSet<String>();
            
            try{
            if(!allFirst.containsKey(c))
               f= retFirst(c);
            }            
            catch(Error y)
            {
                //System.out.println("Enter correct grammar !!");                
                allFirst.clear();
                return null;
            }                
            
        }//end of for
        
        //System.out.println(allFirst);
        return allFirst;
    } 
    * 
    */
    
    private void calculateNoOfTAndNT()
    {
        terminalSet = new HashMap<Integer, String>();
        nonTerminalSet = new HashMap<Integer, String>();
        
        Production temp= null;
        String e= String.valueOf(SpecialSymbol.EPSILON);
        int count_nt=0;
        int count_t=0;
        for(int i=0; i<grmrProdctns.size(); i++)
        {
            temp= grmrProdctns.get(i);
            int lhs_len= grmrProdctns.get(i).left.size();
            for(int j=0; j<lhs_len; j++)
            {
                if(!nonTerminalSet.containsValue(temp.left.get(j)))
                {
                    nonTerminalSet.put(count_nt, temp.left.get(j));
                    count_nt++;
                }
            }//end of for loop scanning left hand side of production
            
            int rhs_len= grmrProdctns.get(i).right.size();
            for(int k=0; k<rhs_len; k++)
            {
                String s= temp.right.get(k);
                if(!isNonTerminal(s) && !terminalSet.containsValue(s) && !s.equals(e))
                {
                    terminalSet.put(count_t, s);
                    count_t++; //here epsilon also gets added
                }
                
            }// end of for loop scanning right side of production
            
        }//end of for loop scanning all productions
        
        final int rows= count_nt;
        final int columns= count_t +1; //+1 for $
        terminalSet.put(count_t, "$");
        
        System.out.println("NonTerminal : "+nonTerminalSet );
        System.out.println("Terminal : "+terminalSet);
        
    }
    
    //LL(1) Grammar   
    
    public void calculate_LL1_table() throws Exception
    {
        calculateNoOfTAndNT();
        
        Production temp= null;
        String e= new String();
        e= String.valueOf(SpecialSymbol.EPSILON);
        
        ll1Table.rowName = new HashMap<Integer, String>();
        HashMap<Integer, String> nt= ll1Table.rowName;
        ll1Table.colNames = new HashMap<Integer, String>();
        HashMap<Integer, String> t= ll1Table.colNames;
        
        int count_nt=0;
        int count_t=0;
        
        //counting no. of terminals and non terminals for deciding the size of the LL1 table
        for(int i=0; i<grmrProdctns.size(); i++)
        {
            temp= grmrProdctns.get(i);
            int lhs_len= grmrProdctns.get(i).left.size();
            for(int j=0; j<lhs_len; j++)
            {
                if(!nt.containsValue(temp.left.get(j)))
                {
                    nt.put(count_nt, temp.left.get(j));
                    count_nt++;
                }
            }//end of for loop scanning left hand side of production
            
            int rhs_len= grmrProdctns.get(i).right.size();
            for(int k=0; k<rhs_len; k++)
            {
                String s= temp.right.get(k);
                if(!isNonTerminal(s) && !t.containsValue(s) && !s.equals(e))
                {
                    t.put(count_t, s);
                    count_t++; //here epsilon also gets added
                }
                
            }// end of for loop scanning right side of production
            
        }//end of for loop scanning all productions
        
        final int rows= count_nt;
        final int columns= count_t +1; //+1 for $
        t.put(count_t, "$");        
        
        //System.out.println("* Terminals : "+t);
        //System.out.println("* Non-Terminals : "+nt);
        
        /*
        calculateNoOfTAndNT();
        
        ll1Table.rowName=(HashMap<Integer, String>) nonTerminalSet.clone();
        ll1Table.colNames=(HashMap<Integer, String>) terminalSet.clone();
        final int rows= nonTerminalSet.size();
        final int columns= terminalSet.size();
        
        
        //System.out.println("Terminals : "+t);
        //System.out.println("Non-Terminals : "+nt);        
        
        Production temp= null;
        String e= new String();
        */
        
        ll1Table.extra= new HashMap<CellPos, Integer>();
        
        ll1Table.tableData = new Integer[rows][columns];
        
        for(int r=0; r<rows; r++)
            for(int c=0; c<columns; c++)
                ll1Table.tableData[r][c]=0;
        
        //adding productions in LL1 table
        for(int r=0; r<rows; r++)
        {
            String NT= nt.get(r);
            //HashSet<String> y= new HashSet<String>();
            
            for(int i=0; i<grmrProdctns.size(); i++)
            {
                
                temp= grmrProdctns.get(i);
                if(temp.left.get(0).equals(NT))
                {
                    int flag=0;
                    HashSet<String> y= new HashSet<String>();
                    HashSet<String> y1= new HashSet<String>();
                    HashSet<String> y2= new HashSet<String>();
                    int len_rhs= temp.right.size();
                    String rhs1= temp.right.get(0);
                
                    if(!isNonTerminal(rhs1))
                        y.add(rhs1);
                    else
                    {
                        y=allFirst.get(rhs1);
                        flag=1;
                    }
                    int j=1;
                
                    if(flag==1)
                    {
                        while(y.contains(e) && j<len_rhs)
                        {
                            String rhs_next= temp.right.get(j++);
                            if(!isNonTerminal(rhs_next))
                                y.add(rhs_next);
                            else//if rhs_next is a non terminal
                            {
                                y1= allFirst.get(rhs_next);
                                y.remove(e);
                                y.addAll(y1);
                            }
                        }//end of while
                    }//end of if(flag==1)
                    if(y.contains(e))//adding follow of symbol on the lhs of the production
                    {
                        y.remove(e);
                        y2= allFollow.get(NT);
                        y.addAll(y2);
                    }
                    //System.out.println("Production No."+(i+1)+"=>"+y);  ***
                
                    for(int c=0; c<columns; c++)
                    {
                        if(y.contains(t.get(c)))
                        {
                            if(ll1Table.tableData[r][c]==0)
                                ll1Table.tableData[r][c]= i+1;
                            else// if that cell of parsing table is not empty add it in a different HashMap<CellPos,Production>
                            {
                                CellPos pos=new CellPos();
                                pos.row=r;
                                pos.col=c;
                                ll1Table.extra.put(pos,i+1);
                                //System.out.println("Extra hash map is :("+r+","+c+")==>production no."+(i+1));  ***
                            }
                        }
                    }//end of for of columns of LL1 table
                }//end of if(temp.left.get(0).equals(NT))
            }//end of for loop of scanning all productions with lhs as NT
        }//end of for of rows of LL1 table
        
        /*
        for(int r=0; r<rows; r++)
        {
            for(int c=0; c<columns; c++)
                System.out.print(ll1_table[r][c]+"\t");
            System.out.println();
        }
        */
                
        if(ll1Table.extra.isEmpty())
        {
            //System.out.println("It is LL1 grammar");
            isLL1Flag=true;
        }
        else
        {
            //System.out.println("It is not LL1 grammar");
            isLL1Flag=false;
        }
        
        //reverse maps
        ll1Table.createReveseMap();
            
    }
    
    public void displayLL1OnTable(JTable table)
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
        model.addColumn(new String("Non Terminal"));
        for(int i=0;i<ll1Table.colNames.size();i++)
        {
            model.addColumn(ll1Table.colNames.get(i));
        }
        
        //fill rows
        /*
        Object row[]=null;
        for(int i=0;i<ll1_rowName.size();i++)
        {
            row=new Object[ll1_colNames.size()+1];
            row[0]=ll1_rowName.get(i);
            for(int j=0;j<ll1_colNames.size();j++)
            {
                row[j+1]=ll1_table[i][j];
            }
            
            model.insertRow(table.getRowCount(), row);        
        }
        * 
        */
        
        /*
        int rows=ll1_rowName.size();
        int columns=ll1_colNames.size();
        
        for(int r=0; r<rows; r++)
        {
            System.out.print(ll1_rowName.get(r)+ "\t");
            for(int c=0; c<columns; c++)
            {
                if(ll1_table[r][c]==0)
                { System.out.print("null"+ "\t"); }
                else
                {
                    System.out.print( grmrProdctns.get(ll1_table[r][c]-1).toString() +"\t"  );
                }
                //System.out.print(ll1_table[r][c]+  "\t");
            }
            System.out.println();
        }*/
        
        int rows=ll1Table.rowName.size();
        int columns=ll1Table.colNames.size();
        Object row[]=null;
        for(int r=0; r<rows; r++)
        {
            row=new Object[ll1Table.colNames.size()+1];
            row[0]=ll1Table.rowName.get(r);
            for(int c=0; c<columns; c++)
            {
                if(ll1Table.tableData[r][c]==0)    //Emplt Error
                { row[c+1]=""; }
                else if(ll1Table.tableData[r][c]<0)     //Filled Error
                { row[c+1]=Math.abs(ll1Table.tableData[r][c]); }
                else
                {                    
                    row[c+1]=grmrProdctns.get(ll1Table.tableData[r][c]-1).toString();
                }                
            }
            model.insertRow(table.getRowCount(), row);            
        }
        
        
        if(!ll1Table.extra.isEmpty())
        {
            //System.out.println("Total "+ll1_extra.size());
            Set<CellPos> extraSet= ll1Table.extra.keySet();
            CellPos tmp=null;
            
            try
            {
                for(Iterator<CellPos> it = extraSet.iterator();; it.hasNext() )
                {                
                    tmp=it.next(); 
                    if(tmp!=null)
                    { 
                        //System.out.println(tmp+" : "+ll1_extra.get(tmp)); 
                        table.setValueAt(table.getValueAt(tmp.row, tmp.col+1)+" , "+grmrProdctns.get(ll1Table.extra.get(tmp)-1)  , tmp.row, tmp.col+1);
                    }
                }
            }catch(Exception e){}
        }
        
        
        //Decorating table        
                
    }   
    
    public boolean isLL1Grammar()
    {
        return isLL1Flag;
    }
    
    public void setLL1ErrRecv(ArrayList<String> errLst)
    {
        ll1Table.errorRecv=errLst;
    }
    
    public boolean applyLL1ErrVar(JTable table)
    {
        boolean ret=true;
        try
        {
            for(int i=0;i<ll1Table.tableData.length;i++)
            {
                for(int j=0;j<ll1Table.tableData[i].length;j++)                    
                {                
                    if(ll1Table.tableData[i][j]<=0)
                    {
                        String str=String.valueOf(table.getValueAt(i,j+1));  
                        if(str.equals(""))
                        { ll1Table.tableData[i][j]=0; continue; }
                        
                        int errVal=Integer.parseInt(str);    //System.out.println("errVal : "+errVal);
                        if(errVal>ll1Table.errorRecv.size() || errVal<0)                        
                        { throw new Exception("Invalid Error Variable!"); }
                        
                        ll1Table.tableData[i][j]=-1*errVal;
                    }
                }
            }
            
        }catch(Exception e)
        { //System.out.println(e.getMessage());
          ret=false; 
        }
        
        /* 
        System.out.println("");
        for(int i=0;i<ll1Table.tableData.length;i++)
            {
                for(int j=0;j<ll1Table.tableData[i].length;j++)                    
                {     
                    System.out.print(ll1Table.tableData[i][j]+" ");
                }
                System.out.println("");
            }
        */
        
        return ret;
    }
    
    //public String ll1parsingAlgo(ArrayList<Token> inputBuf,JTable table)
    public ParsingReturn ll1parsingAlgo(ArrayList<Token> inputBuf,JTable table) throws Exception
    {
        boolean disp=true;
        String retStr=null;
        DefaultTableModel model=null;
        Object jTbRowObj[]=null;
        
        if(table==null)
        { disp=false; }
        else
        { 
            model=(DefaultTableModel) table.getModel(); 
            
            //remoave all rows
            for( int i = model.getRowCount() - 1; i >= 0; i-- )
            {  model.removeRow(i); }            
            
        }
        
        //ParseTree        
        ParseTreeNode start=null,parent=null,newNode=null;
        Stack<ParseTreeNode> treeStack = new Stack<ParseTreeNode>();
        //---------
        
        //Initialize stack;
        Stack<String> stack = new Stack<String>();
        stack.push("$");
        stack.push(grmrProdctns.get(0).left.get(0));
        
        //add $ inputBuff
        inputBuf.add(new Token("$", "", 0));
        
        //Algo Variables
        String X="",tmp;
        Token a;
               
        int tbVal;
        ArrayList<String> right=null;
                
        //(1) set ip
        int ip=0;
        
        if(disp)
        {
            jTbRowObj = new Object[3];
            jTbRowObj[0]=stackToString(stack); //stack.toString();
            jTbRowObj[1]= inputBfToString(ip, inputBuf);
            jTbRowObj[2]="";
            
            model.insertRow(table.getRowCount(), jTbRowObj);                        
        }
        
        do
        {
            //(3) intialize variables
            X=stack.peek();
            a=inputBuf.get(ip);
            
            if(disp)
            { jTbRowObj= new Object[3]; }
            
            //(4)
            if(!isNonTerminal(X) || X.equals("$"))   //X is terminal
            {
                //(5)
                if(X.equals(a.toUseStr))
                {
                    //(6)
                    tmp=stack.pop();
                    ip++;
                    
                    if(disp)
                    {
                        jTbRowObj = new Object[3];
                        jTbRowObj[0]=stackToString(stack); //stack.toString();
                        jTbRowObj[1]= inputBfToString(ip, inputBuf);
                        jTbRowObj[2]="";
            
                        model.insertRow(table.getRowCount(), jTbRowObj);                        
                    }
                    
                    
                                        
                }
                else
                {
                    //(7)
                    retStr="NOT Accepted!"; //System.out.println("Reached at ip = "+ip);
                    start=null;
                    break; 
                }
            }
            else   // X is non Terminal
            {
                //(9)
                try
                {  tbVal=ll1Table.getProdNo(X, a.toUseStr); }
                catch(Exception e)
                {  
                   //System.out.println("Exception : "+e); 
                   retStr="NOT Accepted!"; 
                   start=null;
                   break; 
                }
                
                if(tbVal>0)  // production found
                {
                    tbVal--;
                    
                    //10
                    stack.pop();
                    
                    //ParseTree        
                    if(treeStack.isEmpty())
                    {
                        start=new ParseTreeNode(grmrProdctns.get(tbVal).left.get(0));
                        parent=start;
                    }
                    else
                    {
                        parent=treeStack.pop();
                    }
                    //---------
                    
                    //11
                    right=grmrProdctns.get(tbVal).right;                    
                    for(int k=right.size()-1;k>=0;k--)
                    { 
                        //ParseTree        
                        newNode= new ParseTreeNode(right.get(k));
                        start=ParseTreeNode.addNodeToEnd(start, newNode);
                        parent.addLinkToNBeg(newNode);
                        //---------
                        
                        if(! right.get(k).equals( String.valueOf(SpecialSymbol.EPSILON)) )
                        {                             
                            stack.push(right.get(k));   //System.out.print(right.get(k) + " ");
                            
                            //ParseTree        
                            if(isNonTerminal(right.get(k)))
                            { treeStack.push(newNode); }
                            //---------
                        }
                    }
                    
                    //12
                    if(disp)
                    {
                        jTbRowObj = new Object[3];
                        jTbRowObj[0]=stackToString(stack); //stack.toString();
                        jTbRowObj[1]= inputBfToString(ip, inputBuf);
                        jTbRowObj[2]= grmrProdctns.get(tbVal).toString();
            
                        model.insertRow(table.getRowCount(), jTbRowObj);                        
                    }
                    
                }
                else
                {
                    //(14)
                    retStr=tbVal==0 ? "LL(1) Parsing Error! No Error Recovery Found!" :  ll1Table.errorRecv.get( (Math.abs(tbVal)-1 )  );
                    start=null;
                    break;
                }               
                
            }
            
            
        }while( !X.equals("$") );
        
        if(retStr==null)
        { retStr="Accepted!"; }
        
        /*
        try 
        {
            System.out.println("## "+ll1Table.getProdNo("E", "+"));
        } catch (Exception ex) 
        {   System.out.println("Exception in LL1 - no data in reverse maps!");   }
        */
        
        //return retStr;   //null : OK , "" : not matched , message string : error
        return (new ParsingReturn(retStr, start));
    }
    
    private String inputBfToString(int startIdx,ArrayList<Token> buf)
    {
        String ret="";
        
        for(int i=startIdx;i<buf.size();i++)
        {
            ret+=buf.get(i).toUseStr+" ";
        }
        
        return ret;
    }
    
    private String stackToString(Stack<String> st)
    {
        String ret="";
        
        Object obArr[]=st.toArray();
        for(int i=0;i<obArr.length;i++)
        { ret+=obArr[i]+" "; }
        
        return ret;
    }
    
    public ItemProduction augmentStartSymbol()
    {
        //add starting new start symbol
        String stSymb = grmrProdctns.get(0).left.get(0); //System.out.println("Start Symbol : "+stSymb);
                
        int totQuot=0;
        String currleft;
        for(int i=1;i<grmrProdctns.size();i++)
        {
            currleft=grmrProdctns.get(i).left.get(0);
            if(currleft.startsWith(stSymb) && currleft.length()>stSymb.length())
            {
                totQuot=Math.max(totQuot, (currleft.length()-stSymb.length()) );
            }
        }
        //System.out.println("max qoutes found : "+totQuot );
        
        for(int i=0;i<(totQuot+1);i++)
        { stSymb+="'"; }
        //System.out.println("Start Symbol : "+stSymb);
        
        ArrayList<String> left= new ArrayList<String>();
        left.add(stSymb);
        ArrayList<String> right= new ArrayList<String>();
        right.add(grmrProdctns.get(0).left.get(0));
        
        return ( new ItemProduction(left, right) ); 
        
    }
    
    public ArrayList<ItemProduction> getAugmentedGrammar()
    {
        ArrayList<ItemProduction> augGram = new ArrayList<ItemProduction>();
        
        //add starting new start symbol
        String stSymb = grmrProdctns.get(0).left.get(0); //System.out.println("Start Symbol : "+stSymb);
                
        int totQuot=0;
        String currleft;
        for(int i=1;i<grmrProdctns.size();i++)
        {
            currleft=grmrProdctns.get(i).left.get(0);
            if(currleft.startsWith(stSymb) && currleft.length()>stSymb.length())
            {
                totQuot=Math.max(totQuot, (currleft.length()-stSymb.length()) );
            }
        }
        //System.out.println("max qoutes found : "+totQuot );
        
        for(int i=0;i<(totQuot+1);i++)
        { stSymb+="'"; }
        //System.out.println("Start Symbol : "+stSymb);
        
        ArrayList<String> left= new ArrayList<String>();
        left.add(stSymb);
        ArrayList<String> right= new ArrayList<String>();
        right.add(grmrProdctns.get(0).left.get(0));
        augGram.add(new ItemProduction(left, right));        
        
        
        for(int i=0;i<grmrProdctns.size();i++)
        {            
            augGram.add(new ItemProduction(grmrProdctns.get(i).left, grmrProdctns.get(i).right));
        }
        
        return augGram;
    }
    
    public Grammar createCopy()
    {
        Grammar copyGram = new Grammar();
        //copyGram.grmrProdctns=(ArrayList<Production>) grmrProdctns.clone();
        
        Production currProduction;
        for(int i=0;i<grmrProdctns.size();i++)
        {
            copyGram.grmrProdctns.add(i, grmrProdctns.get(i).createCopy());
        }
        
        
        
        return copyGram;
    }
    
    
    public void calculateSLRTable(JTable table) throws Exception //public void calculateSLRTable(JTable table,JTextArea textA,JPanel panelGrph) throws Exception
    {
        int nextNodelbl=0;
        ItemProdctnSetNode start,ptr,newNode,ref;
        ItemProduction currItProd,currItProdPtr,newItProd;
        String currSymbl;
        Grammar copyGram=null;
        //ArrayList<ItemProduction> newNodeSet= new ArrayList<ItemProduction>();
        
        //start = new ItemProdctnSetNode(nextNodelbl++,getAugmentedGrammar());
        start = new ItemProdctnSetNode(nextNodelbl++);
        start.prodSet.add(augmentStartSymbol());
        
        copyGram = this.createCopy();
        String symb=start.prodSet.get(start.prodSet.size()-1).getSymbolAfterDot();
        if(symb!=null)
        { start.SLRClosure(symb, copyGram, start.prodSet.size()-1); }
        
        ptr=start;

        while(ptr != null)
        {
            for(int i=0;i<ptr.prodSet.size();i++)
            {
                currItProd=ptr.prodSet.get(i);
                if(!currItProd.hasProdBeenProcessed() && !currItProd.isEpsilonProduction())
                {
                    currSymbl=currItProd.getSymbolAfterDot();
                    newNode=new ItemProdctnSetNode(nextNodelbl++);  
                    for(int j=i;j<ptr.prodSet.size();j++)
                    {
                        currItProdPtr=ptr.prodSet.get(j);
                        if(currItProdPtr.isPresent(currSymbl))
                        {
                            newItProd=currItProdPtr.createCopy();   
                            newItProd.incrementDotPositon();
                            newNode.prodSet.add(newItProd);  
                            currItProdPtr.setProcessed();    
                            
                            //addMoreProductions
                            //try{                                
                                copyGram = this.createCopy();
                                //System.out.println(newNode.prodSet.get(newNode.prodSet.size()-1).toString());
                                symb=newNode.prodSet.get(newNode.prodSet.size()-1).getSymbolAfterDot();
                                if(symb!=null)
                                { newNode.SLRClosure(symb, copyGram, newNode.prodSet.size()-1); }
                            //} catch (CloneNotSupportedException ex) 
                            //{  System.out.println("SLR - Exception Occured while cloning!");        }
                                //System.out.println("\n\n");
                            
                            
                        }// end of if
                    }// end of j loop
                   ref=ItemProdctnSetNode.doesSetNodeExistInGraph(start,newNode);
                   if(ref==null)
                   {
                       start=ItemProdctnSetNode.addSetNodeToGraph(start,newNode);
                       ptr.addLink(newNode,currSymbl);                       
                   }
                   else{
                       ptr.addLink(ref,currSymbl);
                       nextNodelbl--;
                   }                   
                }                
            }
            ptr=ptr.nextNode;
        }
        
        /*
        //Testing for output
        ptr=start;
        while(ptr!=null){
            System.out.println(ptr.toString());
            ptr=ptr.nextNode;
        }
        */        
        
        //display 
        //textA.setText("");
        int lines=1;
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object rowObj[] =null;
        
        //remoave all rows
        for( int i = model.getRowCount() - 1; i >= 0; i-- )
        {  model.removeRow(i); } 
        
        //add rows
        ptr=start;
        //String text="";
        while(ptr!=null)
        {
            rowObj=new Object[2];
            rowObj[0]=ptr.label;
            rowObj[1]=ptr.getProdtcString();
        
            //table
            lines=Math.max(lines, ptr.prodSet.size());
            table.setRowHeight(16 * lines  );
            model.insertRow(table.getRowCount(), rowObj);
            
            //textArea
            //text+="Set State : "+ptr.label+" - Links : "+ptr.toStringLinks()+"\n";           
            
            
            ptr=ptr.nextNode; 
        }
        //textA.setText(text);  
        
        slrDfaStart=start;
        
        //panelGrph=new Panel_mxGraphDisplay(ItemProdctnSetNode.createMxGraph(start));
        
        /*
        System.out.println("Grammar After SLR");;
        System.out.println(this.toString());
        */
        
        //(2)Create Table;
        int r= nextNodelbl;
        
        int c= terminalSet.size()+ nonTerminalSet.size();
        
        //System.out.println(r+" "+c);
        
        slrTable=new ParsingTableBtmUp(r, c, terminalSet, nonTerminalSet);
        
        ptr=start;
        ItmPrdSt_InternalNode ptrlink= null;
        ParseTblElmnt newElm= null;
        while(ptr!=null)
        {
            //shift & stateno
            if(ptr.links!=null)
            {
                //System.out.println("Examining state "+ptr.toString());
                ptrlink= ptr.links;
                while(ptrlink!=null)
                {
                    if(isNonTerminal(ptrlink.value))
                    {
                        newElm= new ParseTblElmnt(ParseTblElmnt.STATENO, ptrlink.addr.label);
                    }
                    else
                    {
                        newElm= new ParseTblElmnt(ParseTblElmnt.SHIFT, ptrlink.addr.label);
                    }
                    slrTable.addElement(ptr.label, ptrlink.value, newElm);
                    
                    ptrlink=ptrlink.nextLnk;
                }                                
            }
            
            
            //reduce & acc
            for(int k=0; k<ptr.prodSet.size(); k++)
            {
                currItProd=ptr.prodSet.get(k);
                if(currItProd.isDotAtLast() || currItProd.isEpsilonProduction())
                {
                    if(currItProd.left.equals( augmentStartSymbol().left )) // Acc
                    {                        
                        newElm= new ParseTblElmnt(ParseTblElmnt.ACCEPT, 0);
                        //System.out.println("** : "+i);
                        slrTable.addElement(ptr.label, "$", newElm);
                        
                    }
                    else  //reduce
                    {
                        int prodNo=-1;
                        for(int p=0;p<grmrProdctns.size();p++)
                        {
                            if(currItProd.isEqualToProduction(grmrProdctns.get(p)))
                            { prodNo=p+1; break; }
                        }                        
                        newElm= new ParseTblElmnt(ParseTblElmnt.REDUCE, prodNo);
                        for(Object i : allFollow.get(currItProd.left.get(0)).toArray())
                        {
                            //System.out.println("## : "+i);                            
                            slrTable.addElement(ptr.label, i.toString(), newElm);
                        }
                    }
                    
                }
            }
            
            
            //----------
            ptr=ptr.nextNode;
        }
        
    }
            
    public mxGraphComponent getSLRmxGraph() throws Exception
    {
        return ItemProdctnSetNode.createMxGraph(slrDfaStart);
    }
    
    public int displaySLRonTable(JTable table)
    {
        slrTable.displayOnJTable(table);
        return slrTable.getTotalNoOfRows();
    }
    
    public boolean isSLRGrammar()
    {
        isSLR=slrTable.isParsable();
        
        return isSLR;                
    }
    
    public void setSLRErrRecv(ArrayList<String> errLst)
    {
        slrTable.errorRecv=errLst;
    }
    
    
    public boolean applyErrorVariablesToTable(int algoID,JTable jTable) throws Exception
    {        
        ParsingTableBtmUp table=null;
        switch(algoID)
        {
            case SLR : table=slrTable; break;
            case CLR : table=clrTable; break;
            case LALR : table=lalrTable; break;
            default : throw new Exception("Invalid Algorithm Choice!");
        }
        return table.applyErrorVariables(jTable);
    }
    
    
    //CLR
     
    public ItemProduction augmentStartSymbolCLR()
    {
        //add starting new start symbol
        String stSymb = grmrProdctns.get(0).left.get(0); //System.out.println("Start Symbol : "+stSymb);
                
        int totQuot=0;
        String currleft;
        for(int i=1;i<grmrProdctns.size();i++)
        {
            currleft=grmrProdctns.get(i).left.get(0);
            if(currleft.startsWith(stSymb) && currleft.length()>stSymb.length())
            {
                totQuot=Math.max(totQuot, (currleft.length()-stSymb.length()) );
            }
        }
        //System.out.println("max qoutes found : "+totQuot );
        
        for(int i=0;i<(totQuot+1);i++)
        { stSymb+="'"; }
        //System.out.println("Start Symbol : "+stSymb);
        
        ArrayList<String> left= new ArrayList<String>();
        left.add(stSymb);
        ArrayList<String> right= new ArrayList<String>();
        right.add(grmrProdctns.get(0).left.get(0));
        HashSet<String> lookahead= new HashSet<String>();
        lookahead.add("$");
        
        return ( new ItemProduction(left, right, lookahead) ); 
        
    }
    
    public mxGraphComponent getCLRmxGraph() throws Exception
    {
        return ItemProdctnSetNode.createMxGraph(clrDfaStart);
    }
    
    public void calculateCLRTable(JTable table) throws Exception //public void calculateCLRTable(JTable table, JTextArea textA) throws Exception
    {
        /*
        int nextNodelbl=0;
        ItemProdctnSetNode start,ptr,newNode,ref;
        ItemProduction currItProd,currItProdPtr,newItProd;
        String currSymbl;
        Grammar copyGram=null;
        
        HashSet<String> la= new HashSet<String>();
        
        start = new ItemProdctnSetNode(nextNodelbl++);
        start.prodSet.add(augmentStartSymbolCLR());
        
        copyGram = this.createCopy();
        String symb=start.prodSet.get(start.prodSet.size()-1).getSymbolAfterDot();
        String secnd_symb=start.prodSet.get(start.prodSet.size()-1).getSecondSymbolAfterDot();
        if(symb!=null)
        { 
            la.add("$");
            start.closureCLR(symb, copyGram, start.prodSet.size()-1, secnd_symb ,la); 
        }
        
        ptr=start;

        while(ptr != null)
        {
            for(int i=0;i<ptr.prodSet.size();i++)
            {
                currItProd=ptr.prodSet.get(i);
                if(!currItProd.hasProdBeenProcessed())
                {
                    currSymbl=currItProd.getSymbolAfterDot();
                    newNode=new ItemProdctnSetNode(nextNodelbl++);  
                    for(int j=i;j<ptr.prodSet.size();j++)
                    {
                        currItProdPtr=ptr.prodSet.get(j);
                        if(currItProdPtr.isPresent(currSymbl))
                        {
                            newItProd=currItProdPtr.createCopy();   
                            newItProd.incrementDotPositon();

                            //la.add();
                            //newItProd.addLookAhead(la);
                            newNode.prodSet.add(newItProd);  
                            currItProdPtr.setProcessed();    
                            
                            //addMoreProductions
                            //try{                                
                                copyGram = this.createCopy();
                                //System.out.println(newNode.prodSet.get(newNode.prodSet.size()-1).toString());
                               // symb=newNode.prodSet.get(newNode.prodSet.size()-1).getSymbolAfterDot();
                               // secnd_symb=newNode.prodSet.get(newNode.prodSet.size()-1).getSecondSymbolAfterDot();
                                symb=newItProd.getSymbolAfterDot();
                                secnd_symb=newItProd.getSecondSymbolAfterDot();
                                
                                
                                
                                //    System.out.println("label "+(nextNodelbl-1)+" Symbol after dot :="+symb+" and second symbol after dot :="+secnd_symb+"old lookahead :="+currItProd.getLookAhead());
                                if(symb!=null)
                                { newNode.closureCLR(symb, copyGram, newNode.prodSet.size()-1, secnd_symb, newItProd.getLookAhead()); }
                            //} catch (CloneNotSupportedException ex) 
                            //{  System.out.println("SLR - Exception Occured while cloning!");        }
                            
                            
                        }// end of if
                    }// end of j loop
                   ref=ItemProdctnSetNode.doesSetNodeExistInGraph(start,newNode);
                   if(ref==null)
                   {
                       start=ItemProdctnSetNode.addSetNodeToGraph(start,newNode);
                       ptr.addLink(newNode,currSymbl);                       
                   }
                   else{
                       ptr.addLink(ref,currSymbl);
                       nextNodelbl--;
                   }                   
               }
           }
           ptr=ptr.nextNode;
        }
        */

         int nextNodelbl=0;
        ItemProdctnSetNode start,ptr,newNode,ref;
        ItemProduction currItProd,currItProdPtr,newItProd;
        String currSymbl;
        Grammar copyGram=null;
        
        HashSet<String> la= new HashSet<String>();
        
        start = new ItemProdctnSetNode(nextNodelbl++);
        start.prodSet.add(augmentStartSymbolCLR());
        
        copyGram = this.createCopy();
        String symb=start.prodSet.get(start.prodSet.size()-1).getSymbolAfterDot();
        String secnd_symb=start.prodSet.get(start.prodSet.size()-1).getSecondSymbolAfterDot();
        if(symb!=null)
        { 
            la.add("$");
            start.closureCLR(symb, copyGram, start.prodSet.size()-1, secnd_symb ,la); 
        }
        start.simplifyItemSetNode();
        ptr=start;

        while(ptr != null)
        {
            for(int i=0;i<ptr.prodSet.size();i++)
            {
                currItProd=ptr.prodSet.get(i);
                if(!currItProd.hasProdBeenProcessed())
                {
                    currSymbl=currItProd.getSymbolAfterDot();
                    newNode=new ItemProdctnSetNode(nextNodelbl++);  
                    for(int j=i;j<ptr.prodSet.size();j++)
                    {
                        currItProdPtr=ptr.prodSet.get(j);
                        if(currItProdPtr.isPresent(currSymbl))
                        {
                            newItProd=currItProdPtr.createCopy();   
                            newItProd.incrementDotPositon();

                            
                            newNode.prodSet.add(newItProd);  
                            currItProdPtr.setProcessed();    
                            
                                                           
                                copyGram = this.createCopy();
                                
                                symb=newItProd.getSymbolAfterDot();
                                secnd_symb=newItProd.getSecondSymbolAfterDot();
                                
                                
                                
                                if(symb!=null && isNonTerminal(symb))     //yaha change hai.........................
                                { newNode.closureCLR(symb, copyGram, newNode.prodSet.size()-1, secnd_symb, newItProd.getLookAhead()); }
                                
                            
                        }// end of if
                    }// end of j loop

	newNode.simplifyItemSetNode();

                   ref=ItemProdctnSetNode.doesSetNodeExistInGraph(start,newNode);
                   if(ref==null)
                   {
                       start=ItemProdctnSetNode.addSetNodeToGraph(start,newNode);
                       ptr.addLink(newNode,currSymbl);                       
                   }
                   else{
                       ptr.addLink(ref,currSymbl);
                       nextNodelbl--;
                   }                   
            }
        }
        ptr=ptr.nextNode;
     }
              
        
        
        //display 
        //textA.setText("");
        int lines=1;
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object rowObj[] =null;
        
        //remoave all rows
        for( int i = model.getRowCount() - 1; i >= 0; i-- )
        {  model.removeRow(i); } 
        
        //add rows
        ptr=start;
        //String text="";
        while(ptr!=null)
        {
            rowObj=new Object[2];
            rowObj[0]=ptr.label;
            rowObj[1]=ptr.getProdtcString();
        
            //table
            lines=Math.max(lines, ptr.prodSet.size());
            table.setRowHeight(16 * lines  );
            model.insertRow(table.getRowCount(), rowObj);
            
            //textArea
            //text+="Set State : "+ptr.label+" - Links : "+ptr.toStringLinks()+"\n";           
            
            
            ptr=ptr.nextNode; 
        }
        //textA.setText(text);
        
        clrDfaStart=start;
        /*
        //Testing for output
        ptr=start;
        while(ptr!=null){
            System.out.println(ptr.toString());//tune pr SOP k pelhe b ptr bola tha jisse error a ra hai.
            ptr=ptr.nextNode;   //******************
        }
        
        System.out.println("Grammar After CLR");;
        System.out.println(this.toString());
        * 
        */
        
        //(2)Create Table;
        int r= nextNodelbl;
        
        int c= terminalSet.size()+ nonTerminalSet.size();
        
        //System.out.println(r+" "+c);
        
        clrTable=new ParsingTableBtmUp(r, c, terminalSet, nonTerminalSet);
        
        ptr=start;
        ItmPrdSt_InternalNode ptrlink= null;
        ParseTblElmnt newElm= null;
        while(ptr!=null)
        {
            if(ptr.links!=null)
            {
                ptrlink= ptr.links;
                while(ptrlink!=null)
                {
                    if(isNonTerminal(ptrlink.value))
                    {
                        newElm= new ParseTblElmnt(ParseTblElmnt.STATENO, ptrlink.addr.label);
                    }
                    else
                    {
                        newElm= new ParseTblElmnt(ParseTblElmnt.SHIFT, ptrlink.addr.label);
                    }
                    clrTable.addElement(ptr.label, ptrlink.value, newElm);
                    
                    ptrlink=ptrlink.nextLnk;
                }                                
            }
            
            //reduce
            for(int k=0; k<ptr.prodSet.size(); k++)
            {
                currItProd=ptr.prodSet.get(k);
                if(currItProd.isDotAtLast())
                {
                    if(currItProd.left.equals( augmentStartSymbol().left )) // Acc
                    {
                        HashSet<String> lahead= currItProd.getLookAhead();
                        newElm= new ParseTblElmnt(ParseTblElmnt.ACCEPT, 0);
                        for(Object i : lahead.toArray())
                        {
                            System.out.println("## : "+i);
                            clrTable.addElement(ptr.label, i.toString(), newElm);
                        }
                    }
                    else  //reduce
                    {
                        int prodNo=-1;
                        for(int p=0;p<grmrProdctns.size();p++)
                        {
                            if(currItProd.isEqualToProduction(grmrProdctns.get(p)))
                            { prodNo=p+1; break; }
                        }
                        HashSet<String> lahead= currItProd.getLookAhead();                        
                        newElm= new ParseTblElmnt(ParseTblElmnt.REDUCE, prodNo);
                        for(Object i : lahead.toArray())
                        {
                            //System.out.println("## : "+i);                            
                            clrTable.addElement(ptr.label, i.toString(), newElm);
                        }
                    }
                    
                }
            }
            
            //----------
            ptr=ptr.nextNode;
        }
        
        //System.out.println(clrTable.toString());
        
        //-------------
    }
    
    public int displayCLRonTable(JTable table)
    {
        clrTable.displayOnJTable(table);
        return clrTable.getTotalNoOfRows();
    }
    
    public boolean isCLRGrammar()
    {
        isCLR=clrTable.isParsable();
        
        return isCLR;                
    }
    
    public void setCLRErrRecv(ArrayList<String> errLst)
    {
        clrTable.errorRecv=errLst;
    }
    
    //LALR
    public void calculateLALRTable(JTable table) throws Exception //public void calculateLALRTable(JTable table,JTextArea textA) throws Exception
    {
        if(clrDfaStart==null)
        { 
            try{
                calculateCLRTable(null); 
            }catch(Exception e)
            { throw e; } //new Exception(e); }                        
        }
        
        //copy clr dfa
        ItemProdctnSetNode lalrDfa= ItemProdctnSetNode.createCopyOfEntireGraph(clrDfaStart);
        
        
        
        ItemProdctnSetNode ptr=lalrDfa,ptr2;
        
        //find all comman states
        ArrayList<ItemProdctnSetNode> comnSets = null;
        while(ptr!=null)
        {            
            ptr2=ptr.nextNode;
            comnSets= new ArrayList<ItemProdctnSetNode>();
            while(ptr2!=null)
            {
                if(ptr.isCoreEqual(ptr2))
                { comnSets.add(ptr2); }
                
                ptr2=ptr2.nextNode;
            }
                        
            if(!comnSets.isEmpty())
            {
                //Temp : print list
                System.out.print("For "+ptr.label+" : ");
                for(int x=0;x<comnSets.size();x++)
                { System.out.print(comnSets.get(x).label+" ");   }
                System.out.println("");    
                
                int lbl,tmp;
                for(int x=0;x<comnSets.size();x++)
                { 
                    ptr.mergeSameCoreNodeIntoIt(comnSets.get(x));
                    
                    //chk & update label of ptr
                    tmp=comnSets.get(x).label;
                    tmp=ptr.label*((int)Math.pow(10, ( (int)(Math.log10(tmp)+1) ) )) +tmp;
                    
                    
                    ptr2=lalrDfa;
                    while(ptr2!=null)
                    {
                        if(tmp==ptr2.label)
                        { tmp*=10; }
                        
                        ptr2=ptr2.nextNode;
                    }
                    //System.out.println(tmp);
                    ptr.label=tmp;
                    
                    //change links 
                    ItmPrdSt_InternalNode lnkPtr;
                    ptr2=lalrDfa;
                    while(ptr2!=null)
                    {
                        lnkPtr=ptr2.links;
                        while(lnkPtr!=null)
                        {
                            //System.out.println(lnkPtr.addr.label+" -- "+comnSets.get(x).label);
                            if(lnkPtr.addr.label==comnSets.get(x).label )
                            { 
                                System.out.println("**"); 
                                lnkPtr.addr=ptr;
                            }
                            
                            lnkPtr=lnkPtr.nextLnk;
                        }
                        
                        ptr2=ptr2.nextNode;
                    }
                    
                    //deleted redundant node
                    ptr2=lalrDfa;
                    ItemProdctnSetNode ptrPrev=ptr;
                    
                    while(ptr2!=null)
                    {
                        if(ptr2==comnSets.get(x))
                        { 
                            if(ptr2==lalrDfa)
                            { lalrDfa=ptr2.nextNode; }
                            else
                            { ptrPrev.nextNode=ptr2.nextNode; }
                                                        
                        }
                        ptrPrev=ptr2;
                        ptr2=ptr2.nextNode;
                    }
                    
                }
            }
            
            ptr=ptr.nextNode;
        }
        
        //Purposely renaming states AGAIN as 0,1,2,3,4,5...
        ItemProdctnSetNode.renameNodesInOrder(lalrDfa);
        
        lalrDfaStart=lalrDfa;
        
        //display 
        //textA.setText("");
        int lines=1;
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object rowObj[] =null;
        
        //remoave all rows
        for( int i = model.getRowCount() - 1; i >= 0; i-- )
        {  model.removeRow(i); } 
        
        //add rows
        ptr=lalrDfa;
        //String text="";
        while(ptr!=null)
        {
            rowObj=new Object[2];
            rowObj[0]=ptr.label;
            rowObj[1]=ptr.getProdtcString();
        
            //table
            lines=Math.max(lines, ptr.prodSet.size());
            table.setRowHeight(16 * lines  );
            model.insertRow(table.getRowCount(), rowObj);
            
            //textArea
            //text+="Set State : "+ptr.label+" - Links : "+ptr.toStringLinks()+"\n";           
            
            
            ptr=ptr.nextNode; 
        }
        //textA.setText(text);
        
        
        //(2)Create Table  -- COPIED FROM CLR
        ItemProduction currItProd;
        int r= ItemProdctnSetNode.getTotalNoOfNodes(lalrDfa); //nextNodelbl;        
        int c= terminalSet.size()+ nonTerminalSet.size();
        
        lalrTable=new ParsingTableBtmUp(r, c, terminalSet, nonTerminalSet);
        
        ptr=lalrDfa;
        ItmPrdSt_InternalNode ptrlink= null;
        ParseTblElmnt newElm= null;
        while(ptr!=null)
        {
            if(ptr.links!=null)
            {
                ptrlink= ptr.links;
                while(ptrlink!=null)
                {
                    if(isNonTerminal(ptrlink.value))
                    {
                        newElm= new ParseTblElmnt(ParseTblElmnt.STATENO, ptrlink.addr.label);
                    }
                    else
                    {
                        newElm= new ParseTblElmnt(ParseTblElmnt.SHIFT, ptrlink.addr.label);
                    }
                    lalrTable.addElement(ItemProdctnSetNode.getNodeIndex(lalrDfa, ptr), ptrlink.value, newElm);    //its is different from clr
                    
                    ptrlink=ptrlink.nextLnk;
                }                                
            }
            
            //reduce
            for(int k=0; k<ptr.prodSet.size(); k++)
            {
                currItProd=ptr.prodSet.get(k);
                if(currItProd.isDotAtLast())
                {
                    if(currItProd.left.equals( augmentStartSymbol().left )) // Acc
                    {
                        HashSet<String> lahead= currItProd.getLookAhead();
                        newElm= new ParseTblElmnt(ParseTblElmnt.ACCEPT, 0);
                        for(Object i : lahead.toArray())
                        {
                            System.out.println("## : "+i);
                            lalrTable.addElement(ItemProdctnSetNode.getNodeIndex(lalrDfa, ptr), i.toString(), newElm);     //different to clr
                        }
                    }
                    else  //reduce
                    {
                        int prodNo=-1;
                        for(int p=0;p<grmrProdctns.size();p++)
                        {
                            if(currItProd.isEqualToProduction(grmrProdctns.get(p)))
                            { prodNo=p+1; break; }
                        }
                        HashSet<String> lahead= currItProd.getLookAhead();                        
                        newElm= new ParseTblElmnt(ParseTblElmnt.REDUCE, prodNo);
                        for(Object i : lahead.toArray())
                        {
                            //System.out.println("## : "+i);                            
                            lalrTable.addElement(ItemProdctnSetNode.getNodeIndex(lalrDfa, ptr), i.toString(), newElm);  //diffenerent from clr
                        }
                    }
                    
                }
            }
            
            //----------
            ptr=ptr.nextNode;
        }
        
    }
    
    public mxGraphComponent getLALRmxGraph() throws Exception
    {
        return ItemProdctnSetNode.createMxGraph(lalrDfaStart);
    }
    
    public int displayLALRonTable(JTable table)
    {
        lalrTable.displayOnJTable(table);
        return lalrTable.getTotalNoOfRows();
    }
    
    public boolean isLALRGrammar()
    {
        isLALR=lalrTable.isParsable();
        
        return isLALR;                
    }
       
    public void setLALRErrRecv(ArrayList<String> errLst)
    {
        lalrTable.errorRecv=errLst;
    }
    
    //public String topDwnParsingAlgo(int algo,ArrayList<Token> inputBuf,JTable jTable) throws Exception
    public ParsingReturn topDwnParsingAlgo(int algo,ArrayList<Token> inputBuf,JTable jTable) throws Exception
    {
        ParsingTableBtmUp table=null;
        switch(algo)
        {
            case 0 : table=slrTable; break;
            case 1 : table=clrTable; break;
            case 2 : table=lalrTable; break;
            default : throw new Exception("Invalid Algorithm Choice!");
        }
        
        
        boolean disp=true;
        String retStr=null;
        DefaultTableModel model=null;
        Object jTbRowObj[]=null;
        
        if(jTable==null)
        { disp=false; }
        else
        { 
            model=(DefaultTableModel) jTable.getModel(); 
            
            //remoave all rows
            for( int i = model.getRowCount() - 1; i >= 0; i-- )
            {  model.removeRow(i); }            
            
        }
        
        //ParseTree        
        Stack<ParseTreeNode> treeStack = new Stack<ParseTreeNode>();
        ParseTreeNode start=null,newNode=null;
        //---------
        
        //Initialize stack;
        Stack<String> stack = new Stack<String>();
        stack.push("0");        
        
        //add $ inputBuff
        inputBuf.add(new Token("$", "", 0));
        
        //Algo Variables
        int ip=0;
        String s,s1,A;
        Token a;
        boolean accept=false;
        ParseTblElmnt tblElm;
        Production curProd;
          
        /*
        //show initial stage
        if(disp)
        {
                    jTbRowObj = new Object[3];
                    jTbRowObj[0]=stackToString(stack); 
                    jTbRowObj[1]= inputBfToString(ip, inputBuf);
                    jTbRowObj[2]="";
            
                    model.insertRow(jTable.getRowCount(), jTbRowObj);                        
        }
        */
        
        //Algo
        //(1)
        while(!accept)
        {
            if(disp)
            {
                    jTbRowObj = new Object[3];
                    jTbRowObj[0]=stackToString(stack); 
                    jTbRowObj[1]= inputBfToString(ip, inputBuf);                    
            }
            
            //(2) & (3)
            s=stack.peek();
            a=inputBuf.get(ip);
            
            //(4)
            try{
                tblElm=table.getElement(Integer.parseInt(s), a.toUseStr);
            }catch(Exception e)
            { 
                if(disp)
                {
                    /*
                    jTbRowObj = new Object[3];
                    jTbRowObj[0]=stackToString(stack); 
                    jTbRowObj[1]= inputBfToString(ip, inputBuf);
                    */
                    jTbRowObj[2]="Terminated";
            
                    model.insertRow(jTable.getRowCount(), jTbRowObj);                        
                }
                
                return (new ParsingReturn("Syntax Error!", null) );    //"Syntax Error!"; 
            }
            
            if(tblElm.isShift())   //SHIFT
            {
                //(5)
                stack.push(a.toUseStr);
                s1=String.valueOf(tblElm.retValue());
                stack.push(s1);
                
                //(6)
                ip++;
                
                //output shift
                if(disp)
                {
                    /*
                    jTbRowObj = new Object[3];
                    jTbRowObj[0]=stackToString(stack); 
                    jTbRowObj[1]= inputBfToString(ip, inputBuf);
                    */
                    jTbRowObj[2]="Shift "+tblElm.retValue();
            
                    model.insertRow(jTable.getRowCount(), jTbRowObj);                        
                }
                
                //ParseTree        
                newNode=new ParseTreeNode(a.toUseStr);
                start=ParseTreeNode.addNodeToBeg(start, newNode);
                treeStack.push(newNode);
                //---------
            }
            else if(tblElm.isReduce())    //REDUCE
            {
                curProd=grmrProdctns.get(tblElm.retValue()-1);
                
                //(9)
                if(!curProd.isEpsilonProduction())
                {
                    for(int i=0;i<(curProd.right.size()*2);i++)
                    { stack.pop(); }
                }
                else
                {
                    //ParseTree        
                    newNode=new ParseTreeNode(curProd.right.get(0));    //Epsilon
                    start=ParseTreeNode.addNodeToBeg(start, newNode);
                    treeStack.push(newNode);
                    //---------
                }
                
                
                //(10)
                s1=stack.peek();
                
                //(11)
                A=curProd.left.get(0);
                stack.push(A);
                stack.push( String.valueOf( table.getElement(Integer.parseInt(s1), A).retValue() ) );
                
                //ParseTree        
                newNode=new ParseTreeNode(curProd.left.get(0));
                start=ParseTreeNode.addNodeToBeg(start, newNode);
                for(int l=curProd.right.size()-1;l>=0;l--)
                { newNode.addLinkToNBeg(treeStack.pop()); }
                treeStack.push(newNode);
                //---------
                
                //output reduce
                if(disp)
                {
                    /*
                    jTbRowObj = new Object[3];
                    jTbRowObj[0]=stackToString(stack); 
                    jTbRowObj[1]= inputBfToString(ip, inputBuf);
                    */
                    jTbRowObj[2]="Reduce by "+curProd.toString();
            
                    model.insertRow(jTable.getRowCount(), jTbRowObj);                        
                }
            }
            else if(tblElm.isAccept())   //ACCEPT
            {    
                
                //ParseTree                        
                start=treeStack.pop();
                //---------
                
                //output Accept
                if(disp)
                {
                    /*
                    jTbRowObj = new Object[3];
                    jTbRowObj[0]=stackToString(stack); 
                    jTbRowObj[1]= inputBfToString(ip, inputBuf);
                    */
                    jTbRowObj[2]="Accept";
            
                    model.insertRow(jTable.getRowCount(), jTbRowObj);                        
                }
                
                /*
                //Delete
                ParseTreeNode ptr=start;
                while(ptr!=null)
                { 
                    System.out.println(ptr.toString()); 
                    ptr=ptr.nextNd;                
                }                
                //------------delete
                */
                return (new ParsingReturn("Accepted!", start) );  //"Accepted!";
            }
            else
            {
                //output
                if(disp)
                {
                    /*
                    jTbRowObj = new Object[3];
                    jTbRowObj[0]=stackToString(stack); 
                    jTbRowObj[1]= inputBfToString(ip, inputBuf);
                    */
                    jTbRowObj[2]="Error";
            
                    model.insertRow(jTable.getRowCount(), jTbRowObj);                        
                }
                
                try{
                    String errStr = table.getErrorString(tblElm.retValue());
                    if(errStr!=null)
                    { return (new ParsingReturn(errStr, null) ); } //errStr; }
                    else
                    { throw new Exception(); }
                }catch(Exception e)
                { return (new ParsingReturn("LR Parsing Error! No Error Recovery Found!", null) ); } //"LR Parsing Error! No Error Recovery Found!"; }
            }
            
        }
        
        return (new ParsingReturn("NOT Accepted!", null) ); //"NOT Accepted!";
        
    }
    
    public void setParsingChoice(int choice) throws Exception
    {
        if(choice<-1 || choice>2)
        {
            throw new Exception("Invalid Parsing Choice!");
        }
        else
        {
            parseChoice=choice;
        }
    }
    
    //-------------------
    
    
    public String toString()
    {
        String ret="";
        ret=retGrammarString();
        
        return ret;
    }
  
    //-----------------------------------
    /*
    class CellPos 
    {
        int row;
        int col;
        
        public String toString()
        {
            return "["+row+","+col+"]";
        }
    }
    */
    
}

class CellPos 
    {
        int row;
        int col;
        
        public String toString()
        {
            return "["+row+","+col+"]";
        }
    }
