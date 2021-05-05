package model.grid;

import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class RelationGrid {

    AStar aStar;
    TreeMap<ScaledPoint, HashSet<PathNode>> relationMap;
    HashSet<Relation> relations;

    public RelationGrid(IDiagram diagram) {
        this.aStar = new AStar(diagram);
        this.relationMap = new TreeMap<>();
        this.relations = new HashSet<>();
    }

    public void add(Relation relation) {
        relations.add(relation);
        findPath(relation);
    }

    public void refreshAllPaths() {
        for (Relation r : relations) {
            findPath(r);
        }
    }

    private void findPath(Relation relation) {
        PathNode current = null;
        try {
            current = aStar.findPath(relation);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<ScaledPoint> pathPoints = new ArrayList<>();
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
        relation.setPath(pathPoints);
    }

    boolean canMergeLines(Relation relation, ScaledPoint position) {
        if (!relationMap.containsKey(position)) return true;

        for (PathNode n : relationMap.get(position)) {
            if (n.relation.getTo() == relation.getTo() && n.relation.getArrowType() == relation.getArrowType()) {
                return true;
            }
        }
        return false;
    }
}
