package model;


import model.boxes.Box;
import model.facades.ModelFacade;

import java.awt.*;
import java.util.ArrayList;

public class Model implements ModelFacade {

    private static Model singleton;
    public static Model getModel() {
        if (singleton == null) singleton = new Model();
        return singleton;
    }

    ArrayList<Observer> observers = new ArrayList<>();
    Diagram diagram = new Diagram();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void addBox(Point position) {
        BoxManager boxManager = new BoxManager(diagram, position);
        if (!boxManager.isEmpty()) {
            observers.forEach(observer -> observer.addBox(boxManager));
        }
    }

    public void loadDiagram(String filename){
        diagram = Database.loadDiagram("test");
        for(Box box : diagram.boxGrid.getAllBoxes()){
            BoxManager boxManager = new BoxManager(diagram, box);
            observers.forEach(observer -> observer.addBox(boxManager));
        }
    }
}
