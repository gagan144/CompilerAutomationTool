package Test;

public class TraversingTest 
{
    public static void main(String args[])
    {
            String code="gagan  235 <=";
            int lineNumber=1;
            int startIdx,ptrIdx,endIdx;
            
            int i=0;
            startIdx=ptrIdx=endIdx=i;
            while(i<code.length())
            {
                
                if(Character.isWhitespace(code.charAt(i)))
                {
                    
                    for(int j=startIdx;j<=endIdx;j++)
                    {  System.out.print(code.charAt(j)); }
                    
                    System.out.println();
                    startIdx=endIdx=i+1;
                }else
                {
                    endIdx=i;
                    //ptrIdx++;
                }
                
                i++;
            }
            
            
            
    }
    
}
