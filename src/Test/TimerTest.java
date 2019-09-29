package Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

public class TimerTest 
{
    public static void main(String[] args) throws InterruptedException 
    {
        
        long startTimeMs = System.currentTimeMillis( ); System.out.println("Start : "+startTimeMs);
        
        //for(int i=0;i<10;i++)
        {
            Thread.sleep(1500);
        }
        long stopTimeMs = System.currentTimeMillis( );
        
        long totalTimeMs=(stopTimeMs-startTimeMs);
    
        
        String str= String.format("%d sec, %d millisec", 
    TimeUnit.MILLISECONDS.toSeconds(totalTimeMs),
    TimeUnit.MILLISECONDS.toMillis(totalTimeMs) - 
    TimeUnit.MINUTES.toMillis(TimeUnit.MILLISECONDS.toSeconds(totalTimeMs))
);
        
                
        
        System.out.println("Total time : "+ str);
        
    }
    
}
