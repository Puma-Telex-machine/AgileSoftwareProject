package model;

import model.grid.Diagram;

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

        //runTest();
    }
/*
    private void runTest() {
        BoxManager boxManager = new BoxManager(diagram, new Point(100, 100));
        if (!boxManager.isEmpty()) {
            observers.forEach(observer -> observer.addBox(boxManager));
        }

        diagram.createRelation()
        addTestPoint(new Point(10, 10));
        //addBox(new Point(20, 20));
    }

    private void addTestPoint(Point point) {
        observers.forEach(observer -> observer.addTestPoint(point.x, point.y));
    }

 */
}
