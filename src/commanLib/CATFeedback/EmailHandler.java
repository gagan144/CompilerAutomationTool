package commanLib.CATFeedback;

import com.sun.mail.smtp.SMTPSSLTransport;
import com.sun.mail.smtp.SMTPTransport;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailHandler 
{  
    //public static final String CAT_EMAIL_ID="gagandeep.taurus91@gmail.com";    
    //private final String CAT_EMAIL_PASS = "chaos199157chaos";
    
    public static final String CAT_EMAIL_ID="singh.gagan144@gmail.com";    
    private final String CAT_EMAIL_PASS = "xxxxx";
    
    private final String TO = CAT_EMAIL_ID;
    private final String FROM;   
    private final String PASS;
    private final String SUBJECT;
    private final String MESSAGE_BODY;
    
    //Exceptions
    public static final int AUTH_EXCPTN=0;
    public static final int CONNECT_EXCPTN=1;
    

    public EmailHandler(String FROM,String PASS,String SUBJECT,String MESSAGE_BODY) 
    {
        if(FROM==null)
        { 
            this.FROM=TO; 
            this.PASS=CAT_EMAIL_PASS;
        }
        else
        { 
            this.FROM = FROM; 
            this.PASS=PASS;
        }        
        
        this.SUBJECT = SUBJECT;
        
        this.MESSAGE_BODY=MESSAGE_BODY;
        
        
    }
    
    public void sendMail() throws Exception
     {
         //Get the session object
         final int PORT=465;
         Properties props = new Properties();
         props.put("mail.smtp.host", "smtp.gmail.com");
         props.put("mail.smtp.socketFactory.port", "465");
         props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.port", String.valueOf(PORT)); //props.put("mail.smtp.port", "465");
         props.put("mail.smtp.starttls.enable","true");
         props.put("mail.smtp.debug", "true");
 
         Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() 
                 {
                     return new PasswordAuthentication(FROM,PASS);      
                 }
         });
 
         Transport transport = session.getTransport("smtps");  //new         
         
         //compose message
         try 
         {
             MimeMessage message = new MimeMessage(session);
             message.setFrom(new InternetAddress(FROM));
             message.addRecipient(Message.RecipientType.TO,new InternetAddress(TO));
             message.setSubject(SUBJECT);
             message.setText(MESSAGE_BODY);   
 
             /*//send message
             Transport.send(message);
             */
             
             transport.connect("smtp.gmail.com", PORT,FROM, PASS);//transport.connect();             
             //message.saveChanges();                         
             transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
             
             
             //System.out.println("message sent successfully");   
         }         
         catch(AuthenticationFailedException ae)
         { throw new Exception(String.valueOf(AUTH_EXCPTN)); }
         catch (MessagingException me) 
         {  throw new Exception(String.valueOf(CONNECT_EXCPTN));    }         
         catch(Exception e)
         { throw e; }
        
         finally{
             //System.out.print("Closing...");
             transport.close();
             props.clear();             
             //System.out.println("Done!");
         }
     }
    
    public String toString()
    {
        String ret="TO : "+TO+"\n"+
                   "FROM : "+FROM+"\n"+
                   "Pass : "+PASS+"\n"+
                   "SUBJECT : "+SUBJECT+"\n"+
                   "MESSAGE :\n"+MESSAGE_BODY;
        return ret;        
    }
}