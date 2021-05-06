package model.grid;

import model.boxes.Box;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.List;

public class Diagram implements IDiagram {

    //TODO: update database whenever diagram is updated

    BoxGrid2 boxGrid = new BoxGrid2();
    RelationGrid relationGrid = new RelationGrid(this);

    @Override
    public void set(Box box) {
        boxGrid.update(box);
        relationGrid.refreshAllPaths();
    }

    @Override
    public void remove(Box box) {
        boxGrid.remove(box);
    }

    public List<Box> getAllBoxes() {
        return boxGrid.getBoxes();
    }

    public void set(Relation relation) {
        relationGrid.add(relation);
    }

    @Override
    public void remove(Relation relation) {
        relationGrid.remove(relation);
    }

    public List<Relation> getAllRelations() {
        return relationGrid.getRelations();
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