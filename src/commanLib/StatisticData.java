package commanLib;

import com.ctc.wstx.sr.ElemAttrs;
import com.lowagie.text.Cell;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.Watermark;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.BubbleXYItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.HighLowItemLabelGenerator;
import org.jfree.chart.labels.IntervalXYItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.SymbolicXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AreaRendererEndType;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.GradientPaintTransformer;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.VerticalAlignment;

public class StatisticData implements Serializable
{
    //Model Properties
    public final String progLang;
    public final String mdlVersion;
    public final String author;
    public final String extension[];
    public final String descptn;
    public final String modelPath;
    
    //System properties
    public final SystemProperties sysProp;
    
    //Performance Data
    private ArrayList<PerformRecord> data= new ArrayList<PerformRecord>();
    private HashMap<Integer,HashSet<Integer>> index = new HashMap<Integer, HashSet<Integer>>();   //key =loc | value=pos in array list;
    
    
    public static final String COMPILE_TIME="Total Compile Time";
    public static final String LEX_TIME="Lexical Time";
    public static final String ALL="All";

    public StatisticData(ModelData modelData,String modelPath,SystemProperties sysProp) 
    {
        if(modelData!=null)
        {
            progLang=modelData.progLang;
            mdlVersion=modelData.mdlVersion;
            author=modelData.author;
            extension=modelData.extension;
            descptn=modelData.descptn;   
            this.modelPath=modelPath;
        }else
        {
            progLang="null";
            mdlVersion="null";
            author="null";
            extension=null;
            descptn="null";  
            this.modelPath=null;
        }
        
        if(sysProp!=null)
        { this.sysProp=sysProp; }
        else
        { this.sysProp=null; }
        
        
        ArrayList<PerformRecord> data= new ArrayList<PerformRecord>();
        HashMap<Integer,HashSet<Integer>> index = new HashMap<Integer, HashSet<Integer>>(); 
    }
    
    
        
    public void addNewRecord(PerformRecord record)
    {        
        data.add(record);        
        
        //search for same loc record
        if(index.containsKey(record.loc))    //record alreay present
        {
            HashSet<Integer> set = index.get(record.loc);
            set.add(data.size()-1);
            //index.put(record.loc,set);
        }
        else       //record not present; create new record
        { 
            HashSet<Integer> newSet = new HashSet<Integer>();
            newSet.add(data.size()-1);
            index.put(record.loc,newSet);
        }
        
    }
    
    public boolean isEmpty()
    {
        return data.isEmpty();
    }
    
    public void resetData()
    {
        data= new ArrayList<PerformRecord>();
        index=new HashMap<Integer, HashSet<Integer>>();
    }
    
    public int getTotalNoOfRecords()
    {
        return data.size();
    }
    
