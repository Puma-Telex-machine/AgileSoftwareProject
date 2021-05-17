package model.facades;

import model.Model;
import model.boxes.BoxType;
import model.point.ScaledPoint;

public interface ModelFacade {

    /**
     * Returns the file handler of the model
     * @return
     */
    FileHandlerFacade getFileHandler();

    static ModelFacade getModel(){
        return Model.getModel();
    }

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void addBox(ScaledPoint position, BoxType boxType);

    /**
     * Call when pressing the "undo" button: will rewind to an older version of the document if possible.
     */
    void loadUndoLayer();

    /**
     * Call when pressing the "redo" button: will "undo an undo" if possible
     */
    void loadRedoLayer();

    /**
     * Safety measure to prevent invalid undos
     * @return True if an undo is possible, False otherwise
     */
    Boolean canUndo();

    /**
     * Safety measure to prevent invalid redos
     * @return True if a redo is possible, False otherwise
     */
    Boolean canRedo();
}
