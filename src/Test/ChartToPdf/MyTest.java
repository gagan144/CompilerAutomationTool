package Test.ChartToPdf;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import commanLib.StatisticData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

public class MyTest 
{
    public static void main(String[] args) 
    {
        try{
            StatisticData stData = StatisticData.readFromFile("C:\\Users\\Gagandeep\\Documents\\COMPILER\\newStatFile.stc");            

            generatePDG(new File("E:\\ChartToPDFTest.pdf"), stData.retLocVsLexTimeChartPanel().getChart());
        
        }catch(Exception e)
        { e.printStackTrace();}
        
        
    }
    
    
    public static void generatePDG(File filePath,JFreeChart chart) throws Exception
    {
        OutputStream file=null;
        com.lowagie.text.Document document = null;
        try
        {        
            file = new FileOutputStream(filePath);
            
            //Set page size and margins
            document = new com.lowagie.text.Document(PageSize.A4, 50, 20, 10, 20); 
            PdfWriter.getInstance(document, file);                        
           
            
            //set pdf file basic info            
            document.addTitle("Compiler Report");
            document.addAuthor("CAT Statistic Studio");
            //document.addSubject("Analysis Report of "+ref.r.clientName+" ("+ref.r.cmpName+")");
            document.addCreator("Compiler Automation Tool (CAT)");      //Application
            
            
            com.lowagie.text.Font authFont = FontFactory.getFont(FontFactory.HELVETICA, 9, com.lowagie.text.Font.ITALIC);
            com.lowagie.text.Font footrFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
            com.lowagie.text.Font normFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            com.lowagie.text.Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            com.lowagie.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, com.lowagie.text.Font.UNDERLINE);
            
            
            //set Footer
            String foot="Compiler Automation Tool (CAT)\n"+                    
                    "Development Team : Gagandeep Singh, Hargeet Kaur, Amarpreet Singh, Harpreet Kaur\n"+
                    "© 2013 | singh.gagan144@gmail.com | 9717568636";
            
            Paragraph ftr=new Paragraph(foot, footrFont);
            
            HeaderFooter footer = new HeaderFooter( ftr ,false);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.setFooter(footer);                   
            
            
            //Open Document
            document.open();
            
            //Set Logo            
            //Image logo = Image.getInstance("/commanLib/images/pdfImages/CATLogoPDF.png");            
            //Image logo = Image.getInstance("E:/CATLogoPDF.png");     
            
            /*
            ImageIcon imgIcon = new ImageIcon(getClass().getResource("/commanLib/images/pdfImages/CATLogoPDF.png"));            
            Image logo = Image.getInstance(imgIcon.getImage(),null);            
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo); 
            */
            
            //Write Document Content
            
            //set headings         
            Calendar cal=Calendar.getInstance();
            int month= cal.get(Calendar.MONTH);
            int year= cal.get(Calendar.YEAR);
            int day= cal.get(Calendar.DAY_OF_MONTH);
            
            Paragraph pra=new Paragraph("Compiler Report\n"+                    
                    "Date : "+day+"/"+(month+1)+"/"+year+"\n\n", authFont);
            
            pra.setAlignment(Element.ALIGN_CENTER);
            document.add(pra);
            
                        
            //4. Statistic Report
            pra=new Paragraph("Statistic Report                                                                                                                ", headFont);
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);
            
            
            int width=700, height=500; 
            //int width=1000, height=800; 
            
            
            //-------------------Poor Image   (WORKING)            
                               
                // create a png passed as an array of bytes to the Image
                // giving low image quality            
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ChartUtilities.writeChartAsPNG(bos, chart, width, height);
                byte[] img = bos.toByteArray();
                Image pngImg = Image.getInstance(img);
                //dpi                
                pngImg.scalePercent(72f / 110 * 100);                
                document.add(new Paragraph(""));
                document.add(new Paragraph("Example1: Poor quality image"));
                pngImg.setAlignment(Element.ALIGN_CENTER);
                document.add(pngImg);
             //-----------------------------------
            
                /*
             //-------------------High qualify------------
                document.open();
                // add chart to document
                // giving high image quality, but without transparency                
                ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
                PdfWriter pdfWriter = PdfWriter.getInstance(document, baosPDF);
                PdfContentByte cb = pdfWriter.getDirectContent();
                PdfTemplate tp = cb.createTemplate(width, height);
                Graphics2D g2 = tp.createGraphics(width, height, new DefaultFontMapper());
                Rectangle2D r2D = new Rectangle2D.Double(0.0D, 0.0D, width, height);
                chart.draw(g2, r2D);
                g2.dispose();
                Image chartImage = Image.getInstance(tp);
                document.add(new Paragraph(""));
                document.add(new Paragraph("Example2: High quality image"));
                document.add(chartImage);
                
             //-------------------------------------------
                */
        }
        finally
        {
            //End of writing
            try{
                document.close();
            }catch(Exception e){}
            
            try{
                file.close();
            }catch(Exception e){}
        }    
    }
        
}
