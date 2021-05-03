package model;

import model.facades.BoxFacade;
import model.relations.ArrowType;
import model.relations.Relation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    public Relation addRelation(BoxFacade from, BoxFacade to){
        //todo add relation and return the apropriate type
        return new Relation(null,null,ArrowType.ASSOCIATION);
    }
    public void changeRelation(Relation relation,ArrowType type){
        //todo change relation
    }

    public List<Point> getArrowBends(BoxFacade from, BoxFacade to) {
        //todo add pathfinding to here
        return new ArrayList<>();
    }
}
