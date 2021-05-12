package model.grid;

import global.point.Scale;
import global.point.ScaledPoint;
import model.diagram.IDiagram;
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
        System.out.println();
        System.out.println("Refreshing all paths!!!");

        relationMap = new TreeMap<>();
        for (Relation r : relations) {
            findPath(r);
        }
    }

    private void findPath(Relation relation) {
        //todo: prevent this from being called way to many times
        //todo: remove random printouts before production
        System.out.println("Finding path from: " + relation.getFromPosition().getX(Scale.Backend) + "," + relation.getFromPosition().getY(Scale.Backend) + " to " + relation.getToPosition().getX(Scale.Backend) + "," + relation.getToPosition().getY(Scale.Backend));
        PathNode current = null;
        try {
            current = aStar.findPath(relation);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<ScaledPoint> pathPoints = new ArrayList<>();

        assert current != null;
        pathPoints.add(current.position);

        while (current != null) {
            HashSet<PathNode> relations = relationMap.computeIfAbsent(current.position, k -> new HashSet<>());
            relations.add(current);

            if (current.previous != null && current.direction != current.previous.direction) {
                pathPoints.add(current.previous.position);
            }

            current = current.previous;
        }

        Collections.reverse(pathPoints);

        relation.setPath(pathPoints);
    }

    int crossCost = 100;
    int stepCost = 0;

    public int moveCost(Relation relation, ScaledPoint position) {
        if (!relationMap.containsKey(position)) {
            return stepCost;
        }

        for (PathNode n : relationMap.get(position)) {
            if (n.relation.getTo() == relation.getTo() && n.relation.getArrowType() == relation.getArrowType()) {
                return stepCost;
            }
        }
        return crossCost;
    }

    public List<Relation> getRelations() {
        return new ArrayList<>(relations);
    }

    public void remove(Relation relation) {
        relations.remove(relation);
    }
}
