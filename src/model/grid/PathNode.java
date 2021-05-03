package model.grid;

import model.point.ScaledPoint;
import model.relations.Relation;

class PathNode implements Comparable<PathNode> {
    private final AStar aStar;
    Relation relation;

    PathNode previous;
    Direction direction;
    ScaledPoint position;
    int cost;

    public PathNode(AStar aStar, Relation relation) {
        this.aStar = aStar;
        this.relation = relation;
    }

    @Override
    public int compareTo(PathNode o) {
        return aStar.getCostEstimate(this) - aStar.getCostEstimate(o); // Correct order?
    }
}
