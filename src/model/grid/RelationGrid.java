package model.grid;

import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class RelationGrid {

    TreeMap<ScaledPoint, HashSet<PathNode>> relationMap = new TreeMap<>();
    HashSet<Relation> relations = new HashSet<>();

    boolean canMergeLines(Relation relation, ScaledPoint position) {
        if (!relationMap.containsKey(position)) return true;

        for (PathNode n : relationMap.get(position)) {
            if (n.relation.getTo() == relation.getTo() && n.relation.getArrowType() == relation.getArrowType()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ScaledPoint> add(PathNode pathStart) {
        ArrayList<ScaledPoint> pathPoints = new ArrayList<>();
        PathNode current = pathStart;
        while (current != null) {
            HashSet<PathNode> relations = relationMap.get(current.position);
            relations.add(current);

            if (current.previous != null) {
                if (current.direction != current.previous.direction) {
                    pathPoints.add(current.position);
                }
            }
            current = current.previous;
        }
        return pathPoints;
    }
}
