package model.pathfinder;

import model.boxes.Box;
import model.grid.RelationGrid;
import model.grid.ScaledGrid;
import model.relations.Relation;

import java.awt.*;

public class Pathfinder implements GridView { // Is there a better way?

    ScaledGrid<Box> boxGrid;
    RelationGrid relationGrid;
    AStar aStar = new AStar(this);

    public Pathfinder(ScaledGrid<Box> boxGrid, RelationGrid relationGrid) {
        this.boxGrid = boxGrid;
        this.relationGrid = relationGrid;
    }

    Relation currentRelation;

    public void findPath(Relation relation) {
        currentRelation = relation;
        try {
            aStar.findPath(relation.getFrom().getPosition(), relation.getTo().getPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getMoveCost(Point point) {
        return 1;
    }

    @Override
    public int getEstimatedCost(Point from, Point to) {
        return (int) (Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()));
    }

    @Override
    public boolean isOccupied(Point point) {
        return !boxGrid.isEmpty(point);
    }

}
