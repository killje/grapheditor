package graph;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphFrame extends JFrame {

    private GraphModel graphModel = new GraphModel();
    private GraphPanel graphPanel;

    public GraphFrame() {
        init();
    }

    private void init() {
        setTitle("Graph editor");
        setSize(Graph.FRAME_WIDTH, Graph.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setJMenuBar(addMenuBar());

        GraphVertex vertex1 = graphModel.addVertex(150, 50, Graph.STANDARD_VERTEX_WIDTH, Graph.STANDARD_VERTEX_HEIGHT, "Default");
        GraphVertex vertex2 = graphModel.addVertex(150, 250, Graph.STANDARD_VERTEX_WIDTH, Graph.STANDARD_VERTEX_HEIGHT, "Dit is lange tekst");
        GraphVertex vertex3 = graphModel.addVertex(/* kijken of Default werkt*/);
        graphModel.addEdge(vertex1, vertex2);
        graphModel.addEdge(vertex2, vertex3);

        graphPanel = new GraphPanel(graphModel);
        SelectionController selectionController = new SelectionController(graphPanel);
        graphPanel.addMouseListener(selectionController);
        graphPanel.addMouseMotionListener(selectionController);
        this.add(graphPanel);

        setVisible(true);
    }

    public void store(File file) {

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.graphModel);

            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            System.err.println("The desired file was not found.");
        } catch (NotSerializableException e) {
            System.err.println("The saved object is not serializable at: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("An error with the I/O was reported, program closing.");
            System.exit(-1);
        }

    }

    public void read(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object object = ois.readObject();
            if (!(object instanceof GraphModel)) {
                throw new Exception("An illegal class type was found (" + object.getClass().getName() + ")");
            }

            GraphModel model = (GraphModel) object;
            graphPanel.setModel(model);
            graphPanel.repaint();

            ois.close();
            fis.close();
        } catch (ClassNotFoundException e) {
            System.err.println("The file could not be read.");
        } catch (FileNotFoundException e) {
            System.err.println("The desired file was not found.");
        } catch (IOException e) {
            System.err.println("An error with the I/O was reported, program closing.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
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
        menuEdit.add(addMenuItem("Add vertex", KeyEvent.VK_V, new AddVertexAction()));
        menuEdit.add(addMenuItem("Add edge", KeyEvent.VK_E, new AddEdgeAction()));
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

    private JMenuItem addMenuItem(String name, int shortKey, AbstractAction actionListener) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(actionListener);
        menuItem.setMnemonic(shortKey);

        return menuItem;
    }

    private class SaveAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            final JFrame parent = new JFrame();
            String path = JOptionPane.showInputDialog(parent, "Path Name:", null);
            store(new File(path));
        }
    }

    private class LoadAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            final JFrame parent = new JFrame();
            String path = JOptionPane.showInputDialog(parent, "Path Name:", null);
            path = path.replace("/", "//");
            read(new File(path));
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
            graphModel.addVertexWithName();
        }
    }

    private class AddEdgeAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            graphPanel.setDrawing();
        }
    }

    private class HideAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
