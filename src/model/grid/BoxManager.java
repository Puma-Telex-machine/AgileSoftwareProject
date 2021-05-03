package model.grid;

import frontend.Observers.UiObserver;
import model.boxes.Box;
import model.boxes.BoxType;
import model.facades.BoxFacade;
import model.facades.MethodData;
import model.facades.VariableData;
import model.point.ScaledPoint;

public class BoxManager implements BoxFacade {
    private final IBoxGrid diagram;
    private Box box;

    public BoxManager(IBoxGrid diagram, Box box) {
        this.diagram = diagram;
        this.box = box;
    }

    @Override
    public Box getBox() {
        return box;
    }

    @Override
    public void deleteBox() {
        diagram.remove(box);
        this.box = null;
    }

    @Override
    public void editMethod(MethodData methodData) {
        box.editMethod(methodData);
        diagram.update(box);
    }

    @Override
    public void editVariable(VariableData variableData) {
        box.editVariable(variableData);
        diagram.update(box);
    }

    @Override
    public void deleteMethod(String methodName) {
        box.deleteMethod(methodName);
        diagram.update(box);
    }

    @Override
    public void deleteVariable(String variableName) {
        box.deleteVariable(variableName);
        diagram.update(box);
    }

    @Override
    public ScaledPoint getPosition() {
        return box.getPosition();
    }

    @Override
    public void setPosition(ScaledPoint point) {
        diagram.move(box, point);
    }

    @Override
    public void setName(String name) {
        box.setName(name);
        diagram.update(box);
    }

    @Override
    public BoxType getType() {
        return null;
    }

    @Override
    public VariableData[] getVariables() {
        return new VariableData[0];
    }

    @Override
    public MethodData[] getMethods() {
        return new MethodData[0];
    }

    public boolean isEmpty() {
        return box == null;
    }

    @Override
    public void subscribe(UiObserver observer) {

    }
}
