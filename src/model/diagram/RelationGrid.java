package model.diagram;

import global.point.Scale;
import global.point.ScaledPoint;
import model.relations.Relation;

import java.util.*;

public class RelationGrid {

    AStar aStar;
    TreeMap<ScaledPoint, HashSet<PathNode>> relationMap = new TreeMap<>();;
    PriorityQueue<Relation> relations = new PriorityQueue<>((r1, r2) -> (int) (calculateAngle(r2) - calculateAngle(r1))); //Not sure if this did any differance
    //HashSet<Relation> relations = new HashSet<>();
    int crossCost = 30;
    int stepCost = 0;

    public RelationGrid(AStar aStar) {
        this.aStar = aStar;
    }

    private double calculateAngle(Relation r) {
        ScaledPoint P1 = r.getFrom().getPosition();
        ScaledPoint P2 = r.getStartPosition();
        ScaledPoint P3 = r.getEndPosition();
        return Math.atan2(P3.getY(Scale.Backend) - P1.getY(Scale.Backend), P3.getX(Scale.Backend) - P1.getX(Scale.Backend)) -
                Math.atan2(P2.getY(Scale.Backend) - P1.getY(Scale.Backend), P2.getX(Scale.Backend) - P1.getX(Scale.Backend));
    }

    public void add(Relation relation) {
        boolean test = relations.add(relation);
        System.out.println(test);
    }

    public void refreshAllPaths() {
        System.out.println();
        System.out.println("Refreshing all paths!!!");
        //todo: runs twice on creation of box

        relationMap = new TreeMap<>();
        for (Relation r : relations) {
            calculatePath(r);
        }
    }

    private void calculatePath(Relation relation) {
        PathNode path = findPath(relation); //todo: runs too often (on  click of point as well at least)
        relation.setPath(extractPathBends(path));
    }

    private PathNode findPath(Relation relation) {
        System.out.println("Finding path from: " + relation.getEndPosition().getX(Scale.Backend) + "," + relation.getEndPosition().getY(Scale.Backend) + " to " + relation.getStartPosition().getX(Scale.Backend) + "," + relation.getStartPosition().getY(Scale.Backend));
        PathNode current = null;
        try {
            current = aStar.findPath(relation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return current;
    }

    private ArrayList<ScaledPoint> extractPathBends(PathNode destination) {
        PathNode current = destination;

        ArrayList<ScaledPoint> pathPoints = new ArrayList<>();
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

        return pathPoints;
    }

    public int crossCost(Relation relation, ScaledPoint position) {
        if (!relationMap.containsKey(position)) {
            return stepCost;
        }

        for (PathNode n : relationMap.get(position)) {
            if ((n.relation.getTo() == relation.getTo() || n.relation.getFrom() == relation.getFrom()) && n.relation.getArrowType() == relation.getArrowType()) {
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
