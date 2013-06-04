package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

    public GraphVertex addVertex(int x, int y, int width, int height, String name) {
        GraphVertex vertex = new GraphVertex(x, y, width, height, name);

        this.vertices.add(vertex);
        this.setChanged();
        notifyObservers();

        return vertex;
    }

    public GraphVertex addVertex() {
        return addVertex(0, 0, Graph.STANDARD_VERTEX_WIDTH, Graph.STANDARD_VERTEX_HEIGHT, "Default");
    }

    public GraphVertex addVertexWithName() {
        return addVertex(0, 0, Graph.STANDARD_VERTEX_WIDTH, Graph.STANDARD_VERTEX_HEIGHT, getNameForVertex());
    }

    public GraphEdge addEdge(GraphVertex vertex1, GraphVertex vertex2) {
        GraphEdge edge = new GraphEdge(vertex1, vertex2);

        this.edges.add(edge);
        notifyObservers();

        return edge;
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

    public String getNameForVertex() {
        final JFrame parent = new JFrame();
        String name = JOptionPane.showInputDialog(parent, "Name of vertex:", null);
        return name;
    }
    
    public void addEdgeAction(){
        final JFrame parent = new JFrame();
        JOptionPane.showMessageDialog(parent, "To create an edge drag the mouse between the two vertecies.");
    }
    
    public void deselectAllVertecies(){
        for (GraphVertex vertecies : this.getVertices()) {
            vertecies.resetSelected();
        }
    }
}
