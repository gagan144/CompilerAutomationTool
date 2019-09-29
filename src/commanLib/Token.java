package commanLib;

import java.util.ArrayList;

public class Token
{
        final public String lexeme;//="";
        final public String token;//="";
        public ArrayList<Integer> lineNo= new ArrayList<Integer>();
        final public String toUseStr;

        public Token(String lx,String tkn,int ln) 
        {
            lexeme=lx;
            token=tkn;
            lineNo.add(ln);
            
            if(token.startsWith("id"))     //only id as filter
            { toUseStr="id"; }
            else
            { toUseStr=lexeme; }
        }
}    