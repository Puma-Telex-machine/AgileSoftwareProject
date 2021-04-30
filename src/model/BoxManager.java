package model;

import model.boxes.Box;
import model.boxes.BoxType;
import model.facades.BoxFacade;

import java.awt.*;

public class BoxManager implements BoxFacade {
    private final Diagram diagram;
    private final Box box;

    public BoxManager(Diagram diagram, Point position) {
        this.diagram = diagram;
        this.box = diagram.createBox(position);
    }

    @Override
    public void deleteBox() {
        diagram.deleteBox(box);
    }

    @Override
    public void editMethod(MethodData methodData) {
        diagram.editMethod(box, methodData);
    }

    @Override
    public void editVariable(VariableData variableData) {
        diagram.editVariable(box, variableData);
    }

    @Override
    public void deleteMethod(String methodName) {
        diagram.deleteMethod(box, methodName);
    }

    @Override
    public void deleteVariable(String variableName) {
        diagram.deleteVariable(box, variableName);
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
