package graph;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 *
 * @author Patrick Beuks (s2288842), Floris Huizinga (s2397617) and
 * @author Timo Smit (s2337789)
 */
public class GraphModelWithUndo extends GraphModel {

    private class AddVertexOperation extends AbstractUndoableEdit {
        
        public AddVertexOperation() {
            GraphModelWithUndo.super.addVertex();
        }

        @Override
        public void undo() throws CannotUndoException {
        }

        @Override
        public void redo() throws CannotRedoException {
        }
    }

    private class AddEdgeOperation extends AbstractUndoableEdit {

        @Override
        public void undo() throws CannotUndoException {
            
        }

        @Override
        public void redo() throws CannotRedoException {
        }
    }

    private class RemoveVertexOperation extends AbstractUndoableEdit {

        @Override
        public void undo() throws CannotUndoException {
            
        }

        @Override
        public void redo() throws CannotRedoException {
        }
    }
}
