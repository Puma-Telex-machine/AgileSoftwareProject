package model.facades;

/**
 * Interface used to call on the model's undo/redo system from the diagram.
 * Not a perfect solution, but it does suit our purposes
 */
public interface UndoChain {
    void updateUndo();
    void stopUndo();
    void resumeUndo();
}
