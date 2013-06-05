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
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphFrame extends JFrame {

    private GraphPanel panel = null;
    private GraphModel model;

    public GraphFrame() {
        createFrame();
        init(testModel());
    }

    private void createFrame() {
        setTitle("Graph editor");
        setSize(Graph.FRAME_WIDTH, Graph.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setJMenuBar(addMenuBar());
        setVisible(true);

    }

    private void init(GraphModel graphModel) {
        model = graphModel;

        if (panel != null) {
            this.remove(panel);
        }

        panel = new GraphPanel(graphModel);
        this.add(panel);
        this.revalidate();
    }

    private GraphModel testModel() {
        GraphModel testModel = new GraphModel();
        GraphVertex vertex1 = testModel.addVertex(150, 50, Graph.STANDARD_VERTEX_WIDTH, Graph.STANDARD_VERTEX_HEIGHT, "Default");
        GraphVertex vertex2 = testModel.addVertex(150, 250, Graph.STANDARD_VERTEX_WIDTH, Graph.STANDARD_VERTEX_HEIGHT, "Dit is lange tekst");
        GraphVertex vertex3 = testModel.addVertex(/* kijken of Default werkt*/);
        testModel.addEdge(vertex1, vertex2);
        testModel.addEdge(vertex2, vertex3);
        return testModel;
    }

    public void store(File file) {

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } else {
                if (JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to override " + file.getName() + "?") != 0) {
                    return;
                }
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.model);

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

            GraphModel readModel = (GraphModel) object;
            init(readModel);

            ois.close();
            fis.close();
        } catch (ClassNotFoundException e) {
            System.err.println("The file could not be read.");
        } catch (FileNotFoundException e) {
            System.err.println("The desired file was not found.");
            JOptionPane.showMessageDialog(this, "the file: " + file.getName() + " does not excist");
            String path = JOptionPane.showInputDialog(this, "Path Name:", file);
            if (path != null) {
                read(new File(path));
            }
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
            String path = JOptionPane.showInputDialog(parent, "Path Name:", "src/graph/saveFiles/test.txt");
            path = path.replace("/", "//");
            store(new File(path));
        }
    }

    private class LoadAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            final JFrame parent = new JFrame();
            String path = JOptionPane.showInputDialog(parent, "Path Name:", "src/graph/saveFiles/test.txt");
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
            model.addVertexWithName();
        }
    }

    private class AddEdgeAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            panel.setDrawing();
        }
    }

    private class HideAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
