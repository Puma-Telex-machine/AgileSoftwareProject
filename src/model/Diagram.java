package model;

import model.boxes.Box;
import model.grid.BoxGrid;

import java.awt.*;

public class Diagram {

    BoxGrid boxGrid = new BoxGrid(6, 8);
    //ScaledGrid<Relation> relationGrid = new ScaledGrid<>(3, 4);
    //HashSet<Relation> relations = new HashSet<>();

    public void move(Box box, Point position) {
        if (boxGrid.moveBox(box, position)) {
            box.setPosition(position);
        }
    }

    public Box createBox(Point point) {
        return boxGrid.createBox(point);
    }

    public void deleteMethod(Box box, String methodName) {
        box.deleteMethod(methodName);
    }

    public void deleteVariable(Box box, String variableName) {
        box.deleteVariable(variableName);
    }

    public void editVariable(Box box, VariableData variableData) {
        box.editVariable(variableData);
    }

    public void editMethod(Box box, MethodData methodData) {
        box.editMethod(methodData);
    }

    public void deleteBox(Box box) {
        boxGrid.removeBox(box.getPosition());
    }
}
