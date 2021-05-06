package model.grid;

import model.boxes.Box;
import model.point.ScaledPoint;
import model.relations.Relation;

public interface IDiagram {
    void set(Box box);
    void remove(Box box);
    void set(Relation relation);
    void remove(Relation relation);
    boolean isOccupied(ScaledPoint position);
    boolean canMergeLines(Relation relation, ScaledPoint position);
}
