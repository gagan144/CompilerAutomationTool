package commanLib.Registration;

import commanLib.CatFileExtensions;
import commanLib.Security.FileDESCipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;
import javax.crypto.Cipher;
import javax.swing.JOptionPane;

public class Registration implements Serializable
{
    //private static final transient String fileName="CATRegist.creg";
    private static final transient String REG_FILE_NAME="CATRegist."+CatFileExtensions.REG_FILE_EXT;
    private static final transient String TMP_REG_NAME="tempReg.tmp";
    private static final transient String winPath=System.getenv("windir");
       
    //public final int regID;
    public final String regID;
    public final String firstName;
    public final String lastName;
    public final String company;
    public final String email;
    public final String desig;
    public final long phoneNo;
    public final String address;    
    public final String state;
    public final String country;
    public final int zip;
    
    public final String regDate;

    public Registration(String firstName, String lastName, String company, String email, String desig, long phoneNo, String address, String state, String country, int zip) 
    {
        //Random rand= new Random();
        //this.regID=10000000+rand.nextInt(89999999);
        this.regID=FileDESCipher.encryptKey(FileDESCipher.autoGenerateKey()); //this.regID=FileDESCipher.autoGenerateKey();
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.email = email;
        this.desig=desig;
        this.phoneNo = phoneNo;
        this.address = address;        
        this.state = state;
        this.country = country;
        this.zip = zip;
        
        this.regDate=setDate();
    }
    
    private String setDate()
    {
        Calendar cal= Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);                
        return ( day+"/"+(month + 1)+"/"+year );
    }
           
    public static Registration readRegFile() throws Exception
    {  
        //Return Values
        // -1 : File Not Found
        //  0 : Corrupted or obselete file
        
        //Decrypt from orginal to tmp file
        File tmpFile = new File(TMP_REG_NAME);
        if(tmpFile.exists())
        {tmpFile.delete();}
        //FileDESCipher cipher = new FileDESCipher("gagandeepsingh");    ///<<<<<<<<---------------- DELETE CHANGE
        FileDESCipher cipher = new FileDESCipher(FileDESCipher.autoGenerateKey());
        try{
            cipher.decrypt(REG_FILE_NAME, TMP_REG_NAME);
            
            //hide file
            //Process p = Runtime.getRuntime().exec("attrib +H "+TMP_REG_NAME);
        }
        catch(FileNotFoundException e)  //CATReg.creg not found
        { 
           //file not found
            //e.printStackTrace();
           throw new Exception("-1");           
        }
        catch(Throwable t)
        {
            //Corrupted file
            throw new Exception("0");
        }
        
        //read tmp file
        FileInputStream fin=null;           
        Registration regFile;
        try
        {
            //fin = new FileInputStream(winPath+"\\SPAconfig.cfg");
            fin = new FileInputStream(TMP_REG_NAME); //fin = new FileInputStream(REG_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fin);
            regFile=(Registration)ois.readObject();              
            
        }catch(FileNotFoundException e)  //tmpReg.creg not found
        { 
           //file not found
           throw new Exception("0"); //throw new Exception("-1");           
        }
        catch(Exception e)
        {  
            //Corrupted file
            throw new Exception("0");
        }
        finally
        {
            //delete tmp file
             try{ 
                 fin.close();
                 new File(TMP_REG_NAME).delete();                 
             }
             catch(Exception e)
             {}//System.out.println("Error Closing File"); }
        }
        
        return(regFile);        
    }
    
    public void writeRegFile() throws Throwable //throws Exception
    {           
        FileOutputStream fout = new FileOutputStream(TMP_REG_NAME);  //FileOutputStream fout = new FileOutputStream(REG_FILE_NAME);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(this);
        fout.close();
        //hide file
        //Process p = Runtime.getRuntime().exec("attrib +H "+TMP_REG_NAME);
        
        //Encrypt        
        //FileDESCipher cipher = new FileDESCipher("gagandeepsingh");    ///<<<<<<<<---------------- DELETE CHANGE
        FileDESCipher cipher = new FileDESCipher(FileDESCipher.autoGenerateKey());
        cipher.encrypt(TMP_REG_NAME,REG_FILE_NAME);
        
        //delete tmp file
        new File(TMP_REG_NAME).delete();        
    } 
    
    public static void deleteRegFile() throws Exception
    {
        File file = new File(REG_FILE_NAME);
        file.delete();
    }
    
    public String toString()
    {
        String ret;        
        ret="Registration Hash Id : "+regID+"\n"+
            "First Name : "+firstName+"\n"+
            "Last Name : "+lastName+"\n"+
            "Company : "+company+"\n"+
            "Email : "+email+"\n"+
            "Designation : "+desig+"\n"+
            "Phone No : "+phoneNo+"\n"+
            "Address : "+address+"\n"+            
            "State : "+state+"\n"+
            "Country : "+country+"\n"+
            "Zip Code : "+zip+"\n"+
            "Date : "+regDate;
        
        return ret;
    }
}
