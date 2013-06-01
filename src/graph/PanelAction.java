/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Patrick
 */
public class PanelAction implements MouseListener {

    GraphModel model = null;

    public PanelAction(GraphModel model) {
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Point mousePosition;
        mousePosition = me.getPoint();
        System.out.println(mousePosition.toString());
        if (model.getVertices() != null) {
            for (GraphVertex vertex : model.getVertices()) {
                System.out.println(vertex.contains(mousePosition));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //not supported
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //not supported
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //not supported
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //not supported
    }
}
