package model.grid;

import model.point.ScaledPoint;
import model.relations.Relation;

public interface IDiagram {
    boolean isOccupied(ScaledPoint position);
    boolean canMergeLines(Relation relation, ScaledPoint position);
}
