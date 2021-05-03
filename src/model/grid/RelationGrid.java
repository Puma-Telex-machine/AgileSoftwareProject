package model.grid;

import model.point.Scale;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.ArrayList;
import java.util.TreeMap;

public class RelationGrid {

    TreeMap<ScaledPoint, ArrayList<PathNode>> relationMap = new TreeMap<>();

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
            ArrayList<PathNode> relations = relationMap.get(current.position);
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

    private double bearing(ScaledPoint from, ScaledPoint to) {
        return Math.atan2(from.getY(Scale.Internal) - to.getY(Scale.Internal), to.getX(Scale.Internal) - from.getX(Scale.Internal));
    }
}
