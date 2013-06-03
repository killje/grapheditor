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

    public GraphModel getModel() {
        return this.model;
    }

    public void paintVertices(Graphics g) {
        if (model.getVertices() != null) {
            for (GraphVertex vertex : model.getVertices()) {
                Rectangle rect = vertex.getVertexRectangle(); // de vertex zelf
                String vertexName = vertex.getVertexName(); // de vertex naam

                // de vertex x, y, width en height
                int rectX = (int) rect.getX();
                int rectY = (int) rect.getY();
                int rectWidth = (int) rect.getWidth() + 10;
                int rectHeight = (int) rect.getHeight();

                // om te bepalen hoelang een string is zodat je weet hoe je moet centreren
                Rectangle bounds = g.getFontMetrics().getStringBounds(vertexName, g).getBounds();
                
                if (bounds.width > rectWidth) {
                    rectWidth = bounds.width + 10;
                }
                
                int centerWidth = rectX + (rectWidth / 2) - (bounds.width / 2);
                int centerHeight = rectY + (rectHeight / 2) + 5;

                if (vertex.isSelected()) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(rectX, rectY, rectWidth, rectHeight); //vertex binnenkant tekenen
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(rectX, rectY, rectWidth, rectHeight); //vertex binnenkant tekenen
                }
                g.setColor(Color.GRAY);
                g.drawRect(rectX, rectY, rectWidth, rectHeight); // vertex border tekenen
                g.setColor(Color.BLACK);
                g.drawString(vertexName, centerWidth, centerHeight); // vertex naam neerzetten
            }
        }

    }

    public void paintEdges(Graphics g) {
        if (model.getEdges() != null) {
            for (GraphEdge edge : model.getEdges()) {
                Rectangle v1, v2;
                v1 = edge.getFirstVertex().getVertexRectangle();
                v2 = edge.getSecondVertex().getVertexRectangle();
                int v1X = (int) v1.getX() + (int) v1.getWidth() / 2;
                int v1Y = (int) v1.getY() + (int) v1.getHeight() / 2;
                int v2X = (int) v2.getX() + (int) v2.getWidth() / 2;
                int V2Y = (int) v2.getY() + (int) v2.getHeight() / 2;

                g.setColor(Color.BLACK);
                g.drawLine(v1X, v1Y, v2X, V2Y); //lijn tussen twee vertecies
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintEdges(g);
        paintVertices(g);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
