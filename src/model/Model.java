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
        diagram.createBox(position, "This is a box name");
        Box box = diagram.getBox(0);
        observers.forEach(observer -> observer.addBox(box));
    }
}
