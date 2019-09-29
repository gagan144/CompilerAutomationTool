package commanLib.Security;

import commanLib.SystemProperties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class FileDESCipher
{
    private final String KEY;

    public FileDESCipher(String KEY) 
    {
        this.KEY = KEY;
    }   
    
    /*
    public static void main(String[] args) 
    {
    try {
        String key = "myencryptedpass123";

        //FileInputStream fis = new FileInputStream("File_to_encrypt.txt");
        //FileOutputStream fos = new FileOutputStream("Encrypted_file.txt");
        FileInputStream fis = new FileInputStream("E:\\new\\CATRegist.creg");
        FileOutputStream fos = new FileOutputStream("E:\\new\\Enrypted_CATRegist.creg");
        encrypt(key, fis, fos);

        //FileInputStream fis2 = new FileInputStream("Encrypted_file.txt");
        //FileOutputStream fos2 = new FileOutputStream("File_to_decrypt.txt");
        FileInputStream fis2 = new FileInputStream("E:\\new\\Enrypted_CATRegist.creg");
        FileOutputStream fos2 = new FileOutputStream("E:\\new\\Decrypted_CATRegist.creg");
        decrypt(key, fis2, fos2);
    } catch (Throwable e) {
        e.printStackTrace();
    }
    }
*/
    public void encrypt(String sourceFilePath, String encryptFilePath) throws Throwable 
    {
        FileInputStream fis = new FileInputStream(sourceFilePath);
        FileOutputStream fos = new FileOutputStream(encryptFilePath);
        encryptOrDecrypt(KEY, Cipher.ENCRYPT_MODE, fis, fos);
    }
    
    public void encrypt(InputStream sourceInpStrm, OutputStream encyptOutpStrm) throws Throwable 
    {  encryptOrDecrypt(KEY, Cipher.ENCRYPT_MODE, sourceInpStrm, encyptOutpStrm);   }

    public void decrypt(String encryptFilePath, String decryptFilePath) throws Throwable 
    {
        FileInputStream fis = new FileInputStream(encryptFilePath);
        FileOutputStream fos = new FileOutputStream(decryptFilePath);
        encryptOrDecrypt(KEY, Cipher.DECRYPT_MODE, fis, fos);
    }
    
    public void decrypt(InputStream encryptInpStrm, OutputStream decryptOutpStrm) throws Throwable 
    {  encryptOrDecrypt(KEY, Cipher.DECRYPT_MODE, encryptInpStrm, decryptOutpStrm);  }
    
    private void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) throws Throwable 
    {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES"); 
    
        if (mode == Cipher.ENCRYPT_MODE) 
        {
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            doCopy(cis, os);
        }
        else if (mode == Cipher.DECRYPT_MODE) 
        {
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            CipherOutputStream cos = new CipherOutputStream(os, cipher);
            doCopy(is, cos);
        }
    }
    
    private void doCopy(InputStream is, OutputStream os) throws IOException 
    {
        byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) 
        {  os.write(bytes, 0, numBytes);   }
        
        os.flush();
        os.close();
        is.close();
    }
    
    public String getKey()
    { return KEY; }
    
    public static String autoGenerateKey()
    {
        SystemProperties sysProp = new SystemProperties();    
        
        String str=sysProp.OS_NAME+ sysProp.OS_VER+sysProp.SYS_TYPE+sysProp.COMPUTER_NAME;  
        //    System.out.println(str);  
    
        //Remove White spaces
        str=str.replaceAll("\\s","");
        //    System.out.println(str);
    
        //Remove Anything that is not character
        str=str.replaceAll("\\W","");
        //    System.out.println(str);
    
        //Apply transposition
        String transKey="";
        for(int i=0;i<sysProp.CPU_SPEED.length();i++)
        {
            transKey+=(char)((int)sysProp.CPU_SPEED.charAt(i) - 48 + 65);
            //System.out.println( (char)((int)sysProp.CPU_SPEED.charAt(i) - 48 + 65) );
        }
        transKey+="CAT";
        //    System.out.println("TransKey : "+transKey);
    
        Transposition trans = new Transposition(transKey);
        
        String key=trans.encrypt(str);    
        return key;
    }    
    
    public static String encryptKey(final String key)
    {
        String eKey="";
        String tmp;
        for(int i=0;i<key.length();i++)
        {
            tmp=String.valueOf((int)key.charAt(i));
            if(tmp.length()<3)  //padding
            {
                tmp="0"+tmp.substring(0);
            }
            eKey+=tmp;
        }        
        return eKey;        
    }
    
    public static String decryptKey(final String eKey) throws Exception
    {
        String key="";
        String tmp="";
        for(int i=0;i<eKey.length();i=i+3)
        {
            tmp=""+eKey.charAt(i)+eKey.charAt(i+1)+eKey.charAt(i+2);            
            key+=(char)Integer.parseInt(tmp);
        }        
        return key;        
    }
    
}