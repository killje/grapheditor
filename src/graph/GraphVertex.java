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

    public void graphVertex(Point p, int width, int height, String name) {
        this.name = name;
        vertex = new Rectangle(p.x, p.y, width, height);
    }

    public void graphVertex() {
        graphVertex(new Point(50, 50), 100, 40, "default");
    }

    public boolean contains(Point p) {
        return false;
    }

    public void setPosition(Point p) {
        this.position = p;
    }
}
