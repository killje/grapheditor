package graph;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphFrame extends JFrame {

    public GraphFrame() {
        this.init();
    }

    private class LoadAction extends AbstractAction {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("itemLoad.");
        }
    }

    private class SaveAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("itemSave.");
        }
    }

    private class QuitAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class UndoAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("itemUndo.");
        }
    }

    private class RedoAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("itemRedo.");
        }
    }

    private class AddVertexAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("itemAddVertex.");
        }
    }

    private class AddEdgeAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("itemAddEdge.");
        }
    }

    private void init() {

        setTitle("Graph editor");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();

        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        JMenuItem itemLoad = new JMenuItem(new LoadAction());
        itemLoad.setText("Load");
        itemLoad.setMnemonic(KeyEvent.VK_L);
        menuFile.add(itemLoad);
        JMenuItem itemSave = new JMenuItem(new SaveAction());
        itemSave.setText("Save");
        itemSave.setMnemonic(KeyEvent.VK_S);
        menuFile.add(itemSave);
        JMenuItem itemQuit = new JMenuItem(new QuitAction());
        itemQuit.setText("Quit");
        itemQuit.setMnemonic(KeyEvent.VK_Q);
        menuFile.add(itemQuit);
        menuBar.add(menuFile);

        JMenu menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_E);
        JMenuItem itemUndo = new JMenuItem(new UndoAction());
        itemUndo.setText("Undo");
        itemUndo.setMnemonic(KeyEvent.VK_U);
        menuEdit.add(itemUndo);
        JMenuItem itemRedo = new JMenuItem(new RedoAction());
        itemRedo.setText("Redo");
        itemRedo.setMnemonic(KeyEvent.VK_R);
        menuEdit.add(itemRedo);
        JMenuItem itemAddVertex = new JMenuItem(new AddVertexAction());
        itemAddVertex.setText("Add vertex");
        itemAddVertex.setMnemonic(KeyEvent.VK_V);
        menuEdit.add(itemAddVertex);
        JMenuItem itemAddEdge = new JMenuItem(new AddEdgeAction());
        itemAddEdge.setText("Add edge");
        itemAddEdge.setMnemonic(KeyEvent.VK_E);
        menuEdit.add(itemAddEdge);
        menuBar.add(menuEdit);

        JMenu menuWindow = new JMenu("Window");
        menuWindow.setMnemonic(KeyEvent.VK_W);
        JMenuItem itemHideBar = new JMenuItem("Hide bar");
        itemHideBar.setMnemonic(KeyEvent.VK_H);
        menuWindow.add(itemHideBar);
        menuBar.add(menuWindow);

        setJMenuBar(menuBar);
        
        GraphModel graphModel = new GraphModel();
        graphModel.addVertex(new GraphVertex());
        JPanel graphPanel = new GraphPanel(graphModel);
        contentPane.add(graphPanel);

        setVisible(true);
    }
}
