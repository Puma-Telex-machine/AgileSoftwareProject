package model;

import model.grid.boxes.Box;
import model.grid.BoxGrid;
import model.grid.RelationGrid;

import java.awt.*;

public class Diagram {

    BoxGrid boxGrid = new BoxGrid(6, 8);
    RelationGrid relationGrid = new RelationGrid(3, 4);
    //HashSet<Relation> relations = new HashSet<>();

    public void move(Box box, Point position) {
        Point newPosition = boxGrid.moveBox(box, position);
        box.setPosition(newPosition);
    }

    public Box createBox(Point point) {
        return boxGrid.createBox(point);
    }

    public void deleteBox(Box box) {
        boxGrid.removeBox(box.getPosition());
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


}
