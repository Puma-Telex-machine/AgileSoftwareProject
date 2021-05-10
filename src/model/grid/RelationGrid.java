package model.grid;

import model.boxes.Box;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.*;

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
            if (relations == null) relations = new HashSet<>();
            relations.add(current);

            pathPoints.add(current.position);
            /*
            if (current.previous != null) {
                if (current.direction != current.previous.direction) {
                    pathPoints.add(current.position);
                }
            }

             */
            current = current.previous;
        }

        Collections.reverse(pathPoints);

        relation.setPath(pathPoints); //TODO: Något är fel, pathpoints innehåller två av samma punkt (sista)
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

    public List<Relation> getRelations() {
        return new ArrayList<>(relations);
    }

    public void remove(Relation relation) {
        relations.remove(relation);
    }
}
