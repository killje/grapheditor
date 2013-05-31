package graph;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphFrame extends JFrame {

    private JMenuItem itemHideBar;

    public GraphFrame() {
        this.init();
    }

    private void init() {
        setTitle("Graph editor");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        JMenuItem itemLoad = new JMenuItem("Load");
        itemLoad.setMnemonic(KeyEvent.VK_L);
        menuFile.add(itemLoad);
        JMenuItem itemSave = new JMenuItem("Save");
        itemSave.setMnemonic(KeyEvent.VK_S);
        menuFile.add(itemSave);
        menuBar.add(menuFile);

        JMenu menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_E);
        JMenuItem itemUndo = new JMenuItem("Undo");
        itemUndo.setMnemonic(KeyEvent.VK_U);
        menuEdit.add(itemUndo);
        JMenuItem itemRedo = new JMenuItem("Redo");
        itemRedo.setMnemonic(KeyEvent.VK_R);
        menuEdit.add(itemRedo);
        JMenuItem itemAddVertex = new JMenuItem("Add vertex");
        itemAddVertex.setMnemonic(KeyEvent.VK_V);
        menuEdit.add(itemAddVertex);
        JMenuItem itemAddEdge = new JMenuItem("Add edge");
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

        JPanel graphPanel = new GraphPanel();
        this.add(graphPanel);
        
        

        setVisible(true);
    }
}
