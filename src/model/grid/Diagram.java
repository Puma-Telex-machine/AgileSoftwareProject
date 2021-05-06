package model.grid;

import model.boxes.Box;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.List;

public class Diagram implements IDiagram {

    //TODO: Box implementerar BoxFacade

    public BoxGrid2 boxGrid = new BoxGrid2(); //TODO: denna borde inte behöva vara public
    RelationGrid relationGrid = new RelationGrid(this);

    public void add(Box box) {
        boxGrid.update(box);
        relationGrid.refreshAllPaths();
    }

    @Override
    public void update(Box box) {
        boxGrid.update(box);
        relationGrid.refreshAllPaths();
    }

    public List<Box> getAllBoxes() {
        return boxGrid.getBoxes();
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