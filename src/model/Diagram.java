package model;

import model.boxes.Box;
import model.grid.ScaledGrid;
import model.relations.ArrowType;
import model.relations.Relation;

import java.awt.*;
import java.util.HashSet;

public class Diagram {

    ScaledGrid<Box> boxGrid = new ScaledGrid<>(6, 8);
    RelationGrid relationGrid = new RelationGrid(boxGrid, 3, 4);

    HashSet<Relation> relations = new HashSet<>();

    public Relation createRelation(Box to, Box from, ArrowType arrowType) {
        Relation relation = new Relation(to, from, arrowType);
        relations.add(relation);
        return relation;
    }


    public Box createBox(Point position) {
        Box box = new Box("Box", position);

        if (boxGrid.set(position, box)) {
            return box;
        }
        return null;
    }

    public void moveBox(Box box, Point to) {
        Point newPosition = boxGrid.move(box.getPosition(), to);
        box.setPosition(newPosition);
    }

    public void removeBox(Point position) {
        boxGrid.remove(position);
    }

    public void deleteBox(Box box) {
    }

    public void editMethod(Box box, MethodData methodData) {
    }

    public void editVariable(Box box, VariableData variableData) {
    }

    public void deleteMethod(Box box, String methodName) {
    }

    public void deleteVariable(Box box, String variableName) {
    }

    public void move(Box box, Point point) {

    }
}
