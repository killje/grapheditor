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
                    GraphVertex vertex = model.isVertex(mousePosition);
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
            GraphVertex vertex = model.isVertex(mousePosition);
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
        GraphVertex vertex = model.isVertex(e.getPoint());
        if (vertex != null) {
            PopUpMenuVertex menu = new PopUpMenuVertex(e.getPoint(), vertex);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }else{
            PopUpMenu menu = new PopUpMenu(e.getPoint(), vertex);
            menu.show(e.getComponent(), e.getX(), e.getY());
            
        }
    }

    private class PopUpMenuVertex extends JPopupMenu {

        JMenuItem addEdge;
        JMenuItem rename;
        JMenuItem Cut;
        JMenuItem Copy;
        JMenuItem Paste;

        public PopUpMenuVertex(Point p, GraphVertex vertex) {
            addEdge = new JMenuItem("add Edge");
            addEdge.addActionListener(new addEdge(p));
            add(addEdge);

            rename = new JMenuItem("rename");
            rename.addActionListener(new rename(vertex));
            add(rename);
            
            Cut = new JMenuItem("Cut");
            Cut.addActionListener(new cutAction());
            add(Cut);
            
            Copy = new JMenuItem("Copy");
            Copy.addActionListener(new copyAction());
            add(Copy);
            
            Paste = new JMenuItem("Paste");
            Paste.addActionListener(new pasteAction());
            add(Paste);
        }
    }
    
    private class PopUpMenu extends JPopupMenu {

        
        JMenuItem Cut;
        JMenuItem Copy;
        JMenuItem Paste;
        

        public PopUpMenu(Point p, GraphVertex vertex) {
            
            
            Cut = new JMenuItem("Cut");
            Cut.addActionListener(new cutAction());
            add(Cut);
            
            Copy = new JMenuItem("Copy");
            Copy.addActionListener(new copyAction());
            add(Copy);
            
            Paste = new JMenuItem("Paste");
            Paste.addActionListener(new pasteAction());
            add(Paste);
        }
    }

    private class addEdge implements ActionListener {

        Point point;

        public addEdge(Point p) {
            point = p;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            selectedVertex = model.isVertex(point);
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
    private class cutAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            GraphVertex vertex = model.getSelectedVertex();
            if (vertex != null) {
                selectedVertex = new GraphVertex(vertex);
                model.removeVertex(model.getSelectedVertex());
            }
        }
    }
    
    private class copyAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            GraphVertex vertex = model.getSelectedVertex();
            if (vertex != null) {
                model.setActionVertex(new GraphVertex(vertex));
            }
        }
    }

    private class pasteAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Rectangle graphRectangle = model.getActionVertex().getVertexRectangle();
            graphRectangle.x = graphRectangle.x + 20;
            graphRectangle.y = graphRectangle.y + 20;

            model.addVertex(graphRectangle, model.getActionVertex().getVertexName());
        }
    }
}
