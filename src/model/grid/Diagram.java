package model.grid;

import model.boxes.Box;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.ArrayList;

public class Diagram implements IDiagram {

    //TODO: Box implementerar BoxFacade

    public BoxGrid boxGrid = new BoxGrid(); //TODO: denna borde inte behöva vara public
    RelationGrid relationGrid = new RelationGrid(this);

    public void add(Box box) {
        boxGrid.add(box);
        relationGrid.refreshAllPaths();
    }

    public void add(Relation relation) {
        relationGrid.add(relation);
    }

    @Override
    public boolean isOccupied(ScaledPoint position) {
        return boxGrid.isOccupied(position);
    }

    @Override
    public boolean canMergeLines(Relation relation, ScaledPoint position) {
        return relationGrid.canMergeLines(relation, position);
    }
}