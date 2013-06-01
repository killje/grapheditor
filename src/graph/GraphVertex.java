package graph;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphVertex {

    private String name;
    private Rectangle vertex;

    public GraphVertex(int x, int y, int width, int height, String name) {
        this.name = name;
        vertex = new Rectangle(x, y, width, height);
    }

    public GraphVertex() {
        this(0,0, Graph.STANDARD_VERTEX_WIDTH, Graph.STANDARD_VERTEX_HEIGHT, "Default");
    }

    public boolean contains(Point p) {
        if (vertex.contains(p)){
            return true;
        }
        return false;
    }

    public void setPosition(Point p) {
        this.vertex.setLocation(p);
    }
    
    public Rectangle getVertexRectangle(){
        return this.vertex;
    }

    public String getVertexName() {
        return this.name;
    }
   
}
