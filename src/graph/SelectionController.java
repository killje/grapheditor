package graph;

import java.awt.Point;
import java.awt.event.*;
import java.util.Observable;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
    private boolean manualAdd = false;

    public SelectionController(GraphPanel panel) {
        this.addObserver(panel);
        this.model = panel.getModel();
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (manualAdd) {
            if (!isPopEvent) {
                if (panel.isDrawing()) {
                    Point mousePosition;
                    mousePosition = me.getPoint();
                    GraphVertex vertex = isVertex(mousePosition);
                    if (vertex != null) {
                        addEdge(vertex, selectedVertex);
                    }
                    panel.resetDrawing();
                    manualAdd = false;
                    this.setChanged();
                }
            }

            this.notifyObservers();
        }
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
            
            model.deselectAllVertices();
            
            if (vertex != null && !manualAdd) {
                vertex.setSelected();
                hasSelected = true;
                selectedVertex = vertex;
                diffPosition = new Point(
                        (int) mousePosition.getX()
                        - (int) vertex.getVertexRectangle().getX(),
                        (int) mousePosition.getY()
                        - (int) vertex.getVertexRectangle().getY());
            }
            if (!hasSelected && !manualAdd) {
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
        //not implemented
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (!isPopEvent) {
            if (panel.isDrawing() && !manualAdd) {
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
        //not implemented
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //not implemented
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if (!isPopEvent) {
            if (panel.isDrawing() && manualAdd) {
                Point mousePosition;
                mousePosition = me.getPoint();
                panel.drawLine(mousePosition, selectedVertex);
                
                this.setChanged();
            }
        }
        
        this.notifyObservers();
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
        GraphVertex vertex = isVertex(e.getPoint());
        
        
        if (vertex != null) {
            PopUpDemo menu = new PopUpDemo(e.getPoint(), vertex);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private class PopUpDemo extends JPopupMenu {

        JMenuItem addEdge;
        JMenuItem rename;

        public PopUpDemo(Point p, GraphVertex vertex) {
            addEdge = new JMenuItem("Add edge");
            addEdge.addActionListener(new addEdge(p));
            add(addEdge);

            rename = new JMenuItem("Rename");
            rename.addActionListener(new rename(vertex));
            add(rename);
        }
    }

    private class addEdge implements ActionListener {

        Point point;

        public addEdge(Point p) {
            point = p;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            selectedVertex = isVertex(point);
            model.deselectAllVertices();
            selectedVertex.setSelected();
            manualAdd = true;
            isPopEvent = false;
            panel.setDrawing();
        }
    }

    private class rename implements ActionListener {

        GraphVertex graphVertex;

        public rename(GraphVertex vertex) {
            graphVertex = vertex;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            String name = JOptionPane.showInputDialog(null, "Name of vertex:", graphVertex.getVertexName());
            if (name != null) {
                graphVertex.setVertexName(name);
                SelectionController.this.setChanged();
                SelectionController.this.notifyObservers();
            }
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
