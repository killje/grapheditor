package graph;

import java.awt.Point;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphVertex {

    private String name = "default";
    private Point position = null;

    public void graphVertex() {
    }

    public boolean contains(Point p) {
        return false;
    }

    public void setPosition(Point p) {
        this.position = p;
    }
}
