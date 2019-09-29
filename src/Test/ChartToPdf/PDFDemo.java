package Test.ChartToPdf;
    /*
    * Created on Jan 29, 2005
    * @author psupawa@gmail.com
    *
    * Create servlet mapping in web.xml
    * <servlet>
    *   <servlet-name>pdfdemo</servlet-name>
    *   <servlet-class>org.jfree.chart.demo.PDFDemo</servlet-class>
    *   <load-on-startup>1</load-on-startup>
    * </servlet>
    * <servlet-mapping>
    *   <servlet-name>pdfdemo</servlet-name>
    *   <url-pattern>/chartpdf</url-pattern>
    * </servlet-mapping>
    *
    * To test with internet browser
    * http://localhost:8080/jfc0921/chartpdf
    *
    */

    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    //import java.awt.image.BufferedImage ;
    import java.awt.Graphics2D;
    import java.awt.geom.Rectangle2D;
    import java.util.Date;
    import javax.servlet.ServletException;
    import javax.servlet.ServletOutputStream;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;

    import com.lowagie.text.*;
    import com.lowagie.text.pdf.*;
    //import com.keypoint.PngEncoder;

    import org.jfree.chart.ChartUtilities;
    import org.jfree.chart.JFreeChart;

    /**
    * Example creating chart in pdf format
    */
    public class PDFDemo extends HttpServlet {

       public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {   
           
            int width = 400; // chart witdh
            int height = 300; // chart height
           
            // create chart, you may get from attribute
            // or get it from your own chart generator
            
            JFreeChart chart = null; //Pie3DChart.getChart();
           
            ByteArrayOutputStream baosPDF;
            baosPDF = new ByteArrayOutputStream();
            PdfWriter pdfWriter = null;
            Document doc = new Document();
            try
            {
                pdfWriter = PdfWriter.getInstance(doc, baosPDF);
                doc.addAuthor("Phuwarin S.");
                doc.addCreationDate();
                doc.addProducer();
                doc.addCreator(getClass().getName());
                doc.addTitle("JFreeChart Demo");
                doc.addKeywords("pdf, itext, Java, open source, http");
                doc.setPageSize(PageSize.A4);
                doc.open();
               
                // add content
                doc.add(new Paragraph("Created by a class named: " + getClass().getName()));
                doc.add(new Paragraph("Created on " + new Date()));
                String strServerInfo = getServletContext().getServerInfo();
                if(strServerInfo != null)
                    doc.add(new Paragraph("Servlet engine: " + strServerInfo));
               
                // create a png passed as an array of bytes to the Image
                // giving low image quality
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ChartUtilities.writeChartAsPNG(bos, chart, width, height);
                byte[] img = bos.toByteArray();
                Image pngImg = Image.getInstance(img);
                doc.add(new Paragraph(""));
                doc.add(new Paragraph("Example1: Poor quality image"));
                doc.add(pngImg);
               
                /*
                // same result of above
                BufferedImage chartImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = (Graphics2D)chartImage.getGraphics();
                chart.draw(g2d, new Rectangle2D.Double(0D, 0D, width, height));
                PngEncoder encoder = new PngEncoder(chartImage, false, 0, 9);
                Image pngImage = Image.getInstance(encoder.pngEncode());
                doc.add(pngImage);
                */
               
                // add chart to document
                // giving high image quality, but without transparency
                PdfContentByte cb = pdfWriter.getDirectContent();
                PdfTemplate tp = cb.createTemplate(width, height);
                Graphics2D g2 = tp.createGraphics(width, height, new DefaultFontMapper());
                Rectangle2D r2D = new Rectangle2D.Double(0.0D, 0.0D, width, height);
                chart.draw(g2, r2D);
                g2.dispose();
                Image chartImage = Image.getInstance(tp);
                doc.add(new Paragraph(""));
                doc.add(new Paragraph("Example2: High quality image"));
                doc.add(chartImage);
               
                // add chart to document at a specific position
                // giving high image quality, but without transparency
                /*
                PdfContentByte cb = pdfWriter.getDirectContent();
                PdfTemplate tp = cb.createTemplate(width, height);
                Graphics2D g2 = tp.createGraphics(width, height, new DefaultFontMapper());
                Rectangle2D r2D = new Rectangle2D.Double(0.0D, 0.0D, width, height);
                chart.draw(g2, r2D);
                g2.dispose();
                cb.addTemplate(tp,36,130); // position from left 36, from bottom 130
                */
               
            }
            catch(DocumentException dex)
            {
                baosPDF.reset();
            }
            finally
            {
                if(doc != null)
                    doc.close();
                if(pdfWriter != null)
                    pdfWriter.close();
            }
           
            StringBuffer sbFilename = new StringBuffer();
            sbFilename.append("jfreechart_");
            sbFilename.append(System.currentTimeMillis());
            sbFilename.append(".pdf");
           
            StringBuffer sbContentDispValue = new StringBuffer();
            sbContentDispValue.append("inline");
            sbContentDispValue.append("; filename=");
            sbContentDispValue.append(sbFilename);
            response.setHeader("Content-disposition", sbContentDispValue.toString());
           
            response.setContentType("application/pdf");
            response.setContentLength(baosPDF.size());
            ServletOutputStream sos = response.getOutputStream();
            baosPDF.writeTo(sos);
            sos.flush();
       }
       
    }
