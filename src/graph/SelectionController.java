package graph;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class SelectionController extends Observable implements MouseListener {

   
    private Point diffPosition;
    private GraphVertex selectedVertex;
    private boolean hasSelected;
    private GraphModel model;

    public SelectionController(GraphPanel panel) {
        
        this.addObserver(panel);
        this.model  = panel.getModel();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        mousePressed(me);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        hasSelected = false;
        Point mousePosition;
        mousePosition = me.getPoint();
        if (model.getVertices() != null) {
            for (GraphVertex vertex : model.getVertices()) {
                if (vertex.contains(mousePosition)) {
                    vertex.setSelected();
                    hasSelected = true;
                    selectedVertex = vertex;
                    diffPosition = new Point(
                            (int) mousePosition.getX()
                            - (int) vertex.getVertexRectangle().getX(),
                            (int) mousePosition.getY()
                            - (int) vertex.getVertexRectangle().getY());
                    this.setChanged();
                } else {
                    vertex.resetSelected();
                    this.setChanged();
                }
            }
        }
        if (!hasSelected) {
            String vertexName = model.getNameForVertex();
            if (vertexName != null) {
                model.addVertex(me.getX(), me.getY(), Graph.STANDARD_VERTEX_WIDTH, Graph.STANDARD_VERTEX_HEIGHT, vertexName);
            }
        }
        this.notifyObservers();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        Point mousePosition;
        mousePosition = me.getPoint();
        if (model.getVertices() != null) {
            for (GraphVertex vertex : model.getVertices()) {
                if (vertex.contains(mousePosition)) {
                    if (selectedVertex != vertex) {
                        hasSelected = false;
                        boolean doContinue = true;
                        for (GraphEdge edge : model.getEdges()) {
                            if (edge.isIncedent(selectedVertex) && edge.isIncedent(vertex)) {
                                doContinue = false;
                            }
                        }
                        if (doContinue) {
                            model.addEdge(selectedVertex, vertex);
                            this.setChanged();
                        }
                    }
                }
            }
        }
        if (hasSelected) {
            Point point = new Point(
                    me.getX() - (int) diffPosition.getX(),
                    me.getY() - (int) diffPosition.getY());
            selectedVertex.setPosition(point);
            this.setChanged();

        }
        this.notifyObservers();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //not suported
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //not suported
    }
}
