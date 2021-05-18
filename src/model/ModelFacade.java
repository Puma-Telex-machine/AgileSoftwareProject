package model;

import model.diagram.DiagramFacade;
import model.facades.FileHandlerFacade;

public interface ModelFacade {

    static ModelFacade getModel(){
        return Model.getModel();
    }

    static FileHandlerFacade getFileHandler() {
        return Model.getModel();
    }

    DiagramFacade getDiagram();

    Boolean canUndo();

    Boolean canRedo();

    void loadUndoLayer();

    void loadRedoLayer();
}
