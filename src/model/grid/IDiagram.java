package model.grid;

import model.boxes.Box;
import model.point.ScaledPoint;
import model.relations.Relation;

public interface IDiagram {
    void update(Box box);
    boolean isOccupied(ScaledPoint position);
    boolean canMergeLines(Relation relation, ScaledPoint position);
}
