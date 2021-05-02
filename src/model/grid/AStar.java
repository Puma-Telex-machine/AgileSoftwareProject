package model.grid;

import model.point.Scale;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class AStar {

    BoxGridView boxGrid;
    RelationGrid relationGrid;

    TreeMap<ScaledPoint, RelationNode> visited = new TreeMap<>();
    PriorityQueue<RelationNode> discovered = new PriorityQueue<>();

    int stepCost = 1;
    int bendCost = 10;
    int crossCost = 100;

    public AStar(BoxGridView boxGrid, RelationGrid relationGrid) {
        this.boxGrid = boxGrid;
        this.relationGrid = relationGrid;
    }

    public ArrayList<Point> addRelation(Relation relation) {
        RelationNode startNode = new RelationNode(relation);
        startNode.position = new Point(relation.getFrom().getPosition());
        //startNode.position.translate(5, -10);
        startNode.cost = 0;
        discovered.add(startNode);

        while (!discovered.isEmpty()) {
            RelationNode current = discovered.remove();

            Point currentPoint = current.position;

            if (current.position.equals(current.destination)) return compilePath(current);

            visited.put(current.position, current);

            discover(current, Direction.UP);
            discover(current, Direction.DOWN);
            discover(current, Direction.LEFT);
            discover(current, Direction.RIGHT);
        }
        return null;
    }

    private ArrayList<Point> compilePath(RelationNode current) {
        ArrayList<Point> path = new ArrayList<>();
        while (current.previous != null) {
            path.add(current.position);
            current = current.previous;
        }
        return path;
    }

    void discover(RelationNode previous, Direction direction) {
        // Get the position to discover
        ScaledPoint position = new ScaledPoint(previous.position);
        position.translate(direction.x, direction.y);

        if (visited.containsKey(position)) {
            return;
        }

        if (!boxGrid.isEmpty(position)) {
            if (!position.equals(previous.destination)) {
                return;
            }
        }

        // Generate the newly discovered node
        RelationNode node = new RelationNode(previous.relation);
        node.previous = previous;
        node.direction = direction;
        node.position = position;

        // Calculate the cost
        int cost = previous.cost + stepCost;

        // If direction changes add the cost of bending
        if (direction != previous.direction) cost += bendCost;

        // If the node is occupied and the lines shouldn't merge
        //if (!relationGrid.get(position).canMergeLines(node)) cost += crossCost;

        // Add the node to unvisited
        node.cost = cost;
        discovered.add(node);
    }

    private static int getCostEstimate(RelationNode node) {
        return manhattanDistance(node.position, node.destination) + node.cost;
    }

    private static int manhattanDistance(ScaledPoint from, ScaledPoint to) {
        return (int) (Math.abs(from.getX(Scale.Backend) - to.getX(Scale.Backend)) + Math.abs(from.getY(Scale.Backend) - to.getY(Scale.Backend)));
    }

    private static class RelationNode implements Comparable<RelationNode> {
        Relation relation;
        ScaledPoint destination;

        RelationNode previous;
        Direction direction;
        ScaledPoint position;
        int cost;

        public RelationNode(Relation relation) {
            this.relation = relation;
            this.destination = relation.getTo().getPosition(); //Dumt?
        }

        @Override
        public int compareTo(RelationNode o) {
            return getCostEstimate(this) - getCostEstimate(o); // Correct order?
        }
    }

    private enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        private final int x;
        private final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
