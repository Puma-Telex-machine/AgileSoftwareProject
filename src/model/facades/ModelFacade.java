package model.facades;

import model.Model;
import model.point.ScaledPoint;

import java.awt.*;

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
    void addBox(ScaledPoint position);
}
