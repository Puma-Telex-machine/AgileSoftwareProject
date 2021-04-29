package model.facades;

import model.Observer;

import java.awt.*;

public interface ModelFacade {

    /**
     * Returns the file handler of the model
     * @return
     */
    FileHandlerFacade getFileHandler();

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void addBox(Point position);
}
