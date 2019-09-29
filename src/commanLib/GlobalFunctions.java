package commanLib;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Stack;

public class GlobalFunctions 
{

    
    public static String insertConcatOpr(String e)
    {
        String tmp="";
        char c1,c2;
        int i;
        
        for(i=0;i<e.length()-1;i++)
        {            
            c1=e.charAt(i);
            c2=e.charAt(i+1);            
            
            if(c1>='a' && c1<='z') 
            {
                if( (c2>='a' && c2<='z') || c2=='(' )
                {
                    tmp+=c1+".";
                }   
                else
                { tmp+=c1; }    
            }
            else if( (c1=='*' || c1=='+' || c1==')') && (c2=='(' || (c2>='a' && c2<='z'))  )
            {
                  tmp+=c1+".";
            }
            else if(c1==')' && c2=='(')
            { tmp+=c1+"."; }
            else
            { tmp+=c1; }    
           
        }
        tmp+=e.charAt(i);
        
        return tmp;
    }
    //-------------------------
    
    public static String infixToPostfixReg(String r) 
    {
        String y="";
        char c,tmp;
        
        Character carr[];
        
        try
        {
            Stack<Character> stack = new Stack<Character>();
            
            for(int i=0;i<r.length();i++)
            {
               c=r.charAt(i);
               //System.out.print("Scanning : "+c+"   ");
               
               if( c=='+' || c=='*' || (c>='a' && c<='z') )
               {   y+=c;    }
               else if(c=='(')
               {
                   stack.push(c);
                   y+=c;
               }
               else if (c=='|')
               {
                   /*                   
                   if(!stack.empty())
                   {  
                       tmp=stack.pop().charValue();   
                       if(tmp!='(')
                       { y+=tmp; }
                   }
                   stack.push(c);
                   * 
                   */
                   
                   if(!stack.empty())
                   {
                       tmp=stack.peek();
                       if(tmp=='.' || tmp=='|')
                       {
                           y+=stack.pop().charValue();
                       }                       
                   }
                   stack.push(c);
               }
               else if(c=='.')
               {
                   /*
                   if(!stack.empty())
                   {        
                       if(stack.peek()=='(')
                       { stack.pop(); }    
                       
                       if(stack.peek()=='|')
                       {                           
                           stack.push(c);
                       }else
                       {
                           y+=stack.pop().charValue(); //y+=tmp;
                           stack.push(c);
                       }
                   }else
                   {
                       stack.push(c);
                   }
                   * 
                   */
                   
                   if(!stack.empty())
                   {
                       tmp=stack.peek();
                       if(tmp=='.')
                       {
                           y+=stack.pop().charValue();
                           
                       }
                       stack.push(c);
                   }
                   else
                   { stack.push(c); }
                   
               }
               else if(c==')')
               {
                   /*
                   if(!stack.empty())
                   {  
                       tmp=stack.pop().charValue();                         
                       if(tmp!='(')
                       { y+=tmp; }
                   }
                   y+=c;
                   * 
                   */
                   while(!stack.empty())
                   {
                       tmp=stack.pop();
                       if(tmp!='(')
                       {
                           y+=tmp;
                       }
                       else
                       {
                           break;
                       }
                   }
                   y+=c;
                   
               }
               
               /*
               if(!stack.empty())
               { System.out.print("Top Element : "+stack.peek().charValue()+"   "); }
               //System.out.println("Y : "+y);
               * 
               */
            }
            
            while(!stack.empty())
            {  y+=stack.pop().charValue();   }
                        
            
        }catch(Exception e)
        { System.out.println("infixToPostfixReg() | Stack Error : "+e); }
        
        return(y);
    }
    
    //-------------------------
    
