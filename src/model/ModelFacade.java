package model;

import global.point.ScaledPoint;
import model.boxes.BoxFacade;
import model.boxes.BoxType;
import model.diagram.DiagramFacade;
import model.facades.FileHandlerFacade;
import model.relations.RelationFacade;

public interface ModelFacade {

    static ModelFacade getModel(){
        return Model.getModel();
    }

    static FileHandlerFacade getFileHandler() {
        return Model.getModel();
    }

    DiagramFacade getDiagram();

}
