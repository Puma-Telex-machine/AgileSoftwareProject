package model;

import frontend.Observers.UiObservable;
import frontend.Observers.UiObserver;
import model.boxes.Box;
import model.boxes.BoxType;
import model.facades.BoxFacade;

import java.awt.*;
import java.util.ArrayList;

public class BoxManager implements BoxFacade {
    private final Diagram diagram;
    private final Box box;
    private ArrayList<UiObserver> observers = new ArrayList<UiObserver>();

    public void subscribe(UiObserver newObserver)
    {
        observers.add(newObserver);
    }

    private  void updateObservers()
    {
        for (int i = 0; i < observers.size(); i++)
        {
            observers.get(i).update();
        }
    }

    public BoxManager(Diagram diagram, Point position) {
        this.diagram = diagram;
        this.box = diagram.createBox(position);
    }

    public BoxManager(Diagram diagram, Box box){
        this.diagram = diagram;
        this.box = box;
    }

    @Override
    public void deleteBox() {
        diagram.deleteBox(box);
    }

    @Override
    public void editMethod(MethodData methodData) {

        diagram.editMethod(box, methodData);
        updateObservers();
    }

    @Override
    public void editVariable(VariableData variableData) {

        diagram.editVariable(box, variableData);
        updateObservers();
    }

    @Override
    public void deleteMethod(String methodName) {
        diagram.deleteMethod(box, methodName);
        updateObservers();
    }

    @Override
    public void deleteVariable(String variableName) {
        diagram.deleteVariable(box, variableName);
        updateObservers();
    }

    @Override
    public Point getPosition() {
        return new Point(box.getPosition());
    }

    @Override
    public void setPosition(Point point) {
        diagram.move(box, point);
    }

    @Override
    public void setName(String name) {
        box.setName(name);
    }

    @Override
    public BoxType getType() {
        return box.getType();
    }

    public boolean isEmpty() {
        return box == null;
    }
}
