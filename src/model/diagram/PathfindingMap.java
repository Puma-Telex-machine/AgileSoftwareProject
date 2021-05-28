package model.diagram;

import global.point.ScaledPoint;
import model.relations.Relation;

public interface PathfindingMap {
    boolean isOccupied(ScaledPoint position);
    int moveCost(Relation relation, ScaledPoint position);
}
