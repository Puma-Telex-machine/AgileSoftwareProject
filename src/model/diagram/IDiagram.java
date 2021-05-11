package model.diagram;

import model.boxes.Box;
import global.point.ScaledPoint;
import model.relations.Relation;

public interface IDiagram {
    void updateBox(Box box);
    void removeBox(Box box);
    void updateRelation(Relation relation);
    void removeRelation(Relation relation);
    boolean isOccupied(ScaledPoint position);
    boolean canMergeLines(Relation relation, ScaledPoint position);
}
