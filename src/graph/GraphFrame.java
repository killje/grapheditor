package graph;

import java.awt.Event;
import java.awt.Rectangle;
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
        setTitle("Graph Editor - Untitled");
        setSize(Graph.FRAME_WIDTH, Graph.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
        init(new GraphModel());
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

    public void store(File path) {
        final JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = 
                new FileNameExtensionFilter(".gme", "gme");
        chooser.removeChoosableFileFilter(chooser.getFileFilter());
        chooser.addChoosableFileFilter(filter);
        chooser.setFileFilter(filter);

        chooser.setCurrentDirectory(path);
        int returnVal = chooser.showSaveDialog(this);

        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File file = new File(chooser.getSelectedFile().toString().replace(".gme", "") + ".gme");

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } else {
                int confirmed = JOptionPane.showConfirmDialog(rootPane, 
                                    "Are you sure you want to override " + 
                                    file.getName() + "?");
                if (confirmed == 1) {
                    store(file.getParentFile());
                    return;
                } else if(confirmed == 2) {
                    return;
                }
            }
            
            this.setTitle("Graph Editor - " + file.getName());
            
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.model);

            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            System.err.println("The desired file was not found.");
        } catch (NotSerializableException e) {
            System.err.println("The saved object is not serializable at: "
                    + e.getMessage());
        } catch (IOException e) {
            System.err.println("An error with the I/O was reported, "
                    + "program closing.");
            System.exit(-1);
        }

    }

    public void store() {
        store(new File(Paths.get("").toAbsolutePath().toString() + 
                File.separator + "src" + File.separator + "graph" +
                File.separator + "saveFiles" + File.separator));
    }

    public void read(File path) {
        final JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = 
                new FileNameExtensionFilter(".gme", "gme");
        chooser.addChoosableFileFilter(filter);
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(path);
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
                throw new Exception("An illegal class type was found (" + 
                        object.getClass().getName() + ")");
            }
            
            this.setTitle("Graph Editor - " + file.getName());

            GraphModel readModel = (GraphModel) object;
            init(readModel);

            ois.close();
            fis.close();
        } catch (ClassNotFoundException e) {
            System.err.println("The file could not be read.");
        } catch (FileNotFoundException e) {
            System.err.println("The desired file was not found.");
            JOptionPane.showMessageDialog(this, "the file you gave "
                    + "was not found");
            read(file.getParentFile());
        } catch (IOException e) {
            System.err.println("An error with the I/O was reported, "
                    + "program closing.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void read() {
        read(new File(Paths.get("").toAbsolutePath().toString() + 
                File.separator + "src" + File.separator + "graph" + 
                File.separator + "saveFiles" + File.separator));
    }

    private JMenuBar addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = addMenu("File", KeyEvent.VK_F);
        menuFile.add(addMenuItem("Load", KeyEvent.VK_L, KeyEvent.VK_O, 
                new LoadAction()));
        menuFile.add(addMenuItem("Save", KeyEvent.VK_S, KeyEvent.VK_S, 
                new SaveAction()));
        menuFile.addSeparator();
        menuFile.add(addMenuItem("Quit", KeyEvent.VK_Q, new QuitAction()));
        menuBar.add(menuFile);

        JMenu menuEdit = addMenu("Edit", KeyEvent.VK_E);
        menuEdit.add(addMenuItem("Cut", KeyEvent.VK_U, KeyEvent.VK_X, 
                new CutAction()));
        menuEdit.add(addMenuItem("Copy", KeyEvent.VK_C, KeyEvent.VK_C, 
                new CopyAction()));
        menuEdit.add(addMenuItem("Paste", KeyEvent.VK_P, KeyEvent.VK_V, 
                new PasteAction()));
        menuEdit.addSeparator();
        menuEdit.add(addMenuItem("Add vertex", KeyEvent.VK_V, KeyEvent.VK_N, 
                new AddVertexAction()));
        menuBar.add(menuEdit);

        return menuBar;
    }

    private JMenu addMenu(String name, int shortKey) {
        JMenu menu = new JMenu(name);
        menu.setMnemonic(shortKey);

        return menu;
    }

    private JMenuItem addMenuItem(String name, int shortKey, 
            AbstractAction actionListener) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(actionListener);
        menuItem.setMnemonic(shortKey);

        return menuItem;
    }

    private JMenuItem addMenuItem(String name, int shortKey, int keyBindings, 
            AbstractAction actionListener) {
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

    private class AddVertexAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            final JFrame parent = new JFrame();
            String vertexName = JOptionPane.showInputDialog(parent, 
                    "Name of vertex:", null);
            model.addVertex(vertexName);
        }
    }

    private class CutAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            GraphVertex vertex = model.getSelectedVertex();
            if (vertex != null) {
                model.setActionVertex(new GraphVertex(vertex));
                model.removeVertex(model.getSelectedVertex());

            }
        }
    }

    private class CopyAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            GraphVertex vertex = model.getSelectedVertex();
            if (vertex != null) {
                model.setActionVertex(new GraphVertex(vertex));

            }
        }
    }

    private class PasteAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {


            Rectangle graphRectangle = model.getActionVertex()
                    .getVertexRectangle();
            graphRectangle.x = graphRectangle.x + 20;
            graphRectangle.y = graphRectangle.y + 20;

            model.addVertex(graphRectangle, model.getActionVertex()
                    .getVertexName());

        }
    }
}