    public ChartPanel retLocVsTotTimeChartPanel() throws Exception
    {
        if(data.isEmpty())
        { throw new Exception("No statistic record found!"); }
        
        //(A) Create XYDataset
         //(a) calculate values
         HashMap<Integer,Long> values = new HashMap<Integer, Long>();
         for (Map.Entry<Integer, HashSet<Integer>> entry : index.entrySet()) 
         {
             int keyLoc = entry.getKey();
             HashSet<Integer> valSet = entry.getValue();       
             PerformRecord curRcd=null;
             long avg=0;
             for(Iterator<Integer> i=valSet.iterator();i.hasNext(); )
             {   
                 curRcd=data.get(i.next());
                 avg+=curRcd.totalTime;
             }
             avg/=valSet.size();
             
             //System.out.println("For "+keyLoc+" avg : "+avg);
             values.put(keyLoc, avg);
         }
         //System.out.println(values);
         
         //(b) creat XYDataSet
         final XYSeries series = new XYSeries("Average Compile Time for individual LOC");
         for (Map.Entry<Integer,Long> entry : values.entrySet()) 
         {
             series.add(entry.getKey(), entry.getValue());
         }
       
         final XYSeriesCollection dataset = new XYSeriesCollection();
         dataset.addSeries(series);            
        //---------------------------------------------
        
        //(B) Create chart        
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Total Compile Time Vs LOC",      // chart title
            "LOC",                      // x axis label
            "Total Compile Time (ms)",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );
        
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);  
        TextTitle title = chart.getTitle();
        title.setPaint(new Color(0,152,218));
        title.setFont(new Font("Arial", 1, 18));
                
        //chart.getXYPlot().set     <<<<<<<<<<<<<<<<---------------------- set domain axis value to inetger

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
     
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(228,244,253));                 //background
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(new Color(0,152,218));                 //grid
        plot.setRangeGridlinePaint(new Color(0,152,218));
        plot.setOutlinePaint(new Color(62,64,149));         //outline
        //plot.getRenderer().setSeriesPaint(0, Color.ORANGE);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, false);   
        
        renderer.setItemLabelGenerator(new StandardXYItemLabelGenerator("({1} , {2})"));
        renderer.setItemLabelsVisible(true);
        final ItemLabelPosition p = new ItemLabelPosition(
            ItemLabelAnchor.OUTSIDE10, TextAnchor.BOTTOM_CENTER, TextAnchor.CENTER, 0
        );                
        renderer.setPositiveItemLabelPosition(p);        
        renderer.setItemLabelPaint(new Color(4,123,180));
        
        plot.setRenderer(renderer);
        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        //set as integer
        NumberAxis domX = (NumberAxis) plot.getDomainAxis();
        domX.setStandardTickUnits(domX.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.    
        
        //---------------------------------------------
        
        
        //(C) create ChartPanel
        final ChartPanel chartPanel = new ChartPanel(chart,false,true,true,true,true);
        chartPanel.setBorder(new LineBorder(new Color(180,180,180)));
        
        return chartPanel;
        
        //return null;
    }
    
    public ChartPanel retLocVsTotTimeChartPanelForLoc(int loc) throws Exception
    {
        if(data==null)
        { throw new Exception("No statistic record found!"); }
        
        //(A) Create XYDataset
        HashSet<Integer> posSet = index.get(loc);
        double values[][] = new double[1][posSet.size()];
        PerformRecord curRcd=null;
        int c=0,avg=0;
        for(Iterator<Integer> i=posSet.iterator();i.hasNext(); )
        {
            curRcd=data.get(i.next());
            values[0][c] = curRcd.totalTime;
            avg+=curRcd.totalTime;
            c++;
        }
        avg/=c;
        
        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
            "Series ",
            "Test Case ",
          values
        );   
        //---------------------------------------------
        
        //(B) Create chart
        final JFreeChart chart = ChartFactory.createBarChart(
        "Total Compile Time for LOC = "+loc,       // chart title
            "Test Case",               // domain axis label
            "Total Time (ms)",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // the plot orientation
            false,                    // include legend
            true,
            false
        );

        chart.setBackgroundPaint(Color.white);        
        TextTitle title = chart.getTitle();
        title.setPaint(new Color(0,152,218));
        title.setFont(new Font("Arial", 1, 18));
                
        //add statement
        chart.addSubtitle(new TextTitle("( Average Compile Time = "+avg+" ms )",
         new Font("Dialog", Font.ITALIC, 14), Color.black,
        RectangleEdge.BOTTOM, HorizontalAlignment.CENTER,
        VerticalAlignment.BOTTOM, RectangleInsets.ZERO_INSETS));
        
         //chart.setBorderPaint(Color.white);
        //final XYPlot plots = chart.getXYPlot();
        //plots.setBackgroundPaint(Color.white);
       
      

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setNoDataMessage("NO DATA!");
        plot.setBackgroundPaint(new Color(228,244,253)); //plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(new Color(0,152,218));
        plot.setOutlinePaint(new Color(62,64,149));  

        
        /*
        final CategoryItemRenderer renderer = new CustomRenderer(
            new Paint[] {new Color(4,123,180)}
        );
        */
        
        //final CategoryItemRenderer renderer = new BarRenderer();
        final BarRenderer renderer = new BarRenderer();
        renderer.setBarPainter(new StandardBarPainter());        
        renderer.setPaint(new Color(4,123,180));        
        renderer.setShadowVisible(false);        
        
        
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setItemLabelsVisible(true);
        final ItemLabelPosition p = new ItemLabelPosition(
            ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER, TextAnchor.CENTER, 0
        );        
        /*final ItemLabelPosition p = new ItemLabelPosition(
            ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER, 45.0
        );
        */
        renderer.setPositiveItemLabelPosition(p);        
        renderer.setItemLabelPaint(new Color(4,123,180));
        plot.setRenderer(renderer);

        // change the margin at the top of the range axis...
        final ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLowerMargin(0.15);
        rangeAxis.setUpperMargin(0.15);

        //---------------------------------------------
        
        
        //(C) create ChartPanel
        final ChartPanel chartPanel = new ChartPanel(chart,false,true,true,true,true);
        chartPanel.setBorder(new LineBorder(new Color(180,180,180)));
        
        return chartPanel;
    }
    
    
    public ChartPanel retLocVsLexTimeChartPanel() throws Exception
    {
        if(data==null)
        { throw new Exception("No statistic record found!"); }
        
        //(A) Create XYDataset
         //(a) calculate values
         HashMap<Integer,Long> values = new HashMap<Integer, Long>();
         for (Map.Entry<Integer, HashSet<Integer>> entry : index.entrySet()) 
         {
             int keyLoc = entry.getKey();
             HashSet<Integer> valSet = entry.getValue();       
             PerformRecord curRcd=null;
             long avg=0;
             for(Iterator<Integer> i=valSet.iterator();i.hasNext(); )
             {   
                 curRcd=data.get(i.next());
                 avg+=curRcd.lexiclTime;
             }
             avg/=valSet.size();
             
             //System.out.println("For "+keyLoc+" avg : "+avg);
             values.put(keyLoc, avg);
         }
         //System.out.println(values);
         
         //(b) creat XYDataSet
         final XYSeries series = new XYSeries("Average Compile Time for individual LOC");
         for (Map.Entry<Integer,Long> entry : values.entrySet()) 
         {
             series.add(entry.getKey(), entry.getValue());
         }
       
         final XYSeriesCollection dataset = new XYSeriesCollection();
         dataset.addSeries(series);            
        //---------------------------------------------
        
        //(B) Create chart        
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Lexical Time Vs LOC",      // chart title
            "LOC",                      // x axis label
            "Lexical Time (ms)",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );
        
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);        
        TextTitle title = chart.getTitle();
        title.setPaint(new Color(0,168,89));
        title.setFont(new Font("Arial", 1, 18));
        

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
     
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(212,247,231));
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(new Color(0,168,89));
        plot.setRangeGridlinePaint(new Color(0,168,89));
        plot.setOutlinePaint(new Color(72,136,123));         //outline
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(1, false);        
        renderer.setSeriesShapesVisible(1, false);
        
        renderer.setItemLabelGenerator(new StandardXYItemLabelGenerator("({1} , {2})"));
        renderer.setItemLabelsVisible(true);
        final ItemLabelPosition p = new ItemLabelPosition(
            ItemLabelAnchor.OUTSIDE10, TextAnchor.BOTTOM_CENTER, TextAnchor.CENTER, 0
        );                
        renderer.setPositiveItemLabelPosition(p);        
        renderer.setItemLabelPaint(new Color(12,151,81));
        
        plot.setRenderer(renderer);
        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        //set as integer
        NumberAxis domX = (NumberAxis) plot.getDomainAxis();
        domX.setStandardTickUnits(domX.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.    
        
        //---------------------------------------------
        
        
        //(C) create ChartPanel
        final ChartPanel chartPanel = new ChartPanel(chart,false,true,true,true,true);
        chartPanel.setBorder(new LineBorder(new Color(180,180,180)));
        
        return chartPanel;
        
        //return null;
    }

    
    public ChartPanel retLocVsLexTimeChartPanelForLoc(int loc) throws Exception
    {
        if(data==null)
        { throw new Exception("No statistic record found!"); }
        
        //(A) Create XYDataset
        HashSet<Integer> posSet = index.get(loc);
        double values[][] = new double[1][posSet.size()];
        PerformRecord curRcd=null;
        int c=0,avg=0;
        for(Iterator<Integer> i=posSet.iterator();i.hasNext(); )
        {
            curRcd=data.get(i.next());
            values[0][c] = curRcd.lexiclTime;
            avg+=curRcd.lexiclTime;
            c++;
        }
        avg/=c;
        
        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
            "Series ",
            "Test Case ",
          values
        );   
        //---------------------------------------------
        
        //(B) Create chart
        final JFreeChart chart = ChartFactory.createBarChart(
        "Lexical Time for LOC = "+loc,       // chart title
            "Test Case",               // domain axis label
            "Lexical Time (ms)",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // the plot orientation
            false,                    // include legend
            true,
            false
        );

        chart.setBackgroundPaint(Color.white);        
        TextTitle title = chart.getTitle();
        title.setPaint(new Color(0,168,89));
        title.setFont(new Font("Arial", 1, 18));
                
        //add statement
        chart.addSubtitle(new TextTitle("( Average Lexical Time = "+avg+" ms )",
         new Font("Dialog", Font.ITALIC, 14), Color.black,
        RectangleEdge.BOTTOM, HorizontalAlignment.CENTER,
        VerticalAlignment.BOTTOM, RectangleInsets.ZERO_INSETS));
        
         //chart.setBorderPaint(Color.white);
        //final XYPlot plots = chart.getXYPlot();
        //plots.setBackgroundPaint(Color.white);
       
      

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setNoDataMessage("NO DATA!");
        plot.setBackgroundPaint(new Color(212,247,231)); //plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(new Color(0,168,89));
        plot.setOutlinePaint(new Color(72,136,123));  

        
        /*
        final CategoryItemRenderer renderer = new CustomRenderer(
            new Paint[] {new Color(4,123,180)}
        );
        */
        
        //final CategoryItemRenderer renderer = new BarRenderer();
        final BarRenderer renderer = new BarRenderer();
        renderer.setBarPainter(new StandardBarPainter());        
        renderer.setPaint(new Color(12,151,81));        
        renderer.setShadowVisible(false);        
        
        
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setItemLabelsVisible(true);
        final ItemLabelPosition p = new ItemLabelPosition(
            ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER, TextAnchor.CENTER, 0
        );        
        /*final ItemLabelPosition p = new ItemLabelPosition(
            ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER, 45.0
        );
        */
        renderer.setPositiveItemLabelPosition(p);        
        renderer.setItemLabelPaint(new Color(12,151,81));
        plot.setRenderer(renderer);

        // change the margin at the top of the range axis...
        final ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLowerMargin(0.15);
        rangeAxis.setUpperMargin(0.15);

        //---------------------------------------------
        
        
        //(C) create ChartPanel
        final ChartPanel chartPanel = new ChartPanel(chart,false,true,true,true,true);
        chartPanel.setBorder(new LineBorder(new Color(180,180,180)));
        
        return chartPanel;
    }
    
    
    public void setGUIComponenets(JTable jTable,JComboBox comboBx_loc,JComboBox comboBx_type) throws Exception
    {
        if(data.isEmpty())
        { throw new Exception("No statistic record found!"); }
        
        //table
        DefaultTableModel model=(DefaultTableModel)jTable.getModel();
        for( int i = model.getRowCount() - 1; i >= 0; i-- )
        { model.removeRow(i); }
        
        Object rowObj[]=null;
        PerformRecord curRcrd=null;
        for(int i=0;i<data.size();i++)
        {
            curRcrd=data.get(i);
            rowObj= new Object[]{curRcrd.loc,curRcrd.srcCodePath,curRcrd.lexiclTime,curRcrd.totalTime};
            model.insertRow(jTable.getRowCount(), rowObj);
        }
                
        //Combo Box
        setComboBox(comboBx_loc, comboBx_type);
        /*
        //(a) loc
        comboBx_loc.removeAllItems();
        comboBx_loc.addItem(ALL);
        for (Map.Entry<Integer, HashSet<Integer>> entry : index.entrySet()) 
        {
            comboBx_loc.addItem(entry.getKey());//int keyLoc = entry.getKey();             
        }
        
        //(b) type
        comboBx_type.removeAllItems();
        comboBx_type.addItem(TIME);
        */
    }
    
    
    public void setComboBox(JComboBox comboBx_loc,JComboBox comboBx_type) throws Exception
    {
        if(data.isEmpty())
        { throw new Exception("No statistic record found!"); }
     
        //(a) loc
        comboBx_loc.removeAllItems();
        comboBx_loc.addItem(ALL);        
                
        int arr[]= new int[index.size()];
        int ctr=0;
        for (Map.Entry<Integer, HashSet<Integer>> entry : index.entrySet()) 
        {
            if(entry.getValue().size()>1  )
            {   arr[ctr++]=entry.getKey();  } //int keyLoc = entry.getKey();      }
        }                        
        arr=Arrays.copyOfRange(arr, 0,ctr);
        Arrays.sort(arr);
        
        for(int i=0;i<ctr;i++)
        {  comboBx_loc.addItem(arr[i]);  }
        
        
        /*for (Map.Entry<Integer, HashSet<Integer>> entry : index.entrySet()) 
        {
            if(entry.getValue().size()>1  )
            {  comboBx_loc.addItem(entry.getKey());  } //int keyLoc = entry.getKey();      }
        }*/
        
        //(b) type
        comboBx_type.removeAllItems();
        comboBx_type.addItem(COMPILE_TIME);
        comboBx_type.addItem(LEX_TIME);
        
    }
    
    public void generatePDG(File filePath,boolean includeData) throws Exception
    {
        OutputStream file=null;
        com.lowagie.text.Document document = null;
        try
        {        
            file = new FileOutputStream(filePath);
            
            //Set page size and margins
            document = new com.lowagie.text.Document(PageSize.A4, 50, 40, 50, 40);  //l,r,t,b
            PdfWriter.getInstance(document, file);                        
           
            
            //set pdf file basic info            
            document.addTitle("Compiler Report");
            document.addAuthor("CAT Statistic Studio");
            //document.addSubject("Analysis Report of "+ref.r.clientName+" ("+ref.r.cmpName+")");
            document.addCreator("Compiler Automation Tool (CAT)");      //Application
            
            
            //com.lowagie.text.Font authFont = FontFactory.getFont(FontFactory.HELVETICA, 9, com.lowagie.text.Font.ITALIC);
            com.lowagie.text.Font headrFont = FontFactory.getFont(FontFactory.HELVETICA, 10,com.lowagie.text.Font.ITALIC);
            com.lowagie.text.Font footrFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
                        
            com.lowagie.text.Font normFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            com.lowagie.text.Font normBoldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            com.lowagie.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            
            final Color COLOR_LTGRAY= new Color(205,205,205);
            Paragraph pra=null;
            
            //set PageNoEvent
            //PdfWriter.getInstance(document, file).setPageEvent(new MyPageEvent());
            
            //Open Document
            document.open();
            
            //COVER PAGE            
            ImageIcon imgIcon = new ImageIcon(getClass().getResource("/commanLib/images/pdfImages/PDFCoverPage.png"));            
            Image coverImg = Image.getInstance(imgIcon.getImage(),null);                        
            coverImg.scalePercent(59.5f);            
            coverImg.setAbsolutePosition(1, 1);
            document.add(coverImg);
            /*
            //set date       
            Calendar cal=Calendar.getInstance();
            int month= cal.get(Calendar.MONTH);
            int year= cal.get(Calendar.YEAR);
            int day= cal.get(Calendar.DAY_OF_MONTH);
            
            pra=new Paragraph(day+"/"+(month+1)+"/"+year+"\n\n", authFont);            
            pra.setAlignment(Element.ALIGN_CENTER);
            pra.set
            document.add(pra);
            */            
            document.resetFooter();
            
            //set Header
            
            String headStr="Performance Report for "+progLang+" ["+new File(modelPath).getName()+"]";
            HeaderFooter header = new HeaderFooter(new Paragraph(headStr, headrFont), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(HeaderFooter.BOTTOM);
            header.setBorderWidthBottom(0.6f);
            document.setHeader(header);
            
            //set Footer
            /*
            String footStr="Compiler Automation Tool (CAT)\n"+                    
                    "Development Team : Gagandeep Singh, Hargeet Kaur, Amarpreet Singh, Harpreet Kaur\n"+
                    "© 2013 | singh.gagan144@gmail.com | 9717568636";            
            */
            
            String footStr="Report generated by CAT Statistic Studio  © 2013 | singh.gagan144@gmail.com | 9717568636";
            //String footStr="Page "+document.getPageNumber();
            HeaderFooter footer = new HeaderFooter( new Paragraph(footStr, footrFont) ,false);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorder(HeaderFooter.TOP);
            //footer.setBorder(HeaderFooter.NO_BORDER);
            document.setFooter(footer);             
            
            
            document.newPage();
            
            /*
            //Set Logo                                    
            imgIcon = new ImageIcon(getClass().getResource("/commanLib/images/pdfImages/CATLogoPDF.png"));            
            Image logo = Image.getInstance(imgIcon.getImage(),null);                        
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo); 
            */
            
            //Write Document Content                        
            int bullet=1,fig=1,tbl=1;
            //1. Introduction
            pra=new Paragraph(bullet+". INTRODUCTION\n", headFont);
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);  
            
            pra=new Paragraph("Compiler Automation Tool (CAT) is a java based application, a tool for "
                    + "automating the task of compilation process. With CAT, a designer can "
                    + "design compiler for any language using user-friendly graphical interface, by "
                    + "specifying various language specification and then using it to compile source codes "
                    + "showing the intermediate results and performance information. CAT is compiler field "
                    + "specific tool and is useful for anyone related to compiler design, development as well "
                    + "as learning.\n"
                    + "This report is the result of user’s usage of the Compiler Automation Tool that describes "
                    + "his/her modelling specification regarding the programming language under consideration "
                    + "along with the working and the information generated by him/her. The complete report describes "
                    + "the summary of various actions and results that the user has obtained while using this software. "
                    + "Moreover, this report is software generated report whose structure and format are "
                    + "predefined by the CAT development team.\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra); 
            
            pra=new Paragraph(bullet+".1 HOW WAS THIS REPORT GENERATED ?\n", normBoldFont);
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);
            
            pra=new Paragraph("As mentioned above this report has been automatically generated by CAT with respect "
                    + "to user specification and usage however, it is not just an outcome of a single click of a "
                    + "button. CAT uses three-tier architecture in which each phase forms a consumer-producer "
                    + "relationship with each other. This strategy not only allow user to model the language but "
                    + "also put it into test and generate statistical information. The below figure describes the "
                    + "basic architecture of CAT :\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra); 
            
            imgIcon = new ImageIcon(getClass().getResource("/commanLib/images/pdfImages/CAT_arch.png"));            
            Image catArch = Image.getInstance(imgIcon.getImage(),null);                        
            catArch.scalePercent(65f);
            catArch.setAlignment(Element.ALIGN_CENTER);
            document.add(catArch); 
            pra= new Paragraph("Fig "+bullet+"."+(fig++)+" - CAT Architecture",normFont);
            pra.setAlignment(Element.ALIGN_CENTER);
            document.add(pra);
            
            pra=new Paragraph("\nStep 1: CAT Modeler - This is the first and most basic step in which the user decides "
                    + "upon a programming language and tries to model it by specifying various language related "
                    + "information such as regular expressions, transition diagram, context free grammar etc. Once "
                    + "finalised, all these information is saved in a CAT Model file with extension .mdc that is fed "
                    + "into the next phase.\n"
                    + "Step 2: CAT Compiler - With the model of the language ready, the user may now use it to compile "
                    + "various test source code to verify the correctness of his model. He may choose any arbitrary "
                    + "source code of the extension supported and view intermediate actions of the compiler such as "
                    + "lexical analysis, symbol table etc. Besides this, the user may instruct the CAT Compiler to "
                    + "keep a track of the source codes compiled along with their performance information.\n"
                    + "Step 3: CAT Statistic Studio - The information regarding source codes as collected by CAT "
                    + "Compiler is passed to CAT Statistic Studio where the data is analysed and presented in "
                    + "graphical form generating various statistical information useful in judging the performance "
                    + "of the model. The user may further use this application to generate a report of format .pdf "
                    + "that contains all discussed information. Thus, this report is the result of this application "
                    + "that allows user to summarize his/her work in a well-structured formatted report.\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra); 
            
            pra=new Paragraph(bullet+".2 WHAT DOES THIS REPORT CONTAIN ?\n", normBoldFont);
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);
            
            pra=new Paragraph("This reports contains rich collection of user usage about the product that ranges from "
                    + "the model he had created to the statistical information. The following report consists of the "
                    + "model properties followed by the environment details in which the compilation occurred, the "
                    + "performance data and statistical analysis including comparisons and analysis of all the phases "
                    + "of compilation.\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra); 
            
            bullet++;
            fig=1;tbl=1;
            
            //2. Model Properties            
            pra=new Paragraph(bullet+". MODEL PROPERTIES\n", headFont);
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra); 
            
            pra=new Paragraph("A model in terms of Compiler Automation Tool (CAT) is a set of programming language "
                    + "specifications such as regular expressions, transition diagram, context free grammar etc. "
                    + "corresponding to a particular programming language that user wishes to model and test. Better "
                    + "the specifications, better the compiler performs. Besides, specifications related to the "
                    + "language, there are certain properties that go hand in hand with such specifications, "
                    + "distinguishing it from other models. These may be described in terms of language name, version, "
                    + "source code extensions and many more. These properties are essential part of the model which "
                    + "not only distinguishes them but also provide light over the language that has been modelled "
                    + "into it. The following table describes the properties of the model:\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra); 
            
                pra= new Paragraph("Table "+bullet+"."+(tbl++)+" - Model Properties",normFont);
                pra.setAlignment(Element.ALIGN_CENTER);
                document.add(pra); 
            
                Table t = new Table(2,6);    //col,row
                t.setBorderColor(Color.WHITE);
                t.setPadding(1);
                t.setSpacing(0);
                //t.setTableFitsPage(true);
                t.setWidth(60);
                t.setBorderWidth(0);
                            
                Cell c1 = new Cell(new Paragraph("Model Attribute", normBoldFont));
                c1.setHeader(true); 
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                c1.setBackgroundColor(COLOR_LTGRAY);
                t.addCell(c1);                
                c1 = new Cell(new Paragraph("Value", normBoldFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                c1.setBackgroundColor(COLOR_LTGRAY);
                t.addCell(c1);                
            
                String extStr="";
                for(int j=0;j<extension.length;j++)
                { 
                    extStr+=extension[j];
                    if(j!=extension.length-1)
                    { extStr+=", "; }
                }
                File fileMdl = new File(modelPath);
                
                t.addCell( new Paragraph("Model file",normFont) );
                t.addCell( new Paragraph(fileMdl.getName(),normFont) );
                
                t.addCell( new Paragraph("Programming Language",normFont) );
                t.addCell( new Paragraph(progLang,normFont) );
                
                t.addCell( new Paragraph("Model Version",normFont) );
                t.addCell( new Paragraph(mdlVersion,normFont) );
                
                t.addCell( new Paragraph("Author",normFont) );
                t.addCell( new Paragraph(author,normFont) );
                
                t.addCell( new Paragraph("Source Code extension(s)",normFont) );
                t.addCell( new Paragraph(extStr,normFont) );
                
                t.addCell( new Paragraph("Description",normFont) );
                t.addCell( new Paragraph(descptn,normFont) );
                
                t.setAlignment(Element.ALIGN_CENTER);                                                 
                document.add(t);                
                
                bullet++;
                fig=1; tbl=1;
                /*
                pra=new Paragraph("\nModel file : "+fileMdl.getName()+"\n"+
                        "Programming Language : "+progLang+"\n"+
                        "Model Version : "+mdlVersion+"\n"+
                        "Auther : "+author+"\n"+
                        "Source Code extension(s) : "+extStr+"\n"+
                        "Description : "+descptn+"\n\n\n",
                        normFont);
                document.add(pra);
                */
                
            //3. Compilation Environemnt            
            pra=new Paragraph("\n"+bullet+". COMPILATION ENVIRONMENT\n", headFont);
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);  
            
            pra=new Paragraph("This section describes various attributes of the system on which the compilation was "
                    + "performed using various test source codes. Various system attributes such as memory, speed etc. "
                    + "has significant impact on the performance of the compiler. In fact, better the system, better "
                    + "is the compilation speed and response time. Of course, a system with higher memory and speed "
                    + "configuration is likely to compile and respond much earlier than a system with comparably "
                    + "lower configuration. Thus, it is necessary to specify the system attributes as the part of this "
                    + "report. The following table describes various system attributes on which the compilation were "
                    + "performed by the user:\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra); 
                        
                pra= new Paragraph("Table "+bullet+"."+(tbl++)+" - Compilation Environment",normFont);
                pra.setAlignment(Element.ALIGN_CENTER);
                document.add(pra); 
            
                t = new Table(2,12);    //col,row
                t.setBorderColor(Color.WHITE);
                t.setPadding(1);
                t.setSpacing(0);
                //t.setTableFitsPage(true);
                t.setWidth(60);
                t.setBorderWidth(0);
                            
                c1 = new Cell(new Paragraph("System Attribute", normBoldFont));
                c1.setHeader(true); 
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                c1.setBackgroundColor(COLOR_LTGRAY);
                t.addCell(c1);                
                c1 = new Cell(new Paragraph("Value", normBoldFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                c1.setBackgroundColor(COLOR_LTGRAY);
                t.addCell(c1);                
                            
                t.addCell( new Paragraph("User Name",normFont) );
                t.addCell( new Paragraph(sysProp.USER_NAME,normFont) );
                
                t.addCell( new Paragraph("Computer Name",normFont) );
                t.addCell( new Paragraph(sysProp.COMPUTER_NAME,normFont) );
                 
                t.addCell( new Paragraph("OS Name",normFont) );
                t.addCell( new Paragraph(sysProp.OS_NAME,normFont) );
                
                t.addCell( new Paragraph("OS Version",normFont) );
                t.addCell( new Paragraph(sysProp.OS_VER,normFont) );
                
                t.addCell( new Paragraph("System Manufacturer",normFont) );
                t.addCell( new Paragraph(sysProp.SYS_MANUF,normFont) );
                
                t.addCell( new Paragraph("System Model",normFont) );
                t.addCell( new Paragraph(sysProp.SYS_MODEL,normFont) );
                
                t.addCell( new Paragraph("System Type",normFont) );
                t.addCell( new Paragraph(sysProp.SYS_TYPE,normFont) );
                
                t.addCell( new Paragraph("CPU Model",normFont) );
                t.addCell( new Paragraph(sysProp.CPU_MODEL,normFont) );
                
                t.addCell( new Paragraph("CPU Speed",normFont) );
                t.addCell( new Paragraph(sysProp.CPU_SPEED,normFont) );
                
                t.addCell( new Paragraph("Total CPU Cores",normFont) );
                t.addCell( new Paragraph(sysProp.CPU_CORES,normFont) );
                
                t.addCell( new Paragraph("CPU Manufacturer",normFont) );
                t.addCell( new Paragraph(sysProp.CPU_MANUF,normFont) );
                
                t.addCell( new Paragraph("RAM",normFont) );
                t.addCell( new Paragraph(sysProp.RAM,normFont) );
                                
                t.setAlignment(Element.ALIGN_CENTER);                                                 
                document.add(t);                
                
                bullet++;
            
            /*
            pra=new Paragraph(sysProp.toString()+"\n\n\n",normFont);
            document.add(pra);
            */            
            
            //4. Data Table
            if(includeData)
            {
                fig=1; tbl=1;
                pra=new Paragraph("\n"+bullet+". PERFORMANCE DATA\n", headFont);
                pra.setAlignment(Element.ALIGN_LEFT);
                document.add(pra); 
                
                pra=new Paragraph("The performance data describes a tabular representation of the data related to "
                        + "the performance of various source codes that were compiled using the model described in "
                        + "above section under specified system environment. This data representation can be regarded "
                        + "analogous to a standard relational database (to some extent) that uses tables with "
                        + "attributes and tuples. As the source code gets compiled, certain information about the "
                        + "performance is generated if carefully observed. This can extend from overall compile time "
                        + "to memory usage as well as to those in individual compiler phases such as lexical, syntax "
                        + "and so on. So, as the source code(s) gets compiled, the user may instruct CAT Compiler to "
                        + "keep track of the performance. The following table describes the performance data "
                        + "collected by the user\n\n",
                    normFont);
                pra.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(pra);
                
                pra= new Paragraph("Table "+bullet+"."+(tbl++)+" - Performance Data (Total no. of records :"+data.size()+")",normFont);
                pra.setAlignment(Element.ALIGN_CENTER);
                document.add(pra); 
                
                
                t = new Table(5,data.size());    //col,row
                t.setBorderColor(Color.WHITE);
                t.setPadding(1);
                t.setSpacing(0);
                //t.setTableFitsPage(true);
                t.setWidth(90);
                t.setBorderWidth(0);
                
                c1 = new Cell(new Paragraph("S.No", normBoldFont));
                c1.setHeader(true); 
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(COLOR_LTGRAY);
                t.addCell(c1);
                
                c1 = new Cell(new Paragraph("Lines of Code (LOC)", normBoldFont));
                c1.setHeader(true); 
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(COLOR_LTGRAY);
                t.addCell(c1);
                
                c1 = new Cell(new Paragraph("Source Code Path", normBoldFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(COLOR_LTGRAY);                
                t.addCell(c1);
                                
                c1 = new Cell(new Paragraph("Lexical Time (ms)", normBoldFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(COLOR_LTGRAY);
                t.addCell(c1);                
                                
                c1 = new Cell(new Paragraph("Total Time (ms)", normBoldFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(COLOR_LTGRAY);
                t.addCell(c1);                  
            
                for(int i=0;i<data.size();i++)
                {
                    //for(int j=0;j<4;j++)
                    //{   
                        t.addCell( new Paragraph(String.valueOf(i+1),normFont) );
                        t.addCell( new Paragraph(String.valueOf(data.get(i).loc),normFont) );
                        t.addCell( new Paragraph(data.get(i).srcCodePath,normFont) );
                        t.addCell( new Paragraph(String.valueOf(data.get(i).lexiclTime),normFont) );
                        t.addCell( new Paragraph(String.valueOf(data.get(i).totalTime),normFont) );
                    //}
                }
            
                t.setAlignment(Element.ALIGN_CENTER);                                 
                t.setWidths(new float[]{20f,30f,210f,30f,30f});
                document.add(t);            
                 
                bullet++;
            }
            pra=new Paragraph("\n",normFont);
            document.add(pra);
            
            //5. Statistic Report
            //document.newPage();            
            fig=1; tbl=1;
            pra=new Paragraph(bullet+". STATISTICAL ANALYSIS\n", headFont);
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);
            
            pra=new Paragraph("The Statistical Analysis is the major part of this report for which all efforts have "
                    + "been made in previous section from modelling to compiling to generating data and finally "
                    + "analysing it. It is here where the performance data is processed and represented in graphical "
                    + "form to describe the efficiency of the model that the user has created. Of course, this directly "
                    + "describes the efficiency of the language that user intend to model. In terms of automation where "
                    + "CAT Compiler describes decisions made by individual phases, CAT Statistic Studio goes one step "
                    + "deeper into details by generating statistics. The whole idea behind statistic is to allow user "
                    + "to choose the best decision regarding the model on the basis of space and time complexity by "
                    + "comparing the data depicted by the graphs generated.\n"
                    + "This section has been further divided into several sub sections each focusing on particular "
                    + "phase of compilation for instance lexical analysis, syntax analysis and so on and then finally "
                    + "summing up with compilation in totality.\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra);
            
            final int WIDTH=700, HEIGHT=500;             
            Image chartImg=null;
            int evenCtr=0;
            
            //(5.1) lexical            
            pra=new Paragraph(bullet+".1 LEXICAL ANALYSIS\n", headFont);
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);
            
            pra=new Paragraph("This subsection of the statistical analysis targets the most basic step of any compiler "
                    + "i.e. the lexical analysis where various types of token are identified and recorded in symbol "
                    + "table. Out of all performance data collected, the information regarding lexical analysis is "
                    + "extracted and presented as follows.\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra);
            
            //(5.1.1) loc vs Lexical time
            pra=new Paragraph(bullet+".1.1 LEXICAL TIME VS LOC GRAGH\n", normBoldFont);
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);
            
            pra=new Paragraph("This representation describes a graph plotted between lexical time and LOC (lines of "
                    + "Codes). On x-axis are various LOC for which codes were compiled while on y-axis lies the "
                    + "lexical compile time that relates to the average time taken to identify tokens for particular "
                    + "LOC. The purpose of this graph is to represent the average time spend in lexical analysis. As "
                    + "the LOC grows, one expect that the lexical time must also increase. However, it is not always "
                    + "the case. The reasoning regarding such cases can be interpreted from the graph as the slop rises "
                    + "and falls. The graph is as follows:\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra);
            
            PdfPTable table_chart = new PdfPTable(1);
            table_chart.setWidthPercentage(100f);             
            
                chartImg = getITextImgFromChart(retLocVsLexTimeChartPanel().getChart(), WIDTH, HEIGHT, 110);
                chartImg.setAlignment(Element.ALIGN_CENTER);
                //document.add(chartImg);
                
                PdfPCell cell = new PdfPCell(chartImg);     
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                //cell.setFixedHeight(HEIGHT-70);  
                cell.setBorder(Rectangle.NO_BORDER);
                table_chart.addCell( cell );
            
            table_chart.setHorizontalAlignment(Element.ALIGN_CENTER);            
            document.add(table_chart);
            
            pra= new Paragraph("Fig "+bullet+"."+(fig++)+" - Lexical Time vs LOC graph",normFont);
            pra.setAlignment(Element.ALIGN_CENTER);
            document.add(pra);
            
            //(5.1.2)Individual lex time
            pra=new Paragraph("\n"+bullet+".1.2 LEXICAL TIME GRAGH FOR INDIVIDUAL LOC\n", normBoldFont);            
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);
            
            pra=new Paragraph("The user may compile a code more than once or perhaps many codes more than once. "
                    + "In such a case it necessary to take the average of the time spend describing the performance "
                    + "corresponding to a particular LOC. This section consider each individual LOC and plots a bar "
                    + "graph of the lexical time spend in each case as well as specifies the average time taken.\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra);
            
//            Table table_chart = new Table(2,index.size()/2);    //col,row
//            table_chart.setBorderColor(Color.WHITE);
//            table_chart.setPadding(1);
//            table_chart.setSpacing(3);                                
//            table_chart.setBorderWidth(0);
            table_chart = new PdfPTable(2);
            table_chart.setWidthPercentage(100f);                        

            int locArr[]= new int[index.size()];
            int ctr=0;
            for (Map.Entry<Integer, HashSet<Integer>> entry : index.entrySet()) 
            {
                if(entry.getValue().size()>1  )
                {   locArr[ctr++]=entry.getKey();  } 
            }                        
            locArr=Arrays.copyOfRange(locArr, 0,ctr);
            Arrays.sort(locArr);
            
            /*        
            for (Map.Entry<Integer, HashSet<Integer>> entry : index.entrySet()) 
            {
                if(entry.getValue().size()>1  )
                {  
                    //table_chart.addCell( new Paragraph(String.valueOf(entry.getKey()),normFont) );                      
                    chartImg = getITextImgFromChart(retLocVsLexTimeChartPanelForLoc(entry.getKey()).getChart(), 340, 310, 110);
                    chartImg.setAlignment(Element.ALIGN_CENTER);            
                    PdfPCell cell = new PdfPCell(chartImg);     
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setFixedHeight(225);  
                    cell.setBorder(Rectangle.NO_BORDER);
                    //cell.setBorderColor(Color.WHITE);
                    table_chart.addCell( cell );                      
                    evenCtr++;                                   
                } 
            }
            */
            evenCtr=0;
            for(int l=0;l<locArr.length;l++)
            {
                    chartImg = getITextImgFromChart(retLocVsLexTimeChartPanelForLoc(locArr[l]).getChart(), 340, 310, 110);
                    chartImg.setAlignment(Element.ALIGN_CENTER);            
                    cell = new PdfPCell(chartImg);     
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setFixedHeight(225);  
                    cell.setBorder(Rectangle.NO_BORDER);
                    //cell.setBorderColor(Color.WHITE);
                    table_chart.addCell( cell );                      
                    evenCtr++;  
            }            
            if(evenCtr%2!=0)   //fill remaining cell
            {  
                cell = new PdfPCell(new Paragraph(""));
                cell.setFixedHeight(225);  
                cell.setBorder(Rectangle.NO_BORDER);
                table_chart.addCell(cell);    
            }
                        
            table_chart.setHorizontalAlignment(Element.ALIGN_CENTER);            
            document.add(table_chart);
            
            pra= new Paragraph("Fig "+bullet+"."+(fig++)+" - Lexical Time graph for individual LOC",normFont);
            pra.setAlignment(Element.ALIGN_CENTER);
            document.add(pra);            
            
            //(5.2) Total Time                       
            pra=new Paragraph("\n"+bullet+".2 TOTAL COMPILATION ANALYSIS\n", headFont);
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);
            
            pra=new Paragraph("This section provides over all statistical information regarding total time required to "
                    + "completely compile source codes covering all phases. This section describes the combined "
                    + "performance of individual phases and is useful in judging the performance of the compilation "
                    + "in totality.\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra);
            
            //(5.2.1) loc vs total compile time
            pra=new Paragraph(bullet+".2.1 TOTAL COMPILE TIME VS LOC GRAGH\n", normBoldFont);
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);
            
            pra=new Paragraph("As discussed in previous subsections corresponding to individual phases, this section "
                    + "provides the same information by representing a graph plotted between total compile time and "
                    + "LOC (lines of Codes) rather than just considering each phase. The graph is as follows:\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra);
            
            table_chart = new PdfPTable(1);
            table_chart.setWidthPercentage(100f);             
            
                chartImg = getITextImgFromChart(retLocVsTotTimeChartPanel().getChart(), WIDTH, HEIGHT, 110);
                chartImg.setAlignment(Element.ALIGN_CENTER);
                //document.add(chartImg);
                
                cell = new PdfPCell(chartImg);     
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                //cell.setFixedHeight(HEIGHT-70);  
                cell.setBorder(Rectangle.NO_BORDER);
                table_chart.addCell( cell );
            
            table_chart.setHorizontalAlignment(Element.ALIGN_CENTER);            
            document.add(table_chart);
            
            pra= new Paragraph("Fig "+bullet+"."+(fig++)+" - Total Compile Time vs LOC graph",normFont);
            pra.setAlignment(Element.ALIGN_CENTER);
            document.add(pra);
            
            //(5.2.2)Individual total Compile time
            pra=new Paragraph("\n"+bullet+".2.2 TOTAL COMPILE TIME GRAGH FOR INDIVIDUAL LOC\n", normBoldFont);            
            pra.setAlignment(Element.ALIGN_LEFT);
            document.add(pra);
            
            pra=new Paragraph("Below are the bar graphs for individual LOC describing total time taken to compile. The "
                    + "average total time is also specified along with each graph.\n\n",
                    normFont);
            pra.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(pra);

            table_chart = new PdfPTable(2);
            table_chart.setWidthPercentage(100f);                       
            /*
            int locArr[]= new int[index.size()];
            int ctr=0;
            for (Map.Entry<Integer, HashSet<Integer>> entry : index.entrySet()) 
            {
                if(entry.getValue().size()>1  )
                {   locArr[ctr++]=entry.getKey();  } 
            }                        
            locArr=Arrays.copyOfRange(locArr, 0,ctr);
            Arrays.sort(locArr);
            */
            evenCtr=0;
            for(int l=0;l<locArr.length;l++)
            {
                    chartImg = getITextImgFromChart(retLocVsTotTimeChartPanelForLoc(locArr[l]).getChart(), 340, 310, 110);
                    chartImg.setAlignment(Element.ALIGN_CENTER);            
                    cell = new PdfPCell(chartImg);     
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setFixedHeight(225);  
                    cell.setBorder(Rectangle.NO_BORDER);
                    //cell.setBorderColor(Color.WHITE);
                    table_chart.addCell( cell );                      
                    evenCtr++;  
            }            
            if(evenCtr%2!=0)   //fill remaining cell
            {  
                cell = new PdfPCell(new Paragraph(""));
                cell.setFixedHeight(225);  
                cell.setBorder(Rectangle.NO_BORDER);
                table_chart.addCell(cell);    
            }
                        
            table_chart.setHorizontalAlignment(Element.ALIGN_CENTER);            
            document.add(table_chart);
            
            pra= new Paragraph("Fig "+bullet+"."+(fig++)+" - Total Compile Time graph for individual LOC",normFont);
            pra.setAlignment(Element.ALIGN_CENTER);
            document.add(pra);            
            
            bullet++;
            //end of 5        
            
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
    
    private Image getITextImgFromChart(JFreeChart chart,int width,int height,int dpi) throws Exception
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ChartUtilities.writeChartAsPNG(bos, chart, width, height);
        byte[] img = bos.toByteArray();
        Image pngImg = Image.getInstance(img);
        
        //dpi                
        pngImg.scalePercent(72f / dpi * 100);                                        
        
        return pngImg;
    }
    
    public String[] getSrcCodeList()
    {
        String list[]=new String[data.size()];
        for(int i=0;i<data.size();i++)
        {
            list[i]=data.get(i).srcCodePath;
        }
        return list;
    }
    
    public static void writeToFile(StatisticData data,String path) throws Exception
    {
        FileOutputStream fout=null;
        ObjectOutputStream oos=null;
        
        try
        {
            fout = new FileOutputStream(path);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(data);
        }
        finally
        {
            if(oos!=null)
            { oos.close(); }
        }
    }
    
    public static StatisticData readFromFile(String path) throws Exception
    {
        StatisticData data=null;
        FileInputStream fin=null;
        ObjectInputStream ois =null;
                
        try
        {
            fin = new FileInputStream(path);            
            ois = new ObjectInputStream(fin);            
            data=(StatisticData)ois.readObject();       
        }   
        finally
        {
            if(ois!=null)
            { ois.close(); }
        }
        
        return data;
    }
    
    class CustomRenderer extends BarRenderer {

        /** The colors. */
        private Paint[] colors;

        /**
         * Creates a new renderer.
         *
         * @param colors  the colors.
         */
        public CustomRenderer(final Paint[] colors) {
            this.colors = colors;
        }

        /**
         * Returns the paint for an item.  Overrides the default behaviour inherited from
         * AbstractSeriesRenderer.
         *
         * @param row  the series.
         * @param column  the category.
         *
         * @return The item color.
         */
        public Paint getItemPaint(final int row, final int column) {
            return this.colors[column % this.colors.length];
        }
    }
    
    //delete
    public void displayIndex()
    {
        System.out.println(index);
    }
    //---------
    
}

class MyPageEvent extends PdfPageEventHelper
{
    public void onEndPage (PdfWriter writer, Document document) 
    {
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, 
                new Phrase(String.format("%d", writer.getPageNumber())),
                300f, 62f, 0
        );
    }
}