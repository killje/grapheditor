package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphModel extends Observable {

    private ArrayList<GraphVertex> vertices = new ArrayList<>();
    private ArrayList<GraphEdge> edges = new ArrayList<>();

    public void GraphModel() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addVertex(GraphVertex v) {
        this.vertices.add(v);
        notifyObservers();
    }

    public void addEdge(GraphEdge e) {
        this.edges.add(e);
        notifyObservers();
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
        notifyObservers();
    }

    public ArrayList<GraphVertex> getVertices() {
        return this.vertices;
    }

    public ArrayList<GraphEdge> getEdges() {
        return this.edges;
    }
}
