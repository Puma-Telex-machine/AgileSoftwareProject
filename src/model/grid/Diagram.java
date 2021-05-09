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
    public void add(Box box) {
        boxGrid.add(box);
    }

    @Override
    public void update(Box box) {
        boxGrid.update(box);
        relationGrid.refreshAllPaths();
    }

    @Override
    public void remove(Box box) {
        boxGrid.remove(box);
        relationGrid.refreshAllPaths();
    }

    @Override
    public void add(Relation relation) {
        relationGrid.add(relation);
        relationGrid.refreshAllPaths();
    }

    @Override
    public void update(Relation relation) {
        relationGrid.add(relation);
        relationGrid.refreshAllPaths();     // Detta gör om arbetet i add, det beror på vilken funktionalitet vi vill ha.
                                            // Hur "känns det" när alla relationer uppdateras är väl det som är viktigast.
    }

    @Override
    public void remove(Relation relation) {
        relationGrid.remove(relation);
        relationGrid.refreshAllPaths();
    }

    public List<Box> getAllBoxes() {
        return boxGrid.getBoxes();
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