    public static parseNode generateParseTree(String rexpr)
    {
        
        parseNode start=null;
        
        //(1) insert concat opr
        rexpr=GlobalFunctions.insertConcatOpr(rexpr);
        
        //(2) infix to postfix
        rexpr=GlobalFunctions.infixToPostfixReg(rexpr);
        
        //(3) scanning
        try
        {
        Stack<parseNode> stack=new Stack<parseNode>();
        
        char c;
        int i,rLbl=0;        
        parseNode rnode,pnode;
        
        
        
        for(i=0;i<rexpr.length();i++)
        {
            c=rexpr.charAt(i);
            //System.out.println("Scanning : "+c);
            
            if(c>='a' && c<='z')    //Input
            {
                rnode=new parseNode();                
                rnode.lb="r"+(++rLbl);
                pnode=new parseNode();
                pnode.lb=String.valueOf(c);                
                rnode.m=pnode;                
                
                stack.push(rnode);
            }
            else if(c=='(')          //left bracket
            {
                rnode=new parseNode();
                rnode.lb="rx";
                pnode=new parseNode();
                pnode.lb=String.valueOf(c);                
                rnode.m=pnode;                
                
                stack.push(rnode);
            }
            else if(c=='|' ||c=='.')   // | or .
            {
                rnode=new parseNode();
                rnode.lb="r"+(++rLbl);
                
                if(c=='|')
                {
                   pnode=new parseNode();
                   pnode.lb=String.valueOf(c);                
                   rnode.m=pnode;                
                }
                
                rnode.r=stack.pop();
                rnode.l=stack.pop();
                
                stack.push(rnode);
            }
            else if(c==')')      //right braket
            {
                rnode=new parseNode();
                rnode.lb="r"+(++rLbl);
                pnode=new parseNode();
                pnode.lb=String.valueOf(c);                
                
                rnode.r=pnode;                
                rnode.m=stack.pop();
                rnode.l=stack.pop().m;
                
                stack.push(rnode);
            }
            else if(c=='*' || c=='+')   // * or + closure
            {
                rnode=new parseNode();
                rnode.lb="r"+(++rLbl);
                pnode=new parseNode();
                pnode.lb=String.valueOf(c); 
                
                rnode.m=pnode;     //rnode.r=pnode;
                rnode.l=stack.pop();
                
                stack.push(rnode);
            }
               
        }//end of for
        
        
        if(i==rexpr.length() && !stack.empty())
        {
            start=stack.pop();
        }
                       
        
        //ptr=start;
        //System.out.println("ptr : |"+ptr.lb+"|"+ptr.l+"|"+ptr.m+"|"+ptr.r+"|");
        
        }catch(Exception e)
        {  System.out.println("generateParseTree() | Error in Stack : "+e); }
        
        return(start);
                
    }
    //------------------------------
    
    public static GraphNode generateNFA(String rexpr)
    {
        GraphNode NfaStart=null,nfa1=null,nfa2=null,newNFA=null;  
        
        //(1) insert concat opr
        rexpr=GlobalFunctions.insertConcatOpr(rexpr);
        
        //(2) infix to postfix
        rexpr=GlobalFunctions.infixToPostfixReg(rexpr);
        
        //(3) scanning
        try
        {
            Stack<GraphNode> stack = new Stack<GraphNode>();
            
            int i;
            char c;            
            
            for(i=0;i<rexpr.length();i++)
            {
                c=rexpr.charAt(i);
                
                if(c>='a' && c<='z')    //Input
                {
                    newNFA=new GraphNode();
                    newNFA.lb="0";                
                    newNFA=newNFA.addStateToLast(newNFA, "1");
                    newNFA.addLink(newNFA, "1", String.valueOf(c));
                    
                    stack.push(newNFA);
                }
                else if(c=='|')   //Union
                {
                    nfa2=stack.pop();
                    nfa1=stack.pop();
                    newNFA=NfaFunctions.unionNFA(nfa1, nfa2);
                    
                    stack.push(newNFA);
                }
                else if(c=='*' || c=='+')  //Closure
                {
                    nfa1=stack.pop();
                    newNFA=NfaFunctions.closure(nfa1);
                    
                    stack.push(newNFA);
                }
                else if(c=='.')   //Concate
                {
                    nfa2=stack.pop();
                    nfa1=stack.pop();
                    newNFA=NfaFunctions.concatNFA(nfa1, nfa2);
                    
                    stack.push(newNFA);
                }
                else if(c=='(')
                {
                    newNFA=new GraphNode();
                    newNFA.lb="(";
                    
                    stack.push(newNFA);
                } 
                else if(c==')')
                {
                    newNFA=stack.pop();
                    stack.pop();
                    
                    stack.push(newNFA);
                }
                
            }//end of for
            
            NfaStart=stack.pop();
            
        }catch(Exception e)
        {  System.out.println("generateNFA() | Error in Stack : "+e); }
        
        //Rename states
        GraphNode ptr = null;
        ptr = NfaStart;  
        NfaStart.state='i';
        int lbCntr = 0;
        while (ptr != null) 
        {
           ptr.lb = String.valueOf(lbCntr++);
           
           if(ptr.nextSt==null)
           { ptr.state='F'; }
           
           //System.out.println(ptr.lb+"|"+ptr.state+"|"+ptr.nextSt+"|"+ptr.links);
           ptr = ptr.nextSt;
        }
        
        return(NfaStart);
    }
    
    //--------------------------------------
    
    
    
}
