package Test;

public class CreateMAtrixTest 
{
    public static void main(String[] args) 
    {
        int noOfElements=1;
        int r,c;
        
        do{
            System.out.print(noOfElements+" : ");    
        
        //------------------------
        double sqrt=Math.sqrt(noOfElements); //System.out.println(sqrt);
        
        if( (sqrt-Math.floor(sqrt))==0 )
        { r=c=(int) sqrt; }
        else
        {
            c=(int) sqrt+1;
            r=(int) Math.round(sqrt);            
        }
        
        //-------------------
        System.out.println(r+"x"+c);
        
        noOfElements++;
        }while(noOfElements<=25);
        
    }    
}
