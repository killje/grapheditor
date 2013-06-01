/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

/**
 *
 * @author Patrick
 */
public class PanelAction extends Observable implements MouseListener  {

    GraphPanel panel = null;

    public PanelAction(GraphPanel panel) {
        this.panel = panel;
        this.addObserver(panel);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Point mousePosition;
        mousePosition = me.getPoint();
        System.out.println(mousePosition.toString());
        if (panel.getModel().getVertices() != null) {
            for (GraphVertex vertex : panel.getModel().getVertices()) {
                System.out.println(vertex.contains(mousePosition));
                if (vertex.contains(mousePosition)){
                    vertex.setSelected();
                }else{
                    vertex.resetSelected();
                }
            }
        }
        this.setChanged();
        this.notifyObservers();
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
