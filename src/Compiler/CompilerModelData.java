package Compiler;

import commanLib.GraphNode;
import commanLib.ModelData;
import commanLib.PriorityMap;
import editor.IntrFrm_GenProperty;
import java.awt.Color;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import commanLib.Token;

public class CompilerModelData extends commanLib.ModelData
{
    ArrayList<Token> tokenStream=new ArrayList<Token>();
    SymbolTable sysmbolTable = new SymbolTable(tokenStream);
    ErrorTable errorTable = new ErrorTable();
    ModelData mdlDatRef=null;

    //Constructors
    public CompilerModelData(ModelData d) 
    {        
        super(d);
        mdlDatRef=d;
    }

    //--------my additional methods
    public String[] getExtensions()
    {           
        return extension;                
    }
    
    public void loadGenProp(Dialog_properties fRef)
    {
        PriorityMap prior = new PriorityMap();
        
        fRef.textF_prgmLang.setText(progLang);
        fRef.textF_mdl.setText(mdlVersion);
        fRef.textF_auth.setText(author);
        
        String ext="";
        for(String i : extension)
        { 
            ext+=i;
            if(!i.equals(extension[extension.length-1]))
            {ext+=","; }
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
    
    /*
    private GraphNode getNextGraph(int currGrIdx)
    {
        switch(priority[currGrIdx])
        {
            case 1 : 
        }
    }
    * 
    */
    
    public void lexicalAnaylser(String code,CATCompiler cmpRef)
    {
        
        // PHASE 1 - Remove Comments
        cmpRef.setCompileMesage("Phase 1 - Removing comments and creating code blocks...",0);
        // (a) remove comments
         StringBuffer filterCode = new StringBuffer(code.length());
         if(comments!=null)
         {
         GraphNode ptr=comments; 
         
         char c;                 
         //boolean graphActive=true;
         //int totLinesInCmt=0;
         int cmStartIdx=-1;
         
         for(int i=0;i<code.length();i++)
         {
             c=code.charAt(i); 
             
             ptr=ptr.getNextState(c, false, mdlDatRef);
             
             if(ptr==null)  //Not accepted
             {
                 if(cmStartIdx!=-1)
                 {
                         for(int j=cmStartIdx;j<i;j++)
                         { filterCode.append(code.charAt(j));    } 
                         cmStartIdx=-1;
                 }
                 
                 filterCode.append(c);
                 ptr=comments;
             }
             else    //Accepted
             {
                 if(cmStartIdx==-1)
                 {
                         cmStartIdx=i;
                 }
                 
                 if(c=='\n')
                 { filterCode.append(c); }
                 
                 if(ptr.state=='F')
                 {
                     filterCode.append(" ");
                     ptr=comments;
                     cmStartIdx=-1;
                 }
             }
                          
         }//for         
         }
         else
         {
             filterCode=new StringBuffer(code);
         }
         //System.out.println("Filtered Code : \n"+filterCode+"\n");
         
        
        //(b) Tokenize
         //StringTokenizer strTkn = new StringTokenizer(filterCode.toString()," \t\n\r\f", true);
         StringTokenizer strTkn = new StringTokenizer(filterCode.toString(),"\n\r", true);
         cmpRef.setCompileMesage("Done!",1);
         /*
         int ctr=0;
         while(strTkn.hasMoreTokens())
         {
            System.out.println("Token "+(++ctr)+" : "+strTkn.nextToken());
         }
         */
        
        
       //PHASE 2 - GENERATE TOKENS                 
         cmpRef.setCompileMesage("Phase 2 - Tokenizing...",0);
         
         String codeBlock="";         
         int lineNo=1;
         while(strTkn.hasMoreTokens())
         {
             codeBlock=strTkn.nextToken();
             if(codeBlock.charAt(0)=='\n')
             { 
                 lineNo++;
             }
             else //if( !Character.isWhitespace(codeBlock.charAt(0)) )   //Not a white space
             {             
                 // analyse code Block
                 //System.out.println("CodeBlock at "+(lineNo)+" : "+codeBlock);
                 
                 /*
                 if(codeBlock.contains("<#@COMMENT$"))
                 //if(codeBlock.startsWith("<#@COMMENT$"))
                 {
                     //System.out.println("To add : "+Integer.parseInt(codeBlock.substring(12, codeBlock.length())));
                     //lineNo+=Integer.parseInt(codeBlock.substring(12, codeBlock.length()));
                     continue;
                 }*/
                 
                 int startIdx=0,lastIdx=-1,prIdx=-1;
                 String newToken="";                 
                 
                 while(startIdx<codeBlock.length())
                 {               
                     
                     //ignore starting white space
                     for(int i=startIdx;i<codeBlock.length();i++)
                     {
                         if(Character.isWhitespace(codeBlock.charAt(i)))
                         { 
                             startIdx++;                              
                         }
                         else
                         { break; }
                     }
                     
                     if(startIdx>=codeBlock.length())
                     {
                         break; //while
                     }
                     
                     //System.out.println("checking sub blk : "+codeBlock.substring(startIdx));
                     prIdx=1;
                     while(prIdx<14)
                     {
                         lastIdx=simulateWithPriority( priority[prIdx], codeBlock.substring(startIdx));
                         if(lastIdx==-1) //Not match
                         {
                             prIdx++;
                             continue;    //proceed to next garph
                         }
                         
                         break;   //MAtch found break while(prIdx<14)
                         
                     }//while(pr<14)
                     
                     if(lastIdx!=-1)
                     {
                         newToken=codeBlock.substring(startIdx, (startIdx+lastIdx)+1 );
                         if(prIdx==1)
                         {
                             if(isKeyword(newToken))
                             {  sysmbolTable.insert(newToken, priority[2], lineNo);  }
                             else
                             {  sysmbolTable.insert(newToken, priority[prIdx], lineNo); }
                         }
                         else
                         {  sysmbolTable.insert(newToken, priority[prIdx], lineNo);  //sysmbolTable.insert(newToken, "id", lineNo);
                         }
                         startIdx+=lastIdx+1;
                     }
                     else
                     {                         
                         cmpRef.setCompileMesage("Lexical Error! Line "+lineNo+" : Unidentified Token",-1);
                         errorTable.addError("Lexical", lineNo, startIdx, "Panic Mode");
                         break; //break while(startIdx<codeBlock.length())
                     }
                     
                     
                     /*
                     //lastIdx=simulateDFA(idGraph, codeBlock.substring(startIdx));
                     lastIdx=simulateWithPriority(2, codeBlock.substring(startIdx));
                     if(lastIdx!=-1)
                     { 
                         newToken=codeBlock.substring(startIdx, (startIdx+lastIdx)+1 );
                         //System.out.println("Token (id) : "+newToken); 
                         sysmbolTable.insert(newToken, "id", lineNo);
                         startIdx+=lastIdx+1;                         
                     }
                     else  //??
                     {
                         startIdx++;    //inc if not matched
                     }
                     */
                     
                 }//while(startIdx<codeBlock.length())
                 
             }
         }
         cmpRef.setCompileMesage("Done!",1);
         
         /*
        // PHASE 2 - GENERATE TOKENS
        GraphNode curGraph=null,reachedptr=null;
        int lineCtr=1;
        //char c;
        
        int startIdx=-1,lastFinalIdx=-1;
        
        //set currGraph in a loop
        curGraph=idGraph;
        ptr=idGraph;
        
        startIdx=0;
        int i=0;        
        while(i<code.length())
        {
            c=code.charAt(i);   System.out.println("Analysing "+c+" ... ");
            
            if(c=='\n')
            {
                lineCtr++;
            }            
            
            //else if(Character.isWhitespace(c))
            //{continue; }
            
            
            //Traverse through current graph              
              ptr=ptr.getNextState(c, true, mdlDatRef); 
              
              if(ptr==null)  //Graph terminated
              {
                  //chk lastFinalIdx
                  if(lastFinalIdx==-1)  //ERROR
                  {
                      System.out.println("ERROR: line "+lineCtr+" Invalid Token : "+code.substring(startIdx, i+1));
                      startIdx=i+1;   // ????
                      
                      //reset Graph
                      curGraph=idGraph; 
                      ptr=idGraph;                      
                  }
                  if(lastFinalIdx>=startIdx)
                  {
                      //generate a token
                      System.out.println("TOKEN found : ["+startIdx+","+lastFinalIdx+"] : "+code.substring(startIdx, lastFinalIdx+1));
                      
                      //reset Graph
                      curGraph=idGraph; 
                      ptr=idGraph;
                      
                      //reset Idx pointer
                      startIdx=i; lastFinalIdx=-1;
                      
                      //backtract
                      i--;
                      
                  }
                  
                  //move to next graph                  
                  
                  //reset
              }
              else
              {
                  //note if it reached a final state
                  if(ptr.state=='F')
                  {
                      lastFinalIdx=i;
                  }
              }
            
            
            i++;
        }
        
        if(lastFinalIdx>=startIdx)
        {
          //generate a token
          System.out.println("Token found : ["+startIdx+","+lastFinalIdx+"] : "+code.substring(startIdx, lastFinalIdx+1));                      
        }
       */ 
        
        
    }
    
    public int simulateWithPriority(int pr,String code)
    {
        int lastIdx=-1;
        switch(pr)
        {
            case 2 : {
                         //System.out.println("Identifier");                
                         if(idGraph!=null)
                         {
                             lastIdx=simulateDFA(idGraph, code);
                         }
                     }break;
            case 1 : {
                         //System.out.println("Keywords");                
                     }break;
            case 3 : {
                         //System.out.println("Integer");                
                         if(intgrGraph!=null)
                         {
                             lastIdx=simulateDFA(intgrGraph, code);
                         }
                     }break;
            case 4 : {
                         //System.out.println("Character");                
                         if(chGraph!=null)
                         {
                             lastIdx=simulateDFA(chGraph, code);
                         }
                     }break;
            case 5 : {
                         //System.out.println("Floating");                
                         if(fltGraph!=null)
                         {
                             lastIdx=simulateDFA(fltGraph, code);
                         }
                     }break;
            case 6 : {
                         //System.out.println("String");    
                         if(strngGraph!=null)
                         {
                             lastIdx=simulateDFA(strngGraph, code);
                         }
                     }break;
            case 7 : {
                         //System.out.println("Punctuator");   
                         if(punctuators!=null)
                         { 
                             lastIdx=simulatePunctuationMatch(code);
                         }
                     }break;
            case 8 : {
                         //System.out.println("Arithmetic");                                         
                         if(operatr[0].t!=null)
                         {
                             lastIdx=simulateTransGraph(operatr[0].t, code);
                         }
                     }break;
            case 9 : {
                         //System.out.println("Relational");                
                         if(operatr[1].t!=null)
                         {
                             lastIdx=simulateTransGraph(operatr[1].t, code);
                         }
                     }break;
            case 10 : {
                         //System.out.println("Logical");          
                         if(operatr[2].t!=null)
                         {
                             lastIdx=simulateTransGraph(operatr[2].t, code);
                         }
                     }break;
            case 11 : {
                         //System.out.println("Bitwise");          
                         if(operatr[3].t!=null)
                         {
                             lastIdx=simulateTransGraph(operatr[3].t, code);
                         }
                     }break;
            case 12 : {
                         //System.out.println("Assignment");                
                         if(operatr[4].t!=null)
                         {
                             lastIdx=simulateTransGraph(operatr[4].t, code);
                         }
                     }break;
            case 13 : {
                         //System.out.println("Others");                
                         if(otherOpr!=null)
                         {
                             lastIdx=simulateStringMatch(otherOpr, code);
                         }
                     }break;
        }
        
        return lastIdx;
    }
    
    
    private int simulateDFA(GraphNode dfaRef,String codeBlock)
    {
        int lastIdx=-1;
        
        GraphNode reachedptr=null,ptr=null;        
                                
        ptr=dfaRef;                
        char c;
        int i=0;        
        while(i<codeBlock.length())
        {
            c=codeBlock.charAt(i);   //System.out.println("Analysing "+c+" ... ");
                    
            //Traverse through current graph              
              ptr=ptr.getNextState(c, true, mdlDatRef); 
              
              if(ptr==null)  //Graph terminated
              {
                  /*
                  //chk lastIdx
                  if(lastIdx==-1)  //ERROR
                  {                      
                      //startIdx=i+1;   // ????                      
                      //reset Graph                      
                      return lastIdx;
                  }
                  else if(lastIdx>=0)
                  {
                      //generate a token
                      //System.out.println("TOKEN found : ["+startIdx+","+lastFinalIdx+"] : "+code.substring(startIdx, lastFinalIdx+1));
                      
                      //reset Graph
                      //curGraph=idGraph; 
                      //ptr=idGraph;
                      
                      //reset Idx pointer
                      
                      
                      //backtract
                      //i--;
                      
                      System.out.println("Token found. returning lastIdx="+lastIdx);
                  }
                  */
                  break;
                  
              }
              else
              {
                  //note if it reached a final state
                  if(ptr.state=='F')
                  {
                      lastIdx=i;
                  }
              }
                        
            i++;
        }//while
        
        /*
        if(lastIdx>=0)
        {
          //generate a token
          //System.out.println("Token found : ["+0+","+lastFinalIdx+"] : "+code.substring(startIdx, lastFinalIdx+1));                                  
        }
        * 
        */
        
        return lastIdx;
    }
    
    
    private int simulateTransGraph(GraphNode transRef,String codeBlock)
    {
        int lastIdx=-1;
        
        GraphNode reachedptr=null,ptr=null;        
                                
        ptr=transRef;                
        char c;
        int i=0;        
        while(i<codeBlock.length())
        {
            c=codeBlock.charAt(i);   //System.out.println("Analysing "+c+" ... ");
                    
            //Traverse through current graph              
              ptr=ptr.getNextState(c, false, null); 
              
              if(ptr==null)  //Graph terminated
              {
                  break;                  
              }
              else
              {
                  //note if it reached a final state
                  if(ptr.state=='F')
                  {
                      lastIdx=i;
                  }
                  else if(ptr.state=='*')
                  {
                      lastIdx=i-1;
                  }
                  
              }
                        
            i++;
        }//while
                
        return lastIdx;
    }
    
    private int simulateStringMatch(String strList[],String codeBlock)
    {
        int lastIdx=-1;
        boolean flag=true;
                
        for(int i=0;i<strList.length;i++)
        {
            //match patter
            flag=true;
            for(int k=0;k<strList[i].length();k++)
            {
                if(codeBlock.charAt(k)!=strList[i].charAt(k))
                {
                    flag=false;
                    break;
                }
                
            }
            
            if(flag)
            {
                lastIdx=strList[i].length()-1;
                return lastIdx;
            }
                       
        }
        
        return lastIdx;
    }
    
    private int simulatePunctuationMatch(String codeBlock)
    {
        int lastIdx=-1;
        boolean flag=true;
        int i,j=0;
                
        for(i=0;i<punctuators.length;i++)
        {
            for(j=0;j<2;j++)
            {                   
                if(punctuators[i][j].equals(""))
                { continue; }
                
                //System.out.print("Matching against... punctuator["+i+"]["+j+"] : "+punctuators[i][j]+" ");
                flag=true;
                //System.out.println("Matching codeB : '"+codeBlock+"'");
                for(int k=0;k<punctuators[i][j].length();k++)
                {
                    if(punctuators[i][j].charAt(k)==codeBlock.charAt(k))
                    {
                        flag=true;
                    }
                    else
                    {
                        flag=false;  
                        break;
                    }
                }
                
                if(flag)
                {
                    //System.out.println("FOUND"+punctuators[i][j].length());
                    lastIdx=punctuators[i][j].length()-1;
                    return lastIdx;
                }
            }
            //System.out.println("");
            
        }              
        
        
        return lastIdx;
    }
    
    public void resetTokenStream()
    {
        tokenStream.clear();
    }
    
    public void displayTokenStream(JTextPane txtPn)
    {
        Style idStyle = txtPn.addStyle("identifier", null);
        StyleConstants.setForeground(idStyle, new Color(0,153,0));
        
        Style keywrdStyle = txtPn.addStyle("Keyword", null);
        StyleConstants.setForeground(keywrdStyle, new Color(0,0,233));
        
        Style strStyle = txtPn.addStyle("String/char", null);
        StyleConstants.setForeground(strStyle, new Color(222,122,0));
        
        Token currToken=null;
        //String str="";
        txtPn.setText("");
        StyledDocument doc = txtPn.getStyledDocument();               
        
        try
        {   
            for(int i=0;i<tokenStream.size();i++)
            {
               currToken=tokenStream.get(i);
               if(currToken.token.startsWith("id"))
               {
                   doc.insertString(doc.getLength(), currToken.token+" ", idStyle );                   
               }
               else if(currToken.token.startsWith("keyword"))
               {
                   doc.insertString(doc.getLength(), currToken.lexeme+" ", keywrdStyle );                   
               }
               else if(currToken.token.startsWith("char") || currToken.token.startsWith("str") )
               {
                   doc.insertString(doc.getLength(), currToken.lexeme+" ", strStyle );                   
               }
               else
               {
                   doc.insertString(doc.getLength(), currToken.lexeme+" ", null );                   
               }
               
               /*
               if(currToken.lexeme.equals("\n"))
               { System.out.println("*"); }
               */
               
            }            
            
        }
        catch(Exception e) 
        { System.out.println(e); }
        
        /*
        String str="";
        txtPn.setText("");
        for(int i=0;i<tokenStream.size();i++)
        {
            str+=tokenStream.get(i)+" ";
        }
        txtPn.setText(str);
        * 
        */
        
        
    }
    
    
    /*
    
    public void simulateDFA(String code)
    {
        GraphNode ptr=idGraph,reachedSt=null;
        char c;
        
        for(int i=0;i<code.length();i++)
        {
            c=code.charAt(i);
            ptr=ptr.getNextState(c,true,mdlDatRef);
            
            reachedSt=ptr;
            if(ptr==null)
            {                                
                break;
            }            
        }
        
        if(reachedSt!=null)
        {
            System.out.println("Final State : "+reachedSt.lb);
            if(reachedSt.state=='F')
            { System.out.println("Accepted"); }
            else
            { System.out.println("NOT Accepted"); }        
            
        }
        else
        {
            System.out.println("Terminated");
        }
        
        
    }
    
    */  
}
