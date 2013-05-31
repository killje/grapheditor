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
    private Point position;
    private Rectangle vertex;

    public GraphVertex(int x, int y, int width, int height, String name) {
        this.name = name;
        vertex = new Rectangle(x, y, width, height);
    }

    public GraphVertex() {
        this(0, 0, 100, 30, "default");
    }

    public boolean contains(Point p) {
        return false;
    }

    public void setPosition(Point p) {
        this.position = p;
    }

    public Rectangle getVertex() {
        return this.vertex;
    }
}
