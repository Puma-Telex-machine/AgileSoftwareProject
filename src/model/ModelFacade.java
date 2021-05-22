package model;

import global.Observable;
import global.point.ScaledPoint;
import model.boxes.BoxFacade;
import model.boxes.BoxType;
import model.diagram.ModelObserver;
import model.facades.FileHandlerFacade;
import model.facades.UndoChain;
import model.relations.ArrowType;

public interface ModelFacade extends Observable<ModelObserver>, UndoChain {

    static ModelFacade getModel(){
        return Model.getModel();
    }

    static FileHandlerFacade getFileHandler() {
        return Model.getModel();
    }

    Boolean canUndo();

    Boolean canRedo();

    void loadUndoLayer();

    void loadRedoLayer();

    void createBox(ScaledPoint position, BoxType boxType);
    void createRelation(BoxFacade from, ScaledPoint offsetFrom, BoxFacade to, ScaledPoint offsetTo, ArrowType arrowType);
}
