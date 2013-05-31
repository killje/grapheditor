package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphPanel extends JPanel implements Observer {

    GraphModel model;

    public GraphPanel(GraphModel model) {
        this.setModel(model);
    }

    public final void setModel(GraphModel model) {
        if (this.model != null) {
            this.model.deleteObserver(this);
        }
        this.model = model;
        this.model.addObserver(this);
    }

    public void paintVertices(Graphics g) {
        if (model.getVertices() != null) {
            for (GraphVertex vertex : model.getVertices()) {
                Rectangle rect = vertex.getVertexRectangle();
                g.setColor(Color.WHITE);
                g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
                g.setColor(Color.GRAY);
                g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
                
            }
        }
        
    }
    
    public void paintEdges(Graphics g){
        if (model.getEdges() != null) {
            for (GraphEdge edge : model.getEdges()) {
                Rectangle v1,v2;
                v1 = edge.getFirstVertex().getVertexRectangle();
                v2 = edge.getSecondVertex().getVertexRectangle();
                g.setColor(Color.BLACK);
                g.drawLine((int) v1.getX() + (int) v1.getWidth()/2, (int) v1.getY() + (int) v1.getHeight()/2,(int) v2.getX() + (int) v2.getWidth()/2, (int) v2.getY() + (int) v2.getHeight()/2);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        paintEdges(g);
        paintVertices(g);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GraphModel) {
            //repaint();
        }
    }
}
