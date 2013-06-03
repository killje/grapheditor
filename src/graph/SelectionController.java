package graph;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class SelectionController extends Observable implements MouseMotionListener, MouseListener {

    private Point diffPosition;
    private GraphVertex selectedVertex;
    private boolean hasSelected;
    private GraphModel model;
    private GraphPanel panel;
    private boolean isPopEvent = false;

    public SelectionController(GraphPanel panel) {
        this.addObserver(panel);
        this.model = panel.getModel();
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        mousePressed(me);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (me.getButton() == 3) {
            isPopEvent = true;
            doPop(me);
            this.setChanged();
        } else {
            isPopEvent = false;
            hasSelected = false;
            Point mousePosition;
            mousePosition = me.getPoint();
            GraphVertex vertex = isVertex(mousePosition);
            model.deselectAllVertecies();
            if (vertex != null) {
                vertex.setSelected();
                hasSelected = true;
                selectedVertex = vertex;
                diffPosition = new Point(
                        (int) mousePosition.getX()
                        - (int) vertex.getVertexRectangle().getX(),
                        (int) mousePosition.getY()
                        - (int) vertex.getVertexRectangle().getY());
            }
            if (!hasSelected) {
                String vertexName = model.getNameForVertex();
                if (vertexName != null) {
                    model.addVertex(me.getX(), me.getY(), Graph.STANDARD_VERTEX_WIDTH, Graph.STANDARD_VERTEX_HEIGHT, vertexName);
                }
            }
        }
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (!isPopEvent) {
            if (model.isDrawing()) {
                Point mousePosition;
                mousePosition = me.getPoint();
                GraphVertex vertex = isVertex(mousePosition);
                if (vertex != null) {
                    addEdge(vertex, selectedVertex);
                    this.setChanged();
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (!isPopEvent) {
            if (model.isDrawing()) {
                Point mousePosition;
                mousePosition = me.getPoint();
                panel.drawLine(mousePosition, selectedVertex);
                this.setChanged();
            } else {

                if (hasSelected) {
                    Point point = new Point(
                            me.getX() - (int) diffPosition.getX(),
                            me.getY() - (int) diffPosition.getY());
                    selectedVertex.setPosition(point);
                    this.setChanged();
                }
            }
        }
        this.notifyObservers();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //not suported
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //not suported
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //not suported
    }

    public void addEdge(GraphVertex v1, GraphVertex v2) {
        if (v1 != v2) {
            hasSelected = false;
            boolean doContinue = true;
            for (GraphEdge edge : model.getEdges()) {
                if (edge.isIncedent(v1) && edge.isIncedent(v2)) {
                    doContinue = false;
                }
            }
            if (doContinue) {
                model.addEdge(v1, v2);
                this.setChanged();
            }
        }
    }

    private void doPop(MouseEvent e) {
        PopUpDemo menu = new PopUpDemo();
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    private class PopUpDemo extends JPopupMenu {

        JMenuItem anItem;

        public PopUpDemo() {
            anItem = new JMenuItem("Click Me!");
            add(anItem);
        }
    }

    private GraphVertex isVertex(Point p) {
        GraphVertex vertex = null;
        if (model.getVertices() != null) {
            for (GraphVertex vertecies : model.getVertices()) {
                if (vertecies.contains(p)) {
                    vertex = vertecies;
                }
            }
        }
        return vertex;
    }
}
