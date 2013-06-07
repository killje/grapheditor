package graph;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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


        setVisible(true);


    }

    private void init(GraphModel graphModel) {
        model = graphModel;

        if (panel != null) {
            this.remove(panel);
        }

        panel = new GraphPanel(graphModel);
        this.add(panel);
        setJMenuBar(addMenuBar());
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

    public void store() {
        final JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".gme", "gme");
        chooser.removeChoosableFileFilter(chooser.getFileFilter() );
        chooser.addChoosableFileFilter(filter);
        chooser.setFileFilter(filter);
        
        chooser.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString() + "\\src\\graph\\saveFiles\\"));
        int returnVal = chooser.showOpenDialog(this);
        
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        if (!chooser.getTypeDescription(chooser.getSelectedFile()).equals(".gme")) {
            chooser.setSelectedFile(new File(chooser.getSelectedFile() + ".gme"));
        }
        File file = chooser.getSelectedFile();
        
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

    public void read() {
        final JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".gme", "gme");
        chooser.addChoosableFileFilter(filter);
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString() + "\\src\\graph\\saveFiles\\"));
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();

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
                read();
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
        menuFile.add(addMenuItem("Load", KeyEvent.VK_L, KeyEvent.VK_O, new LoadAction()));
        menuFile.add(addMenuItem("Save", KeyEvent.VK_S, KeyEvent.VK_S, new SaveAction()));
        menuFile.add(addMenuItem("Quit", KeyEvent.VK_Q, new QuitAction()));
        menuBar.add(menuFile);

        JMenu menuEdit = addMenu("Edit", KeyEvent.VK_E);
        menuEdit.add(addMenuItem("Add vertex", KeyEvent.VK_V, KeyEvent.VK_N, new AddVertexAction()));
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

    private JMenuItem addMenuItem(String name, int shortKey, int keyBindings, AbstractAction actionListener) {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keyBindings, Event.CTRL_MASK);
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(keyStroke, name);
        panel.getActionMap().put(name, actionListener);
        return addMenuItem(name, shortKey, actionListener);

    }

    private class SaveAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            store();
        }
    }

    private class LoadAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            read();
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
