package commanLib;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxGraph;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Panel_mxGraphDisplay extends JPanel
{
    private mxGraphComponent graphComponent =null;
    

	private static final long serialVersionUID = -2707712944901661771L;

        public Panel_mxGraphDisplay()
        { }
        
	public Panel_mxGraphDisplay(mxGraphComponent graphComponent)
	{                       
            if(graphComponent!=null)
            { 
                this.graphComponent=graphComponent;
                this.graphComponent.getViewport().setBackground(Color.white);  
                this.graphComponent.setBorder(null);
                setBackground(Color.white);
                
                this.graphComponent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                this.graphComponent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                this.graphComponent.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                
                //Centering
                //this.graphComponent.getGraph().setOrigin();
                
                add(graphComponent);   
            }
            
            //setBackground(Color.white);
            //setOpaque(true);
	}
        
        public mxGraphComponent getMxGraphComponent()
        {
            return graphComponent;
        }
        
        public void adjustSizeOfGraphComp(Dimension dim)
        {
            if(graphComponent!=null)
            { graphComponent.setSize(dim);}
        }
        
        public void zoomIn()
        {   
            if(graphComponent!=null)
            {
                //resizeGraphArea();
                graphComponent.zoomIn(); 
                //graphComponent.repaint();
            }
            
        }
        
        public void zoomOut()
        {
            if(graphComponent!=null)
            {
                //resizeGraphArea();
                graphComponent.zoomOut();
                //graphComponent.repaint();
            }
            
        }
        
        
        public void zoomToFit(Dimension dim)
        {
            if(graphComponent!=null)
            {
                double newScale = 1;
                                

                Dimension graphSize = graphComponent.getGraphControl().getSize();
                Dimension viewPortSize = dim;

                int gw = (int) graphSize.getWidth();
                int gh = (int) graphSize.getHeight();

                if (gw > 0 && gh > 0) {
                    int w = (int) viewPortSize.getWidth();
                    int h = (int) viewPortSize.getHeight();

                    newScale = Math.min((double) w / gw, (double) h / gh);
                }

                graphComponent.zoom(newScale);
                
                //getComponent(0).setLocation(0,0);            
                //graphComponent.repaint();
            }
            
        }
        
        public mxGraphOutline getGraphOutline()
        {
            if(graphComponent==null)
            { return null; }
            else
            {
                final mxGraphOutline graphOutline = new mxGraphOutline(graphComponent);
                graphOutline.setPreferredSize(new Dimension(100, 100));                
                return graphOutline;
            }            
        }
        
        public void autolayout()
        {
            if(graphComponent!=null)
            {
                mxGraph graph = graphComponent.getGraph();
                new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());
            }
        }
                
        
        /*
        public void zoomToFit()
        {
            resizeGraphArea();
            double newScale = 1;

                Dimension graphSize = graphComponent.getGraphControl().getSize();
                Dimension viewPortSize = graphComponent.getViewport().getSize();

                int gw = (int) graphSize.getWidth();
                int gh = (int) graphSize.getHeight();

                if (gw > 0 && gh > 0) {
                    int w = (int) viewPortSize.getWidth();
                    int h = (int) viewPortSize.getHeight();

                    newScale = Math.min((double) w / gw, (double) h / gh);
                }

                graphComponent.zoom(newScale);
        }
        */
        
        /*
        public void resizeGraphArea()
        {
            if(graphComponent!=null)
            {
                graphComponent.setSize(getSize());//.height-10,getSize().width-10);
                getComponent(0).setLocation(0,0);            
            }
            
        }
        */
 
}
