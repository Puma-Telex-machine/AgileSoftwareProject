package model.grid;

import model.boxes.Box;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.ArrayList;

public class Diagram implements IDiagram {

    public BoxGrid boxGrid = new BoxGrid();
    RelationGrid relationGrid = new RelationGrid();
    AStar aStar = new AStar(this);


    public void addBox(Box box) {
        boxGrid.add(box);
        for (Relation r : relations) {
            findPath(r);
        }
    }

    public void addRelation(Relation relation) {
        relations.add(relation);
        findPath(relation);
    }

    private void findPath(Relation relation) {
        PathNode path = aStar.findPath(relation);
        ArrayList<ScaledPoint> pathPoints = relationGrid.add(path);
        relation.setPath(pathPoints);
    }
}