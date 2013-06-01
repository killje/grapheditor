/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Patrick
 */
public class PanelAction extends Observable implements MouseListener {

    private GraphPanel panel = null;
    private Point diffPosition;
    private GraphVertex selectedVertex;
    private boolean hasSelected;

    public PanelAction(GraphPanel panel) {
        this.panel = panel;
        this.addObserver(panel);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        mousePressed(me);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        hasSelected = false;
        Point mousePosition;
        mousePosition = me.getPoint();
        System.out.println("pressed:" + mousePosition.toString());
        if (panel.getModel().getVertices() != null) {
            for (GraphVertex vertex : panel.getModel().getVertices()) {
                System.out.println("pressed:" + vertex.contains(mousePosition));
                if (vertex.contains(mousePosition)) {
                    vertex.setSelected();
                    hasSelected = true;
                    selectedVertex = vertex;
                    diffPosition = new Point(
                            (int) mousePosition.getX()
                            - (int) vertex.getVertexRectangle().getX(),
                            (int) mousePosition.getY()
                            - (int) vertex.getVertexRectangle().getY());
                    this.setChanged();
                } else {
                    vertex.resetSelected();
                    this.setChanged();
                }
            }
        }
        if (!hasSelected) {
            String vertexName = getNameForVertex();
            if (vertexName != null) {
                GraphVertex newVertex = new GraphVertex(me.getX(), me.getY(), 100, 30, vertexName);
                panel.getModel().addVertex(newVertex);
            }
        }
        this.notifyObservers();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        Point mousePosition;
        mousePosition = me.getPoint();
        if (panel.getModel().getVertices() != null) {
            for (GraphVertex vertex : panel.getModel().getVertices()) {
                System.out.println("pressed:" + vertex.contains(mousePosition));
                if (vertex.contains(mousePosition)) {
                    if (selectedVertex != vertex) {
                        hasSelected = false;
                        boolean doContinue = true;
                        for (GraphEdge edge : panel.getModel().getEdges()) {
                            if (edge.isIncedent(selectedVertex) && edge.isIncedent(vertex)) {
                                doContinue = false;
                            }
                        }
                        if (doContinue) {
                            GraphEdge newEdge;
                            newEdge = new GraphEdge(selectedVertex, vertex);
                            panel.getModel().addEdge(newEdge);
                            this.setChanged();
                            System.out.println("create edge");
                        }
                    }
                }
            }
        }
        if (hasSelected) {
            Point point = new Point(
                    me.getX() - (int) diffPosition.getX(),
                    me.getY() - (int) diffPosition.getY());
            System.out.println(selectedVertex);
            selectedVertex.setPosition(point);
            this.setChanged();

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

    public String getNameForVertex() {
        final JFrame parent = new JFrame();
        String name = JOptionPane.showInputDialog(parent, "Name of vertex:", null);
        return name;
    }
}
