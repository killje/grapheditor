package graph;

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

    public void paintAllComponents(Graphics g) {
        if (model.getVertices() != null) {
            for (GraphVertex vertex : model.getVertices()) {
                Rectangle rect = vertex.getVertex();
                g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        paintAllComponents(g);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GraphModel) {
            //repaint();
        }
    }
}
