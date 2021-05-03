package model;

import model.facades.BoxFacade;
import model.facades.Observer;
import model.facades.RelationFacade;
import model.grid.Diagram;
import model.point.ScaledPoint;
import model.relations.ArrowType;
import model.relations.Relation;

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

    public void addBox(ScaledPoint position) {
        BoxFacade boxManager = diagram.createBox(position);
        observers.forEach(observer -> observer.addBox(boxManager));
    }

    public void addRelation(BoxFacade from, BoxFacade to, ArrowType arrowType) {
        RelationFacade relation = diagram.createRelation(from, to, arrowType);
        observers.forEach(observer -> observer.addRelation(relation));
    }
}
