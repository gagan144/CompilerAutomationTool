package commanLib;

import java.awt.Color;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class OperatorGrammar 
{
    private ArrayList<Production> grmrProdctns = new ArrayList<Production>();
    
    //private HashMap<String,HashSet<String>> allFirst = new HashMap<String, HashSet<String>>();
    
    public boolean addGrammar(String grammarText)
    {           
        grmrProdctns.clear();        
        
        if(grammarText.equals(""))
        {
            return false;
        }
        
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
                
    private boolean isNonTerminal(String str)
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
                            doc.insertString(doc.getLength(), String.valueOf(str), nonTermStyle ); System.out.println(str);
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
    
    
    
    public String toString()
    {
        String ret="";
        ret=retGrammarString();
        
        return ret;
    }
    
}
