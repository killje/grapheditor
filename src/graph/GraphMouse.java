package graph;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphMouse extends MouseAdapter {

    GraphVertex selectedVertex;
    GraphModel model;
    boolean selected = false;

    public void GraphMouse(GraphModel model) {
        this.model = model;
    }

    public void MousePressed(MouseEvent e) {
        Point p = e.getPoint();
        this.selected = false;
        for (GraphVertex vertex : model.getVertices()) {
            if (vertex.contains(p)) {
                this.selected = true;
                this.selectedVertex = vertex;
            }
        }
        if(!selected){
            this.selectedVertex = null;
        }
    }

    public void MosedDragged(MouseEvent e) {
        Point point = e.getPoint();
        if(this.selectedVertex != null){
            this.selectedVertex.setPosition(point);
        }
    }
}
