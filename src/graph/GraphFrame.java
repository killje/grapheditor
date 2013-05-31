package graph;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphFrame extends JFrame {

    public GraphFrame() {
        init();
    }

    private void init() {

        setTitle("Graph editor");
        setSize(Graph.FRAME_WIDTH, Graph.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();

        setJMenuBar(addMenuBar());
        this.add(addToolBar());

        GraphModel graphModel = new GraphModel();
        graphModel.addVertex(new GraphVertex());

        JPanel graphPanel = new GraphPanel(graphModel);
        contentPane.add(graphPanel);

        setVisible(true);
    }

    private JToolBar addToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(addButton("Add Vertex", new AddVertexAction()));
        toolBar.add(addButton("Add Edge", new AddVertexAction()));
        return toolBar;
    }

    private JMenuBar addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = addMenu("File", KeyEvent.VK_F);
        menuFile.add(addMenuItem("Load", KeyEvent.VK_L, new LoadAction()));
        menuFile.add(addMenuItem("Save", KeyEvent.VK_S, new SaveAction()));
        menuFile.add(addMenuItem("Quit", KeyEvent.VK_Q, new QuitAction()));
        menuBar.add(menuFile);

        JMenu menuEdit = addMenu("Edit", KeyEvent.VK_E);
        menuEdit.add(addMenuItem("Undo", KeyEvent.VK_U, new UndoAction()));
        menuEdit.add(addMenuItem("Redo", KeyEvent.VK_R, new RedoAction()));
        menuEdit.add(addMenuItem("add Vertex", KeyEvent.VK_V, new AddVertexAction()));
        menuEdit.add(addMenuItem("add Edge", KeyEvent.VK_E, new AddEdgeAction()));
        menuBar.add(menuEdit);

        JMenu menuWindow = addMenu("Window", KeyEvent.VK_W);
        menuWindow.add(addMenuItem("Hide bar", KeyEvent.VK_H, new HideAction()));
        menuBar.add(menuWindow);
        return menuBar;
    }

    private JMenu addMenu(String name, int shortKey) {
        JMenu menu = new JMenu(name);
        menu.setMnemonic(shortKey);
        return menu;
    }

    private JMenuItem addMenuItem(String name, int shortKey, AbstractAction listner) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(listner);
        menuItem.setMnemonic(shortKey);
        return menuItem;
    }

    private JButton addButton(String name, AbstractAction listner) {
        JButton button = new JButton(name);
        button.addActionListener(listner);
        return button;
    }

    private class SaveAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class LoadAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet.");
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
        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class RedoAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class AddVertexAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class AddEdgeAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class HideAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
