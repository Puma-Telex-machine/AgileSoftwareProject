package model.grid;

import model.boxes.Box;
import model.point.ScaledPoint;
import model.relations.Relation;

public interface IDiagram {
    void update(Box box);
    void remove(Box box);
    void update(Relation relation);
    void remove(Relation relation);
    boolean isOccupied(ScaledPoint position);
    boolean canMergeLines(Relation relation, ScaledPoint position);
}
