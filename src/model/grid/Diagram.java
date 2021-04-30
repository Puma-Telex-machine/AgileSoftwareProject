package model.grid;

import model.MethodData;
import model.VariableData;
import model.boxes.Box;
import model.relations.ArrowType;
import model.relations.Relation;

import java.awt.*;

public class Diagram {

    BoxGrid boxGrid = new BoxGrid(3, 4); // 6, 8
    RelationGrid relationGrid = new RelationGrid(boxGrid, 3, 4); //3, 4
    //Pathfinder pathfinder = new Pathfinder(boxGrid, relationGrid);

    //HashSet<Relation> relations = new HashSet<>();

    public Relation createRelation(Box to, Box from, ArrowType arrowType) {
        Relation relation = new Relation(to, from, arrowType);
        //relation.setPath(relationGrid.findPath());
        return relation;
    }


    public Box createBox(Point position) {

        return boxGrid.createBox(position);

        /*
        Box box = new Box("Box", position);

        if (boxGrid.set(position, box)) {
            return box;
        }
        return null;

         */
    }

    public void moveBox(Box box, Point position) {
        boxGrid.moveBox(box, position);
        /*
        Point newPosition = boxGrid.move(box.getPosition(), to);
        box.setPosition(newPosition);
         */
    }

    public void removeBox(Point position) {
        boxGrid.removeBox(position);
        //boxGrid.remove(position);
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
        box.setPosition(boxGrid.moveBox(box, point));
    }

    public BoxGrid getBoxGrid() {
        return boxGrid;
    }
}