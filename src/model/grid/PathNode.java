package model.grid;

import model.point.ScaledPoint;
import model.relations.Relation;

class PathNode {
    Relation relation;

    PathNode previous;
    Direction direction;
    ScaledPoint position;
    int cost;

    public PathNode(Relation relation) {
        this.relation = relation;
    }
}
