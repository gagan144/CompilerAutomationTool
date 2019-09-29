package commanLib.Security;

import java.util.ArrayList;
import java.util.HashSet;

public class Transposition 
{
    private String key="";
    private ArrayList<String>  matrix = new ArrayList<String>();

    public Transposition(String key) 
    {        
        this.key=key.toUpperCase();
    }
    
    public String getKey()
    { return key; }
    
    public void displayMatrix()
    {
        System.out.println(key);
        for(int i=0;i<matrix.size();i++)
        {
            System.out.println(matrix.get(i));
        }
    }
        
    
    public String encrypt(String text)
    {
        String cText="";
        int keyLength=key.length();        
        
        
        //create matrix
        String tmp="";
        int j=0;
        for(int i=0;i<text.length();i++)
        {
            if(j==keyLength-1)
            { 
                tmp+=text.charAt(i);
                matrix.add(tmp.toString());                
                tmp="";
                j=0;                 
            }            
            else if(i==text.length()-1)
            {
                tmp+=text.charAt(i);
                char c='a';
                for(int x=j+1;x<keyLength;x++)
                {  tmp+=c++; }
                matrix.add(tmp.toString());
            }
            else
            {
                tmp+=text.charAt(i);
                j++;
            }
        }
        
        //Encypt
        HashSet<Integer> doneIdx = new HashSet<Integer>();
        for(int c='A';c<='Z';c++)
        {
            for(int k=0;k<key.length();k++)
            {
                if(key.charAt(k)==c && !doneIdx.contains(k))
                {
                    for(int i=0;i<matrix.size();i++)
                    {
                        cText+=matrix.get(i).charAt(k);
                    }
                    doneIdx.add(k);
                }
            }
        }        
        
        return cText;
    }
    
    
    public String decrypt(String cText)
    {
        String txt="";
        
        int row=cText.length()/key.length();
        int col=key.length();
        char charArr[][]=new char[row][col];
        
        ArrayList<Integer> colOrder = new ArrayList<Integer>();
        for(int c='A';c<='Z';c++)
        {   
            for(int k=0;k<key.length();k++)
            {
                if(key.charAt(k)==c && !colOrder.contains(k))
                {                    
                    colOrder.add(k);
                }
            }
        }
        //System.out.println(colOrder);
        
            int i=0;
            for(int c=0;c<colOrder.size();c++)
            {
                for(int r=0;r<row;r++)
                {
                    charArr[r][colOrder.get(c)]=cText.charAt(i++);
                }
            }
        
        
            for(int r=0;r<row;r++)
            {
                for(int c=0;c<col;c++)
                {
                    txt+=charArr[r][c];
                }
            }
            
        return txt;
    }
    
}
