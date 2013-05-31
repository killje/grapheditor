package graph;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphModel {

    private ArrayList<GraphVertex> vertices;
    private ArrayList<GraphEdge> edges;

    public void GraphModel() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addVertex(GraphVertex v) {
        this.vertices.add(v);
    }

    public void addEdge(GraphEdge e) {
        this.edges.add(e);
    }

    public void removeVertex(GraphVertex v) {
        Iterator<GraphEdge> iter = edges.iterator();
        while (iter.hasNext()) {
            GraphEdge edge = iter.next();
            if (edge.isIncedent(v)) {
                iter.remove();
            }
        }
        this.vertices.remove(v);
    }

    public ArrayList<GraphVertex> getVertices() {
        return this.vertices;
    }
}
