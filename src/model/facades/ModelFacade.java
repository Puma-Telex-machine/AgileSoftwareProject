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
}
