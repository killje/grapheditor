package graph;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphEdge {

    private GraphVertex first, second;

    public GraphEdge(GraphVertex v1, GraphVertex v2) {
        this.first = v1;
        this.second = v2;
    }

    public boolean isIncedent(GraphVertex v) {
        if (this.first == v || this.second == v) {
            return true;
        }
        return false;
    }
}
