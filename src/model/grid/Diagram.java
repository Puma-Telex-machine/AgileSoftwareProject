package model.grid;

import model.MethodData;
import model.VariableData;
import model.boxes.Box;
import model.pathfinder.GridView;
import model.pathfinder.Pathfinder;
import model.relations.ArrowType;
import model.relations.Relation;

import java.awt.*;
import java.util.HashSet;

public class Diagram implements GridView {

    ScaledGrid<Box> boxGrid = new ScaledGrid<>(6, 8);
    RelationGrid relationGrid = new RelationGrid(boxGrid, 3, 4);
    Pathfinder pathfinder = new Pathfinder(boxGrid, relationGrid);

    HashSet<Relation> relations = new HashSet<>();

    public Relation createRelation(Box to, Box from, ArrowType arrowType) {
        Relation relation = new Relation(to, from, arrowType);

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

    @Override
    public int getMoveCost(Point point) {
        return 0;
    }

    @Override
    public int getEstimatedCost(Point from, Point to) {
        return 0;
    }

    @Override
    public boolean isOccupied(Point point) {
        return false;
    }
}