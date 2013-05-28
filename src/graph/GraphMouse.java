package graph;

import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphMouse {
    GraphVertex selectedVertex;
    GraphModel model;
    public void GraphMouse(GraphModel model){
        this.model=model;
    }
    public void MousePressed(MouseEvent e){
        Point p = e.getPoint();
        this.selectedVertex = model.getVertex(p);
    }
    public void MosedDragged(MouseEvent e){
        
    }
}
