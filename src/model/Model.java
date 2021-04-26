package model;

import java.awt.*;
import java.util.ArrayList;

public class Model {

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
}
