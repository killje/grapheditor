package graph;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphVertex implements Serializable {

    private String name;
    private Rectangle vertex;
    private boolean selected = false;

    public GraphVertex(int x, int y, int width, int height, String name) {
        this.name = name;
        vertex = new Rectangle(x, y, width + 10, height);
    }

    public boolean contains(Point p) {
        if (vertex.contains(p)) {
            return true;
        }
        return false;
    }

    public void setPosition(Point p) {
        this.vertex.setLocation(p);
    }

    public void setWidth(int width) {
        this.vertex.setSize(width + 10, (int) this.vertex.getHeight());
    }

    public Rectangle getVertexRectangle() {
        return this.vertex;
    }

    public void setSelected() {
        selected = true;
    }

    public void resetSelected() {
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getVertexName() {
        return this.name;
    }

    public void setVertexName(String name) {
        this.name = name;
    }
}
